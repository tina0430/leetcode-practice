# leetcode-practice

미국 취업 준비를 위한 LeetCode 풀이 저장소 (Java 21, IntelliJ).

## 구조

```
src/
  _template/   문제 풀이 템플릿 (복사해서 시작)
  common/      ListNode, TreeNode 등 공용 자료구조
  array/       유형별 패키지 (string, hashmap, twopointers,
  dp/          slidingwindow, stack, binarysearch, linkedlist,
  ...          tree, heap, backtracking, graph, greedy, intervals)
notes/         일일 공부 기록 (notes/YYYY-MM-DD.md, TEMPLATE.md 참고)
```

## 문제 추가 방법

1. `src/_template/P0000Template.java`를 해당 유형 패키지로 복사
2. 클래스명은 `P<문제번호 4자리><문제명>` — 예: `P0001TwoSum`
3. `main()`에 예제 테스트케이스를 넣고 IntelliJ에서 ▶ 실행으로 확인

터미널에서 실행하려면:

```sh
javac -d out src/common/*.java src/array/P0001TwoSum.java
java -cp out array.P0001TwoSum
```

## 기록

풀고 나면 `notes/오늘날짜.md`에 문제·결과·배운 것·복습 목록을 기록한다.
