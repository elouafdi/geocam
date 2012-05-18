package view;

import inputOutput.TriangulationIO;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import markers.BasicMovingMarkers;
import markers.ManifoldPosition;
import markers.MovingMarker;
import markers.MarkerAppearance;
import markers.MarkerDynamics;
import markers.VisibleMarker;

import triangulation.Face;
import triangulation.Triangulation;
import triangulation.Vertex;
import view.ColorScheme.schemes;
import development.Coord2D;
import development.Development;
import development.EmbeddedTriangulation;
import development.Vector;

public class FindingGame extends JFrame  
                         implements Development.DevelopmentViewer, 
                                    MarkerDynamics.DynamicsListener{

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
      JFrame window = new FindingGame(filename);
      window.setVisible(true);
    }
   
    //--- GUI options -----------------------
    private static int MAX_DEPTH       = 50;
    private static int MAX_SCALING     = 10; 
    private static int MAX_SPEED       = 10000; //in units per millisecond
    //------------------------------------
    
    //--- development options ------------------
    private static Development development;
    private static Vector sourcePoint;
    private static Face sourceFace;
    private static ColorScheme colorScheme;
    private int currentDepth = 4;
    
    private static String[] filenames = { "Data/off/square2.off", 
                                          "Data/off/tetra.off",
                                          "Data/off/tetra2.off",
                                          "Data/blender/neckpinch.off",
                                          "Data/blender/torus.off",
                                          "Data/blender/cube_surf.off",
                                          "Data/off/icosa.off",
                                          "Data/off/dodec2.off",
                                          "Data/off/cone.off",
                                          "Data/off/epcot.off",
                                          "Data/off/square2.off",
                                          "Data/Triangulations/2DManifolds/tetrahedronnonembed2.xml",
                                          "Data/Triangulations/2DManifolds/tetrahedron2.xml",
                                          "Data/Triangulations/2DManifolds/tetrahedronnew.xml",
                                          "Data/Triangulations/2DManifolds/torus-9-2.xml" };
    private static String filename = filenames[3];

    //------------------------------------
    
    //--- viewers -----------------------
    private static DevelopmentView2D view2D = null;
    private static DevelopmentViewSim3D view3D = null;
    private static DevelopmentViewEmbedded embeddedView = null;
    
    private static LinkedList<DevelopmentView> devViewers = new LinkedList<DevelopmentView>(); //active viewers
    
    //some viewer options
    private boolean showView2D = false;
    private boolean showView3D = false;
    private boolean showEmbedded = false;
    
    private boolean drawEdges = false;
    private boolean drawFaces = true;
    //------------------------------------
      
    //--- objects ------------------------
    private static BasicMovingMarkers dynamics = new BasicMovingMarkers(50);
    private static final boolean INITIAL_MOVEMENT_STATUS = false;
    private static final int MOVING_OBJECT_START = 1;
    
    double objectSpeed = 1; //units per second
    double objectScale = 1.0;
    int numObjects = MOVING_OBJECT_START;
    private JButton depthSchemeButton;
    private JCheckBox drawEdgesBox;
    private JCheckBox drawFacesBox;
    private JMenuItem open;
    private JMenu file;
    private JSlider numObjectsSlider;
    private JPanel drawOptionsPanel;
    private JCheckBox showEmbeddedBox;
    private JCheckBox showView3DBox;
    private JCheckBox showView2DBox;
    private JPanel viewerPanel;
    private JLabel jLabel_IL1;
    private JButton stopStartButton;
    private JButton faceSchemeButton;
    private JLabel jLabel_IL;
    private JPanel colorPanel;
    private JSlider pointSizeSlider;
    private JSlider speedSlider;
    private JSlider depthSlider;
    private JPanel sliderPanel;
    
    //don't generally need to keep track of this list, but GUI will change the objects' properties
    private LinkedList<MovingMarker> movingObjects = new LinkedList<MovingMarker>();
   
    //--GUI pieces ----------------------------------------------
    JMenuBar menuBar;

    @SuppressWarnings("static-access")
    public FindingGame(String filename) {
      this.filename = filename;
      layoutGUI();
      loadSurface(filename);
      eDevelopmentGUI();
    }
    
    public FindingGame(){
      layoutGUI();
      eDevelopmentGUI();
    }
    
    private void eDevelopmentGUI() {
      colorScheme = new ColorScheme(schemes.FACE);
      development = null;
      initializeSurface();
      
      setUpObjects();
      //start listening for updates
      development.addViewer(this);
      dynamics.addListener(this);
    }

    
    private Color randomColor(Random rand){
      return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }
    
    private Vector randomUnitVector(Random rand){
      double a = rand.nextDouble()*Math.PI*2;
      return new Vector(Math.cos(a), Math.sin(a));
    }
    
    public void dynamicsEvent(int eventID){
      if(eventID == BasicMovingMarkers.EVENT_DYNAMICS_EVOLVED){
        updateGeometry(false,true);
      }
    }
    
    public void updateDevelopment(){
      updateGeometry(true,true);
    }
    
    private synchronized void updateGeometry(boolean dev, boolean obj){
      for(DevelopmentView dv : devViewers){ dv.updateGeometry(dev,obj); }
      
      VisibleMarker avatar = development.getSourceObject();
      
      double epsilon = 0.5;
      boolean playAgain = false;
      for( MovingMarker o : movingObjects )
        if( o.getFace() == avatar.getFace() && 
            Vector.distanceSquared(o.getPosition(),avatar.getPosition()) < epsilon ){                      
            int result = JOptionPane.showConfirmDialog(this,"You found the cookie! Play again?", "You Won!",
                                                       JOptionPane.YES_NO_OPTION);
            if( result == 0 ){
              // User wants to play again
              playAgain = true;
            } else {
              // User wants to quit
              System.exit(0);
            }            
      }   
      
      if( playAgain ){
        // Set up the objects in new locations.
        for(MovingMarker o : movingObjects){
          //movingObjects.remove(o); 
          o.removeFromManifold();
          dynamics.removeObject(o);
        }       
        movingObjects.clear();
        setUpObjects();
        initializeNewManifold();        
      }
      
    }
    
    public void refresh2D(){
      if(view2D != null) view2D.refreshView();
    }

    public void refresh3D(){
      if(view3D != null) view3D.refreshView();
    }

    private void initializeNewManifold(){
      for(DevelopmentView dv : devViewers){ dv.initializeNewManifold(); } 
    }
    
    /*
     * loads triangulated surface from file given by filename. Chooses an arbitrary source face
     * and source point in that face. Constructs a new Development if one hasn't already been
     * made. Otherwise rebuilds development with new surface.
     */
    private void loadSurface(String file) {
      filename = file;
      String extension = file.substring(file.length() - 3, file.length());
      
      if (extension.contentEquals("off")) {
        EmbeddedTriangulation.readEmbeddedSurface(filename); 
        showEmbeddedBox.setVisible(true);
      }
      else if (extension.contentEquals("xml")){
        TriangulationIO.readTriangulation(file);
        showEmbeddedBox.setVisible(false);
      }
      else System.err.println("invalid file");
    }
    
    private void initializeSurface(){
      Iterator<Integer> i = null;
      // pick some arbitrary face and source point
      i = Triangulation.faceTable.keySet().iterator();
      sourceFace = Triangulation.faceTable.get(i.next());
      sourcePoint = new Vector(0, 0);
      Iterator<Vertex> iv = sourceFace.getLocalVertices().iterator();
      while (iv.hasNext()) {
        sourcePoint.add(Coord2D.coordAt(iv.next(), sourceFace));
      }
      sourcePoint.scale(1.0f / 3.0f);

      if (development == null)
        development = new Development(new ManifoldPosition(sourceFace, sourcePoint), currentDepth, objectScale);
      else development.rebuild(new ManifoldPosition(sourceFace, sourcePoint), currentDepth);

    }
    
    private void setUpObjects(){
      development.getSourceObject()
                 .setAppearance( new MarkerAppearance(MarkerAppearance.ModelType.ANT ));
            
      Random rand = new Random();
      for(int i=0; i<numObjects; i++){
        MovingMarker newObject = 
          new MovingMarker(development.getSource(),
                           new MarkerAppearance(MarkerAppearance.ModelType.COOKIE), 
                           randomUnitVector(rand) );
        movingObjects.add(newObject);
      }
      for(MovingMarker o : movingObjects){
        o.setSpeed(objectSpeed);
        dynamics.addObject(o); 
      }
      
      if(INITIAL_MOVEMENT_STATUS){ 
        dynamics.start(); 
      }else{
        // Move the cookie for a while to "hide" it.
        dynamics.evolve(10000);
      }
    }
    
    TitledBorder depthBorder = BorderFactory.createTitledBorder("Recursion Depth (" + currentDepth + ")");
    TitledBorder pointBorder = BorderFactory.createTitledBorder("Object Scaling (" + objectScale + ")");
    TitledBorder speedBorder = BorderFactory.createTitledBorder("Speed (" + objectSpeed + ")");
    TitledBorder objectsBorder = BorderFactory.createTitledBorder("Number of objects (" + numObjects + ")");

    
    private void layoutGUI() {
      
      this.setSize(220, 559);
      this.setResizable(true);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.setTitle("Finding Game");

      // -------- MENU BAR --------
      menuBar = new JMenuBar();
      this.setJMenuBar(menuBar);
      {
        file = new JMenu();
        menuBar.add(file);
        file.setText("File");
        {
          open = new JMenuItem();
          file.add(open);
          open.setText("Load Surface");
          open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              JFileChooser fc = new JFileChooser();
              
              fc.setDialogTitle("Open File");
              fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
              fc.setCurrentDirectory(new File("."));
              fc.showOpenDialog(null);
              
              File file = null;
              try {
                file = fc.getSelectedFile();
              } catch (Exception ex) {
                System.out.println("Invalid file");
              }
              
              if( file != null ){
                loadSurface(file.getAbsolutePath());
                initializeSurface();
              }
              
            }
          });
        }
      }
      
      // -------- DEPTH SLIDER --------

      // -------- SPEED SLIDER --------

      // -------- POINT SIZE SLIDER --------

      // -------- COLOR SCHEME BUTTONS --------

      // -------- STOP/START MOVEMENT BUTTON --------
      if(INITIAL_MOVEMENT_STATUS){ stopStartButton.setText("Stop"); }

      // -------- VIEWER PANEL --------

      // -------- DRAW OPTIONS PANEL --------

      // -------- ADD PANELS --------
      this.setLayout(new FlowLayout());
      {
        sliderPanel = new JPanel();
        getContentPane().add(sliderPanel);
        sliderPanel.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
        sliderPanel.setLayout(new BoxLayout(sliderPanel,BoxLayout.Y_AXIS));
        {
          depthSlider = new JSlider();
          sliderPanel.add(depthSlider);
          depthSlider.setValue(currentDepth);
          depthSlider.setMaximum(MAX_DEPTH);
          depthSlider.setBorder(depthBorder);
          depthSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
              currentDepth = ((JSlider)e.getSource()).getValue();
              development.setDepth(currentDepth);
              depthBorder.setTitle("Recursion Depth (" + currentDepth + ")");
              initializeNewManifold();
            }
          });
        }
        {
          speedSlider = new JSlider();
          sliderPanel.add(speedSlider);
          
          speedSlider.setPaintLabels(false);
          speedSlider.setPaintTicks(false);
          speedSlider.setPaintTrack(true);                
          
          speedSlider.setValue((int)(objectSpeed*1000));
          speedSlider.setMaximum(MAX_SPEED);
          speedSlider.setBorder(speedBorder);
          speedSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
              objectSpeed = ((JSlider)e.getSource()).getValue()/1000.0;
              for(MovingMarker o : movingObjects){ 
                o.setSpeed(objectSpeed); 
              }
              speedBorder.setTitle("Speed (" + objectSpeed + ")");
            }
          });
        }
        {
          pointSizeSlider = new JSlider();
          sliderPanel.add(pointSizeSlider);
          pointSizeSlider.setValue( (int) (objectScale * 10.0 ) );
          pointSizeSlider.setMaximum( 10 * MAX_SCALING );
          pointSizeSlider.setBorder(pointBorder);
          pointSizeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
              objectScale = ((JSlider)e.getSource()).getValue() / 10.0;
              
              MarkerAppearance oa;
              oa = development.getSourceObject().getAppearance();
              oa.setScale( oa.getDefaultScale() * objectScale );
              
              development.getSourceObject().getAppearance().setScale(objectScale);
              for(MovingMarker mo : movingObjects){
                oa = mo.getAppearance();
                oa.setScale( oa.getDefaultScale() * objectScale );              
              }
              pointBorder.setTitle("Object Scaling (" + objectScale + ")" );
              updateGeometry(false,true);
            }
          });
        }
      }
      {
        colorPanel = new JPanel();
        getContentPane().add(colorPanel);
        colorPanel.setLayout(new GridLayout(5,1));
        {
          jLabel_IL = new JLabel("Set Color Scheme");
          colorPanel.add(jLabel_IL);
          jLabel_IL.setText("Set Color Scheme");
        }
        {
          depthSchemeButton = new JButton();
          colorPanel.add(depthSchemeButton);
          depthSchemeButton.setText("Depth");
          depthSchemeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              if (colorScheme.getSchemeType() != schemes.DEPTH) {
                colorScheme = new ColorScheme(schemes.DEPTH);
                if(showView2D){ view2D.setColorScheme(colorScheme); }
                if(showView3D){ view3D.setColorScheme(colorScheme); }
              }
            }
          });
        }
        {
          faceSchemeButton = new JButton();
          colorPanel.add(faceSchemeButton);
          faceSchemeButton.setText("Face");
          faceSchemeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              if (colorScheme.getSchemeType() != schemes.FACE) {
                colorScheme = new ColorScheme(schemes.FACE);
                if(showView2D){ view2D.setColorScheme(colorScheme); }
                if(showView3D){ view3D.setColorScheme(colorScheme); }
              }
            }
          });
        }
        {
          stopStartButton = new JButton();
          colorPanel.add(stopStartButton);
          stopStartButton.setText("Start");
          stopStartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              JButton source = (JButton)e.getSource();
              if (source.getText().equals("Stop")) {
                source.setText("Start");
                dynamics.stop();
              }else{
                source.setText("Stop");
                dynamics.start();
              }
            }
          });
        }
        {
          jLabel_IL1 = new JLabel("Set Color Scheme");
          colorPanel.add(jLabel_IL1);
          jLabel_IL1.setText("Set Color Scheme");
        }
      }
      {
        viewerPanel = new JPanel();
        getContentPane().add(viewerPanel);
        viewerPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        viewerPanel.setLayout(new BoxLayout(viewerPanel,BoxLayout.Y_AXIS));
        {
          showView2DBox = new JCheckBox();
          viewerPanel.add(showView2DBox);
          showView2DBox.setText("Show 2D view");
          showView2DBox.setSelected(showView2D);
          showView2DBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              showView2D = ((JCheckBox)e.getSource()).isSelected();
              if(showView2D == true){
                //start up the viewer 2D
                view2D = new DevelopmentView2D(development, colorScheme);
                //view2D.setLineVisible(false);
                view2D.setDrawEdges(drawEdges);
                view2D.setDrawFaces(drawFaces);
                view2D.updateGeometry(true,true);
                view2D.initializeNewManifold();
                devViewers.add(view2D);
              }else{
                //end the embedded  viewer
                devViewers.remove(view2D);
                view2D.dispose();
              }
            }
          });
        }
        {
          showView3DBox = new JCheckBox();
          viewerPanel.add(showView3DBox);
          showView3DBox.setText("Show 3D view");
          showView3DBox.setSelected(showView3D);
          showView3DBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              showView3D = ((JCheckBox)e.getSource()).isSelected();
              if(showView3D == true){
                //start up the embedded viewer
                view3D = new DevelopmentViewSim3D(development, colorScheme);
                view3D.setDrawEdges(drawEdges);
                view3D.setDrawFaces(drawFaces);
                view3D.updateGeometry(true,true);
                view3D.initializeNewManifold();
                devViewers.add(view3D);
              }else{
                //end the embedded  viewer
                devViewers.remove(view3D);
                view3D.dispose();
              }
            }
          });
        }
        {
          showEmbeddedBox = new JCheckBox();
          viewerPanel.add(showEmbeddedBox);
          showEmbeddedBox.setText("Show embedded view");
          showEmbeddedBox.setSelected(showEmbedded);
          showEmbeddedBox.setVisible(true);
          showEmbeddedBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              showEmbedded = ((JCheckBox)e.getSource()).isSelected();
              if(showEmbedded == true){
                //start up the embedded viewer
                embeddedView = new DevelopmentViewEmbedded(development, colorScheme);
                embeddedView.setDrawEdges(drawEdges);
                embeddedView.setDrawFaces(drawFaces);
                embeddedView.updateGeometry(true,true);
                embeddedView.initializeNewManifold();
                devViewers.add(embeddedView);
              }else{
                //end the embedded  viewer
                devViewers.remove(embeddedView);
                embeddedView.dispose();
              }
            }
          });
        }
      }
      {
        drawOptionsPanel = new JPanel();
        getContentPane().add(drawOptionsPanel);
        drawOptionsPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        drawOptionsPanel.setLayout(new BoxLayout(drawOptionsPanel,BoxLayout.Y_AXIS));
        {
          drawEdgesBox = new JCheckBox();
          drawOptionsPanel.add(drawEdgesBox);
          drawEdgesBox.setText("Draw edges");
          drawEdgesBox.setSelected(drawEdges);
          drawEdgesBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              drawEdges = ((JCheckBox)e.getSource()).isSelected();
              if(showView2D){ view2D.setDrawEdges(drawEdges); }
              if(showView3D){ view3D.setDrawEdges(drawEdges); }
            }
          });
        }
        {
          drawFacesBox = new JCheckBox();
          drawOptionsPanel.add(drawFacesBox);
          drawFacesBox.setText("Draw faces");
          drawFacesBox.setSelected(drawFaces);
          drawFacesBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              drawFaces = ((JCheckBox)e.getSource()).isSelected();
              if(showView2D){ view2D.setDrawFaces(drawFaces); }
              if(showView3D){ view3D.setDrawFaces(drawFaces); }
            }
          });
        }
      }
      {
        numObjectsSlider = new JSlider();
        getContentPane().add(numObjectsSlider);
        numObjectsSlider.setLayout(null);
        numObjectsSlider.setMaximum(20);
        numObjectsSlider.setValue(numObjects);
        numObjectsSlider.setBorder(objectsBorder);
        numObjectsSlider.addChangeListener(new ChangeListener() {
          public void stateChanged(ChangeEvent e) {
            numObjects = ((JSlider)e.getSource()).getValue();
            //movingObjects = null;
            for(MovingMarker o : movingObjects){
              //movingObjects.remove(o); 
              o.removeFromManifold();
              dynamics.removeObject(o);
            }       
            movingObjects.clear();
            setUpObjects();
            objectsBorder.setTitle("Number of objects (" + numObjects + ")");
            initializeNewManifold();
          }
        });

      }
    }
}
