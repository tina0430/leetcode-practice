package problems.stack;

// 이 문제는 "클래스를 설계하는" 유형이다. 알고리즘 한 개를 짜는 게 아니라 자료구조를 만든다.
// 리트코드 제출 시: 바깥 클래스(P0155MinStack)는 빼고 아래 `static class MinStack`의
// `static`만 지운 뒤 `class MinStack { ... }` 부분만 붙여넣는다.

/**
 * 155. Min Stack
 * https://leetcode.com/problems/min-stack/
 * 난이도: Medium | 유형: 스택 설계, 보조 스택으로 최솟값 동행 저장
 *
 * 기록:
 * - 첫 풀이: 2026-08-13
 *
 * 접근:
 * 1. 입출력 정의: stack 설계
 * 2. 제약 조건:
 *      -2^31 <= val <= 2^31 - 1 // int 의 범위다!
 *      pop, top and getMin 메소드는 뭔가가 있을 때만 호츌 될 예정.
 *      최대 3 * 10^4 회 호출 될꺼임 (push, pop, top, and getMin) // 스택 최대 크기?
 *      각 함수의 시간 복잡도는 O(1) 이어야 함
 * 3. 예제 손으로 + 함정 찾기:
 *    - 지문에 안 적힌 것 (2개 뽑기. "내가 뭘 했나"가 아니라 "지문이 뭘 안 정했나"):
 *      (1)
 *      (2)
 * 4. 브루트포스 + 복잡도: ..?
 * 5. 병목 찾기 → 도구 선택:
 * 6. 검증 (예제 + 엣지 케이스):
 *
 * 시간복잡도: 연산별로 적을 것 (push / pop / top / getMin) 싹다 O(1)
 * 공간복잡도: O(n)
 */
public class P0155MinStack {

    static class MinStack {

        int[] values;
        int cur;
        int[] mins;

        public MinStack() {
            values = new int[30001];
            mins = new int[30001];
            mins[0] = Integer.MAX_VALUE;
        }

        public void push(int val) {
            values[++cur] = val;
            if (mins[cur - 1] > val) {
                mins[cur] = val;
            } else {
                mins[cur] = mins[cur - 1];
            }
        }

        public void pop() {
            values[cur--] = 0;
            mins[cur + 1] = 0;
        }

        public int top() {
            return values[cur];
        }

        public int getMin() {
            return mins[cur];
        }
    }

    public static void main(String[] args) {
        {
            // 공식 예제:
            // ["MinStack","push","push","push","getMin","pop","top","getMin"]
            // [[],[-2],[0],[-3],[],[],[],[]]
            MinStack st = new MinStack();
            st.push(-2);
            st.push(0);
            st.push(-3);
            System.out.println(st.getMin());  // -3
            st.pop();
            System.out.println(st.top());     // 0
            System.out.println(st.getMin());  // -2
        }
        {
            MinStack st = new MinStack();
            st.push(2);
            st.push(-2);
            st.push(1);
            System.out.println(st.top());     // 1
            System.out.println(st.getMin());  // -2
            st.pop();
            System.out.println(st.top());     // -2
            System.out.println(st.getMin());  // -2
            st.pop();
            System.out.println(st.getMin());  // 2
        }

        // 엣지 케이스는 직접 추가할 것
    }
}
