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

    @AfterEach
    public void closeDriver() {
        driver.quit();
        driver = null;
    }

    @Test
        public void shouldShowWarningMassageWithoutName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='phone'] [name='phone']")).sendKeys("+79993332211");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String textUnderName = driver.findElement(By.cssSelector(".input_invalid[data-test-id='name'] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", textUnderName.trim());
    }


    @Test
        public void shouldShowWarningMassageWithoutPhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] [name='name']")).sendKeys("Иванов Святослав");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String textUnderName = driver.findElement(By.cssSelector(".input_invalid[data-test-id='phone'] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", textUnderName.trim());
    }

    @Test
        public void shouldShowWarningMassageWithoutCheckbox() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] [name='name']")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] [name='phone']")).sendKeys("+79000000000");
        driver.findElement(By.cssSelector(".button__text")).click();
        String checkboxWarningText = driver.findElement(By.cssSelector(".input_invalid .checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", checkboxWarningText.trim());
    }

    @Test
    public void shouldShowWarningMassageWithIncorrectName(){
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] [name='name']")).sendKeys("Petrov Petr");
        driver.findElement(By.cssSelector("[data-test-id='phone'] [name='phone']")).sendKeys("+79998887766");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String textUnderName = driver.findElement(By.cssSelector(".input_invalid[data-test-id='name'] .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", textUnderName.trim());
    }

    @Test
    public void shouldShowWarningMassageWithIncorrectPhone(){
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] [name='name']")).sendKeys("Бобл Александр");
        driver.findElement(By.cssSelector("[data-test-id='phone'] [name='phone']")).sendKeys("89008887766");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String textUnderName = driver.findElement(By.cssSelector(".input_invalid[data-test-id='phone'] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", textUnderName.trim());
    }
}