/**************************************************************
Class: Face
Author: Alex Henniges, Tom Williams, Mitch Wilson
Version: June 9, 2008
**************************************************************/

#include "face.h" // class's header file
#include "edge.h"

// class constructor
Face::Face() : Simplex(){
  negative = false;
}

// class destructor
Face::~Face(){}

void Face::switchSide() {
  negative = !negative;
}

void Face::setNegativity(bool negativity) {
  negative = negativity;
}

bool Face::isNegative(){
  return negative;
}
