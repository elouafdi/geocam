/**************************************************************
File: Triangulation Input/Output
Author: Alex Henniges, Tom Williams, Mitch Wilson
Version: June 20, 2008
***************************************************************
The Triangulation Input/Output file holds the functions that handle
the reading and writing of text files.
**************************************************************/
#include <cstdlib>
#include <iostream>
#include <vector>
#include "simplex/simplex.h"
#include <map>
#include <vector>
#include <algorithm>
#include <set>
#include <fstream>
#include <string>
#include "simplex/vertex.h"
#include <sstream>
#include "triangulation.h"
#include "triangulationmath.h"


/*
 * Function to read in a file and build the Triangulation. The 
 * function parses the file and creates the simplices, adding them to
 * the Triangulation tables. A file should be formatted as below:
 *                
 *               Vertex: 1     <---  Simplex name (Vertex 1)
 *               2 3 4         <---  Local vertices
 *               1 2 3         <---  Local edges
 *               1 2 3         <---  Local faces
 *               Vertex: 2     <---  Next simplex (Vertex 2)
 *               1 3 4
 *               1 4 5
 *               1 2 4
 *               :
 *               :
 *               Edge: 5       <--- Another simplex (Edge 5)
 *               1 2
 *               2 3 4 5
 *               1 2
 *               :
 *               :
                 
          (Note: ":" on its own line is being used to show that the 
 *         file continues in the same style.)       
 *                    
 * A simplex is first declared by its name (Vertex, Edge, or Face)
 * followed by a colon and the integer that represents it in the table.
 * Underneath this are three lines of integers, representing in order,
 * the local vertices, edges, and faces of the simplex. No indication
 * for the end of each line or the declaration of a simplex is needed, but
 * every simplex must have three lines of integers. The current
 * maximum size for a list of local simplices is 24. Finally, note that 
 * not all vertices must be listed before declaring edges or faces and 
 * that they do not need to be in any sort of order but should be from
 * 1 to n. 
 */

bool readTriangulationFile(char* fileName) 
{
    // The three names of simplices.
    const string vertexString("Vertex");
    const string edgeString("Edge");
    const string faceString("Face");
    
    // The file stream.
    ifstream scanner(fileName);
    // Name of the simplex to be read in.
    char simplexName[15];
    
    while(scanner.good()) // While there is another simplex to read in...
    {
       scanner.getline(simplexName, 15, ':'); // ':' is the delimiter.
       if(simplexName == vertexString) 
       {
         int indexMapping;
         scanner >> indexMapping;
         Vertex v(indexMapping);
         
         char ignore[5];
         scanner.getline(ignore, 5); // Used to get to next line.
         
         char localVertices[100];
         char localEdges[100];
         char localFaces[100];
         
         stringstream vertexStream(stringstream::in | stringstream::out);
         scanner.getline(localVertices, 100);
         vertexStream << localVertices;
         
         while(vertexStream.good()) // While there's another int...
         {
              int index;
              vertexStream >> index;
              v.addVertex(index);
         }
         
         stringstream edgeStream(stringstream::in | stringstream::out);
         scanner.getline(localEdges, 100);
         edgeStream << localEdges;
         
          while(edgeStream.good())
         {
              int index;
              edgeStream >> index;
              v.addEdge(index);
         }
         
         stringstream faceStream(stringstream::in | stringstream::out);
         scanner.getline(localFaces, 100);
         faceStream << localFaces;
         
          while(faceStream.good())
         {
              int index;
              faceStream >> index;
              v.addFace(index);
         }
         
         Triangulation::putVertex(indexMapping, v); //Finally add to table. 
       } 
       
       else if(simplexName == edgeString) 
       {
         int indexMapping;
         scanner >> indexMapping;
         Edge e(indexMapping);
         
         char ignore[5];
         scanner.getline(ignore, 5);
         
         char localVertices[100];
         char localEdges[100];
         char localFaces[100];
         
         stringstream vertexStream(stringstream::in | stringstream::out);
         scanner.getline(localVertices, 100);
         vertexStream << localVertices;
         
         while(vertexStream.good())
         {
              int index;
              vertexStream >> index;
              e.addVertex(index);
         }
         
         stringstream edgeStream(stringstream::in | stringstream::out);
         scanner.getline(localEdges, 100);
         edgeStream << localEdges;
         
         while(edgeStream.good())
         {
              int index;
              edgeStream >> index;
              e.addEdge(index);
         }
         
         stringstream faceStream(stringstream::in | stringstream::out);
         scanner.getline(localFaces, 100);
         faceStream << localFaces;
         
         while(faceStream.good())
         {
              int index;
              faceStream >> index;
              e.addFace(index);
         }
         
         Triangulation::putEdge(indexMapping, e); 
       } 
       
       else if(simplexName == faceString) 
       {
         int indexMapping;
         scanner >> indexMapping;
         Face f(indexMapping);
         
         char ignore[5];
         scanner.getline(ignore, 5);
         
         char localVertices[100];
         char localEdges[100];
         char localFaces[100];
         
         stringstream vertexStream(stringstream::in | stringstream::out);
         scanner.getline(localVertices, 100);
         vertexStream << localVertices;
         
         while(vertexStream.good())
         {
              int index;
              vertexStream >> index;
              f.addVertex(index);
         }
         
         stringstream edgeStream(stringstream::in | stringstream::out);
         scanner.getline(localEdges, 100);
         edgeStream << localEdges;
         
          while(edgeStream.good())
         {
              int index;
              edgeStream >> index;
              f.addEdge(index);
         }
         
         stringstream faceStream(stringstream::in | stringstream::out);
         scanner.getline(localFaces, 100);
         faceStream << localFaces;
         
          while(faceStream.good())
         {
              int index;
              faceStream >> index;
              f.addFace(index);
         }
         
         Triangulation::putFace(indexMapping, f);    
       }
       
       else 
       {
            scanner.close();
            return false; // File read unsuccessful.
       }
    } 
    
    scanner.close();
    return true; // File read successfully!
}

