package study.project.runner;

import study.project.model.FreeStyleProjectConfigPage;
import study.project.model.HomePage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtils {
    public static String getUniqueName(String value) {
        return value + new SimpleDateFormat("HHmmssSS").format(new Date());
    }

    public static HomePage createFreestyleProject(BaseTest baseTest, String name) {
        return new HomePage(baseTest.getDriver())
                .createNewJob()
                .setNameJob(name)
                .selectFreeStyleProject()
                .clickOK(new FreeStyleProjectConfigPage(baseTest.getDriver()))
                .clickSaveButton()
                .goHomePage();


    }
}
