package org.umm.service;

import org.openqa.selenium.WebDriver;
import org.umm.solver.SudokuSolver;
import org.umm.web.SudokuWebPage;

public class SudokuService {
    private final WebDriver driver;
    private final SudokuSolver solver;
    private final String webPageUrl = "https://www.livesudoku.com/es/sudoku/";
    private final String difficulty = "evil/"; // easy/ << medium/ << hard/ << evil/

    public SudokuService(WebDriver driver, SudokuSolver solver) {
        this.driver = driver;
        this.solver = solver;
    }

    public void run() throws InterruptedException {
        SudokuWebPage page = new SudokuWebPage(driver);
        page.openAndAcceptCookies(webPageUrl + difficulty);
        Thread.sleep(1000);

        int[][] board = page.readSudoku();

        System.out.println("Sudoku original:");
        printBoard(board);

        if (solver.solve(board)) {
            System.out.println("Sudoku solved:");
            printBoard(board);
            page.fillSudoku(board);
        } else {
            System.out.println("Sudoku no resuelto.");
        }
    }

    private void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell == 0 ? "[ ]" : "[" + cell + "]");
            }
            System.out.println();
        }
    }
}
