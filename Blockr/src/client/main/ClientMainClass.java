package client.main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import presentation.BlockrPanel;

public class  ClientMainClass {

	private static BlockrPanel blockrPanel;
	
	private static Class<?> ImplementationGameWorld;

	public static void main(String[] args) {
		
		if(args.length > 0) {
			try {
				ClassLoader loader = ClientMainClass.class.getClassLoader();
				ImplementationGameWorld = loader.loadClass(args[0]);
	            
				JFrame frame = new JFrame("Blockr");
				blockrPanel = new BlockrPanel();
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
	
	public static Class<?> getImplementationClass() {
		return ImplementationGameWorld;
	}

}
