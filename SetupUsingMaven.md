## Prerequisite ##
We assume you use Eclipse and you have install the m2eclipse plugin.

<br />
<br />
## Create a new maven project : ##
> - When the wizard ask you to chose an archetype, use gwt-maven-plugin from org.codehaus.mojo

![http://gwtuml.googlecode.com/svn-history/r321/wiki/images/newMavenProjet-gwtpluginArtifact.png](http://gwtuml.googlecode.com/svn-history/r321/wiki/images/newMavenProjet-gwtpluginArtifact.png)

> - On the next sreen choose your artifactId, let's say DiagramProject on this example.

<br />
<br />
## Change the location of the war directory : ##
> - Go on your project properties
> - Go to Google -> Web Application
> - Replace the value of the WAR directory by "src/main/webapp"

![http://gwtuml.googlecode.com/svn-history/r321/wiki/images/mavenProjectChangeWarDirectory.png](http://gwtuml.googlecode.com/svn-history/r321/wiki/images/mavenProjectChangeWarDirectory.png)

<br />
<br />
## Edit the pom.xml : ##
> - Change the gwt.version to 2.0.3.
> > This is the version currently supported, but higher version should work fine.


> - Add the repository :
```
    <repositories>
        <repository>
           <id>gwt-uml</id>
           <url>http://gwtuml.googlecode.com/svn-history/r319/maven/repository/</url>
        </repository>
    </repositories>
```

> - Add the dependency :
```
    <dependency>
	<groupId>com.objetdirect</groupId>
	<artifactId>gwt-umlapi</artifactId>
	<version>1.0</version>
    </dependency>
```

See [this file](http://gwtuml.googlecode.com/svn-history/r321/wiki/resources/pom.xml) for the full pom.xml.

<br />
<br />
## Edit your Application.gwt.xml file ##

- Add the modules used in the project :
```
   <inherits name='com.google.gwt.widgetideas.WidgetIdeas' />   
   <inherits name='com.google.gwt.libideas.LibIdeas' />
   <inherits name='com.google.gwt.widgetideas.GWTCanvas' />
   <inherits name="com.google.gwt.widgetideas.GlassPanel" />
   <inherits name='com.objetdirect.tatami.Tatami' />
   <inherits name='com.objetdirect.gwt.umlapi.GWTUMLAPI' />

   <!-- Add gwt-log support -->
   <inherits name="com.allen_sauer.gwt.log.gwt-log-DEBUG" />
   <set-property name="log_DivLogger" value="DISABLED" />
```


<br />
<br />
## Edit the Application.java file ##
> - Replace the code in the onModuleLoad method by :
```
public void onModuleLoad()
  {
     UMLCanvas umlCanvas = new UMLCanvas(DiagramType.CLASS);
     final Drawer d = new Drawer(umlCanvas);
     RootLayoutPanel.get().add(d);
    
     DeferredCommand.addCommand(new Command() {
			public void execute() {
				d.onResize();
			}
	});
  }
```


<br />
<br />
## Change the doctype of Application.html ##
- Open the file  src\main\resources\com\objetdirect\gwt\DiagramProject\public\Application.html

> - Replace <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> by <!doctype html>

This will tell the brower to render the page using hmtl 5 (if supported) and not the old quirks mode.


<br />
<br />
## Run the dev mode using maven ##

> - On your favorite shell, run the command : mvn gwt:run

<br />
<br />
## Start playing with the diagram ! ##
> - The drawer panel automatically resize depending on its parent container.

> - Use UMLCanvas.getArtifactById() to get all artifacts currently displayed in the canvas ordered by their ids.