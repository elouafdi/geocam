# Working with a "memoized-pipeline" data structure #

## Key Words ##
geometry, memoized-pipeline, extending, modifying, data structure, geoquant, quantities, singleton, observer, observable

## Authors ##
  * Alex Henniges
  * Joseph Thomas

## Introduction ##
The memoized-pipeline is a data structure we developed for investigating geometries defined on triangulations. It is particularly suited to the situation in which we need to specify the values of some geometric quantities (independent variables) and then need to rapidly calculate the values of some other quantities (the dependent variables). Basically, we achieve this speedup by trading space for time. Usually, the definitions of the dependent variables have many intermediate values in common. By saving these values the first time we compute them, and then reusing them later, we can avoid a lot of useless recalculation. This strategy of saving calculated values, which can be found in most algorithms textbooks, is called "memoization."

In implementing various geometries, we have already developed code and techniques for making memoization an automatic part of encoding a geometry. In this tutorial, we describe how to take advantage of this existing code.

## Implementation Details ##
The underlying implementation of the pipeline is designed to solve two problems in a fairly user-friendly way:
  1. We would like to be able to identify geometric quantities with positions on the triangulation. For example, we can speak of the dihedral angle associated with a particular edge on a tetrahedron. We would like to be able to write code in the same way.
  1. We would like memoization to be nearly automatic. In other words, when writing a particular quantity, the programmer shouldn't have to think much about what happens to memoize that quantity's value.

Taking the programmer's perspective, we can view quantities as being specified by 3 pieces of information:
  1. A position on the triangulation.
  1. A definition of the other quantities (if any) needed to calculate the value of the current quantity, and where those quantities can be found on the triangulation.
  1. A formula for calculating a quantity's value, given the values of the other quantities it depends on.

Usually, specifying just these 3 pieces of information is enough to create a new type of quantity. To help speed the development of quantities, we have developed a Ruby script, `makeQuantity.rb`, that generates much of the source code. This can be invoked at the command line as follows:
```
> ruby makeQuantity.rb [quantity]
```
This produces two files, `[quantity].h` and `[quantity].cpp`.

### The "anatomy" of `quantity.h` ###
In `C++`, header files serve several purposes. Among other uses, a header file can:
  * Specify dependencies on other parts of the project.
  * Define an interface for other parts of your project to use. This includes:
    * Definitions for new data-types (like classes).
    * Definitions for procedure calls (what arguments a procedure takes, and what it returns).
By default, `makeQuantity.rb` gives you the following header file to use (here, we chose `quantity/QUANTITY` as the quantity name, in practice, this is filled out by the script).
```
#ifndef QUANTITY_H_
#define QUANTITY_H_

#include "geoquant.h"

/******************REGION 1*******************
 * This is where you load the headers of the *
 * quantities you require.                   *
 *********************************************/

class quantity : public virtual GeoQuant {
protected:
  quantity( SIMPLICES );
  void recalculate();
  /****************REGION 2*********************
   * The quantity references you need go here. *
   *********************************************/

public:
  ~quantity();
  static quantity* At( SIMPLICES );
  static void CleanUp();
};
#endif /* QUANTITY_H_ */
```

The two important areas of the header are labeled `REGION 1` and `REGION 2`. In region 1, you specify the header files for the quantities and utilities you use in the rest of your quantity. These `#include` statements can be thought of as providing definitions for the data and procedures you want to use in building your quantity. In region 2, you specify the data associated with a given instance of the quantity; typically this amounts to several references to other quantities, or a data structure that manages references to other quantities. Lastly, you will need to modify the region tagged `SIMPLICES` so that it reflects a collection of simplices that describe your quantity's position on the triangulation.

### The "anatomy" of `quantity.cpp` ###

