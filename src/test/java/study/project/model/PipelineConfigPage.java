package study.project.model;

import org.openqa.selenium.WebDriver;
import study.project.model.base.BaseConfigPage;
import study.project.model.base.BasePage;
import study.project.model.base.BaseProjectPage;

public class PipelineConfigPage extends BaseConfigPage<PipelineProjectPage> {

    public PipelineConfigPage(WebDriver driver) {
        super(driver, new PipelineProjectPage(driver));
    }
}
