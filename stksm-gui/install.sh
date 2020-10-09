#!/bin/sh

LCNC_DIR="../../linuxcnc"

set -e

mvn clean install

mkdir -p "$LCNC_DIR/java"
cp target/stksm-gui-*.jar "$LCNC_DIR/java/stksm-friedl-gui.jar"
cp -r target/lib "$LCNC_DIR/java"

mkdir -p "$LCNC_DIR/bin"
cp script/launch.sh "$LCNC_DIR/bin/stksm-friedl-gui"
chmod ugo+x "$LCNC_DIR/bin/stksm-friedl-gui"