In general, a `.cpp` file provides the internal implementation to support the operations described in the corresponding header file. Editing this file will be a little more complicated. By default, `makeQuantity.rb` will produce the following `.cpp` file:
```
#include "quantity.h"

#include <map>
#include <new>
using namespace std;

#define map<TriPosition, quantity*, TriPositionCompare> quantityIndex 
static quantityIndex* Index = NULL;

quantity::quantity( SIMPLICES ){
  /* REGION 1 */
}

quantity::~quantity(){
  /* REGION 2 */
}

void quantity::recalculate(){
  /* REGION 3 */
}

quantity* quantity::At( SIMPLICES ){
  TriPosition T( NUMSIMPLICES, SIMPLICES );
  if( Index == NULL ) Index = new quantityIndex();
  quantityIndex::iterator iter = Index->find( T );

  if( iter == Index->end() ){
    quantity* val = new quantity( SIMPLICES );
    Index->insert( make_pair( T, val ) );
    return val;
  } else {
    return iter->second;
  }
}

void quantity::CleanUp(){
  if( Index == NULL ) return;
  quantityIndex::iterator iter;
  for(iter = Index->begin(); iter != Index->end(); iter++)
    delete iter->second;
  delete Index;
}
```

There are a few smaller areas to fill out, but in general defining the quantity requires the following three definitions:
  * Region 1 specifies how to obtain references on the quantities your quantity depends on. Typically, this will involve using the input simplex information and some utilities for inspecting the triangulation to look up the quantities needed for later calculations.
  * Region 2 specifies how to release any data structures built up using dynamic memory. In many cases, this field will be left blank.
  * Region 3 specifies how to calculate the value of an instance of the quantity. Typically, this will occur in two steps: First, using the quantity references obtained in region 1, we acquire the current values of the quantities used in the calculation. Second, using a formula and the values found in step 1, we calculate the value of the current quantity.

