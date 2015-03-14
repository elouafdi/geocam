# Introduction #

One of our goals this summer is to produce some executables that non-programmers can download and run. In particular, we want to be able to distribute the  "Development 2D" portion of the project, which has some graphical dependencies.

At the risk of stating the obvious, a user-friendly application is more than just an executable or .jar file. It contains at least the following data:
  1. A file telling the OS how to launch the application.
  1. An icon (My impression is that the great majority of users only know how to launch applications by clicking on icons.)
  1. The executable itself (a .jar file, for instance).
  1. Any system-specific libraries the program needs.
  1. A collection of data files, including:
    * Textures and 3D models (for the insects, markers, etc.)
    * Manifolds
  1. A plan for storing all of this material somewhere on the user's machine.

The purpose of this page is to describe:
  1. Where the data above resides within our project
  1. How to package our project for distribution
  1. What tools help with the previous item, and how to use them

## Here be Dragons ##

Last summer we made at least two attempts at packaging the project. Although some progress was made, we ran into a number of problems:
  1. Applications are organized differently on each OS.
    * Ex: On windows, most users are reasonably comfortable with .exe files (which can bundle together some of the items listed above). On Mac OSX, .app files are the norm, etc.)
  1. Path organization problems. Basically, any time you reference a particular file (like a texture) in the code, you need to make sure that reference still makes sense in the final application. With java, using Linux/Mac style relative paths will still work on Windows (Java just converts them to Windows format), but obviously if you use a hard path ("C:\jethro\jethros\_special\_texture.jpg") then we have problems.
  1. The right native libraries need to be included in the distribution. Fortunately, there are only two things we need to get right --- the jReality native library and the lwjgl native library.
  1. We never figured out how to automate the process of packaging. This ended up causing a lot of problems, because it meant people were disinclined to produce a new copy of the application when they fixed a bug.

## Packaging Tools ##

A quick search of stack exchange turned up the following tools for packaging our application on different operating systems. All of the tools listed below are free.

### Windows ###
  * [Launch4j](http://launch4j.sourceforge.net/):
  * [JSmooth](http://jsmooth.sourceforge.net/):
  * [NSIS](http://nsis.sourceforge.net/Main_Page): This tool appears to create an _installer_ rather than an application _launcher_. I've read one needs to use it with one of the tools above.

### Mac ###
  * Package Maker: I think we tried this in the past and didn't like it --- getting the native libraries we needed into the package was a pain. The tool was written by Apple, but I've read that even Apple no longer uses it and that now it has been removed from the latest versions of their OS.

### Windows/Mac/Linux ###
  * [lzpack](http://izpack.org/): This tool appears to be in commerical use, and works for all three operatings systems. I will probably try using this first.