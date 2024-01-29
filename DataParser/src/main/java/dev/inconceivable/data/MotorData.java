package dev.inconceivable.data;

public class MotorData {
    private final double value;
    private final double position;
    private final double velocity;

    public MotorData(double value, double position, double velocity) {
        this.value = value;
        this.position = position;
        this.velocity = velocity;
    }

    public double getValue() {
        return value;
    }

    public double getPosition() {
        return position;
    }

    public double getVelocity() {
        return velocity;
    }
}
