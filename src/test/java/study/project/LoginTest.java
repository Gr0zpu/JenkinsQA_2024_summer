package study.project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.Test;
import study.project.runner.BaseTest;

public class LoginTest extends BaseTest {
    @Test
    public void CiTest() {
        WebDriver driver = getDriver();
        WebElement H1 = driver.findElement(By.xpath("//h1"));
        System.out.println(H1.getText());


        Assert.assertEquals(H1.getText(),"Welcome to Jenkins!");
    }
}
