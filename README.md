[![AUR](https://img.shields.io/aur/license/yaourt.svg?maxAge=2592000)](https://raw.githubusercontent.com/Abhi347/NoobCameraFlash/master/LICENSE) 
[![](https://jitpack.io/v/Abhi347/NoobCameraFlash.svg)](https://jitpack.io/#Abhi347/NoobCameraFlash)

# NoobCameraFlash
An Android library to access Camera Flash in all the versions of Android. It takes care of the permissions and can also work in different flavor of Android OS.

## Features
 * No need to code for extra permissions
 * Works on all versions of Android (Minimum API level 15)

## Planned feature
 * More Customization support
 * Support for devices lower than Android 4.0

## Setup
NoobCameraFlash is hosted at jitpack.io. The instructions are as follows -   
Step 1. Add the JitPack repository to your build file. Add it in your root build.gradle at the end of repositories:

    allprojects {
		    repositories {
    			...
		    	maven { url "https://jitpack.io" }
    		}
    }

Step 2. Add the dependency

    dependencies {
	        compile 'com.github.Abhi347:NoobCameraFlash:0.0.1'
	  }

## Usage
Initialize the `NoobCameraManager` singleton.

    NoobCameraManager.getInstance().init(this);

You can optionally set the Log Level for debug logging. Logging uses [LumberJack](https://github.com/Abhi347/LumberJack) library. The default LogLevel is `LogLevel.None`

    NoobCameraManager.getInstance().init(this, LogLevel.Verbose);

After that you just need to call the singleton to turn on or off the camera flash.

    NoobCameraManager.getInstance().turnOnFlash();
    NoobCameraManager.getInstance().turnOffFlash();
    
You can take care of the runtime permission to access Camera yourself or can allow the library to do it for you

    NoobCameraManager.getInstance().takePermissions();
    
It's easy to toggle Flash too

    if(NoobCameraManager.getInstance().isFlashOn()){
        NoobCameraManager.getInstance().turnOffFlash();
    }else{
        NoobCameraManager.getInstance().turnOnFlash();
    }

## Contributions
Feel free to report bugs, feedback or even suggest new features. I'd love to make it a great library.

## Donate
[Paypal](https://paypal.me/Abhi347/5)

## Warning
NoobCameraFlash is not a production ready library (We haven't reached 1.0.0 yet) and thus you should not use it in a production ready code. Please read the license term carefully before including it in your projects.

