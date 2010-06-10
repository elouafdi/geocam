package tests;

import Geoquant.Length;
import InputOutput.TriangulationIO;
import Triangulation.Edge;
import Triangulation.Triangulation;
import Visualization.TriangulationDisplay;

public class TriangulationDisplayTest {

  public static void main(String[] args) throws InterruptedException {
    
    TriangulationIO.read2DTriangulationFile("Data/flip_test/convex_pair.txt");
    
    TriangulationDisplay.showTriangulation();
    
 //   for (Edge edge : Triangulation.edgeTable.values()) {
 //     if (edge.getIndex() == 0) {
  //      continue;
 //     }
 //     else {
 //       Length.At(edge).setValue(3);
 //     }
 //   }
    
    Thread.sleep(1000);
    
   Triangulation.reset(); 
   TriangulationIO.read2DTriangulationFile("Data/flip_test/eight_triangles_redux.txt");
    
    System.out.println("About to call update");
    TriangulationDisplay.updateDisplay();
    System.out.println("called update");
  }
}