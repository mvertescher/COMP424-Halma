#!/bin/bash
PROVIDEDCODE='./jar/projectsrc.jar'
#javac -cp $PROVIDEDCODE:./halma/src/ ./halma/src/sXXXXXXXXX/sXXXXXXXXXPlayer.java 
java -cp $PROVIDEDCODE:./halma/src/ boardgame.Client sXXXXXXXXX.sXXXXXXXXXPlayer
