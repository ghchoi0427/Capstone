package web.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import web.controller.CSVFactory;
import web.controller.SecondStringParser;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class GraphScreen extends JFrame {

    private static final long serialVersionUID = 1L;

    public GraphScreen(String title) {
        super(title);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "temperature  & humidity chart", // Chart
                "time", // X-Axis Label
                "", // Y-Axis Label
                null);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(255, 228, 196));
        final NumberAxis humidityAxis = new NumberAxis("humidity(%)");
        final NumberAxis temperatureAxis = new NumberAxis("temperature(°C)");
        humidityAxis.setAutoRangeIncludesZero(false);
        temperatureAxis.setAutoRangeIncludesZero(false);
        plot.setRangeAxis(0, temperatureAxis);
        plot.setRangeAxis(1, humidityAxis);
        plot.setDataset(0, createTempDataset());
        plot.setDataset(1, createHumidDataset());
        plot.mapDatasetToRangeAxis(0, 0);
        plot.mapDatasetToRangeAxis(1, 1);
        plot.setRenderer(0, new StandardXYItemRenderer());
        plot.setRenderer(1, new StandardXYItemRenderer());

        ChartPanel panel = new ChartPanel(chart);
        panel.setBounds(0, 0, 500, 400);
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(50, 10, 100, 20);
        btnRefresh.addActionListener(e -> {
            setVisible(false);
            MainScreen.showGraph();
        });
        panel.setLayout(null);
        panel.add(btnRefresh);
        setContentPane(panel);
    }

    private XYDataset createTempDataset() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        List<String[]> data = null;
        try {
            data = CSVFactory.getCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TimeSeries seriesTemp = new TimeSeries("temperature");
        for (String[] entity : Objects.requireNonNull(data)) {
            seriesTemp.add(SecondStringParser.StringToSecond(entity[0]), Integer.parseInt(entity[1]));
        }

        dataset.addSeries(seriesTemp);
        return dataset;
    }

    private XYDataset createHumidDataset() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        List<String[]> data = null;
        try {
            data = CSVFactory.getCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TimeSeries seriesHumid = new TimeSeries("humidity");
        for (String[] entity : Objects.requireNonNull(data)) {
            seriesHumid.add(SecondStringParser.StringToSecond(entity[0]), Integer.parseInt(entity[2]));
        }

        dataset.addSeries(seriesHumid);
        return dataset;
    }
}