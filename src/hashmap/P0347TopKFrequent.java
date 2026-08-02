package hashmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 347. Top K Frequent Elements
 * https://leetcode.com/problems/top-k-frequent-elements/
 * 난이도: Medium | 유형: Array, 카운팅 + ?
 *
 * 기록:
 * - 첫 풀이: 2026-08-02
 *
 * 접근:
 * 1. 입출력 정의: int[] nums, int k (정답 길이) / int[] 제일 자주 나오는 숫자 k 개 반환
 * 2. 제약 조건: 1 <= nums.length <= 10^5, -10^4 <= nums[i] <= 10^4, 1 < k <= set(nums), 정답은 하나다 / 부등호도 중요한가?
 * 3. 예제 손으로 + 함정 찾기: 함정을 제약 조건에서 다 제거해준듯?
 * 4. 브루트포스 + 복잡도: 갯수 세고 나서 정렬 = n + nlongn 이 사실상 정답 나닌가?
 * 5. 병목 찾기 → 도구 선택:
 *   - 마지막에 sort 하면서 낭비되는 시간 아끼기 위해 꼼수 부렸는데 1, 1, 2, 2, 3 케이스를 생각 못함.
 * 6. 검증 (예제 + 엣지 케이스): nums.length 이랑 k랑 같으면 걍 그째로 반환해도 되는건가 / 최대 길이 + 겹치는것 최소
 *
 * 시간복잡도: O(?) / 공간복잡도: O(n)
 */
public class P0347TopKFrequent {

    public int[] topKFrequent(int[] nums, int k) {
        if (nums.length == k) return nums;
        Map<Integer, Integer> counts = new HashMap<>();
        for (int num : nums) {
            counts.merge(num, 1, Integer::sum); // 문법 못외워서 배낌
        }
        Map<Integer, List<Integer>> numMap = new HashMap<>();
        for (int num : counts.keySet()) {
            numMap.computeIfAbsent(counts.get(num), c -> new ArrayList<>()).add(num); // 문법 까먹어서 찾아봄
        }
        int[] result = new int[k];
        List<Integer> list = numMap.keySet().stream().sorted().toList();
        for (int i = list.size() - 1; i >= 0; i--) {
            for (int num : numMap.get(list.get(i))) {
                result[--k] = num;
            }
            if (k == 0) return result;
        }
        return result;
    }

    public static void main(String[] args) {
        P0347TopKFrequent sol = new P0347TopKFrequent();
        // 반환 순서는 채점에 상관없음 (LeetCode: return the answer in any order)
        System.out.println(Arrays.toString(sol.topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2)));    // 기대값: [1, 2]
        System.out.println(Arrays.toString(sol.topKFrequent(new int[]{1}, 1)));                   // 기대값: [1]
        System.out.println(Arrays.toString(sol.topKFrequent(new int[]{4, 4, 4, -1, -1, 7}, 2)));  // 기대값: [4, -1]

        // leetcode가 준 예제인데 1차 시도 실패
        System.out.println(Arrays.toString(sol.topKFrequent(new int[]{1,2,1,2,1,2,3,1,3,2}, 2)));    // 기대값: [1, 2]
        System.out.println(Arrays.toString(sol.topKFrequent(new int[]{-1, -1}, 1)));                   // 기대값: [-1]
        System.out.println(Arrays.toString(sol.topKFrequent(new int[]{3,0,1,0}, 1)));                   // 기대값: [0]

        System.out.println(Arrays.toString(sol.topKFrequent(new int[]{1, 2}, 2)));                   // 기대값: [1, 2]
    }
}
