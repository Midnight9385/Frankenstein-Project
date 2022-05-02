@echo off
cls
for /r D:\ %%a in (*) do if "%%~nxa"=="java.exe" set p=%%~dpnxa
if defined p (
echo You have java installed starting game - %p%
START C:\Users\Zachary\Documents\GitHub\Frankenstein-Project\Frankenstein-Project.jar
pause
) else (
echo You don't have java installed starting install, after rerun
START C:\Users\Zachary\Documents\GitHub\Frankenstein-Project\jdk-11.0.14_windows-x64_bin.exe
pause
)
