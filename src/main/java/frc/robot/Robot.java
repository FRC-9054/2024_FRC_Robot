


/**    VERSION HISTORY
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
*         V1.0.1  | Damien H.    |      Added library elements and a portiom of topworks 
*                 |              |      code from everybot's code.
*         V1.1.0  | Damien H.    |   Finished adding evereybot topworks code. Hasnt been
*                 | Quaid        |      tested on the bot yet. Likely has button mapping
*                 |              |      conflicts.
*         V1.1.1  | Damien H.    |   Remapped robot controls to driver's liking.
*                                     
*         !!!!!!!!!!UPDATE VERSION HISTORY BEFORE COMMIT!!!!!!!!!!
*    !!!!!!!!!!UPDATE VERSION HISTORY BEFORE COMMIT!!!!!!!!!!
*                  !!!!!!!!!!UPDATE VERSION HISTORY BEFORE COMMIT!!!!!!!!!!
*
*
*/



/**
 * TODO/NOTES:
 * 
 * Test on robot
 * check button mapping
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
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkMax;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
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
  // NOTE: variables
  // vvvvvvvvvvvvvvv
  boolean arcadeActive;
  boolean previousMode;
  double previousArcadeMotorSpeed = 0;
  double previousLeftMotorSpeed = 0;
  double previousRightMotorSpeed = 0;

  // note: button functions
  // vvvvvvvvvvvvvvvvvvvvvv
  int drivetrainRotateAxis = 0;
  int drivetrainSpeedAxis = 5;
  // int rightAxis = 1;
  // int leftAxis =4;

  int ampFunctionButton = 10;
  int intakeFunctionButton = 4;
  int feedwheelFunctionButton = 6;
  int launchwheelFunctionButton = 3;

  
  int elevatorExtendFunctionButton = 0; // this is a position on the POV. Must be 0, 45, 90, 135, 180, 225, 270, or 315. front = 0   right = 90   back = 180   left = 270
  int elevatorRetractFunctionButton = 180; // this is a position on the POV. Must be 0, 45, 90, 135, 180, 225, 270, or 315. front = 0   right = 90   back = 180   left = 270

  // note: tunables
  // vvvvvvvvvvvvvv
  Double motorSpeedLimit = -0.50;   // note: why are we inverting the speed limmit and each controler input?



  static void testMethod (int i) {   // todo: Remove
System.out.println("i work"+i);

  }

  private static final String kNothingAuto = "do nothing";
  private static final String kLaunchAndDrive = "launch drive";
  private static final String kLaunch = "launch";
  private static final String kDrive = "drive";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
 
 
    // fixme: fix ramp fnc
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
    m_motorcontroller1.setInverted(false);
    m_motorcontroller3.setInverted(true);  // only invert this one because the otheer follows note:

    m_feedWheel.setInverted(true);
    m_launchWheel.setInverted(true);

    // m_rollerClaw.setInverted(false);
    m_climber.setInverted(false);

    m_motorcontroller2.follow(m_motorcontroller1);
    m_motorcontroller4.follow(m_motorcontroller3);
    m_chooser.setDefaultOption("do nothing", kNothingAuto);
    m_chooser.addOption("launch note and drive", kLaunchAndDrive);
    m_chooser.addOption("launch", kLaunch);
    m_chooser.addOption("drive", kDrive);
    SmartDashboard.putData("Auto choices", m_chooser);

    /*
     * Apply the current limit to the drivetrain motors
     */
    m_motorcontroller1.setSmartCurrentLimit(DRIVE_CURRENT_LIMIT_A);
    m_motorcontroller2.setSmartCurrentLimit(DRIVE_CURRENT_LIMIT_A);
    m_motorcontroller3.setSmartCurrentLimit(DRIVE_CURRENT_LIMIT_A);
    m_motorcontroller4.setSmartCurrentLimit(DRIVE_CURRENT_LIMIT_A);

    // m_feedWheel.setSmartCurrentLimit(FEEDER_CURRENT_LIMIT_A);
    // m_launchWheel.setSmartCurrentLimit(LAUNCHER_CURRENT_LIMIT_A);

    // m_rollerClaw.setSmartCurrentLimit(60);
    m_climber.setSmartCurrentLimit(60);

    // m_rollerClaw.setIdleMode(IdleMode.kBrake);
    m_climber.setIdleMode(IdleMode.kBrake);
  }

  public void robotPeriodic() {
    SmartDashboard.putNumber("Time (seconds)", Timer.getFPGATimestamp());
  }

  /* This function is run when the robot is first started up and should be used for any
  * initialization code.
  */
 

  /* Both of the motors used on the KitBot launcher are CIMs which are brushed motors
  */
  CANSparkBase m_launchWheel = new CANSparkMax(7, MotorType.kBrushed);
  // private final WPI_VictorSPX m_feedWheel = new WPI_VictorSPX(31);
  
 CANSparkBase m_feedWheel = new CANSparkMax(8, MotorType.kBrushed);


 /**
  * Roller Claw motor controller instance.
 */
