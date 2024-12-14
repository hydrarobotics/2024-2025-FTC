package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
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
    private Servo claw1Servo;
    private Servo claw2Servo;
    private Servo clawServo3;

    private boolean setTrue;

    private boolean Manual = true;
    private float LEFT_CLAW_OPEN_POSITION = 1.0f; //use SRS and update this value
    private float LEFT_CLAW_CLOSE_POSITION = 0.5f; //use SRS and update this value
    private float RIGHT_CLAW_OPEN_POSITION = 0.0f; //use SRS and update this value
    private float RIGHT_CLAW_CLOSE_POSITION = 0.5f; //use SRS and update this value

    @Override
    public void init() {

        flMotor = hardwareMap.get(DcMotor.class, "LF");
        frMotor = hardwareMap.get(DcMotor.class, "RF");
        blMotor = hardwareMap.get(DcMotor.class, "LB");
        brMotor = hardwareMap.get(DcMotor.class, "RB");
        armMotor = hardwareMap.get(DcMotor.class, "liftRotate"); // find name
        strMotor = hardwareMap.get(DcMotor.class, "liftExtend"); // too
        claw1Servo = hardwareMap.get(Servo.class, "leftClawServo"); // ya
        claw2Servo = hardwareMap.get(Servo.class, "rightClawServo");
        clawServo3 = hardwareMap.get(Servo.class, "clawRotateServo");

        flMotor.setPower(0.0);
        frMotor.setPower(0.0);
        blMotor.setPower(0.0);
        brMotor.setPower(0.0);
        armMotor.setPower(0.0);
        strMotor.setPower(0.0);
        claw1Servo.setPosition(0.5);
        claw2Servo.setPosition(0.5);
        clawServo3.setPosition(1.0);

        flMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        blMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        brMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        strMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        flMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        blMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        brMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        strMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

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
        int armTarget;
        int strTarget;
        //double servoLPos

        // G1
        // Rt = 1  LS = -1 Rs = -1
        /*turnPower = G1rightTrigger - G1leftTrigger; //1
        frMotorPower = ((G1LeftStickY - G1RightStickX) - (turnPower)); //-1
        flMotorPower = ((G1LeftStickY + G1RightStickX) + (turnPower));//-1
        brMotorPower = ((G1LeftStickY + G1RightStickX) - (turnPower));//-3
        blMotorPower = ((G1LeftStickY - G1RightStickX) + (turnPower));//1*/

        frMotorPower = ((forwardPower - G1LeftStickX) - (G1RightStickX));
        flMotorPower = ((forwardPower + G1LeftStickX) + (G1RightStickX));
        brMotorPower = ((forwardPower + G1LeftStickX) - (G1RightStickX));
        blMotorPower = ((forwardPower - G1LeftStickX) + (G1RightStickX));

        flMotor.setPower(-flMotorPower);//-1
        frMotor.setPower(frMotorPower);//1
        blMotor.setPower(-blMotorPower);//-1
        brMotor.setPower(brMotorPower);

        // Gamepad 2

        if (G2RightBumper){
            Manual = true;
        }
        if (G2LeftBumper){
            Manual = false;
        }

        if (Manual) {
            armTarget = (int) (-G2LeftStickY * -5200);
            strTarget = (int) ((G2rightTrigger - G2leftTrigger) * 2200);

            if (armTarget == 0) {
                armMotorPower = 0.0;
            } else if (armTarget < 0) {
                armMotorPower = 1.0;
            } else if (armTarget > 0) {
                armTarget = 0;
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

            armMotor.setTargetPosition(armTarget);
            strMotor.setTargetPosition(strTarget);
        }

        armMotor.setPower(1.0);
        strMotor.setPower(1.0);

        if (G2DpadUp){
            setHighBar();
        }
        if (G2DpadRight){
            Score();
        }
        if (G2DpadDown){
            grabSub();
        }
        if (G2DpadLeft){
            Transit();
        }

        if (G2aButton) {
            // Program Left and Right Claw to close ....i know its wierd just go with it
            claw1Servo.setPosition(LEFT_CLAW_OPEN_POSITION);
            claw2Servo.setPosition(RIGHT_CLAW_OPEN_POSITION);
        } else {
            // Program Left and Right Claw to open
            claw1Servo.setPosition(LEFT_CLAW_CLOSE_POSITION);
            claw2Servo.setPosition(RIGHT_CLAW_CLOSE_POSITION);
        }

        if (G2xButton) {
            clawServo3.setPosition(0.0);
        }

        if (G2yButton) {
            clawServo3.setPosition(1.0);
        }

        //double clawMotorPosition = G2RightStickX - G2LeftStickX;
        //if (clawMotorPosition != 0){
        //    claw1Servo.setPosition(leftClawServo.getPosition()-clawMotorPosition);
        //    claw2Servo.setPosition(rightClawServo.getPosition()+clawMotorPosition);
        //}


        telemetry.addData("RotatePos: ", armMotor.getCurrentPosition());
        telemetry.addData("LiftPos: ", strMotor.getCurrentPosition());
        //telemetry.addData("Arm Target: ", armTarget);
        //telemetry.addData("String Target: ", strTarget);
    }

    public void grabSub(){
        setTrue = false;
        armMotor.setTargetPosition(-500);
        strMotor.setTargetPosition(2300);
        clawServo3.setPosition(0.0);
    }

    public void Transit(){
        setTrue = false;
        armMotor.setTargetPosition(-700);
        strMotor.setTargetPosition(0);
        clawServo3.setPosition(1.0);
    }

    public void setHighBar(){
        setTrue = true;
        armMotor.setTargetPosition(-5200);
        //strMotor.setTargetPosition();
        clawServo3.setPosition(0.0);
    }

    public void Score(){
        if (setTrue){
            clawServo3.setPosition(1.0);
        }
    }

}