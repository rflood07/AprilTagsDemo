// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
        // Front left
        new Translation2d(DRIVETRAIN_TRACKWIDTH_METERS / 2.0, DRIVETRAIN_WHEELBASE_METERS / 2.0),
        // Front right
        new Translation2d(DRIVETRAIN_TRACKWIDTH_METERS / 2.0, -DRIVETRAIN_WHEELBASE_METERS / 2.0),
        // Back left
        new Translation2d(-DRIVETRAIN_TRACKWIDTH_METERS / 2.0, DRIVETRAIN_WHEELBASE_METERS / 2.0),
        // Back right
        new Translation2d(-DRIVETRAIN_TRACKWIDTH_METERS / 2.0, -DRIVETRAIN_WHEELBASE_METERS / 2.0));


      public static final int DRIVETRAIN_PIGEON_ID = 0;
      public static final int FL_DRIVE_MOTOR_ID = 1;
      public static final int FL_STEER_MOTOR_ID = 2;
      public static final int FL_STEER_ENCODER_ID = 1;
      public static final Rotation2d FL_STEER_OFFSET = Rotation2d.fromRotations(0.272217);
  
      public static final int FR_DRIVE_MOTOR_ID = 3;
      public static final int FR_STEER_MOTOR_ID = 4;
      public static final int FR_STEER_ENCODER_ID = 2;
      public static final Rotation2d FR_STEER_OFFSET = Rotation2d.fromRotations(0.41333);
  
      public static final int BL_DRIVE_MOTOR_ID = 5;
      public static final int BL_STEER_MOTOR_ID = 6;
      public static final int BL_STEER_ENCODER_ID = 3;
      public static final Rotation2d BL_STEER_OFFSET = Rotation2d.fromRotations(-0.11792);
  
      public static final int BR_DRIVE_MOTOR_ID = 7;
      public static final int BR_STEER_MOTOR_ID = 8;
      public static final int BR_STEER_ENCODER_ID = 4;
      public static final Rotation2d BR_STEER_OFFSET = Rotation2d.fromRotations(0.403809);

      public static final String CANIVORE_DRIVETRAIN = "Swerve";
      public static final Translation2d FIELD_CORNER = new Translation2d(16.54, 8.02);

  
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
}
