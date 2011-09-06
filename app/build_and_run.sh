#!/bin/bash
mvn clean install && adb install -r target/density.apk
