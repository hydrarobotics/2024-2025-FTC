package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class EncoderDebug extends OpMode{

    private DcMotor armMotor;
    private DcMotor strMotor;

    private Servo claw1Servo;

    private Servo claw2Servo;
    private Servo clawServo3;

    private float LEFT_CLAW_OPEN_POSITION = 1.0f; //use SRS and update this value
    private float LEFT_CLAW_CLOSE_POSITION = 0.5f; //use SRS and update this value
    private float RIGHT_CLAW_OPEN_POSITION = 0.0f; //use SRS and update this value
    private float RIGHT_CLAW_CLOSE_POSITION = 0.5f;

    @Override
    public void init(){

        armMotor = hardwareMap.get(DcMotor.class, "liftRotate");
        strMotor = hardwareMap.get(DcMotor.class, "liftExtend");
        claw1Servo = hardwareMap.get(Servo.class, "leftClawServo"); // ya
        claw2Servo = hardwareMap.get(Servo.class, "rightClawServo");
        clawServo3 = hardwareMap.get(Servo.class, "clawRotateServo");

        claw1Servo.setPosition(0.5);
        claw2Servo.setPosition(0.5);
        clawServo3.setPosition(1.0);

        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        strMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        strMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    @Override
    public void loop(){

        double strPower = (gamepad1.right_trigger - gamepad1.left_trigger);
        double armPower = (-gamepad1.left_stick_y);

        strMotor.setPower(strPower);
        armMotor.setPower(armPower);

        if (gamepad1.x) {
            armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        if (gamepad1.y) {
            strMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            strMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        telemetry.addData("RotatePos: ", armMotor.getCurrentPosition());
        telemetry.addData("LiftPos: ", strMotor.getCurrentPosition());

    }



}
