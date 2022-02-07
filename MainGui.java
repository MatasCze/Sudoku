import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class MainGui implements ActionListener{

    JFrame f;
    JPanel board;
    JMenuBar mb;
    JMenu menu, generate;
    JMenuItem hard, medium, easy, save, load;
    Sudoku sudoku = new Sudoku();
    Grid solved = new Grid();
    Grid toSolve = new Grid();
    JTextField[][] cell = new JTextField[9][9];
    Font font = new Font(Font.MONOSPACED, Font.BOLD, 20);
    JButton hintb;
    JButton restartb;
    JButton solveb;
    JButton tryb;

    /**
     * konstruktor
     */
    MainGui(){
        f = new JFrame("Sudoku");
        board= new JPanel();

        //plansza sudoku

        board.setLayout(new GridLayout(9,9));
        board.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        for (int row = 0; row<9;row ++){
            for (int col = 0; col < 9; col++){
                cell[row][col] = new JTextField();
                cell[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cell[row][col].setFont(font);
                cell[row][col].setEditable(false);
                board.add(cell[row][col]);
                if(row%3==2 && row!= 8){
                    cell[row][col].setBorder(BorderFactory.createMatteBorder(1,1,4,1,Color.BLACK));
                }
                if(col%3==2 && col!= 8){
                    cell[row][col].setBorder(BorderFactory.createMatteBorder(1,1,1,4,Color.BLACK));
                }
                if(row%3==2 && col%3==2 && row != 8 && col!=8){
                    cell[row][col].setBorder(BorderFactory.createMatteBorder(1,1,4,4,Color.BLACK));
                }


            }
        }


        //siatka gridbacklayout
        f.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 3;
        c.weightx = 4;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 4;

        //Przyciski na dole okna
        f.add(board, c);
        hintb = new JButton("Podpowiedź");
        hintb.addActionListener(this);
        restartb = new JButton("Restart");
        restartb.addActionListener(this);
        solveb = new JButton("Rozwiąż");
        solveb.addActionListener(this);
        tryb = new JButton("Sprawdź");
        tryb.addActionListener(this);
        c.gridwidth = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        f.add(hintb, c);
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        f.add(tryb, c);
        c.gridx = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        f.add(restartb, c);
        c.gridx = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        f.add(solveb, c);






        


        mb = new JMenuBar();
        menu=new JMenu("Menu");
        generate=new JMenu("Generuj");
        load=new JMenuItem("Załaduj");
        load.addActionListener(this);
        save=new JMenuItem("Zapisz");
        save.addActionListener(this);
        easy=new JMenuItem("Latwy");
        easy.addActionListener(this);
        medium=new JMenuItem("Sredni");
        medium.addActionListener(this);
        hard=new JMenuItem("Trudny");
        hard.addActionListener(this);
        menu.add(load);menu.addSeparator(); menu.add(save);menu.addSeparator();
        generate.add(easy);generate.addSeparator(); generate.add(medium);generate.addSeparator(); generate.add(hard);
        menu.add(generate);
        mb.add(menu);
        f.setJMenuBar(mb);
        f.setSize(400,400);
        f.setVisible(true);

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MainGui)) return false;
        MainGui that = (MainGui) o;
        return Arrays.equals(cell, that.cell);
    }


    @Override
    public int hashCode() {
        return Arrays.hashCode(cell);
    }
    Thread thread = new Thread(() -> {
        sudoku.solve();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (!cell[row][col].getText().equals(String.valueOf(sudoku.getValue(row, col)))) {
                    cell[row][col].setForeground(Color.RED);
                }
            }
        }
        try {
            Thread.sleep(900);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (!cell[row][col].getText().equals(String.valueOf(sudoku.getValue(row, col)))) {
                    cell[row][col].setForeground(Color.BLACK);
                }
            }
        }
    });

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==easy)
        {	
        	for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
            	cell[row][col].setEditable(false);
            }
    	}
            sudoku = new Sudoku();
            solved = new Grid();
            toSolve = new Grid();
            sudoku.fillDiagonal();
            sudoku.solve();
            solved.setBoard(sudoku.getBoard());
            sudoku.removeKDigits(25);
            toSolve.setBoard(sudoku.getBoard());
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    cell[row][col].setText(String.valueOf(sudoku.board[row][col]));
                    if (cell[row][col].getText().equals("0")){
                        cell[row][col].setText("");
                        cell[row][col].setEditable(true);
                    }
                }
            }

        }
        if (e.getSource()==medium)
        {	
        	for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                	cell[row][col].setEditable(false);
                }
        	}
            sudoku = new Sudoku();
            solved = new Grid();
            toSolve = new Grid();
            sudoku.fillDiagonal();
            sudoku.solve();
            solved.setBoard(sudoku.getBoard());
            sudoku.removeKDigits(35);
            toSolve.setBoard(sudoku.getBoard());
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    cell[row][col].setText(String.valueOf(sudoku.board[row][col]));
                    if (cell[row][col].getText().equals("0")){
                        cell[row][col].setText("");
                        cell[row][col].setEditable(true);
                    }
                }
            }
        }
        if (e.getSource()==hard)
        {	
        	for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                	cell[row][col].setEditable(false);
                }
        	}
            sudoku = new Sudoku();
            solved = new Grid();
            toSolve = new Grid();
            sudoku.fillDiagonal();
            sudoku.solve();
            solved.setBoard(sudoku.getBoard());
            sudoku.removeKDigits(41);
            toSolve.setBoard(sudoku.getBoard());
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    cell[row][col].setText(String.valueOf(sudoku.board[row][col]));
                    if (cell[row][col].getText().equals("0")){
                        cell[row][col].setText("");
                        cell[row][col].setEditable(true);
                    }
                }
            }
        }

        if(e.getSource()==hintb){
            sudoku.solve();
            Random gen = new Random();
            for (int a = 0; a < 81;a++) {
                int i = gen.nextInt(9);
                int j = gen.nextInt(9);
                if (cell[i][j].getText().equals("") || !cell[i][j].getText().equals(String.valueOf(sudoku.getValue(i,j)))) {
                    cell[i][j].setText(String.valueOf(sudoku.getValue(i, j)));
                    cell[i][j].setEditable(false);
                    a= 100;
                }
            }

        }
        if(e.getSource()==tryb){
           // SwingUtilities.invokeLater(new Runnable() {
             //   @Override
               // public void run() {
                 //   thread.start();
              //  }
            //});
            sudoku.solve();
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    if(!cell[row][col].getText().equals(sudoku.getValue(row,col))&& !cell[row][col].getText().equals("")) {
                        cell[row][col].setText(String.valueOf(sudoku.getValue(row, col)));
                        cell[row][col].setEditable(false);
                    }
                }
            }
        }
        if(e.getSource()==solveb){
            if (!sudoku.solve())
                System.out.println("Unsolved");
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    cell[row][col].setText(String.valueOf(sudoku.getValue(row,col)));
                    cell[row][col].setEditable(false);
                }
            }
        }
        if(e.getSource()==restartb){
            sudoku.setBoard(toSolve.getBoard());
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    cell[row][col].setText(String.valueOf(sudoku.getValue(row,col)));
                    if (cell[row][col].getText().equals("0")) {
                        cell[row][col].setText("");
                        cell[row][col].setEditable(true);
                    }

                }
            }
        }
        if(e.getSource()==load) {
            File file = new File("SudokuSave1.txt");
            Scanner scanner = null;
            try {
                scanner = new Scanner(file);
                for(int i=0;i<9;i++){
                    for(int j=0;j<9;j++){
                        if(scanner.hasNext()) {
                            cell[i][j].setText(String.valueOf(scanner.next()));
                            cell[i][j].setEditable(false);
                            if (cell[i][j].getText().equals("0")){
                                cell[i][j].setText(" ");
                                cell[i][j].setEditable(true);
                            }
                        }
                        else {
                            cell[i][j].setText("");
                        }
                    }
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Plik nie istnieje");
            } catch (InputMismatchException ex){
                System.out.println("Zle dane");
            }
        }

        if(e.getSource()==save) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter("SudokuSave1.txt");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            for (int i=0;i<9;i++){
                for (int j=0;j<9;j++){
                	if (cell[i][j].getText().equals("")) {
                		writer.print(sudoku.board[i][j] + " ");
                		}
                	else {
                		writer.print(cell[i][j].getText() + " ");
                	}

                }
                writer.println();
            }
            writer.close();
        }

    }

    public static void main(String[] args) {
        new MainGui();

    }
}