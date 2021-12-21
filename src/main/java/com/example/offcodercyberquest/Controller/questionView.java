package com.example.offcodercyberquest.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class questionView implements Initializable {
String html="";
@FXML
private WebView webView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String html = fetchViaFile();
        WebEngine engine = webView.getEngine();
        engine.loadContent(html);
        webView.setContextMenuEnabled(false);
    }
    String fetchViaFile() {
        String fileName = "C:\\Users\\Anshi Goel\\Documents\\javaWork\\offcoder_\\questions\\ques1.txt";
        File file = new File(fileName);

        StringBuffer html = new StringBuffer();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = reader.readLine()) != null) {
                html.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return html.toString();
    }
}
