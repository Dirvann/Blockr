package client.main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import simpleui.MainPanel;

public class ClientMainClass {

	private static MainPanel mainPanel;

	public static void main(String[] args) {
		
		/*java.awt.EventQueue.invokeLater(() -> {
	          new SimpleGameCanvasWindow("SimpleGame").show();
	     });*/
		JFrame frame = new JFrame("SimpleUI");
		mainPanel = new MainPanel();
		frame.add(mainPanel);
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
