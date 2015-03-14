# `Approximator::run` #
```
int run(int numsteps, double stepsize)	
int run(double precision, double stepsize)
int run(double precision, int maxNumSteps, double stepsize)
```

> ## Keywords ##
> flow, curvature, stepsize, precision, accuracy, approximator

> ## Authors ##
    * Joseph Thomas
    * Alex Henniges

> ## Introduction ##
> The `run` function of the [Approximator](Approximator.md) class runs a system of differential equations representing a curvature flow for either a number of steps or until the values are within a desired precision. The system to use and how steps are performed is given in the constructor of the approximator. The type of run is based on the parameters given. The `run` function returns 0 on success or any other number if an error is encountered.


> ## Subsidiaries ##
> Functions:
    * `Approximator::step`
    * `Approximator::isPrecise`
    * `Approximator::isAccurate`
    * `Approximator::getLatest`
      1. `Approximator::recordState`

> Global Variables: `radii`, `curvatures`

> Local Variables: `int numsteps`, `double stepsize`, `double precision`, `double accuracy`

> ## Description ##
> If the `run` function is given a number of steps, it will call its step function that number of times. In between steps, the `run` function will record the current state of any values that have been requested to be recorded (this is specified in the constructor).

> If the `run` function is given a precision, it will continue to call its step function until the desired quantities (curvature in two dimensions and curvature divide by radius in three dimensions) have converged within the precision bounds. Precision is defined to be the difference between subsequent values of a quantity. Therefore, precision is a measure of how much a value is changing. In between steps, the `run` function will record the current state of any values that have been requested to be recorded (this is specified in the constructor).

> A flow can also be run with a precision and a max number of steps that will stop once one of the conditions is reached. The last parameter of any run indicates the step size of the flow. A lower step size will lead to more accurate steps, but a longer time to convergence.

> The `run` function and the overarching [Approximator](Approximator.md) class exists as an improvement over the curvature flows of earlier versions of the Geocam project. The `run` function provides the skeleton that is similar for all types of curvature flows. Beyond the constructor, this should be the only thing a user calls from the [Approximator](Approximator.md) class.

> ## Practicum ##
> Example:
```
// Create an approximator that uses the Euler method on a Yamabe flow.
Approximator *app = new EulerApprox(Yamabe);

// Run a Yamabe flow for 300 steps with a stepsize of 0.01.
app->run(300, 0.01);
// Run with a precision bound of 0.000001 and a stepsize of 0.01
app->run(0.000001, 0.01);
```

> ## Limitations ##
> The `run` function is limited in the systems of differential equations that it can run. It is designed to run with curvature flows and, when precision is used, expects the values to converge. If a precision run is performed on a flow that does not converge, the `run` function will not stop. If a new curvature flow is created whose convergence is not the usual (as in curvature divided by radius in Yamabe flow) then the `run` function will have to be modified to accommodate for this.

> ## Revisions ##
    * subversion 659, 5/1/09: Initial `run` function uploaded to the code.
    * subversion 679, 6/3/09: `run` function modified to work with new Geometry structure.
    * subversion 761, 6/12/09: `run` function modified to work with new quantity structure.
    * subversion 787, 6/18/09: Added new `run` options to approximator. Removed accuracy. Checks for bad numbers.

> ## Testing ##
> The function was tested by performing two and three dimensional flows on familiar triangulations. The start and end values for radii and curvature was then compared with our expected values. The expected values were obtained from the earlier curvature flows we had (see [Description](#Description.md) above). We also checked that the end values were within the precision and accuracy bounds when they were in effect.

> ## Future Work ##
    * 6/17 - ~~Add more run options (ex. precision and maxNumSteps).~~ **Complete (6/18)**
    * 6/17 - ~~Have a run stop the moment an undefined number appears.~~ **Complete (6/18)**

> No future work is planned at this time.