package frontend;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import marker.Marker;
import marker.MarkerAppearance;
import marker.MarkerHandler;
import development.Development;
import development.ManifoldPosition;
import development.Vector;

public class ViewerController extends JFrame {

  private static final long serialVersionUID = 1L;

  /********************************************************************************
   * Viewer Controller Data
   * 
   * Data related to the model
   ********************************************************************************/
  private Development develop;
  private static MarkerHandler markerHandler;
  private static Marker source;  

  // This main method is for button layout testing.

  public ViewerController(MarkerHandler mh, Development d) {
    develop = d;
    markerHandler = mh;
    source = markerHandler.getSourceMarker();
    layoutGUI();
    DevelopmentUI.setExponentialView(showView2DBox.isSelected(), TextureEnabledBox.isSelected());
    DevelopmentUI.setEmbeddedView(showEmbeddedBox.isSelected(), TextureEnabledBox.isSelected());
    DevelopmentUI.setFirstPersonView(showView3DBox.isSelected(), TextureEnabledBox.isSelected());
  }

  /********************************************************************************
   * JFrame Data
   * 
   * Data required for laying out the window
   ********************************************************************************/
  private JMenuBar menuBar;
  private JMenu file;
  private JMenuItem open;
  private JPanel textBoxPanel;
  private JPanel recDepthPanel;
  private JFormattedTextField recDepth;
  private JLabel recDepthLabel;
  private JPanel numObjectsPanel;
  private JFormattedTextField numObjects;
  private JLabel numObjectsLabel;
  private JPanel sliderPanel;
  private JSlider speedSlider;
  private JSlider scalingSlider;
  private JButton stopStartButton;
  private JPanel viewerPanel;
  private JCheckBox showView2DBox;
  private JCheckBox showView3DBox;
  private JCheckBox showEmbeddedBox;
  private JPanel drawOptionsPanel;
  private JCheckBox drawEdgesBox;
  private JCheckBox drawFacesBox;
  private JCheckBox drawAvatarBox;
  private JCheckBox TextureEnabledBox;

  private static int MAX_SPEED = 4000;
  private static int MAX_SIZE = 10;

  TitledBorder depthBorder = BorderFactory.createTitledBorder("");
  TitledBorder pointBorder = BorderFactory.createTitledBorder("");
  TitledBorder speedBorder = BorderFactory.createTitledBorder("");
  TitledBorder objectsBorder = BorderFactory.createTitledBorder("");

