package geoquant;

import java.util.HashMap;
import java.util.List;

import triangulation.Edge;
import triangulation.Face;
import triangulation.Vertex;

public class LKCurvature extends Geoquant {
  //Index map
  private static HashMap<TriPosition, LKCurvature> Index = new HashMap<TriPosition, LKCurvature>();
  
  private Vertex v;
  private List<Edge> edges;
  private List<Face> faces;
  
  
  public LKCurvature(Vertex v) {
    super(v);
    this.v = v;
    this.edges = v.getLocalEdges();
    this.faces = v.getLocalFaces();

    CurvatureTube.at(v,v).addObserver(this);
    for (Edge e: this.edges){
      CurvatureTube.at(v,e).addObserver(this);
    }
    
    for (Face f: this.faces){
      CurvatureTube.at(v,f).addObserver(this);
    }
  }
  
  protected void recalculate() {

    value = CurvatureTube.valueAt(v, v);

    for(Edge e : edges) {
      value += CurvatureTube.valueAt(v, e);
    }
    
    for(Face f : faces) {
      value += CurvatureTube.valueAt(v, f);
    }
  }
 
  public void remove() {
    deleteDependents();
    CurvatureTube.at(v,v).deleteObserver(this);
    for (Edge e: this.edges){
      CurvatureTube.at(v,e).deleteObserver(this);
    }
    
    for (Face f: this.faces){
      CurvatureTube.at(v,f).deleteObserver(this);
    }
    
  }
  
  public static LKCurvature at(Vertex v) {
    TriPosition T = new TriPosition(v.getSerialNumber());
    LKCurvature q = Index.get(T);
    if(q == null) {
      q = new LKCurvature(v);
      q.pos = T;
      Index.put(T, q);
    }
    return q;
  }
  
  public static LKCurvature At(Vertex v) {
    TriPosition T = new TriPosition(v.getSerialNumber());
    LKCurvature q = Index.get(T);
    if(q == null) {
      q = new LKCurvature(v);
      q.pos = T;
      Index.put(T, q);
    }
    return q;
  }
  
  public static double valueAt(Vertex v) {
    return at(v).getValue();
  }
}
