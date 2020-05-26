package client.main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;


import simpleui.MainPanel;

public class ClientMainClass {

	private static MainPanel mainPanel;
	private static Class<?> ImplementationGameWorld;

	public static void main(String[] args) {
		if(args.length > 0) {
			try {
				ClassLoader loader = ClientMainClass.class.getClassLoader();
				ImplementationGameWorld = loader.loadClass(args[0]);
	            
	            JFrame frame = new JFrame("SimpleUI");
	    		mainPanel = new MainPanel();
	    		frame.add(mainPanel);
	    		frame.pack();
	    		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    		frame.setVisible(true);
				
			}  catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Class<?> getImplementationClass() {
		return ImplementationGameWorld;
	}
	
	public static Class<?> getClas() {
		return ImplementationGameWorld;
	}

}
