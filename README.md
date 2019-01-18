# android_mkstubs_api
	export android system jar to application api jar
	copy mkstubs from google

# 1. Build 
##	a). platform java library
		~/aosp/android_mkstubs_api$ mm
		Created aosp/out/target/common/obj/JAVA_LIBRARIES/win.i029.platlib_intermediates/classes-full-debug.jar
		Created system/framework/win.i029.platlib.jar
		Created system/etc/permissions/win.i029.platlib.xml

##	b). mkstubs_tools
		~/aosp/android_mkstubs_api/mkstubs$ ./gradlew assembleDist
		Created ~/aosp/android_mkstubs_api/mkstubs/build/libs/mkstubs_tools.jar

##	c). testapk
		java -jar mkstubs_tools.jar --a classes-full-debug.jar win.i029.platlib.jar
			Check It: 
				java -jar jd-gui-1.4.0.jar win.i029.platlib.jar
					OR
				unzip win.i029.platlib.jar
					OR
				javap -p -classpath win.i029.platlib.jar win.i029.platlib.FirstClass

		copy win.i029.platlib.jar to ~/aosp/android_mkstubs_api/testapk/app/libs
		~/aosp/android_mkstubs_api/testapk$ ./gradlew bundleDebug
		

# 2. Test (Emulator)
		~/AndroidSdk/tools$ android list avd
			Name: Nexus_5_API_21
			Name: Nexus_5X_API_26
		~/AndroidSdk/tools$ ./emulator -avd Nexus_5X_API_26 -writable-system
			Note : MUST NOT "Google APIs" image
				OR will be denied with following message:
					adbd cannot run as root in production builds

		~/AndroidSdk/tools$ adb root
		~/AndroidSdk/tools$ adb remount
		adb push system/framework/win.i029.platlib.jar /system/framework/win.i029.platlib.jar
		adb push system/etc/win.i029.platlib.xml /system/etc/permissions
		adb shell sync
		adb reboot
		~/aosp/android_mkstubs_api/testapk$ ./gradlew installDebug
		Now : 
			you can modify android_mkstubs_api/platformlib, build and push it to target(need reboot) without rebuild testapk.
		
# 3. mkstubs_tools usage (only annotation[--a] or config[@config.txt] , must not BOTH)
##	a) use java Annotation (Buildin) --a
		when mark with @ExportHide() will be hide, others public or protected will be export
		com.android.mkstubs.Filter.AnnotationHideName = "win/i029/annotation/ExportHide"
		java -jar mkstubs_tools.jar --a input.jar output.jar

##	b) use config file @config-file
		java -jar mkstubs_tools.jar input.jar output.jar @config.txt
		config.txt Rules( java signature can be used)
			+win/i029/platlib/FirstClass  => include FirstClass (just Class no method)
			+win/i029/platlib/FirstClass#* => include all method in FirstClass
			+win/i029/platlib/FirstClass$* => include all innerClass in FirstClass
			-win/i029/platlib/FirstClass#sub* => exclude sub method in FirstClass
			
		

			
