import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Viewer extends JFrame {
	private BufferedImage image;
	private BufferedImage currentImage;
	private JFileChooser imageChooser = new JFileChooser();
	private static Viewer view = new Viewer();
	private final int SCALE = 900;

	//TRANSFORMATION OBJECTS
	Brightness bright;
	Sharpen sharp;
	Contrast con;
	Dither dit = new Dither();
	Mosaic mos = new Mosaic();
	
	
	//DISPLAY VARS
	private JFrame frame = new JFrame("FotoShawp--");
	private JMenuBar menuBar = new JMenuBar();
	
	//CENTER PANEL
	private JPanel centerPanel = new JPanel();
	private JLabel imageLabel = new JLabel();

	//SOUTH PANEL
	private JPanel southPanel = new JPanel();
	private JSlider sharpness = new JSlider(0,200);
	private JLabel sharpLabel = new JLabel("Sharpness->");
	
	private JSlider contrast = new JSlider(0,200);
	private JLabel contrastLabel = new JLabel("Contrast->");
	
	private JSlider brightness = new JSlider(0,200);
	private JLabel brightLabel = new JLabel("Brightness->");
	
	//private JButton oDither = new JButton("Ordered Dither");
	//private JButton rDither = new JButton("Random Dither");

	private JButton apply = new JButton("Apply Change");

	//FILE MENU
	private JMenu file = new JMenu("File");
	private JMenuItem quitMenu = new JMenuItem("Quit");
	private JMenuItem loadMenu = new JMenuItem("Load");
	private JMenuItem saveMenu = new JMenuItem("Save");


	//DITHER MENU
	private JMenu dither = new JMenu("Dither");
	private JMenuItem oDither = new JMenuItem("Ordered");
	private JMenuItem rDither = new JMenuItem("Random");


	//FILTERS MENU
	private JMenu filters = new JMenu("Filters");
	private JMenuItem sharpenMenu = new JMenuItem("Sharpen");
	private JMenuItem brightMenu = new JMenuItem("Brightness");
	private JMenuItem conMenu = new JMenuItem("Contrast");
	private JMenuItem bw = new JMenuItem("Black and White");
	private JMenuItem sepia = new JMenuItem("sepia");
	private JMenuItem xray = new JMenuItem("X-Ray");
	private JMenuItem popart = new JMenuItem("Pop-Art");
	private JMenuItem bulge = new JMenuItem("Bulge");
	private JMenuItem warp = new JMenuItem("Warp");
	
	//MOSAIC MENU
	private JMenu mosaic = new JMenu("Photo Mosaic");
	private JMenuItem music = new JMenuItem("Music");
	private JMenuItem paints = new JMenuItem("Paintings");
	
	//START VIEWER
	public static void main(String args[]){
		view.start();
	}
	//APPLY CHANGES
	public void apply(){
		Image sImage = currentImage.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
		imageLabel.setIcon(new ImageIcon(sImage));
		centerPanel.repaint();
		southPanel.setVisible(false);
		southPanel.removeAll();
		repaint();
		
	}
	//INITIALIZE GUI AND ACTION LISTENERS
	public Viewer(){
		//APPLY MENUBAR
		frame.setJMenuBar(menuBar);
		//FILE MENU
		file.add(loadMenu);
		file.add(saveMenu);
		file.add(quitMenu);
		menuBar.add(file);
	
		//FILTERS MENU
		filters.add(sharpenMenu);
		filters.add(brightMenu);
		filters.add(conMenu);
		filters.add(bw);
		filters.add(sepia);
		filters.add(xray);
		filters.add(popart);
		filters.add(bulge);
		filters.add(warp);
		menuBar.add(filters);
		
		//DITHER MENU
		dither.add(oDither);
		dither.add(rDither);
		menuBar.add(dither);
		
		//MOSAIC MENU
		mosaic.add(music);
		mosaic.add(paints);
		menuBar.add(mosaic);
		
		//CENTER PANEL (IMAGE)
		centerPanel.add(imageLabel);

		//STRUCTURE GUI
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(centerPanel , BorderLayout.CENTER);
		frame.getContentPane().add(southPanel , BorderLayout.SOUTH);

		
		//ACTIONLISTENER FOR LOAD / QUIT
		frame.addWindowListener(new WindowQuit());	
		quitMenu.addActionListener(new MenuQuit());
		loadMenu.addActionListener(new Load());
		saveMenu.addActionListener(new Save());
		
		//MENU LISTENERS
		sharpenMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				southPanel.add(sharpLabel);
				southPanel.add(sharpness);
				southPanel.add(apply);
				//southPanel.repaint();
				frame.getContentPane().add(southPanel , BorderLayout.SOUTH);
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		
		brightMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				southPanel.add(brightLabel);
				southPanel.add(brightness);
				southPanel.add(apply);
				//southPanel.repaint();
				frame.getContentPane().add(southPanel , BorderLayout.SOUTH);
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		conMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				southPanel.add(contrastLabel);
				southPanel.add(contrast);
				southPanel.add(apply);
				//southPanel.repaint();
				frame.getContentPane().add(southPanel , BorderLayout.SOUTH);
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
	
		
		rDither.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				currentImage = dit.ditherR(currentImage);
				Image sImage = currentImage.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();			
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		
		oDither.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				currentImage = dit.ditherO(currentImage);
				Image sImage = currentImage.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();			
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		
		music.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				currentImage = mos.mosaicIt(currentImage , "music");
				Image sImage = currentImage.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();			
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		
		
		
		//SLIDER / BUTTON LISTENERS
		brightness.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent c) {
				southPanel.setVisible(true);

				
				update();

				/*
				image = bright.brighten( brightness.getValue()/100.f);
				Image sImage = image.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();
				*/
			}
		});
		sharpness.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent c) {
				southPanel.setVisible(true);

				update();

				/*
				image = sharp.sharpen(sharpness.getValue()/100.f);
				Image sImage = image.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();
				*/
			}
		});
		contrast.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent c) {
				southPanel.setVisible(true);
				update();
				/*
				image = con.contr(contrast.getValue()/100.f);
				Image sImage = image.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();
				*/
			}
		});
		
		apply.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				apply();
			}	
		});
	}
	/*
	protected void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(image.getScaledInstance(400, -1, image.SCALE_SMOOTH), 500, 400,this);
		repaint();
	}
	*/
	public void update(){
		//currentImage = 
		currentImage = bright.brighten( brightness.getValue()/100.f , image);
		currentImage = sharp.sharpen(sharpness.getValue()/100.f, currentImage);
		currentImage = con.contr(contrast.getValue()/100.f, currentImage);

		
		Image sImage = currentImage.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
		imageLabel.setIcon(new ImageIcon(sImage));
		centerPanel.repaint();


	}
	
	
	public class Load implements ActionListener{
		public void actionPerformed(ActionEvent event){
			southPanel.setVisible(false);
			southPanel.removeAll();
			southPanel.setVisible(true);
			
			imageChooser.showOpenDialog(null);
			File file = imageChooser.getSelectedFile();
			try{
				image = ImageIO.read(file);
				Image sImage = image.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();
				currentImage = image;
				//Initialize Transformation Objects With Loaded Image
				sharp = new Sharpen(image);
				bright = new Brightness(image);
				con = new Contrast(image);

				
			} catch (IOException e) {
				System.out.println(e);
			}
			
			
		}
	}
	public class Save implements ActionListener{
		public void actionPerformed(ActionEvent event){
			String fileName = new String("");
			String dir = new String("");
		    JFileChooser c = new JFileChooser();
		      // Demonstrate "Save" dialog:
		      int rVal = c.showSaveDialog(Viewer.this);
		      if (rVal == JFileChooser.APPROVE_OPTION) {
		        fileName = (c.getSelectedFile().getName());
		        dir = (c.getCurrentDirectory().toString());
		      }
		      if (rVal == JFileChooser.CANCEL_OPTION) {
		       
		      }
		      System.out.println(dir);
		      try {
		    	    BufferedImage bi = currentImage;
		    	    File outputfile = new File(fileName + ".png");
		    	    ImageIO.write(bi, "png", outputfile);
		    	} catch (IOException e) {
		    	   
		    	}
		      
		      
		} 
	}
	public class MenuQuit implements ActionListener{
		public void actionPerformed(ActionEvent event){
			System.exit(0);
		}
	}
	
	public class WindowQuit extends WindowAdapter{
		public void windowClosing(WindowEvent event){
			System.exit(0);
		}
	}
	
	public void start(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.pack();
		frame.setSize(1000,700);

		frame.setVisible(true);
	}
	
}
