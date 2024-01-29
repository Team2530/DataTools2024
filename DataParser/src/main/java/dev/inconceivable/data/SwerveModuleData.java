package dev.inconceivable.data;

public class SwerveModuleData {

    private final MotorData driveMotor;

    private final MotorData steerMotor;

    private final PIDState pidState;

    public SwerveModuleData(MotorData driveMotor, MotorData steerMotor, PIDState pidState) {
        this.driveMotor = driveMotor;
        this.steerMotor = steerMotor;
        this.pidState = pidState;
    }

    public MotorData getDriveMotor() {
        return driveMotor;
    }

    public MotorData getSteerMotor() {
        return steerMotor;
    }

    public PIDState getPIDState() {
        return pidState;
    }
}
