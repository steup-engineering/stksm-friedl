#!/bin/bash

REST_URL="http://localhost:8080/hal/json/"

PROC_DIR="$HOME/linuxcnc/ksm/proc"
PARAM_DIR="$HOME/linuxcnc/ksm/param"

mkdir -p "$PROC_DIR" "$PARAM_DIR"

JAVADIR="$LINUXCNC_HOME/java"

cd $JAVADIR
java \
  -Dswing.aatext=true \
  -Dswing.plaf.metal.controlFont=SansSerif-18 \
  -Dswing.plaf.metal.userFont=SansSerif-18 \
  -jar stksm-friedl-gui.jar \
  "$REST_URL" "$PROC_DIR" "$PARAM_DIR"

