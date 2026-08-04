package _template;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * ArrayList의 내부 배열(capacity)이 언제, 얼마나 자라는지 눈으로 확인하는 실험.
 * 리플렉션으로 캡슐화를 뚫고 내부 필드를 직접 들여다본다.
 *
 * 실행 (모듈 봉인 해제 플래그 필요):
 *   javac -d out src/_template/CapacityGrowth.java
 *   java --add-opens java.base/java.util=ALL-UNNAMED -cp out _template.CapacityGrowth
 */
public class CapacityGrowth {

    public static void main(String[] args) throws Exception {
        Field f = ArrayList.class.getDeclaredField("elementData");
        f.setAccessible(true);

        List<Integer> list = new ArrayList<>();
        long copied = 0;
        int prevCap = ((Object[]) f.get(list)).length;
        System.out.println("빈 리스트의 capacity = " + prevCap);

        for (int i = 1; i <= 10_000; i++) {
            list.add(i);
            int cap = ((Object[]) f.get(list)).length;
            if (cap != prevCap) {
                int copiedNow = list.size() - 1;
                copied += copiedNow;
                System.out.printf("size=%5d | capacity %5d -> %5d | 이번 복사 %5d개 | 누적 복사 %6d개%n",
                        list.size(), prevCap, cap, copiedNow, copied);
                prevCap = cap;
            }
        }
        System.out.println("총 add 10,000회 / 누적 복사 " + copied + "회");
        System.out.println("(add 횟수 대비 복사가 몇 배쯤인지, 그리고 그 배수가 왜 그 값인지가 다음 문답 주제)");
    }
}
