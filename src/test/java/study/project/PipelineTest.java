package study.project;

import org.testng.Assert;
import org.testng.annotations.Test;
import study.project.model.*;
import study.project.runner.BaseTest;

import static study.project.runner.TestUtils.getUniqueName;

public class PipelineTest extends BaseTest {
    private static final String PIPELINE_NAME = getUniqueName("pipeline");
    @Test
    public void createPipelineTest() {
        String itemPipeline = new HomePage(getDriver())
                .createNewJob()
                .nameJob(PIPELINE_NAME)
                .selectPipeline()
                .clickOK(new PipelineConfigPage(getDriver()))
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(itemPipeline, PIPELINE_NAME);
    }

    @Test(dependsOnMethods = {"createPipelineTest"})
    public void createPipelineWithDuplicateNameTest() {
        String errorText = new HomePage(getDriver())
                .createNewJob().nameJob(PIPELINE_NAME)
                .selectPipeline()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("A job already exists with the name"));
    }

    @Test
    public void createPipelineWithSpecialSymbol() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .nameJob(PIPELINE_NAME + "/")
                .selectPipeline()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("is an unsafe character"));
    }

    @Test
    public void createPipelineWithSpacesName() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .nameJob("     ")
                .selectPipeline()
                .clickOK(new CreateitemPage(getDriver()))
                .getErrorMessage();

        Assert.assertTrue(errorText.contains("No name is specified"));
    }

    @Test
    public void createPipelineWithNameLonger255Symbols() {
        String errorText = new HomePage(getDriver())
                .createNewJob()
                .nameJob("a".repeat(260))
                .selectPipeline()
                .clickOK(new CreateitemPage(getDriver()))
                .getAltErrorMessage();

        Assert.assertTrue(errorText.contains("A problem occurred while processing the request"));
    }

    @Test
    public void createPipelineWithEmptyName() {
        NewJobPage newJobPage =  new HomePage(getDriver())
                .createNewJob()
                .selectPipeline();

        String nameValidateMessage = newJobPage.getValidateNameMessage();

        Boolean okButtonStatus = newJobPage.getOkButtonStatus();

        Assert.assertTrue(nameValidateMessage.contains("This field cannot be empty, please enter a valid name"));
        Assert.assertTrue(okButtonStatus);

    }

    @Test(dependsOnMethods = {"createPipelineTest"})
    public void checkPipelineBySearch() {
        String projectName = new HomePage(getDriver())
                .headerSearch(PIPELINE_NAME, new PipelineProjectPage(getDriver()))
                .getProjectName();

        Assert.assertTrue(projectName.equals(PIPELINE_NAME));
    }

    @Test(dependsOnMethods = {"createPipelineTest"})
    public void checkNewPipelineOnDashBoard() {
        String dashboardPipelineName = new HomePage(getDriver()).getProjectName();

        Assert.assertTrue(dashboardPipelineName.equals(PIPELINE_NAME));
    }

    @Test(dependsOnMethods = {"createPipelineTest"})
    public void checkRedirectFromDashboard() {
        String pipelineName = new HomePage(getDriver())
                .openProject(new PipelineProjectPage(getDriver()))
                .getProjectName();

        Assert.assertTrue(pipelineName.equals(PIPELINE_NAME));
    }
}
