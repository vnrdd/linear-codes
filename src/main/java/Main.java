import jdk.jshell.execution.Util;

import java.util.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int k, n;

        // reading data
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input k: ");
        k = scanner.nextInt();

        System.out.print("Input n: ");
        n = scanner.nextInt();
        //

        if(k > n) {
            System.out.println("Error");
            return;
        }
        // generate

        //messages
        List<Integer[]> messages = new ArrayList<>() {
            {
                Integer[] buf = new Integer[k];
                Arrays.fill(buf, 0);
                for (int i = 0; i < Math.pow(2, k); ++i)
                    add(buf);
            }
        };

        for (int i = 0; i < Math.pow(2, k); ++i) {
            String buf = Integer.toBinaryString(i);
            Integer[] buf2 = Utils.stringToIntegerArray(buf);
            Integer[] res = new Integer[k];
            Arrays.fill(res, 0);
            Utils.reverseCopy(buf2, res);
            messages.set(i, res);
        }
        //

        // code words
        List<Integer[]> P = Code.generateP(n, k);
        List<Integer[]> G = Code.makeG(P);
        List<Integer[]> words = new ArrayList<>();

        System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "\nG-Matrix:"
                + ConsoleColors.RESET);
        for (Integer[] g : G)
            System.out.println(Utils.arrayToString(g));

        for (Integer[] message : messages)
            words.add(Code.encode(message, G));

        System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "\nCode Dictionary:"
                + ConsoleColors.RESET);
        for (int i = 0; i < messages.size(); ++i)
            System.out.println(Utils.arrayToString(messages.get(i)) + " --> "
                    + Utils.arrayToString(words.get(i)));

        System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT
                + "\nDmin = " + ConsoleColors.RESET + Maths.countMinD(G));
        System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT
                + "Corrective Ability = " + ConsoleColors.RESET
                + (Maths.countMinD(G) - 1) / 2);

        System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT
                + "Words count = " + ConsoleColors.RESET + words.size());

        // Decoding Matrix
        List<List<Integer[]>> D = Code.decodingMatrix(words);

        System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT
                + "\nDecoding Matrix:" + ConsoleColors.RESET);
        Utils.printDoubleList(D);

        // Decode
        System.out.print(ConsoleColors.PURPLE_BOLD_BRIGHT
                + "\nInput word to decode (" + words.get(0).length
                + " symbols, please): " + ConsoleColors.RESET);
        String toDecode = scanner.next();
        Integer[] toDecodeArray = Utils.stringToIntegerArray(toDecode);

        System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT
                + "Decoded Word: " + ConsoleColors.RESET
                + Utils.arrayToString(Code.decode(toDecodeArray, D)));

        int decodedMsg = 0;
        for(int i = 0; i < words.size(); ++i) {
            if(Code.isEquals(Code.decode(toDecodeArray, D), words.get(i))) {
                decodedMsg = i;
                break;
            }
        }

        System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT
                + "Decoded message: " + ConsoleColors.RESET
                + Utils.arrayToString(messages.get(decodedMsg)));
    }
}
