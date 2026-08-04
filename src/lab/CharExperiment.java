package lab;

import java.util.HashMap;
import java.util.Map;

/**
 * new String(keyChars)가 왜 되는지 눈으로 확인하는 실험.
 * 한 섹션씩 실행 결과를 보면서 따라와.
 */
public class CharExperiment {

    public static void main(String[] args) {

        System.out.println("=== 실험 1: char는 그냥 숫자다 ===");
        char a = 'a';
        System.out.println((int) a);        // 'a'의 정체는?
        System.out.println((char) 98);      // 숫자 98의 정체는?
        System.out.println((char) ('a' + 1)); // 'a' 다음 글자
        char weird = (char) 3;              // "글자 아닌 char"도 합법
        System.out.println("숫자 3을 char에 담음: [" + weird + "] (안 보이지만 값은 있음)");
        System.out.println("다시 숫자로 꺼내면: " + (int) weird);

        System.out.println();
        System.out.println("=== 실험 2: 배열은 내용이 같아도 '다르다' ===");
        int[] x = {1, 0, 3};
        int[] y = {1, 0, 3};
        System.out.println("x.equals(y) = " + x.equals(y));   // 내용 같은데?
        System.out.println("x.equals(x) = " + x.equals(x));   // 자기 자신이랑은?

        Map<int[], String> arrMap = new HashMap<>();
        arrMap.put(x, "eat의 그룹");
        System.out.println("배열을 키로 쓰면: arrMap.get(y) = " + arrMap.get(y)); // 찾아질까?

        System.out.println();
        System.out.println("=== 실험 3: String은 내용이 같으면 '같다' ===");
        char[] c1 = {(char) 1, (char) 0, (char) 3};  // "eat"의 카운트라고 치자
        char[] c2 = {(char) 1, (char) 0, (char) 3};  // "tea"의 카운트라고 치자
        String k1 = new String(c1);
        String k2 = new String(c2);
        System.out.println("k1.equals(k2) = " + k1.equals(k2));
        System.out.println("해시코드도 같나? " + (k1.hashCode() == k2.hashCode()));

        Map<String, String> strMap = new HashMap<>();
        strMap.put(k1, "eat의 그룹");
        System.out.println("String을 키로 쓰면: strMap.get(k2) = " + strMap.get(k2)); // 이번엔?
    }
}
