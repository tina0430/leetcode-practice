#!/usr/bin/env python3
"""notes/ 문서 건강 점검. 세션 시작 때 실행한다: python3 tools/doc_health.py

각 문서에 기대 갱신 주기를 두고, 마지막 수정일이 그 주기를 넘기면 STALE로 표시한다.
문서가 죽어 가는 것을 세션이 바뀌어도 사람이 아니라 스크립트가 잡게 하려는 것이다
(2026-08-18: recap-log 등 3종이 08-07 이후 11일간 멈춰 있었는데 아무도 몰랐다).
주기가 없는 문서(모듈, 템플릿)는 사건 기반이라 점검 대상에서 뺀다.
"""
import datetime as dt
import glob
import os
import sys

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
TODAY = dt.date.today()

# (경로, 기대 주기(일), 갱신 지점)
DOCS = [
    ("notes/review-deck.md", 1, "매일 덱 문답 후 상자/날짜 갱신"),
    ("notes/concepts.md", 7, "새 개념/패턴 색인. 일주일 넘게 안 늘면 딥다이브가 멈춘 것"),
    ("notes/english/english-speaking.md", 3, "종료 체크리스트 5-①, ④ (정당화 용어, 연결 문구, 노출 횟수)"),
    ("notes/english/english-reading.md", 7, "종료 체크리스트 5-② (지문에서 막힌 표현)"),
    ("notes/english/recap-log.md", 4, "화·금 리캡 전사. 4일 넘으면 이번 주 리캡을 건너뛴 것"),
    ("notes/modules/curriculum.md", 7, "일요일 주간 점검 또는 일정 메일 도착 시 5·6절 재단"),
]


def days_since(path):
    full = os.path.join(ROOT, path)
    if not os.path.exists(full):
        return None
    m = dt.date.fromtimestamp(os.path.getmtime(full))
    return (TODAY - m).days, m


def main():
    stale = 0
    print(f"doc health @ {TODAY}")
    print(f"{'문서':40} {'마지막 수정':12} {'경과':>4} {'주기':>4}  상태")
    for path, cadence, where in DOCS:
        r = days_since(path)
        if r is None:
            print(f"{path:40} {'없음':12} {'-':>4} {cadence:>4}  MISSING")
            stale += 1
            continue
        d, m = r
        status = "OK" if d <= cadence else "STALE"
        if status == "STALE":
            stale += 1
        print(f"{path:40} {m.isoformat():12} {d:>4} {cadence:>4}  {status}   ({where})" if status == "STALE"
              else f"{path:40} {m.isoformat():12} {d:>4} {cadence:>4}  {status}")

    daily = os.path.join(ROOT, "notes/daily", f"{TODAY.isoformat()}.md")
    print(f"{'notes/daily/' + TODAY.isoformat() + '.md':40} {'':12} {'':>4} {'':>4}  "
          + ("OK" if os.path.exists(daily) else "아직 없음 (세션 종료 전에 만든다)"))

    dailies = sorted(glob.glob(os.path.join(ROOT, "notes/daily/2026-*.md")))
    if dailies:
        last = os.path.basename(dailies[-1])[:-3]
        gap = (TODAY - dt.date.fromisoformat(last)).days
        if gap >= 2:
            print(f"마지막 일일 노트 {last}, {gap}일 공백")
    print(f"\nSTALE/MISSING {stale}건" if stale else "\n전부 OK")
    return 1 if stale else 0


if __name__ == "__main__":
    sys.exit(main())
