package problems.linkedlist;

import common.ListNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 206. Reverse Linked List
 * https://leetcode.com/problems/reverse-linked-list/
 * 난이도: Easy | 유형: Linked List, 제자리 뒤집기
 *
 * 기록:
 * - 첫 풀이: 2026-08-05
 *
 * 접근:
 * 1. 입출력 정의: ListNode head / 거꾸로 뒤집은 ListNode 반환
 * 2. 제약 조건: 0< nodes.length < 5000 / -5000 <= Node.val <= 5000
 * 3. 예제 손으로 + 함정 찾기:
 *      주어진 ListNode는 한방향으로만 흐른다 일단 싹 돌면서 int[]에 넣고 다시 하나씩 넣어주는 수밖에..?
 *      가 아니라 결국 나 > next 가 한칸씩 밀려서 next를 물면 되잖아?
 *      처음엔 필드 접근이 안 되는 줄 알고 값을 복사해 새 리스트를 만들었다(공간 O(n))
 *      실제로는 package-private라 같은 패키지에서 대입 가능했고, 링크만 다시 걸면 O(1)
 * 4. 브루트포스 + 복잡도:
 *      ListNode 하나씩 순회하면서 값 찾고 뒤집어서 다시 넣어줌..? 왜냐 ListNode 필드가 public이 아님 (기본이 뭐더라)
 * 5. 병목 찾기 → 도구 선택:
 *      도구가 뭐 별거 있나 주어진 ListNode지..
 * 6. 검증 (예제 + 엣지 케이스):
 *      head가 null인 경우
 *      head가 null이 아닌 경우
 *
 * 시간복잡도: O(n) / 공간복잡도: O(1)
 */
public class P0206ReverseLinkedList {

    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode prev = null;
        ListNode cur = head.next;
        while (cur != null) {
            head.next = prev;
            prev = head;
            head = cur;
            cur = cur.next;
        }
        head.next = prev;
        return head;
    }

    public static void main(String[] args) {
        P0206ReverseLinkedList s = new P0206ReverseLinkedList();

        // [1,2,3,4,5] -> 기대: [5, 4, 3, 2, 1]
        System.out.println(String.valueOf(s.reverseList(ListNode.of(1, 2, 3, 4, 5)).toString()));
        // [1,2] -> 기대: [2, 1]
        System.out.println(String.valueOf(s.reverseList(ListNode.of(1, 2))));
        // [] -> 기대: null
        System.out.println(String.valueOf(s.reverseList(ListNode.of())));
    }
}
