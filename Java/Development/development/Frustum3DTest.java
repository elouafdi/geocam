package development;

import java.util.ArrayList;

import de.jreality.geometry.IndexedFaceSetFactory;
import de.jreality.plugin.JRViewer;
import de.jreality.scene.SceneGraphComponent;
import de.jreality.tools.RotateTool;

public class Frustum3DTest {
  public static void main(String[] args) {
    Frustum3D f1 = new Frustum3D(new Vector3D(0, 0, 1), new Vector3D(1, 0, 0), new Vector3D(0, 1, 0));
    Frustum3D f2 = new Frustum3D(new Vector3D(1, 1, 1), new Vector3D(1, 0, 1), new Vector3D(1, 1, -1));
    
    Frustum3D f3 = null;
    try {
      f3 = Frustum3D.intersect(f1, f2);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    double[][] allVectors = new double[f1.getNumberVectors()+1][3];
    allVectors[0] = new double[]{0, 0, 0};
    for(int i = 1; i < f1.getNumberVectors()+1; i++) {
      allVectors[i] = f1.getVectorAt(i-1).getVectorAsArray();
    }
    
//    ArrayList<Vector3D> vectors = f3.getVectors();
//    for(int i = 0; i < vectors.size(); i++ ){
//      double[] coords = new double[3];
//      coords[0] = vectors.get(i).getComponent(0);
//      coords[1] = vectors.get(i).getComponent(1);
//      coords[2] = vectors.get(i).getComponent(2);
//      allVectors.add(coords);
//    }
        
    double[][] ifsf_verts = allVectors;
    int[][] ifsf_faces = { {0, 1, 2}, {0, 2, 3}, {0, 3, 1} };
    
    SceneGraphComponent sgc_root = new SceneGraphComponent();
    IndexedFaceSetFactory ifsf = new IndexedFaceSetFactory();
    ifsf.setVertexCount(ifsf_verts.length);
    ifsf.setVertexCoordinates(ifsf_verts);
    ifsf.setFaceCount(ifsf_faces.length);
    ifsf.setFaceIndices(ifsf_faces);
    ifsf.setGenerateEdgesFromFaces(true);
    ifsf.update();
    sgc_root.setGeometry(ifsf.getGeometry());
    sgc_root.addTool(new RotateTool());
    
    //set up the main JRViewer
    JRViewer jrv = new JRViewer();
    jrv.addBasicUI();
    jrv.setContent(sgc_root);
    jrv.startup();

  }
}
