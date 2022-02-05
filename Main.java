import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

/**
 * Sudoku
 * sudoku - przechowuje nasze sudoku które rozwiązujemy
 * toSolve przechowuje planszę która jest rozwiązywana pomocna przy restarcie, wpisywaniu żeby przypadkiem nie nadpisać liczby
 * inputMismatch spróbujemy dorobić, narazie zajeliśmy się GUI
 */
public class Main {

    public static void main(String[] args) {
        while (true) {
            Sudoku sudoku = new Sudoku();
            System.out.println("Wygenerowane;Wlasne;Exit");
            Scanner scan = new Scanner(System.in);
            String wejscie = scan.nextLine();
            while (!wejscie.equals("Wygenerowane") && !wejscie.equals("Wlasne") && !wejscie.equals("Exit")) {
                wejscie = scan.nextLine();
            }
                Grid solved = new Grid();
                Grid toSolve = new Grid();
                switch (wejscie) {
                    case "Wlasne":
                        System.out.println("Z pliku? Tak;Nie");
                        wejscie = scan.nextLine();
                        while (!wejscie.equals("Tak") && !wejscie.equals("Nie")) {
                            wejscie = scan.nextLine();
                        }
                        if (wejscie.equals("Tak")) {
                            System.out.println("Nazwa Pliku do odczytu");
                            wejscie = scan.nextLine();
                            sudoku.readFromFile(wejscie);
                            toSolve.setBoard(sudoku.getBoard());
                            sudoku.solve();
                            solved.setBoard(sudoku.getBoard());
                            sudoku.setBoard(toSolve.getBoard());
                        }
                        break;
                    case "Wygenerowane":
                        sudoku.fillDiagonal();
                        sudoku.solve();
                        solved.setBoard(sudoku.getBoard());
                        System.out.println("Hard;Medium;Easy");
                        wejscie = scan.nextLine();
                        while (!wejscie.equals("Hard") && !wejscie.equals("Medium") && !wejscie.equals("Easy")) {
                            wejscie = scan.nextLine();
                        }
                        if (wejscie.equals("Hard"))
                            sudoku.removeKDigits(55);
                        if (wejscie.equals("Medium"))
                            sudoku.removeKDigits(45);
                        if (wejscie.equals("Easy"))
                            sudoku.removeKDigits(35);
                        toSolve.setBoard(sudoku.getBoard());
                        break;
                    case "Exit":
                        System.exit(0);
                        break;
                        }
                System.out.println();
                sudoku.display();
                int flag = 0;
                int flag1 = 0; //nie wiem jak to działa ale raz petla przelatuje nic nie robiac gdy w switch wczytuje
                while (flag == 0) {
                    if (flag1 == 0) {
                        System.out.println("Wpisz;Podpowiedz;Solve;Restart;Save;Menu;Exit");
                    } else
                        flag1 = 0;
                    wejscie = scan.nextLine();

                    switch (wejscie) {
                        case "Wpisz":
                            try {
                                System.out.println("Rzad;Kolumna;Wartosc");
                                int a = scan.nextInt() - 1;
                                int b = scan.nextInt() - 1;
                                int c = scan.nextInt();
                                if (0 < c && c <= 9 && toSolve.getValue(a, b) == 0) {
                                    sudoku.setValue(a, b, c);
                                    sudoku.display();
                                } else
                                    System.out.println("Tak nie mozna");

                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("OD 1 DO 9");
                            }
                            flag1++;
                            break;
                        case "Podpowiedz":
                            try {
                                System.out.println("Rzad;Kolumna");
                                int a = scan.nextInt() - 1;
                                int b = scan.nextInt() - 1;
                                sudoku.setValue(a, b, solved.getValue(a, b));
                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("OD 1 DO 9");
                            }
                            sudoku.display();
                            flag1++;
                            break;
                        case "Restart":
                            sudoku.setBoard(toSolve.getBoard());
                            sudoku.display();
                            break;
                        case "Menu":
                            flag = 1;
                            break;
                        case "Exit":
                            System.exit(0);
                            break;
                        case "Save":
                            System.out.println("Nazwa Pliku do zapisu");
                            wejscie = scan.nextLine();
                            sudoku.saveToFile(wejscie);
                            break;
                        case "Solve":
                            if (!sudoku.solve())
                                System.out.println("Unsolved");
                            sudoku.display();
                            break;
                    }
                }
            }
        }
    }
