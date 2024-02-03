


/*     VERSION HISTORY
*           First number in the version number should only increase if the overall structure of
*                    the code changes. The code should remain functionally the same. If the
*                   first number changes, there should be no other changes to the code and the
*                    version should be a single number (ie. V2  or V15       not V2.1)
*
*           Second number in the version number should only change if a feature is added to the
*                    code. Removal of a feature (so long as its not the last feature added)
*                    should be treated like a change to the overall structure.
*
*           Third number should change only for bug fixes and minor "cosmetic" changes. Use of
*                    this number should be avoided by only commiting robot code that works
*                    propperly.
*
*          !!!!!!!!!!UPDATE VERSION HISTORY BEFORE COMMIT!!!!!!!!!!
*            !!!!!!!!!!UPDATE VERSION HISTORY BEFORE COMMIT!!!!!!!!!!
*        !!!!!!!!!!UPDATE VERSION HISTORY BEFORE COMMIT!!!!!!!!!!
*
*        Version  |  Developer   |   Comments About Changes
*        _________|______________|___________________________________________________________________________________________________________
*         V1      |  RAT         |   Initial commit. Has basic framework for drivetrain
*         V1.0.1  | Damien H.    |   Added library elements and a portiom of topworks code
                                     from everybot's code.
*                                     
*         !!!!!!!!!!UPDATE VERSION HISTORY BEFORE COMMIT!!!!!!!!!!
*    !!!!!!!!!!UPDATE VERSION HISTORY BEFORE COMMIT!!!!!!!!!!
*                  !!!!!!!!!!UPDATE VERSION HISTORY BEFORE COMMIT!!!!!!!!!!
*
*
*/


 








package frc.robot;

// import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMaxLowLevel.MotorType; 

// import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

// import org.opencv.features2d.FlannBasedMatcher;

// import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkMax;
/*last three are from everybot library
- Damien H.
*/

// import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;  // This lib is being depreciated
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/*last five are from everybot library
-Damien H.
*/



/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  // private final WPI_VictorSPX m_motorcontroller0 = new WPI_VictorSPX(0);
  // private final WPI_VictorSPX m_motorcontroller1 = new WPI_VictorSPX(1);
  //  private final WPI_VictorSPX m_motorcontroller2  = new WPI_VictorSPX(2);
  // private final WPI_VictorSPX m_motorcontroller3  = new WPI_VictorSPX(3);
  private final CANSparkMax m_motorcontroller1 = new CANSparkMax(1, MotorType.kBrushed);  // Front Left
  private final CANSparkMax m_motorcontroller2 = new CANSparkMax(2, MotorType.kBrushed);  // Back Left
  private final CANSparkMax m_motorcontroller3 = new CANSparkMax(3, MotorType.kBrushed);  // Front Right
  private final CANSparkMax m_motorcontroller4 = new CANSparkMax(4, MotorType.kBrushed);  // Back Right

  // private final MotorControllerGroup m_leftDrive = new MotorControllerGroup(m_motorcontroller0,m_motorcontroller1);
  // private final MotorControllerGroup m_rightDrive = new MotorControllerGroup(m_motorcontroller2,m_motorcontroller3);
  // private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftDrive, m_rightDrive);
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_motorcontroller1, m_motorcontroller3);
  private final static Joystick m_controller = new Joystick(0);
  private final Timer m_timer = new Timer();
  boolean arcadeActive;
  boolean previousMode;
  double previousArcadeMotorSpeed = 0;
  double previousLeftMotorSpeed = 0;
  double previousRightMotorSpeed = 0;

  static void testMethod (int i) {
System.out.println("i work"+i);

  }

  private static final String kNothingAuto = "do nothing";
  private static final String kLaunchAndDrive = "launch drive";
  private static final String kLaunch = "launch";
  private static final String kDrive = "drive";
  private String m_autoSelected;
 
 

  /*static double RampNum (double rampIncriment,double initialValue,double targetValue) {
    if (targetValue > initialValue) {
      return (initialValue + rampIncriment);
    }

    if (targetValue < initialValue) {
      return (initialValue - rampIncriment);
    }

    if (targetValue == initialValue) {
      return targetValue;
    }
     SmartDashboard.putNumber("Ramp Incriment", rampIncriment);
    //Shuffle board is not displaying Ramp Incriment. This is showing what numbers are going in and out of the Ramp

    //error branch, the below code should theoretically never happen.
    System.out.println("logic error, no ramp");
    return initialValue;
  }*/

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    // m_rightDrive.setInverted(true);
    m_motorcontroller3.setInverted(true);
    m_motorcontroller2.follow(m_motorcontroller1);
    m_motorcontroller4.follow(m_motorcontroller3);
  }

  /* This function is run when the robot is first started up and should be used for any
  * initialization code.
  */
 

  /* Both of the motors used on the KitBot launcher are CIMs which are brushed motors
  */
 CANSparkBase m_launchWheel = new CANSparkMax(6, MotorType.kBrushed);
 CANSparkBase m_feedWheel = new CANSparkMax(5, MotorType.kBrushed);


 /**
  * Roller Claw motor controller instance.
 */
 CANSparkBase m_rollerClaw = new CANSparkMax(8, MotorType.kBrushed);
 /**
  * Climber motor controller instance. In the stock Everybot configuration a
  * NEO is used, replace with kBrushed if using a brushed motor.
  */
 CANSparkBase m_climber = new CANSparkMax(7, MotorType.kBrushless);


   /**
  * The starter code uses the most generic joystick class.
  *
  * To determine which button on your controller corresponds to which number, open the FRC
  * driver station, go to the USB tab, plug in a controller and see which button lights up
  * when pressed down
  *
  * Buttons index from 0
  */

    /*above is the launcher code from the everybot, IDK where exactly this goes but this is my best guess 
        -Damien H.
     */


 Joystick m_driverController = new Joystick(0);




 Joystick m_manipController = new Joystick(1);

 /*i think we will have to assign the joystick IDs ourselves
  - Damien H.
  */

   // --------------- Magic numbers. Use these to adjust settings. ---------------


