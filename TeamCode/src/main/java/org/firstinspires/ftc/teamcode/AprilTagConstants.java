package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import java.util.HashMap;

public class AprilTagConstants {
    public static final HashMap<Integer, Pose2d> TAG_FIELD_POSITIONS = new HashMap<Integer, Pose2d>() {{
        put(11, new Pose2d(-72.0, 36.0, Math.toRadians(90)));
        put(12, new Pose2d(-36.0, -72.0, Math.toRadians(0)));
        put(13, new Pose2d(-72.0, -72.0, Math.toRadians(180)));
        put(14, new Pose2d(72.0, -72.0, Math.toRadians(180)));
        put(15, new Pose2d(36.0, 72.0, Math.toRadians(0)));
        put(16, new Pose2d(72.0, 36.0, Math.toRadians(90)));
    }};
}
