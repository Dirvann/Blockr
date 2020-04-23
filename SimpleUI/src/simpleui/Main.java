package simpleui;
import java.awt.Canvas;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main {

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
