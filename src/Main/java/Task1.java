import java.util.Scanner;

public class Task1 {
    static String createString(int n) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            builder.append(i);
        }
        return builder.toString().replace("0", "");
    }
    static int calcAmountOfRows(int strCharCount, int a)
    {
        if (strCharCount <= a) return 1;
        else
        {
            if(strCharCount%a == 0) return strCharCount/a;
            else return strCharCount/a + 1;
        }
    }
    static String [][] createArray(int rows, int columns, String nums) {
        String[][] table = new String[rows][columns];
        int chrIndx = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (chrIndx < nums.length()) table[i][j] = String.valueOf(nums.charAt(chrIndx++));
                else table[i][j] = "*";
            }
        }
        return table;
    }
    static boolean isConditionTrue(String first, String second) {
        if(first.equals("*") || second.equals("*")) return false;
        return (first.equals(second) || Integer.parseInt(first) + Integer.parseInt(second) == 10);
    }
    static boolean goToTheRight(String [][] array, int row, int column){
        if(column+1<array[0].length && !array[row][column+1].equals("*"))
        {
            if (isConditionTrue(array[row][column], array[row][column + 1])) {
                array[row][column] = "*";
                array[row][column + 1] = "*";
                return true;
            }
        }
        else
        {
            //число, которое добавляем к текущему индексу, для поиска соседа
            int k = 2;
            while(column+k < array[0].length-1 && array[row][column+k].equals("*"))
            {
                k++;
            }
            if(column+k <array[0].length && isConditionTrue(array[row][column], array[row][column+k])){
                array[row][column] = "*";
                array[row][column + k] = "*";
                return true;
            }
        }
        return false;
    }
    static  boolean goToTheDown(String[][] array, int row, int column){
        if (row + 1 < array.length && !array[row + 1][column].equals("*")) {
            if (isConditionTrue(array[row][column], array[row + 1][column])) {
                array[row][column] = "*";
                array[row + 1][column] = "*";
                return true;
            }
        } else {
            int k = 2;
            while (row + k < array.length - 1 && array[row + k][column].equals("*")) {
                k++;
            }
            if (row + k < array.length && isConditionTrue(array[row][column], array[row + k][column])) {
                //вот эти две ебучие строчки я столько раз написал, надо это в метод вынести
                array[row][column] = "*";
                array[row+k][column] = "*";
                return true;
            }
        }
        return false;
    }
    static boolean findNeighbourInRowAndReplace(String[][] array, int row, int column) {
        return goToTheRight(array, row, column) || goToTheDown(array, row, column);
    }
    static boolean arrayWalk(String [][] array) {
        boolean flag = false;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if ( !array[i][j].equals("*")) {
                    if(findNeighbourInRowAndReplace(array, i, j)) flag = true;
                }
            }
        }
        return flag;
    }
    static int runOneIteration(String [][] array) {
        boolean flag = true;
        int count = 0;
        while (flag) {
            flag = arrayWalk(array);
            if (flag) {
                for (int i = 0; i < array.length; i++) {
                    for (int j = 0; j < array[0].length; j++) {
                        System.out.print(array[i][j] + " ");
                    }
                    System.out.println();
                }
                System.out.println();
                count++;
            }
        }
        return count;
    }
    public static void main(String [] args){
        System.out.print("Введите A= ");
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        System.out.print("Введите N= ");
        int n = sc.nextInt();

        String str = createString(n);
        String[][] array = createArray(calcAmountOfRows(str.length(), a), a, str);
//        for (int i = 0; i < array.length; i++) {
//            for (int j = 0; j < array[0].length; j++) {
//                System.out.print(array[i][j] + " ");
//            }
//            System.out.println();
//        }
        StringBuilder builder = new StringBuilder();
        if (runOneIteration(array) > 0) {
            do {
                builder.setLength(0);
                for (int i = 0; i < array.length; i++) {
                    for (int j = 0; j < array[0].length; j++) {
                        if(!array[i][j].equals("*")) builder.append(array[i][j]);
                    }
                }
                array = createArray(calcAmountOfRows(builder.toString().length(), a), a, builder.toString());
            } while (runOneIteration(array)>0);
            System.out.println("\nРезультат");
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[0].length; j++) {
                    System.out.print(array[i][j] + " ");
                }
                System.out.println();
            }
        }
    }
}
