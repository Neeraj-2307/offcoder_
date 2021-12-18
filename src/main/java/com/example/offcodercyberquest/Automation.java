//This java file is responsible for authorizing and returning the csrf token

package com.example.offcodercyberquest;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Automation {

    String csrf;
    private String user_name;
    private String user_password;
    public void setDriver()
    {
        System.setProperty("webdriver.chrome.driver","offcoder_/Resources/chromedriver.exe");
    }
    //checking login
    public int login(String name,String password) throws InterruptedException {
        setDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://codeforces.com/enter?back=%2F");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(By.id("handleOrEmail")).sendKeys(name);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("remember")).click();
        driver.findElement(By.className("submit")).click();

        if(driver.getCurrentUrl().equals("https://codeforces.com/enter?back=%2F") && driver.findElements(By.className("shiftUp")).size() > 0){
            System.out.println("invalid user");
            return -1;
        }
        driver.get("https://codeforces.com/problemset/problem/1610/A");
        driver.findElement(By.className("submit")).click();
        user_name = name;
        user_password = password;
        csrf = driver.getCurrentUrl();
        driver.quit();
        return 1;
    }

    //return csrf token
    public String getCSRF() throws InterruptedException {
        login(user_name,user_password);
        return csrf.split("=")[1];

    }
}
