package study.project;

import org.testng.Assert;
import org.testng.annotations.Test;
import study.project.model.*;
import study.project.runner.BaseTest;

import static study.project.runner.TestUtils.getUniqueName;

public class MultiConfigurationProjectTest extends BaseTest {
    private static final String MULTI_CONFIGURATION_PROJECT_NAME =  getUniqueName("multiConfigurationProject");

    @Test
    public void testCreateMultiConfigurationProject() {
        String itemMultiConfigurationProject = new HomePage(getDriver())
                .createNewJob()
                .setNameJob(MULTI_CONFIGURATION_PROJECT_NAME)
                .selectMultiConfigurationProject()
                .clickOK(new MultiConfigurationProjectConfigPage(getDriver()))
                .clickSaveButton()
                .getProjectName();

        Assert.assertTrue(itemMultiConfigurationProject.contains(MULTI_CONFIGURATION_PROJECT_NAME));
    }

    @Test(dependsOnMethods = {"testCreateMultiConfigurationProject"})
    public void testCreateMultiConfigurationProjectWithDuplicateName() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob(MULTI_CONFIGURATION_PROJECT_NAME)
                .selectMultiConfigurationProject()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("A job already exists with the name"));
    }

    @Test
    public void testCreateMultiConfigurationProjectWithSpecialSymbol() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob(MULTI_CONFIGURATION_PROJECT_NAME + "/")
                .selectMultiConfigurationProject()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("is an unsafe character"));
    }

    @Test
    public void testCreateMultiConfigurationProjectWithSpacesName() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob("    ")
                .selectMultiConfigurationProject()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("No name is specified"));
    }

    @Test
    public void testCreateMultiConfigurationProjectWithNameLonger255Symbols() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob("a".repeat(260))
                .selectMultiConfigurationProject()
                .clickOK(new CreateitemPage(getDriver()))
                .getAltErrorMessage();

        Assert.assertTrue(errorText.contains("A problem occurred while processing the request"));
    }

    @Test
    public void testCreateMultiConfigurationProjectWithEmptyName() {
        NewJobPage newJobPage = new HomePage((getDriver()))
                .createNewJob()
                .selectMultiConfigurationProject();

        String nameValidateMessage = newJobPage.getValidateNameMessage();
        Boolean okButtonStatus = newJobPage.getOkButtonStatus();

        Assert.assertTrue(nameValidateMessage.contains("This field cannot be empty, please enter a valid name"));
        Assert.assertTrue(okButtonStatus);
    }

    @Test(dependsOnMethods = {"testCreateMultiConfigurationProject"})
    public void testCheckMultiConfigurationProjectBySearch() {
        String projectName = new HomePage(getDriver())
                .headerSearch(MULTI_CONFIGURATION_PROJECT_NAME, new MultiConfigurationProjectPage(getDriver()))
                .getProjectName();

        Assert.assertTrue(projectName.equals("Project " + MULTI_CONFIGURATION_PROJECT_NAME));
    }

    @Test(dependsOnMethods = {"testCreateMultiConfigurationProject"})
    public void testCheckMultiConfigurationProjectOnDashBoard() {
        String multiConfigurationProjectName = new HomePage(getDriver())
                .getProjectName();

        Assert.assertTrue(multiConfigurationProjectName.equals(MULTI_CONFIGURATION_PROJECT_NAME));
    }

    @Test(dependsOnMethods = {"testCreateMultiConfigurationProject"})
    public void testCheckRedirectFromDashboard() {
        String multiConfigurationProjectName = new HomePage(getDriver())
                .openProject(new MultiConfigurationProjectPage(getDriver()))
                .getProjectName();

        Assert.assertTrue(multiConfigurationProjectName.equals("Project " + MULTI_CONFIGURATION_PROJECT_NAME));
    }

    @Test(dependsOnMethods = {"testCreateMultiConfigurationProject"})
    public void testCreateMultiConfigurationProjectFromOtherExisting() {
        String newMultiConfigurationProjectName = getUniqueName(MULTI_CONFIGURATION_PROJECT_NAME);

        String multiConfigurationProjectName = new HomePage(getDriver())
                .createNewJob()
                .setNameJob(newMultiConfigurationProjectName)
                .setFromExisting(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickOK(new MultiConfigurationProjectConfigPage(getDriver()))
                .clickSaveButton()
                .getProjectName();

        Assert.assertTrue(multiConfigurationProjectName.equals("Project " + newMultiConfigurationProjectName));
    }
}
