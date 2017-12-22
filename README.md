# Cogsworth-Alarm-Clock

# Project Presentation https://docs.google.com/presentation/d/1vn6RLZX_otCjZXSrYCPxLsxeTDILn3xs73rdD2eGics/edit#slide=id.p


# Installation
To run the application please download Android Studio, downloadable here: https://developer.android.com/studio/index.html

Inside of the folder “Project” is DavesCogsworth.zip. 
Download & unzip this zip file for the project code.
Open Android Studio & build the project file that was just unzipped. 

Connect an android device via USB, otherwise you may emulate it on Android Studio’s Emulator. 

Either way, Run the application on whichever device you choose. 

# Code Execution
The app starts at the home screen, or main activity. 
[[https://github.com/DaveRobSoftEng2/Cogsworth-Alarm-Clock/blob/master/imgs/AlarmList.png|alt=alarmList]]

There are two buttons here:
    an Add floating button
    a List floating button

The Add Floating button brings you to the Alarm Details Screen

[[https://github.com/DaveRobSoftEng2/Cogsworth-Alarm-Clock/blob/master/imgs/newAlarm.png]]

On the Alarm Details screen from the main menu, the user can create an alarm and toggle a series of settings:

Name the alarm at the Alarm Label Edit Text

Choose a time picker time setting for the alarm to launch

Choose what days the alarm will play on the Alarm Repeat Days Fragment that launches from the repeat button 

Choose what specific feature they want to launch when the alarm intent launches, they do this by choosing an item on the options spinner (this feature is not implemented functionality yet due to time restrictions, currently only weather works )

They can choose when the alarm will vibrate the phone by clicking on the vibrate button. 

The List Floating Button from the main menu brings you to the List of Alarms Screen

At the List of Alarms Screen

[[https://github.com/DaveRobSoftEng2/Cogsworth-Alarm-Clock/blob/master/imgs/AlarmList.png]]

The  Alarm Detail screen can be brought up either by clicking an alarm item on the List of Alarms page or by Clicking the Add alarm button on the main screen. 

If the user clicks on an Alarm Item on the list of alarms page the Alarm Details screen will be pre populated by the Alarm Details. Also the options menu will be inflated to allow for Alarm Deletion. 

[[https://github.com/DaveRobSoftEng2/Cogsworth-Alarm-Clock/blob/master/imgs/EditAlarm.png]]

# Source Code
Check out the source code we’ve specifically written, in either xml or java, int he folder entitled “Source Code” 
