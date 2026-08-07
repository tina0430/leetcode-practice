package problems.linkedlist;

import common.ListNode;

// 로컬 테스트 헬퍼: ListNode.of(...)로 만들고, toString()이 [1, 2, 3] 형태로 찍는다.
// 리트코드 제출 시: import를 지우고 class Solution 안의 메서드만 붙여넣는다
// (채점기가 자기 ListNode/TreeNode를 이미 갖고 있어서, 같은 이름을 또 정의하면 타입 충돌).

/**
 * 141. Linked List Cycle
 * https://leetcode.com/problems/linked-list-cycle/
 * 난이도: Easy | 유형: 링크드 리스트 (사이클 판별)
 *
 * 기록:
 * - 첫 풀이: 2026-08-06
 *
 * 접근:
 * 1. 입출력 정의: 링크드 리스트 head / 사이클 있는지 찾을 것
 * 2. 제약 조건:
 *      0 <= head.size <= 10^4 / -10^5 <= Node.val <= 10^5 / pos is -1 or a valid index in the linked-list. (이게 뭐지?)
 *      Follow up: Can you solve it using O(1) (i.e. constant) memory? 이건 일단 풀고 생각해보자
 * 3. 예제 손으로 + 함정 찾기:
 *      next가 지나온애인지 여부를 알아내야하는군? 숫자만 똑같고 반복될 수 있는거 아닌가..?
 * 4. 브루트포스 + 복잡도:
 *      새로운 노드 방문할 때 마다 head에서부터 따라오면서 next node가 존재하는지 체크 : 1 + 2 + 3 + ... + (n - 1) = O(n^2)
 * 5. 병목 찾기 → 도구 선택:
 *      각 노드는 어차피 각자 다른 객체니까 set으로 비교하면 되지 않을까? 그럼 굳이 값이 겹치던 말던 상관 없음
 *      근데...... follow up에 O(1) 뭐냐.. 지나온 애들 표시를 해볼까? Node.val 의 범위가 정해져 있으니..
 *          >> 클로드한테 혼났다. 입력값을 맘대로 건드는 것은 실무에서 일어나서는 안되는 일일 수 있음.. 면접시 물어보고 진행 할 것
 * 6. 검증 (예제 + 엣지 케이스):
 *      길이가 0인 경우 false
 *      길이가 1인데 자기자신을 가리키지 않는 경우 false
 *      길이가 1인데 자기자신을 가리키는 경우 true
 *      싸이클이 있는 케이스 true
 *      싸이클이 없는 케이스 false
 *
 * 시간복잡도: O(n) / 공간복잡도: O(1)
 */
public class P0141LinkedListCycle {

    public boolean hasCycle(ListNode head) {
        ListNode cur = head;
        while (cur != null) {
            if (cur.val == 100001) {
                return true;
            }
            cur.val = 100001;
            cur = cur.next;
        }
        return false;
    }

    public static void main(String[] args) {
        P0141LinkedListCycle s = new P0141LinkedListCycle();

        System.out.println(s.hasCycle(build(1, 3, 2, 0, -4)));  // true
        System.out.println(s.hasCycle(build(0, 1, 2)));         // true
        System.out.println(s.hasCycle(build(-1, 1)));           // false
        System.out.println(s.hasCycle(build(0, 1)));            // true  (자기 자신을 가리킴)
        System.out.println(s.hasCycle(build(-1, 1, 2)));        // false
        System.out.println(s.hasCycle(build(-1, 1, 2, 1, 2)));  // flase
        System.out.println(s.hasCycle(null));                        // false (노드 0개)
    }

    /**
     * 테스트용 리스트 생성. pos = 꼬리가 이어붙을 인덱스, -1이면 사이클 없음.
     * 주의: 사이클이 있는 리스트에 toString()이나 println(list)을 쓰면 무한 루프에 빠진다.
     */
    private static ListNode build(int pos, int... vals) {
        ListNode head = ListNode.of(vals);
        if (pos < 0 || head == null) return head;
        ListNode tail = head;
        while (tail.next != null) tail = tail.next;
        ListNode target = head;
        for (int i = 0; i < pos; i++) target = target.next;
        tail.next = target;
        return head;
    }
}
