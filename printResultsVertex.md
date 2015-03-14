# `printResultsVertex` #
```
   void printResultsVertex(char* fileName, vector<double>* radii, vector<double>* curvs)
```

> ## Key Words ##
> radii, curvatures, file, flow, vertex, print

> ## Authors ##
> Alex Henniges

> ## Introduction ##
> The `printResultsVertex` function prints out the results of a curvature flow, with the results grouped by each vertex of the triangulation. These results will be written to the file given by `filename`.

> ## Subsidiaries ##
> Functions:

> Global Variables:

> Local Variables: `int vertSize`, `int numSteps`

> ## Description ##
> Prints the results of a curvature flow into the file given by `filename`. The results, that is, the radii and curvature values, are given by vectors of doubles. Most commonly, these vectors are taken from the [Approximator](CurvatureFlow.md) class after the flow is run. The `printResultsVertex` function determines the number of vertices of the current triangulation and the total number of steps are then derived from this and the size of the vectors.

> There are several ways to display the results. The `printResultsVertex` function groups by vertex. This means that for each vertex of the triangulation, the radii and curvature values for each step is given. An example is shown below. Other formats are given by [printResultsStep](printResultsStep.md), [printResultsNum](printResultsNum.md), [print3DResultsStep](print3DResultsStep.md).

> ## Practicum ##
> Example:
```
  // Print the results of a curvature flow with Approximator app into file "ODEResult.txt"
  printResultsVertex("./ODEResults.txt", app->radiiHistory, app->curvHistory);
```

> The output of such an example may then be
```
         : 
         :
       Step  298	0.8272161	3.1425516
       Step  299	0.8272081	3.1425294
       Step  300	0.8272004	3.1425078

       Vertex:   2	Radius		Curv

       ---------------------------------
       Step    0	1.0000000	3.1415927
       Step    1	1.0000000	3.5987926
       Step    2	0.9954280	3.5877017
       Step    3	0.9909873	3.5768692
       Step    4	0.9866738	3.5662905
         :
         :
```

> ## Limitations ##
> Currently the `printResultsVertex` function is limited in the information it prints. As our curvature flow has evolved to record additional information such as volumes, it may be time to explore a more robust form for displaying results. As there is considerable dependence on the [Approximator](CurvatureFlow.md) for the data vectors, it may be wise to place this and similar functions in the [Approximator](CurvatureFlow.md) class.

> ## Revisions ##
    * subversion 545, 9/29/08: Moved the printing of results out of calcFlow and into a new function.
    * subversion 783, 6/18/09: Small modifications in response to changes in the Approximator class.

> ## Testing ##
> The `printResultsVertex` function was tested by running multiple curvature flows and printing the results. It was considered working when the format of the data was as desired.

> ## Future Work ##
    * 6/29 - Recreate the print functions to print more data and be more flexible.
    * 6/29 - Move the print functions into the Approximator class.