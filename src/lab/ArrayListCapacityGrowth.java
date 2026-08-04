package lab;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * ArrayList의 내부 배열(capacity)이 언제, 얼마나 자라는지 눈으로 확인하는 실험.
 * 리플렉션으로 캡슐화를 뚫고 내부 필드를 직접 들여다본다.
 *
 * 실행 (모듈 봉인 해제 플래그 필요):
 *   javac -d out src/_template/CapacityGrowth.java
 *   java --add-opens java.base/java.util=ALL-UNNAMED -cp out lab.CapacityGrowth
 */
public class ArrayListCapacityGrowth {

    public static void main(String[] args) throws Exception {
        // 리플렉션(reflection, 실행 중에 클래스의 내부 구조를 들여다보는 기능).
        // f = "ArrayList 설계도에서 elementData 칸의 위치(오프셋)와 타입, 접근 규칙"을 담은 객체.
        // 특정 리스트의 값이 아니라 칸의 위치 정보라서, f 하나로 어떤 ArrayList든 읽을 수 있다.
        Field f = ArrayList.class.getDeclaredField("elementData");
        // private 접근 금지를 해제한다. 실험용 뒷문이라 실무 코드에서는 쓰지 않는다.
        f.setAccessible(true);

        List<Integer> list = new ArrayList<>();
        long copied = 0;
        // f.get(list) = "list 객체 시작 주소 + elementData 오프셋" 위치에서 참조 8바이트를 읽어라.
        // (배열 공식 addr(a[i]) = B + i×크기와 같은 원리다. 시작 주소 + 오프셋)
        // 반환 타입이 Object라서 (Object[])로 캐스팅해야 .length를 읽을 수 있다.
        // 배열의 length가 곧 capacity다 (capacity는 별도 필드가 아님 - 덱 카드 27).
        int prevCap = ((Object[]) f.get(list)).length;
        System.out.println("빈 리스트의 capacity = " + prevCap);

        for (int i = 1; i <= 10_000; i++) {
            list.add(i);
            int cap = ((Object[]) f.get(list)).length;
            // capacity가 변했다 = 방금 add에서 리사이징이 일어났다
            if (cap != prevCap) {
                // 리사이징 직전까지 들어있던 원소 수 = 새 배열로 옮겨 적은(복사한) 개수
                int copiedNow = list.size() - 1;
                copied += copiedNow;
                // printf의 %5d = "정수를 5칸 폭으로 오른쪽 정렬", %n = 줄바꿈. 표 모양 맞추기용이다.
                System.out.printf("size=%5d | capacity %5d -> %5d | 이번 복사 %5d개 | 누적 복사 %6d개 | 평균 O(n)%5d%n",
                        list.size(), prevCap, cap, copiedNow, copied, (i + copied)/i);
                prevCap = cap;
            }
        }
        System.out.println("총 add 10,000회 / 누적 복사 " + copied + "회");
        System.out.println("(add 횟수 대비 복사가 몇 배쯤인지, 그리고 그 배수가 왜 그 값인지가 다음 문답 주제)");
    }
}
