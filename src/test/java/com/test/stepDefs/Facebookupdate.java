package com.test.stepDefs;

import com.test.utils.Baseclass;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Facebookupdate {

  Baseclass baseclass;
    WebDriver driver = null;
    @Given("^user login to facebook$")
    public void navigateToFBandLogin() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/shipr/Downloads/chromedriver_win32/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.facebook.com/");
        System.out.println("Success");
        driver.findElement(By.id("email")).sendKeys("shivanginigam4@gmail.com");
        driver.findElement(By.id("pass")).sendKeys("Facebook@2021");
        driver.findElement(By.name("login")).click();
    }

    @When("^user navigates to Member requests and collate data$")
        public void MemberRequestDataupdate() throws InterruptedException, IOException {
        String email = null;
        String name = null;
        Thread.sleep(10000);
        driver.navigate().to("https://www.facebook.com/groups/180913030412171/member-requests");
        System.out.println("inside");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String size = driver.findElement(By.xpath("//span[contains(@class,'d2edcug0 hpfvmrgz qv66sw1b c1et5uql lr9zc1uh a5q79mjw g1cxx5fr knj5qynh m9osqain')]")).getText();
        size = size.split(" . ")[1];
        WebElement profilesdiv = driver.findElement(By.xpath("//div[@class='l9j0dhe7 stjgntxs ni8dbmo4 ap1mbyyd dhix69tm wkznzc2l ibutc8p7 l82x9zwi uo3d90p7 cwj9ozl2']"));
        List<WebElement> profiles = profilesdiv.findElements(By.xpath("//div[@class='a8nywdso f10w8fjw rz4wbd8a pybr56ya']"));

        for(int i = 0; i<profiles.size(); i++) {
           String data[] = profiles.get(i).getText().split("\\r?\\n");
           if ((data[data.length-1] != " ") && data[data.length-1].contains("@") )
           {
               name = data[0];
               email = data[data.length-1];
               Baseclass.writeJSON(email,name);
           }
          }
    }

    @Then("^Data is updated$")
    public void data_is_updated() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
