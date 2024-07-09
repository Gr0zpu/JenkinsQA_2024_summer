package study.project.model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import study.project.model.FreeStyleProjectPage;

public abstract class BaseProjectPage<T extends BasePage> extends BasePage {
    @FindBy(tagName = "h1")
    private WebElement projectName;

    @FindBy(css = ".error")
    private WebElement error;

    @FindBy(id = "description-link")
    private WebElement descriptionButton;

    @FindBy(name = "description")
    private WebElement inputDescription;

    @FindBy(name = "Submit")
    private WebElement saveDescription;

    @FindBy(xpath = "//div[@id='description']//div")
    private WebElement description;

    @FindBy(xpath = "//*[contains(text(), 'Rename')]/..")
    private WebElement renameProject;

    @FindBy(xpath = "//input[@name='newName']")
    WebElement inputNewName;

    @FindBy(xpath = "//*[@name='Submit']")
    WebElement confirmRename;

    public BaseProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectName() {

        return projectName.getText();
    }

    public <T extends BaseProjectPage> T addOrEditDescription(String description, T page) {
        descriptionButton.click();
        inputDescription.sendKeys(description);
        saveDescription.click();

        return page;
    }

    public <T> T rename (String newName, T page){
        renameProject.click();
        inputNewName.clear();
        inputNewName.sendKeys(newName);
        confirmRename.click();

        return page;
    }

    public FreeStyleProjectPage clickRename() {
        renameProject.click();

        return new FreeStyleProjectPage(getDriver());
    }

    public FreeStyleProjectPage setName(String name) {
        inputNewName.clear();
        inputNewName.sendKeys(name);

        return new FreeStyleProjectPage(getDriver());
    }

    public FreeStyleProjectPage clickSaveName() {
        confirmRename.click();

        return new FreeStyleProjectPage(getDriver());
    }

    public String getDescription() {
        return description.getText();
    }

    public String getErrorText() {

        return error.getText();
    }
}
