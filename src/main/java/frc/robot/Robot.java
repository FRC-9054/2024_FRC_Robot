
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
*         V1.5.1  | Quaid        |   This code has been reverted to a save shortly after day 1
*                 |              |      Belton has started. This code is messed up, but it
*                 |              |      should have the correct step order for each auto (check
*                 |              |      V1.5.0 to be sure), but there is an example of how to
*                 |              |      fix the auto logic problem.
*         V1.5.2  | Quaid        |   Updated exitFromLeftOrRight fn. Need to test and see if
*                 |              |      it runs properly. If it does, then we need to merge the
*                 |              |      teliop changes into this V1.5.x auto_fix branch and test
*                 |              |      agian. Need to be sure that we only keep teliop changes
*                 |              |      from main branch.
*         V1.5.3  | Quaid        |   The previous code didnt work because the step time vars
*                 |              |      assumed milliseconds and the FPGA timer returns seconds
*                 |              |      as a double.
*         V1.5.4  | Quaid        |   Meerged in non auto changes from main branch V1.6.1 for
*                 |              |      testing. If evereything behaves as expected, this code
*                 |              |      will become V1.6.2 on the main branch
*         V1.5.5  | Quaid        |   Auton logic fixed. Use the same logic that is in the 
*                 |              |      exitFromLeftOrRight fn. The initial bug that caused all
*                 |              |      of our issues was due to FPGA returning time as seconds,
*                 |              |      not miliseconds, and our auto logic used miliseconds to
*                 |              |      determine what step to run. The bug caused all of our
*                 |              |      times to be 1000 times larger than we thought. There was
*                 |              |      also an issue with forward being backward and backward
*                 |              |      being forward, that was fixed as well.
*         V1.5.6  | Quaid        |   There was a major bug causing the rio to crash durring the
*                 |              |      match but not durring testing. The issue was caused by
*                 |              |      the getAlly fn being called when the rio was powered on
*                 |              |      but never agian. The rio was initializing the variable
*                 |              |      with an unexpected value that crashed the code when that
*                 |              |      variable is used.
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
*         V1.7.0  | Quaid        |   This is a direct copy of V1.5.6 of the V1.5.x_auto_fix branch.
*                 |              |      The do nothing and exit left or right auto is confirmed as
*                 |              |      working at this point.
*         V1.7.1  | All          |   Finnessed the launch and exit from speaker left logic to ignore
*                 |              |      alliance color and just launch then drive backwards. Stopping
*                 |              |      this auto is done by hitting the e stop button if needed. We
*                 |              |      also tuned thee drive back function to not curve.
*         V1.8.0  | All          |  Post state comp code. Some attempts to debug autonomous. Still a
*                 |              |      problem.
*         V1.9.0  | Quaid        |  Code added to control addressable LEDs. Chase and set all to one
*                 | Damien       |      color functions.
*         V1.10.0 | Quaid        |  Updated vendor libraries.
*         V1.10.1 | Quaid        |  Renamed controllers to make code more readable, and moved all non
*                 |              |      drive controlls to the opperator controller.
*         V1.11.0 | Quaid Damien |  Fixed autonomous code. Turned on the limmit swich for the climber.
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

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.cameraserver.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import java.util.Optional;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.AddressableLED;

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
  private final static Joystick drive_controller = new Joystick(0);   // note: here is how a controler is created. It will use the same functions as this one but have a different name and number
  private final static Joystick opperator_controller = new Joystick(1);

  Optional<Alliance> ally;

  double autoStartDelay = SmartDashboard.getNumber("Auto Start Delay", 0);     // note: move to a periodic fn to use
  double autoLaunchDelay = SmartDashboard.getNumber("Auto Launch Delay", 0);
  // NOTE: variables
  // vvvvvvvvvvvvvvv
  boolean arcadeActive;
  boolean previousMode;
  double previousArcadeMotorSpeed = 0;
  double previousLeftMotorSpeed = 0;
  double previousRightMotorSpeed = 0;
  double launchStartTime = 0;
  // double launchTimeElapsed = 0;
  double teliopLaunchTimeElapsed = 0;
  double teliopLaunchStartTime = 0;
  double launchWheelTime = 1.0;
  Boolean previousLaunchButtonPos = false;
  boolean firstCall = true;
  double startTime = 0.0;

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

  int LEDPort = 9;
  int LEDLength = 92;
  // PWM port 9
  // Must be a PWM header, not MXP or DIO
  AddressableLED m_led = new AddressableLED(LEDPort);
  AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(LEDLength);
  int currentLEDIndex = 0;
  int delayCounter = 0;
  int delay = 5;

  void AddressableLEDSetup() {
    // Reuse buffer
    // Default to a length of 60, start empty output
    // Length is expensive to set, so only set it once, then just update data
    m_led.setLength(m_ledBuffer.getLength());

    // Set the data
    m_led.setData(m_ledBuffer);
    m_led.start();
  }

  void SetLEDsAll(int red, int green, int blue) {
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Sets the specified LED to the RGB values for red
      m_ledBuffer.setRGB(i, green, red, blue);
   }
   m_led.setData(m_ledBuffer);
  }

  void chaseLEDs(int p_delay, int p_redChase, int p_greenChase, int p_blueChase, int p_redBackground, int p_greenBackground, int p_blueBackground, int p_numSnakes, int p_snakeLength) {
    // Increment delay counter
    delayCounter++;
    // Check if it's time to update LED color
    if (delayCounter >= p_delay) {
        // Clear previous LED (set all LEDs to background color)
        for (int i = 0; i < LEDLength; i++) {
            m_ledBuffer.setRGB(i, p_greenBackground, p_redBackground, p_blueBackground);
        }

        // Calculate spacing between snakes
        int snakeSpacing = LEDLength / p_numSnakes;
        
        // Update each snake
        for (int j = 0; j < p_numSnakes; j++) {
            int startLEDIndex = (currentLEDIndex + j * snakeSpacing) % LEDLength;
            for (int i = 0; i < p_snakeLength; i++) { // Assuming each snake is 4 LEDs long
                int snakeLEDIndex = (startLEDIndex + i) % LEDLength;
                m_ledBuffer.setRGB(snakeLEDIndex, p_greenChase, p_redChase, p_blueChase);
            }
        }

        // Update LED strip
        m_led.setData(m_ledBuffer);
        // Increment LED index
        currentLEDIndex = (currentLEDIndex + 1) % LEDLength;
        // Reset delay counter
        delayCounter = 0;
    }
}





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

  void autodriveRed(double launchTimeElapsed) {
  }

  private static final String kNothingAuto = "do nothing";
  private static final String kLaunchAndExitStrait = "launch and drive backwards";
  private static final String kExitStrait = "drive backwards";
  private static final String kExitFromLeftOrRight = "exit left/right";
  private static final String kLaunch = "launch note";
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

  @Override
  public void robotInit() {
    AddressableLEDSetup();
    CameraServer.startAutomaticCapture();
    
    // NOTE: this sets up the front motors on the drivetrain
    m_motorcontroller1.setInverted(false);
    m_motorcontroller3.setInverted(true); // note: only invert this one because the otheer follows
    
    // NOTE: this sets up the rear motors to follow the front motors on the drivetrain
    m_motorcontroller2.follow(m_motorcontroller1);
    m_motorcontroller4.follow(m_motorcontroller3);

    m_feedWheel.setInverted(true);
    m_launchWheel.setInverted(true);
    
    // m_rollerClaw.setInverted(false);
    m_climber.setInverted(true);
    



    // UPDATE:
    // UPDATE:
    // UPDATE:
    // UPDATE:
    // UPDATE: UNCOMMENT THE AUTO OPTIONS THAT YOU WANT TO HAVE AVAILABLE
    m_chooser.setDefaultOption("do nothing", kNothingAuto);
    m_chooser.addOption(kExitFromLeftOrRight, kExitFromLeftOrRight);
    // m_chooser.addOption(kLaunch, kLaunch);
    // m_chooser.addOption(kExitFromCenter, kExitFromCenter);    
    // m_chooser.addOption(kExitFromAmp, kExitFromAmp);    
    // m_chooser.addOption(kExitFromSpeakerLeft, kExitFromSpeakerLeft);    
    // m_chooser.addOption(kExitFromSpeakerCenter, kExitFromSpeakerCenter);    
    // m_chooser.addOption(kExitFromSpeakerRight, kExitFromSpeakerRight);    
    m_chooser.addOption(kLaunchAndExitFromSpeakerLeft, kLaunchAndExitFromSpeakerLeft);    
    // m_chooser.addOption(kLaunchAndExitFromSpeakerCenter, kLaunchAndExitFromSpeakerCenter);    
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
    // get team color
    // set leds to team color
  }

  //  CANSparkBase m_rollerClaw = new CANSparkMax(8, MotorType.kBrushed);

  CANSparkBase m_climber = new CANSparkMax(6, MotorType.kBrushless);

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
  static final double FEEDER_IN_SPEED = .5;

  static final double LAUNCHER_IN_SPEED = .7;
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

  double AUTO_LAUNCH_DELAY_S;
  double AUTO_DRIVE_DELAY_S;

  double AUTO_DRIVE_TIME_S;

  double AUTO_DRIVE_SPEED;
  double AUTO_LAUNCHER_SPEED;

  double autonomousStartTime = 0.0;

 int m_rainbowFirstPixelHue = 10;

  private void rainbow() {
    // For every pixel
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to precess
      final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
      // Set the value
      m_ledBuffer.setHSV(i, hue, 255, 128);
    }
    // Increase by to make the rainbow "move"
    m_rainbowFirstPixelHue += 3;
    // Check bounds
    m_rainbowFirstPixelHue %= 180;
  }


  void driveBackward() {
    m_robotDrive.tankDrive(.5, .5);   // .5 and .42 maybe?
  }

  void driveBackward(Speed speed) {
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

  void driveForward() {
    m_robotDrive.tankDrive(-.4, -.5);
  }

  void driveForward(Speed speed) {
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
    m_robotDrive.tankDrive(0, -.6);
  }

  void turnRight() {
    m_robotDrive.tankDrive(-.6, 0);
  }

  void extendClimber() {
    m_climber.set(CLIMER_EXTEND_POWER);
  }

  void retractClimber() {
    m_climber.set(CLIMER_RETRACT_POWER * -1);
    if (elevatorHomeLimmitSwich.get()) {
      m_climber.set(CLIMER_RETRACT_POWER * -1);
    } else {
      m_climber.set(0);
    }
  }

  void updateClimber(Logic logicVersion) {
    switch (logicVersion) {
      case OLD:
        if (opperator_controller.getPOV() == elevatorExtendFunctionButtonPos) {
          m_climber.set(CLIMER_EXTEND_POWER);
        } else if (opperator_controller.getPOV() == elevatorRetractFunctionButtonPos) {
          m_climber.set(CLIMER_RETRACT_POWER * -1);
        } else {
          m_climber.set(0);
        }
        break;
      case NEW:
        if (opperator_controller.getPOV() == elevatorExtendFunctionButtonPos) {
          extendClimber();
        } else if (opperator_controller.getPOV() == elevatorRetractFunctionButtonPos) {
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

    double step1Time = 3.0;    // drive forward for 3 seconds

    m_feedWheel.set(0);
    m_launchWheel.set(0);

    if (autoTimeElapsed <= autoStartDelay) {
      m_robotDrive.tankDrive(0, 0);
    } else {
      if (ally.get() == Alliance.Red) {
        if (autoTimeElapsed <= autoStartDelay + step1Time) {
          driveBackward();
        } else {
          m_robotDrive.tankDrive(0, 0);
        }
      } else if (ally.get() == Alliance.Blue) {
        if (autoTimeElapsed <= autoStartDelay + step1Time) {
          driveBackward();
        } else {
          m_robotDrive.tankDrive(0, 0);
        }
      }
    }
  }

  void launch(double autoTimeElapsed) {
    if (autoTimeElapsed <= autoStartDelay) {
      m_robotDrive.tankDrive(0, 0);
    } else {}
  }

  void exitFromCenter(double autoTimeElapsed) {

    double step1time = 3000;
    double step2time = 3000;
    double step3time = 3000;
    double step4time = 3000;

    if (autoTimeElapsed <= autoStartDelay) {
      m_robotDrive.tankDrive(0, 0);
    } else {
      if (ally.get() == Alliance.Blue) {
        if (autoTimeElapsed <= autoStartDelay + step1time && autoTimeElapsed > autoStartDelay) {
          m_robotDrive.tankDrive(0, 0);
        } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time
            && autoTimeElapsed > autoStartDelay + step1time) {
          driveForward();
        } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time + step3time
            && autoTimeElapsed > autoStartDelay + step1time + step2time) {
          turnLeft();
        } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time + step3time + step4time
            && autoTimeElapsed > autoStartDelay + step1time + step2time + step3time) {
          driveForward();
        } else {
          m_robotDrive.tankDrive(0, 0);
        }
      }

      if (ally.get() == Alliance.Red) {
        if (autoTimeElapsed <= autoStartDelay + step1time) {
          m_robotDrive.tankDrive(0, 0);
        } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time) {
        }
        if (autoTimeElapsed <= autoStartDelay + step1time) {
          driveForward();
        } else if (autoTimeElapsed <= autoStartDelay + step1time + step2time) {
          turnRight();
        } else if (autoTimeElapsed <= autoStartDelay + step1time + step2time + step3time) {
          driveForward();
        } else {
          m_robotDrive.tankDrive(0, 0);
        }
      }
    }
  }
  

  void exitFromAmp(double autoTimeElapsed) {

    double step1time = 3.0;
    //double step2time = 4.0;
    //double step3time = 10.0;

    if (ally.get() == Alliance.Red) {
      do {
        driveBackward();
        if (autoTimeElapsed == 0.0) {
          break;
        }
      } while (step1time > autoTimeElapsed);
      // } while (step2time >= autoTimeElapsed && step1time < autoTimeElapsed) {
      //   turnLeft();
      // } while (step3time >= autoTimeElapsed && step2time < autoTimeElapsed ) {
      //   driveForward();

      m_robotDrive.tankDrive(0, 0);
    }

    else if (ally.get() == Alliance.Blue) {
      do {
        driveBackward();
        if (autoTimeElapsed == 0.0) {
          break;
        }
      } while (step1time > autoTimeElapsed);
      // } while (step2time >= autoTimeElapsed && step1time < autoTimeElapsed) {
      //   turnLeft();
      // } while (step3time >= autoTimeElapsed && step2time < autoTimeElapsed ) {
      //   driveForward();
    }
    m_robotDrive.tankDrive(0, 0);
  }

  void exitFromSpeakerA(double autoTimeElapsed) { // red left and blue right
    double step1time = 1000;
    double step2time = 1000;
    double step3time = 1000;

    if (ally.get() == Alliance.Red) {
      if (autoTimeElapsed <= autoStartDelay + step1time) {
        driveBackward();
      } else if (autoTimeElapsed <= autoStartDelay + step1time + step2time) {
        turnRight();
      } else if (autoTimeElapsed <= autoStartDelay + step1time + step2time + step3time) {
        driveBackward();
      } else {
        m_robotDrive.tankDrive(0, 0);
      }
    }

    if (ally.get() == Alliance.Blue) {
      if (autoTimeElapsed <= autoStartDelay + step1time) {
        driveBackward();
      } else if (autoTimeElapsed <= autoStartDelay + step1time + step2time) {
        turnLeft();
      } else if (autoTimeElapsed <= autoStartDelay + step1time + step2time + step3time) {
        driveBackward();
      } else {
        m_robotDrive.tankDrive(0, 0);
      }
    }
  }

  void exitFromSpeakerB(double autoTimeElapsed) { // red right and blue left
    double step1time = 3000;
    double step2time = 6000;
    double step3time = 10000;

    if (ally.get() == Alliance.Blue) {
      if (autoTimeElapsed <= autoStartDelay + step1time) {
        driveBackward();
      } else if (autoTimeElapsed <= autoStartDelay + step1time + step2time) {
        turnRight();
      } else if (autoTimeElapsed <= autoStartDelay + step1time + step2time + step3time) {
        driveBackward();
      } else {
        m_robotDrive.tankDrive(0, 0);
      }
    }

    if (ally.get() == Alliance.Red) {
      if (autoTimeElapsed <= autoStartDelay + step1time) {
        driveBackward();
      } else if (autoTimeElapsed <= autoStartDelay + step1time + step2time) {
        turnLeft();
      } else if (autoTimeElapsed <= autoStartDelay + step1time + step2time + step3time) {
        driveBackward();
      } else {
        m_robotDrive.tankDrive(0, 0);
      }
    }
  }

  void launchAndExitFromSpeakerA(double autoTimeElapsed) { // red left and blue right
    double step1time = 1.5;
    double step2time = 3.0;
    // double step3time = 0.0;
    // double step4time = 0.0;
    autoStartDelay = 1.0;

    

    // if (ally.get() == Alliance.Red) {
    if (true) {
      if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay) {
        m_robotDrive.tankDrive(0, 0);
      } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time) {
        if (firstCall) {
          startTime = Timer.getFPGATimestamp();
          launchNote(0.0);
          firstCall = false;
          SmartDashboard.putNumber("DBG pos", 1);
        } else {
          launchNote(Timer.getFPGATimestamp() - startTime);
          SmartDashboard.putNumber("DBG pos", 2);
        }
      } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time) {
        driveBackward();
      } else {

        m_robotDrive.tankDrive(0, 0);
      }
     
     }
      double launchTime = Timer.getFPGATimestamp() - startTime;
      SmartDashboard.putNumber("launch time",launchTime);
    }

    // if (ally.get() == Alliance.Blue) {
    //   if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay) {
    //     m_robotDrive.tankDrive(0, 0);
    //   } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time) {
    //     if (firstCall) {
    //       startTime = Timer.getFPGATimestamp();
    //       launchNote(Timer.getFPGATimestamp() - startTime);
    //       firstCall = false;
    //     } else {
    //       launchNote(Timer.getFPGATimestamp() - startTime);
    //     }
    //   } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time) {
    //     driveBackward();
    //   } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time
    //       + step3time) {
    //     turnRight();
    //   } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time
    //       + step3time + step4time) {
    //     driveBackward();
    //   } else {
    //     m_robotDrive.tankDrive(0, 0);
    //   }
    // }
  


  void launchAndExitFromSpeakerB(double autoTimeElapsed) { // red right and blue left
    double step1time = 1.5;
    double step2time = 2.0;
    double step3time = 2.0;
    double step4time = 2.0;

    double startTime = 0;

    if (ally.get() == Alliance.Red) {
      if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay) {
        m_robotDrive.tankDrive(0, 0);
      } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time) {
        if (firstCall) {
          startTime = Timer.getFPGATimestamp();
          launchNote(Timer.getFPGATimestamp() - startTime);
          firstCall = false;
        } else {
          launchNote(Timer.getFPGATimestamp() - startTime);
        }
      } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time) {
        driveBackward();
      } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time
          + step3time) {
        turnLeft();
      } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time
          + step3time + step4time) {
        driveBackward();
      } else {
        m_robotDrive.tankDrive(0, 0);
      }
    }

    if (ally.get() == Alliance.Blue) {
      if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay) {
        m_robotDrive.tankDrive(0, 0);
      } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time) {
        if (firstCall) {
          startTime = Timer.getFPGATimestamp();
          launchNote(Timer.getFPGATimestamp() - startTime);
          firstCall = false;
        } else {
          launchNote(Timer.getFPGATimestamp() - startTime);
        }
      } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time) {
        driveBackward();
      } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time
          + step3time) {
        turnRight();
      } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time
          + step3time + step4time) {
        driveBackward();
      } else {
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
        exitFromSpeakerA(autoTimeElapsed);
      }
    }
  }

  void exitFromSpeakerCenter(double autoTimeElapsed) {

    double step1time = 1000;
    double step2time = 1000;
    double step3time = 1000;
    double step4time = 1000;
    double step5time = 1000;

    if (autoTimeElapsed <= autoStartDelay && autoTimeElapsed > autoStartDelay + autoLaunchDelay) {
      m_robotDrive.tankDrive(0, 0);
    } else {
      Optional<Alliance> ally = DriverStation.getAlliance();
      if (ally.get() == Alliance.Red) {
        if (autoTimeElapsed <= autoStartDelay + step1time) {
          driveBackward();
        } else if (autoTimeElapsed <= autoStartDelay + step1time + step2time) {
          turnRight();
        } else if (autoTimeElapsed <= autoStartDelay + step1time + step2time + step3time) {
          driveBackward();
        } else if (autoTimeElapsed <= autoStartDelay + step1time + step2time + step3time
            + step4time) {
          turnLeft();
        } else if (autoTimeElapsed <= autoStartDelay + step1time + step2time + step3time + step4time
            + step5time) {
          driveBackward();
        } else {
          m_robotDrive.tankDrive(0, 0);
        }
      }
      if (ally.get() == Alliance.Blue) {
        if (autoTimeElapsed <= autoStartDelay + step1time) {
          driveBackward();
        } else if (autoTimeElapsed <= autoStartDelay + step1time + step2time) {
          turnLeft();
        } else if (autoTimeElapsed <= autoStartDelay + step1time + step2time + step3time) {
          driveBackward();
        } else if (autoTimeElapsed <= autoStartDelay + step1time + step2time + step3time
            + step4time) {
          turnRight();
        } else if (autoTimeElapsed <= autoStartDelay + step1time + step2time + step3time + step4time
            + step5time) {
          driveBackward();
        } else {
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
        launchAndExitFromSpeakerA(autoTimeElapsed);
      }
    }
  }

  void launchAndExitFromSpeakerCenter(double autoTimeElapsed) {

    double step1time = 1250;
    double step2time = 4000;
    double step3time = 0;
    double step4time = 0;
    double step5time = 0;
    double step6time = 0;

    double startTime = 0;
    if (autoTimeElapsed <= autoStartDelay + step1time && autoTimeElapsed > autoStartDelay + autoLaunchDelay) {
      m_robotDrive.tankDrive(0, 0);
    } else {
      if (ally.get() == Alliance.Red) {
        if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + autoLaunchDelay
            && autoTimeElapsed > autoStartDelay + autoLaunchDelay + step1time) {
          m_robotDrive.tankDrive(0, 0);
        } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time) {
          if (firstCall) {
            startTime = Timer.getFPGATimestamp();
            launchNote(Timer.getFPGATimestamp() - startTime);
            firstCall = false;
          } else {
            launchNote(Timer.getFPGATimestamp() - startTime);
          }

        } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time
            && autoTimeElapsed > autoStartDelay + step1time + step2time) {
          driveBackward();
          m_launchWheel.set(0);
          m_feedWheel.set(0);
        } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time
            + step3time) {
          turnRight();
        } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time
            + step3time + step4time) {
          driveBackward();
        } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time
            + step3time + step4time + step5time) {
          turnLeft();
        } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time
            + step3time + step4time + step5time + step6time) {
          driveBackward();
        } else {
          m_robotDrive.tankDrive(0, 0);
        }

      }

      if (ally.get() == Alliance.Blue) {
        if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay && autoTimeElapsed > autoStartDelay + autoLaunchDelay) {
          m_robotDrive.tankDrive(0, 0);
        } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time
            && autoTimeElapsed > autoStartDelay + autoLaunchDelay + step1time) {
          if (firstCall) {
            startTime = Timer.getFPGATimestamp();
            launchNote(Timer.getFPGATimestamp() - startTime);
            firstCall = false;
          } else {
            launchNote(Timer.getFPGATimestamp() - startTime);
          }

        } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time) {
          driveBackward();
        } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time
            + step3time) {
          turnLeft();
        } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time
            + step3time + step4time) {
          driveBackward();
        } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time
            + step3time + step4time + step5time) {
          turnRight();
        } else if (autoTimeElapsed <= autoStartDelay + autoLaunchDelay + step1time + step2time
            + step3time + step4time + step5time + step6time) {
          driveBackward();
        } else {
          m_robotDrive.tankDrive(0, 0);
        }

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

  /*^^^^^^^^^^^^^^*/
  /*auto functions*/

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    firstCall = true;

    autoStartDelay = SmartDashboard.getNumber("Auto Start Delay", 0);
    autoLaunchDelay = SmartDashboard.getNumber("Auto Launch Delay", 0);

    SmartDashboard.putNumber("DBG pos", 0);

    m_motorcontroller1.setIdleMode(IdleMode.kBrake);
    m_motorcontroller2.setIdleMode(IdleMode.kBrake);
    m_motorcontroller3.setIdleMode(IdleMode.kBrake);
    m_motorcontroller4.setIdleMode(IdleMode.kBrake);

    AUTO_LAUNCH_DELAY_S = 0.0;
    AUTO_DRIVE_DELAY_S = 3.0;

    AUTO_DRIVE_TIME_S = 2.0;
    AUTO_DRIVE_SPEED = -0.5;
    AUTO_LAUNCHER_SPEED = 1;
    startTime = 0.0;

    ally = DriverStation.getAlliance();

    autonomousStartTime = Timer.getFPGATimestamp();

    switch (m_autoSelected) {
      case kNothingAuto:
        m_robotDrive.arcadeDrive(0, 0);
        break;

      case kExitFromLeftOrRight:
        exitFromLeftOrRight(0);
        break;
      
      case kLaunch:
        launch(0);
        break;

      case kExitFromCenter:
        exitFromCenter(0);
        break;

      case kExitFromAmp:
        exitFromAmp(0);
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
    double autoTimeElapsed = Timer.getFPGATimestamp() - autonomousStartTime;
    SmartDashboard.putNumber("autoTimeElapsed", autoTimeElapsed);

    switch (m_autoSelected) {
      case kNothingAuto:
        m_robotDrive.arcadeDrive(0, 0);
        break;

      case kExitFromLeftOrRight:
        exitFromLeftOrRight(autoTimeElapsed);
        break;

      case kLaunch:
        launch(0);
        break;

      case kExitFromCenter:
        exitFromCenter(autoTimeElapsed);
        break;

      case kExitFromAmp:
        exitFromAmp(autoTimeElapsed);
        break;

      case kExitFromSpeakerLeft:
        exitFromSpeakerLeft(autoTimeElapsed);
        break;

      case kExitFromSpeakerCenter:
        exitFromSpeakerCenter(autoTimeElapsed);
        break;

      case kExitFromSpeakerRight:
        exitFromSpeakerRight(autoTimeElapsed);
        break;

      case kLaunchAndExitFromSpeakerLeft:
        launchAndExitFromSpeakerLeft(autoTimeElapsed);
        break;

      case kLaunchAndExitFromSpeakerCenter:
        launchAndExitFromSpeakerCenter(autoTimeElapsed);
        break;

      case kLaunchAndExitFromSpeakerRight:
        launchAndExitFromSpeakerRight(autoTimeElapsed);
        break;

      default:
        m_robotDrive.arcadeDrive(0, autoTimeElapsed);
        break;
    }
  }

  @Override
  public void teleopInit() {
    m_motorcontroller1.setIdleMode(IdleMode.kBrake);
    m_motorcontroller2.setIdleMode(IdleMode.kBrake);
    m_motorcontroller3.setIdleMode(IdleMode.kBrake);
    m_motorcontroller4.setIdleMode(IdleMode.kBrake);
  }

  @Override
  public void teleopPeriodic() {

      // Fill the buffer with a rainbow 
      // rainbow();
      // Set the LEDs
      m_led.setData(m_ledBuffer);

    m_robotDrive.arcadeDrive(-drive_controller.getRawAxis(drivetrainSpeedAxis) * motorSpeedLimit,
        -drive_controller.getRawAxis(drivetrainRotateAxis) * motorRotateSpeedLimit);

    if (opperator_controller.getRawButton(intakeFunctionButton) && noteDetectionLimitSwich.get()) {
      m_launchWheel.set(-LAUNCHER_IN_SPEED);
      m_feedWheel.set(-FEEDER_IN_SPEED);
    } else {
      m_launchWheel.set(0);
      m_feedWheel.set(0);
    }

    if (opperator_controller.getRawButton(ampFunctionButton)) {
      m_feedWheel.set(FEEDER_AMP_SPEED);
      m_launchWheel.set(LAUNCHER_AMP_SPEED);
    } else if (opperator_controller.getRawButtonReleased(ampFunctionButton)) {
      m_feedWheel.set(0);
      m_launchWheel.set(0);
    }

    Boolean launchButtonPos = opperator_controller.getRawButton(1);
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

    updateClimber(Logic.NEW);
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
    // SetLEDsAll(150, 0, 150);
    //     delay     red green blue chase         red green blue background
    chaseLEDs(5, 0, 255, 0, 0, 100, 100, 2, 12);
  }

}
