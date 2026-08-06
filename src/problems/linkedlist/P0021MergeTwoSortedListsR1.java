package problems.linkedlist;

import common.ListNode;

/**
 * 21. Merge Two Sorted Lists (복습 1회차)
 * https://leetcode.com/problems/merge-two-sorted-lists/
 * 난이도: Easy | 유형: Linked List, 병합 (복습)
 *
 * 기록:
 * - 첫 풀이: 2026-08-05 (44:14, 머리 노드를 따로 처리하느라 루프 앞에 중복 블록)
 * - 복습 1회차: 2026-08-05 (더미 노드로 재작성. 원본 파일 보지 않기)
 *
 * Approach:
 * 1. Input/Output: sorted linked list1, 2 / return merged and sorted linked list
 * 2. Constraints: 0 <= list.size <= 50 / -100 <= Node.val <= 100 / non-decreasing order
 * 3. Examples & edge cases:
 *      empty list
 *      different length
 * 4. Brute force + complexity:
 *      merge two list and sort > O(n long n) (not sure)
 * 5. Bottleneck -> tool:
 *      순회하다 each list at the sime time, and if I find small or same, put the target list.
 * 6. Verification:
 *      l1.size is shorter than l2.size
 *      l2.size is shorter than l1.size
 *      both lists' size is 1
 *      one of the lists' size is 0
 *      l1 and l2 has same element
 *
 * Time: O(n+m) / Space: O(1)
 */
public class P0021MergeTwoSortedListsR1 {

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        ListNode head;
        if (list1.val < list2.val) {
            head = list1;
            list1 = list1.next;
        } else {
            head = list2;
            list2 = list2.next;
        }
        if (list1 == null) {
            head.next = list2;
        }
        if (list2 == null) {
            head.next = list1;
        }
        ListNode tail = head;
        while (list1 != null && list2 != null) {
            int val1 = list1.val;
            int val2 = list2.val;
            if (val1 < val2) {
                tail.next = list1;
                list1 = list1.next;
                if (list2 != null) {
                    tail.next.next = list2;
                }
            } else {
                tail.next = list2;
                list2 = list2.next;
                if (list1 != null) {
                    tail.next.next = list1;
                }
            }
            tail = tail.next;
        }
        return head;
    }

    public static void main(String[] args) {
        P0021MergeTwoSortedListsR1 s = new P0021MergeTwoSortedListsR1();

        // [1,2,4] + [1,3,4] -> expect: [1, 1, 2, 3, 4, 4]
        System.out.println(String.valueOf(s.mergeTwoLists(ListNode.of(1, 2, 4), ListNode.of(1, 3, 4))));
        // [] + [] -> expect: null
        System.out.println(String.valueOf(s.mergeTwoLists(ListNode.of(), ListNode.of())));
        // [] + [0] -> expect: [0]
        System.out.println(String.valueOf(s.mergeTwoLists(ListNode.of(), ListNode.of(0))));
        // one side runs out first -> expect: [1, 2, 3, 4, 5, 6]
        System.out.println(String.valueOf(s.mergeTwoLists(ListNode.of(4, 5, 6), ListNode.of(1, 2, 3))));
        // duplicates at the boundary -> expect: [-100, -100, 0, 100, 100]
        System.out.println(String.valueOf(s.mergeTwoLists(ListNode.of(-100, 100), ListNode.of(-100, 0, 100))));

        System.out.println(String.valueOf(s.mergeTwoLists(ListNode.of(2), ListNode.of(1))));
    }
}
