# `NewtonsMethod` #

> ## Key Words ##
> gradient, hessian, extrema

> ## Authors ##
    * Alex Henniges
    * Dan Champion

> ## Introduction ##
> The `NewtonsMethod` class is used to find an extremum of a given functional. In addition to the functional given, the user can provide the gradient or hessian function. If not, these are approximated during run-time.

> ## Subsidiaries ##
> Functions:
    * NewtonsMethod::maximize
    * NewtonsMethod::step
    * NewtonsMethod::setPrintFunc
    * NewtonsMethod::printInfo

> ## Description ##
> As a class, `NewtonsMethod` is used as a general way to perform Newton's method on a function in order to find its extrema. Newton's method will find an extrema much faster than Euler's method, but is also more complicated. In order for Newton's method to work, it requires the first and second-order partial derivatives in addition to the original function. If the user knows these explicitly, they can be passed in the [constructor](#Constructor.md) and should lead to more accurate and possible quicker calculations. If the first and secord-partial derivatives are not provided, then they will be approximated using quotients.

> For our purposes within the `NewtonsMethod` class, the original function is defined to take as a parameter an array of `double`s that represent the values of each variable. The function should return a double. The gradient function is defined to take an array of doubles that also represent the point at which the partial derivatives should be calculated. In addition, it takes another array of doubles for the partial derivatives to be placed in. Lastly, a hessian function is also defined to take an array of doubles for the point at which the calculation is being done. It also takes a two-dimensional array of double for the second-order partial derivatives to be placed in. Both the gradient and hessian function do not return a value.

> ## Constructor ##
> There are three constructors for the `NewtonsMethod` class, allowing for a combination of potential functions that can be given explicitly. In addition, every constructor must be given an integer that indicates the number of variables given to the functional.
```
    typedef double (*orig_function)(double vars[])
    typedef void   (*gradient)(double vars[], double sol[])
    typedef void   (*hessian)(double vars[], double *sol[])

    NewtonsMethod(orig_function func, int numVars)
    NewtonsMethod(orig_function func, gradient df, int numVars)
    NewtonsMethod(orig_function func, gradient df, hessian d2f, int numVars)
```

> ## Practicum ##
> Below is a full example of how to use the `NewtonsMethod` class to find the minimum of an ellipse. In this case, the gradient and hessian are not given. The maximum found is at (0,0).
```
   // This function takes two variables.
   // f(x, y) = (1 - x^/4 - y^2/9) ^(1/2)
   double ellipse(double vars[]) {
       double val = 1 - pow(vars[0], 2) / 4 - pow(vars[1], 2) / 9;
       return sqrt(val);
   }

   int main(int arg, char** argv) {
    // Create the NewtonsMethod object, 2 variables
    NewtonsMethod *nm = new NewtonsMethod(ellipse, 2);
    // Build the array that holds the initial values.
    double initial[] = {0.1, 2.5};
    // Build the array that will hold the final solution.
    double soln[2];
    // Run the maximize function
    nm->maximize(initial, soln);

    // Display the results
    printf("\nSolution: %.10f, %.10f\n", soln[0], soln[1]);

    return 0;
   }
```

> Using the same ellipse, one can use the `step` function instead of `maximize` to gain greater flexibility over the procedure. In this case, we also print out useful information after each step.
```
     // This function takes two variables.
   double ellipse(double vars[]) {
       double val = 1 - pow(vars[0], 2) / 4 - pow(vars[1], 2) / 9;
       return sqrt(val);
   }

   int main(int arg, char** argv) {
    NewtonsMethod *nm = new NewtonsMethod(ellipse, 2);
    double x_n[] = {1, 1};
    int i = 1;
    fprintf(stdout, "Initial\n-----------------\n");
    for(int j = 0; j < 2; j++) {
      fprintf(stdout, "x_n_%d[%d] = %f\n", i, j, x_n[j]);
    }
    // Continue with the procedure until the length of the gradient is 
    // less than 0.000001.
    while(nm->step(x_n) > 0.000001) {
      fprintf(stdout, "\n***** Step %d *****\n", i++);
      nm->printInfo(stdout);
      for(int j = 0; j < 2; j++) {
        fprintf(stdout, "x_n_%d[%d] = %f\n", i, j, x_n[j]);
      }
    }
    printf("\nSolution: %.10f\n", x_n[0]);

    return 0;
   }
```

> In this example, we use a one variable Gaussian function, but provide a gradient and hessian, as well. The maximum is found at `x = 0`.
```
   // The function, e^(-x^2).
   double gaussian(double vars[]) {
       return exp(-pow(vars[0], 2));
   }

   // The gradient function, -2x * e^(-x^2).
   // Note that the solution is placed in the array.
   void gradFunc(double vars[], double sol[]) {
     sol[0] = -2 * vars[0] * func(vars);
   }

   // The hessian function, e^(-x^2)(4x^2 - 2).
   // Note that the solution is placed in a matrix.
   void hessFunc(double vars[], double *sol[]) {
     sol[0][0] = func(vars) * (4 * pow(vars[0], 2) - 2);
   }


   int main(int arg, char** argv) {
    // Create the NewtonsMethod object
    NewtonsMethod *nm = new NewtonsMethod(gaussian, gradFunc, hessFunc, 1);
    // Build the array that holds the initial value.
    double initial[] = {0.1};
    // Build the array that will hold the final solution.
    double soln[1];
    // Run the maximize function
    nm->maximize(initial, soln);

    // Display the results
    printf("\nSolution: %.10f\n", soln[0]);

    return 0;
   }
```

> ## Limitations ##
> There are limitations with the `NewtonsMethod` class with regards to the approximation of the gradient and hessian. In both cases, a delta value for the quotients is hard-coded as 10<sup>-5</sup>. This could lead to accuracy issues when the point where the derivative is being calculated is less than this value. It can also be too accurate at times and lead to unnecessarily slowing down the procedure. One solution could be to provide a function where a delta value is set by the user.

> ## Revisions ##
    * subversion 876, 7/16/09: Added a NewtonsMethod class for general maximizing.

> ## Future Work ##
    * 7/16 - Provide greater flexibility to the user for approximating the gradient and hessian.