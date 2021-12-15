module com.example.offcodercyberquest {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires java.net.http;
    requires  org.json;
    requires neko.htmlunit;
    requires org.apache.httpcomponents.httpclient;

    requires org.jsoup;
    requires java.logging;
    requires org.controlsfx.controls;
    opens com.example.offcodercyberquest to javafx.fxml;
    exports com.example.offcodercyberquest;
    opens com.example.offcodercyberquest.Controller to javafx.fxml;
    exports com.example.offcodercyberquest.Controller;
    exports com.example.offcodercyberquest.Beans;
    opens com.example.offcodercyberquest.Beans to javafx.fxml;
    exports com.example.offcodercyberquest.Scrapper;
    opens com.example.offcodercyberquest.Scrapper to javafx.fxml;

}
