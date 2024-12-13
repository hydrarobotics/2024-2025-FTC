package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.apriltag.AprilTagDetection;


import java.util.ArrayList;
import java.util.HashMap;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;


public class AprilTagLocalization {

    private HardwareMap hardwareMap;
    private OpenCvCamera camera;
    private AprilTagDetectionPipeline aprilTagDetectionPipeline;

    // Lens intrinsics (adjust based on your camera calibration)
    private final double fx = 578.272;
    private final double fy = 578.272;
    private final double cx = 402.145;
    private final double cy = 221.506;

    private final double tagSize = 0.166 * 39.3701; // Convert meters to inches

    private final HashMap<Integer, Pose2d> tagFieldPositions;

    public AprilTagLocalization(HashMap<Integer, Pose2d> tagFieldPositions, HardwareMap hardwareMap) {
        this.tagFieldPositions = tagFieldPositions;
        this.hardwareMap = hardwareMap;
    }

    public void initializeCamera(int cameraMonitorViewId, String webcamName) {
        camera = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, webcamName),
                cameraMonitorViewId
        );
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagSize, fx, fy, cx, cy);
        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Error", "Error Code: " + errorCode);
                telemetry.update();
            }
        });

    }

    public Pose2d getRobotPose() {
        ArrayList<AprilTagDetection> detections = aprilTagDetectionPipeline.getLatestDetections();

        if (!detections.isEmpty()) {
            AprilTagDetection closestTag = findClosestTag(detections);
            if (closestTag != null) {
                return calculateRobotPoseFromTag(closestTag);
            }
        }
        return null; // No valid detections
    }

    private AprilTagDetection findClosestTag(ArrayList<AprilTagDetection> detections) {
        AprilTagDetection closestTag = null;
        double minDistance = Double.MAX_VALUE;

        for (AprilTagDetection tag : detections) {
            if (tagFieldPositions.containsKey(tag.id)) {
                double distance = Math.hypot(tag.pose.x * 39.3701, tag.pose.y * 39.3701); // Convert to inches
                if (distance < minDistance) {
                    minDistance = distance;
                    closestTag = tag;
                }
            }
        }
        return closestTag;
    }

    private Pose2d calculateRobotPoseFromTag(AprilTagDetection tag) {
        Pose2d tagPoseOnField = tagFieldPositions.get(tag.id);

        double tagToCameraX = tag.pose.x * 39.3701;
        double tagToCameraY = tag.pose.y * 39.3701;
        double tagToCameraHeading = Math.atan2(tag.pose.R.get(1, 0), tag.pose.R.get(0, 0));


        Pose2d cameraOffset = new Pose2d(8.0, 0, 0); // Camera offset in inches

        Pose2d cameraPoseOnField = new Pose2d(
                tagPoseOnField.getX() - tagToCameraX,
                tagPoseOnField.getY() - tagToCameraY,
                tagPoseOnField.getHeading() - tagToCameraHeading
        );

        return new Pose2d(
                cameraPoseOnField.getX() - cameraOffset.getX(),
                cameraPoseOnField.getY() - cameraOffset.getY(),
                cameraPoseOnField.getHeading()
        );
    }
}
