import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Viewer extends JFrame {
	private BufferedImage image;
	private JFileChooser imageChooser = new JFileChooser();
	private static Viewer view = new Viewer();

	
	//DISPLAY VARS
	private JFrame frame = new JFrame("FotoShawp--");
	private JMenuBar menuBar = new JMenuBar();
	
	//CENTER PANEL
	private JPanel centerPanel = new JPanel();
	private JLabel imageLabel = new JLabel();

	//SOUTH PANEL
	private JPanel southPanel = new JPanel();
	private JSlider sharpness = new JSlider();
	private JLabel sharpLabel = new JLabel("Sharpness->");
	
	private JSlider contrast = new JSlider();
	private JLabel contrastLabel = new JLabel("Contrast->");
	
	private JSlider brightness = new JSlider();
	private JLabel brightLabel = new JLabel("Brightness->");
	
	private JButton oDither = new JButton("Ordered Dither");
	private JButton rDither = new JButton("Random Dither");


	//FILE MENU
	private JMenu file = new JMenu("File");
	private JMenuItem quitMenu = new JMenuItem("Quit");
	private JMenuItem loadMenu = new JMenuItem("Load");
	private JMenuItem saveMenu = new JMenuItem("Save");

	//FILTERS MENU
	private JMenu filters = new JMenu("Filters");
	private JMenuItem sharpen = new JMenuItem("Sharpen");


	public static void main(String args[]){
		view.start();
	}
	
	public Viewer(){
		frame.setJMenuBar(menuBar);
		//FILE MENU
		file.add(loadMenu);
		file.add(saveMenu);
		file.add(quitMenu);
		menuBar.add(file);
		
		//FILTERS MENU
		filters.add(sharpen);
		menuBar.add(filters);
		
		//CENTER PANEL (IMAGE)
		centerPanel.add(imageLabel);
		
		//SOUTH PANEL (IMAGE TRANSFORMATION)
		southPanel.add(sharpLabel);
		southPanel.add(sharpness);
		
		southPanel.add(contrastLabel);
		southPanel.add(contrast);
		
		southPanel.add(brightLabel);
		southPanel.add(brightness);
		
		southPanel.add(rDither);
		southPanel.add(oDither);

		
		
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(centerPanel , BorderLayout.CENTER);
		frame.getContentPane().add(southPanel , BorderLayout.SOUTH);

		
		frame.addWindowListener(new WindowQuit());
			
		quitMenu.addActionListener(new MenuQuit());
		loadMenu.addActionListener(new Load());
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(image.getScaledInstance(400, -1, image.SCALE_SMOOTH), 500, 400,this);
		repaint();
	}
	
	public class Load implements ActionListener{
		public void actionPerformed(ActionEvent event){
			imageChooser.showOpenDialog(null);
			File file = imageChooser.getSelectedFile();
			try{
				image = ImageIO.read(file);
				Image sImage = image.getScaledInstance(700, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();
				
			} catch (IOException e) {
				System.out.println(e);
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
		frame.setSize(1000,800);

		frame.setVisible(true);
	}
	
}
