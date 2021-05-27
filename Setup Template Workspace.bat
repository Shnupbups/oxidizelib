@echo off
title Fabric Template Workspace Setup

:choice1
set /p c="Do you want to rename things? [Y/N] "
if /i "%c%" equ "Y" goto :rename
if /i "%c%" equ "N" goto :choice2
echo Invalid choice!
goto :choice1

:rename
title Fabric Template Workspace Setup - Renaming
set /p modid="Enter Mod ID: "
move "%cd%\src\main\java\com\shnupbups\modid" "%cd%\src\main\java\com\shnupbups\%modid%"
move "%cd%\src\main\resources\assets\modid" "%cd%\src\main\resources\assets\%modid%"
move "%cd%\src\main\resources\data\modid" "%cd%\src\main\resources\data\%modid%"
move "%cd%\src\main\resources\modid.mixins.json" "%cd%\src\main\resources\%modid%.mixins.json"
echo Renamed things to %modid%
title Fabric Template Workspace Setup - %modid%

:choice2
set /p c="Do you want to run gradlew? [Y/N] "
if /i "%c%" equ "Y" goto :gradlew
if /i "%c%" equ "N" goto :end
echo Invalid choice!
goto :choice2

:gradlew
set /p a="Enter any arguments you'd like to parse to gradlew. "
title Fabric Template Workspace Setup - Running gradlew %a%
gradlew %a%

:end
echo Done. Don't forget to edit the fabric.mod.json and gradle.properties too!
title Fabric Template Workspace Setup - Done!
pause
exit