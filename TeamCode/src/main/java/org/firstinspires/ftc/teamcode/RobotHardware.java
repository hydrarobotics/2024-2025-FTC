package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

public class RobotHardware {

    //These motors are used to control the drivetrain
    public DcMotor LFMotor; //Left Front Motor
    public DcMotor LBMotor; //Left Back Motor
    public DcMotor RBMotor; //Right Back Motor
    public DcMotor RFMotor; //Right Front Motor

    //These motors control the Intake and Outtake Systems
    public DcMotor liftRotateMotor; //Motor that actuates the lift motor
    public DcMotor liftExtendMotor; //Motor that actuates the extension motor

    public Servo leftClawServo;
    public Servo rightClawServo;
    public Servo clawRotateServo;

    //This is the onboard imu located on the controller hub
    public IMU imu;

    //This method initializes actuators and sensors
    public void init(HardwareMap hardwareMap) {

        LFMotor = hardwareMap.get(DcMotor.class, "LF");
        LBMotor = hardwareMap.get(DcMotor.class, "LB");
        RBMotor = hardwareMap.get(DcMotor.class, "RB");
        RFMotor = hardwareMap.get(DcMotor.class, "RF");

        liftRotateMotor = hardwareMap.get(DcMotor.class, "liftRotate");
        liftExtendMotor = hardwareMap.get(DcMotor.class, "liftExtend");

        leftClawServo = hardwareMap.get(Servo.class, "leftClawServo");
        rightClawServo = hardwareMap.get(Servo.class, "rightClawServo");
        clawRotateServo = hardwareMap.get(Servo.class, "clawRotateServo");

        LFMotor.setPower(0.0);
        LBMotor.setPower(0.0);
        RBMotor.setPower(0.0);
        RFMotor.setPower(0.0);

        liftRotateMotor.setPower(0.0);
        liftExtendMotor.setPower(0.0);

        leftClawServo.setPosition(0.5);
        rightClawServo.setPosition(0.5);
        clawRotateServo.setPosition(1.0);

        LFMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        LBMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        RBMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        RFMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);

        liftRotateMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftExtendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        LFMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LBMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RBMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RFMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        liftRotateMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftExtendMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
