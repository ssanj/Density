#!/bin/bash
mvn clean install -DskipTests=true && adb install -r target/density.apk
