package study.project.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import study.project.model.base.BasePage;

public class NewJobPage extends BasePage {
    @FindBy(xpath = "//*[contains(text(), 'Enter an item name')]")
    private WebElement pageTitle;

    @FindBy(id = "name")
    private WebElement inputName;

    @FindBy(xpath = "//*[contains(text(), 'Freestyle project')]")
    private WebElement choiceFreeStyleProject;

    @FindBy(xpath = "//*[text() = 'Pipeline']")
    private WebElement choicePipeline;

    @FindBy(xpath = "//*[text() = 'Multi-configuration project']")
    private WebElement choiceMultiConfigurationProject;

    @FindBy(xpath = "//*[text() = 'Folder']")
    private WebElement choiceFolder;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    @FindBy(id = "itemname-required")
    private WebElement itemRequiredMessage;

    @FindBy(id = "jenkins-home-link")
    private WebElement goHomePage;

    @FindBy(className = "input-validation-message")
    private WebElement validateNameMessage;

    public NewJobPage(WebDriver driver) {
        super(driver);
    }

    public HomePage goHomePage() {
        return new HomePage(driver);
    }

    public String itemRequiredMessage() {
        return itemRequiredMessage.getText();
    }
    public String getPageTitle() {
        return pageTitle.getText();
    }

    public NewJobPage nameJob(String name){
        inputName.sendKeys(name);
        return this;
    }
    public NewJobPage selectFreeStyleProject() {
        choiceFreeStyleProject.click();
        return this;
    }

    public NewJobPage selectPipeline() {
        choicePipeline.click();
        return this;
    }

    public NewJobPage selectMultiConfigurationProject() {
        choiceMultiConfigurationProject.click();
        return this;
    }

    public NewJobPage selectFolder() {
        choiceFolder.click();
        return this;
    }

    public <T> T clickOK(T page) {
        okButton.click();
        return page;
    }

    public String getValidateNameMessage() {
        return validateNameMessage.getText();
    }

    public boolean getOkButtonStatus() {
        boolean isDisabled = okButton.getAttribute("disabled") != null;

        if (isDisabled) {
            return true;
        }else {
            return false;
        }
    }
}
