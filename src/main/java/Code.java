import java.util.*;

public class Code {
    public static Integer[] generateWord(int length) {
        Integer[] word = new Integer[length];

        for (int i = 0; i < length; ++i)
            word[i] = (new Random()).nextInt(2);

        return word;
    }

    public static boolean isEquals(Integer[] first, Integer[] second) {
        if (first.length != second.length)
            return false;

        for (int i = 0; i < first.length; ++i) {
            if (!first[i].equals(second[i]))
                return false;
        }

        return true;
    }

    public static Integer[] xor(Integer[] first, Integer[] second) {
        Integer[] res = new Integer[first.length];

        for (int i = 0; i < first.length; ++i)
            res[i] = first[i] ^ second[i];

        return res;
    }

    public static int vectorWeight(Integer[] vector) {
        int weight = 0;

        for (Integer i : vector)
            weight += i;

        return weight;
    }

    public static boolean isLI(List<Integer[]> words, Integer[] candidate) {
//        List<Integer[]> buf = new ArrayList<>(words);
//        Integer[] cand = new Integer[candidate.length];
//        System.arraycopy(candidate, 0, cand, 0, candidate.length);
//        buf.add(cand);
//        int maxM = buf.size();
//
//        List<Integer[]> C = new ArrayList<>();
//        for (int i = 2; i <= maxM; ++i) {
//            Integer[] tmp = new Integer[i];
//            Utils.p2(0, 0, i, buf.size(), tmp, C);
//        }
//
//        for (int i = 0; i < C.size(); ++i) {
//            Integer[] sum = new Integer[candidate.length];
//            Arrays.fill(sum, 0);
//            for (int k = 0; k < C.get(i).length; ++k)
//                sum = xor(sum, buf.get(C.get(i)[k] - 1));
//            if (isEquals(cand, sum))
//                return true;
//        }
//
//        buf.remove(buf.size() - 1);
        return false;
    }

    public static List<Integer[]> generateP(int n, int k) {
        List<Integer[]> P = new ArrayList<>();
        int vectorsFound = 0;
        int r = n - k;
        // form B
        List<Integer[]> Pbuf = new ArrayList<>() {
            {
                Integer[] buf = new Integer[r];
                Arrays.fill(buf, 0);
                for (int i = 1; i < Math.pow(2, r); ++i)
                    add(buf);
            }
        };

        for (int i = 1; i < Math.pow(2, r); ++i) {
            String buf = Integer.toBinaryString(i);
            Integer[] buf2 = new Integer[r];
            Arrays.fill(buf2, 0);
            Integer[] tmp = Utils.stringToIntegerArray(buf);
            Collections.reverse(Arrays.asList(tmp));
            System.arraycopy(tmp, 0,
                    buf2, 0, tmp.length);
            Pbuf.set(i - 1, buf2);
        }
        //
        Collections.shuffle(Pbuf);
        int dmin = n - k - 1;
        for (Integer[] b : Pbuf) {
            if (vectorWeight(b) > dmin - 1) {
                P.add(b);
                Pbuf.remove(b);
                vectorsFound++;
                break;
            }
        }

        while (vectorsFound != k) {
            for (Integer[] vec : Pbuf) {
                if (!isLI(P, vec) && vectorWeight(vec) > dmin - 1) {
                    P.add(vec);
                    vectorsFound++;
                    Pbuf.remove(vec);
                    break;
                }
            }
        }

        return P;
    }

    public static List<Integer[]> makeG(List<Integer[]> P) {
        List<Integer[]> unit = new ArrayList<Integer[]>() {
            {
                for (int i = 0; i < P.size(); ++i) {
                    Integer[] buf = new Integer[P.size()];
                    Arrays.fill(buf, 0);
                    buf[i] = 1;
                    add(buf);
                }
            }
        };

        List<Integer[]> G = new ArrayList<Integer[]>();

        for (int i = 0; i < unit.size(); ++i) {
            Integer[] buf = new Integer[unit.get(i).length + P.get(i).length];
            System.arraycopy(unit.get(i), 0, buf, 0, unit.get(i).length);
            System.arraycopy(P.get(i), 0, buf, unit.get(i).length, P.get(i).length);

            G.add(buf);
        }

        return G;
    }

    public static Integer[] encode(Integer[] message, List<Integer[]> G) {
        Integer[] res = new Integer[G.get(0).length];
        Arrays.fill(res, 0);

        for (int i = 0; i < message.length; ++i) {
            if (message[i] == 1)
                res = xor(res, G.get(i));
        }

        return res;
    }

    public static List<List<Integer[]>> decodingMatrix(List<Integer[]> words) {
        int grade = words.get(0).length;
        List<Integer[]> pool = new ArrayList<>() {
            {
                Integer[] buf = new Integer[grade];
                Arrays.fill(buf, 0);
                for (int i = 0; i < Math.pow(2, grade); ++i)
                    add(buf);
            }
        };

        for (int i = 0; i < Math.pow(2, grade); ++i) {
            String buf = Integer.toBinaryString(i);
            Integer[] buf2 = Utils.stringToIntegerArray(buf);
            Integer[] res = new Integer[grade];
            Arrays.fill(res, 0);
            Utils.reverseCopy(buf2, res);
            pool.set(i, res);
        }

        List<Integer[]> sortedPool = new ArrayList<>();
        for (int i = 1; i <= 6; ++i) {
            for (Integer[] p : pool) {
                if (vectorWeight(p) == i) {
                    Integer[] buf = new Integer[p.length];
                    System.arraycopy(p, 0, buf, 0, p.length);
                    sortedPool.add(buf);
                }
            }
        }

        for (Integer[] word : words) {
            for (Integer[] p : sortedPool) {
                if (isEquals(p, word)) {
                    sortedPool.remove(p);
                    break;
                }
            }
        }

        List<List<Integer[]>> D = new ArrayList<>();
        D.add(words);

        while (!sortedPool.isEmpty()) {
            List<Integer[]> row = new ArrayList<>();
            Integer[] firstBuf = new Integer[sortedPool.get(0).length];
            System.arraycopy(sortedPool.get(0), 0, firstBuf,
                    0, sortedPool.get(0).length);
            row.add(firstBuf);
            sortedPool.remove(0);

            for (int i = 1; i < words.size(); ++i) {
                Integer[] xorred = xor(words.get(i), row.get(0));
                row.add(xorred);

                for (Integer[] p : sortedPool) {
                    if (isEquals(p, xorred)) {
                        sortedPool.remove(p);
                        break;
                    }
                }
            }

            D.add(row);
        }

        return D;
    }

    public static Integer[] decode(Integer[] word, List<List<Integer[]>> D) {
        Integer[] result = new Integer[word.length];

        for (int i = 0; i < D.size(); ++i) {
            for (int j = 0; j < D.get(i).size(); ++j) {
                if (isEquals(word, D.get(i).get(j))) {
                    System.arraycopy(D.get(0).get(j), 0,
                            result, 0, result.length);
                    return result;
                }
            }
        }

        return result;
    }
}
