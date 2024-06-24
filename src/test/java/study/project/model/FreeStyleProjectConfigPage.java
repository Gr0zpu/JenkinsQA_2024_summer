package study.project.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import study.project.model.base.BaseConfigPage;
import study.project.model.base.BasePage;

public class FreeStyleProjectConfigPage extends BaseConfigPage<FreeStyleProjectPage> {


    public FreeStyleProjectConfigPage(WebDriver driver) {
        super(driver, new FreeStyleProjectPage(driver));
    }


}
