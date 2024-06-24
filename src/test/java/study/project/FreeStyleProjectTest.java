package study.project;

import org.testng.Assert;
import org.testng.annotations.Test;
import study.project.model.CreateitemPage;
import study.project.model.FreeStyleProjectConfigPage;
import study.project.model.HomePage;
import study.project.runner.BaseTest;

import static study.project.runner.TestUtils.getUniqueName;

public class FreeStyleProjectTest extends BaseTest {
    private static final String FREESTYLE_PROJECT_NAME = getUniqueName("freeStyleProject");

    @Test
    public void openPageTest() {
        String pageTitle = new HomePage(getDriver()).createNewJob().getPageTitle();
        System.out.println(pageTitle);

        Assert.assertEquals(pageTitle, "Enter an item name");
    }

    @Test
    public void createFreeStyleProjectTest() {
        String compleateName = new HomePage(getDriver())
                .createNewJob()
                .nameJob(FREESTYLE_PROJECT_NAME)
                .selectFreeStyleProject()
                .clickOK(new FreeStyleProjectConfigPage(getDriver()))
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(compleateName, FREESTYLE_PROJECT_NAME);
    }

    @Test(dependsOnMethods = {"createFreeStyleProjectTest"})
    public void createFreeStyleProjectWithDuplicateNameTest() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .nameJob(FREESTYLE_PROJECT_NAME)
                .selectFreeStyleProject()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("A job already exists with the name"));
    }

    @Test
    public void createFreeStyleProjectWithSpecialSymbol() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .nameJob(FREESTYLE_PROJECT_NAME + "/")
                .selectFreeStyleProject()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("is an unsafe character"));

    }

    @Test
    public void createFreeStyleProjectWithSpacesName() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .nameJob("     ")
                .selectFreeStyleProject()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("No name is specified"));
    }

    @Test
    public void createFreeStyleProjectWithEmptyName() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .nameJob("")
                .selectFreeStyleProject()
                .itemRequiredMessage();

        Assert.assertTrue(errorText.contains("This field cannot be empty, please enter a valid name"));
    }

    @Test
    public void createFreeStyleProjectWithNameLonger255Symbols() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .nameJob("a".repeat(260))
                .selectFreeStyleProject()
                .clickOK(new CreateitemPage(getDriver()))
                .getAltErrorMessage();

        Assert.assertTrue(errorText.contains("A problem occurred while processing the request"));
    }

    @Test(dependsOnMethods = {"createFreeStyleProjectTest"})
    public void checkNewFreeStyleProjectOnDashBoard() {
        String dashboardProjectName = new HomePage(getDriver()).getProjectName();

        System.out.println(dashboardProjectName);

        Assert.assertTrue(dashboardProjectName.equals(FREESTYLE_PROJECT_NAME));
    }
}
