# `printResultsNum` #
```
	void printResultsNum(char* fileName, vector<double>* radii, vector<double>* curvs)
```

## Key Words ##
radii, curvatures, file, flow, vertex, print

## Authors ##
Alex Henniges

## Introduction ##

The `printResultsNum` function prints out the results of a curvature
flow, with the results grouped by each vertex of the triangulation but
without labels. This format is used for when a program, (GUI, Matlab,
etc) needs to parse the data. These results will be written to the
file given by `filename`.

## Subsidiaries ##

Functions:

Global Variables:

Local Variables: `int vertSize`, `int numSteps`

## Description ##

Prints the results of a curvature flow into the file given by
`filename`. The results, that is, the radii and curvature values, are
given by vectors of doubles. Most commonly, these vectors are taken
from the [Approximator](CurvatureFlow.md) class after the flow is run. The
`printResultsNum` function determines the number of vertices of the
current triangulation and the total number of steps are then derived
from this and the size of the vectors.

There are several ways to display the results. The `printResultsNum`
function groups by vertex but provides no labels. This means that for
each vertex of the triangulation, the radii (first column) and
curvature (second column) values for each step is given. This format
is used for when a program, (GUI, Matlab, etc) needs to parse the
data. Therefore, it would be difficult for a human to read, but allows
the computer to do so much easier. An example is shown below. Other
formats are given by [printResultsStep](printResultsStep.md), [printResultsVertex](printResultsVertex.md),
[print3DResultsStep](print3DResultsStep.md).

## Practicum ##

Example:
```
    // Print the results of a curvature flow with
    // Approximator app into file "ODEResult.txt"
    printResultsNum("./ODEResults.txt", app->radiiHistory, app->curvHistory); 
```

The output of such an example may then be
```
         :
         :
      0.8272160717	3.1425515910
      0.8272081392	3.1425294463
      0.8272003900	3.1425078130

      1.0000000000	3.1415926536
      1.0000000000	3.5987926375
      0.9954280002	3.5877016632
      0.9909873062	3.5768691746
      0.9866737711	3.5662905388
         :
         :
```

## Limitations ##

Currently the `printResultsNum` function is limited in the information
it prints. As our curvature flow has evolved to record additional
information such as volumes, it may be time to explore a more robust
form for displaying results. As there is considerable dependence on
the [Approximator](CurvatureFlow.md) for the data vectors, it may be wise
to place this and similar functions in the [CurvatureFlow
Approximator] class.

## Revisions ##

**subversion 545, 9/29/08: Moved the printing of results out of calcFlow and into a new function.** subversion 783, 6/18/09: Small modifications in response to changes in the Approximator class.

## Testing ##

The `printResultsNum` function was tested by running multiple
curvature flows and printing the results. It was considered working
when the format of the data was as desired.

## Future Work ##

**6/29 - Recreate the print functions to print more data and be more flexible.** 6/29 - Move the print functions into the Approximator class.