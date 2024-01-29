package dev.inconceivable.data;

public class PIDState {
    public final double p, i, d;

    public PIDState(double p, double i, double d) {
        this.p = p;
        this.i = i;
        this.d = d;
    }
}
