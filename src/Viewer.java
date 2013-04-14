import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Viewer extends JFrame {
	private BufferedImage image;
	private JFileChooser imageChooser = new JFileChooser();
	
	private JFrame frame = new JFrame("FotoShawp--");
	private JPanel centerPanel = new JPanel();
	private JMenuBar menuBar = new JMenuBar();
	
	
	private JLabel imageLabel = new JLabel();
	private JScrollPane imageHolder = new JScrollPane();

	//FILE MENU
	private JMenu file = new JMenu("File");
	private JMenuItem quitMenu = new JMenuItem("Quit");
	private JMenuItem loadMenu = new JMenuItem("Load");

	//FILTERS MENU
	private JMenu filters = new JMenu("Filters");
	private JMenuItem sharpen = new JMenuItem("Sharpen");


	public static void main(String args[]){
		Viewer view = new Viewer();
		view.start();
	}
	
	public Viewer(){
		frame.setJMenuBar(menuBar);
		//FILE MENU
		file.add(quitMenu);
		file.add(loadMenu);
		menuBar.add(file);
		
		//FILTERS MENU
		filters.add(sharpen);
		menuBar.add(filters);
		
		//PANELS
		imageHolder.setSize(300,250);
		centerPanel.add(imageHolder);
		
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(centerPanel , BorderLayout.NORTH);
		
		
		frame.addWindowListener(new WindowQuit());
			
		quitMenu.addActionListener(new MenuQuit());
		loadMenu.addActionListener(new Load());
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(image, 0,0,null);
	}
	
	public class Load implements ActionListener{
		public void actionPerformed(ActionEvent event){
			imageChooser.showOpenDialog(null);
			File file = imageChooser.getSelectedFile();
			try{
				image = ImageIO.read(file);
				imageHolder = new JScrollPane(new JLabel(new ImageIcon(image)));
				centerPanel.add(imageHolder);
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
		frame.setSize(1000,800);

		frame.setVisible(true);
	}
	
}
