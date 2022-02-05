import java.lang.*;

public class Sudoku extends Grid{

    public static final int EMPTY = 0; // empty cell
    public static final int SIZE = 9; // size of our Sudoku grids
    //constructor
    public Sudoku(int[][] board) {
        this.board = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.board[i][j] = board[i][j];
            }
        }
    }
    public Sudoku() {
        this.board = new int[9][9];
    }
    //uzupelniamy po kwadraty po skosie losowymi wartosciami
    public void fillDiagonal() {

        for (int i = 0; i<9; i+=3)

            fillBox(i, i);
    }
    private void fillBox(int a, int b){
        int num;
        for (int i=0; i<3; i++)
        {
            for (int j=0; j<3; j++)
            {
                do
                {
                    num = randomGenerator(9);
                }
                while (isInBox(a, b, num));

                board[a+i][b+j] = num;
            }
        }
    }
    private int randomGenerator(int num)
    {
        return (int) Math.floor((Math.random()*num+1));
    }
    //usuwamy c losowych liczb
    public void removeKDigits(int c)
    {
        int count;
        count = c;
        while (count != 0)
        {
            int cellId = randomGenerator(80);
            // System.out.println(cellId);
            // extract coordinates i  and j
            int i = (cellId/9);
            int j = cellId%9;

            // System.out.println(i+" "+j);
            if (board[i][j] != 0)
            {
                count--;
                board[i][j] = 0;
            }
        }
    }
    //sprawdzamy czy jest w rzedzie
    private boolean isInRow(int row, int number) {
        for (int i = 0; i < SIZE; i++)
            if (board[row][i] == number)
                return true;

        return false;
    }
    //sprawdzamy czy jest w kolumnie
    private boolean isInCol(int col, int number) {
        for (int i = 0; i < SIZE; i++)
            if (board[i][col] == number)
                return true;

        return false;
    }
    //sprawdzamy czy jest w kwdracie 3x3
    private boolean isInBox(int row, int col, int number) {
        int r = row - row % 3;
        int c = col - col % 3;

        for (int i = r; i < r + 3; i++)
            for (int j = c; j < c + 3; j++)
                if (board[i][j] == number)
                    return true;

        return false;
    }
    //sprawdzamy czy jest ok
    public boolean isOk(int row, int col, int number) {
        return !isInRow(row, number)  &&  !isInCol(col, number)  &&  !isInBox(row, col, number);
    }
    //solver rozwiazuje sudoku
    public boolean solve() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    for (int number = 1; number <= SIZE; number++) {
                        if (isOk(row, col, number)) {
                            board[row][col] = number;
                            if (solve()) {
                                return true;
                            } else {
                                board[row][col] = EMPTY;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}