#include "resources.h"
#include "triangulation.h"
#include "triangulationmath.h"
#include "spherical/sphericalmath.h"
#include "hyperbolic/hyperbolicmath.h"
#include "triangulationInputOutput.h"

class TriangulationModel 
{
      static bool loaded;
      static vector<double> weights;
      static vector<double> curvs;
      static int numSteps;
      static double stepSize;
      static bool flow;
      public:
      
      TriangulationModel();
      ~TriangulationModel();
      static void clearSystem();
      static void clearData();
      static void setNumSteps(int);
      static void setStepSize(double);
      static void setFlowFunction(bool);
      static bool runCalcFlow(double*, int);
      static bool loadFile(char*, int);
      static bool saveFile(char*);
      static bool printResults(int);
      static bool isLoaded();
       
};