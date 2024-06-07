package study.project.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import study.project.model.base.BasePage;

public class FreeStyleProjectConfigPage extends BasePage {
    @FindBy(name = "Submit")
    WebElement saveButton;

    public FreeStyleProjectConfigPage(WebDriver driver) {
        super(driver);
    }

    public FreeStyleProjectPage saveProject() {
        saveButton.click();

        return new FreeStyleProjectPage(driver);
    }
}
