package hashmap;

import java.util.HashSet;
import java.util.Set;

/**
 * 217. Contains Duplicate
 * https://leetcode.com/problems/contains-duplicate/
 * 난이도: Easy | 유형: Array, Hash Set
 *
 * 접근:
 * 1. 입출력 정의: 정수 배열 / 한 값이라도 두 번 이상 나오면 true, 아님 false
 * 2. 제약 조건: 숫자 음수 가능 양음 10억씩 -> 이거 integer 길이 되나?(int 최대값이 약 21.4억(2^31 −1).
 * 3. 예제 손으로 + 함정 찾기: 양끝에 똑같은게 있을 경우에도 패스 가능
 * 4. 브루트포스 + 복잡도: nums 100k -> O(n^2) 시간초과. O(n)으로 풀어야 함
 * 5. 병목 찾기 → 도구 선택:
 *      - HashSet.add 는 존재하면 false 를 반환 > 두 번 이상 나왔다는 뜻
 *      - HashSet은 16칸으로 시작, 75% 차면 rehashing. nums 의 최대 길이는 100k 니까 계속 늘어날것을 대비해서 미리 사이즈 지정하면 시간을 아낄 수 있다.
 * 6. 검증 (예제 + 엣지 케이스):
 *
 * 시간복잡도: O(n) / 공간복잡도: O(n)
 */
public class P0217ContainsDuplicate {

    public boolean containsDuplicate(int[] nums) {
        Set<Integer> exist = new HashSet<>(nums.length * 2);
        for (int num : nums) {
            if (!exist.add(num)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        P0217ContainsDuplicate s = new P0217ContainsDuplicate();
        System.out.println(s.containsDuplicate(new int[]{1, 2, 3, 1}));                   // 기대값: true
        System.out.println(s.containsDuplicate(new int[]{1, 2, 3, 4}));                   // 기대값: false
        System.out.println(s.containsDuplicate(new int[]{1, 1, 1, 3, 3, 4, 3, 2, 4, 2})); // 기대값: true
        System.out.println(s.containsDuplicate(new int[]{1000000000, 1, 1, 3, 3, 4, 3, 2, 4, 1000000000})); // 기대값: true
    }
}
