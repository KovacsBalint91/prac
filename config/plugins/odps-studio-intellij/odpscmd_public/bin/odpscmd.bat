@echo off
set argC=0
for %%x in (%*) do Set /A argC+=1

SETLOCAL ENABLEDELAYEDEXPANSION
@set basedir="%~dp0"
pushd %basedir%

@set mainclass=com.aliyun.openservices.odps.console.ODPSConsole
@set libpath=%cd%\..\lib\
@set mrlibpath=%cd%\..\lib\mapreduce-api.jar
@set confpath=%cd%\..\conf\
@set classpath=.;!confpath!;!mrlibpath!;!libpath!\*

@set javaBoot=java
if exist "%INTELLIJ_BOOT_JAVA%" (
	@set javaBoot="%INTELLIJ_BOOT_JAVA%"
)

@set java9opt=
%javaBoot% -version 2>&1 | findstr /c:"build 9" > nul
if %errorlevel% == 0 @set java9opt=--add-modules=java.xml.bind

rem set %javaBoot% env
popd
%javaBoot% -Xms64m -Xmx512m %java9opt% -classpath "!classpath!" %mainclass% %*

if errorlevel 1 (
  if %argC% == 0 (
   PAUSE
  )
)

ENDLOCAL
