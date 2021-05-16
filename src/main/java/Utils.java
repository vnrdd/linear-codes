import java.util.Arrays;
import java.util.List;

public class Utils {
    public static Integer[] stringToIntegerArray(String src) {
        Integer[] res = new Integer[src.length()];

        for (int i = 0; i < src.length(); ++i)
            res[i] = Integer.parseInt(String.valueOf(src.charAt(i)));

        return res;
    }

    public static Integer[] reverseCopy(Integer[] src, Integer[] dst) {
        for (int i = 0; i < src.length; ++i)
            dst[dst.length - i - 1] = src[src.length - i - 1];

        return dst;
    }

    public static boolean isZeroVector(Integer[] vector) {
        int onesCount = 0;
        for(Integer i : vector)
            onesCount += i;

        return onesCount == 0;
    }

    public static boolean isAllEquals(Integer[] vec) {
        int a = vec[0];
        for(Integer p : vec) {
            if(p != a)
                return false;
        }

        return true;
    }

    static void p2(int pos, int maxUsed, int k, int n, Integer[] a, List<Integer[]> C) {
        if (pos == k) {
            Integer[] b = new Integer[a.length];
            System.arraycopy(a, 0, b, 0, a.length);
            C.add(b);
        } else {
            for(int i = maxUsed+1; i <= n; i++) {
                a[pos] = i;
                p2(pos+1,i, k, n, a, C);
            }
        }
    }

    public static void printDoubleList(List<List<Integer[]> > list) {
        for(List<Integer[]> sublist : list) {
            for(Integer[] vector : sublist) {
                for(Integer i : vector)
                    System.out.print(i);
                System.out.print(" ");
            }
            System.out.println("");
        }
    }

    public static String arrayToString(Integer[] array) {
        StringBuilder sb = new StringBuilder();
        for(Integer i : array)
            sb.append(i);
        return sb.toString();
    }
}
