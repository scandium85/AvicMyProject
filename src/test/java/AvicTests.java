import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class AvicTests {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void profileSetUp(){
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
    }

    @BeforeMethod
    public void testSetUp(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://avic.ua/");
    }

    @Test(priority = 1)
    public void checkAddingFirstProdInBasketandDelete(){
        List<WebElement> elements;
        elements = driver.findElements(By.xpath("//span[@class='sidebar-item-title']"));
        elements.get(0).click();
        elements = driver.findElements(By.xpath("//div[@class='brand-box__title']/a"));
        elements.get(0).click();
        elements = driver.findElements(By.xpath("//a[@class='prod-cart__buy']"));
        elements.get(0).click();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        assertTrue(driver.findElement(By.xpath("(//div[@class='cart-parent-limit']/div[@class='item'])")).isEnabled());
    }

    @Test(priority = 3)
    public void checkEmptyBasket(){
        driver.findElement(By.xpath("//div[@class='header-bottom__right-icon']/i[@class='icon icon-cart-new']")).click();
        String textBasket = driver.findElement(By.xpath("//div[@id='js_cart']//b")).getText();
        assertEquals(textBasket,"Корзина пустая!");
    }

    @Test(priority = 2)
    public void checkVisibleMessageIfEmptySearch(){
        driver.findElement(By.xpath("//button[@class='button-reset search-btn']")).click();
        assertTrue(driver.findElement(By.xpath("//div[@id='modalAlert']")).isDisplayed());
    }

    @AfterTest
    public void testDonw(){
        driver.quit();
    }
}
