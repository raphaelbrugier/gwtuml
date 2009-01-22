java ^
-Xms1024m -Xmx1024m ^
-cp "%~dp0\src;%~dp0\bin;C:/gwt/gwt-user.jar;C:/gwt/gwt-dev-windows.jar;C:/gwt-tatami/tatami-1.3.jar;C:/gwt-incubator/gwt-incubator.jar;C:/gwt-log/gwt-log-2.5.3.jar" ^
com.google.gwt.dev.GWTCompiler ^
-out "%~dp0\www" %* com.objetdirect.gwt.umlapi.UMLDrawer
c:\svn\bin\svn.exe rm latest_build\*.html
c:\svn\bin\svn.exe rm latest_build\*.rpc
c:\svn\bin\svn.exe rm latest_build\*.png
xcopy www\com.objetdirect.gwt.umlapi.UMLDrawer latest_build /s /d
c:\svn\bin\svn.exe add --force latest_build\*