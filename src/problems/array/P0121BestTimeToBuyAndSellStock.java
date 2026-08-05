package problems.array;

/**
 * 121. Best Time to Buy and Sell Stock
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
 * 난이도: Easy | 유형: 배열 one-pass (running min 추적)
 *
 * 기록:
 * - 첫 풀이: 2026-08-04
 *
 * 접근:
 * 1. 입출력 정의: prices[] i번째 날의 주가 / 최대 수익 반환 (수익을 낼 수 없을 때는 0을 반환)
 * 2. 제약 조건: 1 <= prices.length <= 10^5, 0 <= prices[i] <= 10^4
 * 3. 예제 손으로 + 함정 찾기:
 *      일별 최대 최소 가격을 구해서 빼면 되겠다. 최소는 올라가면서 최대는 내려가면서
 *      수익이 없는 날이라는 조건이 있다. 내려가기만 하는 경우도 체크해야 함
 * 4. 브루트포스 + 복잡도:
 *      prices 순회하면서 자기보다 뒤에 있는 숫자중 큰수가 있으면 profit 구하면서 최대값 찾음 -> O(n^2)
 *      근데 n = 10^5 니까 O(n^2) 이면 터짐.
 * 5. 병목 찾기 → 각 날짜별 최소값을 갱신하면서 이익 계산해가면 굳이 공간 낭비할 필요 없음
 * 6. 검증 (예제 + 엣지 케이스):
 *      prices.length = 1
 *
 * 시간복잡도: O(n) / 공간복잡도: O(1)
 */
public class P0121BestTimeToBuyAndSellStock {

    public int maxProfit(int[] prices) {
        if (prices.length == 1) return 0;
        int minPrice = prices[0];
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            int profit = prices[i] - minPrice;
            if (profit > maxProfit) {
                maxProfit = profit;
            }
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            }
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        P0121BestTimeToBuyAndSellStock s = new P0121BestTimeToBuyAndSellStock();
        System.out.println(s.maxProfit(new int[]{7, 1, 5, 3, 6, 4})); // 기대값: 5
        System.out.println(s.maxProfit(new int[]{7, 6, 4, 3, 1}));    // 기대값: 0
        System.out.println(s.maxProfit(new int[]{5}));                // 기대값: 0 (하루뿐)
        System.out.println(s.maxProfit(new int[]{5, 5, 4}));                // 기대값: 0
    }
}
