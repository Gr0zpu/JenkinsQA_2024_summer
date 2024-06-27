package study.project.model;

import org.openqa.selenium.WebDriver;
import study.project.model.base.BaseConfigPage;

public class MultiConfigurationProjectConfigPage extends BaseConfigPage<MultiConfigurationProjectPage> {

    public MultiConfigurationProjectConfigPage(WebDriver driver) {
        super(driver, new MultiConfigurationProjectPage(driver));
    }
}
