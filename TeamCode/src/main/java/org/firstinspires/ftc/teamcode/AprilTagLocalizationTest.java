package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

import java.util.HashMap;

@TeleOp(name = "AprilTag Localization Test", group = "ExperimentalTesting")
public class AprilTagLocalizationTest extends LinearOpMode {

    private AprilTagLocalization aprilTagLocalization;

    @Override
    public void runOpMode() {
        // Define AprilTag field positions
        HashMap<Integer, Pose2d> tagFieldPositions = new HashMap<Integer, Pose2d>() {{
            put(11, new Pose2d(-72.0, 36.0, Math.toRadians(90)));  // Blue Alliance Audience Wall
            put(12, new Pose2d(-36.0, -72.0, Math.toRadians(0))); // Blue Alliance Wall
            put(13, new Pose2d(-72.0, -72.0, Math.toRadians(180))); // Blue Alliance Rear Wall
            put(14, new Pose2d(72.0, -72.0, Math.toRadians(180)));  // Red Alliance Rear Wall
            put(15, new Pose2d(36.0, 72.0, Math.toRadians(0)));    // Red Alliance Wall
            put(16, new Pose2d(72.0, 36.0, Math.toRadians(90)));   // Red Alliance Audience Wall
        }};

        // Initialize AprilTagLocalization
        aprilTagLocalization = new AprilTagLocalization(tagFieldPositions, hardwareMap);

        // Get the camera monitor view ID
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()
        );

        // Initialize the camera
        aprilTagLocalization.initializeCamera(cameraMonitorViewId, "Webcam 1");

        telemetry.addLine("AprilTag Localization Test Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Attempt to localize the robot
            Pose2d robotPose = aprilTagLocalization.getRobotPose();

            if (robotPose != null) {
                telemetry.addData("Robot Pose", robotPose);
                telemetry.addData("X (inches)", robotPose.getX());
                telemetry.addData("Y (inches)", robotPose.getY());
                telemetry.addData("Heading (degrees)", Math.toDegrees(robotPose.getHeading()));
            } else {
                telemetry.addLine("No valid AprilTags detected.");
            }

            telemetry.update();
        }
    }
}
