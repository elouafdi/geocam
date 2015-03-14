# `TriangulationDisplay` #
```
namespace TriangulationDisplay
```

> ## Keywords ##

> visualization, triangulation

> ## Authors ##
> Kurt Norwood

> ## Introduction ##

> TriangulationDisplay is a namespace which allows users to display planar triangulations. It provides functions for doing this in a couple of different ways. TriangulationDisplay also captures key presses which can be used to alter the viewing mode, select edges and perform flips, toggling dual edges on/off, toggling radii on/off, and running the flip algorithm.

> ## Subsidiaries ##

> Functions:
```
  ShowTriangulation(...)
  setFile(char * f)
```
> Global Variables:

> Local Variables:


> ## Description ##

> `TriangulationDisplay` does a variety of things. Its primary purpose is to display planar triangulations to make debugging and investigating flip algorithms easier, plus it can look pretty cool sometimes. To get it started you just have to call `ShowTriangulation(char * f)` with the name of a triangulation file, and it will pop up a window with that triangulation drawn out in a plane. After calling `ShowTriangulation`, your program will not return because part of displaying a glut (part of opengl) window is to enter an infinite loop. There is an alternate version of this function which looks like `ShowTriangulation()` which we can use to display a triangulation without loading it from a file, for example if the triangulation was created or modified prior to being displayed the user would want to call this version of `ShowTriangulation`.

> Behind the scenes there are a lot of things going on in the `TriangulationDisplay` namespace. Apart from automatically configuring opengl, it tries to determine an appropriate distance and position for viewing the triangulation. If this configuration isn't suitable for the users needs they can alter it manually with keyboard inputs. This brings us to one of the more complicated parts of `TriangulationDisplay` which is the `handleKeyPress` and `handleSpecialKeyPress` functions which are the only functions people might want to alter in the future. Opengl will call these functions anytime a key is pressed, and so these functions are where all the key commands are defined. Any keys that have standard ascii representations are sent to `handleSpecialKeyPress` while special keys like the arrow keys are sent to `handleSpecialKeyPress`. Most of the key commands alter some state (either state in the Triangulation class or state pertaining to how the triangulation is being viewed) in `TriangulationDisplay`, others will cause useful info to be printed to the command line.

> ## Practicum ##
> You just want to display a triangulation
```
    #include "TriangulationDisplay.h"
    ...
    TriangulationDisplay::ShowTriangulation("path/to/triangulation.txt");
```

> Want to set up a triangulation your self, tweak it, break it, fix it up, then display it

```
  #include "TriangulationDisplay.h"
  ...
  readTriangulationFile("path/to/triangulation.txt");
  ...
  //bunch of code modifying the triangulation
  ...
  //you can optionally tell TriangulationDisplay which file you are using
  //this can be good if you want to ask TriangulationDisplay to reload the triangulation at sometime
  setFile("path/to/triangulation.txt")
  ...
  //calling ShowTriangulation with no parameters will not try to load a file
  //this way any changes you've made will be visible
  ShowTriangulation();
```
> ## Limitations ##
> > Would take a bit of work to show multiple triangulations at the same time.

> ## Revisions ##

> [r890](https://code.google.com/p/geocam/source/detail?r=890) | kortox | 2009-07-22 11:13:23 -0700 (Wed, 22 Jul 2009) | 2 lines

> Lots of changes to the graphical display of the triangulation, displaying the dual edges works properly now, as well as showing the weights on the vertices. The dirichlet energy computation is included in the delaunay.cpp file and it seems to be working properly. added several files to the new\_flip/test\_files as examples of how flipping reduces the dirichlet energy which right now is the only info that will be printed while running the new\_flip project


> [r854](https://code.google.com/p/geocam/source/detail?r=854) | kortox | 2009-07-07 11:07:22 -0700 (Tue, 07 Jul 2009) | 2 lines

> Got the TriangulationDisplay class working a bit better, also fixed an error in the TriangulationCoordinateSystem class involving an accidental call to getLocalVertices instead of getLocalEdges which had been causing some very strange and difficult to track down behavior

> ## Testing ##

> ## Future Work ##