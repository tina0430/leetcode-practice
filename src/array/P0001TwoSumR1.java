package array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 1. Two Sum (복습 1회차)
 * https://leetcode.com/problems/two-sum/
 * 난이도: Easy | 유형: Array, Hash Map (보수 찾기)
 *
 * 기록:
 * - 첫 풀이: 2026-08-01 (브루트포스 → HashMap two-pass → one-pass)
 * - 복습 1회차: 2026-08-02 (one-pass를 안 보고 다시)
 *
 * 접근:
 * 1. 입출력 정의: int[] nums / int target > int[2] indices of two nums which sum target
 * 2. 제약 조건: nums.length < 10^4 / -10^9 < num < 10^9 / -10^9 < target < 10^9
 * 3. 예제 손으로 + 함정 찾기: num can be 음수 / if nums.length if 10^4, O(n^2) is 10^8 which is too tight
 * 4. 브루트포스 + 복잡도: (n -1) + (n -2) + ... + 1 -> O(n^2)
 * 5. 병목 찾기 → 도구 선택: HashMap
 * 6. 검증 (예제 + 엣지 케이스): 음수 case
 *
 * 시간복잡도: O(n) / 공간복잡도: O(n)
 */
public class P0001TwoSumR1 {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> indices = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (indices.containsKey(target - nums[i])) {
                return new int[]{indices.get(target - nums[i]), i};
            }
            indices.put(nums[i], i);
        }
        return new int[]{};
    }

    public static void main(String[] args) {
        P0001TwoSumR1 s = new P0001TwoSumR1();
        System.out.println(Arrays.toString(s.twoSum(new int[]{2, 7, 11, 15}, 9)));  // 기대값: [0, 1]
        System.out.println(Arrays.toString(s.twoSum(new int[]{3, 2, 4}, 6)));       // 기대값: [1, 2]
        System.out.println(Arrays.toString(s.twoSum(new int[]{3, 3}, 6)));          // 기대값: [0, 1]
        System.out.println(Arrays.toString(s.twoSum(new int[]{-2, -3}, -5)));       // 기대값: [0, 1] (음수 함정)
    }
}
