package web.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;

public class GraphScreen extends JFrame {

    private static final long serialVersionUID = 1L;

    public GraphScreen(String title) {
        super(title);

        XYDataset dataset = createDataset();
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

    private XYDataset createDataset() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        TimeSeries series1 = new TimeSeries("temperature");
        series1.add(new Day(1, 1, 2017), 50);
        series1.add(new Day(2, 1, 2017), 40);
        series1.add(new Day(3, 1, 2017), 45);
        series1.add(new Day(4, 1, 2017), 30);
        series1.add(new Day(5, 1, 2017), 50);
        series1.add(new Day(6, 1, 2017), 45);
        series1.add(new Day(7, 1, 2017), 60);
        series1.add(new Day(8, 1, 2017), 45);
        series1.add(new Day(9, 1, 2017), 55);
        series1.add(new Day(10, 1, 2017), 48);
        series1.add(new Day(11, 1, 2017), 60);
        series1.add(new Day(12, 1, 2017), 45);
        series1.add(new Day(13, 1, 2017), 65);
        series1.add(new Day(14, 1, 2017), 45);
        series1.add(new Day(15, 1, 2017), 55);
        dataset.addSeries(series1);

        TimeSeries series2 = new TimeSeries("humidity");
        series2.add(new Day(1, 1, 2017), 40);
        series2.add(new Day(2, 1, 2017), 35);
        series2.add(new Day(3, 1, 2017), 26);
        series2.add(new Day(4, 1, 2017), 45);
        series2.add(new Day(5, 1, 2017), 40);
        series2.add(new Day(6, 1, 2017), 35);
        series2.add(new Day(7, 1, 2017), 45);
        series2.add(new Day(8, 1, 2017), 48);
        series2.add(new Day(9, 1, 2017), 31);
        series2.add(new Day(10, 1, 2017), 32);
        series2.add(new Day(11, 1, 2017), 21);
        series2.add(new Day(12, 1, 2017), 35);
        series2.add(new Day(13, 1, 2017), 10);
        series2.add(new Day(14, 1, 2017), 25);
        series2.add(new Day(15, 1, 2017), 15);
        dataset.addSeries(series2);


        return dataset;
    }
}