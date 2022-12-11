// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot { 
  VictorSPX leftDrive = new VictorSPX(3);   
  VictorSPX rightDrive = new VictorSPX(4);
  VictorSP arm = new VictorSP(5);
  private final PWMSparkMax intake1 = new PWMSparkMax(6, MotorType.kBrushless); 
  private final PWMSparkMax intake2 = new PWMSparkMax(7, MotorType.kBrushless); 
  Joystick driverController = new Joystick(0);
  Joystick armController = new Joystick(1); 

  boolean armUp = false; //Arm initialized to up because that's how it would start a match
  boolean burstMode = false;
  double lastBurstTime = 0;
  double autoStart = 0;
  boolean goForAuto = false;


   //how fast/strong arm moves and stays put
   final double armHoldUp = .06; 
   final double armHoldDown = .02; 
   final double armTravel = .35; 
   final double armTimeUp = 1.62;   
   final double armTimeDown = 1.575; 

   double counterclock = 1;   //intake
   double clock = -1;  

  /*private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>(); 

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
   /* m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);*/
 rightDrive.setInverted(false);
 leftDrive.setInverted(false);
    
    

    SmartDashboard.putBoolean("Go For Auto", false);
    //boolean goForAuto = SmartDashboard.getBoolean("Go For Auto", false);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. 
   */
  @Override
  public void autonomousInit() {
   autoStart = Timer.getFPGATimestamp();
    goForAuto = true; 
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    /*switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break; */
    }
  

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double forward = -driverController.getRawAxis(1);
       double turn = -driverController.getRawAxis(4);
       
       double driveLeftPower = (forward - turn);
       double driveRightPower = (forward + turn);
   
      leftDrive.set(driveLeftPower/2.25); //change value on end to change speed
       rightDrive.set(driveRightPower/2.25);

       if(armController.getRawButton(6)){   
        if(armController.getRawButton(6)){   
          intake1.set(clock);   //-1,1
          intake2.set(counterclock); 
        }
        else{ if(armController.getRawButton(4)){   
          intake1.set(counterclock); 
          intake2.set(clock);  
        } 
        else{
          intake1.set(0);
          intake2.set(0); 
        }
    
        //Arm Controls
        if(armUp){
          if(Timer.getFPGATimestamp() - lastBurstTime < armTimeUp){
            arm.set(armTravel);
          }
          else{
            arm.set(armHoldUp);
          }
        }
        else{
          if(Timer.getFPGATimestamp() - lastBurstTime < armTimeDown){
            arm.set(-armTravel);
          }
          else{
            arm.set(-armHoldDown);
          }
        }
      
        if(armController.getRawButtonPressed(5) && !armUp){ 
          lastBurstTime = Timer.getFPGATimestamp();
          armUp = true;
        }
        else if(armController.getRawButtonPressed(3) && armUp){
          lastBurstTime = Timer.getFPGATimestamp();
          armUp = false;}}}
        }
           
        
  
 
   

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    leftDrive.set(0);
    rightDrive.set(0);
    arm.set(0);
    intake1.set(0);
    intake2.set(0);
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}
}

 