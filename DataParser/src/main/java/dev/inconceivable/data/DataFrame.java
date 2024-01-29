package dev.inconceivable.data;

import java.time.Instant;

public class DataFrame {
    private final short deltaMS;
    private final RobotMode robotMode;
    private final double swerveHeading;
    private final SwerveModuleData[] swerveModules;

    private Instant instant;

    public DataFrame(short deltaMS, RobotMode robotMode, double swerveHeading, SwerveModuleData[] swerveModules) {
        this.deltaMS = deltaMS;
        this.robotMode = robotMode;
        this.swerveHeading = swerveHeading;
        this.swerveModules = swerveModules;
    }

    public short getDeltaMS() {
        return deltaMS;
    }

    public RobotMode getRobotMode() {
        return robotMode;
    }

    public double getSwerveHeading() {
        return swerveHeading;
    }

    public SwerveModuleData[] getSwerveModules() {
        return swerveModules;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    
}
