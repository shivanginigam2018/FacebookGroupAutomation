package com.test.stepDefs;

import com.test.utils.Baseclass;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.formula.udf.UDFFinder;
import org.apache.poi.ss.usermodel.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPointer;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class Facebookupdate {
    List<String> profilesGlobal = null;
    String groupID = null;
    String hubspotUN = null;
    String hubspotPass = null;
    EventFiringWebDriver eDriver;
    WebDriverEventListener eventListener;
    HashMap<String, String> ApprovedProfiles;
    Baseclass baseclass;
    WebDriver driver = null;


    @Given("^user login to facebook$")
    public void navigateToFBandLogin() throws IOException, ParseException {
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("start-maximised","--disable-blink-features=AutomationControlled");
        System.setProperty("webdriver.chrome.driver", "C:/Users/shipr/Downloads/chromedriver_win32/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.facebook.com/");
        driver.manage().window().maximize();
        System.out.println("Success");
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader("src/test/resources/Data/credentials.json");
        Object obj = jsonParser.parse(reader);
        org.json.simple.JSONObject jsondata = (org.json.simple.JSONObject)obj;
        groupID = (String) jsondata.get("groupID");
        hubspotUN = (String) jsondata.get("hubspotUN");
        hubspotPass = (String) jsondata.get("hubspotPass");
        driver.findElement(By.id("email")).sendKeys((CharSequence) jsondata.get("usernameFB"));
        driver.findElement(By.id("pass")).sendKeys((CharSequence) jsondata.get("passwordFB"));
        driver.findElement(By.name("login")).click();
    }

    @When("^user navigates to Member requests and collate data$")
    public void MemberRequestDataupdate() throws InterruptedException, IOException, ParseException {
        JSONObject obj = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        boolean flag = false;
        String email = null;
        String name = null;
        String profileID = null;
        JSONArray json = new JSONArray();
        Thread.sleep(5000);
        driver.navigate().to("https://www.facebook.com/groups/" + groupID + "/member-requests");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        if (driver.findElements(By.xpath("//span[contains(@class,'d2edcug0 hpfvmrgz qv66sw1b c1et5uql lr9zc1uh a5q79mjw g1cxx5fr knj5qynh m9osqain')]")).size() > 0) {
            String size = driver.findElement(By.xpath("//span[contains(@class,'d2edcug0 hpfvmrgz qv66sw1b c1et5uql lr9zc1uh a5q79mjw g1cxx5fr knj5qynh m9osqain')]")).getText();
            size = size.split(" . ")[1];
            WebElement profilesdiv = driver.findElement(By.xpath("//div[@class='l9j0dhe7 stjgntxs ni8dbmo4 ap1mbyyd dhix69tm wkznzc2l ibutc8p7 l82x9zwi uo3d90p7 cwj9ozl2']"));
            List<WebElement> profiles = profilesdiv.findElements(By.xpath("//div[@class='a8nywdso f10w8fjw rz4wbd8a pybr56ya']"));
            List<WebElement> profileIDs = driver.findElements(By.xpath("//div/a[contains(@href,'/groups/"+groupID+"/user')]"));
            for (int i = 0; i < profiles.size(); i++) {
                String data[] = profiles.get(i).getText().split("\\r?\\n");
                if ((data[data.length - 1] != " ") && data[data.length - 1].contains("@")) {
                    name = data[0];
                    email = data[data.length - 1];
                    profileID = profileIDs.get(i).getAttribute("href").split("user/")[1];
                    profileID = profileID.split("/")[0];
                    obj.put("ProfileID", profileID);
                    obj.put("Name", name);
                    obj.put("Email", email);
                }
                System.out.println("Hi");
                File outputfile = new File("src/test/resources/Data/output.json");
                boolean exists = outputfile.exists();
                int length = (int) outputfile.length();
                FileReader file = new FileReader(outputfile);
                if (exists && length > 0) {
                    Object obj1 = jsonParser.parse(file);
                    org.json.simple.JSONArray usersList = (org.json.simple.JSONArray) obj1;
                    for(int j =0; j< usersList.size(); j++) {
                        org.json.simple.JSONObject existingProfile= (org.json.simple.JSONObject) usersList.get(j);
                        String existingID = (String) existingProfile.get("ProfileID");
                        if(obj.get("ProfileID").toString().equalsIgnoreCase(existingID)) {
                            flag = true;
                        }
                        if(flag)
                            break;
                    }
                    if(flag==false) {
                        usersList.add(obj);
                        FileWriter filew = new FileWriter("src/test/resources/Data/output.json");
                        filew.append(usersList.toJSONString());
                        filew.flush();
                    }
                    file.close();
                }
                else {
                    FileWriter filew1 = new FileWriter("src/test/resources/Data/output.json");
                    JSONArray jsonArr = new JSONArray();
                    jsonArr.put(obj);
                    filew1.write(jsonArr.toString());
                    filew1.close();
                }

            }
        }
        driver.close();
        driver.quit();
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
        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
//        driver.findElement(By.xpath("//button[@id='loginBtn']")).click();
        JSONParser jp = new JSONParser();
        Object obj = jp.parse(new FileReader("src/test/resources/Data/output.json"));
        org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) obj;
        ApprovedProfiles = new HashMap<String, String>();
        for (int i = 0; i < profilesGlobal.size(); i++) {
            for (int j = 0; j < jsonArray.size(); j++) {
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) jsonArray.get(j);
                String profileID = (String)jsonObject.get("ProfileID");
                String userName = (String)jsonObject.get("Name");
                String userEmail = (String) jsonObject.get("Email");
                if (profilesGlobal.get(i).equalsIgnoreCase(profileID)) {
                    System.out.println("Match for profile" + profileID);
                    ApprovedProfiles.put(userName, userEmail);
                    break;
                }
            }
        }
        this.ApprovedProfiles=ApprovedProfiles;
    }

    @Then("^DB is updated$")
    public void dbIsUpdated() throws IOException {
        String line = null;
        boolean dataflag = false;
        File file = new File("src/test/resources/Data/FBdata.csv");
        Iterator<Map.Entry<String, String>> it = ApprovedProfiles.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        PrintWriter bw = new PrintWriter(new FileWriter("src/test/resources/Data/FBdata.xlsx"));
        if(file.exists() && file.length()>0) {
            while (it.hasNext()) {
                Map.Entry<String, String> set = (Map.Entry<String, String>) it.next();
                Scanner scanner = new Scanner(file);
                String headerline = scanner.nextLine();
                while (scanner.hasNext()) {
                    line = scanner.nextLine();
                    // process the line
                    if ((set.getKey()).equalsIgnoreCase(line.trim().split("\t")[0] + " " + line.split("\t")[1])) {
                        dataflag = true;
                    } else
                        dataflag = false;
                    if (dataflag)
                        break;
                }
                if (dataflag == false) {
                    FileWriter filew = new FileWriter("src/test/resources/Data/FBdata.csv", true);
                    sb.append("\r\n");
                    sb.append(set.getKey().toString().split(" ")[0]);
                    sb.append("\t");
                    sb.append(set.getKey().toString().split(" ")[1]);
                    sb.append("\t");
                    sb.append(set.getValue());
                    sb.append("\r\n");
                    filew.append(sb);
                    filew.flush();
                }
            }
        }
        else {
            PrintWriter pw = new PrintWriter(file);
            sb.append("First name");
            sb.append("\t");
            sb.append("Last name");
            sb.append("\t");
            sb.append("Email");
            sb.append("\r\n");
            while (it.hasNext()) {
                Map.Entry<String, String> set = (Map.Entry<String, String>) it.next();
                sb.append(set.getKey().toString().split(" ")[0]);
                sb.append("\t");
                sb.append(set.getKey().toString().split(" ")[1]);
                sb.append("\t");
                sb.append(set.getValue());
                sb.append("\r\n");
            }
            pw.write(sb.toString());
            pw.close();
        }
        driver.close();
        driver.quit();
    }
    }


