package study.project.model.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import study.project.model.FreeStyleProjectPage;
import study.project.model.HomePage;

import java.util.List;

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
    private WebElement inputNewName;

    @FindBy(xpath = "//*[@name='Submit']")
    private WebElement confirmRename;

    @FindBy(xpath = "//*[contains(text(), 'Delete')]")
    private WebElement delete;

    @FindBy(xpath = "//*[contains(text(), 'Yes')]")
    private WebElement confirmDeleteButton;

    @FindBy(xpath = "//li[@class='jenkins-breadcrumbs__list-item']")
    private List<WebElement> breadCrumbles;
    @FindBy(xpath = "//li//button")
    private List<WebElement> breadCrumbleMenus;

    @FindBy(xpath = "//*[contains(text(), 'Yes')]")
    private WebElement confirmDelete;




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

    public <T> T clickSaveName(T page) {
        confirmRename.click();

        return page;
    }

    public String getDescription() {
        return description.getText();
    }

    public String getErrorText() {

        return error.getText();
    }

    public HomePage deleteProject() {
        delete.click();
        confirmDeleteButton.click();

        return new HomePage(getDriver());
    }

    public HomePage deleteFromBreadCrumble() {
        WebElement currentProject =breadCrumbles.get(breadCrumbles.size()-1);

        Actions actions = new Actions(getDriver());
        actions.moveToElement(currentProject).perform();

        getWait10();

        currentProject.findElement(By.className("jenkins-menu-dropdown-chevron")).click();
        currentProject.findElement(By.xpath("//button[contains(normalize-space(), 'Delete')]")).click();

        confirmDelete.click();

        return new HomePage(getDriver());
    }
}
