#!/bin/bash
echo Running instrumentation on $1
adb shell am instrument -w -e class com.robodreamz.density.test.$1 com.robodreamz.density.test/android.test.InstrumentationTestRunner
