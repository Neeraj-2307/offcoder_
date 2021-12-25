package com.example.offcodercyberquest;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Chrometon {
    static private Chrometon chrometon;
    private static WebDriver webDriver;
    private Chrometon(){
        System.setProperty("webdriver.chrome.driver",".\\Resources\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
         webDriver = new ChromeDriver(options);
    }

    private static WebDriver getWebDriver() {
        return webDriver;
    }

    public static WebDriver getDriverInstance(){
        if(chrometon == null){
            chrometon = new Chrometon();
            return getWebDriver();
        }
        return getWebDriver();
    }
}
