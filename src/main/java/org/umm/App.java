package org.umm;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
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

        Thread.sleep(2000);






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

        System.out.println("Quitting...");
    }
}
