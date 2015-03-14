# `printResultsNumSteps` #
```
   void printResultsNumSteps(char* fileName, vector<double>* radii, vector<double>* curvs)
```

> ## Key Words ##
> radii, curvatures, file, flow, vertex, print

> ## Authors ##
> Alex Henniges

> ## Introduction ##
> The `printResultsNumSteps` function prints out the results of a curvature flow, with the results grouped by each step of the triangulation but without labels. This format is used for with the GUI to create a polygonal representation of curvatures. These results will be written to the file given by `filename`.

> ## Subsidiaries ##
> Functions:

> Global Variables:

> Local Variables: `int vertSize`, `int numSteps`

> ## Description ##
> Prints the results of a curvature flow into the file given by `filename`. The results are curvature divided by radii values, and are given by vectors of doubles. Most commonly, these vectors are taken from the [Approximator](CurvatureFlow.md) class after the flow is run. The `printResultsNumSteps` function determines the number of vertices of the current triangulation and the total number of steps are then derived from this and the size of the vectors.

> There are several ways to display the results. The `printResultsNumSteps` function groups by step but provides no labels and does not print out radii, but instead curvautre divided by radii. The purpose for this format is to create the "Polygon flows" in the GUI. Therefore, it would be difficult for a human to read, but allows the computer to do so much easier. An example is shown below.

> ## Practicum ##
> Example:
```
  // Print the results of a curvature flow with Approximator app into file "ODEResult.txt"
  printResultsNumSteps("./ODEResults.txt", app->radiiHistory, app->curvHistory);
```

> The output of such an example may then be
```
         :
         :
      3.1425515910
      3.1425294463
      3.1425078130

      3.1415926536
      3.5987926375
      3.5877016632
      3.5768691746
      3.5662905388
         :
         :
```

> ## Limitations ##
> Unlike the other print functions, the purpose of `printResultsNumSteps` is to only display the curvature divided by radii, and so is not limited in the information it prints. On the otherhand, an overhaul of the entire printing system would likely involve modifying this function.

> ## Revisions ##
    * subversion 545, 9/29/08: Moved the printing of results out of calcFlow and into a new function.
    * subversion 783, 6/18/09: Small modifications in response to changes in the Approximator class.

> ## Testing ##
> The `printResultsNumSteps` function was tested by running multiple curvature flows and printing the results. It was considered working when the format of the data was as desired.

> ## Future Work ##
    * 6/29 - Recreate the print functions to print more data and be more flexible.
    * 6/29 - Move the print functions into the Approximator class.