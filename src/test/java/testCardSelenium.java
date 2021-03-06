import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.By.cssSelector;

public class testCardSelenium {
    private WebDriver driver;

//    @BeforeAll
//    static void setUpAll() {
//        System.setProperty("webdriver.chrome.driver", "driver/tmp/chromedriver.exe");
//    }
    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setupTest() {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            options.addArguments("disable-infobars");
            options.addArguments("--headless");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");
            driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    //Положительное
    @Test
    void shouldPositivScript() {
        driver.get("http://localhost:9999");
        //List<WebElement> elements =driver.findElements(By.className("input__control"));
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79115111111");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Сергей Крылов");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualMessege = driver.findElement(cssSelector(".paragraph")).getText();
        String expectedMessege = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expectedMessege, actualMessege.trim());
    }
//английские символы в фамилии

    @Test
    void shouldNegativScript() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79115111111");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Sergey");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualMessege = driver.findElement(cssSelector(".input_type_text > .input__inner > .input__sub")).getText();
        String expectedMessege = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expectedMessege, actualMessege.trim());

    }

    //неправильный номер телефона
    @Test
    void shouldNegativScriptBadPhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+791151111111");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Сергей Крылов");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualMessege = driver.findElement(cssSelector(".input_type_tel > .input__inner > .input__sub")).getText();
        String expectedMessege = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expectedMessege, actualMessege.trim());

    }

    @Test
    void shouldEmptyFieldName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualMessege = driver.findElement(cssSelector(".input_type_text > .input__inner > .input__sub")).getText();
        String expectedMessege = "Поле обязательно для заполнения";
        assertEquals(expectedMessege, actualMessege.trim());

    }

    //пустое поле телефона
    @Test
    void shouldEmptyFieldPhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Сергей Крылов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualMessege = driver.findElement(cssSelector(".input_type_tel > .input__inner > .input__sub")).getText();
        String expectedMessege = "Поле обязательно для заполнения";
        assertEquals(expectedMessege, actualMessege.trim());
    }
    @Test
    void shouldCheckboxEmpty() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Сергей Крылов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+76666658666");
        driver.findElement(By.cssSelector(".button")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector(".input_invalid .checkbox__text")).getText();
         assertEquals(expected, actual.trim());
    }
}
