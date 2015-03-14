#Fixes to make when downloading/updating the project on Mac

# Download/Update Issues for Mac OSX #

When downloading for the first time, need to change the native library of some JARs in the project's build path. To change the native libraries:

  * Geocam => Configure build path => Libraries
  * Click arrow next to the JAR file whose native library you wish to modify (need to modify for gluegen-rt.jar, jogl.jar, jReality.jar, and lwjgl.jar
  * Double click on Native library location
  * In workspace folder, locate lib => jni => jReality => macosx (for the first three JARs) or lib => jni => lwjgl => macosx (for lwjgl.jar)