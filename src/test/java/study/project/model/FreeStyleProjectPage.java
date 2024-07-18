package study.project.model;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import study.project.model.base.BasePage;
import study.project.model.base.BaseProjectPage;

public class FreeStyleProjectPage extends BaseProjectPage<FreeStyleProjectPage> {
    @FindBy(xpath = "//*[contains(text(), 'Disable Project')]")
    WebElement disableProjectButton;

    @FindBy(xpath = "//*[contains(text(), 'Enable')]")
    WebElement enableProjectButton;

    public FreeStyleProjectPage disableFreeStyleProject() {
        disableProjectButton.click();
        return this;
    }

    public FreeStyleProjectPage enableFreeStyleProject() {
        enableProjectButton.click();
        return this;
    }

    public String getProjectStatus() {
        try {
            if (disableProjectButton.isDisplayed()) {
                return "Enable";
            }
        } catch (NoSuchElementException e) {

        }

        try {
            if (enableProjectButton.isDisplayed()) {
                return "Disable";
            }
        } catch (NoSuchElementException e) {

        }

        return "None";
    }
    public FreeStyleProjectPage(WebDriver driver) {
        super(driver);
    }

}
