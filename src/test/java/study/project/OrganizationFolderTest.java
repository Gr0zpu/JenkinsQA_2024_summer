package study.project;

import org.testng.Assert;
import org.testng.annotations.Test;
import study.project.model.*;
import study.project.runner.BaseTest;

import static study.project.runner.TestUtils.getUniqueName;

public class OrganizationFolderTest extends BaseTest {
    private static final String ORGANIZATION_FOLDER_NAME = getUniqueName("organizationFolder");

    @Test
    public void createOrganizationFolderTest() {
        String organizationFolderName = new HomePage(getDriver())
                .createNewJob()
                .setNameJob(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolder()
                .clickOK(new OrganizationFolderConfigPage(getDriver()))
                .clickSaveButton()
                .getProjectName();

        Assert.assertTrue(organizationFolderName.equals(ORGANIZATION_FOLDER_NAME));
    }

    @Test(dependsOnMethods = {"createOrganizationFolderTest"})
    public void createOrganizationFolderWithDuplicateNameTest() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolder()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("A job already exists with the name"));
    }

    @Test
    public void createOrganizationFolderWithSpecialSymbolTest() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob(ORGANIZATION_FOLDER_NAME + "/")
                .selectOrganizationFolder()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("is an unsafe character"));
    }

    @Test
    public void createOrganizationFolderWithSpacesNameTest() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob("    ")
                .selectOrganizationFolder()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("No name is specified"));
    }

    @Test
    public void createOrganizationFolderWithNameLonger255Symbols() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob("a".repeat(261))
                .selectOrganizationFolder()
                .clickOK(new CreateitemPage(getDriver()))
                .getAltErrorMessage();

        Assert.assertTrue(errorText.contains("A problem occurred while processing the request"));
    }

    @Test
    public void createOrganizationFolderWithEmptyNameTest() {
        NewJobPage newJobPage = new HomePage(getDriver())
                .createNewJob()
                .selectOrganizationFolder();

        String nameValidateMessage = newJobPage.getValidateNameMessage();
        Boolean okButtonStatus = newJobPage.getOkButtonStatus();

        Assert.assertTrue(nameValidateMessage.contains("This field cannot be empty, please enter a valid name"));
        Assert.assertTrue(okButtonStatus);
    }

    @Test(dependsOnMethods = {"createOrganizationFolderTest"})
    public void checkBySearchTest() {
        String organizationFolderName = new HomePage(getDriver())
                .headerSearch(ORGANIZATION_FOLDER_NAME, new OrganizationFolderProjectPage(getDriver()))
                .getProjectName();

        Assert.assertTrue(organizationFolderName.equals(ORGANIZATION_FOLDER_NAME));
    }

    @Test(dependsOnMethods = {"createOrganizationFolderTest"})
    public void checkOrganizationFolderOnDashBoardTest() {
        String organizationFolderName = new HomePage(getDriver())
                .getProjectName();
        Assert.assertTrue(organizationFolderName.equals(ORGANIZATION_FOLDER_NAME));
    }

    @Test(dependsOnMethods = {"createOrganizationFolderTest"})
    public void checkOrganizationFolderRedirectFromDashboardTest() {
        String organizationFolderName = new HomePage(getDriver())
                .openProject(new OrganizationFolderProjectPage(getDriver()))
                .getProjectName();

        Assert.assertTrue(organizationFolderName.equals(ORGANIZATION_FOLDER_NAME));
    }
}
