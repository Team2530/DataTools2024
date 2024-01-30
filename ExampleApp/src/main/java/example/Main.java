package example;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;

import javax.swing.JFileChooser;

import dev.inconceivable.data.*;
import dev.inconceivable.parser.*;

public class Main {
    public static void main(String[] args) throws Throwable {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.showOpenDialog(null);
        jFileChooser.setVisible(true);

        File f = jFileChooser.getSelectedFile();

        DataParser parser = new DataParser();

        CollectedData data = parser.parse(f.getAbsolutePath());
        Instant start = data.getHeader().startInstant;

        System.out.println(start.toString());
    }
}
