package com.Project.Scrapers;

import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;





public class BourseScraper {


    public List<String[]> BScraper() {

        System.setProperty("webdriver.edge.driver", "C:/Users/chbab/Desktop/msedgedriver.exe");
        WebDriver driver = new EdgeDriver();

        try {
            // Navigate to the website
            driver.get("https://www.casablanca-bourse.com/fr/instruments");

            // Detection and refuse cookies:
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                // Wait for the cookie button to be visible before attempting to click
                WebElement cookies = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button[contains(text(), 'Je refuse')]")
                ));
                cookies.click(); // Click the button
            } catch (NoSuchElementException e) {
                System.out.println("Cookie button not found. Proceeding without clicking.");
            } catch (TimeoutException e) {
                System.out.println("Cookie button not found in time. Proceeding without clicking.");
            }

            // -> Instrument input field
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement instrumentInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("headlessui-combobox-input-:R1mida66:")
            ));
            instrumentInput.click();
            instrumentInput.sendKeys("AKDITAL");

            // Wait for the dropdown to appear
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@role='listbox']")));

            // Optionally, you can also select the value from the dropdown programmatically
            List<WebElement> options = driver.findElements(By.xpath("//ul[@role='listbox']/li"));
            for (WebElement option : options) {
                if (option.getText().contains("AKDITAL")) {
                    option.click();
                    break;
                }
            }

            // Select a value in the period dropdown
            Select periodDropdown = new Select(driver.findElement(By.id("range-date")));
            periodDropdown.selectByValue("1");



            // Click the "Appliquer" button
            // Example for closing an overlay/modal if it exists
            List<WebElement> overlays = driver.findElements(By.xpath("//div[contains(@class, 'overlay-class')]"));
            if (!overlays.isEmpty()) {
                overlays.get(0).click();  // Close the overlay/modal
            }

            // Wait for the "Appliquer" button to become clickable
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement applyButton = wait1.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(), 'Appliquer')]")
            ));
            applyButton.click();

            // Wait for the table to load
            Thread.sleep(5000);

            // Extract the generated table
            WebElement table = driver.findElement(By.xpath("//table"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            List<String[]> tableData = new ArrayList<>();


            for (WebElement row : rows) {
                // Get all the cells in the row
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (cells.size() == 11) {
                    String[] rowData = new String[11];
                    for (int i = 0; i < 11; i++) {
                        rowData[i] = cells.get(i).getText();
                    }

                    tableData.add(rowData);
                }
            }

            return tableData;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // Close the browser
            driver.quit();
        }
    }


}
