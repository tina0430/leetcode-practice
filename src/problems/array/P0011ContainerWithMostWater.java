package problems.array;

/**
 * 11. Container With Most Water
 * https://leetcode.com/problems/container-with-most-water/
 * 난이도: Medium | 유형: 값을 키로 쓴 경계 인덱스 사전 계산 (O(n+m)). **R1 과제: m을 없앤 O(n) 공간 O(1) 재설계**
 *
 * 기록:
 * - 첫 풀이: 2026-08-15
 *
 * 접근:
 * 1. 입출력 정의: integer[] height of length n / 물을 제일 많이 담을 수 있는 두 라인을 찾고 물의 양을 반환 (slant가 뭐지)
 * 2. 제약 조건:
 *      n == height.length
 *      2 <= n <= 10^5   / 라인은 최소 2개부터니까 물을 못담는 경우는 없다
 *      0 <= height[i] <= 10^4 / 높이가 0부터 10K - 물의 양이 0이 될 수 있겠으나 예외로 뺄만한건 아닌듯?
 * 3. 예제 손으로 + 함정 찾기:
 *    - 지문에 안 적힌 것 (2개 뽑기. "내가 뭘 했나"가 아니라 "지문이 뭘 안 정했나"):
 *      (1)
 *      (2)
 * 4. 브루트포스 + 복잡도: 순회하면서 자신과 자신보다 뒤에 있는 라인을 선택했을 때 최대 물의 양을 구한다. (n-1) + (n-2) + ... + 1 = O(n^2)
 * 5. 병목 찾기 → 도구 선택:
 *      각 높이 마다 시작할 수 있는 지점을 쫙 깔아놓고, 반대로 순회하면서 거꾸로 갈 수 있는 지접을 찾도록 해보자
 *      첨부터 10K가 나와버리면 느리겠지만 그래도 10K니까 통과는 한다...
 * 6. 검증 (예제 + 엣지 케이스):
 *      0과 10K..?
 *      더 높은게 나중에 나오는 경우 [1, 2]
 *
 * 시간복잡도: O(n + m) / 공간복잡도: O(m)
 */
public class P0011ContainerWithMostWater {

    public int maxArea(int[] height) {
        int[] startIdxs = new int[10001];
        int[] endIdxs = new int[10001];
        for (int i = 0; i <= 10000; i++) {
            startIdxs[i] = -1;
            endIdxs[i] = -1;
        }
        for (int i = 0; i < height.length; i++) {
            for (int j = height[i]; j >= 0; j--) {
                if (startIdxs[j] >= 0) {
                    break;
                }
                startIdxs[j] = i;
            }
        }
        for (int i = height.length - 1; i >= 0; i--) {
            for (int j = height[i]; j >= 0; j--) {
                if (endIdxs[j] >= 0) {
                    break;
                }
                endIdxs[j] = i;
            }
        }
        int amount = 0;
        for (int i = 0; i < height.length; i++) {
            int temp = height[i] * (endIdxs[height[i]] - startIdxs[height[i]]);
            if (temp > amount) {
                amount = temp;
            }
        }
        return amount;
    }

    public static void main(String[] args) {
        P0011ContainerWithMostWater sol = new P0011ContainerWithMostWater();

        System.out.println(sol.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));    // 49
        System.out.println(sol.maxArea(new int[]{1, 1}));                         // 1
        // 엣지 케이스는 직접 추가할 것
        System.out.println(sol.maxArea(new int[]{10000, 0}));                     // 0
        System.out.println(sol.maxArea(new int[]{1, 2}));                         // 1
    }
}
