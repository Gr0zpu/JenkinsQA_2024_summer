package study.project.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import study.project.model.base.BasePage;
import study.project.model.base.BaseProjectPage;

public class HomePage extends BasePage {
    @FindBy(linkText = "New Item")
    private WebElement newItem;

    @FindBy(linkText = "Create a job")
    private WebElement createNewJob;

    @FindBy(xpath = "//td/a[@class='jenkins-table__link model-link inside']/span")
    private WebElement project;

    @FindBy(id = "search-box")
    private WebElement searchBox;

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


}
