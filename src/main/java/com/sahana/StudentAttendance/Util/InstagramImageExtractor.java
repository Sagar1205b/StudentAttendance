package com.sahana.StudentAttendance.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InstagramImageExtractor {

    public static String getProfileImageUrl(String profileUrl) throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--headless=new"); // Optional if you want it hidden

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://mollygram.com/");

            // Wait for the input box
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
            WebElement inputBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Enter username or link']")));
            inputBox.sendKeys(profileUrl);
            try {
                Thread.sleep(1000); // Wait for 1 second (adjust as needed)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.downbtn.bg-gradient-primary")));
            submitButton.click();

            // Wait for avatar image to load
            WebElement avatarImage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("img.rounded-circle")));

            String imageUrl = avatarImage.getAttribute("src");
            return imageUrl;

        } catch (Exception e) {
            throw new Exception("Failed to scrape image from molly gram: " + e.getMessage(), e);
        } finally {
            driver.quit();
        }
    }
}
