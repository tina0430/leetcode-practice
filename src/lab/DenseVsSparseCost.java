package lab;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 같은 데이터를 int[키범위]와 HashMap<Integer,Integer>에 담아 시간과 메모리를 직접 잰다.
 * 시나리오 1은 11 Container With Most Water의 상황(키 범위 10001, 원소 10^5)이다.
 * 실행: javac -d out src/lab/DenseVsSparseCost.java && java -cp out lab.DenseVsSparseCost
 */
public class DenseVsSparseCost {

    static final int N = 100000;            // 원소 수 n
    static final int[] DATA = new int[N];
    static int range;                       // 키 범위 m
    static long checksum;                   // JIT가 루프를 통째로 지우지 못하게 결과를 쌓는다
    static Object keepAlive;                // GC가 측정 대상을 수거하지 못하게 붙잡는다

    static long buildArray() {
        int[] count = new int[range];
        for (int v : DATA) count[v]++;
        keepAlive = count;
        return count[DATA[0]];
    }

    static long buildMap() {
        Map<Integer, Integer> count = new HashMap<>();
        for (int v : DATA) count.merge(v, 1, Integer::sum);
        keepAlive = count;
        return count.get(DATA[0]);
    }

    /** 20회 중 최소값을 쓴다. GC와 스케줄러의 방해는 시간을 늘리기만 하므로 최소값이 가장 깨끗하다. */
    static long fastestMicros(boolean useArray) {
        long best = Long.MAX_VALUE;
        for (int i = 0; i < 20; i++) {
            long t = System.nanoTime();
            checksum += useArray ? buildArray() : buildMap();
            best = Math.min(best, System.nanoTime() - t);
        }
        return best / 1000;
    }

    static long usedKB() {
        Runtime r = Runtime.getRuntime();
        for (int i = 0; i < 3; i++) System.gc();
        return (r.totalMemory() - r.freeMemory()) / 1024;
    }

    static long retainedKB(boolean useArray) {
        keepAlive = null;
        long before = usedKB();
        checksum += useArray ? buildArray() : buildMap();
        return usedKB() - before;
    }

    static void scenario(String label, int m) {
        range = m;
        Random rnd = new Random(42);
        for (int i = 0; i < N; i++) DATA[i] = rnd.nextInt(range);
        for (int i = 0; i < 30; i++) checksum += buildArray() + buildMap();   // 예열, 결과 버림
        System.out.printf("%s m=%-9d | array %6d us %6d KB | map %6d us %6d KB%n",
                label, m, fastestMicros(true), retainedKB(true), fastestMicros(false), retainedKB(false));
    }

    public static void main(String[] args) {
        scenario("dense ", 10001);
        scenario("sparse", 10000000);
        System.out.println("checksum = " + checksum);
    }
}
