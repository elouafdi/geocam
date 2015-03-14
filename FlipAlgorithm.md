# `FlipAlgorithm` #
```
class FlipAlgorithm()
```

> ## Keywords ##

> flips, weighted Delaunay, flip algorithm

> ## Authors ##
> Kurt Norwood

> ## Introduction ##

> "The flip algorithm" is typically used to refer to the algorithm which turns some unweighted triangulations into a Delaunay triangulation by flipping any non-Delaunay edges. We would like to be able to implement this algorithm and create new algorithms which might be suitable for doing something similar to a weighted Triangulation. In particular we want some condition to call "weighted Delaunay", and an algorithm which will take in a weighted Triangulation and alter it to be weighted Delaunay by performing flips on edges.

> ## Subsidiaries ##

> Functions:
```
    public:
    FlipAlgorithm(void);
    ~FlipAlgorithm(void);

    //iterates over the edges flipping any that are convex and not weighted Delaunay
    //returns 0 if none are found, 1 if a flip occurs
    int flipConvexHinges(void);

    //seeks out the a non-convex hinge that is also not weighted Delaunay and flips it
    //returns 0 if non are found, 1 if a flip occurs
    int flipOneNonConvexHinge(void);

    //calls the function flip_hinge on currently selected edge
    void performFlip(void);

    //returns the index of the currenly selected edge
    int currentEdgeIndex(void);

    //performs a single "step" of the algorithm, by calling either flipConvexHinges, or flipOneNonConvexHinge
    int step(void);

    //performs steps until 0 is returned by step
    void runFlipAlgorithm(void);
```

> Global Variables:

> Local Variables:
```
    //points to the currenly selected edge
    map<int, Edge>::iterator currEdge;

    //flip flops between 0 and 1 so that step() knows which function to call
    int whichStep;
```

> ## Description ##

> FlipAlgorithm is a class intended to make the writing and testing of different flip algorithms easier. To do this we write functions which perform a particular action on the triangulation. These actions should be fairly easy to describe, and independent of other actions. There are also a couple other functions which are where the actions are organized into a potential flip algorithm.

> Currently there are two functions describing the actions we would like to perform. The function "flipConvexHinges" will iterate over all the edges and flip any convex hinges having a negative dual edge. The function "flipOneNonConvexHinge" will iterate over the edges and flip the first nonconvex hinge with a negative dual edge. Each of these functions is considered one step of the algorithm. To keep track of which step we're on, the whichStep variable flips between 0 and 1 so that the function "step" knows which of the other functions to call. The function "runFlipAlgorithm" just calls step over and over again until step says it did nothing at which point the algorithm is considered to be done.

> ## Practicum ##
```
    //we'll use triDisp for drawing the triangulation
    TriangulationDisplay * triDisp = new TriangulationDisplay("some/triangulation/file.txt");
    
    FlipAlgorithm * flipAlg = new FlipAlgorithm();
    
    vector<triangle_parts> ts;
    ts = triDisp->getTriangles();
    
    //here you could draw to the screen the triangles
    
    //this will run the flip algorithm
    flipAlg->runFlipAlgorithm();
    
    //now you can draw the triangulation after the algorithm is complete
```
> ## Limitations ##

> Requires rewriting of the flipAlgorithm, and step functions to change the algorithm

> ## Revisions ##

> 
---

> [r1009](https://code.google.com/p/geocam/source/detail?r=1009) | kortox | 2009-10-14 13:52:10 -0700 (Wed, 14 Oct 2009) | 3 lines

> ## Testing ##

> ## Future Work ##