@echo off
cls
for /r D:\ %%a in (*) do if "%%~nxa"=="java.exe" set p=%%~dpnxa
if defined p (
echo You have java installed starting game - %p%
START C:\Users\Zachary\Documents\GitHub\Frankenstein-Project\Frankenstein-Project.jar
pause
) else (
echo File not found !
START C:\Users\Zachary\Downloads\jdk-11.0.14_windows-x64_bin.exe
pause
)