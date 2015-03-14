# Introduction #

When you first download a developer's version of our code, you need to explain to eclipse how to compile the code into a working program. In addition to our source code, there are some libraries that you'll need to install.

# What libraries do we use, exactly? #

In the interest of producing a reasonable-sized final executable, we should only include libraries that we actually need to use. Below are the libraries you will need to incorporate into the build path, and a list of what they do.
  * Xerces / XML-APIs : These read our XML formatted data files.
  * jReality : Our main graphics library.
  * lwjgl : The "lightweight java game library" is used to read input from a game pad.
  * gluegen : I have the impression that this lets library allows java code to call into C/C++ code, which is necessary for a graphics library like JReality/JOGL.
  * JOGL : The graphics library that jreality uses for lower-level rendering work.

# Configuring your Eclipse Build Path #

The first thing you'll need to do is make sure the "Package Explorer" window is visible. Then, right click on the GEOCAM project, which will open a large menu, and select "Build Path". Within a sub-menu, you should see "Configure Build Path."

Selecting "Configure Build Path" should bring up a window with several tabs in it. Select the tab labeled "Libraries". Ultimately, you want the following list of libraries in this tab's window.
  * gluegen-rt.jar
  * jogl-all.jar
  * jReality.jar
  * lwjgl.jar
  * xercesImpl.jar
  * xercesSamples.jar
  * xml-apis.jar
  * JRE Systems Library
  * JUnit 4

The procedure for adding each JAR is very simple, just press the "Add JAR" button, then browse to GEOCAM/lib and select the appropriately labeled .jar file. You may have to browse subfolders of of the lib directory to find the .jar's for lwjgl and jogl.

Now that you have all the .jar files, you need to set up the appropriate native libraries. The good news is **you only need to set up native libraries for lwjgl and jReality** (in the past, more native libraries were needed). Start by clicking the arrow next to "jReality.jar." You should see a sub-menu with four items, one of them reading "Native Library Location". Select this item and press the "Edit" button.

You should now see a new window that reads "Native Library Folder Configuration". Press "Workspace" and browse to the folder /Geocam/lib/jni/jReality. Select the sub-folder that describes your processer and operating system, and click OK. For example, my machine is a 32 bit computer with a Linux operating system so I selected linux32. Repeat these same steps with lwjgl.java and /Geocam/lib/jni/lwjgl. Now you should be able to compile and run our code. Try selecting "Geocam->Development2D->frontend->DevelopmentExplorer.java" and see if you can get it to run.

# I just did an SVN update and now the project won't compile! #

Occasionally, someone makes a mistake and commits to SVN the files describing how their own personal eclipse setup is supposed to compile the project. I'm working to try to get all of those files out of our version control system. In the meantime, try the following:
  * Follow the steps above to open the "Configure Build Path" window.
  * Inspect the .jar's to see that you have exactly the necessary libraries.
  * Open each of the .jar's submenus. Do .jReality and .lwjgl have the right native library locations for your machine? Are they the only ones with native libraries? Usually it's this information that is no longer correct.