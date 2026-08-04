#!/usr/bin/env python3
"""LeetCode 문제 스켈레톤 생성기.

LeetCode GraphQL에서 문제 정보를 실시간으로 받아 6단계 템플릿 자바 파일을 만든다.
유형 라벨(topicTags)은 일부러 요청하지 않는다 (패턴 라벨 미노출 규칙).

사용법:
  python3 tools/skeleton.py two-sum
  python3 tools/skeleton.py https://leetcode.com/problems/two-sum/
  python3 tools/skeleton.py two-sum --dir src/problems/inbox
"""

import argparse
import datetime
import json
import re
import sys
import urllib.request
from pathlib import Path

GRAPHQL_URL = "https://leetcode.com/graphql"
# topicTags를 여기 추가하지 말 것: 유형 칸은 풀고 나서 사용자가 채운다
QUERY = """
query q($titleSlug: String!) {
  question(titleSlug: $titleSlug) {
    questionFrontendId
    title
    titleSlug
    difficulty
    exampleTestcases
    codeSnippets { langSlug code }
  }
}
"""


def fetch_question(slug: str) -> dict:
    body = json.dumps({"query": QUERY, "variables": {"titleSlug": slug}}).encode()
    req = urllib.request.Request(
        GRAPHQL_URL,
        data=body,
        headers={
            "Content-Type": "application/json",
            "Referer": "https://leetcode.com",
            # 기본 UA(Python-urllib)는 403으로 차단된다
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)",
        },
    )
    with urllib.request.urlopen(req, timeout=15) as res:
        data = json.load(res)
    q = data.get("data", {}).get("question")
    if not q:
        sys.exit(f"문제를 찾지 못했다: {slug} (slug 확인)")
    return q


def slug_from_arg(arg: str) -> str:
    m = re.search(r"leetcode\.com/problems/([^/?]+)", arg)
    return m.group(1) if m else arg.strip().strip("/")


def pascal_title(title: str) -> str:
    words = re.findall(r"[A-Za-z0-9]+", title)
    return "".join(w[:1].upper() + w[1:] for w in words)


def java_method(snippets: list) -> str:
    code = next((s["code"] for s in snippets if s["langSlug"] == "java"), None)
    if not code:
        sys.exit("자바 스니펫이 없는 문제다")
    # class Solution { ... } 껍데기를 벗기고 안쪽 멤버만 남긴다
    m = re.search(r"class\s+Solution\s*(?:extends[^{]*)?\{(.*)\}\s*$", code, re.S)
    inner = m.group(1) if m else code
    lines = [ln for ln in inner.splitlines() if ln.strip()]
    return "\n".join(lines)


def build_file(q: dict, today: str) -> tuple[str, str]:
    num = int(q["questionFrontendId"])
    cls = f"P{num:04d}{pascal_title(q['title'])}"
    examples = "\n".join(
        f"        // {ln}" for ln in q["exampleTestcases"].splitlines()
    )
    method = java_method(q["codeSnippets"])
    src = f"""package problems.inbox;

/**
 * {num}. {q['title']}
 * https://leetcode.com/problems/{q['titleSlug']}/
 * 난이도: {q['difficulty']} | 유형: -
 *
 * 기록:
 * - 첫 풀이: {today}
 *
 * 접근:
 * 1. 입출력 정의:
 * 2. 제약 조건:
 * 3. 예제 손으로 + 함정 찾기:
 * 4. 브루트포스 + 복잡도:
 * 5. 병목 찾기 → 도구 선택:
 * 6. 검증 (예제 + 엣지 케이스):
 *
 * 시간복잡도: O(?) / 공간복잡도: O(?)
 */
public class {cls} {{

{method}

    public static void main(String[] args) {{
        {cls} s = new {cls}();
        // 공식 예제 입력 (한 문제당 여러 줄. 호출 형태로 바꿔서 기대값 주석과 함께 쓸 것):
{examples}
    }}
}}
"""
    return cls, src


def main() -> None:
    ap = argparse.ArgumentParser()
    ap.add_argument("problem", help="title slug 또는 문제 URL")
    ap.add_argument("--dir", default="src/problems/inbox", help="출력 디렉토리")
    args = ap.parse_args()

    q = fetch_question(slug_from_arg(args.problem))
    today = datetime.date.today().isoformat()
    cls, src = build_file(q, today)

    out_dir = Path(args.dir)
    out_dir.mkdir(parents=True, exist_ok=True)
    out = out_dir / f"{cls}.java"
    if out.exists():
        sys.exit(f"이미 존재한다: {out} (덮어쓰지 않음)")
    out.write_text(src, encoding="utf-8")
    print(f"생성: {out}  ({q['difficulty']})")


if __name__ == "__main__":
    main()
