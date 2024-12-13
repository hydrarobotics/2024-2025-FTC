package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.AprilTagLocalization;
import org.firstinspires.ftc.teamcode.AprilTagConstants;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "AprilTag Auto Localization", group = "ExperimentalTesting")
public class AprilTagAutoLocalization extends LinearOpMode {

    private SampleMecanumDrive drive;
    private AprilTagLocalization aprilTagLocalization;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize drive and AprilTagLocalization
        drive = new SampleMecanumDrive(hardwareMap);
        aprilTagLocalization = new AprilTagLocalization(AprilTagConstants.TAG_FIELD_POSITIONS, hardwareMap);

        // Camera setup
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()
        );
        aprilTagLocalization.initializeCamera(cameraMonitorViewId, "Webcam 1");

        telemetry.addLine("Waiting for start...");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            // Relocalize using AprilTags
            Pose2d robotPose = aprilTagLocalization.getRobotPose();
            if (robotPose != null) {
                drive.setPoseEstimate(robotPose);
                telemetry.addData("Pose Updated", robotPose);
            } else {
                telemetry.addLine("No valid AprilTags detected.");
            }
            telemetry.update();

            // Build and execute a trajectory
            TrajectorySequence trajectory = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .splineToSplineHeading(new Pose2d(56.50, 59.85, Math.toRadians(225.00)), Math.toRadians(45.00))
                    .build();

            drive.followTrajectorySequence(trajectory);

            // Final telemetry
            telemetry.addLine("Autonomous Complete!");
            telemetry.update();
        }
    }
}
