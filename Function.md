# `Function` #
```
class Function()
```

> ## Keywords ##

> function

> ## Authors ##
> Kurt Norwood

> ## Introduction ##

> Function is intended to represent a mapping of integers to real numbers. The goal in writing the class was to make it fairly versatile in how it reads in numbers, and to allow the user to give an incomplete set of values which would then be expanded by this class when more values are needed. The class can take an appropriately fomratted file and create a map<int, double> which represents the contents of that file. It can also add to this file when necessary, and be given a custom function which allows it to compute new values to append to an incomplete set of data.
> The motivation for writing this class was for cleaning up the `dirichletEnergy` function.

> ## Subsidiaries ##

> Functions:
```
    Function(char* fileName, char* nameSpecifier, vector<int> indexList);
    Function(char* fileName, char* nameSpecifier, vector<int> indexList, bool append);
    Function(char* fileName, char* nameSpecifier, vector<int> indexList, bool append, double (*f)(int));

    ~Function(void);

    double valueOf(int index);
    double valueOf(Simplex s);
```

> Global Variables:

> Local Variables:
```
    map<int, double> func - the mapping of integers to doubles
    double (*customFunc)(int) - a custom function used for expanding the data set
    bool customFuncGiven - flag
    char* varName - the string that will be used to indicate a parse-able line in the file
    char* file - the name of the file to be parsed
    bool appendMoreValues - flag indicating whether the new entries should be added to the file or if an error should occur
```

> ## Description ##

> The class Function takes a `fileName`, a `nameSpecifier`, and an `indexList` for its constructor. It optionally takes a boolean flag and a function pointer. Upon construction of a Function object, the file specified by `fileName` will be opened and parsed based on the `nameSpecifier`. The expected format of lines containing `nameSpecifier` is:

> nameSpecifier: int double

> The int and double are used as the key and value pair in the `map<int, double> func` class variable. The keys of `func` are compared to the entries of `vector<int> indexList`, is an entry does not exist a few different things can happen depending on what the `bool append` flag is, and whether a custom function was specified. Basically if `append` was given as false, an error occurs and the user will be notified at the console to change their input file. Otherwise, new entries are added to `fileName`, which would be random numbers between 0 and 1 by default, or the value returned by the custom function if one was given.

> Once the object is built, it can then be used to access the data stored in `map<int, double> func` with the public method `double valueOf(int index)`.

> ## Practicum ##


> ## Limitations ##


> ## Revisions ##


> ## Testing ##


> ## Future Work ##