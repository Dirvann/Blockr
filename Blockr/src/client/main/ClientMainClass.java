package client.main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import presentation.BlockrPanel;
/**
 * The class that runs the current version of our Blockr Game.
 * 
 * @version 4.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public class  ClientMainClass {

	private static BlockrPanel blockrPanel;

	public static void main(String[] args) {
		
		if(args.length > 0) {
			try {
				ClassLoader loader = ClientMainClass.class.getClassLoader();
				Class<?> gwClass = loader.loadClass(args[0]);
	            
				JFrame frame = new JFrame("Blockr");
				blockrPanel = new BlockrPanel(gwClass);
				frame.add(blockrPanel);
				frame.pack();
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				frame.setVisible(true);
				
			}  catch (ClassNotFoundException e) {
				System.out.println("Run failure due to: Class Not Found Exception");
				e.printStackTrace();
			} catch (InstantiationException e) {
				System.out.println("Run failure due to: Instantiation Exception");
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				System.out.println("Run failure due to: Illegal Access Exception");
				e.printStackTrace();
			}
		}
	}

}
