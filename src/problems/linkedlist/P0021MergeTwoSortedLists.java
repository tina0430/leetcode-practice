package problems.linkedlist;

import common.ListNode;

// 로컬 테스트 헬퍼: ListNode.of(...)로 만들고, toString()이 [1, 2, 3] 형태로 찍는다.
// 리트코드 제출 시: import를 지우고 class Solution 안의 메서드만 붙여넣는다
// (채점기가 자기 ListNode/TreeNode를 이미 갖고 있어서, 같은 이름을 또 정의하면 타입 충돌).

/**
 * 21. Merge Two Sorted Lists
 * https://leetcode.com/problems/merge-two-sorted-lists/
 * 난이도: Easy | 유형: Linked List, 병합
 *
 * 기록:
 * - 첫 풀이: 2026-08-05
 *
 * 접근:
 * 1. 입출력 정의: 정렬된 링크드리스트 헤더 둘 list1, list2 / 둘을 다시 sorted list로 merge 해서 반환
 * 2. 제약 조건: 노드의 갯수는 0 ~ 50 / -100 <= Node.val <= 100 / 두 리스트 모두 오름차순
 * 3. 예제 손으로 + 함정 찾기: 노드의 갯수가 0일 수 있음
 * 4. 브루트포스 + 복잡도: list 둘을 연결하고 다시 정렬하나? > O(n log n)
 * 5. 병목 찾기 → 도구 선택:
 *      어차피 정렬된 리스트이므로 list1, 2를 따라가면서 값 비교해가며 줄줄이 이어붙이자
 *      시작점은 제일 작은걸로 고정하고, 붙일 자리를 옮겨다니자.
 *      큰 값이 나오면 작은 값 뒤에 붙이고, 작거나 같은 값이 나오면 양보함.
 *      빈 리스트 처리해야함.
 *      ListNode 사용하라고 문제에서 명시해줌
 * 6. 검증 (예제 + 엣지 케이스):
 *      l1이 l2보다 짧은 경우
 *      l2가 l1보다 짧은 경우
 *      l1, l2 가 각각 길이가 1인 경우
 *      l1, l2 둘 중 하나라도 길이가 0인 경우
 *      l1, l2 가 같은 숫자를 가지는 경우
 *
 * 시간복잡도: O(n+m) / 공간복잡도: O(1)
 */
public class P0021MergeTwoSortedLists {

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null && list2 == null) {
            return null;
        } else if (list1 == null) {
            return list2;
        } else if (list2 == null) {
            return list1;
        }
        ListNode head;
        if (list1.val <= list2.val) {
            head = list1;
            list1 = list1.next;
            if (list1 == null) {
                head.next = list2;
            }
        } else {
            head = list2;
            list2 = list2.next;
            if (list2 == null) {
                head.next = list1;
            }
        }
        ListNode tail = head;
        while (list1 != null && list2 != null) {
            int val1 = list1.val;
            int val2 = list2.val;
            if (val1 < val2) {
                ListNode next = list1.next;
                list1.next = list2;
                tail.next = list1;
                list1 = next;
            } else {
                ListNode temp = list2.next;
                list2.next = list1;
                tail.next = list2;
                list2 = temp;
            }
            tail = tail.next;
        }
        return head;
    }

    public static void main(String[] args) {
        P0021MergeTwoSortedLists s = new P0021MergeTwoSortedLists();

        // [1,2,4] + [1,3,4] -> 기대: [1, 1, 2, 3, 4, 4]
        System.out.println(String.valueOf(s.mergeTwoLists(ListNode.of(1, 2, 4), ListNode.of(1, 3, 4))));
        // [] + [] -> 기대: null
        System.out.println(String.valueOf(s.mergeTwoLists(ListNode.of(), ListNode.of())));
        // [] + [0] -> 기대: [0]
        System.out.println(String.valueOf(s.mergeTwoLists(ListNode.of(), ListNode.of(0))));
        // l1이 더 짧은 경우 -> 기대: [1, 1, 2, 3, 4, 5, 6]
        System.out.println(String.valueOf(s.mergeTwoLists(ListNode.of(1, 2, 4), ListNode.of(1, 3, 5, 6))));
        System.out.println(String.valueOf(s.mergeTwoLists(ListNode.of(12), ListNode.of(1, 2, 3, 4, 5))));
        System.out.println(String.valueOf(s.mergeTwoLists(ListNode.of(1), ListNode.of(2, 3, 5, 6, 11))));
        System.out.println(String.valueOf(s.mergeTwoLists(ListNode.of(2, 4, 7), ListNode.of(1, 3, 5, 6, 11))));
        // 길이가 둘다 1인 경우 -> [2] + [1] -> 기대: [1, 2]
        System.out.println(String.valueOf(s.mergeTwoLists(ListNode.of(2), ListNode.of(1))));
    }
}
