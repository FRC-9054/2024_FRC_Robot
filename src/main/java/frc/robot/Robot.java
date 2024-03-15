
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
*         V1.2.1  | All          |   Added autonumous code
*         V1.2.2  | Quaid        |   Finished creating autonomous functions and
*                 |              |      implimenting a start delay and launch delay for
*                 |              |      autonomous. Still need to add selection logic
*                 |              |      and selections to the shoffleboard.
*         V1.3.0  | Quaid        |   Changed launch mode to use a single button instead of
*                 |              |      2 sepperate buttons. Added limmit swich to keep
*                 |              |      intake from intaking when note is inside. Adjusted
*                 |              |      some parameters.
*         V1.4.0  | Quaid        |   Autonomous options fully implimented. Need to set the
*                 |              |      step times for each option. Current times are just
*                 |              |      place holders.
*         V1.4.1  | Quaid        |   Cleaned up extra code and comments.
*         V1.5.0  | Quaid        |   Added support for a limmit swich to detect the fully
*                 |              |      retracted state of the climber. 
*         V1.6.0  | Damien       |   This code is the resulting code from the Belton
*                 | Faylynn      |      district event with the addition of uncommenting
*                 |              |      some auto code and opening up the drivetrain speed
*                 |              |      to %100. NOTE: the auto code in this version WILL
*                 |              |      NOT WORK. The code uses while loops which prevent
*                 |              |      the FMS from being able to make function calls to
*                 |              |      our robot. This must be fixed, or they may disable
*                 |              |      our robot for the entire match. I will likely revert
*                 |              |      to V1.5.0, and fix the auto logic from that version.
*                 |              |      V1.5.0 auto logic ran the first step and never
*                 |              |      continued passed that point. -Quaid
*         V1.6.1  | All          |   This code is only working teliop code. BAD AUTO
*          
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
 * revert and fix auto code
 *  1. auto is all screwed up. look at version history (latest entry) for details
 * write test code
 *  1. auto step tuning actions
 *  2. hardware test
 *    a. spin each motor on drivetrain to verrify all are driving bot
 *    b. spin each intake motor to verrify all are driving bot
 *    c. extend elevator
 *    d. prompt user to manualy click each limmit swich
 *    e. retract elevator till limmit sw is activated
 *  3. manual elevator reset
 *    a. make option to adjust w/out remote
 *      1. possibly a couple of onboard buttons that plug into DIO 1 and 2
 */

package frc.robot;



