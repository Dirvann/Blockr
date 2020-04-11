package presentation;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main {

	private static BlockrPanel blockrPanel;

	public static void main(String[] args) {
		JFrame frame = new JFrame("Blockr");
		blockrPanel = new BlockrPanel();
		frame.add(blockrPanel);
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
