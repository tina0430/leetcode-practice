package lab;

/**
 * 704 Binary Search의 루프 경계를 눈으로 확인하는 실험.
 *
 * 불변식(invariant, 루프가 도는 내내 항상 참인 성질): target이 배열에 있다면 항상 [lo, hi] 안에 있다.
 * 이 불변식이 참이면 두 가지가 따라 나온다. 아래 세 버전은 그 둘 중 하나씩을 일부러 깬다.
 *   (1) 루프는 [lo, hi]가 비었을 때 끝나야 한다  -> 조건은 lo <= hi   (B가 이것을 깬다)
 *   (2) 매 바퀴 범위가 반드시 줄어야 한다        -> hi = mid - 1     (C가 이것을 깬다)
 *
 * 실행: javac -d out src/lab/BinarySearchInvariant.java && java -cp out lab.BinarySearchInvariant
 */
public class BinarySearchInvariant {

    static int search(int[] a, int target, boolean closed, boolean shrink, String label) {
        System.out.println("--- " + label + ", target=" + target + " ---");
        int lo = 0, hi = a.length - 1, steps = 0;
        while (closed ? lo <= hi : lo < hi) {
            if (++steps > 10) {
                System.out.println("  범위가 안 줄어든다. 10바퀴에서 강제 중단 [" + lo + ", " + hi + "]");
                return -2;
            }
            int mid = lo + (hi - lo) / 2;
            System.out.println("  [" + lo + ", " + hi + "] 남은 칸 " + (hi - lo + 1)
                    + " / mid=" + mid + " a[mid]=" + a[mid]);
            if (a[mid] == target) return mid;
            if (a[mid] < target) lo = mid + 1;
            else hi = shrink ? mid - 1 : mid;
        }
        System.out.println("  루프 종료 [" + lo + ", " + hi + "] 남은 칸 " + (hi - lo + 1));
        return -1;
    }

    public static void main(String[] args) {
        int[] a = {-1, 0, 3, 5, 9, 12};
        System.out.println("nums = [-1, 0, 3, 5, 9, 12]\n");

        System.out.println("반환 " + search(a, 12, true, true, "A. lo<=hi, hi=mid-1 (정본)") + "\n");
        System.out.println("반환 " + search(a, 4, true, true, "A. lo<=hi, hi=mid-1 (정본)") + "\n");
        System.out.println("반환 " + search(a, 9, false, true, "B. lo<hi (마지막 칸을 못 읽는다)") + "\n");
        System.out.println("반환 " + search(a, 12, false, true, "B. lo<hi (마지막 칸을 못 읽는다)") + "\n");
        System.out.println("반환 " + search(a, 4, true, false, "C. lo<=hi, hi=mid (안 줄어듦)") + "\n");

        int lo = 1_500_000_000, hi = 2_000_000_000;
        System.out.println("--- mid 공식, lo=" + lo + " hi=" + hi + " ---");
        System.out.println("lo + hi            = " + (lo + hi) + "  (Integer.MAX_VALUE = " + Integer.MAX_VALUE + ")");
        System.out.println("(lo + hi) / 2      = " + ((lo + hi) / 2));
        System.out.println("lo + (hi - lo) / 2 = " + (lo + (hi - lo) / 2));
        System.out.println("(lo + hi) >>> 1    = " + ((lo + hi) >>> 1) + "  (2006년 JDK가 고른 형태)");
    }
}
