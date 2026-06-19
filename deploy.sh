#!/bin/bash

# Creation du repertoire out
mkdir -p bin

# Chercher dans src tous les fichier .java et les ecrires dans sources.txt
find src -name "*.java" > sources.txt

# Compiler tout ce qui est ecrit dans sources.txt
javac -cp "lib/*" -d bin @sources.txt

# Supprimer sources.txt
rm sources.txt

# Crée le fichier .jar à partir du contenu de bin/
jar cf framework.jar -C bin .

cp framework.jar /opt/Tomcat/webapps/testFramework/WEB-INF/lib/
cp framework.jar /home/chiu/Documents/ITU/L2/S4/"Web Dynamique"/Exo/AppTestFrameworkSpring/lib/