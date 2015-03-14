# Overview #

Development of geometric evolutions like combinatorial Ricci/Yamabe flow, geodesic flow on (abstract) piecewise flat 2D/3D manifolds. Partly supported by grant NSF DMS 0748283 (CAREER grant from National Science Foundation).

# Geoquant viewer #

This is a GUI that allows one to read 2D/3D manifolds, read/set geometric data (such as length, circle packing metrics, curvatures), and run certain geometric evolutions (such as combinatorial Ricci/Yamabe flows).

# Development #

This project takes an abstract 2D/3D manifold and unfolds it into the plane/3-space. This is a general source unfolding, modeling the (Riemannian) exponential map and giving a view as if you are inside the manifold and light is moving by geodesics. The program also allows you to navigate through the unfolding (called the "development").

# Notes #
Current versions are all in Java. C++ code is deprecated. Most of the data files have been ported to xml format and can be found in the source directory under Java/Data/Triangulations (.xml) or Java/Data/off (.off/.noff)