#!/usr/bin/env python3
"""복습 덱 CLI (라이트너). 원본은 data/study.db, notes/review-deck.md는 export한 읽기용 뷰.

  deck.py import                 notes/review-deck.md 표를 DB로 (최초 1회. 이미 있으면 거부)
  deck.py due [--limit N]        만기 카드를 상자 낮은 순으로 (id, 상자, 만기일, 문맥 한 줄)
  deck.py show ID                질문 / 정답 원문(줄바꿈 그대로) / 이력
  deck.py grade ID pass|hold|fail [--note "..."]
  deck.py add --q "..." --a "..." --src "..."
  deck.py stats                  카드 수, 만기, 상자 분포, 최근 3회 연속 실패 카드
  deck.py export                 DB → notes/review-deck.md (운영 규칙 머리글은 기존 파일 것을 유지)

설계: tools/DESIGN.md 4.1. 판정(pass/hold/fail)은 사람이 하고 도구는 상자와 날짜만 옮긴다.
"""
import argparse
import datetime as dt
import os
import re
import sqlite3
import sys

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
DB = os.path.join(ROOT, "data", "study.db")
MD = os.path.join(ROOT, "notes", "review-deck.md")
INTERVAL = {1: 1, 2: 3, 3: 7, 4: 14, 5: 30}
GRADUATE_BOX = 6


def today(args):
    return dt.date.fromisoformat(args.date) if getattr(args, "date", None) else dt.date.today()


def connect():
    os.makedirs(os.path.dirname(DB), exist_ok=True)
    con = sqlite3.connect(DB)
    con.execute("PRAGMA foreign_keys = ON")
    con.executescript(
        """
        CREATE TABLE IF NOT EXISTS cards (
          id INTEGER PRIMARY KEY, question TEXT NOT NULL, answer TEXT NOT NULL,
          source TEXT NOT NULL DEFAULT '', box INTEGER NOT NULL, due DATE NOT NULL,
          created DATE, retired DATE, kind TEXT NOT NULL DEFAULT 'recall');  -- recall / read-aloud / spoken
        CREATE TABLE IF NOT EXISTS card_reviews (
          id INTEGER PRIMARY KEY AUTOINCREMENT, card_id INTEGER NOT NULL REFERENCES cards(id),
          date DATE NOT NULL, outcome TEXT NOT NULL, note TEXT,
          box_before INTEGER, box_after INTEGER);
        """
    )
    return con


# ---------- import / export ----------

def parse_md_rows(text):
    rows = []
    for line in text.splitlines():
        if not re.match(r"^\| \d+ \|", line):
            continue
        cells = [c.strip() for c in line.strip().strip("|").split("|")]
        if len(cells) == 7:
            cid, kind, q, a, src, box, due = cells
        elif len(cells) == 6:
            cid, q, a, src, box, due = cells; kind = "recall"
        else:
            raise SystemExit(f"열 개수 {len(cells)}: {line[:60]}")
        rows.append((int(cid), q, a, src, int(box), due, kind))
    return rows


def cmd_import(args):
    con = connect()
    if con.execute("SELECT COUNT(*) FROM cards").fetchone()[0]:
        raise SystemExit("DB에 이미 카드가 있다. import는 최초 1회만")
    rows = parse_md_rows(open(MD, encoding="utf-8").read())
    con.executemany(
        "INSERT INTO cards (id, question, answer, source, box, due, created, kind) VALUES (?,?,?,?,?,?,?,?)",
        [(r[0], r[1], r[2], r[3], r[4], r[5], None, r[6]) for r in rows],
    )
    con.commit()
    print(f"{len(rows)}장 import 완료 → {os.path.relpath(DB, ROOT)}")


def cmd_export(args):
    con = connect()
    head = open(MD, encoding="utf-8").read()
    marker = "## 카드"
    if marker in head:
        head = head[: head.index(marker)]
    lines = [head.rstrip("\n"), "", marker, "",
             "이 표는 `python3 tools/deck.py export`가 data/study.db에서 생성한다. 직접 고치지 말 것 (다음 export에 덮인다). 카드 수정은 `deck.py`로.",
             "",
             "| # | 종류 | 질문 | 정답 요지 | 출처(문맥) | DB 이력 | 상자 | 다음 복습 |", "|---|---|---|---|---|---|---|---|"]
    for r in con.execute("SELECT id, kind, question, answer, source, box, due FROM cards WHERE retired IS NULL ORDER BY id"):
        hist = " / ".join(f"{dd[5:]} {o}" + (f"({n})" if n else "") for dd, o, n in con.execute(
            "SELECT date, outcome, note FROM card_reviews WHERE card_id=? ORDER BY id", (r[0],)))
        lines.append("| " + " | ".join([str(r[0]), r[1], r[2], r[3], r[4], hist, str(r[5]), r[6]]) + " |")
    retired = con.execute("SELECT id, question, retired FROM cards WHERE retired IS NOT NULL ORDER BY id").fetchall()
    if retired:
        lines += ["", "## 졸업한 카드", ""] + [f"- {r[0]}: {r[1]} ({r[2]})" for r in retired]
    open(MD, "w", encoding="utf-8").write("\n".join(lines) + "\n")
    print(f"export 완료 → {os.path.relpath(MD, ROOT)}")


# ---------- daily use ----------

def context_line(source):
    first = re.split(r" → |\. ", source, maxsplit=1)[0]
    return first[:70]


