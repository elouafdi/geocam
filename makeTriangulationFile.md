# `makeTriangulationFile` #
```
void makeTriangulationfile(char* fileIN, char* fileOUT)
```

> ## Keywords ##
> triangulation, Lutz, simplices

> ## Authors ##
    * Alex Henniges
    * Mitch Wilson

> ## Introduction ##
> The `makeTriangulationFile` function converts a text file, given by `fileIn`, in the `Lutz` format to the standard format, printed to `fileOUT`. The file in standard format can then be read into the system to build the triangulation.

> ## Subsidiaries ##
> Functions:
    * Pair::positionOf
    * Pair::contains
    * Pair::isInTuple
> Global Variables:

> Local Variables: `fileIN`, `fileOUT`

> ## Description ##
> This function is used to convert one format to another format that we consider to be the standard for reading in a triangulation. We have dubbed the format we are converting from `Lutz`. This is based on the source we retrieve this format from, http://www.math.tu-berlin.de/diskregeom/stellar/ .

> The `Lutz` format provides a simpler interface than our standard format, and can therefore allow for a user to create a quick triangulation. The idea is to provide only the index of every vertex on each face of the triangulation. No information about edges or adjacencies need to be given. The file should begin with a "=" followed by "[" and "]"'s to contain the triangulation and each face. An example `Lutz` format for a tetrahedron is given [below](#Practicum.md).

> Note that the `makeTriangulationFile` is used only for two-dimensional triangulations, and that for three-dimensions, one should use [make3DTriangulationFile](make3DTriangulationFile.md).

> ## Pracicum ##
```
  // Convert the tetrahedron written in Lutz format to a file in standard format.
  makeTriangulationFile("./tetra_lutz.txt", "./tetra_standard.txt");
  
  // Now read in the triangulation from standard format.
  readTriangulationFile("./tetra_standard.txt");
```

> The `Lutz` format may look like:
```
  =[[1,2,3],[1,2,4],[2,3,4],[1,3,4]]
```

> The `makeTriangulationFile` would then create a file with:
```
Vertex: 1
2 3 4 
1 2 4 
1 2 4 
Vertex: 2
1 3 4 
1 3 5 
1 2 3 
Vertex: 3
1 2 4 
2 3 6 
1 3 4 
Vertex: 4
1 2 3 
4 5 6 
2 3 4 
Edge: 1
1 2
2 3 4 5 
1 2 
Edge: 2
1 3
1 3 4 6 
1 4 
Edge: 3
2 3
1 2 5 6 
1 3 
Edge: 4
1 4
1 2 5 6 
2 4 
Edge: 5
2 4
1 3 4 6 
2 3 
Edge: 6
3 4
2 3 4 5 
3 4 
Face: 1
1 2 3 
1 2 3 
2 3 4 
Face: 2
1 2 4 
1 4 5 
1 3 4 
Face: 3
2 3 4 
3 5 6 
1 2 4 
Face: 4
1 3 4 
2 4 6 
1 2 3 
```

> ## Limitations ##
> The limitation with the `Lutz` format that prevents it from being considered the standard format is that the user cannot create the most general of triangulations. To be more specific, it is impossible with the `Lutz` format to specify for there to be two edges with the same vertices.

> A limitation of the `makeTriangulationFile` is that its requirements are unintuitive. There should be no "=" required, for example. Another limitation is that despite collecting enough information to build the triangulation, the function instead writes this to a file, requiring the user to subsequently call the function [readTriangulationFile](readTriangulationFile.md).

> ## Revisions ##
    * subversion 545, 9/29/08: Added the `makeTriangulationFile` function.

> ## Testing ##
> This function has been tested through frequent use.

> ## Future Work ##
    * 7/1 - Improve the format system.
    * 7/1 - Create the triangulation without performing a conversion to another file.