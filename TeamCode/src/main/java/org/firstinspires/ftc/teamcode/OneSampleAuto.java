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

@Autonomous(name = "OneSampleAuto", group = "TrajectorySequenceTest")
public class OneSampleAuto extends LinearOpMode {
    SampleMecanumDrive drive;


    public Servo leftClawServo;
    public Servo rightClawServo;
    public Servo clawRotateServo;

    @Override
    public void runOpMode() throws InterruptedException {
        RobotHardware robot = new RobotHardware();
        robot.init(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);
        robot.liftRotateMotor.setPower(1.0);
        robot.liftRotateMotor.setTargetPosition(0);
        robot.liftExtendMotor.setPower(1.0);
        robot.liftExtendMotor.setTargetPosition(0);
        robot.liftRotateMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.liftExtendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leftClawServo.setPosition(0.0);
        robot.rightClawServo.setPosition(1.0);

        robot.clawRotateServo.setPosition(0.0);

        //Target1
        TrajectorySequence oneSampleStack = drive.trajectorySequenceBuilder(new Pose2d(33.03, 70.74, Math.toRadians(270.00)))
                .splineToSplineHeading(new Pose2d(56.50, 59.85, Math.toRadians(225.00)), Math.toRadians(45.00))
                .addDisplacementMarker(() -> {
                    robot.liftRotateMotor.setPower(1.0);
                    robot.liftRotateMotor.setTargetPosition(-5000);
                    robot.liftExtendMotor.setPower(1.0);
                    robot.liftExtendMotor.setTargetPosition(8000);
                })
                .waitSeconds(4)
                .addDisplacementMarker(() -> {
                    robot.leftClawServo.setPosition(0.5);
                    robot.rightClawServo.setPosition(0.5);

                    robot.clawRotateServo.setPosition(1.0);
                })
                .waitSeconds(6)
                .build();
        drive.setPoseEstimate(oneSampleStack.start());
        ;
        waitForStart();
        drive.followTrajectorySequence(oneSampleStack);
    }
}