  private void layoutGUI() {

    this.setSize(220, 480);
    this.setResizable(false);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setTitle("Development View");
    this.setLayout(new FlowLayout());

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
            // Choose only files, not directories
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            // Start in current directory
            fc.setCurrentDirectory(new File("."));
            fc.showOpenDialog(null);

            File file = null;
            try {
              file = fc.getSelectedFile();
            } catch (Exception ex) {
              System.out.println("Invalid file");
            }
            DevelopmentUI.loadSurface(file.getAbsolutePath());
            DevelopmentUI.resetView();
            resetViewController();
          }
        });
      }
    }

    // ******************************TEXT BOX
    // PANEL********************************
    textBoxPanel = new JPanel();
    getContentPane().add(textBoxPanel);
    textBoxPanel.setBorder(BorderFactory
        .createEtchedBorder(EtchedBorder.LOWERED));
    textBoxPanel.setLayout(new GridLayout(2, 1));

    // ****************************RECURSION DEPTH TEXT
    // BOX******************************
    // ******************************NUM OBJECTS TEXT
    // BOX********************************
    {
      NumberFormat f = NumberFormat.getIntegerInstance();
      recDepth = new JFormattedTextField(f);
      recDepth.setColumns(3);
      recDepth.setValue(develop.getDepth());
      recDepthLabel = new JLabel("Recursion Depth");
      recDepthPanel = new JPanel();
      recDepthPanel.setLayout(new FlowLayout());
      recDepthPanel.add(recDepthLabel);
      recDepthPanel.add(recDepth);
      textBoxPanel.add(recDepthPanel);

      numObjects = new JFormattedTextField(f);
      numObjects.setColumns(3);
      int current = markerHandler.getAllMarkers().size() - 1;
      numObjects.setValue(current);
      numObjectsLabel = new JLabel("Number of objects");
      numObjectsPanel = new JPanel();
      numObjectsPanel.setLayout(new FlowLayout());
      numObjectsPanel.add(numObjectsLabel);
      numObjectsPanel.add(numObjects);
      textBoxPanel.add(numObjectsPanel);

      // Recursion depth action listener
      ActionListener recDepthListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String value = recDepth.getValue().toString();
          int newDepth = Integer.parseInt(value);
          if (newDepth < 1) {
            newDepth = 1;
          } else if (newDepth > 20) {
            newDepth = 20;
          }
          develop.setDepth(newDepth);
        }
      };
      recDepth.addActionListener(recDepthListener);

      // Number of objects action listener
      ActionListener numObjectsListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String value = numObjects.getValue().toString();
          int newMarkers = Integer.parseInt(value);
          if (newMarkers < 0) {
            newMarkers = 0;
          } else if (newMarkers > 20) {
            newMarkers = 20;
          }

          Set<Marker> markers = markerHandler.getAllMarkers();
          int currentMarkers = markers.size() - 1;

          // if necessary, add markers
          if (currentMarkers < newMarkers) {
            Random rand = new Random();
            ManifoldPosition pos;
            MarkerAppearance app;

            for (int ii = 0; ii < newMarkers - currentMarkers; ii++) {
              pos = new ManifoldPosition(develop.getSource());
              double scale = scalingSlider.getValue() / 10.0;
              app = new MarkerAppearance(MarkerAppearance.ModelType.ANT, scale);
              double a = rand.nextDouble() * Math.PI * 2;
              Vector vel = new Vector(Math.cos(a), Math.sin(a));
              // advance ants off of source point
              vel.scale(0.25);
              pos.move(vel);
              Marker m = new Marker(pos, app, Marker.MarkerType.MOVING, vel);
              // set the speed of the marker objects after they are constructed
              // when new ants are added, their speed will depend on whether the
              // stop/start
              // button is pressed
              if (stopStartButton.getText().equals("Stop")) {
                double sliderSpeed = speedSlider.getValue() / 1000.0;
                m.setSpeed(0.05 * Math.pow(Math.E, sliderSpeed));
              } else
                m.setSpeed(0);
              markerHandler.addMarker(m);
            }
          }

          // if necessary, remove markers
          if (currentMarkers > newMarkers) {
            int counter = 0;            
            for (Marker m : markers) {
              if (m.getMarkerType() == Marker.MarkerType.SOURCE)
                continue;
              m.flagForRemoval();
              counter++;
              if (counter == currentMarkers - newMarkers) break;
            }
          }
        }
      };
      numObjects.addActionListener(numObjectsListener);
    }

    // ********************************SLIDER
    // PANEL**********************************
    sliderPanel = new JPanel();
    getContentPane().add(sliderPanel);
    sliderPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
    sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));

    // ******************************SPEED
    // SLIDER********************************
    /********************************************************************************
     * Note: Speed slider is using the exponential function y = 0.05*e^x to
     * convert input from the slider to a speed. This gives you more control
     * over low speeds on the slider.
     ********************************************************************************/
    {
      speedSlider = new JSlider();
      sliderPanel.add(speedSlider);
      speedSlider.setMaximum(MAX_SPEED);
      double speed = markerHandler.getMarkerSpeed(source);
      double speedToSet = Math.log(speed / 0.05);
      speedSlider.setValue((int) (speedToSet * 1000.0));

      final DecimalFormat speedFormat = new DecimalFormat("0.00");
      speedSlider.setBorder(speedBorder);
      speedBorder.setTitle("Speed (" + speedFormat.format(speed) + ")");

      // Speed slider action listener
      ChangeListener speedSliderListener = new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          double sliderValue = speedSlider.getValue() / 1000.0;
          double newSpeed = 0.05 * Math.pow(Math.E, sliderValue);
          Set<Marker> allMarkers = markerHandler.getAllMarkers();          
          for(Marker m : allMarkers){
            if( m.getMarkerType() == Marker.MarkerType.MOVING){
              m.setSpeed(newSpeed);
            }
          }
          speedBorder.setTitle("Speed (" + speedFormat.format(newSpeed) + ")");
        }
      };
      speedSlider.addChangeListener(speedSliderListener);
    }

    // ******************************SCALE
    // SLIDER********************************
    {
      scalingSlider = new JSlider();
      sliderPanel.add(scalingSlider);
      scalingSlider.setMaximum(MAX_SIZE);
      scalingSlider.setValue((int) (markerHandler.getMarkerScale(source) * 10));
      scalingSlider.setBorder(pointBorder);
      pointBorder.setTitle("Object scaling (" + scalingSlider.getValue() / 10.0
          + ")");

      // Scaling slider change listener
      ChangeListener scalingSliderListener = new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          double newScale = scalingSlider.getValue() / 10.0;
          Set<Marker> markers = markerHandler.getAllMarkers();
          Iterator<Marker> i = markers.iterator();

          synchronized (markers) {
            while (i.hasNext()) {
              Marker m = i.next();
              if (!m.equals(source)) {
                MarkerAppearance newAppearance = m.getAppearance();
                newAppearance.setScale(newScale);
                m.setAppearance(newAppearance);
              }
            }
          }
          pointBorder.setTitle("Object scaling (" + newScale + ")");
        }
      };
      scalingSlider.addChangeListener(scalingSliderListener);
    }

    // ****************************START/STOP
    // BUTTON******************************
    stopStartButton = new JButton();
    stopStartButton.setText("Stop");
    this.add(stopStartButton);
    // Start/stop button action listener
    ActionListener stopStartButtonListener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (stopStartButton.getText().equals("Stop")) {
          markerHandler.pauseSimulation();          
          stopStartButton.setText("Start");
        } else {
          markerHandler.unpauseSimulation();
          stopStartButton.setText("Stop");
        }
      }
    };
    stopStartButton.addActionListener(stopStartButtonListener);

    // ********************************VIEW
    // PANEL**********************************
    viewerPanel = new JPanel();
    getContentPane().add(viewerPanel);
    viewerPanel.setBorder(BorderFactory
        .createEtchedBorder(EtchedBorder.LOWERED));
    viewerPanel.setLayout(new BoxLayout(viewerPanel, BoxLayout.Y_AXIS));

    // ***************************2D VIEW CHECK BOX*****************************
    {
      showView2DBox = new JCheckBox();
      viewerPanel.add(showView2DBox);
      showView2DBox.setText("Show Exponential View");
      showView2DBox.setSelected(true);
      // add action listener here
      ActionListener view2DListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
          // TODO Auto-generated method stub
          boolean checkBox = showView2DBox.isSelected();
          DevelopmentUI.setExponentialView(checkBox, TextureEnabledBox.isSelected());
        }
      };
      showView2DBox.addActionListener(view2DListener);

      /********************************************************************************
       * 
       ********************************************************************************/
    }
    // ***************************3D VIEW CHECK BOX*****************************
    {
      showView3DBox = new JCheckBox();
      viewerPanel.add(showView3DBox);
      showView3DBox.setText("Show First Person View");
      showView3DBox.setSelected(true);
      ActionListener view3DListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
          // TODO Auto-generated method stub
          boolean checkBox = showView3DBox.isSelected();
          DevelopmentUI.setFirstPersonView(checkBox, TextureEnabledBox.isSelected());
        }
      };
      showView3DBox.addActionListener(view3DListener);

    }
    // ************************EMBEDDED VIEW CHECK BOX**************************
    {
      showEmbeddedBox = new JCheckBox();
      viewerPanel.add(showEmbeddedBox);
      showEmbeddedBox.setText("Show Embedded View");
      showEmbeddedBox.setSelected(true);
      ActionListener viewEmbeddedListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
          // TODO Auto-generated method stub
          boolean checkBox = showEmbeddedBox.isSelected();
          DevelopmentUI.setEmbeddedView(checkBox,TextureEnabledBox.isSelected());
        }
      };
      showEmbeddedBox.addActionListener(viewEmbeddedListener);
    }
 // ************************TEXTURE ENABLED CHECK BOX**************************
    {
      TextureEnabledBox = new JCheckBox();
      viewerPanel.add(TextureEnabledBox);
      TextureEnabledBox.setText("Display Texture");
      TextureEnabledBox.setSelected(true);
      ActionListener TextureEnabledListener = new ActionListener(){
        public void actionPerformed(ActionEvent arg0) {
          boolean textureEnabled = TextureEnabledBox.isSelected();
          DevelopmentUI.setExponentialView(false, textureEnabled);
          DevelopmentUI.setFirstPersonView(false, textureEnabled);
          DevelopmentUI.setEmbeddedView(false, textureEnabled);
          DevelopmentUI.setEmbeddedView(showEmbeddedBox.isSelected(),textureEnabled);
          DevelopmentUI.setExponentialView(showView2DBox.isSelected(), textureEnabled);
          DevelopmentUI.setFirstPersonView(showView3DBox.isSelected(), textureEnabled);
        } 
    };
    TextureEnabledBox.addActionListener(TextureEnabledListener);
    }
    // ******************************DRAW OPTIONS
    // PANEL********************************
    drawOptionsPanel = new JPanel();
    getContentPane().add(drawOptionsPanel);
    drawOptionsPanel.setBorder(BorderFactory
        .createEtchedBorder(EtchedBorder.LOWERED));
    drawOptionsPanel
        .setLayout(new BoxLayout(drawOptionsPanel, BoxLayout.Y_AXIS));

    // ***************************DRAW EDGES CHECK
    // BOX*****************************
    {
      drawEdgesBox = new JCheckBox();
      drawOptionsPanel.add(drawEdgesBox);
      drawEdgesBox.setText("Draw edges");
      drawEdgesBox.setSelected(true);

      ActionListener drawEdgesListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          boolean boxChecked = drawEdgesBox.isSelected();
          DevelopmentUI.setDrawEdges(boxChecked);
        }
      };
      drawEdgesBox.addActionListener(drawEdgesListener);
    }
    // ***************************DRAW FACES CHECK
    // BOX*****************************
    {
      drawFacesBox = new JCheckBox();
      drawOptionsPanel.add(drawFacesBox);
      drawFacesBox.setText("Draw faces");
      drawFacesBox.setSelected(true);

      ActionListener drawFacesListener = new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
          boolean boxChecked = drawFacesBox.isSelected();
          DevelopmentUI.setDrawFaces(boxChecked);
        }
      };
      drawFacesBox.addActionListener(drawFacesListener);
    }
    // ***************************DRAW AVATAR CHECK
    // BOX*****************************
    {
      drawAvatarBox = new JCheckBox();
      drawOptionsPanel.add(drawAvatarBox);
      drawAvatarBox.setText("Draw avatar");
      drawAvatarBox.setSelected(true);

      // Draw avatar action listener
      ActionListener drawAvatar = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          boolean showAvatar = drawAvatarBox.isSelected();
          if (showAvatar) {
            source.setVisible(true);
          } else {
            source.setVisible(false);
          }
        }
      };
      drawAvatarBox.addActionListener(drawAvatar);
    }
  }

  private void resetViewController() {
    source = markerHandler.getSourceMarker();
    numObjects.setValue(markerHandler.getAllMarkers().size() - 1);
    recDepth.setValue(develop.getDepth());
    double speed = markerHandler.getMarkerSpeed(source);
    double speedToSet = Math.log(speed / 0.05);
    speedSlider.setValue((int) (speedToSet * 1000.0));
    scalingSlider.setValue((int) (markerHandler.getMarkerScale(source) * 10));
    stopStartButton.setText("Stop");
    showView2DBox.setSelected(true);
    showView3DBox.setSelected(true);
    showEmbeddedBox.setSelected(true);
    drawEdgesBox.setSelected(true);
    drawAvatarBox.setSelected(true);
    drawFacesBox.setSelected(true);
    System.out.println(markerHandler.getAllMarkers().size() - 1);
  }

  public void setMarkerHandler(MarkerHandler mh) {
    markerHandler = mh;
  }
}
