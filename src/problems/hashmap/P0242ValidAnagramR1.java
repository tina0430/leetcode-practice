package problems.hashmap;

/**
 * 242. Valid Anagram (복습 1회차)
 * https://leetcode.com/problems/valid-anagram/
 * 난이도: Easy | 유형: String, 카운팅
 *
 * 기록:
 * - 첫 풀이: 2026-08-01 (HashMap 카운팅 → int[26]로 최적화)
 * - 복습 1회차: 2026-08-02 (int[26] 버전을 안 보고, 주석은 영어로)
 *
 * Approach:
 * 1. Input/Output: strings s, t / if t is anagram of s
 * 2. Constraints:
 *   - 1 <= s.length, t.length <= 5 * 10^4, O(n^2) = 25 * 10^8 which is too tight
 *   - s, t lowercase alphabet
 * 3. Examples & edge cases: both s and t's length = 5 * 10^4 they are reverse?
 * 4. Brute force + complexity: sort s, t and compare each elements. nlong + nlogn + n -> O(nlogn)
 * 5. Bottleneck -> tool: int[] bc. targeted 대상 consist of only lowercase alphabet
 * 6. Verification: anagram, non anagram with same length, non anagram with diffrent length
 *
 * Time: O(n) / Space: O(1)
 */
public class P0242ValidAnagramR1 {

    public boolean isAnagram(String s, String t) {
        // TODO: solve from memory (int[26] version, no peeking at the original)
        if (s.length() != t.length()) return false;
        int[] counts = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counts[s.charAt(i) - 'a']++;
            counts[t.charAt(i) - 'a']--;
        }
        for (int i = 0; i < 26; i++) {
            if (counts[i] != 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        P0242ValidAnagramR1 sol = new P0242ValidAnagramR1();
        System.out.println(sol.isAnagram("anagram", "nagaram")); // 기대값: true
        System.out.println(sol.isAnagram("rat", "car"));         // 기대값: false
        System.out.println(sol.isAnagram("a", "ab"));            // 기대값: false (길이 다름)
        System.out.println(sol.isAnagram("aacc", "ccac"));       // 기대값: false (개수 함정)
    }
}
