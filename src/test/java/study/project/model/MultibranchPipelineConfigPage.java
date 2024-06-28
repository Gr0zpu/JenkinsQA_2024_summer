package study.project.model;

import org.openqa.selenium.WebDriver;
import study.project.model.base.BaseConfigPage;

public class MultibranchPipelineConfigPage extends BaseConfigPage<MultibranchPipelineProjectPage> {
    public MultibranchPipelineConfigPage(WebDriver driver) {
        super(driver, new MultibranchPipelineProjectPage(driver));
    }
}
