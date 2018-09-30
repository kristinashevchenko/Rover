import java.util.Scanner;

public class Rover {

    private int number = 0;

    private int lowerBorder = 1;
    private int upperBorder = 0;
    private int degree = 0;

    private int table[][];
    Rover(int number){
        this.number=number;
    }


    private void makeTable() {
        table = new int[(upperBorder + 1) * 2][degree * 2 + 2];

        for (int i = 0; i < upperBorder * 2 + 2; i++)
            for (int j = 0; j < degree * 2 + 2; j++) {
                table[i][j] = Integer.MAX_VALUE;
            }

        for (int i = 0; i < degree + 1; i++) {
            table[0][i] = (-1) * (int) Math.pow(2, degree - i);
            table[0][degree * 2 + 1 - i] = table[0][i] * (-1);
        }

        for (int i = upperBorder + 2, j = 1; i <= upperBorder * 2 + 1; i += (int) Math.pow(2, j), j++) {
            table[i][degree + 1 + j] = j;
            table[i][degree] = j + 1;
            table[i][degree + 1] = j + 2;
        }

        for (int i = upperBorder, j = 1; i >= lowerBorder; i -= (int) Math.pow(2, j), j++) {
            table[i][degree - j] = j + 1;
            table[i][degree] = j + 3;
            table[i][degree + 1] = j + 2;
        }

        table[upperBorder + 1][degree] = 1;
        table[upperBorder + 1][degree + 1] = 0;
    }

    public int solve() {

        degree = (int) Math.floor(Math.log(Math.abs(number)) / Math.log(2)) + 1;
        upperBorder = (int) Math.pow(2, degree) - 1;

        makeTable();
        filling();

        int freeLine = findFreeLine();
        while (freeLine != 0) {
            table[freeLine][degree] = find(-1, freeLine);
            table[freeLine][degree + 1] = find(1, freeLine);

            workCase(freeLine, degree, -1);
            workCase(freeLine, degree + 1, 1);

            freeLine = findFreeLine();
        }

        int result = Integer.MAX_VALUE;
        for (int j = 0; j < degree * 2 + 2; j++) {
            if (result > table[upperBorder + 1 + number][j]) {
                result = table[upperBorder + 1 + number][j];
            }
        }
        return result;
    }

    private void filling() {
        for (int i = 1; i <= upperBorder * 2 + 1; i++) {
            for (int j = 0; j < degree * 2 + 2; j++) {
                if (table[i][j] != Integer.MAX_VALUE) {
                    workCase(i, j, (int) Math.signum(table[0][j]));
                }
            }
        }
    }

    private int find(int sign, int line) {
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;

        for (int j = 0; j < degree; j++) {
            if (table[line][j] < min2)
                min2 = table[line][j];
        }
        for (int j = degree + 2; j < degree * 2 + 2; j++) {
            if (table[line][j] < min1)
                min1 = table[line][j];
        }

        if (min1 != Integer.MAX_VALUE && min2 != Integer.MAX_VALUE) {
            if (sign == 1)
                return Math.min(min1 + 2, min2 + 1);
            else
                return Math.min(min1 + 1, min2 + 2);
        } else {
            if (min1 == Integer.MAX_VALUE && sign == 1)
                return min2 + 1;
            if (min2 == Integer.MAX_VALUE && sign == 1)
                return min1 + 2;
            if (min1 == Integer.MAX_VALUE && sign == -1)
                return min2 + 2;
            if (min2 == Integer.MAX_VALUE && sign == -1)
                return min1 + 1;
        }

        return 0;
    }

    private void workCase(int line, int column, int sign) {
        int j = column;
        int i = line;

        while (true) {
            if ((i + table[0][j]) >= lowerBorder && (i + table[0][j]) <= upperBorder * 2 + 1) {
                if (table[i + table[0][j]][j + sign] > table[i][j] + 1)
                    table[i + table[0][j]][j + sign] = table[i][j] + 1;
                else
                    break;

                i += table[0][j];
                j += sign;
            } else
                break;
        }
    }

    private int findFreeLine() {
        for (int i = lowerBorder; i <= upperBorder * 2 + 1; i++)
            if (table[i][degree] == Integer.MAX_VALUE && i != upperBorder + 1 + number) {
                return i;
            }
        return 0;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the number of block:");
        int num = in.nextInt();
        int result;
        if (num == 0) {
            result = 0;
        } else {
            Rover m=new Rover(num);
            result = m.solve();
        }
        System.out.println("Answer: " + result);
    }
}