void writeTriangulationFile(char* newFileName)
{
     ofstream output(newFileName);
     
     for(int i = 1; i <= Triangulation::vertexTable.size(); i++)
     {
            
             
             output << "Vertex: " << i << "\n";
             
             for(int j = 0; j < Triangulation::vertexTable[i].getLocalVertices()->size(); j++)
             {
                     output << ((*(Triangulation::vertexTable[i].getLocalVertices()))[j]) << " ";
             }
             output << "\n";
             
             for(int j = 0; j < Triangulation::vertexTable[i].getLocalEdges()->size(); j++)
             {
                     output << ((*(Triangulation::vertexTable[i].getLocalEdges()))[j]) << " ";
             }
             output << "\n";
             
             for(int j = 0; j < Triangulation::vertexTable[i].getLocalFaces()->size(); j++)
             {
                     output << ((*(Triangulation::vertexTable[i].getLocalFaces()))[j]) << " ";
                   
             }
             output << "\n";
     }
     
     for(int i = 1; i <= Triangulation::edgeTable.size(); i++)
     {
             output << "Edge: " << Triangulation::edgeTable[i].getIndex() << "\n";
             
             for(int j = 0; j < Triangulation::edgeTable[i].getLocalVertices()->size(); j++)
             {
                     output << ((*(Triangulation::edgeTable[i].getLocalVertices()))[j]) << " ";
             }
             output << "\n";
             
             for(int j = 0; j < Triangulation::edgeTable[i].getLocalEdges()->size(); j++)
             {
                     output << ((*(Triangulation::edgeTable[i].getLocalEdges()))[j]) << " ";
             }
             output << "\n";
             
             for(int j = 0; j < Triangulation::edgeTable[i].getLocalFaces()->size(); j++)
             {
                     output << ((*(Triangulation::edgeTable[i].getLocalFaces()))[j]) << " ";
             }
             output << "\n";
     }
     
     for(int i = 1; i <= Triangulation::faceTable.size(); i++)
     {
             output << "Face: " << i << "\n";
             
             for(int j = 0; j < Triangulation::faceTable[i].getLocalVertices()->size(); j++)
             {
                     output << ((*(Triangulation::faceTable[i].getLocalVertices()))[j]) << " ";
             }
             output << "\n";
             
             for(int j = 0; j < Triangulation::faceTable[i].getLocalEdges()->size(); j++)
             {
                     output << ((*(Triangulation::faceTable[i].getLocalEdges()))[j]) << " ";
             }
             output << "\n";
             
             for(int j = 0; j < Triangulation::faceTable[i].getLocalFaces()->size(); j++)
             {
                     output << ((*(Triangulation::faceTable[i].getLocalFaces()))[j]) << " ";
             }
             output << "\n";
     }
     
}

struct Pair
{
       int v1, v2;
       int positionOf(vector<Pair>*);  
       bool contains(int);
       bool isInTriple(vector<int>*);
};

int Pair::positionOf(vector<Pair>* list)
{
     for (int i = 0; i < (*list).size(); i++)
     {
         Pair other = (*list)[i];
         if ((this->v1 == other.v1 && this-> v2 == other.v2)||(this->v1 == other.v2 && this-> v2 == other.v1))
         {
                return i;    
         }
     }
     return -1;
}

