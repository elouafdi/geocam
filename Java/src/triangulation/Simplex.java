package triangulation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Simplex {
  protected int serialNumber;
  protected int index;
  private static int simplexCounter = 0;
  protected LinkedList<Vertex> localVertices;
  protected LinkedList<Edge> localEdges;
  protected LinkedList<Face> localFaces;
  protected LinkedList<Tetra> localTetras;
  protected int multiplicity;
  protected ArrayList<Integer> metaFace;
  protected boolean hasMetaFace;
  protected boolean boundary;
  
  public Simplex(int index) {
    this.index = index;
    serialNumber = simplexCounter;
    simplexCounter++;
    localVertices = new LinkedList<Vertex>();
    localEdges = new LinkedList<Edge>();
    localFaces = new LinkedList<Face>();
    localTetras = new LinkedList<Tetra>();
    multiplicity = 1;
    hasMetaFace = false;
    metaFace = new ArrayList<Integer>();
  }
  
  public int getIndex() {
    return index;
  }
  
  public int getSerialNumber() {
    return serialNumber;
  }
  
  public int getMultiplicity(){
    return multiplicity;
  }
  
  public boolean getBoundary(){
	  return boundary;
  }
  
  public void setIndex(int index) {
    this.index = index;
  }
  
  public void setMultiplicity(int multiplicity){
    this.multiplicity = multiplicity;
  }
  
  public void setBoundary(boolean newBoundary){
	  this.boundary = newBoundary;
  }
  
  // Local simplices retrieval methods
  public List<Vertex> getLocalVertices() {
    return localVertices;
  }
  public List<Edge> getLocalEdges() {
    return localEdges;
  }
  public List<Face> getLocalFaces() {
    return localFaces;
  }
  public List<Tetra> getLocalTetras() {
    return localTetras;
  }
  
  // Adding local simplices
  public void addVertex(Vertex v) {
    localVertices.add(v);
  }
  public void addEdge(Edge e) {
    localEdges.add(e);
  }
  public void addFace(Face f) {
    localFaces.add(f);
  }
  public void addTetra(Tetra t) {
    localTetras.add(t);
  }
  // Adding local simplices, no duplicate
  public void addUniqueVertex(Vertex v) {
    if(!localVertices.contains(v)) {
      localVertices.add(v);
    }
  }
  public void addUniqueEdge(Edge e) {
    if(!localEdges.contains(e)) {
      localEdges.add(e);
    }
  }
  public void addUniqueFace(Face f) {
    if(!localFaces.contains(f)) {
      localFaces.add(f);
    }
  }
  public void addUniqueTetra(Tetra t) {
    if(!localTetras.contains(t)) {
      localTetras.add(t);
    }
  }
  
  // Removing local simplices
  public void removeVertex(Vertex v) {
    localVertices.remove(v);
  }
  public void removeEdge(Edge e) {
    localEdges.remove(e);
  }
  public void removeFace(Face f) {
    localFaces.remove(f);
  }
  public void removeTetra(Tetra t) {
    localTetras.remove(t);
  }
  public void clearLocals() {
    localVertices.clear();
    localEdges.clear();
    localFaces.clear();
    localTetras.clear(); 
  }
  
  // Adjacency checks
  public boolean isAdjVertex(Vertex v) {
    return localVertices.contains(v);
  }
  public boolean isAdjEdge(Edge e) {
    return localEdges.contains(e);
  }
  public boolean isAdjFace(Face f) {
    return localFaces.contains(f);
  }
  public boolean isAdjTetra(Tetra t) {
    return localTetras.contains(t);
  }
  
  public boolean equals(Object other) {
    if(other.getClass() != this.getClass()) {
      return false;
    }
    return this.index == ((Simplex) other).index;
  }
  
  public int hashCode() {
    return this.getClass().hashCode() ^ (new Integer(this.index)).hashCode();
  }
  
  public String toString() {
    String type = this.getClass().toString().substring(6);
    if(type.contains(".")) {
      type = type.substring(type.lastIndexOf(".") + 1);
    }
    return type + " " + index;
  }
  
  public void addMetaFace(Integer mf){
	  this.metaFace.add(mf);
	  hasMetaFace = true;
  }
  
  public boolean hasMetaFace(){
	  return hasMetaFace;
  }
  
  public ArrayList<Integer> getMetaFace(){
	  return metaFace;
  }
}