//  CANSparkBase m_rollerClaw = new CANSparkMax(8, MotorType.kBrushed);
 /**
  * Climber motor controller instance. In the stock Everybot configuration a
  * NEO is used, replace with kBrushed if using a brushed motor.
  */
 CANSparkBase m_climber = new CANSparkMax(6, MotorType.kBrushless);


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


//  Joystick m_driverController = new Joystick(0);




//  Joystick m_controller = new Joystick(0);

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
 static final double FEEDER_IN_SPEED = .4;

 static final double LAUNCHER_IN_SPEED = .4;
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
 static final double LAUNCHER_AMP_SPEED = .10;
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

 double AUTO_LAUNCH_DELAY_S;
 double AUTO_DRIVE_DELAY_S;

 double AUTO_DRIVE_TIME_S;

 double AUTO_DRIVE_SPEED;
 double AUTO_LAUNCHER_SPEED;

 double autonomousStartTime;

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();

    m_motorcontroller1.setIdleMode(IdleMode.kBrake);
    m_motorcontroller2.setIdleMode(IdleMode.kBrake);
    m_motorcontroller3.setIdleMode(IdleMode.kBrake);
    m_motorcontroller4.setIdleMode(IdleMode.kBrake);

    AUTO_LAUNCH_DELAY_S = 2;
    AUTO_DRIVE_DELAY_S = 3;

    AUTO_DRIVE_TIME_S = 2.0;
    AUTO_DRIVE_SPEED = -0.5;
    AUTO_LAUNCHER_SPEED = 1;

    if (m_autoSelected == kLaunch) {
      AUTO_DRIVE_SPEED = 0;

    } else if (m_autoSelected == kDrive) {
      AUTO_LAUNCHER_SPEED = 0;

    } else if (m_autoSelected == kNothingAuto) {
      AUTO_DRIVE_SPEED = 0;
      AUTO_LAUNCHER_SPEED = 0;
    }

    autonomousStartTime = Timer.getFPGATimestamp();

  }
  

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {

    double timeElapsed = Timer.getFPGATimestamp() - autonomousStartTime;

    /*
     * Spins up launcher wheel until time spent in auto is greater than AUTO_LAUNCH_DELAY_S
     *
     * Feeds note to launcher until time is greater than AUTO_DRIVE_DELAY_S
     *
     * Drives until time is greater than AUTO_DRIVE_DELAY_S + AUTO_DRIVE_TIME_S
     *
     * Does not move when time is greater than AUTO_DRIVE_DELAY_S + AUTO_DRIVE_TIME_S
     */
    if(timeElapsed < AUTO_LAUNCH_DELAY_S)
    {
      m_launchWheel.set(AUTO_LAUNCHER_SPEED);
      m_robotDrive.arcadeDrive(0, 0);

    }
    else if(timeElapsed < AUTO_DRIVE_DELAY_S)
    {
      m_feedWheel.set(AUTO_LAUNCHER_SPEED);
      m_robotDrive.arcadeDrive(0, 0);
    }
    else if(timeElapsed < AUTO_DRIVE_DELAY_S + AUTO_DRIVE_TIME_S)
    {
      m_launchWheel.set(0);
      m_feedWheel.set(0);
      m_robotDrive.arcadeDrive(AUTO_DRIVE_SPEED, 0);
    }
    else
    {
      m_robotDrive.arcadeDrive(0, 0);
    }
    /* For an explanation on differintial drive, squaredInputs, arcade drive and tank drive see the bottom of this file */
  }
  

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {
    m_motorcontroller1.setIdleMode(IdleMode.kCoast);
    m_motorcontroller2.setIdleMode(IdleMode.kCoast);
    m_motorcontroller3.setIdleMode(IdleMode.kCoast);
    m_motorcontroller4.setIdleMode(IdleMode.kCoast);
  }

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
//     // no driver selection of drivemode
//     boolean AButtonPos;
//     boolean BButtonPos;
//     previousMode = arcadeActive;
//     AButtonPos = m_controller.getRawButton(1);
//     BButtonPos = m_controller.getRawButton(2);
//     //previousArcadeMotorSpeed = RampNum(.4, previousArcadeMotorSpeed, -m_controller.getRawAxis(1));
//     //previousLeftMotorSpeed = RampNum(0.4, previousLeftMotorSpeed, -m_controller.getRawAxis(1));
//     //previousRightMotorSpeed = RampNum(0.4, previousRightMotorSpeed, -m_controller.getRawAxis(5));
// //Troubleshoot driving (Joystick and Motor relationship)
//     if(AButtonPos ==true && BButtonPos ==true) {
//       arcadeActive = previousMode;
//     }else if(AButtonPos ==true && BButtonPos ==false) {
//       arcadeActive =true;
//     }else if(AButtonPos ==false && BButtonPos ==true) {
//       arcadeActive =false;
//     }else if(AButtonPos ==false && BButtonPos ==false) {
//       arcadeActive = previousMode;
//       //if both modes are active, put robot into the last mode it was in, if A button pressed then arcade mode is active, if B button is pressed then arcade isnt active, if neither are active then set robot to previous mode
//       }
//       previousMode = arcadeActive;
//    if (arcadeActive) {
//       m_robotDrive.arcadeDrive(previousArcadeMotorSpeed * .6, -m_controller.getRawAxis(1) * .55);
    
