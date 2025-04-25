package org.umm;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Sudoku solver by Unai
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        System.out.println("Starting driver...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(2000));


        // Aceptar cookies
        try {
            driver.get("https://www.livesudoku.com/es/sudoku/easy/");
            try {
                WebElement cookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".fc-button-label")));
                cookiesButton.click();
                System.out.println("Cookies accepted");
            } catch (TimeoutException e) {
                System.out.println("Cookies not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(1000);






        // Buscar el cuadrado del sudoku y mapear los datos
        WebElement sudokuSquare = driver.findElement(By.cssSelector("table.width400desktop"));
        List<WebElement> rows = sudokuSquare.findElements(By.cssSelector("tr"));
        int[][] sudoku = new int[9][9];

        for (int i = 0; i < 9; i++) {
            List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
            for (int j = 0; j < 9; j++) {
                String text = cells.get(j).getText().trim();

                sudoku[i][j] = text.isEmpty() ? 0 : Integer.parseInt(text);
            }
        }

        System.out.println("Sudoku mapped:");

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(sudoku[i][j] + " ");
            }
            System.out.println();
        }






        // Solucionar el sudoku
        if (solveSudoku(sudoku)) {
            System.out.println("Sudoku solved:");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    System.out.print(sudoku[i][j] + " ");
                }
                System.out.println();
            }
        } else {
            System.out.println("Sudoku not solved");
        }






        // Rellenar la tabla
        Actions actions = new Actions(driver);
        WebElement firstCell = sudokuSquare.findElement(By.tagName("td"));
        firstCell.click();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String num = String.valueOf(sudoku[i][j]);
                actions.sendKeys(num).perform();
                actions.sendKeys("\u0009").perform();
            }
        }
        System.out.println("Sudoku completed");




        System.out.println("Quitting...");
    }

    private static boolean solveSudoku(int[][] sudoku) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudoku[row][col] == 0) {
                    for (int k = 1; k <= 9; k++) {
                        if (isValid(sudoku, row, col, k)) {
                            sudoku[row][col] = k;

                            if (solveSudoku(sudoku)) {
                                return true;
                            }

                            sudoku[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValid(int[][] sudoku, int row, int col, int k) {
        for (int i = 0; i < 9; i++) {
            if (sudoku[row][i] == k) {
                return false;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (sudoku[i][col] == k) {
                return false;
            }
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (sudoku[startRow + i][startCol + j] == k) {
                    return false;
                }
            }
        }

        return true;
    }
}
