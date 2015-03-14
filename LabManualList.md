# Introduction #

This page contains basic information on the documentation of the functions and classes for the geocam project.

# Details #
Add classes and functions to the following lists as follows:
  1. locate the appropriate function type (double, int, void, etc.),
> 2.  Place the function in alphabetical order with exactly the following format (in edit mode):
```
||ExampleFunction1||double||Kathrine||draft||
||ExampleFunction2||int||Jules||done||
||ExampleFunction3||vector||Lord Vader||partial||
||ExampleFunction4||void||Zoltar|| ||   <- leave "Progress" blank if no progress
||ExampleFunction5||bool|| || ||        <- leave "Owner" blank if it is unknown
```

| **Class** | **Owner** | **Progress** |
|:----------|:----------|:-------------|
|Approximator|Alex|draft|
|Area|  |  |
|Circle|Alex|  |
|Curvature2D|  |  |
|Curvature3D|  |  |
|DihedralAngle|  |  |
|Edge|  |  |
|EdgeCurvature|  |  |
|Eta|  |  |
|EuclideanAngle|  |  |
|EulerApprox|  |  |
|Face|  |  |
|GeoQuant|  |  |
|Length|  |  |
|Line|  |  |
|PartialEdge|Dan |  |
|Point|Alex|  |
|Radius|  |  |
|RungaApprox|Joe |  |
|Simplex|Alex|  |
|SphericalAngle|  |  |
|Tetra|  |  |
|Triangulation|Alex|  |
|TriangulationCoordinateSystem|Alex|  |
|TriPosition|  |  |
|Vertex|  |  |
|Volume|  |  |
|Pair|Alex|  |
|Triple|Alex|  |




| **Function** | **Type** | **Owner** | **Progress** |
|:-------------|:---------|:----------|:-------------|
|  |  |  |  |
| **boolean functions** | **boolean functions** |  |  |
|isAccurate|bool|  |  |
|isDelaunay|bool|Alex|obsolete|
|isDoubleTriangle|bool|Alex|obsolete|
|isPrecise|bool|  |  |
|isWeightedDelaunay|bool|Alex|obsolete|
|read3DTriangulationFile|bool|Alex|  |
|  |  |  |  |
| **double functions** | **double functions** |  |  |
|adjDiffEQ|double|  |  |
|Aij\_kl|double|Dan |LaTex |
|angle|double|Alex|  |
|calcNormalization|double|Dan |  |
|CayleyvolumeSq|double|Dan |  |
|CayleyvolumeSqDerivative|double|Dan |  |
|curvature|double|  |  |
|Curvature\_Partial|double|Dan |LaTex |
|dij|double|Dan |LaTex |
|distancePoint|double|  |  |
|dualArea|double|  |  |
|dualLength|double|  |  |
|EHR|double|Dan |LaTex |
|EHR\_Partial|double|Dan |LaTex |
|EHR\_Second\_Partial|double|Dan |LaTex |
|F |double|Dan |  |
|FE|double|Dan |  |
|FEE|double|Dan |  |
|FR|double|Dan |  |
|getDual|double|Alex|  |
|getHeight|double|Alex|  |
|hij\_k|double|Dan |LaTex |
|hijk\_l|double|Dan |LaTex |
|inRadius|double|Alex|obsolete|
|Lij\_star|double|Dan |LaTex |
|plot|double|Dan |  |
|SecondPartial|double|Dan |  |
|Total\_Curvature|double|Dan |LaTex |
|Total\_Volume|double|Dan |LaTex |
|Volume\_Partial|double|Dan |LaTex |
|Volume\_Second\_Partial|double|Dan |LaTex |
|volumeSq|double|Dan |  |
|  |  |  |  |
| **int functions** | **int functions** |  |  |
|addEdgeToEdge|int|Alex|  |
|addVertexToEdge|int|Alex|  |
|addVertexToFace|int|Alex|  |
|addVertexToVertex|int|Alex|  |
|LinearEquationsSolving|int|??? |  |
|makeFace|int|Alex|  |
|makeTetra|int|Alex|  |
|  |  |  |  |
| **point functions** | **point functions** |  |  |
|findPoint|point|Alex|  |
|rotateVector|point|Alex|  |
|  |  |  |  |
| **vector functions** | **vector functions** |  |  |
|circleIntersection|vector|Alex|  |
|getTriangles|vector

<triangle\_parts>

|Kurt|  |
|listDifference|vector|Alex|  |
|listIntersection|vector|Alex|  |
|quadratic|vector|Alex|  |
|  |  |  |  |
| **void functions** | **void functions** |  |  |
|add|void|Alex|  |
|addCrossCap|void|Tom|  |
|addHandle|void|Tom|  |
|addLeaf|void|Tom|  |
|addNewVertex|void|Tom|  |
|addTriangle|void|Tom|  |
|AdjHypRicc|void|  |  |
|AdjSpherRicci|void|  |  |
|calcDeltaFE|void|Dan |  |
|calcDeltaFR|void|Dan |  |
|errorMessage|void|  |  |
|flip|void|Kurt|  |
|fourOneMove|void|Tom|  |
|Hessian|void|Dan |  |
|HypRicci|void|  |  |
|loadRadii|void|  |  |
|make3DTriangulationFile|void|Alex|complete|
|makeTriangulationFile|void|Alex|  |
|MinMax|void|Dan |  |
|Newtons\_Method|void|Dan |  |
|oneFourMove|void|Tom|  |
|oneThreeMove|void|Tom|  |
|print3DResultsStep|void|Alex|complete|
|printData|void|  |  |
|printResultsNum|void|Alex|complete|
|printResultsNumSteps|void|Alex|  |
|printResultsStep|void|Alex|complete|
|printResultsVertex|void|Alex|complete|
|printResultsVolumes|void|Alex|  |
|**readTriangulationFile**|void|Alex|  |
|readEtas|void|Alex |  |
|recalculate|void|  |  |
|remove|void|Alex|  |
|removeVertex|void|Alex|  |
|SpherRicci|void|  |  |
|StdRicci|void|  |  |
|threeOneMove|void|Tom|  |
|threeTwoMove|void|Tom|  |
|updateEtas|void|Dan |  |
|updateRadii|void|Dan |  |
|validateArgs|void|  |  |
|write3DTriangulationFile|void|Alex|  |
|writeTriangulationFile|void|Alex|  |
|**writeTriangulationFileWithData**|void|Alex|  |
|Yamabe|void|  |  |
|  |  |  |  |
| **constructor functions** | **constructor functions** |  |  |
|Area|constructor|  |  |
|Curvature2D|constructor|  |  |
|Curvature3D|constructor|  |  |
|DihedralAngle|constructor|  |  |
|EdgeCurvature|constructor|  |  |
|Eta|constructor|  |  |
|EuclideanAngle|constructor|  |  |
|HyperbolicAngle|constructor|  |  |
|IndLength|constructor|  |  |
|Length|constructor|  |  |
|PartialEdge|constructor|  |  |
|Radius|constructor|  |  |
|SphericalAngle|constructor|  |  |
|TriPosition|constructor|  |  |
|Volume|constructor|  |  |

Additional things we need: How to install Dev-C++, How to install glut on Dev-C++.