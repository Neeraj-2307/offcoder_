package com.example.offcodercyberquest.Controller;
import com.example.offcodercyberquest.HelloApplication;
import com.example.offcodercyberquest.UserStats;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*
*
*
* getting data from the json and displaying the stats of the user which includes
* data of user
* history of submitted questions
* stats
* graph
*
*
* */
public class StatsController implements Initializable {

    @FXML
    private Button dashboard_button;

    @FXML
    private ListView<String> history;

    @FXML
    private PieChart piechart;

    @FXML
    private LineChart<String ,Number> rating;

    @FXML
    private BarChart<String,Number> solved;

    @FXML
    private Label currentRating;

    @FXML
    private Label maxRating;

    @FXML
    private Label ratingDecrease;

    @FXML
    private Label ratingIncrease;

    @FXML
    private Label totalContest;

    @FXML
    private Label handle;

    @FXML
    private ImageView avatar;



    UserStats data = new UserStats();
    ObservableList<PieChart.Data> pieChartData = data.getPieChart();

    public void stats_controller() throws IOException, InterruptedException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        XYChart.Series chart = data.getBarChart();
        solved.getData().add(chart);
        for (final XYChart.Series<String, Number> series : solved.getData()) {
            for (final XYChart.Data<String, Number> data : series.getData()) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(data.getYValue().toString());
                Tooltip.install(data.getNode(), tooltip);
            }
        }
        try {
            XYChart.Series lineChart = data.getLineChart();
            rating.getData().add(lineChart);
            for (final XYChart.Series<String, Number> series : rating.getData()) {
                for (final XYChart.Data<String, Number> data : series.getData()) {
                    Tooltip tooltip = new Tooltip();
                    tooltip.setText(data.getXValue().toString() + " : " + data.getYValue().toString());
                    Tooltip.install(data.getNode(), tooltip);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        piechart.setData(pieChartData);
        piechart.setClockwise(true);
        piechart.setLabelsVisible(false);
        piechart.setStartAngle(180);
        ArrayList<String> elements = data.getListItem();
        for(String item : elements)
        {
            history.getItems().add(item);
        }
        currentRating.setText(data.getcurrentRating());
        maxRating.setText(data.getmaxRating());
        totalContest.setText(data.getContest());
        ratingIncrease.setText(data.getmaxIncrease());
        ratingDecrease.setText(data.getmaxDecrease());
        handle.setText(data.getUser_name());

        Image image = new Image(data.getAvatar());
        avatar.setImage(image);
    }

    @FXML
    void load_dashboard(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("dashboard.fxml");
    }
}

