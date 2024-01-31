


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

// import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;  // This lib is being depreciated
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



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
