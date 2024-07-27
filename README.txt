# 2024_FRC_Robot
Robot code for 2024 FRC game




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
*          
*                                     
*         !!!!!!!!!!UPDATE VERSION HISTORY BEFORE COMMIT!!!!!!!!!!
*    !!!!!!!!!!UPDATE VERSION HISTORY BEFORE COMMIT!!!!!!!!!!
*                  !!!!!!!!!!UPDATE VERSION HISTORY BEFORE COMMIT!!!!!!!!!!
*
*
*/