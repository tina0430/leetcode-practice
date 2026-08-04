package problems.hashmap;

import java.util.HashMap;
import java.util.Map;

/**
 * 219. Contains Duplicate II
 * https://leetcode.com/problems/contains-duplicate-ii/
 * 난이도: Easy | 유형: Array, Hash Map
 *
 * 기록:
 * - 첫 풀이: 2026-08-02
 *
 * 접근:
 * 1. 입출력 정의: int[] nums, int k(기준 인덱스의 최대 갭), / i != j, abs(i - j) <= k 이면서 nums[i] == nums[j]가 존재하는 경우 true 반환
 * 2. 제약 조건: 1 <= nums.length <= 10^5 / -10^9 <= nums[i] <= 10^9 (음양 10억 확인) / 0 <= k <= 10^5
 * 3. 예제 손으로 + 함정 찾기: 주어진 예제를 보니 num들 중복 숫자 허용됨. 하지만 하나라도 발견시 true 반환 하면 됨
 * 4. 브루트포스 + 복잡도: nums.length 돌면서 k 씩 순회하기 O(n*k) -> 10^5 * 10^5 = 10^10 -> 터진다
 * 5. 병목 찾기 → 도구 선택:
 *   - i 에서 k 만큼 검사 했다는 것은 즉, i + 1 에서는 한개만 더 검사하면 되는거 아님?
 *   - 아니다 num 별로 idx 저장해놓고 현재 idx 랑 k 차이 나는지 찾으면 되겠군
 * 6. 검증 (예제 + 엣지 케이스): 엣지 케이스가 떠오르지 않는다. 예외를 떠올리는 연습이 필요할듯
 *
 * 시간복잡도: O(n) / 공간복잡도: O(?)
 */
public class P0219ContainsDuplicateII {

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> idxes = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer prevIdx = idxes.put(nums[i], i); // 문제 풀다가 정의부 잠깐 컨닝함
            if (prevIdx != null && i - prevIdx <= k) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        P0219ContainsDuplicateII sol = new P0219ContainsDuplicateII();
        System.out.println(sol.containsNearbyDuplicate(new int[]{1, 2, 3, 1}, 3));       // 기대값: true
        System.out.println(sol.containsNearbyDuplicate(new int[]{1, 0, 1, 1}, 1));       // 기대값: true
        System.out.println(sol.containsNearbyDuplicate(new int[]{1, 2, 3, 1, 2, 3}, 2)); // 기대값: false
    }
}
