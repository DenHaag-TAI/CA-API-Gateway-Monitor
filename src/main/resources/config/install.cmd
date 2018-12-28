@REM
@REM Copyright (C) 2016-2020 by Gemeente Den Haag.
@REM Written by Harald Blauwtand <kim.meulenbroek@denhaag.nl>
@REM All Rights Reserved
@REM
@REM This library is free software; you can redistribute it and/or
@REM modify it under the terms of the GNU Lesser General Public
@REM License as published by the Free Software Foundation; either
@REM version 3 of the License, or (at your option) any later version.
@REM
@REM This library is distributed in the hope that it will be useful,
@REM but WITHOUT ANY WARRANTY; without even the implied warranty of
@REM MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
@REM Lesser General Public License for more details.
@REM
@REM You should have received a copy of the GNU Lesser General Public
@REM License along with this library; if not, write to the Free Software
@REM Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
@REM

@echo off
rem 
rem Licensed to the Apache Software Foundation (ASF) under one or more
rem contributor license agreements.  See the NOTICE file distributed with
rem this work for additional information regarding copyright ownership.
rem The ASF licenses this file to You under the Apache License, Version 2.0
rem (the "License"); you may not use this file except in compliance with
rem the License.  You may obtain a copy of the License at
rem
rem     http://www.apache.org/licenses/LICENSE-2.0
rem
rem Unless required by applicable law or agreed to in writing, software
rem distributed under the License is distributed on an "AS IS" BASIS,
rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
rem See the License for the specific language governing permissions and
rem limitations under the License.

rem Batch script for defining the ProcrunService (JVM and Java versions)

rem Copy this file and ProcrunService.jar into the same directory as prunsrv (or adjust the paths below)

setlocal
rem The service names (make sure they does not clash with an existing service)
set SERVICE_NAME=ca-monitor
set SERVICE_DISPLAY_NAME="CA Monitor"
set SERVICE_DESCRIPTION="This service checks whether the CA API gateway contains new or changed webservices."

rem my location; directory containing this batchfile
set BIN_PATH=%~dp0

rem location of Prunsrv
set PATH_PRUNSRV=%BIN_PATH%
set BASE_DIR=%PATH_PRUNSRV%\..
set LOG_DIR=%BASE_DIR%\logs
rem location of jarfile
set PATH_JAR=%BIN_PATH%

rem Allow prunsrv to be overridden
if "%PRUNSRV%" == "" set PRUNSRV=%PATH_PRUNSRV%prunsrv
rem Install the 2 services
rem TODO start en stop class aangeven
echo Installing %SERVICE_NAME%
%PRUNSRV% //IS//%SERVICE_NAME% --Jvm=auto --StdOutput auto --StdError auto ^
--Classpath=%PATH_JAR%ca-monitor.jar ^
--StartMode=jvm --StartClass=nl.denhaag.rest.runner.MonitorTask --StartMethod=start ^
 --StopMode=jvm  --StopClass=nl.denhaag.rest.runner.MonitorTask  --StopMethod=stop ^
 --Startup=auto --DisplayName=%SERVICE_DISPLAY_NAME% --Description=%SERVICE_DESCRIPTION% ^
 ++StartParams=%BASE_DIR% ++StartParams=%LOG_DIR% --LogPath=%LOG_DIR%



echo Installation of %SERVICE_NAME% is complete
sc start %SERVICE_NAME% 
rem %PRUNSRV% //RS//%SERVICE_NAME% 
echo Finished
pause