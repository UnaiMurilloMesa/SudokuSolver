package org.umm.web;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.*;

public class SudokuWebPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private WebElement table;

    public SudokuWebPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public void openAndAcceptCookies(String url) {
        driver.get(url);
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".fc-button-label")));
            button.click();
        } catch (TimeoutException ignored) {}
    }

    public int[][] readSudoku() {
        table = driver.findElement(By.cssSelector("table.width400desktop"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        int[][] board = new int[9][9];

        for (int i = 0; i < 9; i++) {
            List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
            for (int j = 0; j < 9; j++) {
                String text = cells.get(j).getText().trim();
                board[i][j] = text.isEmpty() ? 0 : Integer.parseInt(text);
            }
        }
        return board;
    }

    public void fillSudoku(int[][] board) {
        Actions actions = new Actions(driver);
        WebElement firstCell = table.findElement(By.tagName("td"));
        firstCell.click();

        for (int[] row : board) {
            for (int cell : row) {
                actions.sendKeys(String.valueOf(cell)).perform();
                actions.sendKeys(Keys.TAB).perform();
            }
        }
    }
}
