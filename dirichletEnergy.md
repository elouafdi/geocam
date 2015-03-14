# `dirichletEnergy` #
```
double dirichletEnergy(Function vertexF)
```

> ## Keywords ##
> Dirichlet, energy, flip algorithm

> ## Authors ##
> Kurt Norwood

> ## Introduction ##

> The `dirichletEnergy` function computes the Dirichlet energy of the triangulation currently being modeled. It is important to us because it could be used in a proof of the flip algorithm for triangulations. It decreases when a non-Delaunay hinge is flipped to being Delaunay. This characteristic does not completely carry over to weighted triangulations so being able to compute this energy easily at anytime may give us insight into developing a weighted flip algorithm. For our purposes the Dirichlet energy is being used to test our model and the `flip` procedure.

> The formula for the Dirichlet energy can be viewed in latex with:
> ` D(f_{V(T)}, T) = \sum\limits_{\{i,j\}\in E(T)} \frac{|dual(\{i,j\})|}{|\{i,j\}|} (f(j)- f(i))^2`


> ## Subsidiaries ##

> Functions:

```
  getDual(Edge e)

  Length::valueAt(Edge e)
```

> Global Variables:

> Local Variables:

```
  double total - the running sum of the terms in the dirchlet energy formula

  Function vertexF - the function on the vertices
```

> ## Description ##

> `dirichletEnergy()` assumes that `vertexF` was built correctly, this is important to keep in mind because a call like `vertexF[n]` with the variable `n` undefined `vertexF` will just return 0.0 so the program will run without error.

> With that in mind, `dirichletEnergy` simply iterates over the edges, computing each edge's `dualHeight` and length, as well as getting the vertexF value for each of its vertices. These values can be used to compute each term in the formula for the dirichlet energy. Each of these terms is added to the variable `total` which is eventually returned.


> ## Practicum ##

> The following code should be able to be dropped in a `main` and give a demonstration of the Dirichlet energy decreasing as a result of flipping non-Delaunay edges.

```
  readTriangulationFile("path/to/a/triangulation/file.txt");
  
  cout << dirichletEnergy() << "\n";
  
  map<int, Edge>::iterator eit;
  eit = Triangulation::edgeTable.begin();
  
  for (; eit != Triangulation::edgeTable.end(); eit++) {
    if (!isWeightedDelaunay(eit->second)) {
      flip(eit->second);
      cout << dirichletEnergy() << "\n";
    }
  }
  
  cout << "done!\n";

  ------------output-might-look-something-like---------------------
 
4.61646

4.06364

3.72746

2.43225

1.72232
done!
  
```

> ## Limitations ##

> The only current limitation is that the `vertexF` variable is assumed to be built correctly for the current triangulation.

> ## Revisions ##

> 
---

> [r992](https://code.google.com/p/geocam/source/detail?r=992)

> Added the class Function.cpp which represents a function on a set of Simplex objects.

> Also fixed dirichletEnergy() in delaunay.cpp so that is looks like dirichletEnergy(Function vertexF)

> 
---

> [r890](https://code.google.com/p/geocam/source/detail?r=890)

> Lots of changes to the graphical display of the triangulation, displaying the dual edges works properly now, as well as showing the weights on the vertices. The dirichlet energy computation is included in the delaunay.cpp file and it seems to be working properly. added several files to the new\_flip/test\_files as examples of how flipping reduces the dirichlet energy which right now is the only info that will be printed while running the new\_flip project

> ## Testing ##

> It's been used many times, and exhibits the behavior expected. Also it is relatively simple so it is tested by inspection of the code.

> ## Future Work ##
