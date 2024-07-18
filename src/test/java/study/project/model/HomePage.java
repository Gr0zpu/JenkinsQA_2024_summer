package study.project.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import study.project.model.base.BasePage;
import study.project.model.base.BaseProjectPage;

public class HomePage extends BasePage {
    @FindBy(linkText = "New Item")
    private WebElement newItem;

    @FindBy(xpath = "//h1")
    private WebElement welcomeText;

    @FindBy(linkText = "Create a job")
    private WebElement createNewJob;

    @FindBy(xpath = "//td/a[@class='jenkins-table__link model-link inside']/span")
    private WebElement project;

    @FindBy(xpath = "//td//button[@class='jenkins-menu-dropdown-chevron']")
    private WebElement projectDropDownMenu;

    @FindBy(id = "search-box")
    private WebElement searchBox;

    @FindBy(xpath = "//button[contains(normalize-space(), 'Delete')]")
    private WebElement delete;

    @FindBy(xpath = "//*[contains(text(), 'Yes')]")
    private WebElement confirmDelete;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public NewJobPage createNewJob() {
        newItem.click();
        return new NewJobPage(getDriver());
    }

    public String getProjectName() {
        return project.getText();
    }

    public <T extends BaseProjectPage> T openProject (T page) {
        project.click();

        return page;
    }

    public HomePage deleteProject() {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(project).perform();
        getWait10();
        projectDropDownMenu.click();
        delete.click();
        confirmDelete.click();

        return this;
    }
    public String getWelcomeText() {
        return welcomeText.getText();
    }


}
