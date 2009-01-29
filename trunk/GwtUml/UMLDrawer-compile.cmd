java ^
-Xms1024m -Xmx1024m ^
-cp "%~dp0\src;%~dp0\bin;C:/gwt/gwt-user.jar;C:/gwt/gwt-dev-windows.jar;C:/gwt-tatami/tatami-1.3.jar;C:/gwt-incubator/gwt-incubator_1-5_Dec_28.jar;C:/gwt-log/gwt-log-2.5.3.jar;C:/gwt-mosaic/gwt-mosaic-0.1.7.jar;C:/gwt-dnd/gwt-dnd-2.5.6.jar" ^
com.google.gwt.dev.GWTCompiler ^
-out "%~dp0\www" %* com.objetdirect.gwt.umlapi.UMLDrawer
cd %~dp0
c:\svn\bin\svn.exe rm --force latest_build\*.cache.*
c:\svn\bin\svn.exe rm --force latest_build\*.gwt.rpc
xcopy www\com.objetdirect.gwt.umlapi.UMLDrawer latest_build /m /s /e /v /y
c:\svn\bin\svn.exe add --force latest_build\*
pause