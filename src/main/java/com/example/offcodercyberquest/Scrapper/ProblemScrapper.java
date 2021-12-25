package com.example.offcodercyberquest.Scrapper;

import com.example.offcodercyberquest.Beans.Problem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

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


    public void myScrapper(String url,String contestid, String idx) throws IOException {
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



        p.setStatement(problem.toString());

//            System.out.println(p.getStatement());
          System.out.println(problem);


        String ques1="<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +"<style>\n" +
                "body{\n" +
                "    background-color: black;\n" +
                "    color: aliceblue;\n" +
                "    text-align: center;\n" +
                "}\n" +
                ".title{\n" +
                "    color: aquamarine;\n" +
                "}\n" +
                ".section-title{\n" +
                "    color: aqua;\n" +
                "}\n" +
                "pre{\n" +
                "text-align: left;\n" +
                "}\n" +
                "</style>"+"<script type=\"text/x-mathjax-config\">\n" +
                "    MathJax.Hub.Config({\n" +
                "      tex2jax: {inlineMath: [['$$$','$$$']], displayMath: [['$$$$$$','$$$$$$']]}\n" +
                "    });\n" +
                "    MathJax.Hub.Register.StartupHook(\"End\", function () {\n" +
                "        Codeforces.runMathJaxListeners();\n" +
                "    });\n" +
                "    </script>\n" +
                "    <script type=\"text/javascript\" async\n" +
                "            src=\"https://mathjax.codeforces.org/MathJax.js?config=TeX-AMS_HTML-full\"\n" +
                "    >\n" +
                "    </script>"+
                "<title>Page Title</title>\n" +
                "</head>\n" +
                "<body>";

                String ques2=p.getTitle()+"\n"+p.getMemoryLimit()+"\n"+p.getTimeLimit()+"\n"+p.getStatement();

                 String ques3 = "</body>\n" +
                "</html>";
                 String ques=ques1+ques2+ques3;
                 String fileName = ".\\questions\\"+contestid+idx+".txt";
                FileOutputStream fout=new FileOutputStream(fileName);
                fout.write(ques.getBytes());
                fout.close();
    }
}
