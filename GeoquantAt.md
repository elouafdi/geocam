# `Geoquant::At` #
```
Geoquant*  Geoquant::At(Simplex  s1,  ...)
```

> ## Key Words ##
> geoquant, recalculate, dependent, triposition, simplex

> ## Authors ##
    * Joseph Thomas

> ## Introduction ##
> The `At` function is defined for every type of geoquant as a way to retrieve that quantity. Once the quantity is retrieved, a value can be set or asked of the quantity. A quantity is retrieved by providing a list of simplices that describe the position of the quantity in the triangulation.

> ## Subsidiaries ##
> Functions:
    * getSerialNumber
> Global Variables: map
> Local Variables: possible list of simplices

> ## Description ##
> The `At` function is a little diﬀerent for every type of geoquant, but in all cases it is a static function for that class that serves as an object retrieval in place of a constructor. The function takes as a parameter a list of simplices which may be diﬀerent for each type of geoquant. The list is the natural description of where the quantity is in the triangulation. For example, a radius is described by a vertex, whereas an angle is described as a vertex on a certain face. The At function returns a pointer to the requested quantity.

> When the `At` function is called, it searches a local map for a quantity with the given list of simplices.  If it is found, a pointer to that quantity in the map is simply returned.  If it is not found, the quantity is constructed and placed into the map.  If the construction of the object requires other types of quantities not yet created, then these will be constructed automatically at this time. Lastly, this quantity is returned.

> The constructor is hidden from the user for several reasons. The ﬁrst is that this avoids redundant construction and the need for an encapsulating object to hold a large set of geoquants (like the Geometry class in a previous version).  In the same vein, the need for an initial build step and a required order of construction is removed. In addition, this is an eﬃciency improvement as quantities that are never requested are never created, decreasing memory use and large dependency trees which can take a while for an `invalidate` to traverse.

> ## Practicum ##
> Example:
```
//  Get  the  Radius  quantity  from  the  first  vertex  in  the  triangulation.
Radius  *r  =  Radius::At(Triangulation::vertexTable[0]);
//  Get  the  angle  of  vertex  v  incident  on  face  f
Vertex  v;
Face  f;
...
EuclideanAngle  *ang  =  EuclideanAngle::At(v,  f);
```

> ## Limitations ##
> The `At` function is limited in that a speciﬁc set of simplices will always return the exact same object.  While this is in fact the design goal, this can limit one’s ability to modify an object as a change in one place will aﬀect its use elsewhere in the code. The function also will require the user to handle pointers, a powerful yet fragile and sometimes daunting aspect of the programming language.

> ## Revisions ##
    * subversion 761, 6/12/09: A working copy of `At` and the Geoquant system.

> ## Testing ##
> The `At` function was tested in small modularized systems, then tested in a three dimensional ﬂow, which required many varied uses of `At`.   Some retrieved quantities had their values set while others had their values accessed and compared with what mathematica calculations predicted.

> ## Future Work ##
> No future work is planned at this time.