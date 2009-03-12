cp -f src/com/objetdirect/gwt/umlapi/UMLDrawer.gwt.xml src/com/objetdirect/gwt/umlapi/UMLDrawer.gwt.xml.back
cp -f src/com/objetdirect/gwt/umlapi/UMLDrawerGeckoOnly.gwt.xml src/com/objetdirect/gwt/umlapi/UMLDrawer.gwt.xml
APPDIR=`dirname $0`;
java \
-Xms2048m -Xmx2048m \
-cp "$APPDIR/src:$APPDIR/bin:$APPDIR/gwt-linux/*:$APPDIR/external_jars/*" \
com.google.gwt.dev.Compiler \
-out "$APPDIR/www" "$@" \
com.objetdirect.gwt.umlapi.UMLDrawer
cd $APPDIR
svn rm --force latest_build/*.cache.*
svn rm --force latest_build/*.rpc
cp -u www/com.objetdirect.gwt.umlapi.UMLDrawer/* latest_build/
cp -ur www/com.objetdirect.gwt.umlapi.UMLDrawer/gwt latest_build/
cp -ur www/com.objetdirect.gwt.umlapi.UMLDrawer/images latest_build/
cp -u www/com.objetdirect.gwt.umlapi.UMLDrawer/dojo/dojo.js latest_build/dojo
cp -u www/com.objetdirect.gwt.umlapi.UMLDrawer/dojox/gfx.js latest_build/dojox
cp -ur www/com.objetdirect.gwt.umlapi.UMLDrawer/dojox/gfx latest_build/dojox
svn add --force latest_build/*
cp src/com/objetdirect/gwt/umlapi/UMLDrawer.gwt.xml.back src/com/objetdirect/gwt/umlapi/UMLDrawer.gwt.xml
