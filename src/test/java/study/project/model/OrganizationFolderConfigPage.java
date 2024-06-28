package study.project.model;

import org.openqa.selenium.WebDriver;
import study.project.model.base.BaseConfigPage;

public class OrganizationFolderConfigPage extends BaseConfigPage<OrganizationFolderProjectPage> {
    public OrganizationFolderConfigPage(WebDriver driver) {
        super(driver, new OrganizationFolderProjectPage(driver));
    }
}
