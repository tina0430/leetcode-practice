# leetcode-practice

취업 준비를 위한 LeetCode 풀이 저장소 (Java 21, IntelliJ).

## 구조

```
src/
  problems/    풀이 (제출하는 코드)
    inbox/     새로 받은 문제 (풀리면 유형 패키지로 이동)
    array/     유형별 패키지 (hashmap, string, twopointers, slidingwindow,
    dp/        stack, binarysearch, linkedlist, tree, heap, backtracking,
    ...        graph, greedy, intervals)
  lab/         학습 실험 코드 (제출하지 않고 눈으로 확인하는 코드)
  common/      ListNode, TreeNode 등 공용 자료구조
  _template/   문제 풀이 템플릿
tools/         glue code (스켈레톤 생성기 등)
notes/         공부 기록. daily/ 일일 노트(TEMPLATE.md 참고), concepts.md 개념, english/ 영어, modules/ 학습 모듈
```

## 문제 추가 방법

```sh
python3 tools/skeleton.py <slug 또는 문제 URL>
```

LeetCode에서 시그니처와 예제를 받아 `src/problems/inbox/`에 6단계 템플릿 파일을 만든다.
수동으로 만들 때는 `src/_template/P0000Template.java`를 복사한다.
클래스명은 `P<문제번호 4자리><문제명>` — 예: `P0001TwoSum`.

터미널에서 실행하려면:

```sh
javac -d out src/common/*.java src/problems/array/P0001TwoSum.java
java -cp out problems.array.P0001TwoSum
```

## 기록

풀고 나면 `notes/daily/오늘날짜.md`에 문제·결과·배운 것·복습 목록을 기록한다.
