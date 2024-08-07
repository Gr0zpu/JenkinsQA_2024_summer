package study.project;

import org.testng.Assert;
import org.testng.annotations.Test;
import study.project.model.CreateitemPage;
import study.project.model.FreeStyleProjectConfigPage;
import study.project.model.FreeStyleProjectPage;
import study.project.model.HomePage;
import study.project.runner.BaseTest;
import study.project.runner.TestUtils;

import static study.project.runner.TestUtils.getUniqueName;

public class FreeStyleProjectTest extends BaseTest {
    private static final String FREESTYLE_PROJECT_NAME = getUniqueName("freeStyleProject");
    private static final String RENAME_FREESTYLE_PROJECT_NAME = getUniqueName("reNameFreeStyleProject");

    @Test
    public void testCreateFreeStyleProject() {
        String compleateName = new HomePage(getDriver())
                .createNewJob()
                .setNameJob(FREESTYLE_PROJECT_NAME)
                .selectFreeStyleProject()
                .clickOK(new FreeStyleProjectConfigPage(getDriver()))
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(compleateName, FREESTYLE_PROJECT_NAME);
    }

    @Test(dependsOnMethods = {"testCreateFreeStyleProject"})
    public void testCreateFreeStyleProjectWithDuplicateName() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob(FREESTYLE_PROJECT_NAME)
                .selectFreeStyleProject()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("A job already exists with the name"));
    }

    @Test
    public void testCreateFreeStyleProjectWithSpecialSymbol() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob(FREESTYLE_PROJECT_NAME + "/")
                .selectFreeStyleProject()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("is an unsafe character"));
    }

    @Test
    public void testCreateFreeStyleProjectWithSpacesName() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob("     ")
                .selectFreeStyleProject()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("No name is specified"));
    }

    @Test
    public void testCreateFreeStyleProjectWithEmptyName() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob("")
                .selectFreeStyleProject()
                .itemRequiredMessage();

        Assert.assertTrue(errorText.contains("This field cannot be empty, please enter a valid name"));
    }

    @Test
    public void testCreateFreeStyleProjectWithNameLonger255Symbols() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob("a".repeat(260))
                .selectFreeStyleProject()
                .clickOK(new CreateitemPage(getDriver()))
                .getAltErrorMessage();

        Assert.assertTrue(errorText.contains("A problem occurred while processing the request"));
    }

    @Test(dependsOnMethods = {"testCreateFreeStyleProject"})
    public void testCheckNewFreeStyleProjectOnDashBoard() {
        String dashboardProjectName = new HomePage(getDriver()).getProjectName();

        System.out.println(dashboardProjectName);

        Assert.assertTrue(dashboardProjectName.equals(FREESTYLE_PROJECT_NAME));
    }

    @Test(dependsOnMethods = {"testCreateFreeStyleProject"})
    public void testCreateFreeStyleProjectFromOtherExisting() {
        String newFreeStyleProjectName = getUniqueName(FREESTYLE_PROJECT_NAME);

        String freeStyleProjectName = new HomePage(getDriver())
                .createNewJob()
                .setNameJob(newFreeStyleProjectName)
                .setFromExisting(FREESTYLE_PROJECT_NAME)
                .clickOK(new FreeStyleProjectConfigPage(getDriver()))
                .clickSaveButton()
                .getProjectName();

        Assert.assertTrue(newFreeStyleProjectName.equals(freeStyleProjectName));
    }

    @Test(dependsOnMethods = {"testCreateFreeStyleProject"})
    public void testFreeStyleProjectAddDescription() {
        String description = new HomePage(getDriver())
                .openProject(new FreeStyleProjectPage(getDriver()))
                .addOrEditDescription("description " + FREESTYLE_PROJECT_NAME, new FreeStyleProjectPage(getDriver()))
                .getDescription();

        Assert.assertEquals(description,"description " + FREESTYLE_PROJECT_NAME);
    }

    @Test
    public void testRenameFreeStyleProject() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        String projectName = new HomePage(getDriver())
                .openProject(new FreeStyleProjectPage(getDriver()))
                .clickRename()
                .setName(RENAME_FREESTYLE_PROJECT_NAME)
                .clickSaveName(new FreeStyleProjectPage(getDriver()))
                .getProjectName();

        Assert.assertEquals(projectName,RENAME_FREESTYLE_PROJECT_NAME);
    }

    @Test
    public void testRenameFreeStyleProjectContainSpecialSymbol(){
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        String errorText = new HomePage(getDriver())
                .openProject(new FreeStyleProjectPage(getDriver()))
                .clickRename()
                .setName(RENAME_FREESTYLE_PROJECT_NAME + "/")
                .clickSaveName(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("is an unsafe character"));
    }

    @Test
    public void testRenameFreeStyleProjectWithSpacesName() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        String errorText = new HomePage(getDriver())
                .openProject(new FreeStyleProjectPage(getDriver()))
                .clickRename()
                .setName("   ")
                .clickSaveName(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("No name is specified"));
    }

    @Test(enabled = false)
    public void testRenameFreeStyleProjectWithNameLonger255Symbols() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        String projectName = new HomePage(getDriver())
                .openProject(new FreeStyleProjectPage(getDriver()))
                .clickRename()
                .setName("a".repeat(260))
                .clickSaveName(new FreeStyleProjectPage(getDriver()))
                .getProjectName();

        Assert.assertTrue(projectName.equals("a".repeat(260)));
    }

    @Test
    public void testDisableFreeStyleProject() {
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT_NAME);

        String projectStatus = new HomePage(getDriver())
                .openProject(new FreeStyleProjectPage(getDriver()))
                .disableFreeStyleProject()
                .getProjectStatus();

        Assert.assertTrue(projectStatus.equals("Disable"));
    }

    @Test(dependsOnMethods = {"testDisableFreeStyleProject"})
    public void testEnableFreeStyleProject() {
        String projectStatus = new HomePage(getDriver())
                .openProject(new FreeStyleProjectPage(getDriver()))
                .enableFreeStyleProject()
                .getProjectStatus();

        Assert.assertTrue(projectStatus.equals("Enable"));
    }
}