bool Pair::contains(int i)
{
     if (i == v1 || i == v2)
     {
           return true;
     }
     else
         return false;
}
 
bool Pair::isInTriple(vector<int>* triple)
{
     int result = 0;
     for(int i = 0; i < triple->size(); i++)
     {
          if(v1 == (*triple)[i])
          {
                result++;
          }
          if(v2 == (*triple)[i])
          {
                result++;
          }
     }
     return result == 2;
}
 
void makeTriangulationFile (char* from, char* to) {

  ifstream infile;
  vector< vector<int> > f; 
  infile.open(from);
  // >> i/o operations here <<
  char temp[50];
  infile.getline(temp, 50 ,'=');
  char ch;
  infile >> ch;
  while (ch != ']')
        {
            infile >> ch;
            vector<int> face;
            for (int n = 1; n <= 3; n++)
            {
                int element;
                infile >> element;
                face.push_back(element);
                infile >> ch;
            }
            f.push_back(face);
            infile >> ch;
        }
        
  vector<Pair> list; 
  for(int i = 0; i < f.size(); i++)
  {
     Pair p1 = {(f[i])[0], (f[i])[1]};
     Pair p2 = {(f[i])[0], (f[i])[2]};
     Pair p3 = {(f[i])[1], (f[i])[2]};
     if (p1.positionOf(&list) == -1)
     {
         list.push_back(p1);
     }
     if (p2.positionOf(&list) == -1)
     {
         list.push_back(p2);
     }
     if (p3.positionOf(&list) == -1)
     {
         list.push_back(p3);
     }
  }     
  
  vector<int> v;
  vector<int>::iterator it;
  for (int i = 0; i < f.size(); i++)
  {
      for (int j = 0; j < 3; j++)
      {
          it = find(v.begin(), v.end(), (f[i])[j]);
          if (it == v.end())
          {
                 v.push_back((f[i])[j]);
          }
      }
  }
   
  ofstream outfile;
  outfile.open(to, ios_base::trunc);
  
  // Vertices
  for (int i = 0; i < v.size(); i++)
  {
      // name
      outfile << "Vertex: " << v[i] << "\n";
      set<int> localv; 
      vector<int> localf; 
      
      for (int k = 0; k < f.size(); k++)
      {      
          it = find(f[k].begin(), f[k].end(), v[i]);
          if (it != f[k].end())
          {
                 localf.push_back(k+1);
                 for(int g = 0; g < 3; g++)
                 {
                       localv.insert((f[k])[g]);
                 }
          }        
      }
      localv.erase(v[i]);
      set<int>::iterator notit;
      set<int>::iterator end = localv.end();
      end--;
      // local vertices
      for (notit = localv.begin(); notit != end; notit++)
      {
          outfile << *notit << " ";
      }
      outfile << *notit << "\n";    
      // local edges
      for (int j = 0; j < list.size(); j++)
      {
          if (list[j].contains(v[i]))
          {
             outfile << j + 1 << " ";
          }
             
      } 
      outfile << "\n";
      // local faces
      for (int i = 0; i < localf.size(); i++)
      {
          outfile << localf[i] << " ";
      }
      outfile << "\n";
  }
  
  //Edges
  for(int i = 0; i < list.size(); i++)
  {
          // name
          outfile << "Edge: " << i + 1 << "\n";
          Pair current = list[i];
          // local vertices
          outfile << current.v1 << " " << current.v2 << "\n";
          // local edges
          for(int j = 0; j < list.size(); j++)
          {
             if(j != i && 
                (list[j].contains(current.v1) || list[j].contains(current.v2)))
             {
                 outfile << j + 1 << " "; 
             }
          }
          outfile << "\n";
          for(int j = 0; j < f.size(); j++)
          {
                  if(current.isInTriple(&(f[j])))
                  {
                      outfile << j + 1 << " ";
                  }
          }
          outfile << "\n";
  }
  
  //Faces
  for(int i = 0; i < f.size(); i++)
  {
      // name
      outfile << "Face: " << i + 1 << "\n";
      vector<int> current = f[i];
      // local vertices
      for(int j = 0; j < current.size(); j++)
      {
              outfile << current[j] << " ";
      }
      outfile << "\n";
      // local edges
      for(int j = 0; j < list.size(); j++)
      {
              if((list[j]).isInTriple(&current))
              {
                 outfile << j + 1 << " ";
              }
      }
      outfile << "\n";
      for(int j = 0; j < f.size(); j++)
      {
         if( j != i)
         {
            vector<int> inter = listIntersection(&current, &(f[j]));
            if(inter.size() == 2)
            {
               outfile << j + 1 << " ";
            }
         }
      }
      outfile << "\n";   
  }
  
  infile.close(); }
