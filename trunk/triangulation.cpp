// Class automatically generated by Dev-C++ New Class wizard

#include "triangulation.h" // class's header file
#include <cstdlib>
#include <map>
#include "triangulationmath.h"
#include <algorithm>
#include <vector>
#include <iostream>

map<int, Vertex> Triangulation::vertexTable;
map<int, Edge> Triangulation::edgeTable;
map<int, Face> Triangulation::faceTable;

// class constructor
Triangulation::Triangulation()
{
	// insert your code here
}

// class destructor
Triangulation::~Triangulation()
{
	// insert your code here
}

void Triangulation::putVertex(int key, Vertex v)
{
     vertexTable.insert(pair<int, Vertex>(key, v));
}

void Triangulation::putEdge(int key, Edge v)
{
     edgeTable.insert(pair<int, Edge>(key, v));
}

void Triangulation::putFace(int key, Face v)
{
     faceTable.insert(pair<int, Face>(key, v));
}

void Triangulation::eraseVertex(int key)
{
     if(key == vertexTable.size())
     {
            vertexTable.erase(key);
            return;
     }
     int switchKey = vertexTable.size();
     map<int, Vertex>::iterator vit;
     for(vit = vertexTable.begin(); vit != vertexTable.end(); vit++)
     {
             vector<int> localV = *((*vit).second.getLocalVertices());
             vector<int>::iterator it;
             it = find(localV.begin(), localV.end(), switchKey);
             if(it != localV.end())
             {
                   (*vit).second.addVertex(key);
                   (*vit).second.removeVertex(switchKey);
             }
     }
     map<int, Edge>::iterator eit;
     for(eit = edgeTable.begin(); eit != edgeTable.end(); eit++)
     {
             vector<int> localV = *((*eit).second.getLocalVertices());
             vector<int>::iterator it;
             it = find(localV.begin(), localV.end(), switchKey);
             if(it != localV.end())
             {
                   (*eit).second.addVertex(key);
                   (*eit).second.removeVertex(switchKey);
             }
     }
     map<int, Face>::iterator fit;
     for(fit = faceTable.begin(); fit != faceTable.end(); fit++)
     {
             vector<int> localV = *((*fit).second.getLocalVertices());
             vector<int>::iterator it;
             it = find(localV.begin(), localV.end(), switchKey);
             if(it != localV.end())
             {
                   (*fit).second.addVertex(key);
                   (*fit).second.removeVertex(switchKey);
             }
     }
     vertexTable.erase(key);
     Vertex switchV = vertexTable[switchKey];
     switchV.setIndex(key);
     vertexTable.erase(switchKey);
     vertexTable.insert(pair<int, Vertex>(key, switchV));
     
}

void Triangulation::eraseEdge(int key)
{
      if(key == edgeTable.size())
     {
            edgeTable.erase(key);
            return;
     }
     int switchKey = edgeTable.size();
     map<int, Vertex>::iterator vit;
     for(vit = vertexTable.begin(); vit != vertexTable.end(); vit++)
     {
             vector<int> localE = *((*vit).second.getLocalEdges());
             vector<int>::iterator it;
             it = find(localE.begin(), localE.end(), switchKey);
             if(it != localE.end())
             {
                   (*vit).second.addEdge(key);
                   (*vit).second.removeEdge(switchKey);
             }
     }
     map<int, Edge>::iterator eit;
     for(eit = edgeTable.begin(); eit != edgeTable.end(); eit++)
     {
             vector<int> localE = *((*eit).second.getLocalEdges());
             vector<int>::iterator it;
             it = find(localE.begin(), localE.end(), switchKey);
             if(it != localE.end())
             {
                   (*eit).second.addEdge(key);
                   (*eit).second.removeEdge(switchKey);
             }
     }
     map<int, Face>::iterator fit;
     for(fit = faceTable.begin(); fit != faceTable.end(); fit++)
     {
             vector<int> localE = *((*fit).second.getLocalEdges());
             vector<int>::iterator it;
             it = find(localE.begin(), localE.end(), switchKey);
             if(it != localE.end())
             {
                   (*fit).second.addEdge(key);
                   (*fit).second.removeEdge(switchKey);
             }
     }
     edgeTable.erase(key);
     Edge switchE = edgeTable[switchKey];
     switchE.setIndex(key);
     edgeTable.erase(switchKey);
     edgeTable.insert(pair<int, Edge>(key, switchE));
     
}

void Triangulation::eraseFace(int key)
{
         if(key == faceTable.size())
     {
            faceTable.erase(key);
            return;
     }

     int switchKey = faceTable.size();
     map<int, Vertex>::iterator vit;
     for(vit = vertexTable.begin(); vit != vertexTable.end(); vit++)
     {
             vector<int> localF = *((*vit).second.getLocalFaces());
             vector<int>::iterator it;
             it = find(localF.begin(), localF.end(), switchKey);
             if(it != localF.end())
             {
                   (*vit).second.addFace(key);
                   (*vit).second.removeFace(switchKey);
             }
     }
     map<int, Edge>::iterator eit;
     for(eit = edgeTable.begin(); eit != edgeTable.end(); eit++)
     {
             vector<int> localF = *((*eit).second.getLocalFaces());
             vector<int>::iterator it;
             it = find(localF.begin(), localF.end(), switchKey);
             if(it != localF.end())
             {
                   (*eit).second.addFace(key);
                   (*eit).second.removeFace(switchKey);
             }
     }
     map<int, Face>::iterator fit;
     for(fit = faceTable.begin(); fit != faceTable.end(); fit++)
     {
             vector<int> localF = *((*fit).second.getLocalFaces());
             vector<int>::iterator it;
             it = find(localF.begin(), localF.end(), switchKey);
             if(it != localF.end())
             {
                   (*fit).second.addFace(key);
                   (*fit).second.removeFace(switchKey);
             }
     }
     faceTable.erase(key);
     Face switchF = faceTable[switchKey];
     switchF.setIndex(key);
     faceTable.erase(switchKey);
     faceTable.insert(pair<int, Face>(key, switchF));
     
}

double Triangulation::netCurvature()
{
       double net;
       map<int, Vertex>::iterator it;
       for(it = vertexTable.begin(); it != vertexTable.end(); it++)
       {
              net += curvature((*it).second);
       }
       return net;
}
