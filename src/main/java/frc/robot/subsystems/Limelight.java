package frc.robot.subsystems;

import frc.robot.LimelightHelpers;
import frc.robot.LimelightHelpers.PoseEstimate;
import frc.robot.settings.LimelightDetectorData;

import static frc.robot.settings.Constants.Vision.*;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight {

    private static Limelight limelight;

    private static Field2d field1 = new Field2d();
    private static Field2d field2 = new Field2d();

    public static Boolean detectorEnabled = false;

    private Limelight() {
        SmartDashboard.putBoolean("Vision/Left/valid", false);
        SmartDashboard.putBoolean("Vision/Left/trusted", false);
        SmartDashboard.putBoolean("Vision/Right/valid", false);
        SmartDashboard.putBoolean("Vision/Right/trusted", false);
        SmartDashboard.putData("Vision/Left/pose", field1);
        SmartDashboard.putData("Vision/Right/pose", field2);
    }

    public static Limelight getInstance() {
        if (limelight == null) {
            limelight = new Limelight();
        }
        return limelight;
    }

    public static void useDetectorLimelight(boolean enabled) {
        detectorEnabled = enabled;
    }

    /**
     * Gets the most recent limelight pose estimate, given that a trustworthy
     * estimate is
     * available. Uses the provided odometryPose for additional filtering.
     * <p>
     * Trusted poses must:
     * <ul>
     * <li>Be within field bounds.
     * <li>Have an average tag distance within 6 meters from the robot.
     * <li>Be within [ALLOWABLE_POSE_DIFFERENCE] from the given odometryPose.
     * </ul>
     * 
     * @param odometryPose The current odometry pose estimate
     * @return A valid and trustworthy pose. Null if no valid pose. Poses are
     *         prioritized by lowest tagDistance.
     */
    public PoseEstimate getTrustedPose(Pose2d odometryPose) {
        PoseEstimate pose1 = LimelightHelpers.getBotPoseEstimate_wpiBlue(APRILTAG_LIMELIGHT2_NAME);
        PoseEstimate pose2 = LimelightHelpers.getBotPoseEstimate_wpiBlue(APRILTAG_LIMELIGHT3_NAME);

        Boolean pose1Trust = isTrustworthy(APRILTAG_LIMELIGHT2_NAME, pose1, odometryPose);
        Boolean pose2Trust = isTrustworthy(APRILTAG_LIMELIGHT3_NAME, pose2, odometryPose);

        if (pose1Trust && pose2Trust) {
            return ((pose1.avgTagDist < pose2.avgTagDist) ? pose1 : pose2); //select the limelight that has closer tags.
        } else if (pose1Trust) {
            return pose1;
        } else if (pose2Trust) {
            return pose2;
        } else
            return null;
    }

    /**
     * Gets the most recent limelight pose estimate, given that a valid estimate is
     * available.
     * <p>
     * Valid estemates are only required to be within the field, so results will be
     * unpredictable.
     * 
     * @return A valid and NOT-ALWAYS-TRUSTWORTHY pose. Null if no valid pose. Poses
     *         are prioritized by lowest tagDistance.
     */
    public PoseEstimate getValidPose() {
        PoseEstimate pose1 = LimelightHelpers.getBotPoseEstimate_wpiBlue(APRILTAG_LIMELIGHT2_NAME);
        PoseEstimate pose2 = LimelightHelpers.getBotPoseEstimate_wpiBlue(APRILTAG_LIMELIGHT3_NAME);

        Boolean pose1Valid = isValid(APRILTAG_LIMELIGHT2_NAME, pose1);
        Boolean pose2Valid = isValid(APRILTAG_LIMELIGHT3_NAME, pose2);

        if (pose1Valid && pose2Valid) {
            return ((pose1.avgTagDist < pose2.avgTagDist) ? pose1 : pose2); //select the limelight that has closer tags.
        } else if (pose1Valid) {
            return pose1;
        } else if (pose2Valid) {
            return pose2;
        } else
            return null;
    }

    public LimelightDetectorData getNeuralDetectorValues() {
        return new LimelightDetectorData(
                LimelightHelpers.getTX(OBJ_DETECITON_LIMELIGHT_NAME),
                LimelightHelpers.getTY(OBJ_DETECITON_LIMELIGHT_NAME),
                LimelightHelpers.getTA(OBJ_DETECITON_LIMELIGHT_NAME),
                LimelightHelpers.getNeuralClassID(OBJ_DETECITON_LIMELIGHT_NAME),
                LimelightHelpers.getTV(OBJ_DETECITON_LIMELIGHT_NAME));
    }
    /**
     * returns a boolean that is dependent on wether or not the given position is within the field bounds
     */
    private boolean isValid(String limelightName, PoseEstimate estimate) {
        Boolean valid = (
                estimate.pose.getX() < Constants.FIELD_CORNER.getX() &&
                estimate.pose.getX() > 0.0 &&
                estimate.pose.getY() < Constants.FIELD_CORNER.getY() &&
                estimate.pose.getY() > 0.0);
        return valid;
    }
    /**
     * checks if the robotPose returned by the limelight is within the field and stable. It does this by running isValid() with the limelight, and checking if 
     * the tag is close enough to the robot to give an accurate reading (within 7 meters)
     * @param limelightName the name of the requested limelight, as seen on NetworkTables
     * @param estimate the poseEstimate from that limelight
     * @param odometryPose the robot's pose from the DriveTrain, unused right now
     * @return
     */
    private boolean isTrustworthy(String limelightName, PoseEstimate estimate, Pose2d odometryPose) {
        Boolean trusted = (
                isValid(limelightName, estimate) &&
                estimate.avgTagDist<7);

        if (limelightName.equalsIgnoreCase(APRILTAG_LIMELIGHT2_NAME)) {
            SmartDashboard.putBoolean("Vision/Left/trusted", trusted);
            SmartDashboard.putNumber("Vision/Left/Stats/trusted", (trusted ? 1 : 0));
        } else if (limelightName.equalsIgnoreCase(APRILTAG_LIMELIGHT3_NAME)) {
            SmartDashboard.putBoolean("Vision/Right/trusted", trusted);
            SmartDashboard.putNumber("Vision/Right/Stats/trusted", (trusted ? 1 : 0));
        } else {
            System.err.println("Limelight name is invalid. (limelight.isTrustworthy)");
        }
        return trusted;
    }
}