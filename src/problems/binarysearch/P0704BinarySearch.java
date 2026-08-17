package problems.binarysearch;

/**
 * 704. Binary Search
 * https://leetcode.com/problems/binary-search/
 * 난이도: Easy | 유형: 이진 탐색 (닫힌 구간 불변식). **08-19 모듈에서 빈칸(3·6단계, 복잡도)과 정본 재작성 예정**
 *
 * 기록:
 * - 첫 풀이: 2026-08-16
 *
 * 접근:
 * 1. 입출력 정의: 오름차순 정렬된 int[] nums, int target / target이 int에 존재하면 index 반환, 없으면 -1 반환
 * 2. 제약 조건:
 *      시간 복잡도 O(log n)로 짜라고 명시되어있음
 *      1 <= nums.length <= 10^4
 *      -10^4 < nums[i], target < 10^4
 *      All the integers in nums are unique.
 *      nums is sorted in ascending order.
 * 3. 예제 손으로 + 함정 찾기:
 *    - 지문에 안 적힌 것 (2개 뽑기):
 *      (1)
 *      (2)
 * 4. 브루트포스 + 복잡도: 순회하다가 자신보다 큰 숫자 만나면 -1 반환, 같은 숫자 만나면 인덱스 반환
 * 5. 병목 찾기 → 도구 선택:
 *      n 보다 작은 시간 복잡도를 쓰라고 했으니 일반적인 순회는 안된다.
 * 6. 검증 (예제 + 엣지 케이스):
 *
 * 시간복잡도: O(?) / 공간복잡도: O(?)
 */
public class P0704BinarySearch {

    public int search(int[] nums, int target) {
        int s = 0;
        int e = nums.length - 1;
        int m;
        while (s <= e) {
            m = (s + e) / 2;
            if (nums[m] == target) {
                return m;
            }
            if (nums[m] < target) {
                s = m + 1;
            } else {
                e = m - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        P0704BinarySearch sol = new P0704BinarySearch();

        System.out.println(sol.search(new int[]{-1, 0, 3, 5, 9, 12}, 9));   // 4
        System.out.println(sol.search(new int[]{-1, 0, 3, 5, 9, 12}, 2));   // -1
        // 엣지 케이스는 직접 추가할 것
    }
}
