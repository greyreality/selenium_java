package ru.st.selenium;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.*;
import org.testng.annotations.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

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
    public String MovieName;
    private static final String SEARCH_INPUT = ".//*[@id='q']";
    private static final String EMPTY_SEARCH_RESULT = ".//*[@id='results']/div[1]";
    private static final String EMPTY_RESULT_MESSAGE = "No movies where found.";
    private static final String NOT_EMPTY_SEARCH_RESULT ="//*[contains(@class, 'title')]";

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
    @Test(description = "delete movie from list",dependsOnMethods = "AddNewMovie")
    public void DeleteMovie() throws Exception {
        driver.findElements(By.xpath(".//*[contains(@class, 'title')]")).get(0).click();
        Thread.sleep(100);
        /*pop-up*/
        driver.findElement(By.linkText("Remove")).click();
        assertTrue(closeAlertAndGetItsText().matches("^Are you sure you want to remove this[\\s\\S]$"));
    }
    @Test (description = "empty search results",dependsOnMethods = "DeleteMovie")
    public void SearchRandomMovie() throws Exception {
        MovieName = "MOVIE_" + RandomStringGenerator.generateRandomString(10, RandomStringGenerator.Mode.ALPHA);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(MovieName);
        clipboard.setContents(selection, selection);
        driver.findElement(By.xpath(SEARCH_INPUT)).sendKeys(Keys.CONTROL + "v");
        driver.findElement(By.xpath(SEARCH_INPUT)).sendKeys(Keys.ENTER);
        Thread.sleep(100);
        String message = driver.findElement(By.xpath(EMPTY_SEARCH_RESULT)).getText();
        Assert.assertTrue(message.equals(EMPTY_RESULT_MESSAGE),"result is not empty");
    }

    @Test (description = "not empty search results")
    public void SearchSpecifiedMovie() throws Exception {
        /*add movie to list*/
        driver.findElement(By.xpath(ADD_MOVIE)).click();
        Movie SearchMovie = new Movie();
        driver.findElement(By.xpath(TITLE)).sendKeys(SearchMovie.GetTitle());
        driver.findElement(By.xpath(YEAR)).sendKeys(SearchMovie.GetYear());
        driver.findElement(By.xpath(SECOND_NAME)).sendKeys(SearchMovie.SecondName());
        driver.findElement(By.xpath(SUBMIT_BUTTON)).click();
        AdminHome();

        MovieName = SearchMovie.GetTitle();
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(MovieName);
        clipboard.setContents(selection, selection);
        driver.findElement(By.xpath(SEARCH_INPUT)).sendKeys(Keys.CONTROL + "v");
        driver.findElement(By.xpath(SEARCH_INPUT)).sendKeys(Keys.ENTER);
        Thread.sleep(100);
        String message = driver.findElement(By.xpath(NOT_EMPTY_SEARCH_RESULT)).getText();
        Assert.assertTrue(message.equals(MovieName),"you find wrong movie");
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