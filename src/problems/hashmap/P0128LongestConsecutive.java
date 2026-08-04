package problems.hashmap;

import java.util.HashSet;
import java.util.Set;

/**
 * 128. Longest Consecutive Sequence
 * https://leetcode.com/problems/longest-consecutive-sequence/
 * 난이도: Medium | 유형: Array
 *
 * 기록:
 * - 첫 풀이: 2026-08-03
 *
 * 접근:
 * 1. 입출력 정의: unsorted int[] nums / length of the longest consecutive(연속적인 - 구글링함) elements sequence.
 * 2. 제약 조건:
 *      O(n) time -> 정렬하지 말라는거네
 *      0 <= nums.length <= 10^5
 *      -10^9 <= nums[i] <= 10^9
 * 3. 예제 손으로 + 함정 찾기:
 *      연속된 숫자를 어떻게 알아내지?
 *      연속된 숫자를 알아내는게 아니라 내 바로 앞의 숫자의 최장길이만 알면 되는거 아님?
 *      근데 문제는 숫자가 점점 작아지먄사 들어올수도 있음
 *      그럼 오름차순으로 갱신해줄까..?
 *      1:42경 작전 변경
 *          나보다 작은 애가 있으면 작은애 -> 나
 *          나보다 큰 애가 있으면 -> 나 -> 큰애
 *          암것도 없으면 나 -> 나
 *          하고 순회하자..
 * 4. 브루트포스 + 복잡도: 정렬하고 정렬된 결과 순회하면서 갯수 세면서 max 갱신 : O(n log n) + O(n) = O(n log n) (이거 띄어쓰기 어디에 해야 하는거?)
 * 5. 병목 찾기 → 도구 선택:
 *      일단 클로드가 이걸 HashMap에 뒀으니 사용해보자.. 는 개뿔 배열 쓸꺼야 흠근데 8GB 졸라 크군 다시 맵으로 가보자
 * 6. 검증 (예제 + 엣지 케이스):
 *
 * 시간복잡도: O(n) / 공간복잡도: O(n)
 */
public class P0128LongestConsecutive {

    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;
        Set<Integer> numSet = new HashSet<>(nums.length * 2);
        for (int num : nums) {
            numSet.add(num);
        }
        int max = 0;
        for (int num : numSet) {
            if (!numSet.contains(num - 1)) {
                int count = 1;
                while (numSet.contains(num + 1)) {
                    count++;
                }
                if (count > max) {
                    max = count;
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        P0128LongestConsecutive sol = new P0128LongestConsecutive();
        System.out.println(sol.longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}));          // 기대값: 4  (1,2,3,4)
        System.out.println(sol.longestConsecutive(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}));  // 기대값: 9  (0~8)
        System.out.println(sol.longestConsecutive(new int[]{}));                              // 기대값: 0

        // 숫자가 점점 작아짐
        System.out.println(sol.longestConsecutive(new int[]{1,2,4,5,3,0,3,6,7})); // 8
        System.out.println(sol.longestConsecutive(new int[]{6, 5, 4, 3, 1, 2, 7}));  // 기대값: 7  (1~7)
        System.out.println(sol.longestConsecutive(new int[]{-1000000000+1, -1000000000, 4, 13, 1, 32, 7}));  // 기대값: 2
    }
}
