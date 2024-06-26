package study.project.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseProjectPage<T extends BasePage> extends BasePage {
    @FindBy(tagName = "h1")
    private WebElement projectName;

    @FindBy(css = ".error")
    private WebElement error;

    public BaseProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectName() {

        return projectName.getText();
    }

    public String getErrorText() {

        return error.getText();
    }
}
