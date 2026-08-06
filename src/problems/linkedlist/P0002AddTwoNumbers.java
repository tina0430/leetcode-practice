package problems.linkedlist;

import common.ListNode;

// 로컬 테스트 헬퍼: ListNode.of(...)로 만들고, toString()이 [1, 2, 3] 형태로 찍는다.
// 리트코드 제출 시: import를 지우고 class Solution 안의 메서드만 붙여넣는다
// (채점기가 자기 ListNode/TreeNode를 이미 갖고 있어서, 같은 이름을 또 정의하면 타입 충돌).

/**
 * 2. Add Two Numbers
 * https://leetcode.com/problems/add-two-numbers/
 * 난이도: Medium | 유형: Linked List, 자릿수 덧셈
 *
 * 기록:
 * - 첫 풀이: 2026-08-05
 *
 * 접근:
 * 1. 입출력 정의: 정렬안된 양의 정수 링크드리스트 2개 / 각 리스트 뒤집어서 숫자 만들고 둘이 더한 값을 또 다시 뒤집어서 반환 (이거 해석 못해서 예제 보고 유추함ㅜ)
 * 2. 제약 조건: 1 < list size < 100 / 0 <= Node.val <= 9 (그래도 뭐라도 들어있음)
 * 3. 예제 손으로 + 함정 찾기:
 *      뒤집어 더하라고 했기 때문에 나온 순서대로 더하고 10이상이면 -10 해서 오른쪽으로 넘기면 됨
 * 4. 브루트포스 + 복잡도:
 *      각 리스트 순회하면서 숫자 완성하고 더해서 하나씩 쪼개서 리스트 만들겠지 뭐.. O(n)
 * 5. 병목 찾기 → 도구 선택:
 *      리스트 동시에 순회하면서 더하고 나중에 정리
 *      l1 와 l2의 길이가 다를 때 > l1을 베이스로 두고, l1이 더 짧은 경우 l2를 이어 붙이고 마무리
 * 6. 검증 (예제 + 엣지 케이스):
 *       길이 다른 경우 양방향
 *       마지막에서 자리 올림이 넘칠 때 ([9,9,9,9] + [9,9,9,9,9])
 *       0 + 0
 *
 * 시간복잡도: O(n+m) / 공간복잡도: O(1)
 */
public class P0002AddTwoNumbers {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = l1;
        while (l2 != null) {
            l1.val += l2.val;
            l2 = l2.next;
            if (l1.next == null) {
                if (l2 == null) break;
                l1.next = l2;
                break;
            }
            l1 = l1.next;
        }
        ListNode cur = head;
        while (cur != null) {
            if (cur.val >= 10) {
                if (cur.next == null) {
                    cur.next = new ListNode();
                }
                cur.next.val++;
                cur.val -= 10;
            }
            cur = cur.next;
        }
        return head;
    }

    public static void main(String[] args) {
        P0002AddTwoNumbers s = new P0002AddTwoNumbers();

        // [2,4,3] + [5,6,4] -> 기대: [7, 0, 8]   (342 + 465 = 807)
        System.out.println(String.valueOf(s.addTwoNumbers(ListNode.of(2, 4, 3), ListNode.of(5, 6, 4))));
        // [0] + [0] -> 기대: [0]
        System.out.println(String.valueOf(s.addTwoNumbers(ListNode.of(0), ListNode.of(0))));
        // [9,9,9,9,9,9,9] + [9,9,9,9] -> 기대: [8, 9, 9, 9, 0, 0, 0, 1]
        System.out.println(String.valueOf(s.addTwoNumbers(ListNode.of(9, 9, 9, 9, 9, 9, 9), ListNode.of(9, 9, 9, 9))));

        System.out.println(String.valueOf(s.addTwoNumbers(ListNode.of(9, 9, 9, 9), ListNode.of(9, 9, 9, 9, 9))));
    }
}
