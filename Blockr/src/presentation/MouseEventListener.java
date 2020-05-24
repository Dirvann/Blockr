package presentation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
/**
 * A class that handles mouse and key presses.
 * 
 * @version 3.0
 * @author Andreas Awouters 
 * 	       Thomas Van Erum 
 * 		   Dirk Vanbeveren 
 * 		   Geert Wesemael
 *
 */
public class MouseEventListener implements MouseListener, MouseMotionListener, KeyListener {
	
	private BlockAreaCanvas blockAreaCanvas;

	public MouseEventListener(BlockAreaCanvas blockAreaCanvas){
		this.blockAreaCanvas = blockAreaCanvas;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		blockAreaCanvas.handleMousePressed(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		blockAreaCanvas.handleMouseReleased(e.getX(), e.getY());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		blockAreaCanvas.handleMouseDragged(e.getX(), e.getY());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		blockAreaCanvas.handleKeyPressed(e);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {;}

	@Override
	public void mouseEntered(MouseEvent e) {;}

	@Override
	public void mouseExited(MouseEvent e) {;}

	@Override
	public void mouseMoved(MouseEvent e) {;}

	@Override
	public void keyReleased(KeyEvent e) {;}

	@Override
	public void keyTyped(KeyEvent e) {;}

}
