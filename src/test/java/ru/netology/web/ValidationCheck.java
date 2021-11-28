package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidationCheck {
    private WebDriver driver;


    @BeforeAll
    public static void setUpWebDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");     // Запуск браузера без графического интерфейса
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
    }

    @Test
    public void shouldShowWarningMassageWithoutEverything() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".button__text")).click();
        String textUnderName = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", textUnderName.trim());
    }

    @Test
    public void shouldShowWarningMassageWithOnlyName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] [name='name']")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector(".button__text")).click();
        String textUnderPhone = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", textUnderPhone.trim());
    }

    @Test
    public void shouldShowWarningMassageWithOnlyPhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='phone'] [name='phone']")).sendKeys("+79990003322");
        driver.findElement(By.cssSelector(".button__text")).click();
        String textUnderName = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", textUnderName.trim());
    }



    @Test
    public void shouldShowWarningMassageWithNameAndCheckbox() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] [name='name']")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String textUnderName = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", textUnderName.trim());
    }

    @Test
    public void shouldShowWarningMassageWithEnglishName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] [name='name']")).sendKeys("Sidorov Pavel");
        driver.findElement(By.cssSelector(".button__text")).click();
        String textUnderName = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", textUnderName.trim());
    }

    @AfterEach
    public void closeDriver() {
        driver.quit();
        driver = null;
    }
}