package Geoquant;

import java.util.HashMap;
import java.util.LinkedList;

import Triangulation.Face;
import Triangulation.Triangulation;
import Triangulation.Vertex;

public class Curvature2D extends Geoquant {
  // Index map
  private static HashMap<TriPosition, Curvature2D> Index = new HashMap<TriPosition, Curvature2D>();
  private static Sum sum = null;
  
  // Needed geoquants
  private LinkedList<Angle> angles;
  
  public Curvature2D(Vertex v) {
    super();
    
    angles = new LinkedList<Angle>();
    Angle a;
    for(Face f : v.getLocalFaces()) {
      a = Angle.At(v, f);
      a.addDependent(this);
      angles.add(a);
    }
  }
  
  protected void recalculate() {
    value = 2 * Math.PI;

    for(Angle a : angles) {
      value -= a.getValue();
    }
  }
 
  protected void remove() {
    deleteDependents();
    for(Angle a : angles) {
      a.removeDependent(this);
    }
    angles.clear();
    Index.remove(pos);
  }
  
  public static Curvature2D At(Vertex v) {
    TriPosition T = new TriPosition(v.getSerialNumber());
    Curvature2D q = Index.get(T);
    if(q == null) {
      q = new Curvature2D(v);
      q.pos = T;
      Index.put(T, q);
    }
    return q;
  }
  
  public static double valueAt(Vertex v) {
    return At(v).getValue();
  }

  public static Geoquant sum() {
    if(sum == null) {
      sum = new Sum();
    }
    return sum;
  }
  
  public static double getSum() {
    return sum.getValue();
  }
  
  private static class Sum extends Geoquant {
    LinkedList<Curvature2D> curvs = new LinkedList<Curvature2D>();
    
    Sum() {
      super();
      Curvature2D k;
      for(Vertex v : Triangulation.vertexTable.values()) {
        k = Curvature2D.At(v);
        k.addDependent(this);
        curvs.add(k);
      }
    }
    protected void recalculate() {
      value = 0;
      for(Curvature2D k : curvs) {
        value += k.getValue();
      }
    }

    protected void remove() {
      deleteDependents();
      for(Curvature2D k : curvs) {
        k.removeDependent(this);
      }
      curvs.clear();
    }
  }
}