import java.util.Optional;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class Robot extends TimedRobot {
  public enum Speed {
    LOW, MEDIUM, HIGH
  }

  public enum Start_Pos {
    LEFT, CENTER, RIGHT, SPEAKER, AMP
  }

  public enum Speaker_Pos {
    LEFT, CENTER, RIGHT
  }

  enum Auto_Options {
    NONE, exitFromLeftOrRight
  }

  enum Logic {
    OLD, NEW
  }

  private final CANSparkMax m_motorcontroller1 = new CANSparkMax(1, MotorType.kBrushed); // Front Left
  private final CANSparkMax m_motorcontroller2 = new CANSparkMax(2, MotorType.kBrushed); // Back Left
  private final CANSparkMax m_motorcontroller3 = new CANSparkMax(3, MotorType.kBrushed); // Front Right
  private final CANSparkMax m_motorcontroller4 = new CANSparkMax(4, MotorType.kBrushed); // Back Right

  private final DifferentialDrive m_robotDrive =
      new DifferentialDrive(m_motorcontroller1, m_motorcontroller3);
  private final static Joystick m_controller = new Joystick(0); // note: here is how a controler is created. It will use the same functions as this one but have a different name and number
  private final static Joystick n_controller = new Joystick(3);

  Optional<Alliance> ally = DriverStation.getAlliance();

  double autoStartDelay = SmartDashboard.getNumber("Auto Start Delay", 0);
  double autoLaunchDelay = SmartDashboard.getNumber("Auto Launch Delay", 0);

  Timer timer = new Timer();
  // NOTE: variables
  // vvvvvvvvvvvvvvv
  boolean arcadeActive;
  boolean previousMode;
  double previousArcadeMotorSpeed = 0;
  double previousLeftMotorSpeed = 0;
  double previousRightMotorSpeed = 0;
  double launchStartTime = 0;
  double launchTimeElapsed = 0;
  double teliopLaunchTimeElapsed = 0;
  double teliopLaunchStartTime = 0;
  double launchWheelTime = 1.0;
  Boolean previousLaunchButtonPos = false;

  // note: button functions 
  // vvvvvvvvvvvvvvvvvvvvvv
  int drivetrainRotateAxis = 0;
  int drivetrainSpeedAxis = 5;
  // int rightAxis = 1;
  // int leftAxis =4;

  int ampFunctionButton = 2;
  int intakeFunctionButton = 5;
  // int feedwheelFunctionButton = 6;
  // int launchwheelFunctionButton = 3;

  int elevatorExtendFunctionButtonPos = 0; // this is a position on the POV. Must be 0, 45, 90, 135, 180, 225, 270, or 315. front = 0   right = 90   back = 180   left = 270
  int elevatorRetractFunctionButtonPos = 180; // this is a position on the POV. Must be 0, 45, 90, 135, 180, 225, 270, or 315. front = 0   right = 90   back = 180   left = 270

  // note: tunables
  // vvvvvvvvvvvvvv
  Double motorSpeedLimit = -1.0; // note: why are we inverting the speed limmit and each controler input?
  Double motorRotateSpeedLimit = -0.63;

  /* Both of the motors used on the KitBot launcher are CIMs which are brushed motors*/
  CANSparkBase m_launchWheel = new CANSparkMax(7, MotorType.kBrushed);
  // private final WPI_VictorSPX m_feedWheel = new WPI_VictorSPX(31);

  // CANSparkBase m_feedWheel = new CANSparkMax(8, MotorType.kBrushed);
  private final WPI_VictorSPX m_feedWheel = new WPI_VictorSPX(31);

  int noteDetectionLimitSwichPin = 9;
  int elevatorHomeLimmitSwichPin = 8;

  DigitalInput noteDetectionLimitSwich = new DigitalInput(noteDetectionLimitSwichPin);
  DigitalInput elevatorHomeLimmitSwich = new DigitalInput(elevatorHomeLimmitSwichPin);

  void launchNote(double launchTimeElapsed) {
    double l_launchTimeElapsed = launchTimeElapsed;
    SmartDashboard.putNumber("l_launchTimeElapsed", l_launchTimeElapsed);
    if (l_launchTimeElapsed < launchWheelTime) {
      m_launchWheel.set(LAUNCHER_SPEED);
      m_feedWheel.set(0);
    } else {
      m_launchWheel.set(LAUNCHER_SPEED);
      m_feedWheel.set(FEEDER_OUT_SPEED);
    }
  }

  void autodriveRed(double launchTimeElapsed) {}

  private static final String kNothingAuto = "do nothing";
  private static final String kExitFromLeftOrRight = "exit left/right";
  private static final String kExitFromCenter = "exit center";
  private static final String kExitFromAmp = "exit amp";
  private static final String kExitFromSpeakerLeft = "exit speaker left";
  private static final String kExitFromSpeakerCenter = "exit speaker center";
  private static final String kExitFromSpeakerRight = "exit speaker right";
  private static final String kLaunchAndExitFromSpeakerLeft = "launch exit speaker left";
  private static final String kLaunchAndExitFromSpeakerCenter = "launch exit speaker center";
  private static final String kLaunchAndExitFromSpeakerRight = "launch exit speaker right";


  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  // fixme : fix ramp fnc
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
  static final double FEEDER_IN_SPEED = .1;

  static final double LAUNCHER_IN_SPEED = .6;
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
   * Percent output for scoring in amp or dropping note, configure based on polycarb bend .14 works
   * well with no bend from our testing
   */
  static final double LAUNCHER_AMP_SPEED = .25;
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
  static final double CLIMER_EXTEND_POWER = .4;
  static final double CLIMER_RETRACT_POWER = .4;


  @Override
  public void robotInit() {
    CameraServer.startAutomaticCapture();

    m_motorcontroller1.setInverted(false);
    m_motorcontroller3.setInverted(true); // note: only invert this one because the otheer follows

    m_feedWheel.setInverted(true);
    m_launchWheel.setInverted(true);

    // m_rollerClaw.setInverted(false);
    m_climber.setInverted(true);


    // UPDATE:
    // UPDATE:
    // UPDATE:
    // UPDATE:
    // UPDATE: UNCOMMENT THE AUTO OPTIONS THAT YOU WANT TO HAVE AVAILABLE
    m_motorcontroller2.follow(m_motorcontroller1);
    m_motorcontroller4.follow(m_motorcontroller3);
    m_chooser.setDefaultOption("do nothing", kNothingAuto);
    m_chooser.addOption(kExitFromLeftOrRight, kExitFromLeftOrRight);
    // m_chooser.addOption(kExitFromCenter, kExitFromCenter);    
    // m_chooser.addOption(kExitFromAmp, kExitFromAmp);    
    // m_chooser.addOption(kExitFromSpeakerLeft, kExitFromSpeakerLeft);    
    // m_chooser.addOption(kExitFromSpeakerCenter, kExitFromSpeakerCenter);    
    // m_chooser.addOption(kExitFromSpeakerRight, kExitFromSpeakerRight);    
    // m_chooser.addOption(kLaunchAndExitFromSpeakerLeft, kLaunchAndExitFromSpeakerLeft);    
    m_chooser.addOption(kLaunchAndExitFromSpeakerCenter, kLaunchAndExitFromSpeakerCenter);
    // m_chooser.addOption(kLaunchAndExitFromSpeakerRight, kLaunchAndExitFromSpeakerRight);   
    SmartDashboard.putData("Auto choices", m_chooser);


    m_motorcontroller1.setSmartCurrentLimit(DRIVE_CURRENT_LIMIT_A);
    m_motorcontroller2.setSmartCurrentLimit(DRIVE_CURRENT_LIMIT_A);
    m_motorcontroller3.setSmartCurrentLimit(DRIVE_CURRENT_LIMIT_A);
    m_motorcontroller4.setSmartCurrentLimit(DRIVE_CURRENT_LIMIT_A);


    // m_rollerClaw.setSmartCurrentLimit(60);
    m_climber.setSmartCurrentLimit(60);

    // m_rollerClaw.setIdleMode(IdleMode.kBrake);
    m_climber.setIdleMode(IdleMode.kBrake);
  }

  public void robotPeriodic() {
    SmartDashboard.putNumber("Time (seconds)", Timer.getFPGATimestamp());
  }

  //  CANSparkBase m_rollerClaw = new CANSparkMax(8, MotorType.kBrushed);

  CANSparkBase m_climber = new CANSparkMax(6, MotorType.kBrushless);


  // --------------- Magic numbers. Use these to adjust settings. ---------------



  double AUTO_LAUNCH_DELAY_S;
  double AUTO_DRIVE_DELAY_S;

  double AUTO_DRIVE_TIME_S;

  double AUTO_DRIVE_SPEED;
  double AUTO_LAUNCHER_SPEED;

  double autonomousStartTime;
  double currentTime;



  void driveForward() {
    m_robotDrive.tankDrive(.4, .5);
  }

  void driveForward(Speed speed) {
    switch (speed) {
      case LOW:
        m_robotDrive.tankDrive(.4, .5);
        break;
      case MEDIUM:
        m_robotDrive.tankDrive(.4, .5);
        break;
      case HIGH:
        m_robotDrive.tankDrive(.4, .5);
        break;
      default:
        m_robotDrive.tankDrive(.4, .5);
        break;
    }
  }

  void driveBackward() {
    m_robotDrive.tankDrive(-.4, -.5);
  }

  void driveBackward(Speed speed) {
    switch (speed) {
      case LOW:
        m_robotDrive.tankDrive(-.4, -.5);
        break;
      case MEDIUM:
        m_robotDrive.tankDrive(-.4, -.5);
        break;
      case HIGH:
        m_robotDrive.tankDrive(-.4, -.5);
        break;
      default:
        m_robotDrive.tankDrive(-.4, -.5);
        break;
    }
  }

  void turnLeft() {
    m_robotDrive.tankDrive(0, .6);
  }

  void turnRight() {
    m_robotDrive.tankDrive(.6, 0);
  }

  void extendClimber() {
    m_climber.set(CLIMER_EXTEND_POWER);
  }

  void retractClimber() {
    m_climber.set(CLIMER_RETRACT_POWER * -1);
    if (!elevatorHomeLimmitSwich.get()) {
      m_climber.set(CLIMER_RETRACT_POWER * -1);
    } else {
      m_climber.set(0);
    }
  }

  void updateClimber(Logic logicVersion) {
    switch (logicVersion) {
      case OLD:
        if (n_controller.getPOV() == elevatorExtendFunctionButtonPos) {
          m_climber.set(CLIMER_EXTEND_POWER);
        } else if (n_controller.getPOV() == elevatorRetractFunctionButtonPos) {
          m_climber.set(CLIMER_RETRACT_POWER * -1);
        } else {
          m_climber.set(0);
        }
        break;
      case NEW:
        if (n_controller.getPOV() == elevatorExtendFunctionButtonPos) {
          extendClimber();
        } else if (n_controller.getPOV() == elevatorRetractFunctionButtonPos) {
          retractClimber();
        } else {
          m_climber.set(0);
        }
        break;
    }
  }

  /*auto functions*/
  /*VVVVVVVVVVVVVV*/


  void exitFromLeftOrRight(double autoTimeElapsed) {

    if (autoTimeElapsed <= autoStartDelay) {

      if (ally.get() == Alliance.Red) {
        if (Timer.getFPGATimestamp() < currentTime + 4.0) {
          m_robotDrive.tankDrive(0.5, 0.5);
        }
        if (Timer.getFPGATimestamp() >= currentTime + 5.0) {
          m_robotDrive.tankDrive(0, 0);
        }
      }

      if (ally.get() == Alliance.Blue) {
        if (Timer.getFPGATimestamp() < currentTime + 4.0) {
          m_robotDrive.tankDrive(0.5, 0.5);
        }
        if (Timer.getFPGATimestamp() >= currentTime + 5.0) {
          m_robotDrive.tankDrive(0, 0);
        }
      }
    }
  }

  void exitFromCenter(double autoTimeElapsed) {

    if (ally.get() == Alliance.Red) {
      Timer cenRedTimer = new Timer();

      cenRedTimer.reset();
      if (cenRedTimer.get() < 3.0) {
        m_robotDrive.tankDrive(0, 0);
      }
      if (cenRedTimer.get() < 6.0 && cenRedTimer.get() > 3.0) {
        driveForward();
      }
      if (cenRedTimer.get() < 9.0 && cenRedTimer.get() > 6.0) {
        turnRight();
      }
      if (cenRedTimer.get() < 12.0 && cenRedTimer.get() > 9.0) {
        driveForward();
      }
      if (cenRedTimer.get() < 15.0 && cenRedTimer.get() > 12.0) {
        m_robotDrive.tankDrive(0, 0);
      }
    }

    if (ally.get() == Alliance.Blue) {
      Timer cenBluTimer = new Timer();

      cenBluTimer.reset();
      if (cenBluTimer.get() < 3.0) {
        m_robotDrive.tankDrive(0, 0);
      }
      if (cenBluTimer.get() < 6.0 && cenBluTimer.get() > 3.0) {
        driveForward();
      }
      if (cenBluTimer.get() < 9.0 && cenBluTimer.get() > 6.0) {
        turnLeft();
      }
      if (cenBluTimer.get() < 12.0 && cenBluTimer.get() > 9.0) {
        driveForward();
      }
      if (cenBluTimer.get() < 15.0 && cenBluTimer.get() > 12.0) {
        m_robotDrive.tankDrive(0, 0);
      }
    }
  }


  void exitFromAmp() {

    Timer redtimer = new Timer();

    redtimer.restart();
    if (redtimer.get() < 4.0) {
      m_robotDrive.tankDrive(-0.5, -0.5);
    }
    redtimer.restart();
    if (redtimer.get() < 2.0) {
      turnRight();
    }
    redtimer.restart();
    if (redtimer.get() < 5.0) {
      driveForward();
    }
    if (redtimer.get() >= 15.0) {
      m_robotDrive.tankDrive(0, 0);
    }

    if (ally.get() == Alliance.Blue) {
      Timer bluetimer = new Timer();

      bluetimer.restart();
      if (bluetimer.get() < 4.0) {
        m_robotDrive.tankDrive(-0.5, -0.5);
      }
      bluetimer.restart();
      if (bluetimer.get() < 2.0) {
        turnLeft();
      }
      bluetimer.restart();
      if (bluetimer.get() < 5.0) {
        driveForward();
      }
      if (bluetimer.get() >= 15.0) {
        m_robotDrive.tankDrive(0, 0);
      }
    }
  }



  void exitFromSpeakerA(double autoTimeElapsed) { // red left and blue right
    Timer ATimer = new Timer();

    ATimer.restart();
    if (ally.get() == Alliance.Blue) {
      if (ATimer.get() < 3.0) {
        driveBackward();
      }
      if (ATimer.get() < 6.0 && ATimer.get() > 3.0) {
        turnLeft();
      }
      if (ATimer.get() < 10.0 && ATimer.get() > 6.0) {
        driveBackward();
      }
      if (ATimer.get() == 15.0) {
        m_robotDrive.tankDrive(0, 0);
      }
    }

    if (ally.get() == Alliance.Red) {
      if (ATimer.get() < 3.0) {
        driveBackward();
      }
      if (ATimer.get() < 6.0 && ATimer.get() > 3.0) {
        turnRight();
      }
      if (ATimer.get() < 10.0 && ATimer.get() > 6.0) {
        driveBackward();
      }
      if (ATimer.get() == 15.0) {
        m_robotDrive.tankDrive(0, 0);
      }
    }
  }

  void exitFromSpeakerB(double autoTimeElapsed) { // red right and blue left
    Timer BTimer = new Timer();

    BTimer.restart();
    if (ally.get() == Alliance.Blue) {
      if (BTimer.get() < 3.0) {
        driveBackward();
      }
      if (BTimer.get() < 6.0 && BTimer.get() > 3.0) {
        turnRight();
      }
      if (BTimer.get() < 10.0 && BTimer.get() > 6.0) {
        driveBackward();
      }
      if (BTimer.get() == 15.0) {
        m_robotDrive.tankDrive(0, 0);
      }
    }

    if (ally.get() == Alliance.Red) {
      if (BTimer.get() < 3.0) {
        driveBackward();
      }
      if (BTimer.get() < 6.0 && BTimer.get() > 3.0) {
        turnLeft();
      }
      if (BTimer.get() < 10.0 && BTimer.get() > 6.0) {
        driveBackward();
      }
      if (BTimer.get() == 15.0) {
        m_robotDrive.tankDrive(0, 0);
      }
    }
  }

  void launchAndExitFromSpeakerA(double autoTimeElapsed) { // red left and blue right
    Timer launchATimer = new Timer();

    launchATimer.restart();
    if (ally.get() == Alliance.Red) {
      if (launchATimer.get() == 0.0) {
        m_robotDrive.tankDrive(0, 0);
      }
      if (launchATimer.get() < 1.25) {
        m_launchWheel.set(LAUNCHER_SPEED);
        m_feedWheel.set(0.0);
      }
      if (launchATimer.get() < 2.25) {
        m_launchWheel.set(LAUNCHER_SPEED);
        m_feedWheel.set(FEEDER_OUT_SPEED);
      }

      if (launchATimer.get() < 5.0 && launchATimer.get() > 2.25) {
        driveBackward();
        m_launchWheel.set(0);
        m_feedWheel.set(0);
      }
      if (launchATimer.get() < 8.0 && launchATimer.get() > 5.0) {
        turnLeft();
      }
      if (launchATimer.get() < 10.0 && launchATimer.get() > 8.0) {
        driveBackward();
      }
      if (launchATimer.get() == 15.0) {
        m_robotDrive.tankDrive(0, 0);
      }
    }

    if (ally.get() == Alliance.Blue) {
      if (launchATimer.get() == 0.0) {
        m_robotDrive.tankDrive(0, 0);
      }
      if (launchATimer.get() < 1.25) {
        m_launchWheel.set(LAUNCHER_SPEED);
        m_feedWheel.set(0.0);
      }
      if (launchATimer.get() < 2.25) {
        m_launchWheel.set(LAUNCHER_SPEED);
        m_feedWheel.set(FEEDER_OUT_SPEED);
      }

      if (Timer.getFPGATimestamp() < 5.0 && Timer.getFPGATimestamp() > 2.25) {
        driveBackward();
        m_launchWheel.set(0);
        m_feedWheel.set(0);
      }
      if (Timer.getFPGATimestamp() < 8.0 && Timer.getFPGATimestamp() > 5.0) {
        turnRight();
      }
      if (Timer.getFPGATimestamp() < 10.0 && Timer.getFPGATimestamp() > 8.0) {
        driveBackward();
      }
      if (Timer.getFPGATimestamp() == 15.0) {
        m_robotDrive.tankDrive(0, 0);
      }
    }
  }

  void launchAndExitFromSpeakerB(double autoTimeElapsed) { // red right and blue left

    if (ally.get() == Alliance.Red) {
      if (Timer.getFPGATimestamp() == 0.0) {
        m_robotDrive.tankDrive(0, 0);
      }
      if (Timer.getFPGATimestamp() < 1.25 && Timer.getFPGATimestamp() > 0.0) {
        m_launchWheel.set(LAUNCHER_SPEED);
        m_feedWheel.set(0.0);
      }
      if (Timer.getFPGATimestamp() < 2.25 && Timer.getFPGATimestamp() > 1.25) {
        m_launchWheel.set(LAUNCHER_SPEED);
        m_feedWheel.set(FEEDER_OUT_SPEED);
      }

      if (Timer.getFPGATimestamp() < 5.0 && Timer.getFPGATimestamp() > 2.25) {
        driveBackward();
        m_launchWheel.set(0);
        m_feedWheel.set(0);
      }
      if (Timer.getFPGATimestamp() < 8.0 && Timer.getFPGATimestamp() > 5.0) {
        turnLeft();
      }
      if (Timer.getFPGATimestamp() < 10.0 && Timer.getFPGATimestamp() > 8.0) {
        driveBackward();
      }
      if (Timer.getFPGATimestamp() == 15.0) {
        m_robotDrive.tankDrive(0, 0);
      }
    }

    if (ally.get() == Alliance.Blue) {
      if (Timer.getFPGATimestamp() == 0.0) {
        m_robotDrive.tankDrive(0, 0);
      }
      if (Timer.getFPGATimestamp() < 1.25 && Timer.getFPGATimestamp() > 0.0) {
        m_launchWheel.set(LAUNCHER_SPEED);
        m_feedWheel.set(0.0);
      }
      if (Timer.getFPGATimestamp() < 2.25 && Timer.getFPGATimestamp() > 1.25) {
        m_launchWheel.set(LAUNCHER_SPEED);
        m_feedWheel.set(FEEDER_OUT_SPEED);
      }

      if (Timer.getFPGATimestamp() < 5.0 && Timer.getFPGATimestamp() > 2.25) {
        driveBackward();
        m_launchWheel.set(0);
        m_feedWheel.set(0);
      }
      if (Timer.getFPGATimestamp() < 8.0 && Timer.getFPGATimestamp() > 5.0) {
        turnRight();
      }
      if (Timer.getFPGATimestamp() < 10.0 && Timer.getFPGATimestamp() > 8.0) {
        driveBackward();
      }
      if (Timer.getFPGATimestamp() == 15.0) {
        m_robotDrive.tankDrive(0, 0);
      }
    }

  }

  void exitFromSpeakerLeft(double autoTimeElapsed) {
    if (autoTimeElapsed <= autoStartDelay) {
      m_robotDrive.tankDrive(0, 0);
    } else {
      if (ally.get() == Alliance.Red) {
        exitFromSpeakerA(autoTimeElapsed);
      }
      if (ally.get() == Alliance.Blue) {
        exitFromSpeakerB(autoTimeElapsed);
      }
    }
  }

  void exitFromSpeakerCenter(double autoTimeElapsed) {

    if (autoTimeElapsed <= autoStartDelay && autoTimeElapsed > autoStartDelay + autoLaunchDelay) {
      m_robotDrive.tankDrive(0, 0);
    } else {
      Optional<Alliance> ally = DriverStation.getAlliance();
      if (ally.get() == Alliance.Red) {
        double fpgaTimestamp = Timer.getFPGATimestamp();
        if (fpgaTimestamp < 3.0) {
          driveBackward();
        }
        if (Timer.getFPGATimestamp() < 6.0 && Timer.getFPGATimestamp() > 3.0) {
          turnRight();
        }
        if (Timer.getFPGATimestamp() < 9.0 && Timer.getFPGATimestamp() > 6.0) {
          driveBackward();
        }
        if (Timer.getFPGATimestamp() < 12.0 && Timer.getFPGATimestamp() > 9.0) {
          turnLeft();
        }
        if (Timer.getFPGATimestamp() < 15.0 && Timer.getFPGATimestamp() > 12.0) {
          driveBackward();
        }
        if (Timer.getFPGATimestamp() == 15.0) {
          m_robotDrive.tankDrive(0, 0);
        }
      }
      if (ally.get() == Alliance.Blue) {
        if (Timer.getFPGATimestamp() < 3.0) {
          driveBackward();
        }
        if (Timer.getFPGATimestamp() < 6.0 && Timer.getFPGATimestamp() > 3.0) {
          turnLeft();
        }
        if (Timer.getFPGATimestamp() < 9.0 && Timer.getFPGATimestamp() > 6.0) {
          driveBackward();
        }
        if (Timer.getFPGATimestamp() < 12.0 && Timer.getFPGATimestamp() > 9.0) {
          turnRight();
        }
        if (Timer.getFPGATimestamp() < 15.0 && Timer.getFPGATimestamp() > 12.0) {
          driveBackward();
        }
        if (Timer.getFPGATimestamp() == 15.0) {
          m_robotDrive.tankDrive(0, 0);
        }
      }
    }
  }

  void exitFromSpeakerRight(double autoTimeElapsed) {
    if (autoTimeElapsed <= autoStartDelay) {
      m_robotDrive.tankDrive(0, 0);
    } else {
      if (ally.get() == Alliance.Blue) {
        exitFromSpeakerA(autoTimeElapsed);
      }
      if (ally.get() == Alliance.Red) {
        exitFromSpeakerB(autoTimeElapsed);
      }
    }
  }

  void launchAndExitFromSpeakerLeft(double autoTimeElapsed) {
    if (autoTimeElapsed <= autoStartDelay) {
      m_robotDrive.tankDrive(0, 0);
    } else {
      if (ally.get() == Alliance.Red) {
        launchAndExitFromSpeakerA(autoTimeElapsed);
      }
      if (ally.get() == Alliance.Blue) {
        launchAndExitFromSpeakerB(autoTimeElapsed);
      }
    }
  }

  void launchAndExitFromSpeakerRight(double autoTimeElapsed) {
    if (autoTimeElapsed <= autoStartDelay) {
      m_robotDrive.tankDrive(0, 0);
    } else {
      if (ally.get() == Alliance.Red) {
        launchAndExitFromSpeakerB(autoTimeElapsed);
      }
      if (ally.get() == Alliance.Blue) {
        launchAndExitFromSpeakerA(autoTimeElapsed);
      }
    }
  }

  void launchAndExitFromSpeakerCenter(double autoTimeElapsed) {

    if (ally.get() == Alliance.Red) {
      if (Timer.getFPGATimestamp() == 0.0) {
        m_robotDrive.tankDrive(0, 0);
      }
      if (Timer.getFPGATimestamp() < currentTime + 1.25) {
        m_launchWheel.set(LAUNCHER_SPEED);
        m_feedWheel.set(0.0);
      }
      if (Timer.getFPGATimestamp() < currentTime + 2.25) {
        m_launchWheel.set(LAUNCHER_SPEED);
        m_feedWheel.set(FEEDER_OUT_SPEED);
      }

    }
    if (Timer.getFPGATimestamp() < currentTime + 5.25
        && Timer.getFPGATimestamp() > currentTime + 2.25) {
      //driveForward();
      m_launchWheel.set(0);
      m_feedWheel.set(0);
      //  } while (launchTimer.get() < 8.0 && launchTimer.get() > 5.0) {
      //   turnRight();
      // } while (launchTimer.get() < 10.0 && launchTimer.get() > 8.0) {
      //   driveForward();
      // } while (launchTimer.get() < 12.0 && launchTimer.get() > 10.0) {
      //   turnLeft();
      // } while (launchTimer.get() < 15.0 && launchTimer.get() > 12.0) {
      //   driveForward();
    }
    if (Timer.getFPGATimestamp() >= currentTime + 5.25) {
      m_robotDrive.tankDrive(0, 0);
    }



    if (ally.get() == Alliance.Blue) {
      if (Timer.getFPGATimestamp() == 0.0) {
        m_robotDrive.tankDrive(0, 0);
      }
      if (Timer.getFPGATimestamp() < currentTime + 1.25) {
        m_launchWheel.set(LAUNCHER_SPEED);
        m_feedWheel.set(0.0);
      }
      if (Timer.getFPGATimestamp() < currentTime + 2.25) {
        m_launchWheel.set(LAUNCHER_SPEED);
        m_feedWheel.set(FEEDER_OUT_SPEED);
      }

      if (Timer.getFPGATimestamp() < currentTime + 5.25
          && Timer.getFPGATimestamp() > currentTime + 2.25) {
        //driveBackward();
        m_launchWheel.set(0);
        m_feedWheel.set(0);
        //  } while (launchTimer.get() < 8.0 && launchTimer.get() > 5.0) {
        //   turnLeft();
        // } while (launchTimer.get() < 10.0 && launchTimer.get() > 8.0) {
        //   driveBackward();
        // } while (launchTimer.get() < 12.0 && launchTimer.get() > 10.0) {
        //   turnRight();
        // } while (launchTimer.get() < 15.0 && launchTimer.get() > 12.0) {
        //   driveBackward();
      }
      if (Timer.getFPGATimestamp() >= currentTime + 5.25) {
        m_robotDrive.tankDrive(0, 0);
      }
    }
  }


  /*^^^^^^^^^^^^^^*/
  /*auto functions*/


  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();

    autoStartDelay = SmartDashboard.getNumber("Auto Start Delay", 0);
    autoLaunchDelay = SmartDashboard.getNumber("Auto Launch Delay", 0);

    m_motorcontroller1.setIdleMode(IdleMode.kBrake);
    m_motorcontroller2.setIdleMode(IdleMode.kBrake);
    m_motorcontroller3.setIdleMode(IdleMode.kBrake);
    m_motorcontroller4.setIdleMode(IdleMode.kBrake);

    AUTO_LAUNCH_DELAY_S = 7;

    AUTO_DRIVE_TIME_S = 2.0;
    AUTO_DRIVE_SPEED = -0.5;
    AUTO_LAUNCHER_SPEED = 1;

    // autonomousStartTime = Timer.getFPGATimestamp();
    // timer.restart();

    switch (m_autoSelected) {
      case kNothingAuto:
        m_robotDrive.arcadeDrive(0, 0);
        break;

      case kExitFromLeftOrRight:
        exitFromLeftOrRight(0);
        break;

      case kExitFromCenter:
        exitFromCenter(0);
        break;

      case kExitFromAmp:
        exitFromAmp();
        break;

      case kExitFromSpeakerLeft:
        exitFromSpeakerLeft(0);
        break;

      case kExitFromSpeakerCenter:
        exitFromSpeakerCenter(0);
        break;

      case kExitFromSpeakerRight:
        exitFromSpeakerRight(0);
        break;

      case kLaunchAndExitFromSpeakerLeft:
        launchAndExitFromSpeakerLeft(0);
        break;

      case kLaunchAndExitFromSpeakerCenter:
        launchAndExitFromSpeakerCenter(0);
        break;

      case kLaunchAndExitFromSpeakerRight:
        launchAndExitFromSpeakerRight(0);
        break;

      default:
        m_robotDrive.arcadeDrive(0, 0);
        break;
    }
  }


  @Override
  public void autonomousPeriodic() {
    // if (timer.get()<5){
    //   m_launchWheel.set(LAUNCHER_SPEED);
    //   m_feedWheel.set(0);
    // }
    // else if (timer.get()<7){
    //   m_launchWheel.set(LAUNCHER_SPEED);
    //   m_feedWheel.set(FEEDER_OUT_SPEED);
    // }
    // else{
    //   m_launchWheel.set(0);
    //   m_feedWheel.set(0);
    // }
  }

  //   double autoTimeElapsed = Timer.getFPGATimestamp() - autonomousStartTime;
  //   SmartDashboard.putNumber("autoTimeElapsed", autoTimeElapsed);

  //   switch (m_autoSelected) {
  //     case kNothingAuto:
  //       m_robotDrive.arcadeDrive(0, 0);
  //       break;

  //     case kExitFromLeftOrRight:
  //       exitFromLeftOrRight(autoTimeElapsed);
  //       break;

  //     case kExitFromCenter:
  //       exitFromCenter(autoTimeElapsed);
  //       break;

  //     case kExitFromAmp:
  //       exitFromAmp(autoTimeElapsed);
  //       break;

  //     case kExitFromSpeakerLeft:
  //       exitFromSpeakerLeft(autoTimeElapsed);
  //       break;

  //     case kExitFromSpeakerCenter:
  //       exitFromSpeakerCenter(autoTimeElapsed);
  //       break;

  //     case kExitFromSpeakerRight:
  //       exitFromSpeakerRight(autoTimeElapsed);
  //       break;

  //     case kLaunchAndExitFromSpeakerLeft:
  //       launchAndExitFromSpeakerLeft(autoTimeElapsed);
  //       break;

  //     case kLaunchAndExitFromSpeakerCenter:
  //       launchAndExitFromSpeakerCenter(autoTimeElapsed);
  //       break;

  //     case kLaunchAndExitFromSpeakerRight:
  //       launchAndExitFromSpeakerRight(autoTimeElapsed);
  //       break;

  //     default:
  //       m_robotDrive.arcadeDrive(0, autoTimeElapsed);
  //       break;
  //   }



  @Override
  public void teleopInit() {
    m_motorcontroller1.setIdleMode(IdleMode.kBrake);
    m_motorcontroller2.setIdleMode(IdleMode.kBrake);
    m_motorcontroller3.setIdleMode(IdleMode.kBrake);
    m_motorcontroller4.setIdleMode(IdleMode.kBrake);
  }


  @Override
  public void teleopPeriodic() {
    m_robotDrive.arcadeDrive(-m_controller.getRawAxis(drivetrainSpeedAxis) * motorSpeedLimit,
        -m_controller.getRawAxis(drivetrainRotateAxis) * motorRotateSpeedLimit);



    if (m_controller.getRawButton(intakeFunctionButton) && noteDetectionLimitSwich.get()) {
      m_launchWheel.set(-LAUNCHER_IN_SPEED);
      m_feedWheel.set(-FEEDER_IN_SPEED);
    } else {
      m_launchWheel.set(0);
      m_feedWheel.set(0);
    }


    if (m_controller.getRawButton(ampFunctionButton)) {
      m_feedWheel.set(FEEDER_AMP_SPEED);
      m_launchWheel.set(LAUNCHER_AMP_SPEED);
    } else if (m_controller.getRawButtonReleased(ampFunctionButton)) {
      m_feedWheel.set(0);
      m_launchWheel.set(0);
    }

    Boolean launchButtonPos = m_controller.getRawButton(1);
    SmartDashboard.putBoolean("launchButtonPos", launchButtonPos);

    if (launchButtonPos) {
      if (!previousLaunchButtonPos) {
        previousLaunchButtonPos = launchButtonPos;
        teliopLaunchStartTime = Timer.getFPGATimestamp();
        teliopLaunchTimeElapsed = Timer.getFPGATimestamp() - launchStartTime;
      } else {
        previousLaunchButtonPos = launchButtonPos;
        teliopLaunchTimeElapsed = Timer.getFPGATimestamp() - teliopLaunchStartTime;
      }
      launchNote(teliopLaunchTimeElapsed);
    } else {
      previousLaunchButtonPos = launchButtonPos;
    }

    updateClimber(Logic.OLD);
  }


  @Override
  public void testInit() {}


  @Override
  public void testPeriodic() {}

}
