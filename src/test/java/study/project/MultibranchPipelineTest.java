package study.project;

import org.testng.Assert;
import org.testng.annotations.Test;
import study.project.model.*;
import study.project.runner.BaseTest;

import static study.project.runner.TestUtils.getUniqueName;

public class MultibranchPipelineTest extends BaseTest {
    private static final String MULTIBRANCH_PIPELINE_NAME = getUniqueName("newMultibranchPipeline");

    @Test
    public void testCreateMultibranchPipeline() {
        String newMultibranchPipeline = new HomePage(getDriver())
                .createNewJob()
                .setNameJob(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipeline()
                .clickOK(new MultibranchPipelineConfigPage(getDriver()))
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(newMultibranchPipeline, MULTIBRANCH_PIPELINE_NAME);
    }

    @Test(dependsOnMethods = {"testCreateMultibranchPipeline"})
    public void testCreateMultibranchPipelineWithDuplicate() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipeline()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("A job already exists with the name"));
    }

    @Test
    public void testCreateMultibranchPipelineWithSpecialSymbol() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob(MULTIBRANCH_PIPELINE_NAME + "/")
                .selectMultibranchPipeline()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("is an unsafe character"));
    }

    @Test
    public void testCreateMultibranchPipelineWithSpacesName() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob("   ")
                .selectMultibranchPipeline()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("No name is specified"));
    }

    @Test
    public void testCreateMultibranchPipelineWithNameLonger255Symbols() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .setNameJob("A".repeat(260))
                .selectMultibranchPipeline()
                .clickOK(new CreateitemPage(getDriver()))
                .getAltErrorMessage();

        Assert.assertTrue(errorText.contains("A problem occurred while processing the request"));
    }

    @Test
    public void testCreateMultibranchPipelineWithEmptyName() {
        NewJobPage newJobPage = new HomePage(getDriver())
                .createNewJob()
                .selectMultibranchPipeline();

        String nameValidateMessage = newJobPage.getValidateNameMessage();

        Boolean okButtonStatus = newJobPage.getOkButtonStatus();

        Assert.assertTrue(nameValidateMessage.contains("This field cannot be empty, please enter a valid name"));
        Assert.assertTrue(okButtonStatus);
    }

    @Test(dependsOnMethods = {"testCreateMultibranchPipeline"})
    public void testCheckMultibranchPipelineBySearch() {
        String projectName = new HomePage(getDriver())
                .headerSearch(MULTIBRANCH_PIPELINE_NAME, new MultiConfigurationProjectPage(getDriver()))
                .getProjectName();

        Assert.assertTrue(projectName.equals(MULTIBRANCH_PIPELINE_NAME));
    }
    
    @Test(dependsOnMethods = {"testCreateMultibranchPipeline"})
    public void testCheckMultibranchPipelineOnDashboard() {
        String dashboardMultibranchPipelineName = new HomePage(getDriver()).getProjectName();

        Assert.assertTrue(dashboardMultibranchPipelineName.equals(MULTIBRANCH_PIPELINE_NAME));
    }

    @Test(dependsOnMethods = {"testCreateMultibranchPipeline"})
    public void testCheckRedirectFromDashboard() {
        String multibranchPipelineName = new HomePage(getDriver())
                .openProject(new MultibranchPipelineProjectPage(getDriver()))
                .getProjectName();

        Assert.assertTrue(multibranchPipelineName.equals(MULTIBRANCH_PIPELINE_NAME));
    }

    @Test(dependsOnMethods = {"testCreateMultibranchPipeline"})
    public void testCreateMultibranchPipelineFromOtherExisting() {
        String newMultibranchPipelineName = getUniqueName(MULTIBRANCH_PIPELINE_NAME);

        String multibranchPipelineName = new HomePage(getDriver())
                .createNewJob()
                .setNameJob(newMultibranchPipelineName)
                .setFromExisting(MULTIBRANCH_PIPELINE_NAME)
                .clickOK(new MultibranchPipelineConfigPage(getDriver()))
                .clickSaveButton()
                .getProjectName();

        Assert.assertTrue(newMultibranchPipelineName.equals(multibranchPipelineName));
    }
}
