import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class AvicTests {

    private WebDriver driver;

    @BeforeTest
    public void profileSetUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    }

    @BeforeMethod
    public void testSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://avic.ua/");
    }

    @Test(priority = 1)
    public void checkAddingFirstProdInBasketandDelete() {
        List<WebElement> elements;
        elements = driver.findElements(By.xpath("//span[@class='sidebar-item-title']"));
        elements.get(0).click();
        elements = driver.findElements(By.xpath("//div[@class='brand-box__title']/a"));
        elements.get(0).click();
        elements = driver.findElements(By.xpath("//a[@class='prod-cart__buy']"));
        elements.get(0).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertTrue(driver.findElement(By.xpath
                ("(//div[@class='cart-parent-limit']/div[@class='item'])")).isEnabled());
    }

    @Test(priority = 3)
    public void checkEmptyBasket() {
        driver.findElement(By.xpath
                ("//div[@class='header-bottom__right-icon']/i[@class='icon icon-cart-new']")).click();
        String textBasket = driver.findElement(By.xpath("//div[@id='js_cart']//b")).getText();
        assertEquals(textBasket, "Корзина пустая!");
    }

    @Test(priority = 2)
    public void checkVisibleMessageIfEmptySearch() {
        driver.findElement(By.xpath("//button[@class='button-reset search-btn']")).click();
        assertTrue(driver.findElement(By.xpath("//div[@id='modalAlert']")).isDisplayed());
    }

    @Test(priority = 2)
    public void checkAllDiscountProdHaveIconDiscount() {
        driver.findElement(By.xpath
                ("//div[@class='top-links__left flex-wrap']//a[contains(@href,'discount')]")).click();
        List<WebElement> elementList;
        elementList = driver.findElements(By.xpath("//div[@class='item-prod col-lg-3']"));
        int countProducts = elementList.size();
        elementList = driver.findElements(By.xpath("//img[contains(@data-src,'utsenka')]"));
        int countDiscounts = elementList.size();
        assertEquals(countDiscounts, countProducts);
    }

    @AfterMethod
    public void testDown() {
        driver.quit();
    }
}
