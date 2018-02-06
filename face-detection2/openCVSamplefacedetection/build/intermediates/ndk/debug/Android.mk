LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := detection_based_tracker
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	/home/pufik/mhml/face-detection2/openCVSamplefacedetection/src/main/jni/DetectionBasedTracker_jni.cpp \
	/home/pufik/mhml/face-detection2/openCVSamplefacedetection/src/main/jni/Application.mk \
	/home/pufik/mhml/face-detection2/openCVSamplefacedetection/src/main/jni/Android.mk \

LOCAL_C_INCLUDES += /home/pufik/mhml/face-detection2/openCVSamplefacedetection/src/main/jni
LOCAL_C_INCLUDES += /home/pufik/mhml/face-detection2/openCVSamplefacedetection/src/debug/jni

include $(BUILD_SHARED_LIBRARY)
