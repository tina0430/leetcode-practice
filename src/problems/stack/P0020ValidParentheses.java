package problems.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 20. Valid Parentheses
 * https://leetcode.com/problems/valid-parentheses/
 * 난이도: Easy | 유형: 스택, 짝 맞추기 (기대값을 미리 push)
 *
 * 기록:
 * - 첫 풀이: 2026-08-13
 *
 * 접근:
 * 1. 입출력 정의: 문자열 s / 괄호 쌍이 올바르게 닫혔는지 여부 반환
 * 2. 제약 조건: 1 <= s.length <= 10^4 / s consists of parentheses only '()[]{}' -> 일단 뭐라도 들어오는 군
 * 3. 예제 손으로 + 함정 찾기:
 *    - 지문에 안 적힌 것 (2개 뽑기. "내가 뭘 했나"가 아니라 "지문이 뭘 안 정했나"):
 *      (1)
 *      (2)
 * 4. 브루트포스 + 복잡도: 문자열 순회하다가 닫히는
 * 5. 병목 찾기 → 도구 선택:
 *      최대 길이로 반은 싹다 열리고 반은 싹다 닫히는게 아마 제일 느릴듯?
 * 6. 검증 (예제 + 엣지 케이스):
 *      s 의 길이가 홀수인 경우는 바로 false
 *      닫히는 괄호가 나오면 바로 최근 괄호 확인하면 됨 -> stack 사용
 *      열리기 전에 닫힐 수 있음
 *
 * 시간복잡도: O(n) / 공간복잡도: O(n)
 */
public class P0020ValidParentheses {

    public boolean isValid(String s) {
        if (s.length() % 2 == 1) return false;
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(')');
            } else if (c == '[') {
                stack.push(']');
            } else  if (c == '{') {
                stack.push('}');
            } else if (stack.isEmpty()) {
                return false;
            } else {
                Character pop = stack.pop();
                if (c != pop) return false;
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        P0020ValidParentheses sol = new P0020ValidParentheses();

        System.out.println(sol.isValid("()"));      // true
        System.out.println(sol.isValid("()[]{}"));  // true
        System.out.println(sol.isValid("(]"));      // false
        System.out.println(sol.isValid("([])"));    // true
        System.out.println(sol.isValid("([)]"));    // false
        System.out.println(sol.isValid("}{"));      // false
        // 엣지 케이스는 직접 추가할 것
    }
}
