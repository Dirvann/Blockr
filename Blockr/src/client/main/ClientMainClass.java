package client.main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import presentation.BlockrPanel;
/**
* @version 3.0
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
