package org.umm;

import org.umm.driver.WebDriverFactory;
import org.umm.solver.BacktrackingSolver;
import org.umm.service.SudokuService;
import org.openqa.selenium.WebDriver;

public class App {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = WebDriverFactory.create();
        SudokuService service = new SudokuService(driver, new BacktrackingSolver());

        long startTime = System.currentTimeMillis();

        service.run();

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + "ms");

        driver.quit();
    }
}
