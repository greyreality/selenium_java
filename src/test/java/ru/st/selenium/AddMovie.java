package ru.st.selenium;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.*;
import org.testng.annotations.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;

public class AddMovie extends ru.st.selenium.TestBase {
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    public WebDriverWait wait;
    public WebElement element;
    private static final String ADD_MOVIE =".//*[@id='content']/section/nav/ul/li[1]/div/div/a";
    private static final String TITLE =".//*[@id='updateform']/table/tbody/tr[2]/td[2]/input";
    private static final String YEAR = ".//*[@id='updateform']/table/tbody/tr[4]/td[2]/input";
    private static final String FORMAT = ".//*[@id='formats']";
    private static final String SECOND_NAME = ".//*[@id='updateform']/table/tbody/tr[3]/td[2]/textarea";
    private static final String SUBMIT_BUTTON = ".//*[@id='submit']";
    private static final String INVALID_MESSAGE = "This field is required";
    private static final String FAKE_INVALID = "Lol";
    private static final String LABEL_YEAR = ".//*[@id='updateform']/table/tbody/tr[4]/td[2]/label";
    private static final String LABEL_TITLE = ".//*[@id='updateform']/table/tbody/tr[2]/td[2]/label";
    private static final String LABEL_FORMAT = ".//*[@id='updateform']/table/tbody/tr[7]/td[2]/label";
    private static final String DELETE_BUTTON = ".//*[@id='content']/section/nav/ul/li[2]/div/div/a";

    @Test
        public void AddNewMovie() throws Exception {
        driver.findElement(By.xpath(ADD_MOVIE)).click();
        Movie NewMovie = new Movie();
        driver.findElement(By.xpath(TITLE)).sendKeys(NewMovie.GetTitle());
        driver.findElement(By.xpath(YEAR)).sendKeys(NewMovie.GetYear());
        driver.findElement(By.xpath(SECOND_NAME)).sendKeys(NewMovie.SecondName());
        driver.findElement(By.xpath(SUBMIT_BUTTON)).click();
        AdminHome();
    }
    @Test
    public void AddNewMovieValidation() throws Exception {
        Thread.sleep(100);
        driver.findElement(By.xpath(ADD_MOVIE)).click();
        Thread.sleep(100);
        driver.findElement(By.xpath(FORMAT)).clear();
        driver.findElement(By.xpath(SUBMIT_BUTTON)).click();
        Thread.sleep(100);
        /*проверка сообщения об обязательном поле Год*/
        String message = driver.findElement(By.xpath(LABEL_YEAR)).getText();
        Assert.assertTrue(message.equals(INVALID_MESSAGE),"bad validation");
        /*проверка сообщения об обязательном поле Название*/
        message = driver.findElement(By.xpath(LABEL_TITLE)).getText();
        Assert.assertTrue(message.equals(INVALID_MESSAGE),"bad validation");
        /*проверка сообщения об обязательном поле Формат*/
        message = driver.findElement(By.xpath(LABEL_FORMAT)).getText();
        Assert.assertTrue(message.equals(INVALID_MESSAGE),"bad validation");
        AdminHome();
    }
    @Test
    public void DeleteMovie() throws Exception {
        driver.findElements(By.xpath(".//*[contains(@class, 'title')]")).get(0).click();
        Thread.sleep(100);
        /*pop-up*/
        driver.findElement(By.linkText("Remove")).click();
        assertTrue(closeAlertAndGetItsText().matches("^Are you sure you want to remove this[\\s\\S]$"));
    }
    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}