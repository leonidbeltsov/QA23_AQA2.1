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

public class HappyPathTest {
    private WebDriver driver;


    @BeforeAll
    public static void setUpWebDriver() {
        WebDriverManager.chromedriver().setup();
    }

    // Перед каждым тестом будет создавать новый web driver, т.е. будет запускать браузер
    @BeforeEach
    public void setDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");             // Запуск браузера без графического интерфейса
//        options.addArguments("--start-maximized");      // Запуск в режиме максимального окна
        options.addArguments("--incognito");            // Запуск браузера в режиме incognito
        driver = new ChromeDriver(options);
    }

    @Test
    public void shouldUseCssSelectors() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] [name='name']")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] [name='phone']")).sendKeys("+79993332211");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }


    // После каждого теста браузер будет закрываться
    @AfterEach
    public void closeDriver() {
        driver.quit();
        driver = null;
    }
}
