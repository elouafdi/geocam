/**************************************************************
Class: Edge
Author: Alex Henniges, Tom Williams, Mitch Wilson
Version: July 16, 2008
**************************************************************/
#ifndef EDGE_H
#define EDGE_H

#include "simplex.h"
#include <map>

/*
 * The Edge class is derived from the Simplex class. It is the
 * one-dimensional simplex. Every edge also has a length, which
 * is calculated based on the radii of its two local vertices.
 * Therefore, every length is initialized and then either set to
 * a length specified by the user or calculated from the radii.
 */
class Edge : public Simplex {
 public:
  Edge();
  Edge(int setIndex);
  ~Edge();
   
  bool isBorder();
};

#endif // EDGE_H
