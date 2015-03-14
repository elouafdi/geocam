# `pause` #
```
   void pause();
   void pause(char *fmt, ...);
```

> ## Key Words ##
> pause, print

> ## Authors ##
> Alex Henniges

> ## Introduction ##
> The `pause` freezes the current process until the user presses the **enter** key. This function also allows the user to print information at the pause line.

> ## Subsidiaries ##
> Functions:
    * `vprintf`
    * `scanf`
    * `fflush`

> Global Variables:

> Local Variables:

> ## Description ##
> The `pause` function is designed to place break points in the code that will stop the process until the user presses the enter key. There are several uses to this. A standard one is debugging as it can allow a programmer to step through a procedure. While there are usually similar debugging options in code editors, this function can be added and removed easily from within the code. The second use is that the console for programs will close immediately after execution with some editors. Without a way to freeze the program, the console would close before the data could be read and interpreted.

> There are two options for this `pause` function. If the default pause is used, the following message will be printed:
> > "PAUSE..."

> Pressing the **enter** key will resume the process. The function can also print out a message provided to it. This uses the `vprintf` function so that the printed information can be formatted text. The user must still press **enter** to resume when this form is used. Pressing other keys will not affect the program.

> Historically, the project has used
```
  system("PAUSE");
```
> to pause the program. However, this can only be used on a Windows machine, a limiting factor that we wish to remove from the project.

> ## Practicum ##
> Example:
```
  pause("Done...press enter to exit."); // PAUSE
```

> ## Limitations ##
> One limitation of the `pause` function is that it only resumes after pressing the **enter** key. This is compared to the former pause function (see above) that would resume after pressing any key. This could also be considered an improvement.

> ## Revisions ##
    * subversion 909, 8/4/09: Added the fully functional `pause` function.

> ## Testing ##
> The `pause` function has been tested simply through using it extensively.

> ## Future Work ##
> No future work is planned at this time.