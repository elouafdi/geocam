# `flip` #
```
Edge flip(Edge e)
```

> ## Keywords ##
> flip, delaunay

> ## Authors ##
> Kurt Norwood

> ## Introduction ##
> The `flip` function takes a single Edge as a parameter and performs a flip on it. This involves determining the new length of the edge after the flip and changing the topological information in the edge being flipped as well as all of the edge's adjacent simplices. This can be thought of as taking two triangles which share an edge (the parameter to flip) and making two new triangles which share an edge between the two vertices which were previously non-adjacent.

> ## Subsidiaries ##

> Functions:

```
    void flipPP(struct simps b)

    void flipPN(struct simps b)

    void flipNN(struct simps b)

    void topo_flip(Edge, struct simps)

    bool prep_for_flip(Edge, struct simps*)
```

> Global Variables:

> Local Variables:

```
    Edge e
```


> ## Description ##

> flip begins by calling the prep\_for\_flip function, that will setup the struct given to it to contain all the important information necessary for the flip to occur, such as indices for the different simplices and the lengths of the triangles' edges, and the two angles which are not incident on the edge being flipped. The struct looks like:
```
struct simps {
       int v0, v1, v2, v3, e0, e1, e2, e3, e4, f0, f1;
       double e0_len, e1_len, e2_len, e3_len, e4_len;
       double a0, a2;
};
```

> With all this information known, the next step is to determine the type of flip that is to occur. The possibilities are broken up three ways: positive positive (PP), positive negative (PN), negative negative (NN); based on the initial condition of the two triangles. This will determine which of flipPP, flipPN, flipNN is called. Within these function is logic which should compute the new edge length and assign it to the edge e, and determine the positive/negative configuration of the two triangles and assign the appropriate boolean value to each face.

> With the new edge length computed and assigned, the topo\_flip function is called which performs the rearrangement of all the adjacencies of the different simplices which are adjacent to edge e.

> The edge is returned.

> ## Practicum ##

```
  Edge e;
  e = Triangulation::edgeTable[indexOfE];
  e = flip(e);
```

> one thing to note is that in future implementations the edge being given as the parameter may be different than the one returned

> ## Limitations ##

> The biggest limitation of the flip function is that it currently only works for bistellar flips. If higher dimensional flips are required this function will need to be modified heavily.

> ## Revisions ##

> 
---

> [r816](https://code.google.com/p/geocam/source/detail?r=816) | kortox | 2009-06-29 12:41:33 -0700 (Mon, 29 Jun 2009) | 1 line

> have all the new\_flip stuff up to date and working with the new geometry classes

> 
---

> [r795](https://code.google.com/p/geocam/source/detail?r=795) | kortox | 2009-06-18 17:58:30 -0700 (Thu, 18 Jun 2009) | 5 lines

> anyway, this is a project for devopment of the flip algorithm, so far it contains a new flip function which is intended to replace the flip function that was previously in Triangulation/triangulationmorphs.cpp

> main currently contains some test functions that can be called one at a time manually and should produce output that can indicate how the flip function is performing, this testing really needs to be improved


> ## Testing ##

> Initially testing was done inefficiently by manually analyzing what was written by the writeTriangulationFile  function. Now that we have a way to display the triangulation, we can select an edge and flip it in the display and see that the flip occurred correctly. Granted this should at sometime in the future be automated, but for now if there is an issue we can try to debug it with the display.

> ## Future Work ##

  * Adding the ability to flip in higher dimensions. This would involve altering the function to take a Simplex object instead of an edge so that it is more general.

  * We'll most likely want to have the function add an edge to the triangulation instead of just reposition the edge given, since this will lend itself better to the possible addition of 3-1 flips. Related to this would also be changing the return type to be a vector of Simplex objects for generality's sake.

  * Moving the whole thing to a different file with an appropriate name other than new\_flip