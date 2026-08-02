package hashmap;

/**
 * 242. Valid Anagram
 * https://leetcode.com/problems/valid-anagram/
 * 난이도: Easy | 유형: String, Hash Map (카운팅)
 *
 * 접근:
 * 1. 입출력 정의: string s,t > t가 s의 anagram 이면 true 아님 false / anagram 이 뭐지? 순서만 바꾼 문자열인가?
 * 2. 제약 조건: s,t 각각 50k 길이, 알파벳 소문자로 이루어져있음 > 26칸만 쓰면 된다는 뜻
 * 3. 예제 손으로 + 함정 찾기: 일단 t에서 각 알파벳 별 갯수 세고, s 에서 나온 알파벳이 t 맵에 없으면 바로 false, 그외 경우 일단 끝까지 가긴 하고 갯수 비교해야 할듯
 * 4. 브루트포스 + 복잡도: 브루트포스로 어떻게 풀어야 할지도 모르겠음. 일단 O(n)으로 풀 수 있을 듯? (s, t 각각 한번씩 순회)
 * 5. 병목 찾기 → 도구 선택: Map t로 증가시키고 s로 감소시키자
 * 6. 검증 (예제 + 엣지 케이스): 두 문자열의 길이가 다르면 일단 짤라.
 *
 * 시간복잡도: O(n) / 공간복잡도: O(1) > 알파벳은 26개
 */
public class P0242ValidAnagram {

    public boolean isAnagram(String s, String t) {
        int[] count = new  int[26];
        if (s.length() != t.length()) {
            return false;
        }
        for (int i = 0; i < t.length(); i++) {
            count[t.charAt(i) - 'a']++;
            count[s.charAt(i) - 'a']--;
        }
        for (int i = 0; i < 26; i++) {
            if (count[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        P0242ValidAnagram sol = new P0242ValidAnagram();
        System.out.println(sol.isAnagram("anagram", "nagaram")); // 기대값: true
        System.out.println(sol.isAnagram("rat", "car"));         // 기대값: false
        System.out.println(sol.isAnagram("a", "ab"));            // 기대값: false
        System.out.println(sol.isAnagram("aacc", "ccac"));       // 기대값: false
    }
}
