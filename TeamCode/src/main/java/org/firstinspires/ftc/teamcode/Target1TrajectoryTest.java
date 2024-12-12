package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
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

        //Target1
        TrajectorySequence Target1 = drive.trajectorySequenceBuilder(new Pose2d(-36.52, -66.41, Math.toRadians(90.00)))
                .splineTo(new Vector2d(-35.55, -13.48), Math.toRadians(88.94))
                .lineToConstantHeading(new Vector2d(56.36, -13.76))
                .build();

        drive.setPoseEstimate(Target1.start());
        ;
        waitForStart();
        drive.followTrajectorySequence(Target1);
    }
}
