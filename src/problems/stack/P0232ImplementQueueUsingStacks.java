package problems.stack;

import java.util.Stack;

/**
 * 232. Implement Queue using Stacks
 * https://leetcode.com/problems/implement-queue-using-stacks/
 * 난이도: Easy | 유형: -
 *
 * 기록:
 * - 첫 풀이: 2026-08-17
 *
 * 접근:
 * 1. 입출력 정의:
 *      만들 것: MyQueue (FIFO).
 *      push(x): 뒤에 넣는다, 반환 없음
 *      pop(): 가장 오래된 것을 빼서 반환
 *      peek(): 가장 오래된 것을 반환(제거 안 함)
 *      empty(): boolean
 *      쓸 수 있는 것: 스택의 표준 연산만 (push to top, peek/pop from top, size, isEmpty)
 * 2. 제약 조건:
 *      push to top, peek/pop from top, size, and is empty operations are valid.
 *      1 <= x <= 9
 *      At most 100 calls will be made to push, pop, peek, and empty.
 *      All the calls to pop and peek are valid.
 *      follow-up
 *          (a) Can you implement the queue such that each operation is amortized O(1) time complexity?
 *          (b) In other words, performing n operations will take overall O(n) time even if one of those operations may take longer.
 * 3. 예제 손으로 + 함정 찾기:
 *      out이 비어있을 때 push/peek/pop
 *      out이 채워져 있을 때 push/peek/pop
 *      in 만 채워져 있을 때 empty
 *      out 만 채워져 있을 때 empty
 *      in/out 둘 다 채워져 있을 때 empty
 * 4. 브루트포스 + 복잡도:
 *      stack 두개를 들고 매번 뒤집어가면서 동작 > O(n^2)
 * 5. 병목 찾기 → 도구 선택:
 *      매번 뒤집으면 pop마다 O(n) 이동이 병목 → out이 빌 때만 옮기는 지연 이동
 *      각 원소는 in에 push 1회, out으로 이동 1회, pop 1회로 총 3회, n번 연산에 3n → amortized O(1)
 * 6. 검증 (예제 + 엣지 케이스):
 *
 * 시간복잡도: push & empty: O(1) / pop & peek: amortized O(1)
 * 공간복잡도: O(n)
 */
public class P0232ImplementQueueUsingStacks {

static class MyQueue {
    private Stack<Integer> in;
    private Stack<Integer> out;

    public MyQueue() {
        in = new Stack<>();
        out = new Stack<>();
    }
    public void push(int x) {
        in.push(x);
    }
    public int pop() {
        refillOutIfEmpty();
        return out.pop();
    }
    public int peek() {
        refillOutIfEmpty();
        return out.peek();
    }
    private void refillOutIfEmpty() {
        if (out.isEmpty()) {
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
        }
    }
    public boolean empty() {
        return in.isEmpty() && out.isEmpty();
    }
}
/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */

    public static void main(String[] args) {
        P0232ImplementQueueUsingStacks s = new P0232ImplementQueueUsingStacks();
        // 공식 예제 입력 (한 문제당 여러 줄. 호출 형태로 바꿔서 기대값 주석과 함께 쓸 것):
        // ["MyQueue","push","push","peek","pop","empty"]
        // [[],[1],[2],[],[],[]]
        {
            MyQueue queue = new MyQueue();
            queue.push(1);
            queue.push(2);
            System.out.println(queue.empty());      // false
            System.out.println(queue.peek());       // 1
            System.out.println(queue.pop());        // 1
            System.out.println(queue.empty());      // false

            //
            queue.push(3);
            queue.push(4);
            System.out.println(queue.empty());      // false
            System.out.println(queue.pop());        // 2
            System.out.println(queue.pop());        // 3
            System.out.println(queue.pop());        // 4
            System.out.println(queue.empty());      // true

        }
    }
}
