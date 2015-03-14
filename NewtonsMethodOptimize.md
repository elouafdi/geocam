# `NewtonsMethod::optimize` #
```
void optimize(double initial[], double soln[])
```

> ## Keywords ##
> Newtons Method, optimize, extremum, gradient, hessian

> ## Authors ##
    * Alex Henniges

> ## Introduction ##
> The `optimize` function of the [NewtonsMethod](NewtonsMethod.md) class is designed to find either a maximum or minimum of a functional near a given point.

> ## Subsidiaries ##
> Functions:
    * `NewtonsMethod::step`

> ## Description ##
> The `optimize` function is called once by the user and it will continue to loop until an extremum of the functional is found. The functional is given in the constructor for [NewtonsMethod](NewtonsMethod.md). The initial point is the first parameter of the `optimize` function and the solution point is placed in the second parameter. This means that if one cannot be found, the function will loop without end. This is unlike the `step` function used within `optimize` that can also be used by a client program to gain much greater flexibility in the optimization process, such as more leeway on when to stop and allowing for data collection in between. See the `step` function for a description of how the optimization is performed.

> ## Practicum ##
> Example:
```
    double func(double vars[]) {
       double val = 1 - pow(vars[0], 2) / 4 - pow(vars[1], 2) / 9;
       return sqrt(val);
    }
    
    NewtonsMethod *nm = new NewtonsMethod(func, 2);
    double initial[] = {1, 1};
    double soln[2];

    nm->optimize(initial, soln);
```

> ## Limitations ##
> The `optimize` function is limited in its termination condition. This must be a constant over any use of the `optimize` function. It is also limited in that it may not terminate at all and the user will be forced to quit the program. Instead of modifying the function, these limitations are addressed by the `step` function which trades simplicity in terms of number of lines for greater flexibility.

> ## Revisions ##
    * subversion 876 7/16/09: Added a NewtonsMethod class for general maximizing.
    * subversion 906 8/3/09: Changed the name of the function maximize to optimize in the NewtonsMethod class.

> ## Testing ##
> Newtons Method has been tested using several functions of 1 or 2 variables including the Gaussian function. It has been tested with both approximating the gradient and hessian and when both are given explicitly.

> ## Future Work ##
    * 8/4 - Add the ability to only move partially in the direction of the gradient.