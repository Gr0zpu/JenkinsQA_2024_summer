package study.project;

import org.testng.Assert;
import org.testng.annotations.Test;
import study.project.model.*;
import study.project.runner.BaseTest;

import static study.project.runner.TestUtils.getUniqueName;

public class FolderTest extends BaseTest {
    private static final String FOLDER_NAME = getUniqueName("newFolder");

    @Test
    public void testCreateFolderTest() {
        String newFolderName = new HomePage(getDriver())
                .createNewJob()
                .setNameJob(FOLDER_NAME)
                .selectFolder()
                .clickOK(new FolderConfigPage(getDriver()))
                .clickSaveButton()
                .getProjectName();
        Assert.assertEquals(newFolderName, FOLDER_NAME);
    }

    @Test(dependsOnMethods = {"testCreateFolderTest"})
    public void testCreateFolderWithDuplicateName() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob(FOLDER_NAME)
                .selectFolder()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("A job already exists with the name"));
    }

    @Test
    public void testCreateFolderWithSpecialSymbol() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob(FOLDER_NAME + "/")
                .selectFolder()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("is an unsafe character"));
    }

    @Test
    public void testCreateFolderWithSpacesName() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob("    ")
                .selectFolder()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("No name is specified"));
    }

    @Test
    public void testCreateFolderWithNameLonger255Symbols() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob("a".repeat(260))
                .selectFolder()
                .clickOK(new CreateitemPage(getDriver()))
                .getAltErrorMessage();

        Assert.assertTrue(errorText.contains("A problem occurred while processing the request"));
    }

    @Test
    public void testCreateFolderWithEmptyName() {
        NewJobPage newFolder = new HomePage(getDriver())
                .createNewJob()
                .selectPipeline();

        String nameValidateMessage = newFolder.getValidateNameMessage();

        Boolean okButtonStatus = newFolder.getOkButtonStatus();

        Assert.assertTrue(nameValidateMessage.contains("This field cannot be empty, please enter a valid name"));
        Assert.assertTrue(okButtonStatus);
    }

    @Test(dependsOnMethods = {"testCreateFolderTest"})
    public void testCheckFolderBySearch() {
        String folderName = new HomePage(getDriver())
                .headerSearch(FOLDER_NAME, new FolderProjectPage(getDriver()))
                .getProjectName();

        Assert.assertTrue(folderName.equals(FOLDER_NAME));
    }

    @Test(dependsOnMethods = {"testCreateFolderTest"})
    public void testCheckNewFolderOnDashboard() {
        String dashboardFolderName = new HomePage(getDriver()).getProjectName();

        Assert.assertTrue(dashboardFolderName.equals(FOLDER_NAME));
    }

    @Test(dependsOnMethods = {"testCreateFolderTest"})
    public void testCheckRedirectFromDashboard() {
        String folderName = new HomePage(getDriver())
                .openProject(new FolderProjectPage(getDriver()))
                .getProjectName();

        Assert.assertTrue(FOLDER_NAME.equals(folderName));
    }

    @Test(dependsOnMethods = {"testCreateFolderTest"})
    public void testCreateFolderFromOtherExisting() {
        String newFolderName = getUniqueName(FOLDER_NAME);

        String folderName = new HomePage(getDriver())
                .createNewJob()
                .setNameJob(newFolderName)
                .setFromExisting(FOLDER_NAME)
                .clickOK(new FolderConfigPage(getDriver()))
                .clickSaveButton()
                .getProjectName();

        Assert.assertTrue(newFolderName.equals(folderName));
    }
}
