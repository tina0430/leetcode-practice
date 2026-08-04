package problems.hashmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 347. Top K Frequent Elements (복습 1회차: 버킷 정렬 O(n) 버전)
 * https://leetcode.com/problems/top-k-frequent-elements/
 * 난이도: Medium | 유형: 카운팅 + 버킷 정렬
 *
 * 기록:
 * - 첫 풀이: 2026-08-02 (TreeMap 버전 통과. 버킷 설계는 고정 폭 크래시로 후퇴)
 * - 복습 1회차: 2026-08-03 (버킷을 "리스트의 배열"로 완성하기)
 *
 * Approach:
 * 1. Input/Output: int[] nums, int k / int[] most frequent elements (count = k)
 * 2. Constraints:
 *      1 <= nums.length <= 10^5
 *      -10^4 <= nums[i] <= 10^4
 *      k is in the[1, the number of unique elements].
 *      answer is unique.
 * 3. Examples & edge cases: different number, same count
 * 4. Brute force + complexity: check all element and count and sort by count -> O(nlog n)
 * 5. Bottleneck -> tool: k = nums.length - 1 and all eliment is unique with maximum size HashMap or HashSet..?
 * 6. Verification:
 *
 * Time: O(n) / Space: O(n)
 */
public class P0347TopKFrequentR1 {

    public int[] topKFrequent(int[] nums, int k) {
        if (nums.length == k) return nums;
        int[] result = new int[k];
        // O(n)
        Map<Integer, Integer> counts = new HashMap<>();
        for (int num : nums) {
            counts.merge(num, 1, Integer::sum);
        }
        // O(m)..?
        Map<Integer, List<Integer>> countArr = new HashMap<>();
        boolean[] exsits = new boolean[nums.length + 1];
        for (int num : counts.keySet()) {
            int count = counts.get(num);
            countArr.computeIfAbsent(count, c -> new ArrayList<>()).add(num);
            exsits[count] = true;
        }
        // O(n)
        for (int i = exsits.length - 1; i >= 0 && k > 0; i--) {
            if (exsits[i] && countArr.containsKey(i)) {
                for (int num : countArr.get(i)) {
                    result[--k] = num;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        P0347TopKFrequentR1 sol = new P0347TopKFrequentR1();
        System.out.println(Arrays.toString(sol.topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2)));    // 기대값: [1, 2]
        System.out.println(Arrays.toString(sol.topKFrequent(new int[]{1}, 1)));                   // 기대값: [1]
        System.out.println(Arrays.toString(sol.topKFrequent(new int[]{4, 4, 4, -1, -1, 7}, 2)));  // 기대값: [4, -1]
        // 어제의 함정 3종
        System.out.println(Arrays.toString(sol.topKFrequent(new int[]{1, 2, 1, 2, 1, 2, 3, 1, 3, 2}, 2)));
        System.out.println(Arrays.toString(sol.topKFrequent(new int[]{3, 0, 1, 0}, 1)));          // 기대값: [0]  (같은 count에 여러 숫자 + 0/음수)
        System.out.println(Arrays.toString(sol.topKFrequent(new int[]{7, 7, 7}, 1)));             // 기대값: [7]  (count = n, 버킷 배열 크기 경계)
    }
}
