package web.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import web.controller.CSVFactory;
import web.controller.SecondStringParser;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class GraphScreen extends JFrame {

    private static final long serialVersionUID = 1L;

    public GraphScreen(String title) {
        super(title);

        XYDataset dataset = createDB();
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Time Series Chart Example", // Chart
                "time", // X-Axis Label
                "temperature", // Y-Axis Label
                dataset);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(255, 228, 196));

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private XYDataset createDB() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        List<String[]> data = null;
        try {
            data = CSVFactory.getCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TimeSeries seriesTemp = new TimeSeries("temperature");
        for (String[] entity : data) {
            seriesTemp.add(SecondStringParser.StringToSecond(entity[0]), Integer.parseInt(entity[1]));
            System.out.println(Integer.parseInt(entity[1]));
        }

        TimeSeries seriesHumid = new TimeSeries("humidity");
        for (String[] entity : data) {
            seriesHumid.add(SecondStringParser.StringToSecond(entity[0]), Integer.parseInt(entity[2]));
            System.out.println(Integer.parseInt(entity[2]));
        }

        dataset.addSeries(seriesTemp);
        dataset.addSeries(seriesHumid);
        return dataset;
    }
}