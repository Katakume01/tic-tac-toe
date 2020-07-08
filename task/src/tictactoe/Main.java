package tictactoe;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String cells = " ";
    static String cInput = "";
    static String result = "Game not finished";

    static int index;
    static int x;
    static int o;
    static int empty;
    static int coordRow;
    static int coordCol;
    static boolean isThreeX = false;
    static boolean isThreeO = false;
    static boolean exit = false;
    static int[][] cField = {
            {13,23,33},
            {12,22,32},
            {11,21,31}
    };
    static int[] coordNumbers = new int[2];
    static char[][] field = new char[3][3];
    public static void main(String[] args) {

        //System.out.print("Enter cells: ");
        // = scanner.nextLine().trim();

        cells = "_________";
        setField();
        printField();

        while (!exit) {
            switch (result) {
                case "Game not finished":
                    setCoordinates('X');
                    printField();
                    gameState();
                    if (result.equals("Game not finished")) {
                        setCoordinates('O');
                        printField();
                        gameState();
                    }
                    break;
                case "Impossible":
                case "Draw":
                case "X wins":
                case "O wins":
                    exit = true;
                    break;
            }
        }

    }
    public static void setField() {
        index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cells.charAt(index) == '_') {
                    field[i][j] = ' ';
                    index++;
                } else {
                    field[i][j] = cells.charAt(index++);
                }
                if (field[i][j] == ' ') {
                    empty++;
                } else {
                    if (field[i][j] == 'X') {
                        x++;
                    } else {
                        o++;
                    }
                }
            }
        }
    }
    public static void printField() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.print("|");
            System.out.println();
        }

        System.out.println("---------");
        x = o = empty = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j] == ' ') {
                    empty++;
                } else {
                    if (field[i][j] == 'X') {
                        x++;
                    } else {
                        o++;
                    }
                }
            }
        }
        //System.out.println("X:" + x + " O:" + o + " empty:" + empty);
    }
    public static void threeTesting(){
        //int 'X' = 88 x 3 = 264    int 'O' = 79 x 3 = 237

        int tmpRow = 0;
        int tmpCol = 0;

        if (x >= 3 || o >=3) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                  tmpRow += field[i][j];
                  tmpCol += field[j][i];
                }
                if (tmpRow == 264) {
                    isThreeX = true;
                }
                if (tmpRow == 237) {
                    isThreeO = true;
                }
                if (tmpCol == 264) {
                    isThreeX = true;
                }
                if (tmpCol == 237) {
                    isThreeO = true;
                }
               //System.out.println("+ tmpRow:" + tmpRow + " tmpCol:" + tmpCol);
                tmpRow = tmpCol = 0;
            }
            // crossing
            tmpRow = tmpCol = 0;

                for (int j = 0; j < 3; j++) {
                    tmpRow += field[j][j];
                    tmpCol += field[j][2-j];
                    //System.out.println(j + "," + j + " ; " + j + "," + (2-j));
                }
                //System.out.println("x tmpRow:" + tmpRow + " tmpCol:" + tmpCol);

            if (tmpRow == 264) {
                isThreeX = true;
            }
            if (tmpRow == 237) {
                isThreeO = true;
            }
            if (tmpCol == 264) {
                isThreeX = true;
            }
            if (tmpCol == 237) {
                isThreeO = true;
            }

        }
    }
    public static void gameState() {
        threeTesting();
        if (isThreeX && isThreeO || Math.abs(x - o) > 1) {
            System.out.println("Impossible");
            result = "Impossible";
        } else if (!isThreeO && !isThreeX && empty > 0) {
            //System.out.println("Game not finished");
            result = "Game not finished";
        } else if (!isThreeO && !isThreeX && empty == 0) {
            System.out.println("Draw");
            result = "Draw";
        } else if (isThreeX) {
            System.out.println("X wins");
            result = "X wins";
        } else if (isThreeO) {
            System.out.println("O wins");
            result = "O wins";
        }
    }
    public static void setCoordinates(char x) {
        int cNumber = 0;
        boolean correct = false;

        while (!correct) {
            System.out.print("Enter the coordinates: ");
            cInput = scanner.nextLine().trim();
            //if ( (input.matches("\\d")) ) // check that input string consist from digit
            String[] tmp = cInput.split(" ");
            if (tmp.length == 2) {
                try {
                    cNumber = Integer.parseInt(cInput.replaceAll(" ", ""));
                    for (int i = 0; i < 2; i++) {
                        coordNumbers[i] = Integer.parseInt(tmp[i]);
                        if (coordNumbers[i] > 3 || coordNumbers[i] < 1) {
                            System.out.println("Coordinates should be from 1 to 3!");
                            cNumber = 0;
                            break;
                        }
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("You should enter numbers!");
                    cNumber = 0;
                }
            } else {
                System.out.println("You should enter numbers!");
            }

            if (cNumber != 0) {
                if (isIncField(cNumber) && !isCellEmpty(coordRow, coordCol)) {
                    System.out.println("This cell is occupied! Choose another one!");
                } else {
                    //[3 - y][x - 1] 
                    field[coordRow][coordCol] = x;
                    correct = true;
                }
            }
        }

    }
    public static boolean isIncField(int number) {
        boolean result = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(cField[i][j] == number) {
                    result = true;
                    coordRow = i;
                    coordCol = j;
                }
            }
        }
        return result;
    }
    public static boolean isCellEmpty(int coordRow, int coordCol) {
        boolean result = false;
        if (field[coordRow][coordCol] == ' ') {
            result = true;
        }
        return result;
    }
}
