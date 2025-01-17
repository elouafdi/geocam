#include "volume_partial_sum.h"

#include <stdio.h>

typedef map<TriPosition, VolumePartialSum*, TriPositionCompare> VolumePartialSumIndex;
static VolumePartialSumIndex* Index = NULL;

VolumePartialSum::VolumePartialSum( Vertex& v ){
  volPartials = new vector<VolumePartial*>();
  vector<int>* localTetras = v.getLocalTetras();
  
  int tetraIndex; 
  VolumePartial* vp;
  
  for(int ii = 0; ii < localTetras->size(); ii++){
    tetraIndex = localTetras->at(ii);
    Tetra& t = Triangulation::tetraTable[ tetraIndex ];
    vp = VolumePartial::At( v, t );
    vp->addDependent( this );
    volPartials->push_back( vp );
  }
}

void VolumePartialSum::recalculate(){
  value = 0.0;
  VolumePartial* vp;
  for(int ii = 0; ii < volPartials->size(); ii++){
    vp = volPartials->at(ii);
    value += vp->getValue();
  }
}

VolumePartialSum::~VolumePartialSum(){

}

void VolumePartialSum::remove() {
     deleteDependents();
     for(int ii = 0; ii < volPartials->size(); ii++){
        volPartials->at(ii)->removeDependent(this);
     }
     Index->erase(pos);
     delete volPartials;
     delete this;
}

VolumePartialSum* VolumePartialSum::At( Vertex& v ){
  TriPosition T( 1, v.getSerialNumber() );
  if( Index == NULL ) Index = new VolumePartialSumIndex();
  VolumePartialSumIndex::iterator iter = Index->find( T );

  if( iter == Index->end() ){
    VolumePartialSum* val = new VolumePartialSum( v );
    val->pos = T;
    Index->insert( make_pair( T, val ) );
    return val;
  } else {
    return iter->second;
  }
}

void VolumePartialSum::CleanUp(){
  if( Index == NULL ) return;
  VolumePartialSumIndex::iterator iter;
  VolumePartialSumIndex copy = *Index;
  for(iter = copy.begin(); iter != copy.end(); iter++) {
    iter->second->remove();
  }
    
  delete Index;
  Index = NULL;
}



