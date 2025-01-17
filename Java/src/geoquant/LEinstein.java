package geoquant;

import java.util.HashMap;

import triangulation.Edge;


public class LEinstein extends Geoquant {
  // Index map
  private static HashMap<TriPosition, LEinstein> Index = new HashMap<TriPosition, LEinstein>();
  
  // Needed geoquants
  private SectionalCurvature Curv;
  private LEHR LEHRtemp1;
  private Length leng;
  
  public LEinstein(Edge e) {
    super(e);
    
    Curv = SectionalCurvature.at(e);
    Curv.addObserver(this);
    LEHRtemp1 = LEHR.getInstance();
    LEHRtemp1.addObserver(this);
    leng = Length.at(e);
    leng.addObserver(this);
    
  }
  
  protected void recalculate() {
    double K_ij = Curv.getValue();
    double LEHRtemp2 = LEHRtemp1.getValue();
    double L_ij = leng.getValue();

    value = K_ij*L_ij-LEHRtemp2*L_ij;

  }
 
  public void remove() {
    deleteDependents();
    Curv.deleteObserver(this);
    LEHRtemp1.deleteObserver(this);
    leng.deleteObserver(this);
    Index.remove(pos);
  }
  
  public static LEinstein at(Edge e) {
    TriPosition T = new TriPosition(e.getSerialNumber());
    LEinstein q = Index.get(T);
    if(q == null) {
      q = new LEinstein(e);
      q.pos = T;
      Index.put(T, q);
    }
    return q;
  }
  
  public static LEinstein At(Edge e) {
    TriPosition T = new TriPosition(e.getSerialNumber());
    LEinstein q = Index.get(T);
    if(q == null) {
      q = new LEinstein(e);
      q.pos = T;
      Index.put(T, q);
    }
    return q;
  }
  
  public static double valueAt(Edge e) {
    return at(e).getValue();
  }

}
