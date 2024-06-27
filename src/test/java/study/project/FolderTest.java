package study.project;

import org.testng.Assert;
import org.testng.annotations.Test;
import study.project.model.*;
import study.project.runner.BaseTest;

import static study.project.runner.TestUtils.getUniqueName;

public class FolderTest extends BaseTest {
    private static final String FOLDER_NAME = getUniqueName("newFolder");

    @Test
    public void createFolderTest() {
        String newFolderName = new HomePage(getDriver())
                .createNewJob()
                .nameJob(FOLDER_NAME)
                .selectFolder()
                .clickOK(new FolderConfigPage(getDriver()))
                .clickSaveButton()
                .getProjectName();
        Assert.assertEquals(newFolderName, FOLDER_NAME);
    }

    @Test(dependsOnMethods = {"createFolderTest"})
    public void createFolderWithDuplicateNameTest() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .nameJob(FOLDER_NAME)
                .selectFolder()
                .clickOK(new FolderConfigPage(getDriver()))
                .clickSaveButton()
                .getErrorText();

        Assert.assertTrue(errorText.contains("A job already exists with the name"));
    }

    @Test
    public void createFolderWithSpecialSymbol() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .nameJob(FOLDER_NAME + "/")
                .selectFolder()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("is an unsafe character"));
    }

    @Test
    public void createFolderWithSpacesName() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .nameJob("    ")
                .selectFolder()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("No name is specified"));
    }

    @Test
    public void createFolderWithNameLonger255Symbols() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .nameJob("a".repeat(260))
                .selectFolder()
                .clickOK(new CreateitemPage(getDriver()))
                .getAltErrorMessage();

        Assert.assertTrue(errorText.contains("A problem occurred while processing the request"));
    }

    @Test
    public void createFolderWithEmptyName() {
        NewJobPage newFolder = new HomePage(getDriver())
                .createNewJob()
                .selectPipeline();

        String nameValidateMessage = newFolder.getValidateNameMessage();

        Boolean okButtonStatus = newFolder.getOkButtonStatus();

        Assert.assertTrue(nameValidateMessage.contains("This field cannot be empty, please enter a valid name"));
        Assert.assertTrue(okButtonStatus);
    }

    @Test(dependsOnMethods = {"createFolderTest"})
    public void checkFolderBySearch() {
        String folderName = new HomePage(getDriver())
                .headerSearch(FOLDER_NAME, new FolderProjectPage(getDriver()))
                .getProjectName();

        Assert.assertTrue(folderName.equals(FOLDER_NAME));
    }

    @Test(dependsOnMethods = {"createFolderTest"})
    public void checkNewFolderOnDashboard() {
        String dashboardFolderName = new HomePage(getDriver()).getProjectName();

        Assert.assertTrue(dashboardFolderName.equals(FOLDER_NAME));
    }

    @Test(dependsOnMethods = {"createFolderTest"})
    public void checkRedirectFromDashboard() {
        String folderName = new HomePage(getDriver())
                .openProject(new FolderProjectPage(getDriver()))
                .getProjectName();

        Assert.assertTrue(FOLDER_NAME.equals(folderName));
    }
}
