package com.test.stepDefs;

import com.test.utils.Baseclass;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPointer;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Facebookupdate {
    List<String> profilesGlobal = null;
    String groupID = null;

    Baseclass baseclass;
    WebDriver driver = null;

    @Given("^user login to facebook$")
    public void navigateToFBandLogin() throws IOException, ParseException {
        System.setProperty("webdriver.chrome.driver", "C:/Users/shipr/Downloads/chromedriver_win32/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.facebook.com/");
        System.out.println("Success");
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader("src/test/resources/Data/credentials.json");
        Object obj = jsonParser.parse(reader);
        org.json.simple.JSONObject jsondata = (org.json.simple.JSONObject)obj;
        groupID = (String) jsondata.get("groupID");
        driver.findElement(By.id("email")).sendKeys((CharSequence) jsondata.get("usernameFB"));
        driver.findElement(By.id("pass")).sendKeys((CharSequence) jsondata.get("passwordFB"));
        driver.findElement(By.name("login")).click();
    }

    @When("^user navigates to Member requests and collate data$")
    public void MemberRequestDataupdate() throws InterruptedException, IOException {
        JSONObject obj = new JSONObject();
        String email = null;
        String name = null;
        String profileID = null;
        FileWriter file = new FileWriter("src/test/resources/Data/output.json");
        Thread.sleep(10000);
        driver.navigate().to("https://www.facebook.com/groups/"+groupID+"/member-requests");
        System.out.println("inside");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        if (driver.findElements(By.xpath("//span[contains(@class,'d2edcug0 hpfvmrgz qv66sw1b c1et5uql lr9zc1uh a5q79mjw g1cxx5fr knj5qynh m9osqain')]")).size() > 0) {
            String size = driver.findElement(By.xpath("//span[contains(@class,'d2edcug0 hpfvmrgz qv66sw1b c1et5uql lr9zc1uh a5q79mjw g1cxx5fr knj5qynh m9osqain')]")).getText();
            size = size.split(" . ")[1];
            WebElement profilesdiv = driver.findElement(By.xpath("//div[@class='l9j0dhe7 stjgntxs ni8dbmo4 ap1mbyyd dhix69tm wkznzc2l ibutc8p7 l82x9zwi uo3d90p7 cwj9ozl2']"));
            List<WebElement> profiles = profilesdiv.findElements(By.xpath("//div[@class='a8nywdso f10w8fjw rz4wbd8a pybr56ya']"));
            List<WebElement> profileIDs = driver.findElements(By.xpath("//a[contains(@href,'https://www.facebook.com/profile.php?id')]"));
            for (int i = 0; i < profiles.size(); i++) {
                String data[] = profiles.get(i).getText().split("\\r?\\n");
                if ((data[data.length - 1] != " ") && data[data.length - 1].contains("@")) {
                    name = data[0];
                    email = data[data.length - 1];
                    profileID = profileIDs.get(i).getAttribute("href").split("=")[1].trim();
                    obj.put("ProfileID", profileID);
                    obj.put("Name", name);
                    obj.put("Email", email);
                }
            }
            JSONArray json = new JSONArray();
            json.put(obj);
            file.write(String.valueOf(json));
            file.close();
        }
    }

    @When("^user navigates to Member$")
    public List<String> userNavigatesToMember() throws InterruptedException {
        ArrayList<String> profiles = new ArrayList<>();
        Thread.sleep(5000);
        driver.navigate().to("https://www.facebook.com/groups/"+groupID+"/members");
        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
        System.out.println("Members");
        Thread.sleep(5000);
        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
        List<WebElement> newMembers = driver.findElements(By.xpath("//a[@aria-label='Profile']"));
        for (WebElement member : newMembers) {
            String profileID = member.getAttribute("href").split("user/")[1].replace("/", "");
            profiles.add(profileID);
        }
        this.profilesGlobal = profiles;
        return profilesGlobal;
    }

    @Then("^Data is updated$")
    public void data_is_updated() throws Throwable {
        System.out.println(profilesGlobal);
        JSONParser jp = new JSONParser();
//        FileReader fr = new FileReader("src/test/resources/Data/output.json");
        Object obj = jp.parse(new FileReader("src/test/resources/Data/output.json"));
        org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) obj;
        for (int i = 0; i < profilesGlobal.size(); i++) {
            for (int j = 0; j < jsonArray.size(); j++) {
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) jsonArray.get(j);
                String profileID = (String)jsonObject.get("ProfileID");
                String userName = (String)jsonObject.get("Name");
                String userEmail = (String) jsonObject.get("Email");
                if (profilesGlobal.get(i).equalsIgnoreCase(profileID)) {
                    System.out.println("Match for profile" + profileID);
                    break;
                }
            }
        }
    }
}
