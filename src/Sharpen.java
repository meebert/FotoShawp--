import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;


public class Sharpen extends JFrame {

	private BufferedImage image;
	private JFileChooser imageChooser = new JFileChooser();
	
	
	private JFrame frame = new JFrame("Sharpen");
	private JPanel centerPanel = new JPanel();
	
	
	
	private JMenuBar menuBar = new JMenuBar();
	
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
	
	public Sharpen(){
		frame.setJMenuBar(menuBar);
		
		//file.add(quitMenu);
		file.add(loadMenu);
		menuBar.add(file);
		
		filters.add(sharpen);
		menuBar.add(filters);
		
		
		frame.getContentPane().setLayout(new BorderLayout());
		frame.addWindowListener(new WindowQuit());
			
		quitMenu.addActionListener(new MenuQuit());
		loadMenu.addActionListener(new Load());
	}
	
	public class Load implements ActionListener{
		public void actionPerformed(ActionEvent event){
			File file = imageChooser.getSelectedFile();
		
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
		frame.setVisible(true);
	}
	
}
