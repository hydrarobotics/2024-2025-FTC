package org.firstinspires.ftc.teamcode.src.utills;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.src.robotAttachments.driveTrains.TeleopDriveTrain;


/**
 * A template for all Teleop opModes, allows easy initialization
 */
@Disabled
public abstract class TeleOpTemplate extends GenericOpModeTemplate {

    /**
     * Provides methods for driving
     */
    protected TeleopDriveTrain driveTrain;

    /**
     * Initializes the objects provided by this class
     */
    protected void initAll() throws InterruptedException {
        initDriveTrain();

        super.initAll();
        podServos.raise();

        telemetry.addData("Initialization Status", "Initialized");
        telemetry.update();
    }

    protected void initDriveTrain() {
        driveTrain = new TeleopDriveTrain(hardwareMap, frontRightName, frontLeftName, backRightName, backLeftName);
    }

    protected void initOdometryServos() {
        super.initOdometryServos();
        podServos.raise();
    }


}
