# Download and Update Issues for Linux Operating System #
This page explains how to re-configure the build path in the project either after downloading or updating the project.

## Configuring the Build Path ##

If you encounter problems running the Geocam project after either updating or downloading, the first thing to do is to try to re-configure the build path. To do this, first right click on the Geocam project under project explorer. Scroll down on the menu that pops up until you reach "build path". Under this menu, click "configure build path".

Next, a small screen should pop up with four headings that you can toggle between: source, projects, libraries, order and export. First, click on the libraries heading. You want to first make sure that you have all of the correct jar files and libraries under this tab. Here is what you should see:
  * gluegen-rt.jar
  * jogl.jar
  * jreality.jar
  * lwgjl.jar
  * xerceslmpl.jar
  * xercesSamples.jar
  * xml-apis.jar
  * JRESystemLibrary
  * JUnit4

If you click the arrow next to each object, you should see a label that says "native library location". You will need to change several of these library locations in order for the project to run correctly. The objects that need to be changed are :
  * gluegen-rt.jar
  * jogl.jar
  * jreality.jar
  * lwgjl.jar

For each item, click on the "native library location", select "edit", and then "workspace". Next, choose "Geocam", "lwgil", "native", and then "linux".

This is everything that needs to be configured in the build path. The next section briefly covers another option, which is cleaning the project.

## Cleaning the Project ##

After configuring the build path, one more thing that can be done if you are still having difficulties with the project is to clean it. To do this, select "project" from the main menu and choose "clean". This should bring up a small menu. Choose "clean projects selected below" and then select the Geocam project from the project list. After cleaning the project, you should then attempt to run it and see if the problems are resolved.