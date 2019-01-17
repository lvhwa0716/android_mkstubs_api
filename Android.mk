

LOCAL_PATH := $(call my-dir)

PLATFORM_JAVA_LIB_ROOT := platformlib

# the library 
# ============================================================
include $(CLEAR_VARS)
LOCAL_SRC_FILES := \
            $(call all-java-files-under,$(PLATFORM_JAVA_LIB_ROOT))

LOCAL_MODULE_TAGS := optional

LOCAL_MODULE:= win.i029.platlib
LOCAL_JACK_ENABLED := disabled
include $(BUILD_JAVA_LIBRARY)


# the permission 
#	<application
#		... ...
#        >
#        <uses-library android:name="win.i029.platlib" android:required="true" />
#        ... ...
#    </application>
# ============================================================
include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := optional
LOCAL_MODULE := win.i029.platlib.xml
LOCAL_MODULE_CLASS := ETC
LOCAL_MODULE_PATH := $(TARGET_OUT_ETC)/permissions
LOCAL_SRC_FILES := win.i029.platlib.xml
include $(BUILD_PREBUILT)


# the documentation
# ============================================================
include $(CLEAR_VARS)

LOCAL_SRC_FILES := $(call all-java-files-under,$(PLATFORM_JAVA_LIB_ROOT)) $(call all-subdir-html-files-under,$(PLATFORM_JAVA_LIB_ROOT))

LOCAL_MODULE:= win.i029.platlib_doc
LOCAL_DROIDDOC_OPTIONS :=
LOCAL_MODULE_CLASS := JAVA_LIBRARIES
LOCAL_DROIDDOC_USE_STANDARD_DOCLET := true

include $(BUILD_DROIDDOC)

# The JNI component
# ============================================================

include $(CLEAR_VARS)

include $(call all-makefiles-under,$(LOCAL_PATH))

