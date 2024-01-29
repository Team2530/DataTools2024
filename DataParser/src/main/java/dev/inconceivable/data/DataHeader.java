package dev.inconceivable.data;

import java.time.Instant;

public class DataHeader {
    public final short magicNumber;
    public final byte version;
    public final Instant startInstant;

    public DataHeader(short magicNumber, byte version, Instant startInstant) {
        this.magicNumber = magicNumber;
        this.version = version;
        this.startInstant = startInstant;
    }
}