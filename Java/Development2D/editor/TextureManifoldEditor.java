package editor;

import inputOutput.TriangulationIO;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import menus.DevelopmentSettingsPanel;
import menus.ManifoldMenu;
import menus.SimplexLabelMenu;
import menus.SimulationManager;
import menus.TexturePanel;
import menus.ViewMenu;
import menus.ZoomSlider;
import util.AssetManager;

public class TextureManifoldEditor {

	private SimulationManager simMan;

	private JFrame settingsFrame;
	private JFrame textureFrame;
	
	private boolean sessionEnded;
	
	private ManifoldMenu manMenu;
	private ViewMenu viewMenu;
	
	private TexturePanel texPanel;
	private DevelopmentSettingsPanel dsp;		
	private ZoomSlider expZoom;
	private SimplexLabelMenu simplexLabels;
	
	public static void main(String[] args){
		new TextureManifoldEditor();
	}
	
	public TextureManifoldEditor(){
		String defaultPath = AssetManager.getAssetPath("off/cube_surf.off");

		simMan = new SimulationManager(defaultPath);

		settingsFrame = new JFrame();
		textureFrame = new JFrame();
		
		manMenu = new ManifoldMenu(simMan, settingsFrame);
		
		JMenuItem saveItem = new JMenuItem("Save current manifold");
		manMenu.add(saveItem);
		saveItem.addActionListener(new SaveManifoldListener());
		
		viewMenu = new ViewMenu(simMan);		
		
		dsp = new DevelopmentSettingsPanel(simMan.getDevelopment());
		expZoom = new ZoomSlider("Exponential Map View");
		viewMenu.addExponentialViewController(expZoom);
		
		texPanel = new TexturePanel(simMan.getDevelopment().getSource());	
		simplexLabels = new SimplexLabelMenu( simMan.getMarkerHandler() );
		
		assembleSwingComponents();
		viewMenu.createExponentialView();

		while (true) {
			simMan.run(); // We only return from this call once ManifoldMenu
							// signals a new simulation is needed.			
			manMenu.setInterfacesEnabled(false);

			if( sessionEnded ){
				viewMenu.closeViews();
				break;
			}

			simMan = new SimulationManager(manMenu.getManifoldPath());
			manMenu.setSimulationManager(simMan);
			viewMenu.setSimulationManager(simMan);

			// Note: mdsp, expZoom, and embZoom don't need to be updated,
			// because they are ViewControllers and work through the ViewMenu
			// object (which gets updated).
			dsp.setDevelopment(simMan.getDevelopment());
			viewMenu.createExponentialView();
			manMenu.setInterfacesEnabled(true);
			
			simplexLabels.setMarkerHandler(simMan.getMarkerHandler());
		}
		
		System.exit(0);
	}

	public void assembleSwingComponents() {		
		settingsFrame.addWindowListener( new CloseListener() );
		Container cp = settingsFrame.getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

		JMenuBar jmb = new JMenuBar();
		settingsFrame.setJMenuBar(jmb);

		jmb.add(manMenu);
		jmb.add(viewMenu);
		jmb.add(simplexLabels);
		settingsFrame.add(dsp);
		
		JPanel camPanel = new JPanel();
		camPanel.setLayout(new BoxLayout(camPanel, BoxLayout.Y_AXIS));
		camPanel.setBorder(BorderFactory.createTitledBorder("Camera Distances"));
		camPanel.add(expZoom);		
		settingsFrame.add(camPanel);
		
		settingsFrame.pack();
		settingsFrame.setVisible(true);
		
		cp = textureFrame.getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));		
		textureFrame.add( texPanel );
		textureFrame.pack();
		textureFrame.setVisible(true);
	}
	
	private class CloseListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			sessionEnded = true;
			simMan.terminate();
		}
	}
	
	private class SaveManifoldListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser(AssetManager.getAssetPath("."));
			
			int ret = jfc.showSaveDialog(manMenu);
			if(ret == JFileChooser.APPROVE_OPTION) {
				File file = jfc.getSelectedFile();
				TriangulationIO.writeTriangulation(file.getAbsolutePath());
			}
		}
	}
}
