package presentation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

abstract public class MouseEventListener implements MouseListener, MouseMotionListener, KeyListener {
	@Override
	public void mouseClicked(MouseEvent e) {;}

	@Override
	public void mouseEntered(MouseEvent e) {;}

	@Override
	public void mouseExited(MouseEvent e) {;}

	@Override
	public abstract void mousePressed(MouseEvent e);

	@Override
	public abstract void mouseReleased(MouseEvent e);

	@Override
	public abstract void mouseDragged(MouseEvent e);

	@Override
	public void mouseMoved(MouseEvent e) {;}

	@Override
	public abstract void keyPressed(KeyEvent e);

	@Override
	public void keyReleased(KeyEvent e) {;}

	@Override
	public void keyTyped(KeyEvent e) {;}

}
