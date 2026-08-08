package problems.linkedlist;

import common.ListNode;

// 로컬 테스트 헬퍼: ListNode.of(...)로 만들고, toString()이 [1, 2, 3] 형태로 찍는다.
// 리트코드 제출 시: import를 지우고 class Solution 안의 메서드만 붙여넣는다
// (채점기가 자기 ListNode/TreeNode를 이미 갖고 있어서, 같은 이름을 또 정의하면 타입 충돌).

/**
 * 21. Merge Two Sorted Lists (복습 2회차)
 * https://leetcode.com/problems/merge-two-sorted-lists/
 * 난이도: Easy | 유형: Linked List, 병합 (복습)
 *
 * 기록:
 * - 첫 풀이: 2026-08-05 (44:14, 머리 노드를 따로 처리하느라 루프 앞에 중복 블록)
 * - 복습 1회차: 2026-08-05 (31:08, 더미 노드 미적용. 루프 앞 11줄이 그대로 남음)
 * - 복습 2회차: 2026-08-07 (더미 노드로 재작성. 목표 7분)
 *
 * 이번 재작성의 통과 조건 (셋 다 만족해야 완료):
 * - 루프가 시작되기 전에 특수 케이스 분기가 0개다
 * - `return dummy.next;`로 끝난다
 * - 한쪽 리스트가 먼저 소진되는 처리가 반복문 뒤 한 줄이다
 *
 * Approach:
 * 1. Input/Output: sorted linked lists list1 and list2 / Return the head of the merged linked list.
 * 2. Constraints: 0 <= the number if nodes <= 50 / -100 <= Node.val <= 100 / Both list1 and list2 are sorted in non-decreasing order.
 * 3. Examples & edge cases:
 *      list is empty
 *      each lists have different size
 *      is it okay if I change the input..? (I moved the head of the lists)
 * 4. Brute force + complexity:
 *      merge two list and sort it > O( n log n..?)
 * 5. Bottleneck -> tool:
 *      both lists have maximum sizd with same numbers
 * 6. Verification:
 *      same length
 *      different length
 *      empty list
 *      length = 1
 *
 * Time: O(n) / Space: O(1)
 */
public class P0021MergeTwoSortedListsR2 {

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode();
        ListNode tail = dummy;
        while (list1 != null && list2 != null) {
            int val1 = list1.val;
            int val2 = list2.val;
            if (val1 < val2) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }
            tail = tail.next;
        }
        tail.next = (list1 != null) ? list1 : list2;
        return dummy.next;
    }

    public static void main(String[] args) {
        P0021MergeTwoSortedListsR2 s = new P0021MergeTwoSortedListsR2();

        System.out.println(s.mergeTwoLists(ListNode.of(1, 2, 4), ListNode.of(1, 3, 4)));      // [1, 1, 2, 3, 4, 4]
        System.out.println(s.mergeTwoLists(ListNode.of(), ListNode.of()));                    // null
        System.out.println(s.mergeTwoLists(ListNode.of(), ListNode.of(0)));                   // [0]
        System.out.println(s.mergeTwoLists(ListNode.of(4, 5, 6), ListNode.of(1, 2, 3)));      // [1, 2, 3, 4, 5, 6]
        System.out.println(s.mergeTwoLists(ListNode.of(-100, 100), ListNode.of(-100, 0, 100)));  // [-100, -100, 0, 100, 100]
        System.out.println(s.mergeTwoLists(ListNode.of(2), ListNode.of(1)));                  // [1, 2]
    }
}
