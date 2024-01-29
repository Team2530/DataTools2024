package dev.inconceivable.parser;

public class ParserOptions {

    // If this is disabled, only the header will be parsed.
    public boolean parseFrames = true;

    // If enabled, this will cause the parser to calculate the exact time each frame occured.
    public boolean calculateFrameTimes = true; 

    public boolean useGZIPCompression = true;

}
