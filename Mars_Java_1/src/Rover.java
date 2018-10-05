import java.util.Scanner;

public class Rover {

    private int number = 0;
    private int lowerBorder = 1;
    private int upperBorder = 0;
    private int degree = 0;
    private int table[][];

    Rover(int number) {
        this.number = number;
    }

    private void makeTable() {
        table = new int[(upperBorder + 1) * 2][degree * 2 + 2];

        for (int i = 0; i < upperBorder * 2 + 2; i++)
            for (int j = 0; j < degree * 2 + 2; j++)
                table[i][j] = Integer.MAX_VALUE;

        for (int i = 0; i < degree + 1; i++) {
            table[0][i] = (-1) * (int) Math.pow(2, degree - i);
            table[0][degree * 2 + 1 - i] = table[0][i] * (-1);
        }
    }

    private void marched(int coord, int speed_index, int steps) {
        if (table[upperBorder + 1 + coord][speed_index] > steps) {
            table[upperBorder + 1 + coord][speed_index] = steps;

            if (inBorders(upperBorder + 1 + coord + table[0][speed_index])) {
                marched(coord + table[0][speed_index], speed_index + (int) Math.signum(table[0][speed_index]), steps + 1);
            }
            if (Math.signum(table[0][speed_index]) < 0) {
                marched(coord, degree + 1, steps + 1);
            } else {
                marched(coord, degree, steps + 1);
            }
        }
    }

    private boolean inBorders(int line) {
        if (line >= lowerBorder && line <= upperBorder * 2 + 1)
            return true;
        else
            return false;
    }

    public int solve() {

        degree = (int) Math.floor(Math.log(Math.abs(number)) / Math.log(2)) + 1;
        upperBorder = (int) Math.pow(2, degree) - 1;
        makeTable();

        marched(0, degree + 1, 0);

        int result = Integer.MAX_VALUE;
        for (int j = 0; j < degree * 2 + 2; j++) {
            if (result > table[upperBorder + 1 + number][j]) {
                result = table[upperBorder + 1 + number][j];
            }
        }
        return result;
    }


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the number of block:");
        int num = scan.nextInt();
        Thread thread = new Thread(null, null, "MarsRover", 100000000) {
            @Override
            public void run() {
                int result = 0;
                try {
                    if (num != 0) {
                        Rover r = new Rover(num);
                        result = r.solve();
                    } else {
                        result = 0;
                    }
                    System.out.println("Answer: " + result);
                } catch (StackOverflowError error) {
                    System.out.println("Not enough stack.");
                }
            }
        };
        thread.start();
    }
}
