package dev.inconceivable.viewer;

import dev.inconceivable.data.CollectedData;
import dev.inconceivable.data.DataFrame;
import dev.inconceivable.data.SwerveModuleData;
import dev.inconceivable.parser.DataParser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class Main extends JPanel implements KeyListener {
    public static void main(String[] args) {
        JFrame frame = new JFrame("");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setUndecorated(true);

        frame.addKeyListener(new Main());


        JFreeChart chart = ChartFactory.createLineChart("Delta time (ms) over time (periodics)", "Time (periodics)", "Some values (?)", createDataset(), PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel( chart );
        frame.add(chartPanel);

        frame.setVisible(true);
    }

    private static CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

        try {
            CollectedData data = new DataParser().parse("2024-01-29-19-29.log.bin");
            for (int i = 0; i < data.getFrames().size(); i++) {
                DataFrame frame = data.getFrames().get(i);

                int j = 0;
                for(SwerveModuleData module : frame.getSwerveModules()) {

//                    dataset.addValue(module.getDriveMotor().getPosition(), j + "-value", String.valueOf(i));
                    dataset.addValue(module.getSteerMotor().getPosition(), j + "-velocity", String.valueOf(i));
                    j++;
                }
//                dataset.addValue(data.getFrames().get(i).getSwerveHeading(), "swerve_heading", String.valueOf(i));
//                dataset.addValue(data.getFrames().get(i).getDeltaMS(), "deltams", String.valueOf(i));
            }
//            data.getFrames().forEach(df -> dataset.addValue(df.getDeltaMS(), "deltams", "idk"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return dataset;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