/**
  * How many amps can an individual drivetrain motor use.
  */
 static final int DRIVE_CURRENT_LIMIT_A = 60;


 /**
  * How many amps the feeder motor can use.
  */
 static final int FEEDER_CURRENT_LIMIT_A = 80;


 /**
  * Percent output to run the feeder when expelling note
  */
 static final double FEEDER_OUT_SPEED = 1.0;


 /**
  * Percent output to run the feeder when intaking note
  */
 static final double FEEDER_IN_SPEED = -.4;


 /**
  * Percent output for amp or drop note, configure based on polycarb bend
  */
 static final double FEEDER_AMP_SPEED = .4;


 /**
  * How many amps the launcher motor can use.
  *
  * In our testing we favored the CIM over NEO, if using a NEO lower this to 60
  */
 static final int LAUNCHER_CURRENT_LIMIT_A = 80;


 /**
  * Percent output to run the launcher when intaking AND expelling note
  */
 static final double LAUNCHER_SPEED = 1.0;


 /**
  * Percent output for scoring in amp or dropping note, configure based on polycarb bend
  * .14 works well with no bend from our testing
  */
 static final double LAUNCHER_AMP_SPEED = .17;
 /**
  * Percent output for the roller claw
  */
 static final double CLAW_OUTPUT_POWER = .5;
 /**
  * Percent output to help retain notes in the claw
  */
 static final double CLAW_STALL_POWER = .1;
 /**
  * Percent output to power the climber
  */
 static final double CLIMER_OUTPUT_POWER = 1;


 /**

/* settings for launcher from everybot code
 -Damien H.
 */



  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.restart();
    //testMethod(137);
  }
  

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 1.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      m_robotDrive.tankDrive(0.4, 0.5, false);
    }else if(m_timer.get() >= 1.0 && m_timer.get() < 3.0){
      m_robotDrive.stopMotor();
    } else if(m_timer.get() >= 3.0 && m_timer.get() < 4.2) {
      m_robotDrive.tankDrive(0.1, 0.4, false);
    } else if(m_timer.get() >= 4.2 && m_timer.get() < 6.0) {
      m_robotDrive.stopMotor();
    } else if(m_timer.get() >= 6.0 && m_timer.get() < 7.0) {
      m_robotDrive.tankDrive(0.4, 0.5, false);
    } else if(m_timer.get() >= 7.0 && m_timer.get() < 8.0) {
      m_robotDrive.stopMotor();
    } else if(m_timer.get() >= 8.0 && m_timer.get() < 8.6) {
      m_robotDrive.tankDrive(0.4, 0.5, false);
    } else if(m_timer.get() >= 8.6 && m_timer.get() < 9.0) {
      m_robotDrive.stopMotor();

    } else{
      m_robotDrive.stopMotor(); // stop robot*
    }
  }
  

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
   
    boolean AButtonPos;
    boolean BButtonPos;
    previousMode = arcadeActive;
    AButtonPos = m_controller.getRawButton(1);
    BButtonPos = m_controller.getRawButton(2);
    //previousArcadeMotorSpeed = RampNum(.4, previousArcadeMotorSpeed, -m_controller.getRawAxis(1));
    //previousLeftMotorSpeed = RampNum(0.4, previousLeftMotorSpeed, -m_controller.getRawAxis(1));
    //previousRightMotorSpeed = RampNum(0.4, previousRightMotorSpeed, -m_controller.getRawAxis(5));
//Troubleshoot driving (Joystick and Motor relationship)
    if(AButtonPos ==true && BButtonPos ==true) {
      arcadeActive = previousMode;
    }else if(AButtonPos ==true && BButtonPos ==false) {
      arcadeActive =true;
    }else if(AButtonPos ==false && BButtonPos ==true) {
      arcadeActive =false;
    }else if(AButtonPos ==false && BButtonPos ==false) {
      arcadeActive = previousMode;
      //if both modes are active, put robot into the last mode it was in, if A button pressed then arcade mode is active, if B button is pressed then arcade isnt active, if neither are active then set robot to previous mode
      }
      previousMode = arcadeActive;
   /* if (arcadeActive) {
      m_robotDrive.arcadeDrive(previousArcadeMotorSpeed * .6, -m_controller.getRawAxis(1) * .6);
    
    } else {
      m_robotDrive.tankDrive(previousLeftMotorSpeed * .6, previousRightMotorSpeed * .6);
    }
    System.out.println (arcadeActive);
    System.out.println (previousMode);
*/
    SmartDashboard.putNumber("axis 1", m_controller.getRawAxis(1));

    boolean tank; //button B
    boolean arcade; //button A
    tank = m_controller.getRawButton(2);
    arcade = m_controller.getRawButton(1);
    if (tank == true && arcade == true) {
      tank =false;
      arcade =false;
      
    }

    Double motorSpeedLimit = .06;
    
    if (arcadeActive == false) {
      m_robotDrive.tankDrive(-m_controller.getRawAxis(1) *motorSpeedLimit, -m_controller.getRawAxis(5) *motorSpeedLimit);
    } else if(arcadeActive == true) {
      m_robotDrive.arcadeDrive(-m_controller.getRawAxis(1) *motorSpeedLimit, -m_controller.getRawAxis(0) *motorSpeedLimit);
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
    
  } 

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
  

  
}
