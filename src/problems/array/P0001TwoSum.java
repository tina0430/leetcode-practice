package problems.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 1. Two Sum
 * https://leetcode.com/problems/two-sum/
 * 난이도: Easy | 유형: Array, Hash Map
 *
 * 접근:
 * 1. 입출력 정의: 숫자 배열(nums), 두 숫자의 합(target)
 * 2.
 *
 * 시간복잡도: O(n) / 공간복잡도: O(n)
 */
public class P0001TwoSum {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> idxs = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (idxs.containsKey(target - nums[i])) {
                return new int[]{idxs.get(target - nums[i]), i};
            } else {
                idxs.put(nums[i], i);
            }
        }
        return new int[]{};
    };

    public static void main(String[] args) {
        P0001TwoSum s = new P0001TwoSum();
        System.out.println(Arrays.toString(s.twoSum(new int[]{2, 7, 11, 15}, 9)));    // 기대값: [0, 1]
        System.out.println(Arrays.toString(s.twoSum(new int[]{3, 2, 4}, 6)));         // 기대값: [1, 2]
        System.out.println(Arrays.toString(s.twoSum(new int[]{3, 3}, 6)));            // 기대값: [0, 1]
        System.out.println(Arrays.toString(s.twoSum(new int[]{-1,-2,-3,-4,-5}, -8))); // 기대값: [2, 4]

    }
}
