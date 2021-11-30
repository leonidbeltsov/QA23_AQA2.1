package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

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
//    public void test_000() {
        public void shouldShowWarningMassageWithoutEverything() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".button__text")).click();
        String textUnderName = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", textUnderName.trim());
    }


    @Test
//    public void test_000v2() {
    public void shouldShowWarningMassageWithoutEverythingThroughWebElement() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".button__text")).click();
        List<WebElement> elements = driver.findElements(By.cssSelector(".input_invalid .input__sub"));
        String warningTextUnderName = elements.get(0).getText();
        assertEquals("Поле обязательно для заполнения", warningTextUnderName.trim());
//        String warningTextUnderPhone = elements.get(1).getText();
//        assertEquals("Поле обязательно для заполнения", warningTextUnderPhone.trim());
//        String checkboxWarningText = elements.get(2).getText();
//        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", checkboxWarningText.trim());
    }

    @Test
//    public void test_001() {
        public void shouldShowWarningMassageWithCheckboxOnly() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String textUnderName = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", textUnderName.trim());
    }

    @Test
//    public void test_010() {
        public void shouldShowWarningMassageWithPhoneOnly() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='phone'] [name='phone']")).sendKeys("+79990003322");
        driver.findElement(By.cssSelector(".button__text")).click();
        String textUnderName = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", textUnderName.trim());
    }

    @Test
//    public void test_011() {
        public void shouldShowWarningMassageWithPhoneAndCheckBox() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='phone'] [name='phone']")).sendKeys("+79993332211");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String textUnderName = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", textUnderName.trim());
    }

    @Test
//    public void test_100() {
        public void shouldShowWarningMassageWithNameOnly() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] [name='name']")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector(".button__text")).click();
        String textUnderPhone = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", textUnderPhone.trim());
    }

    @Test
//    public void test_101() {
        public void shouldShowWarningMassageWithNameAndCheckbox() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] [name='name']")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String textUnderName = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", textUnderName.trim());
    }

    @Test
//    public void test_110() {
        public void shouldShowWarningMassageWithNameAndPhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] [name='name']")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] [name='phone']")).sendKeys("+79000000000");
        driver.findElement(By.cssSelector(".button__text")).click();
        String checkboxWarningText = driver.findElement(By.cssSelector(".input_invalid .checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", checkboxWarningText.trim());
    }

    @Test
    public void shouldShowWarningMassageWithEnglishNameOnly() {
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