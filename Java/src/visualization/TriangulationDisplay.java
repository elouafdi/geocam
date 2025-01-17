package visualization;

import java.util.Hashtable;

import triangulation.Face;
import triangulation.Triangulation;
import triangulation.Vertex;
import de.jreality.geometry.IndexedFaceSetFactory;
import de.jreality.plugin.JRViewer;
import de.jreality.scene.IndexedFaceSet;
import de.jreality.scene.Viewer;
import de.jreality.scene.data.Attribute;
import de.jreality.scene.data.StorageModel;


public class TriangulationDisplay {
  
static IndexedFaceSetFactory ifsf;
static  Viewer viewer;

  public static void showTriangulation() {
    
    //generate the coordinates
    PlanarDevelopment pd = new PlanarDevelopment();
    pd.generatePlane();
    
    //Need to make a mapping from Vertices to 0 through n where n is the number of vertices
    Hashtable<Vertex, Integer> labeling = new Hashtable<Vertex, Integer>();
    int index = 0;
    for (Vertex vertex : Triangulation.vertexTable.values()) {
      labeling.put(vertex, index);
      index += 1;
    }
    int numVertices = index;
    double[][] vertCoords = new double[numVertices][3];
    for (Vertex vertex : Triangulation.vertexTable.values()) {
      System.out.println(vertex);
      Point point =  pd.getPoint(vertex);
      System.out.println(point);
      vertCoords[labeling.get(vertex)][0] = point.x;
      vertCoords[labeling.get(vertex)][1] = point.y;
      vertCoords[labeling.get(vertex)][2] = 0;
    }
    
    int numFaces = Triangulation.faceTable.size();
    int[][] combinatorics = new int[numFaces][3];
    int faceIndex = 0; //keeps track of which face we're on
    //note that it doesn't matter if this index relates to the face's index in any way
    for (Face embeddedFace : Triangulation.faceTable.values()) {
      int vertIndex = 0;
      for (Vertex vertex : embeddedFace.getLocalVertices()) {
        combinatorics[faceIndex][vertIndex] = labeling.get(vertex);
        vertIndex += 1;
      }
      faceIndex += 1;
    }
    
    //
    ifsf = new IndexedFaceSetFactory();
    ifsf.setVertexCount(Triangulation.vertexTable.keySet().size());
    ifsf.setFaceCount(Triangulation.faceTable.keySet().size());
    ifsf.setVertexCoordinates(vertCoords);
    ifsf.setFaceIndices(combinatorics);
    ifsf.setGenerateEdgesFromFaces(true);
    ifsf.setGenerateFaceNormals(true);
    ifsf.update();
    
    int numEdges = Triangulation.edgeTable.keySet().size();
    double[][] colors = new double[numEdges][3];
    
    for (int i = 0; i < numEdges; i++) {
      colors[i][0] = 1;
      colors[i][1] = 0;
      colors[i][2] = 0;
    }
    
    IndexedFaceSet ifs = ifsf.getIndexedFaceSet();
    ifs.setEdgeAttributes(Attribute.COLORS, StorageModel.DOUBLE_ARRAY.array(3).createReadOnly(colors));

    viewer = JRViewer.display(ifs);
  }
  
  public static void updateDisplay() {
    
    PlanarDevelopment pd = new PlanarDevelopment();
    pd.generatePlane();
    
    Hashtable<Vertex, Integer> labeling = new Hashtable<Vertex, Integer>();
    int index = 0;
    for (Vertex vertex : Triangulation.vertexTable.values()) {
      labeling.put(vertex, index);
      index += 1;
    }
    
    int numVertices = index;
    double[][] vertCoords = new double[numVertices][3];
    for (Vertex vertex : Triangulation.vertexTable.values()) {
      Point point =  pd.getPoint(vertex);
      vertCoords[labeling.get(vertex)][0] = point.x;
      vertCoords[labeling.get(vertex)][1] = point.y;
      vertCoords[labeling.get(vertex)][2] = 0; 
    }
    
    int numFaces = Triangulation.faceTable.size();
    int[][] combinatorics = new int[numFaces][3];
    int faceIndex = 0; //keeps track of which face we're on
    //note that it doesn't matter if this index relates to the face's index in any way
    for (Face embeddedFace : Triangulation.faceTable.values()) {
      int vertIndex = 0;
      for (Vertex vertex : embeddedFace.getLocalVertices()) {
        combinatorics[faceIndex][vertIndex] = labeling.get(vertex);
        vertIndex += 1;
      }
      faceIndex += 1;
    }
    
     
     ifsf.setVertexCount(Triangulation.vertexTable.keySet().size());
     ifsf.setFaceCount(Triangulation.faceTable.keySet().size());
     ifsf.setVertexCoordinates(vertCoords);
     ifsf.setFaceIndices(combinatorics);
     ifsf.setGenerateEdgesFromFaces(true);
     ifsf.setGenerateFaceNormals(true);
     ifsf.update();
     
     int numEdges = Triangulation.edgeTable.keySet().size();
     double[][] colors = new double[numEdges][3];
     
     for (int i = 0; i < numEdges; i++) {
       colors[i][0] = 1;
       colors[i][1] = 0;
       colors[i][2] = 0;
     }
     
     IndexedFaceSet ifs = ifsf.getIndexedFaceSet();
     ifs.setEdgeAttributes(Attribute.COLORS, StorageModel.DOUBLE_ARRAY.array(3).createReadOnly(colors));
     
     viewer.render();
     //type lower-case 'e' in the JReality window and the new image will be centered and resized 
  }
}
