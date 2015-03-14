# `Approximator` #

> ## Key Words ##
> curvature flow, differential equations, Euler's method, Runga Kutta

> ## Authors ##
    * Joe Thomas
    * Alex Henniges

> ## Introduction ##
> The Approximator class runs a curvature flow using one of several methods. The class itself is abstract and the method is chosen by the instantiating object.

> ## Subsidiaries ##
> Functions:
    * [run](Functions#Approximator::run.md)
> Sub-classes:
    * EulerApprox
    * RungaApprox
> Public Variables:
    * radiiHistory
    * curvHistory
    * areaHistory
    * volumeHistory

> ## Description ##
> The `Approximator` class is the shell for running a curvature flow. The class provides the functionality to determine what system of differential equations to use, how to perform a step, and even which values to record for later use. The system is provided by the user at run-time and is defined to be a function that takes in an empty array of `double`s and fills the array with the values calculated in the system of equations. The `Approximator` class is abstract with an abstract method `step`. A class that extends `Approximator` implements `step` with the method of approximation to use (i.e Euler's method). Lastly, the `Approximator` stores values after each `step` of a flow according to which values were requested at construction. Values include radii, curvatures, areas, and volumes. These histories can then be accessed directly from the `Approximator` object.

> ## Constructor ##
> The constructor takes in a function that defines a system of differential equations and a string of characters representing what histories to record. For the function to be a `sysdiffeq` it must not return a value and its only parameter is an array of doubles that will be filled in with values after the function completes. The history string must be nul-terminated and consisting of only valid characters. The valid characters currently are:
    * r - Record radii
    * 2 - Record two-dimensional curvatures
    * 3 - Record three-dimensional curvatures
    * a - Record areas
    * v - Record volumes
> One cannot list both two- and three-dimensional curvatures.
```
  typedef void (*sysdiffeq)(double derivs[]);
  Approximator(sysdiffeq funct, char* histories);
```

> ## Practicum ##
> This example will show how to run a Yamabe curvature flow on the pentachoron using precision and accuracy bounds (see [run](Functions#run.md)) while recording radii, curvatures, and volumes. It will show how to initialize the system and also how to print out results at the end.
```
int main(int argc, char** argv) {
   map<int, Vertex>::iterator vit;
   map<int, Edge>::iterator eit;
   map<int, Face>::iterator fit;
   map<int, Tetra>::iterator tit;
     
   vector<int> edges;
   vector<int> faces;
   vector<int> tetras;
    
    
   time_t start, end;
   
   // File to read in triangulation from.
   char from[] = "./Triangulation Files/3D Manifolds/Lutz Format/pentachoron.txt";
   // File to convert to proper format.
   char to[] = "./Triangulation Files/manifold converted.txt";
   // Convert, then read in triangulation.
   make3DTriangulationFile(from, to);
   read3DTriangulationFile(to);

   int vertSize = Triangulation::vertexTable.size();
   int edgeSize = Triangulation::edgeTable.size();
   
   // Set the radii
   for(int i = 1; i <= vertSize; i++) {
      Radius::At(Triangulation::vertexTable[i])->setValue(1 + (0.5 - i/5.0) );        
   }
   // Set the etas
   for(int i = 1; i <= edgeSize; i++) {
       Eta::At(Triangulation::edgeTable[i])->setValue(1.0);
   }

   // Construct an Approximator object that uses the Euler method and Yamabe flow while
   // recording radii, 3D curvatures, and volumes.
   Approximator *app = new EulerApprox((sysdiffeq) Yamabe, "r3v");

   // Run the Yamabe flow with precision and accuracy bounds of 0.0001 and stepsize of 0.01
   app->run(0.0001, 0.0001, 0.01);

   // Print out radii, curvatures and volumes
   printResultsStep("./Triangulation Files/ODE Result.txt", &(app->radiiHistory), &(app->curvHistory));
   printResultsVolumes("./Triangulation Files/Volumes.txt", &(app->volumeHistory));

   return 0;
}
```