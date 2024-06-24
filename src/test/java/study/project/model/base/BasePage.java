package study.project.model.base;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {
    protected static WebDriver driver;
    @FindBy(id = "search-box")
    private WebElement headerSearchBox;


    public BasePage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    public <T> T headerSearch(String requestName, T page) {
        headerSearchBox.sendKeys(requestName + Keys.ENTER);
        return page;

    }
}
