package study.project.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import study.project.model.base.BasePage;

public class CreateitemPage extends BasePage {
    @FindBy(xpath = "//p")
    WebElement errorMessage;

    @FindBy(xpath = "//div/h2")
    WebElement altErrorMessage;

    public CreateitemPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public String getAltErrorMessage() {
        return altErrorMessage.getText();
    }
}
