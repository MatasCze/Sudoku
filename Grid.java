import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JTextField;

public class Grid {
    public int[][] board;
    private static final int SIZE = 9;
    public int[][] getBoard() {
        return board;
    }

    public int getValue(int a,int b) {
        return board[a][b];
    }

    public void setValue(int a,int b,int c) {
        board[a][b]=c;
    }

    public Grid() {
        this.board = new int[SIZE][SIZE];
    }

    public void setBoard(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.board[i][j] = board[i][j];
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grid grid = (Grid) o;
        return Arrays.equals(board, grid.board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }
    //wypisuje sudoku
    public void display(int[][] A) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(" "+board[i][j]);
                if (j%3==2){
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i%3==2){
                for (int k=0;k<7;k++){
                    System.out.print("---");
                }
                System.out.println();
            }
        }
        System.out.println();
    }


    /**
     * wczytuje z pliku
     */
    public void readFromFile(String nazwaPliku){
        File file = new File(nazwaPliku);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            for(int i=0;i<SIZE;i++){
                for(int j=0;j<SIZE;j++){
                    if(scanner.hasNext())
                        board[i][j]=scanner.nextInt();
                    else
                        board[i][j]=0;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Plik nie istnieje");
        } catch (InputMismatchException e){
            System.out.println("Zle dane");
        }
    }

    /**
     * zapisuje do pliku
     */
    public void saveToFile(String nazwaPliku) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(nazwaPliku);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i=0;i<SIZE;i++){
            for (int j=0;j<SIZE;j++){
                writer.print(board[i][j]+" ");
            }
            writer.println();
        }
        writer.close();
    }

    /**
     * wypisuje
     */
    public void display() {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    System.out.print(" "+board[i][j]);
                    if (j%3==2){
                        System.out.print("|");
                    }
                }
                System.out.println();
                if (i%3==2){
                    System.out.print("---------------------");
                    System.out.println();
                }
            }
            System.out.println();
    }
}
