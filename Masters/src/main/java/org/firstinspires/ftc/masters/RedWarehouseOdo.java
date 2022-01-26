package org.firstinspires.ftc.masters;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.masters.drive.DriveConstants;
import org.firstinspires.ftc.masters.drive.SampleMecanumDrive;
import org.firstinspires.ftc.masters.trajectorySequence.TrajectorySequence;

import java.util.Date;

@Autonomous(name="Red - Warehouse (Park City)", group = "competition")
public class RedWarehouseOdo extends LinearOpMode {

    final int SERVO_DROP_PAUSE=800;

    @Override
    public void runOpMode() throws InterruptedException {
      //  robot = new RobotClass(hardwareMap,telemetry,this);

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap, this, telemetry);

        drive.openCVInnitShenanigans("red");
        MultipLeCameraCV.ShippingElementDeterminationPipeline.FreightPosition freightLocation = null;

        freightLocation = drive.analyze();

        Pose2d startPose = new Pose2d(new Vector2d(13.5, -63),Math.toRadians(90));

        drive.setPoseEstimate(startPose);
        TrajectorySequence fromStartToHub = drive.trajectorySequenceBuilder(startPose)
                .strafeTo(new Vector2d(-13, -45))
                .build();
        TrajectorySequence fromHubToWarehouse = drive.trajectorySequenceBuilder(fromStartToHub.end())
                .lineToSplineHeading(new Pose2d(new Vector2d(5, -60), Math.toRadians(180)))
                .addTemporalMarker(1,()->{drive.retract();})
                .splineToLinearHeading(new Pose2d( new Vector2d(48, -66), Math.toRadians(180)), Math.toRadians(0))
                .build();
        drive.linearSlideServo.setPosition(FreightFrenzyConstants.DUMP_SERVO_LIFT);

        waitForStart();
        drive.CV.duckWebcam.stopStreaming();

        long startTime = new Date().getTime();
        long time = 0;

        while (time < 200 && opModeIsActive()) {
            time = new Date().getTime() - startTime;
            freightLocation = drive.analyze();

            telemetry.addData("Position", freightLocation);
            telemetry.update();
        }
        switch (freightLocation) {
            case LEFT:
                drive.linearSlideMotor.setTargetPosition(FreightFrenzyConstants.SLIDE_LOW);
                break;
            case MIDDLE:
                drive.linearSlideMotor.setTargetPosition(FreightFrenzyConstants.SLIDE_MIDDLE);
                break;
            case RIGHT:
                drive.linearSlideMotor.setTargetPosition(FreightFrenzyConstants.SLIDE_TOP);
                break;
            default:
                drive.linearSlideMotor.setTargetPosition(FreightFrenzyConstants.SLIDE_TOP);
        }
        drive.linearSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drive.linearSlideMotor.setPower(.8);

        if (isStopRequested()) return;

        drive.followTrajectorySequence(fromStartToHub);
        drive.linearSlideServo.setPosition(FreightFrenzyConstants.DUMP_SERVO_DROP);
        drive.pause(SERVO_DROP_PAUSE);
        drive.linearSlideServo.setPosition(FreightFrenzyConstants.DUMP_SERVO_BOTTOM);
//        if (freightLocation== FreightFrenzyComputerVisionShippingElementReversion.SkystoneDeterminationPipeline.FreightPosition.LEFT){
//            drive.pause(300);
//        }
      // drive.retract();

        drive.followTrajectorySequence(fromHubToWarehouse);

        //pick up cube
        boolean gotCube= drive.getCube();
        if (!gotCube){
            TrajectorySequence trajBack = drive.trajectorySequenceBuilder(drive.getLocalizer().getPoseEstimate())
                    .lineTo(fromHubToWarehouse.end().vec())
                    .build();
            drive.followTrajectorySequence(trajBack);
            gotCube = drive.getCube();
        }
        if (gotCube) {
            TrajectorySequence trajSeq3 = drive.trajectorySequenceBuilder(drive.getLocalizer().getPoseEstimate())

                    .lineTo(new Vector2d(15, -66))
                    .addDisplacementMarker(()->drive.intakeMotor.setPower(0))
                    .addDisplacementMarker(() -> {
                        drive.linearSlideMotor.setTargetPosition(FreightFrenzyConstants.SLIDE_TOP);
                        drive.linearSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        drive.linearSlideMotor.setPower(.8);
                    })
                    .splineToSplineHeading(new Pose2d(-10, -47, Math.toRadians(90)), Math.toRadians(90))
                    .build();
            drive.followTrajectorySequence(trajSeq3);

            drive.linearSlideServo.setPosition(FreightFrenzyConstants.DUMP_SERVO_DROP);
            drive.pause(SERVO_DROP_PAUSE);
            drive.linearSlideServo.setPosition(FreightFrenzyConstants.DUMP_SERVO_BOTTOM);
            drive.retract();

            drive.followTrajectorySequence(fromHubToWarehouse);
             if (drive.getCube()) {

                 trajSeq3 = drive.trajectorySequenceBuilder(drive.getLocalizer().getPoseEstimate())

                         .lineTo(new Vector2d(15, -66))
                         .addDisplacementMarker(()->drive.intakeMotor.setPower(0))

                         .addDisplacementMarker(() -> {
                             drive.linearSlideMotor.setTargetPosition(FreightFrenzyConstants.SLIDE_TOP);
                             drive.linearSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                             drive.linearSlideMotor.setPower(.8);
                         })
                         .splineToSplineHeading(new Pose2d(-10, -47, Math.toRadians(90)), Math.toRadians(90))

                         .build();
                 drive.followTrajectorySequence(trajSeq3);

                 drive.linearSlideServo.setPosition(FreightFrenzyConstants.DUMP_SERVO_DROP);
                 drive.pause(SERVO_DROP_PAUSE);
                 drive.linearSlideServo.setPosition(FreightFrenzyConstants.DUMP_SERVO_BOTTOM);
                 drive.retract();

                 drive.followTrajectorySequence(fromHubToWarehouse);
                 drive.getCube();
             }
        }
    }



}
