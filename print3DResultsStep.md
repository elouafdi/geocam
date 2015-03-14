# `print3DResultsStep` #
```
   void print3DResultsStep(char* fileName, vector<double>* radii, vector<double>* curvs)
```

> ## Key Words ##
> radii, curvatures, file, flow, step, print, three-dimensional

> ## Authors ##
> Alex Henniges

> ## Introduction ##
> The `print3DResultsStep` function prints out the results of a curvature flow, with the results grouped by each step of the flow. These results will be written to the file given by `filename`.

> ## Subsidiaries ##
> Functions:

> Global Variables:

> Local Variables: `int vertSize`, `int numSteps`

> ## Description ##
> Prints the results of a curvature flow into the file given by `filename`. The results, that is, the radii and curvature values, are given by vectors of doubles. Most commonly, these vectors are taken from the [Approximator](CurvatureFlow.md) class after the flow is run. The `print3DResultsStep` function determines the number of vertices of the current triangulation and the total number of steps are then derived from this and the size of the vectors.

> There are several ways to display the results. The `print3DResultsStep` function groups by step. This means that for each step of the curvature flow, the radii and curvature values for each vertex is printed. In addition, since Yamabe flow converges with respect to curvature divided by radius, this value is printed as well. Therefore, this function should be used with three-dimensional curvature flows. An example is shown below. Other formats are given by [printResultsStep](printResultsStep.md) , [printResultsVertex](printResultsVertex.md), [printResultsNum](printResultsNum.md).

> ## Practicum ##
> Example:
```
  // Print the results of a curvature flow with Approximator app into file "ODEResult.txt"
  print3DResultsStep("./ODEResults.txt", app->radiiHistory, app->curvHistory);
```

> The output of such an example may then be
```
         :
         :
       Vertex   5     0.8324396       8.5301529       10.2471738
       Total Curvature: 44.5286316

       Step    74     Radius          Curvature        Curv:Radius
       -----------------------------------------------------
       Vertex   1     0.8883594       9.3071126       10.4767428
       Vertex   2     0.8725496       9.0880458       10.4155064
       Vertex   3     0.8579899       8.8858872       10.3566333
       Vertex   4     0.8448655       8.7033021       10.3014058
       Vertex   5     0.8333839       8.5432883       10.2513233
       Total Curvature: 44.5276360

       Step    75     Radius          Curvature        Curv:Radius
       -----------------------------------------------------
       Vertex   1     0.8873282       9.2928034       10.4727922
         :
         :
```

> ## Limitations ##
> Currently the `print3DResultsStep` function is limited in the information it prints. As our curvature flow has evolved to record additional information such as volumes, it may be time to explore a more robust form for displaying results. As there is considerable dependence on the [Approximator](CurvatureFlow.md) for the data vectors, it may be wise to place this and similar functions in the [Approximator](CurvatureFlow.md) class.

> ## Revisions ##
    * subversion 545, 9/29/08: Moved the printing of results out of calcFlow and into a new function.
    * subversion 783, 6/18/09: Small modifications in response to changes in the Approximator class.

> ## Testing ##
> The `print3DResultsStep` function was tested by running multiple curvature flows and printing the results. It was considered working when the format of the data was as desired.

> ## Future Work ##
    * 6/29 - Recreate the print functions to print more data and be more flexible.
    * 6/29 - Move the print functions into the Approximator class.