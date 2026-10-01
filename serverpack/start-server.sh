#!/bin/bash
cd "$(dirname "$0")" || exit 1

DO_RAMDISK=0
if [[ $(grep 'ramDisk:' server-setup-config.yaml | awk 'BEGIN {FS=":"}{print $2}') =~ "true" ]]; then
    SAVE_DIR=$(grep 'level-name' server.properties | awk 'BEGIN {FS="="}{print $2}')
    mv "$SAVE_DIR" "${SAVE_DIR}_backup"
    mkdir "$SAVE_DIR"
    sudo mount -t tmpfs -o size=2G tmpfs "$SAVE_DIR"
    DO_RAMDISK=1
fi

if [ ! -f serverstarter-2.4.0.jar ]; then
    URL="https://github.com/EnigmaticaModpacks/ServerStarter/releases/download/v2.4.0/serverstarter-2.4.0.jar"
    if command -v wget >>/dev/null; then
        echo "DEBUG: (wget) Downloading ${URL}"
        wget -O serverstarter-2.4.0.jar "${URL}"
    elif command -v curl >>/dev/null; then
        echo "DEBUG: (curl) Downloading ${URL}"
        curl -L -o serverstarter-2.4.0.jar "${URL}"
    else
        echo "Neither wget or curl were found on your system. Please install one and try again"
        exit 1
    fi
fi

java -jar serverstarter-2.4.0.jar "$@"

if [[ $DO_RAMDISK -eq 1 ]]; then
    sudo umount "$SAVE_DIR"
    rm -rf "$SAVE_DIR"
    mv "${SAVE_DIR}_backup" "$SAVE_DIR"
fi