## An Extended Example ##
Perhaps the easiest way to understand the system is by examining a few working quantities. In this example, we consider the code written to represent the "Length" quantity discussed in ["Discrete conformal variations and scalar curvature on piecewise flat two and three dimensional manifolds"](http://arxiv.org/abs/0906.1560) (in the paper, this quantity is also called l<sub>ij</sub>). We assign length quantities to the edges of the triangulation; for a given edge, we can determine its length as a function of the radii assigned to the edge's end vertices and the inversive distance assigned to the edge (this is also discussed in the paper).

The header file for length.h is as follows:
```
#ifndef LENGTH_H_
#define LENGTH_H_

#include <cmath>
#include <map>
#include <new>
using namespace std;

#include "geoquant.h"
#include "triposition.h"
#include "triangulation.h"

#include "radius.h"
#include "eta.h"

class Length : public virtual GeoQuant {
 private:
  Radius* radius1;  // To calculate the length of an edge, 
  Radius* radius2;  // we require references to three 
  Eta* eta;         // quantities associated with the edge.

protected:
  Length( Edge& e ); 
  void recalculate();

public:
  ~Length();
  static Length* At( Edge& e );
};

#endif /* LENGTH_H_ */
```


```
#include "length.h"
#include <stdio.h>

typedef map<TriPosition, Length*, TriPositionCompare> LengthIndex;
static LengthIndex* Index = NULL;

Length::Length( Edge& e ){
  // Step #1: Acquire additional topological information about the edge.
  Vertex& v1 = Triangulation::vertexTable[ (*(e.getLocalVertices()))[0] ];
  Vertex& v2 = Triangulation::vertexTable[ (*(e.getLocalVertices()))[1] ];

  // Step #2: Use the topological information to locate other quantities. 
  radius1 = Radius::At( v1 );  
  radius2 = Radius::At( v2 );
  eta = Eta::At( e );
  
  // Step #3: Inform the quantities that they have a new dependent.
  radius1->addDependent( this );
  radius2->addDependent( this );
  eta->addDependent( this );
}

void Length::recalculate(){
  // Step #1: Acquire the current values from our quantity references.
  double r1 = radius1->getValue();
  double r2 = radius2->getValue();
  double etaV = eta->getValue();

  // Step #2: Use the values and a formula to calculate the current
  // value of the quantity.
  value = sqrt( r1*r1 + r2*r2 + 2*r1*r2*etaV );   
}

// We didn't allocate any additional memory to construct
// this quantity, so the destructor can be left blank.
Length::~Length(){} 

// This procedure is responsible for making length
// construction "idempotent." That is, once a Length
// quantity is constructed for a given edge, it
// should be the only Length quantity assigned to that
// edge. In addition, all calls for the length
// associated with a given edge should receive a
// reference to the same quantity.

Length* Length::At( Edge& e ){
  TriPosition T( 1, e.getSerialNumber() );
  if( Index == NULL ) Index = new LengthIndex();
  LengthIndex::iterator iter = Index->find( T );

  if( iter == Index->end() ){
    Length* val = new Length( e );
    val->pos = T;
    Index->insert( make_pair( T, val ) );
    return val;
  } else {
    return iter->second;
  }
}
```

## Consequences of Our Design ##
You may have noticed that in the example above, the constructor for `Length` objects is private. This is intentional; in general, when you are using your quantities, you should obtain them through the `At` procedure. You never need to explicitly construct a quantity---instead the `At` quantity will construct quantities as needed. This turns out to be very convenient, because there may be many quantities of a given type on a triangulation; explicitly describing how to initialize them all would be tedious and error-prone.

Another intentional feature of our design is that each quantity is given by a header and source file. Why not have one massive file describing all the quantities in the geometry? We decided to spare fellow programmers the trouble of having to cut up an existing geometry file when they want to reuse code in creating new geometries. Currently, if a programmer wants to build a new geometry using some existing quantities, he needs only to write the new quantities he needs, and `#include` the appropriate header files from existing quantities. The resulting quantity object code can be bundled together using a static-library creation tool like `ar` to create a "geometry library" (like `geometry.a`) that other parts of the project can compile against to create the final executable.

## Common Mistakes ##
A common mistake is to forget to call `addDependent` for each of the quantities your quantity references. If you observe that within a given run of your program the pipeline works correctly initially but is incorrect in later iterations, it is likely that some quantity in the pipeline has failed to call `addDependent` (and hence isn't ever being updated).

## Fancier Techniques ##
One detail to note is that you are not obliged to use the "Map-TriPosition" construction to identify quantity objects with positions on the triangulation. For example, suppose you wish to create a quantity `TotalVolume` that describes the total volume of the triangulation. Since there is only one `TotalVolume` for the triangulation, we could substitute the standard mapping of tripositions to quantities with a single `TotalVolume` pointer. The first time the quantity is requested, it is constructed and the pointer is directed to the new object. On later requests for the quantity, we simply return the pointer.

The data structure you select has an impact on the run-time of the `At` procedure for the quantity, and also on the ease with which your code can be understood. For example, since TriPositions are unordered tuples of simplicies, selecting these as key values for a quantity most easily parameterized by ordered pairs of vertices might be a bad choice. Instead, you could use the standard `C++` "pair" data type along with the standard `C++` map to store your quantities.

In working on our geometry-code for studying the Einstein-Hilbert-Regge functional, we found that the `dot` utility (http://www.graphviz.org/) provided a very nice tool for documenting how quantities depend on one another. It was also very useful for debugging the geometry. Basically, `dot` provides a way to draw a nice-looking directed graph given a text file that describes the nodes and edges of the graph (these look like `"Node A" -> "Node B"`. Learning how to use `dot` is fairly easy and well worth the effort.

## Limitations, Areas to Improve ##
Currently, many of our quantities use our "simplex-labeling" code in order to determine topological information about the triangulation (this is often necessary for a quantity to find the other quantities it depends on). Consequently, any topological assumptions the "simplex-labeling" code makes about the triangulation also hold for our geometry code.

In the future, it might be nice to have a tool that converts a graphical description of a "memoized-pipeline" (for example, the source code for a `dot` graph) and creates all the necessary quantity templates for coding up that geometry. This would require a fair amount of clever parsing, but it might speed up the task of writing a given geometry.

A problem that has come up frequently in debugging is the need to iterate over all of the quantities of a particular type (say, to inspect their values). This is a little hard to support currently, since we don't have a specified data structure for storing quantities of a given type. If someone could devise an iterator that supplied quantities as (key, value) pairs (key being a position on the triangulation, value being a quantity), we suspect that feature would be widely used.