//     } else {
//       m_robotDrive.tankDrive(previousLeftMotorSpeed * .55, previousRightMotorSpeed * .55);
//     }
//     System.out.println (arcadeActive);
//     System.out.println (previousMode);




// SmartDashboard.putNumber("axis 1", m_controller.getRawAxis(1));

// boolean tank; //button B
// boolean arcade; //button A
// tank = m_controller.getRawButton(2);
// arcade = m_controller.getRawButton(1);
// if (tank == true && arcade == true) {
//   tank =false;
//   arcade =false;
       
//     }

    
//     /*   no driver selection of drive mode
//      SmartDashboard.putNumber("m_controller a1", -m_controller.getRawAxis(1));
//      SmartDashboard.putNumber("m_controller a5", -m_controller.getRawAxis(5));
//      SmartDashboard.putNumber("m_controller a1", -m_controller.getRawAxis(1));
//      SmartDashboard.putNumber("m_controller a0", -m_controller.getRawAxis(0));
//     if (arcadeActive == false) {
//        SmartDashboard.putNumber("m_controller a1", -m_controller.getRawAxis(1));
//       SmartDashboard.putNumber("m_controller a5", -m_controller.getRawAxis(5));
//       m_robotDrive.tankDrive(-m_controller.getRawAxis(1) *motorSpeedLimit, -m_controller.getRawAxis(5) *motorSpeedLimit);
//     } else if(arcadeActive == true) {
//      SmartDashboard.putNumber("m_controller a1", -m_controller.getRawAxis(1));
//      SmartDashboard.putNumber("m_controller a0", -m_controller.getRawAxis(0));
//       m_robotDrive.arcadeDrive(-m_controller.getRawAxis(1) *motorSpeedLimit, -m_controller.getRawAxis(0) *motorSpeedLimit);
//     } else {
//       m_robotDrive.stopMotor(); // stop robot
//     }
//     */
    
    // no driver selection of drive mode
    Double motorSpeedLimit = -0.75;   // note: why are we inverting the speed limmit and each controler input?
    m_robotDrive.arcadeDrive(-m_controller.getRawAxis(drivetrainSpeedAxis) *motorSpeedLimit, -m_controller.getRawAxis(drivetrainRotateAxis) *motorSpeedLimit);
    

    
    /////////////////
    /*Topworks code*/
    /*
     * Spins up the launcher wheel
     */
    if (m_controller.getRawButton(launchwheelFunctionButton)) {
      m_launchWheel.set(LAUNCHER_SPEED);
    }
    else if(m_controller.getRawButtonReleased(launchwheelFunctionButton))
    {
      m_launchWheel.set(0);

    }

    // spins up feeder wheel
     if (m_controller.getRawButton(launchwheelFunctionButton)) {
      m_launchWheel.set(FEEDER_OUT_SPEED);
    }
    else if(m_controller.getRawButtonReleased(launchwheelFunctionButton))
    {
      m_launchWheel.set(0);
    }


    /*
     * Spins feeder wheel, wait for launch wheel to spin up to full speed for best results
     */
    if (m_controller.getRawButton(feedwheelFunctionButton))
    {
      m_feedWheel.set(FEEDER_OUT_SPEED);
    }
    else if(m_controller.getRawButtonReleased(feedwheelFunctionButton))
    {
      m_feedWheel.set(0);
    }

    /*
     * While the button is being held spin both motors to intake note
     */


    if(m_controller.getRawButton(intakeFunctionButton))
    {
      m_launchWheel.set(-LAUNCHER_IN_SPEED);
      
      m_feedWheel.set(-FEEDER_IN_SPEED);
    }
    else if(m_controller.getRawButtonReleased(intakeFunctionButton))
    {
      m_launchWheel.set(0);
      m_feedWheel.set(0);
    }

    

    /*
     * While the amp button is being held, spin both motors to "spit" the note
     * out at a lower speed into the amp
     *
     * (this may take some driver practice to get working reliably)
     */
    if(m_controller.getRawButton(ampFunctionButton))
    {
      m_feedWheel.set(FEEDER_AMP_SPEED);
      m_launchWheel.set(LAUNCHER_AMP_SPEED);
    }
    else if(m_controller.getRawButtonReleased(ampFunctionButton))
    {
      m_feedWheel.set(0);
      m_launchWheel.set(0);
    }

    /**
     * Hold one of the two buttons to either intake or exjest note from roller claw
     * 
     * One button is positive claw power and the other is negative
     * 
     * It may be best to have the roller claw passively on throughout the match to 
     * better retain notes but we did not test this
     */ 
    /*if(m_controller.getRawButton(3))
    {
      m_rollerClaw.set(CLAW_OUTPUT_POWER);
    }
    else if(m_controller.getRawButton(4))
    {
      m_rollerClaw.set(-CLAW_OUTPUT_POWER);
    }
    else
    {
      m_rollerClaw.set(0);
    }
*/
    /**
     * POV is the D-PAD (directional pad) on your controller, 0 == UP and 180 == DOWN
     * 
     * After a match re-enable your robot and unspool the climb
     */
    if(m_controller.getPOV() == 0)
    {
      m_climber.set(1);
    }
    else if(m_controller.getPOV() == 180)
    {
      m_climber.set(-1);
    }
    else
    {
      m_climber.set(0);
    }
    /*Topworks code*/
    /////////////////
  } 

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
  

  
}
