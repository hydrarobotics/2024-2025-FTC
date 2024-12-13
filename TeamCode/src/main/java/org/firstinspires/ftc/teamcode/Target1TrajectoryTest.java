package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "Target 1", group = "TrajectorySequenceTest")
public class Target1TrajectoryTest extends LinearOpMode {
    SampleMecanumDrive drive;

    public Servo airplaneLauncherServo;
    public Servo pixelHolderTiltServo1;
    public Servo pixelHolderTiltServo2;
    public Servo outtakeArmServo1;
    public Servo outtakeArmServo2;
    public Servo pixelDropServo;
    public Servo purplePixelServo;
    public Servo cameraServo;


    @Override
    public void runOpMode() throws InterruptedException {
        RobotHardware robot = new RobotHardware();
        robot.init(hardwareMap);
        robot.liftRotateMotor.setTargetPosition(-5000);
        robot.liftExtendMotor.setTargetPosition(8000);
        robot.liftRotateMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.liftExtendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.liftRotateMotor.setPower(1.0);
        robot.liftRotateMotor.setPower(1.0);

    }
}
