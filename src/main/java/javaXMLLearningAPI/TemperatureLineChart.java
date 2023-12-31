//
//package javaXMLLearningAPI;
//
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.NumberAxis;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.data.time.Second;
//import org.jfree.data.time.TimeSeries;
//import org.jfree.data.time.TimeSeriesCollection;
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.Random;
//
//public class TemperatureLineChart {
//
//    private static TimeSeries seriesMax = new TimeSeries("Máxima");
//    private static TimeSeries seriesMin = new TimeSeries("Mínima");
//
//    public static void main(String[] args) {
//        seriesMax.setMaximumItemAge(60); // 60 segundos para la serie Máxima
//        seriesMin.setMaximumItemAge(60); // 60 segundos para la serie Mínima
//
//        TimeSeriesCollection dataset = new TimeSeriesCollection();
//        dataset.addSeries(seriesMax);
//        dataset.addSeries(seriesMin);
//
//        JFreeChart lineChart = ChartFactory.createTimeSeriesChart(
//                "Temperaturas Máxima y Mínima",
//                "Tiempo",
//                "Temperatura",
//                dataset,
//                true, true, false);
//
//        XYPlot plot = lineChart.getXYPlot();
//        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//        rangeAxis.setRange(-10.0, 20.0);
//
//        ChartPanel chartPanel = new ChartPanel(lineChart);
//        JFrame frame = new JFrame();
//        frame.setContentPane(chartPanel);
//        frame.setTitle("Gráfico de Temperaturas en Tiempo Real");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//
//        new Timer(1000, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                updateDataset();
//            }
//        }).start();
//    }
//
//    private static void updateDataset() {
//        Random random = new Random();
//        Second current = new Second();
//        seriesMax.addOrUpdate(current, random.nextInt(20));
//        seriesMin.addOrUpdate(current, random.nextInt(-10, 10));
//    }
//}

package javaXMLLearningAPI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TemperatureLineChart {

    private static TimeSeries seriesMax = new TimeSeries("Máxima");
    private static TimeSeries seriesMin = new TimeSeries("Mínima");

    public static void main(String[] args) {
        seriesMax.setMaximumItemAge(60); // 60 segundos para la serie Máxima
        seriesMin.setMaximumItemAge(60); // 60 segundos para la serie Mínima

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(seriesMax);
        dataset.addSeries(seriesMin);

        JFreeChart lineChart = ChartFactory.createTimeSeriesChart(
                "Temperaturas Máxima y Mínima",
                "Tiempo",
                "Temperatura",
                dataset,
                true, true, false);

        customizeChart(lineChart);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        JFrame frame = new JFrame();
        frame.setContentPane(chartPanel);
        frame.setTitle("Gráfico de Temperaturas en Tiempo Real");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDataset(lineChart);
            }
        }).start();
    }

    private static void customizeChart(JFreeChart chart) {
        XYPlot plot = chart.getXYPlot();

        // Usar XYSplineRenderer para suavizar las líneas
        XYSplineRenderer renderer = new XYSplineRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
        plot.setRenderer(renderer);

        // Personalizar los ejes
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        rangeAxis.setLabelFont(new Font("SansSerif", Font.BOLD, 14));
        DateAxis domainAxis = (DateAxis) plot.getDomainAxis();
        domainAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        domainAxis.setLabelFont(new Font("SansSerif", Font.BOLD, 14));

        // Fondo y líneas de cuadrícula
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);

        // Transparencia
        plot.setForegroundAlpha(0.8f);
    }

    private static void updateDataset(JFreeChart chart) {
        Random random = new Random();
        Second current = new Second(new Date());
        seriesMax.addOrUpdate(current, random.nextInt(20));
        seriesMin.addOrUpdate(current, random.nextInt(-10, 10));

        // Asegurarse de que la actualización del gráfico se hace en el EDT
        SwingUtilities.invokeLater(() -> {
            chart.fireChartChanged();
            XYPlot plot = chart.getXYPlot();
            DateAxis axis = (DateAxis) plot.getDomainAxis();
            axis.setRange(new Date(System.currentTimeMillis() - 60000), new Date(System.currentTimeMillis()));
        });
    }
}
