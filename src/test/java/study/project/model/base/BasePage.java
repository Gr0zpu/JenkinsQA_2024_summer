package study.project.model.base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import study.project.model.FreeStyleProjectPage;
import study.project.model.HomePage;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePage extends BaseModel{
    @FindBy(css = "[class$=jenkins_ver]")
    private WebElement version;

    @FindBy(tagName = "h1")
    private WebElement heading;

    @FindBy(xpath = "//a[@class='main-search__icon-trailing']")
    private WebElement tutorialIcon;
    @FindBy(id = "search-box")
    private WebElement headerSearchBox;

    @FindBy(id = "jenkins-name-icon")
    private WebElement headerGoHome;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public <T> T headerSearch(String requestName, T page) {
        headerSearchBox.sendKeys(requestName + Keys.ENTER);
        return page;

    }

    public HomePage goHomePage() {
        headerGoHome.click();
        return new HomePage(getDriver());
    }



    public void openElementDropdown(WebElement element) {
        WebElement chevron = element.findElement(By.cssSelector("[class $= 'chevron']"));

        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", chevron);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('click'));", chevron);
    }

    public boolean isThereTextInBreadcrumbs(String text) {
        return getDriver().findElements(By.className("jenkins-breadcrumbs__list-item"))
                .stream()
                .anyMatch(e -> e.getText()
                        .contains(text));
    }

    public void hoverOverElement(WebElement element) {
        new Actions(getDriver())
                .moveToElement(element)
                .perform();
    }

    public void clickSpecificDropdownArrow(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));" +
                "arguments[0].dispatchEvent(new Event('click'));", element);
    }

    protected void clickElement(WebElement webElement) {
        new Actions(getDriver())
                .scrollToElement(webElement)
                .scrollByAmount(0, 100)
                .moveToElement(webElement)
                .click().perform();
    }

    public boolean areElementsEnabled(List<WebElement> elements) {
        return elements
                .stream()
                .allMatch(WebElement::isEnabled);
    }

    public String getText(WebElement webElement) {
        return webElement.getText();
    }

    public String getHeadingText() {
        return heading.getText();
    }

    public void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("return arguments[0].scrollIntoView(true);", element);
    }

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    public HomePage clickVersion() {
        version.click();

        return new HomePage(getDriver());
    }

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public FreeStyleProjectPage triggerJobViaHTTPRequest(String token, String user, String projectName) {
        final String postBuildJob = "http://" + user + ":" + token + "@localhost:8080/job/Project1/build?token=" + projectName;

        getDriver().switchTo().newWindow(WindowType.TAB);
        getDriver().navigate().to(postBuildJob);

        List<String> tabs = new ArrayList<>(getDriver().getWindowHandles());

        getDriver().switchTo().window(tabs.get(0));

        return new FreeStyleProjectPage(getDriver());
    }

    public void revokeTokenViaHTTPRequest(String token, String uuid, String user) {
        final String postRevokeToken = "http://" + user + ":" + token + "@localhost:8080/user/" + user
                + "/descriptorByName/jenkins.security.ApiTokenProperty/revoke?tokenUuid=" + uuid;

        getDriver().switchTo().newWindow(WindowType.TAB);
        getDriver().navigate().to(postRevokeToken);
        getWait5().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();

        List<String> tabs = new ArrayList<>(getDriver().getWindowHandles());

        getDriver().switchTo().window(tabs.get(0));
    }

    public void scrollToTopOfPage() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, 0);");
    }

    public static ExpectedCondition<Boolean> isElementInViewPort(WebElement element) {
        return new ExpectedCondition<>() {
            @Override
            public Boolean apply(WebDriver driver) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                return (Boolean) js.executeScript(
                        "let rect = arguments[0].getBoundingClientRect();" +
                                "return (rect.top >= 0 && rect.left >= 0 && " +
                                "rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && " +
                                "rect.right <= (window.innerWidth || document.documentElement.clientWidth));",
                        element);
            }
        };
    }

    public BasePage openTutorial() {
        getWait5().until(ExpectedConditions.visibilityOf(tutorialIcon)).click();

        return this;
    }
}
