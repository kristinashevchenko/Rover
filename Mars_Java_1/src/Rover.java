import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Rover {
    public static Map<Integer, Integer> m = new TreeMap<Integer, Integer>();

    static int solve(int number) {
        if (number == 1) {
            return 1;
        }
        int count = (int) Math.floor(Math.log((double) number) / Math.log(2.0d));

        int pos = (int) Math.pow(2, count) - 1;
        int speed = (int) Math.pow(2, count);

        int f = number - pos;
        int b = pos + speed - number;

        if (f == 0) {
            return count;
        }
        if (b == 0) {
            return count + 1;
        }

        int ret = 0;
        if (f == b) {
            if (m.containsKey(f)) {
                ret = m.get(f);
            } else {
                ret = solve(f);
                m.put(f, ret);
            }
        } else {
            int ret1 = 0, ret2 = 0;
            if (m.containsKey(f)) {
                ret1 = m.get(f);
            } else {
                ret1 = solve(f);
                m.put(f, ret1);

            }

            if (m.containsKey(b)) {
                ret2 = m.get(b);
            } else {
                ret2 = solve(b);
                m.put(b, ret2);
            }
            ret = ret1 < ret2 ? ret1 : ret2;
        }
        m.put(number, ret + count + 2);
        return ret + count + 2;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the number of block:");
        int number = in.nextInt();
        int result;
        if (number == 0) {
            result = 0;
        } else {
            result = solve(Math.abs(number));
            if (number < 0) {
                result++;
            }
        }
        System.out.println("Answer: " + result);
    }
}

