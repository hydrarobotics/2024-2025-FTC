package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class AlexOp extends OpMode {

    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor flMotor;
    private DcMotor blMotor;
    private DcMotor frMotor;
    private DcMotor brMotor;

    private DcMotor armMotor;
    private DcMotor strMotor;
    private Servo leftClawServo;
    private Servo rightClawServo;
    private Servo rotateClawServo;

    private boolean setTrue; // Used to check if the current preset position is at high bar.

    private boolean Manual = true;
    private float LEFT_CLAW_OPEN_POSITION = 1.0f; //use SRS and update this value
    private float LEFT_CLAW_CLOSE_POSITION = 0.5f; //use SRS and update this value
    private float RIGHT_CLAW_OPEN_POSITION = 0.0f; //use SRS and update this value
    private float RIGHT_CLAW_CLOSE_POSITION = 0.5f; //use SRS and update this value

    private RobotHardware robot = new RobotHardware(); // Class with all of the robot's hardware.

    @Override
    public void init() {
        robot.init(hardwareMap);

        flMotor = robot.LFMotor;
        frMotor = robot.RFMotor;
        blMotor = robot.LBMotor;
        brMotor = robot.RBMotor;
        armMotor = robot.liftRotateMotor; // The arm's rotation motor.
        strMotor = robot.liftExtendMotor; // The arm extension motor.
        leftClawServo = robot.leftClawServo;
        rightClawServo = robot.rightClawServo;
        rotateClawServo = robot.clawRotateServo; // Rotates the claw.

        telemetry.addData("Status", "Initialized");
        telemetry.update();

    }

    @Override
    public void loop() {

        //Driver #1 Gamepad Controls
        //Buttons
        boolean G1xButton = gamepad1.x;
        boolean G1aButton = gamepad1.a;
        boolean G1bButton = gamepad1.b;
        boolean G1yButton = gamepad1.y;

        //Triggers
        double G1rightTrigger = gamepad1.right_trigger;
        double G1leftTrigger = gamepad1.left_trigger;

        //Sticks X and Y values
        double G1RightStickX = gamepad1.right_stick_x;
        double G1RightStickY = gamepad1.right_stick_y;

        double G1LeftStickX = gamepad1.left_stick_x;
        double G1LeftStickY = gamepad1.left_stick_y;

        //Bumpers
        boolean G1LeftBumper = gamepad1.left_bumper;
        boolean G1RightBumper = gamepad1.right_bumper;

        //Driver #2 Gamepad Controls
        //Buttons
        boolean G2xButton = gamepad2.x;
        boolean G2aButton = gamepad2.a;
        boolean G2bButton = gamepad2.b;
        boolean G2yButton = gamepad2.y;

        //Triggers
        double G2rightTrigger = gamepad2.right_trigger;
        double G2leftTrigger = gamepad2.left_trigger;

        //Sticks X and Y values
        double G2RightStickX = gamepad2.right_stick_x;
        double G2RightStickY = gamepad2.right_stick_y;

        double G2LeftStickX = gamepad2.left_stick_x;
        double G2LeftStickY = gamepad2.left_stick_y;

        //Bumpers
        boolean G2LeftBumper = gamepad2.left_bumper;
        boolean G2RightBumper = gamepad2.right_bumper;

        //D-pads
        boolean G2DpadUp = gamepad2.dpad_up;
        boolean G2DpadRight = gamepad2.dpad_right;
        boolean G2DpadDown = gamepad2.dpad_down;
        boolean G2DpadLeft = gamepad2.dpad_left;

        //DriveTrain Variables
        double flMotorPower = 0.0;
        double frMotorPower = 0.0;
        double blMotorPower = 0.0;
        double brMotorPower = 0.0;
        double armMotorPower = 0.0;
        double strMotorPower = 0.0;
        double forwardPower = (G1rightTrigger - G1leftTrigger);

        //double servoLPos

        frMotorPower = ((forwardPower - G1LeftStickX) - (G1RightStickX));
        flMotorPower = ((forwardPower + G1LeftStickX) + (G1RightStickX));
        brMotorPower = ((forwardPower + G1LeftStickX) - (G1RightStickX));
        blMotorPower = ((forwardPower - G1LeftStickX) + (G1RightStickX));

        flMotor.setPower(-flMotorPower);
        frMotor.setPower(frMotorPower);
        blMotor.setPower(-blMotorPower);
        brMotor.setPower(brMotorPower);

        // Gamepad 2

        // This swaps the robot's arm mode from manual to preset positions.
        if (G2RightBumper){
            Manual = true;
        }
        if (G2LeftBumper){
            Manual = false;
        }

        if (Manual) { // This is the code for manual mode.

            int armRotateTarget; // The arm rotate encoder's target.
            int strTarget; // The extension encoder's target.

            armRotateTarget = (int) (-G2LeftStickY * -5200);
            strTarget = (int) ((G2rightTrigger - G2leftTrigger) * 2200);

            if (armRotateTarget == 0) {
                armMotorPower = 0.0;
            } else if (armRotateTarget < 0) {
                armMotorPower = 1.0;
            } else if (armRotateTarget > 0) {
                armRotateTarget = 0;
                armMotorPower = 1.0;
            }

            if (strTarget == 0) {
                strMotorPower = 0.0;
            } else if (strTarget > 0) {
                strMotorPower = 1.0;
            } else if (strTarget < 0) {
                strTarget = 0;
                strMotorPower = 1.0;
            }

            /* Both of these if-else statement blocks are to determine what the arm
            * should be doing. If the target position is within reach, it'll go
            * to that position. If there is no input for arm movement, the arm will
            * stay put. If the arm's target is out of reach, it will set the target
            * to zero, sending it back to its original position. */

            armMotor.setTargetPosition(armRotateTarget);
            strMotor.setTargetPosition(strTarget);

            armMotor.setPower(armMotorPower);
            strMotor.setPower(strMotorPower);
        }

        if (!Manual) { // Code for the preset mode.
            if (G2DpadUp) {
                setHighBar();
            }
            if (G2DpadRight) {
                Score(); // Needs to have setHighBar first.
            }
            if (G2DpadDown) {
                grabSub();
            }
            if (G2DpadLeft) {
                Transit();
            }
        }

        if (G2aButton) {
            leftClawServo.setPosition(LEFT_CLAW_OPEN_POSITION);
            rightClawServo.setPosition(RIGHT_CLAW_OPEN_POSITION);
        } else {
            leftClawServo.setPosition(LEFT_CLAW_CLOSE_POSITION);
            rightClawServo.setPosition(RIGHT_CLAW_CLOSE_POSITION);
        }

        if (G2xButton) {
            rotateClawServo.setPosition(0.0);
        }

        if (G2yButton) {
            rotateClawServo.setPosition(1.0);
        }

        telemetry.addData("RotatePos: ", armMotor.getCurrentPosition());
        telemetry.addData("LiftPos: ", strMotor.getCurrentPosition());
        telemetry.addData("Arm Target: ", armMotor.getTargetPosition());
        telemetry.addData("String Target: ", armMotor.getTargetPosition());
        telemetry.addData("Manual On: ", Manual);
    }

    public void grabSub(){ //Dpad Down
        setTrue = false;
        armMotor.setTargetPosition(-500);
        strMotor.setTargetPosition(2300);
        rotateClawServo.setPosition(0.0);
    }

    public void Transit(){ //Dpad Left
        setTrue = false;
        armMotor.setTargetPosition(-700);
        strMotor.setTargetPosition(0);
        rotateClawServo.setPosition(1.0);
    }

    public void setHighBar(){ //Dpad Up
        setTrue = true;
        armMotor.setTargetPosition(-5200);
        //strMotor.setTargetPosition(); // Don't know how high this has to be.
        rotateClawServo.setPosition(0.0);
    }

    public void Score(){ //Dpad Right
        if (setTrue){
            rotateClawServo.setPosition(1.0);
        }
    }

}