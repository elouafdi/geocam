# `printResultsStep` #
```
   void printResultsStep(char* fileName, vector<double>* radii, vector<double>* curvs)
```

> ## Key Words ##
> radii, curvatures, file, flow, step, print

> ## Authors ##
> Alex Henniges

> ## Introduction ##
> The `printResultsStep` function prints out the results of a curvature flow, with the results grouped by each step of the flow. These results will be written to the file given by `filename`.

> ## Subsidiaries ##
> Functions:

> Global Variables:

> Local Variables: `int vertSize`, `int numSteps`

> ## Description ##
> Prints the results of a curvature flow into the file given by `filename`. The results, that is, the radii and curvature values, are given by vectors of doubles. Most commonly, these vectors are taken from the [Approximator](CurvatureFlow.md) class after the flow is run. The `printResultsStep` function determines the number of vertices of the current triangulation and the total number of steps are then derived from this and the size of the vectors.

> There are several ways to display the results. The `printResultsStep` function groups by step. This means that for each step of the curvature flow, the radii and curvature values for each vertex is printed. An example is shown below. Other formats are given by [printResultsVertex](printResultsVertex.md), [printResultsNum](printResultsNum.md), [print3DResultsStep](print3DResultsStep.md).

> ## Practicum ##
> Example:
```
  // Print the results of a curvature flow with Approximator app into file "ODEResult.txt"
  printResultsStep("./ODEResults.txt", app->radiiHistory, app->curvHistory);
```

> The output of such an example may then be
```
         :    
         :
       Vertex   4     0.5397923       2.1411007
       Total Curvature: 12.5663706

       Step     9     Radius          Curvature
       -----------------------------------------------------
       Vertex   1     1.1681789       3.9076805
       Vertex   2     0.9668779       3.5170408
       Vertex   3     0.7605802       2.9772908
       Vertex   4     0.5451929       2.1643586
       Total Curvature: 12.5663706

       Step    10     Radius          Curvature
       -----------------------------------------------------
       Vertex   1     1.1592296       3.8916213
         :
         :
```

> ## Limitations ##
> Currently the `printResultsStep` function is limited in the information it prints. As our curvature flow has evolved to record additional information such as volumes, it may be time to explore a more robust form for displaying results. As there is considerable dependence on the [Approximator](CurvatureFlow.md) for the data vectors, it may be wise to place this and similar functions in the [Approximator](CurvatureFlow.md) class.

> ## Revisions ##
    * subversion 545, 9/29/08: Moved the printing of results out of calcFlow and into a new function.
    * subversion 783, 6/18/09: Small modifications in response to changes in the Approximator class.

> ## Testing ##
> The `printResultsStep` function was tested by running multiple curvature flows and printing the results. It was considered working when the format of the data was as desired.

> ## Future Work ##
    * 6/29 - Recreate the print functions to print more data and be more flexible.
    * 6/29 - Move the print functions into the Approximator class.