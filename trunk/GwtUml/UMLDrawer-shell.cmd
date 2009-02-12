@java ^
-Xms1024m -Xmx1024m ^
-cp "%~dp0\src;%~dp0\bin;%~dp0\external_jars\*" ^
com.google.gwt.dev.GWTShell ^
-out "%~dp0\www" %* com.objetdirect.gwt.umlapi.UMLDrawer/UMLDrawer.html