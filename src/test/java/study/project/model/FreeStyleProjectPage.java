package study.project.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import study.project.model.base.BasePage;
import study.project.model.base.BaseProjectPage;

public class FreeStyleProjectPage extends BaseProjectPage<FreeStyleProjectPage> {
    @FindBy(xpath = "//*[contains(text(), 'Disable Project')]")
    WebElement disableProjectButton;

    @FindBy(id = "enable-project")
    WebElement disableProjectMessage;

    public FreeStyleProjectPage disableFreeStyleProject() {
        disableProjectButton.click();
        return this;
    }

    public String getDisableProjectMessage() {
        return disableProjectMessage.getText();
    }
    public FreeStyleProjectPage(WebDriver driver) {
        super(driver);
    }

}
