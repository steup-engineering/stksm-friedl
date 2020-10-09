#!/bin/sh

if [ -z "$EMC2_HOME" ]; then
  echo 'ERROR: EMC2_HOME not set (rip-environment not called?)'
  exit 1
fi

set -e

mvn clean install

mkdir -p "$EMC2_HOME/java"
cp target/stksm-gui-*.jar "$EMC2_HOME/java/stksm-friedl-gui.jar"
cp -r target/lib "$EMC2_HOME/java"

mkdir -p "$EMC2_HOME/bin"
cp script/launch.sh "$EMC2_HOME/bin/stksm-friedl-gui"
chmod ugo+x "$EMC2_HOME/bin/stksm-friedl-gui"

