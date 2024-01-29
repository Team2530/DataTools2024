package dev.inconceivable.data;

import java.util.List;

public class CollectedData {
    private final DataHeader header;
    private final List<DataFrame> frames;

    public CollectedData(DataHeader header, List<DataFrame> frames) {
        this.header = header;
        this.frames = frames;
    }

    public DataHeader getHeader() {
        return header;
    }

    public List<DataFrame> getFrames() {
        return frames;
    }
}