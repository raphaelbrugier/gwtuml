APPDIR=`dirname $0`;
java \
-Xms1024m -Xmx1024m \
-cp "$APPDIR/src:$APPDIR/bin:$APPDIR/gwt-linux/*:$APPDIR/external_jars/*" \
com.google.gwt.dev.GWTCompiler \
-out "$APPDIR/www" "$@" \
com.objetdirect.gwt.umlapi.UMLDrawer
cd $APPDIR
svn rm --force latest_build/*
mv latest_build/.svn /tmp
rm -fr latest_build/*
cp -fr www/com.objetdirect.gwt.umlapi.UMLDrawer/* latest_build
mv /tmp/.svn latest_build
svn add --force latest_build/*
