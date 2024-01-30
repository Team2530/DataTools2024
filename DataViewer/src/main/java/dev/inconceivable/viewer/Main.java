package dev.inconceivable.viewer;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.flow.FlowPlot;
import org.jfree.data.flow.FlowDatasetUtils;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("a");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Integer[][] data = new Integer[][] {{-3, -2}, {-1, 1}, {2, 3}};
        CategoryDataset<String, String> dataset
                = DatasetUtils.createCategoryDataset("S", "C", data);
        JFreeChart chart = ChartFactory.createBarChart("Bar Chart", "Domain", "Range",
                dataset, PlotOrientation.HORIZONTAL, true, true, true);
    }
}
