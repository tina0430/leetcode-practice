package problems.array;

/**
 * 268. Missing Number
 * https://leetcode.com/problems/missing-number/
 * 난이도: Easy | 유형: -
 *
 * 기록:
 * - 첫 풀이: 2026-08-17
 *
 * 접근:
 * 1. 입출력 정의: int[] nums = 0 ~ nums.length with one missing number / missing number.
 * 2. 제약 조건:
 *      n == nums.length / 1 <= n <= 10^4 / 0 <= nums[i] <= n
 *      Follow up: O(1) extra space complexity / O(n) runtime complexity?
 * 3. 예제 손으로 + 함정 찾기:
 *      unique num
 * 4. 브루트포스 + 복잡도:
 *      nums.length + 1 길이의 배열 정의하고 nums 순회하면서 있는 숫자로 인덱스 찾아가서 표시, 유일하게 0이 들어있는 칸(slot?)의 인덱스 반환
 *      시간복잡도: O(n) / 공간복잡도: O(n)
 * 5. 병목 찾기 → 도구 선택:
 *      공간 복잡도를 O(1)로 만들기 위해 nums의 값 합 + missing num의 합을 미리 구해놓고(가우스 합 n(n+1)/2, O(1)) 순회하면서 뺀다
 * 6. 검증 (예제 + 엣지 케이스):
 *      [0], [1]
 * 시간복잡도: O(n) / 공간복잡도: O(1)
 */
public class P0268MissingNumber {

    public int missingNumber(int[] nums) {
        int missing = nums.length * (nums.length + 1) / 2; // 10k × 10k / 2 = 5 × 10^7 < 2^31
        for (int i = 0; i < nums.length; i++) {
            missing -= nums[i];
        }
        return missing;
    }

    public static void main(String[] args) {
        P0268MissingNumber s = new P0268MissingNumber();
        // 공식 예제 입력 (한 문제당 여러 줄. 호출 형태로 바꿔서 기대값 주석과 함께 쓸 것):
        System.out.println(s.missingNumber(new int[] {0}));                             // 1
        System.out.println(s.missingNumber(new int[] {1}));                             // 0
        System.out.println(s.missingNumber(new int[] {3, 0, 1}));                       // 2
        System.out.println(s.missingNumber(new int[] {0, 1}));                          // 2
        System.out.println(s.missingNumber(new int[] {9, 6, 4, 2, 3, 5, 7, 0, 1}));     // 8
    }
}
