package study.project.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import study.project.model.base.BasePage;

public class FreeStyleProjectPage extends BasePage {
    @FindBy(xpath = "//h1")
    WebElement projectName;

    public FreeStyleProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectName() {
        return projectName.getText();
    }
}
