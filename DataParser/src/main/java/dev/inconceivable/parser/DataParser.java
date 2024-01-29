package dev.inconceivable.parser;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import dev.inconceivable.data.CollectedData;
import dev.inconceivable.data.DataFrame;
import dev.inconceivable.data.DataHeader;
import dev.inconceivable.data.MotorData;
import dev.inconceivable.data.PIDState;
import dev.inconceivable.data.RobotMode;
import dev.inconceivable.data.SwerveModuleData;

public class DataParser {

    public static final byte VERSION = 1;

    private final ParserOptions options;

    public DataParser() {
        this(new ParserOptions());
    }

    public DataParser(ParserOptions options) {
        this.options = options;
    }

    public CollectedData parse(String file) throws FileNotFoundException, IOException {
        return parse(new FileInputStream(file));
    }

    public CollectedData parse(InputStream inputStream) throws IOException {
        DataInputStream data = new DataInputStream(options.useGZIPCompression ? new GZIPInputStream(inputStream) : inputStream);

        DataHeader header = new DataHeader(data.readShort(), data.readByte(), Instant.parse(data.readUTF()));

        validateHeader(header);

        List<DataFrame> frames = new ArrayList<>();
        Instant frameInstant = header.startInstant;

        if (options.parseFrames) {
            for (;;) {
                try {
                    DataFrame frame = parseDataFrame(data);

                    // Set instant value
                    if (options.calculateFrameTimes) {
                        frameInstant = frameInstant.plusMillis(frame.getDeltaMS());
                        frame.setInstant(frameInstant);
                    }

                    frames.add(frame);
                } catch (EOFException eofException) {
                    break;
                }
            }
        }

        data.close();
        return new CollectedData(header, frames);
    }

    private DataFrame parseDataFrame(DataInputStream data) throws EOFException, IOException {
        short deltaMS = data.readShort();

        RobotMode robotMode = RobotMode.fromValue(data.readByte());

        double swerveHeading = data.readDouble();

        SwerveModuleData[] swerveModules = new SwerveModuleData[] {
                parseSwerveModuleData(data),
                parseSwerveModuleData(data),
                parseSwerveModuleData(data),
                parseSwerveModuleData(data)
        };

        return new DataFrame(deltaMS, robotMode, swerveHeading, swerveModules);
    }

    private SwerveModuleData parseSwerveModuleData(DataInputStream data) throws IOException {
        return new SwerveModuleData(parseMotorData(data), parseMotorData(data), parsePIDState(data));
    }

    private MotorData parseMotorData(DataInputStream data) throws IOException {
        return new MotorData(data.readDouble(), data.readDouble(), data.readDouble());
    }

    private PIDState parsePIDState(DataInputStream data) throws IOException {
        return new PIDState(data.readDouble(), data.readDouble(), data.readDouble());
    }

    private void validateHeader(DataHeader header) {
        // Validate magic number
        if (header.magicNumber != 0x2530)
            throw new RuntimeException("Data must begin with the header '0x2530'.");

        // Validate file version
        if (header.version != VERSION)
            throw new RuntimeException(
                    String.format("Unsupported data file version '%s'! This parser only supports version %d.",
                            header.version, VERSION));
    }
}