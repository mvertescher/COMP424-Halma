#!/bin/bash
PROVIDEDCODE='./jar/projectsrc.jar'
#javac -cp $PROVIDEDCODE:./HalmaProject/src/ ./halma/src/s260469756/s260469756Player.java 
java -cp $PROVIDEDCODE:./HalmaProject/bin/ boardgame.Client s260469756.s260469756Player
