java ^
-Xms1024m -Xmx1024m ^
-cp "%~dp0\src;%~dp0\bin;%~dp0\external_jars\*" ^
com.google.gwt.dev.GWTCompiler ^
-out "%~dp0\www" %* com.objetdirect.gwt.umlapi.UMLDrawer
cd %~dp0
xcopy www\com.objetdirect.gwt.umlapi.UMLDrawer latest_build /m /s /e /v /y
pause