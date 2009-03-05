APPDIR=`dirname $0`;
/home/fmounier/installs/jdk1.6.0_12/bin/java \
-Xms1024m -Xmx1024m \
-cp "$APPDIR/src:$APPDIR/bin:$APPDIR/gwt-linux/*:$APPDIR/external_jars/*" \
com.google.gwt.dev.GWTShell \
-out "$APPDIR/www" "$@" \
com.objetdirect.gwt.umlapi.UMLDrawer/UMLDrawer.html
