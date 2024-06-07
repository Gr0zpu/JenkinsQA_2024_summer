package study.project.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import study.project.model.base.BasePage;

public class HomePage extends BasePage {
    @FindBy(linkText = "New Item")
    private WebElement newItem;

    @FindBy(linkText = "Create a job")
    private WebElement createNewJob;

    @FindBy(xpath = "//td/a[@class='jenkins-table__link model-link inside']/span")
    private WebElement getProjectname;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public NewJobPage createNewJob() {
        newItem.click();
        return new NewJobPage(driver);
    }

    public String getProjectName() {
        return getProjectname.getText();
    }


}
