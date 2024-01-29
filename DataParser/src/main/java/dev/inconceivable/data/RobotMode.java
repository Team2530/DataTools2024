package dev.inconceivable.data;

public enum RobotMode {
    DISABLED(0),
    AUTO(1),
    TELEOP(2),
    TEST(3);

    public final byte value;

    RobotMode(int value) {
        this.value = (byte) value;
    }

    public static RobotMode fromValue(byte value) {
        return RobotMode.values()[value];
    }
}
