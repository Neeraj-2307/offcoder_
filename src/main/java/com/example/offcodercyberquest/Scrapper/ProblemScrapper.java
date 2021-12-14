package com.example.offcodercyberquest.Scrapper;

import com.example.offcodercyberquest.Beans.Problem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ProblemScrapper {
    private String question;
    private String input;
    private String output;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
    public void myScrapper(String url) throws IOException {
        Document jsoup= Jsoup.connect(url).get();
        Problem p=new Problem();
        p.setTimeLimit(jsoup.getElementsByAttributeValue("class","time-limit").html());

        p.setMemoryLimit(jsoup.getElementsByAttributeValue("class","memory-limit").html());

        p.setInputSpecification(jsoup.getElementsByAttributeValue("class","input-specification").toString());

        p.setOutputSpecification(jsoup.getElementsByAttributeValue("class","output-specification").html());
        p.setSamples(jsoup.getElementsByTag("pre").html());
        p.setTitle(jsoup.getElementsByAttributeValue("class","title").html());
        Elements s=jsoup.getElementsByAttributeValue("class","header").nextAll();
        StringBuilder problem= new StringBuilder();
        for(Element e : s){
            problem.append(e.html()).append("\n");
        }
        p.setStatement(problem.toString());
          System.out.println(p.getSamples());

        /*
        problem = {
        "title": soup.find('div', 'title').string,
        "timeLimit": split_limit(soup.find('div', 'time-limit').contents[1].string),
        "memoryLimit": split_limit(soup.find('div', 'memorxxy-limit').contents[1].string),
        "statement": get_statement(soup),
        "inputSpecification": get_content(soup, 'input-specification'),
        "outputSpecification": get_content(soup, 'output-specification'),
        "samples": get_sample_tests(soup),
        "note": get_content(soup, 'note'),
    }
        */
    }
}
