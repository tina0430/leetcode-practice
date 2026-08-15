package problems.twopointers;

/**
 * 125. Valid Palindrome
 * https://leetcode.com/problems/valid-palindrome/
 * 난이도: Easy | 유형: 투 포인터 (양끝에서 좁히기), 문자열 정규화
 *
 * 기록:
 * - 첫 풀이: 2026-08-14
 *
 * 접근:
 * 1. 입출력 정의: 문자열 s / 공백을 제거 + 싹다 소문자로 변환한 문자열이 palindrome 인지 여부 반환
 * 2. 제약 조건:
 *      1 <= s.length <= 2 * 10^5
 *      s consists only of printable ASCII characters.
 * 3. 예제 손으로 + 함정 찾기:
 *    - 지문에 안 적힌 것 (2개 뽑기. "내가 뭘 했나"가 아니라 "지문이 뭘 안 정했나"):
 *      (1) 영숫자가 하나도 없으면 printable인가?
 *      (2)
 * 4. 브루트포스 + 복잡도:
 *      전체 순회하면서 공백 없애고 비교하려나 O(n)
 * 5. 병목 찾기 → 도구 선택:
 *      왼/오 점점 좁혀와햐지! (도구릴께 있나..?)
 * 6. 검증 (예제 + 엣지 케이스):
 *      공백도 문자열도 아닌것이 나올 수 있음..
 *
 * 시간복잡도: O(n) / 공간복잡도: O(1)
 */
public class P0125ValidPalindrome {

    public boolean isPalindrome(String s) {
        int start = 0;
        int end = s.length() - 1;
        while(start < end){
            char left = s.charAt(start);
            if ('A' <= left && left <= 'Z') {
                left = (char) (left + 32);
            } else if (!(('a' <= left && left <= 'z') || ('0'  <= left && left <= '9'))) {
                start = start + 1;
                continue;
            }
            char right = s.charAt(end);
            if ('A' <= right && right <= 'Z') {
                right = (char) (right + 32);
            } else if (!(('a' <= right && right <= 'z') || ('0'  <= right && right <= '9'))) {
                end = end - 1;
                continue;
            }
            if (left != right) {
                return false;
            } else {
                start++;
                end--;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        P0125ValidPalindrome sol = new P0125ValidPalindrome();

        System.out.println(sol.isPalindrome("A man, a plan, a canal: Panama"));  // true
        System.out.println(sol.isPalindrome("race a car"));                      // false
        System.out.println(sol.isPalindrome(" "));                               // true

        // 엣지 케이스는 직접 추가할 것
        System.out.println(sol.isPalindrome("0P"));                               // false
        System.out.println(sol.isPalindrome("P0"));                               // false

    }
}
