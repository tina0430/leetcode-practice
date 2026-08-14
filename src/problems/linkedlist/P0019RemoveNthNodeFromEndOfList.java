package problems.linkedlist;

import common.ListNode;

// 로컬 테스트 헬퍼: ListNode.of(...)로 만들고, toString()이 [1, 2, 3] 형태로 찍는다.
// 리트코드 제출 시: import를 지우고 class Solution 안의 메서드만 붙여넣는다
// (채점기가 자기 ListNode/TreeNode를 이미 갖고 있어서, 같은 이름을 또 정의하면 타입 충돌).

/**
 * 19. Remove Nth Node From End of List
 * https://leetcode.com/problems/remove-nth-node-from-end-of-list/
 * 난이도: Medium | 유형: 링크드 리스트, 노드 배열 복사 + 끝 센티넬 (follow-up 미해결: 순회 1번 + 공간 O(1))
 *
 * 기록:
 * - 첫 풀이: 2026-08-07
 *
 * 접근:
 * 1. 입출력 정의: head of a linked list, n / 뒤에서 n번째 노드를 삭제한뒤 head 반환
 * 2. 제약 조건:
 *      1 <= 노드 개수 <= 30 / 0 <= Node.val <= 100 / 1 <= n <= 노드 개수
 *      노드는 무조건 존재 , n이 너무 큰 경우는 없다.
 *      수가 엄청 작은 이유가 따로 있을까? > 복잡도로 변별하는게 아니라 포인터 조작의 정확성과 엣지 케이스를 떠올릴 수 있는지 여부를 테스트 하는 것
 *      Follow up: Could you do this in one pass?
 * 3. 예제 손으로 + 함정 찾기:
 *    - 지문에 안 적힌 것 (2개 뽑기):
 *      (1) 원본 리스트를 변경해야 하는가?
 *      (2) 리스트 두 번 순회가 시간상 가능은 한데, 더 최적화해야 하는지?
 * 4. 브루트포스 + 복잡도: 끝까지 세어 길이 L을 구한 뒤, 앞에서 다시 (L-n-1)번째까지 순회 2번 > O(2n) > O(n)
 * 5. 병목 찾기 → 도구 선택:
 *      코드를 짜다보니 체크 순서도 중요했다.
 *      맨 끝값 처리를 먼저 했더니 길이가 1인 경우를 커버하지 못해서 첫 노드 삭제를 먼저 처리했다.
 * 6. 검증 (예제 + 엣지 케이스):
 *      첫 노드 삭제
 *      마지막 노드 삭제
 *      중간 값 삭제
 *      길이가 1인 경우
 *
 * 시간복잡도: O(n) / 공간복잡도: O(1)
 */
public class P0019RemoveNthNodeFromEndOfList {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode cur = dummy;
        ListNode target = dummy;
        while (cur != null) {
            cur = cur.next;
            if (n < 0) {
                target = target.next;
            }
            n--;
        }
        target.next = target.next.next;
        return dummy.next;
    }

    public static void main(String[] args) {
        P0019RemoveNthNodeFromEndOfList s = new P0019RemoveNthNodeFromEndOfList();

//        System.out.println(s.removeNthFromEnd(ListNode.of(1, 2, 3, 4, 5), 2));  // [1, 2, 3, 5]
        System.out.println(s.removeNthFromEnd(ListNode.of(1), 1));              // []
        System.out.println(s.removeNthFromEnd(ListNode.of(1, 2), 1));           // [1]

        System.out.println(s.removeNthFromEnd(ListNode.of(1, 2, 3, 4, 5), 5));  // [2, 3, 4, 5]
        System.out.println(s.removeNthFromEnd(ListNode.of(1, 2, 3), 1));        // [1, 2]
    }
}