def cmd_due(args):
    con = connect()
    d = today(args).isoformat()
    rows = con.execute(
        "SELECT id, box, due, question, source, kind FROM cards WHERE retired IS NULL AND due <= ? ORDER BY box, due, id",
        (d,),
    ).fetchall()
    total = len(rows)
    if args.limit:
        rows = rows[: args.limit]
    print(f"만기 {total}장 (기준 {d}), 표시 {len(rows)}장")
    for cid, box, due, q, src, kind in rows:
        tag = "" if kind == "recall" else f" ({kind})"
        print(f"[{cid:>2}] 상자{box} 만기{due}{tag}  {q[:60]}")
        print(f"      문맥: {context_line(src)}")


def cmd_show(args):
    con = connect()
    r = con.execute("SELECT id, question, answer, source, box, due FROM cards WHERE id=?", (args.id,)).fetchone()
    if not r:
        raise SystemExit(f"카드 {args.id} 없음")
    kind = con.execute("SELECT kind FROM cards WHERE id=?", (args.id,)).fetchone()[0]
    print(f"카드 {r[0]}  (종류 {kind}, 상자 {r[4]}, 만기 {r[5]})")
    print("질문:", r[1])
    print("정답:")
    for part in r[2].split("<br>"):
        print("  -", part.strip())
    print("이력:", r[3])
    for rv in con.execute("SELECT date, outcome, box_before, box_after, note FROM card_reviews WHERE card_id=? ORDER BY id", (args.id,)):
        print(f"  {rv[0]} {rv[1]} {rv[2]}→{rv[3]} {rv[4] or ''}")


def cmd_grade(args):
    con = connect()
    r = con.execute("SELECT box, source FROM cards WHERE id=? AND retired IS NULL", (args.id,)).fetchone()
    if not r:
        raise SystemExit(f"카드 {args.id} 없음(또는 졸업)")
    box, source = r
    d = today(args)
    if args.outcome == "pass":
        new_box = box + 1
    elif args.outcome == "hold":
        new_box = box
    else:
        new_box = 1
    # 이력은 card_reviews에만 쌓는다. source는 문맥(어느 문제의 어떤 상황) 전용 (08-18 검토 반영)
    if new_box >= GRADUATE_BOX:
        con.execute("UPDATE cards SET box=?, retired=? WHERE id=?", (new_box, d.isoformat(), args.id))
        due = None
    else:
        due = d + dt.timedelta(days=INTERVAL[new_box])
        con.execute("UPDATE cards SET box=?, due=? WHERE id=?", (new_box, due.isoformat(), args.id))
    con.execute(
        "INSERT INTO card_reviews (card_id, date, outcome, note, box_before, box_after) VALUES (?,?,?,?,?,?)",
        (args.id, d.isoformat(), args.outcome, args.note, box, new_box),
    )
    con.commit()
    print(f"카드 {args.id}: {args.outcome} 상자 {box}→{new_box}" + (f", 다음 {due}" if due else ", 졸업"))


def cmd_add(args):
    con = connect()
    d = today(args)
    nid = (con.execute("SELECT COALESCE(MAX(id),0) FROM cards").fetchone()[0]) + 1
    due = d + dt.timedelta(days=INTERVAL[1])
    con.execute(
        "INSERT INTO cards (id, question, answer, source, box, due, created, kind) VALUES (?,?,?,?,1,?,?,?)",
        (nid, args.q, args.a, args.src or f"{d.strftime('%m-%d')} 신설", due.isoformat(), d.isoformat(), args.kind),
    )
    con.commit()
    print(f"카드 {nid} 추가 (상자 1, 다음 {due})")


def cmd_stats(args):
    con = connect()
    d = today(args).isoformat()
    total = con.execute("SELECT COUNT(*) FROM cards WHERE retired IS NULL").fetchone()[0]
    due = con.execute("SELECT COUNT(*) FROM cards WHERE retired IS NULL AND due <= ?", (d,)).fetchone()[0]
    print(f"카드 {total}장, 만기 {due}장 (기준 {d})")
    for box, n in con.execute("SELECT box, COUNT(*) FROM cards WHERE retired IS NULL GROUP BY box ORDER BY box"):
        print(f"  상자 {box}: {n}장")
    # 최근 3회 연속 fail
    bad = []
    for (cid,) in con.execute("SELECT id FROM cards WHERE retired IS NULL"):
        last = [o for (o,) in con.execute(
            "SELECT outcome FROM card_reviews WHERE card_id=? ORDER BY id DESC LIMIT 3", (cid,))]
        if len(last) == 3 and all(o == "fail" for o in last):
            bad.append(cid)
    if bad:
        print("최근 3회 연속 실패 (질문 설계를 의심할 것):", bad)
    else:
        print("최근 3회 연속 실패 카드: 없음 (DB 이력 기준. import 이전 이력은 출처 열 참조)")


def main():
    p = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    p.add_argument("--date", help="기준 날짜 YYYY-MM-DD (기본 오늘)")
    sub = p.add_subparsers(dest="cmd", required=True)
    sub.add_parser("import").set_defaults(f=cmd_import)
    sub.add_parser("export").set_defaults(f=cmd_export)
    s = sub.add_parser("due"); s.add_argument("--limit", type=int, default=0); s.set_defaults(f=cmd_due)
    s = sub.add_parser("show"); s.add_argument("id", type=int); s.set_defaults(f=cmd_show)
    s = sub.add_parser("grade"); s.add_argument("id", type=int); s.add_argument("outcome", choices=["pass", "hold", "fail"])
    s.add_argument("--note", default=""); s.set_defaults(f=cmd_grade)
    s = sub.add_parser("add"); s.add_argument("--q", required=True); s.add_argument("--a", required=True)
    s.add_argument("--src", default=""); s.add_argument("--kind", default="recall", choices=["recall", "read-aloud", "spoken"]); s.set_defaults(f=cmd_add)
    sub.add_parser("stats").set_defaults(f=cmd_stats)
    args = p.parse_args()
    args.f(args)


if __name__ == "__main__":
    main()
