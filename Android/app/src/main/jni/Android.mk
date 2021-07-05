LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

#opencv library
#오류나면 OPENCVROOT 경로를 프로젝트의 opencv 경로로 바꿔주세여~
OPENCVROOT := ${LOCAL_PATH}\..\..\..\..\opencv
OPENCV_CAMERA_MODULES:=on
OPENCV_INSTALL_MODULES:=on
OPENCV_LIB_TYPE:=SHARED
include ${OPENCVROOT}\native\jni\OpenCV.mk


LOCAL_MODULE    := native-lib
LOCAL_SRC_FILES := main.cpp
LOCAL_LDLIBS += -llog -landroid

include $(BUILD_SHARED_LIBRARY)