package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "ScoreOneSampleTestAuto", group = "Tests")
public class ScoreOneSampleTestAuto extends LinearOpMode {

    // Define Motors
    private DcMotor liftRotateMotor;
    private DcMotor liftExtendMotor;

    // Define Servos
    private Servo leftClawServo;
    private Servo rightClawServo;
    private Servo clawRotateServo;

    private SampleMecanumDrive drive;

    // Constants for servo positions
    private static final double LEFT_CLAW_OPEN = 0.5;
    private static final double RIGHT_CLAW_OPEN = 0.5;
    private static final double LEFT_CLAW_CLOSED = 0.0;
    private static final double RIGHT_CLAW_CLOSED = 1.0;
    private static final double CLAW_ROTATE_INITIAL = 0.0;
    private static final double CLAW_ROTATE_SCORE = 1.0;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize hardware
        drive = new SampleMecanumDrive(hardwareMap);
        liftRotateMotor = hardwareMap.get(DcMotor.class, "liftRotate");
        liftExtendMotor = hardwareMap.get(DcMotor.class, "liftExtend");
        leftClawServo = hardwareMap.get(Servo.class, "leftClawServo");
        rightClawServo = hardwareMap.get(Servo.class, "rightClawServo");
        clawRotateServo = hardwareMap.get(Servo.class, "clawRotateServo");

        // Set motor modes
        liftRotateMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRotateMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        liftExtendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftExtendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Initialize servos
        leftClawServo.setPosition(LEFT_CLAW_CLOSED);
        rightClawServo.setPosition(RIGHT_CLAW_CLOSED);
        clawRotateServo.setPosition(CLAW_ROTATE_INITIAL);

        // Build trajectory sequence
        TrajectorySequence oneSampleStack = drive.trajectorySequenceBuilder(new Pose2d(33.03, 70.74, Math.toRadians(270.00)))
                .splineToSplineHeading(new Pose2d(56.50, 59.85, Math.toRadians(225.00)), Math.toRadians(45.00))
                .addDisplacementMarker(() -> {
                    telemetry.addLine("Lifting and Extending...");
                    telemetry.update();
                    liftRotateMotor.setTargetPosition(-5000);
                    liftExtendMotor.setTargetPosition(8000);
                    liftRotateMotor.setPower(1.0);
                    liftExtendMotor.setPower(1.0);
                })
                .waitSeconds(3)
                .addDisplacementMarker(() -> {
                    telemetry.addLine("Rotating Claw...");
                    telemetry.update();
                    clawRotateServo.setPosition(CLAW_ROTATE_SCORE);
                })
                .waitSeconds(1)
                .addDisplacementMarker(() -> {
                    telemetry.addLine("Opening Claw...");
                    telemetry.update();
                    leftClawServo.setPosition(LEFT_CLAW_OPEN);
                    rightClawServo.setPosition(RIGHT_CLAW_OPEN);
                })
                .build();

        // Set initial pose
        drive.setPoseEstimate(oneSampleStack.start());

        // Wait for start
        waitForStart();

        // Follow trajectory sequence
        if (opModeIsActive()) {
            telemetry.addLine("Starting trajectory...");
            telemetry.update();
            drive.followTrajectorySequence(oneSampleStack);

            // Stop motors at the end
            liftRotateMotor.setPower(0);
            liftExtendMotor.setPower(0);
        }
    }
}