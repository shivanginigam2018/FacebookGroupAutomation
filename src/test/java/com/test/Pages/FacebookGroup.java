package com.test.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class FacebookGroup {

    WebDriver driver = null;
    public void navigateToFBandLogin() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/shipr/Downloads/chromedriver_win32/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.facebook.com/");
        System.out.println("Success");
        driver.findElement(By.id("email")).sendKeys("shivanginigam4@gmail.com");
        driver.findElement(By.id("pass")).sendKeys("Facebook@2021");
        driver.findElement(By.name("login")).click();
    }
//
//    public void MemberRequestDataupdate() {
////        driver.navigate().to("https://www.facebook.com/groups/180913030412171/member-requests");
//        System.out.println("inside");
//    }




}
