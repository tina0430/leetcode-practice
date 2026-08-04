package problems.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 49. Group Anagrams
 * https://leetcode.com/problems/group-anagrams/
 * 난이도: Medium | 유형: String, Hash Map (그룹핑)
 *
 * 접근:
 * 1. 입출력 정의: practice.string 배열 strs > anagrams 끼리 묶어서 리스트로 반환
 * 2. 제약 조건:
 *   - strs 는 최대 10K, 각 문자열은 최대 100, 알파벳 소문자로 구성됨
 *   - 문자열을 각각 정리하면 (100 * 10K) 1000K > 2^8 이랑 같은 단위, 1초 아슬아슬하네?
 * 3. 예제 손으로 + 함정 찾기: eet 이런것도 잘 구분하자..  모든 문자열의 길이가 동일하지 않을 수 있다.
 * 4. 브루트포스 + 복잡도: 아놔 망할 브루트포스..
 * 5. 병목 찾기 → 도구 선택:
 * 6. 검증 (예제 + 엣지 케이스): 다 100인데 싹다 다른 케이스도 통과해야 한다.
 *
 * 시간복잡도: O(?) / 공간복잡도: O(?)
 */
public class P0049GroupAnagrams {

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            int[] arr = new int[26];
            for (Character ch : strs[i].toCharArray()) {
                arr[ch - 'a']++;
            }
            char[] keyChars = new char[26];
            for (int j = 0; j < 26; j++) {
                if (arr[j] > 0) {
                    keyChars[j] = (char) arr[j];
                }
            }
            String key = new String(keyChars);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(strs[i]);
        }
        return map.values().stream().toList();
    }

    public static void main(String[] args) {
        P0049GroupAnagrams sol = new P0049GroupAnagrams();
        // 그룹 순서/그룹 안 순서는 달라도 정답 (LeetCode도 순서 무관 채점)
        System.out.println(sol.groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}));
        // 기대값: [[bat], [nat, tan], [ate, eat, tea]] 같은 3그룹
        System.out.println(sol.groupAnagrams(new String[]{""}));  // 기대값: [[]] 안의 빈 문자열 1그룹 → [[""]]
        System.out.println(sol.groupAnagrams(new String[]{"a"})); // 기대값: [[a]]


        // 길이가 다른 녀석 (함점)
        System.out.println(sol.groupAnagrams(new String[]{"eat", "teaa", "tans", "ate", "nat", "bat"}));
    }
}
