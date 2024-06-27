package study.project.model;

import org.openqa.selenium.WebDriver;
import study.project.model.base.BaseConfigPage;

public class FolderConfigPage extends BaseConfigPage<FolderProjectPage> {
    public FolderConfigPage(WebDriver driver) {
        super(driver, new FolderProjectPage(driver));
    }
}
