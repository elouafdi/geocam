# Introduction #

For some months we have been trying to find a simple way to export our application from eclipse, such that the application

  1. runs properly (all data and native libraries available)
  1. can be reasonably installed and run by non-programmers.

This page describes our current procedure for exporting the application. It is definitely a work in progress, so problems should be noted and reported!

# Exporting the Application #

We wish to export versions of our application for each of the three major operating systems (Linux, Macintosh, and Windows) and different processors (32 and 64 bit). Probably the easiest way to use the instructions below is to do the export on a machine that has the OS/processor combination you wish to target. This way, you can immediately test the application you created.

First Steps:
  1. Make sure the application you want to export runs under eclipse! In particular, you need to make sure the build path is set up correctly because this tells eclipse what libraries to export. See http://code.google.com/p/geocam/wiki/eclipse_setup for more information about the build path.
  1. Next, within the "Project Explorer" select the Geocam project. Then, click File->Export. This will bring up a window offering to export various things, select "Java -> Runnable Jar" within the tree of options and press "Next".
  1. You should see a window entitled "Runnable JAR File Export". Stepping out of Eclipse for a moment, you should make a folder somewhere on disk to contain the application and its libraries/data. For my purposes, I'll call this folder "export" and place it on the desktop.
  1. Within the Geocam Project, you should locate the "Data" folder, which contains texture images, triangulations, etc. Copy this folder **exactly** into the folder you created. (If there isn't now a folder called "Data" within "export", we won't be able to run the application from within the "export" folder.)
  1. Returning to Eclipse, locate the "Launch Configuration" pulldown menu, and select the name of the class containing the "main" method you want to launch when the application is run.
  1. Next, select the radio button that begins with "Copy required libraries into a subfolder..."
  1. Within the "export destination" text box, select the path to the folder you created and provide a name for the final jar file. In my case, I used "/home/jthomas/Desktop/export/app.jar"
  1. Click "Finish".

At this point, you're nearly finished. The directory you've created ("Desktop/export", in my example) should contain three things: a jar file, a folder called "Data" full of GEOCAM data, and a folder of libraries.

The remaining task is to add the right native libraries to get everything to run correctly. Open your file-browser of choice, browse to your GEOCAM eclipse project, and open the "lib/jogl" folder. You should see many jars, of the form "jogl-all-natives-OS and architecture here". Copy only the jar applicable to your target OS/architecture pair, and paste it into your app\_lib directory ("/home/jthomas/Desktop/export/app\_lib" in my example).

Next, return the the lib directory in the GEOCAM project. This time, enter the "gluegen" folder. Again, you should see jars of the form "gluegen-rt-natives-OS/Architecture.jar"; copy the appropriate one to your app\_lib directory.

**Aside**: For some reason, eclipse doesn't include these native libraries; perhaps it's because they're wrapped in jars. For a while, I thought I should be able to cover all OS/Arch combinations by just copying all of the "jogl-all-natives-" and "gluegen-rt-natives-" jars to my app\_lib folder. This leads to runtime errors, however; I think JReality is not smart enough to select the appropriate OS-specific library at runtime. The moral seems to be: you need to select exactly the appropriate native libraries for your machine.

At this point, your designated export folder should contain a stand-alone version of our application. Assuming you are following these steps on the same kind of machine you want the application to run on, you can now test the exported code. Open a terminal and browse to the appropriate folder (ex. "/home/jthomas/Desktop/export"). There, type "java -jar app.jar" to launch the application. Double clicking the jar file within your file browser may also work, depending on the settings in your file browser.

Assuming everything worked properly, you're ready to post the folder (zipped and appropriately named, perhaps) to the web for others to download.