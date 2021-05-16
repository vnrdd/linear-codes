import java.util.List;

public class Maths {
    public static int countD(Integer[] first, Integer[] second) {
        int d = 0;

        int n = Math.max(first.length, second.length);
        Integer[] buf1 = new Integer[n];
        Integer[] buf2 = new Integer[n];

        System.arraycopy(first, 0, buf1, 0, first.length);
        System.arraycopy(second, 0, buf2, 0, second.length);

        for(int i = 0; i < first.length; ++i) {
            if(!buf1[i].equals(buf2[i]))
                ++d;
        }

        return d;
    }

    public static int countMinD(List<Integer[]> words) {
        int minD = Integer.MAX_VALUE;
        int n = words.get(words.size() - 1).length;

        for(int i = 0; i < words.size(); ++i) {
            for(int k = 0; k < words.size(); ++k) {
                if(k == i)
                    continue;
                int d = countD(words.get(i), words.get(k));
                if(d < minD)
                    minD = d;
            }
        }

        return minD;
    }
}
