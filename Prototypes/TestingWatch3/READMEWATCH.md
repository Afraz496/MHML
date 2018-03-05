# Continuous Heart Rate Monitoring with Android Wear

This guide illustrates all of the required dependencies and usage for the Continuous Heart Rate Monitoring Component for StudyBuddy.

Before you start this guide you should have an Android Wear watch setup with your Android Phone. 

## Installing the APK from Android Studio

After you have connected your Android phone (minimum version: 24) navigate to **Settings > About Phone > Scroll to the bottom > Build Number > (tap Build Number 7 times)** It will then say "You are now a developer!" If you go back to Settings and scroll all the way down (or in Additional Settings in some phones) you will see a new option called **Developer Options**. Scroll down and enable an option called **enable USB Debug** (NB: It could be USB Debugging). This will allow APKs to be installed on an Android Phone (and this is vital for receiving the Heart Rate on a phone).

(Assuming that your android wear is a moto 360) navigate to the **Settings > About > Build Number > (tap 7 times)**. It should say "You are now a developer!" Navigate back to the Settings section and underneath About you should see an option called **Developer Options**. In this view you need to **enable ADB Debugging** and **enable Debug over Bluetooth**.

By now you should be able to install the StudyBuddy application on your phone (but not on the watch). Before doing everything listed below make sure you have the [Android Wear](https://play.google.com/store/apps/details?id=com.google.android.wearable.app&hl=en_GB) application installed on your phone. Then use the Android Wear application to setup a connection with your smartwatch (we used the Moto 360 for testing purposes). After setting up the watch **navigate to the Android Wear app** on your phone. Then select the **Settings** on the top right corner. Scroll down and you should see an option called **Debugging Over Bluetooth**. Select**enable** and you should see:

`Host: Disconnected`
`Target: Connected` 

At this point, there is only one dependency that needs to be installed on your laptop to proceed with the app installation. There is a [comprehensive guide](https://www.xda-developers.com/install-adb-windows-macos-linux/) which helps a developer set up ADB Debugging (any platform). By this point you should have ADB Debugging enabled in your terminal.

Open **Terminal** (ADB might only work in your platform-tools folder, type adb and if the terminal prints output then the installation was successful) and type the following commands:

`$ adb forward tcp:4444 localabstract:/adb-hub`
`$ adb connect 127.0.0.1:4444`

On Android Wear it should now say:

`Host: Connected`
`Target: Connected` 

Now your watch is ready for APK installation. Navigate to Android Studio and tap on the Play Button. It will ask you to choose a module. Choose Mobile for the APK on the phone and wear for the APK on the wear device. The connected devices list will be updated automatically.
