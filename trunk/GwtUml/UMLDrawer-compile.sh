APPDIR=`dirname $0`;
java \
-Xms1024m -Xmx1024m \
-cp "$APPDIR/src:$APPDIR/bin:$APPDIR/gwt-linux/*:$APPDIR/external_jars/*" \
com.google.gwt.dev.GWTCompiler \
-out "$APPDIR/www" "$@" \
com.objetdirect.gwt.umlapi.UMLDrawer
cd $APPDIR
svn rm --force latest_build/*.cache.*
svn rm --force latest_build/*.rpc
rm -f latest_build/*.cache.*
rm -f latest_build/*.rpc
cp -aufvr www/com.objetdirect.gwt.umlapi.UMLDrawer/* latest_build/
svn add --force latest_build/*
