//This java file is responsible for authorizing and returning the csrf token

package com.example.offcodercyberquest;

import com.example.offcodercyberquest.Beans.Language;
import com.example.offcodercyberquest.Beans.Problem;
import com.example.offcodercyberquest.Beans.User;
import com.example.offcodercyberquest.Scrapper.ContestScrapper;
import com.example.offcodercyberquest.Scrapper.ProblemScrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.CacheRequest;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Automation {
    private WebDriver driver;
    public Automation(){
        driver = Chrometon.getDriverInstance();
    }
    private void setUpUserFiles(String handle){
        String root = handle;
        File parent = new File(root);
        parent.mkdir();
        File favlist = new File(parent, "favLists.txt");
        try {
            favlist.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int login(String name,String password) throws InterruptedException {
        driver.get("https://codeforces.com/enter?back=%2F");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.findElement(By.id("handleOrEmail")).sendKeys(name);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("remember")).click();
        driver.findElement(By.className("submit")).click();


        if(driver.getCurrentUrl().equals("https://codeforces.com/enter?back=%2F")
                && driver.findElements(By.className("shiftUp")).size() > 0){
            System.out.println("invalid user");
            return -1;
        }

        String csrf = getCSRF();
        System.out.println(csrf);
        setUpUserFiles(name);
        return 1;
    }

    //return csrf token
    public String getCSRF() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 400);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='header-bell__img']")));
        String token =  driver.findElement(By.className("csrf-token"))
                .getAttribute("data-csrf");
        return token;
    }
    public String submit(String contestID, String problemID , File file, Language language) throws IOException, InterruptedException {
        String problem_link = generateProblemURL(contestID, problemID);

        driver.get(problem_link);

        Select langDrop = new Select(driver.findElement(By.name("programTypeId")));

        switch ( language){
            case JAVA -> langDrop.selectByValue("60");
            case JAVASCRIPT -> langDrop.selectByValue("34");
            case CPP -> langDrop.selectByValue("51");
            case PYTHON -> langDrop.selectByValue("31");
        }

        driver.findElement(By.name("sourceFile")).sendKeys(file.getAbsolutePath());
        driver.findElement(By.className("submit")).click();

        // get error messages.
        if(driver.getCurrentUrl().contains(problem_link)){
            return "SAME CODE HAS BEEN SUBMITTED";
        }else{
            try{
                String submissionAPI = "https://codeforces.com/api/user.status?handle=sal_vat_ion&from=1&count=1";
                HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(submissionAPI)).build();
                HttpClient client = HttpClient.newBuilder().build();
                Thread.sleep(5000);

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode submissionNode = objectMapper.readTree(response.body());
                String verdict = submissionNode.get("result")
                        .get(0).get("verdict").asText();
                return verdict;
            }catch (IOException e){
                return "assf";
            }
        }
    }

    public String generateProblemURL(String contestID, String problemID){
        return "https://codeforces.com/contest/" + contestID + "/problem/" + problemID;
    }
    public String generateProblemURL(String contestID){
        return  "https://codeforces.com/contest/"+contestID+"/problems";
    }
    public String download(String contestID, String problemID) throws IOException {
        String fileName = ".\\questions\\"+contestID+"_"+problemID+".txt";
        File f=new File(fileName);
        if(f.exists())
            return "Already downloaded";
        String url=generateProblemURL(contestID,problemID);
        ProblemScrapper ps=new ProblemScrapper();
        String ques=ps.myScrapper(url,contestID,problemID);

        FileOutputStream fout=new FileOutputStream(fileName);

        fout.write(ques.getBytes());
        fout.close();
        fileName=".\\"+ User.getInstance().handle+"\\questions.txt";
        BufferedWriter out = new BufferedWriter(
                new FileWriter(fileName, true));

        // Writing on output stream
        out.write(contestID+"_"+problemID);
        // Closing the connection
        out.close();
        return "successfully downloaded problem";
    }
    public String download(String contestID) throws IOException {
        String fileName = ".\\Contests\\"+contestID+".txt";
        File f=new File(fileName);
        if(f.exists())
            return "Already exisits contest";
        String url=generateProblemURL(contestID);
        ContestScrapper cs=new ContestScrapper();
        String ques=cs.myScrapper(url,contestID);
        FileOutputStream fout=new FileOutputStream(fileName);
        fout.write(ques.getBytes());
        fout.close();
        fileName=".\\"+ User.getInstance().handle+"\\contests.txt";
        BufferedWriter out = new BufferedWriter(
                new FileWriter(fileName, true));

        // Writing on output stream
        out.write(contestID);
        // Closing the connection
        out.close();
        return "successfully downloaded Contest";
    }
}
