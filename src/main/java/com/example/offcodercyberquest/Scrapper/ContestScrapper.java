package com.example.offcodercyberquest.Scrapper;

import com.example.offcodercyberquest.Beans.Problem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;

public class ContestScrapper {
    private String question;

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    private String contestName;
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }


    public String myScrapper(String url,String contestid) throws IOException {

        System.out.println(url);
        Document jsoup= Jsoup.connect(url).get();
//        Problem p=new Problem();
//        p.setTimeLimit(jsoup.getElementsByAttributeValue("class","time-limit").html());
//
//        p.setMemoryLimit(jsoup.getElementsByAttributeValue("class","memory-limit").html());
//
//        p.setInputSpecification(jsoup.getElementsByAttributeValue("class","input-specification").toString());
//
//        p.setOutputSpecification(jsoup.getElementsByAttributeValue("class","output-specification").html());
//
//        p.setSamples(jsoup.getElementsByTag("pre").html());
//
//        p.setTitle(jsoup.getElementsByAttributeValue("class","title").html());
        setContestName(String.valueOf(jsoup.getElementsByAttributeValue("class","caption")));
        Elements s=jsoup.getElementsByAttributeValue("class","caption").nextAll();

        StringBuilder problem= new StringBuilder();

        for(Element e : s){
            problem.append(e.html()).append("\n");
        }
        System.out.println(s);
       // p.setStatement(problem.toString());

//            System.out.println(p.getStatement());
        //System.out.println(p.getSamples());

//
        String ques1="<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +"<style>\n" +
                "body{\n" +
                "    background-color: #212121;\n" +
                "    color: aliceblue;\n" +
                "    text-align: center;\n " +
                "    margin-left:20px;\n" +
                "    margin-right:20px;\n" +
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
                "<body>\n"+getContestName();
//
        String ques2=problem.toString();
        if(ques2.length()<2)
            ques2="<h3>CONTEST YET TO START<h3>";
        String ques3 = "</body>\n" +
                "</html>";
       setQuestion(ques1+ques2+ques3);
       return getQuestion();

    }


}
