package presentation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import domain.GameController;
import domain.block.block_types.Block;
import domain.game_world.GameWorld;
import domain.game_world.Vector;
import presentation.block.PresentationBlock;

public class BlockAreaCanvas extends Canvas implements MouseListener, MouseMotionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6133416834652649432L;
	
	private final double panelProportion = 0.2;
	private PalettePresentation paletteP;
	private ProgramAreaPresentation programAreaP;
	
	private BlockrPanel blockrPanel;
	
	private PresentationBlock<?> selectedBlock = null;
	
	Vector previousMousePos = null;
	boolean mouseDown = false;
	
	
	public BlockAreaCanvas(BlockrPanel blockrPanel) {
		paletteP = new PalettePresentation();
		programAreaP = new ProgramAreaPresentation();
		
		this.blockrPanel = blockrPanel;
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
	}
	
	
	public void paint(Graphics g) {
		// Draw vertical line to mark end of palette
		g.setColor(Color.BLACK);
		g.drawLine(
				(int) (panelProportion * this.getWidth()), 
				0, 
				(int) (panelProportion * this.getWidth()),
				this.getHeight()
				);
		
		// Draw number of blocks left
		g.setFont(new Font("Arial", Font.PLAIN, (int) (this.getHeight()/20)));
		g.drawString("" + programAreaP.getBlocksLeft(),getWidth()/18, 17 * getHeight()/18);
		
		// Draw palette only if max number of blocks not reached
		if (programAreaP.getBlocksLeft() > 0) {
			paletteP.paint(g);
		}
		
		// Draw programArea
		programAreaP.paint(g);
		
		Block nextToExecute = blockrPanel.getGameController().getNextBlockToExecute();
		if (nextToExecute != null) {
			nextToExecute.getPresentationBlock().highLight(g);
		}
	}

	
	public void handleMousePressed(int x, int y) {
		Vector mousePos = new Vector(x, y);

		PresentationBlock<?> paletteBlockP = paletteP.GetClickedPaletteBlock(mousePos);
		// Clicked block in palette
		// Create functional copy of paletteBlock and add to programArea
		if (paletteBlockP != null && programAreaP.getBlocksLeft() >= 0) {
			programAreaP.decreaseBlocksLeft();
			PresentationBlock<?> presentationCopy;
			presentationCopy = paletteBlockP.getNewBlockOfThisType();
			programAreaP.addBlock(presentationCopy);
			selectedBlock = presentationCopy;
			System.out.println("presentationCopy.getBlock == null: " + presentationCopy.getBlock() == null);
		}

		PresentationBlock<?> programBlockP = programAreaP.getBlockAtPosition(mousePos);
		if (programBlockP != null) {
			selectedBlock = programBlockP;
			blockrPanel.getGameInterface().disconnect(selectedBlock.getBlock());
		}

		previousMousePos = mousePos;
		this.mouseDown = true;
	}
	
	
	public void handleMouseDragged(int x, int y) {
		if (this.mouseDown && this.selectedBlock != null) {
			Vector moveDifference = new Vector(x - previousMousePos.getX(), y - previousMousePos.getY());
			// selectedBlock.setPositionByDifference(moveDifference);
			selectedBlock.setPosition(selectedBlock.getPosition().add(moveDifference));
			this.previousMousePos = new Vector(x, y);
			repaint();
		}
	}
	
	
	public void handleMouseReleased(int x, int y) {
		Vector mousePos = new Vector(x, y);

		if (this.selectedBlock != null) {
			// Check for snapping
			// TODO: replace with new snapping code
			boolean snapped = programAreaP.snapBlock(selectedBlock);
			GameController gameController = blockrPanel.getGameController();

			if (!snapped) {
				if (!gameController.isTopLevelBlock(selectedBlock.getBlock())) {
					gameController.addTopLevelBlock(selectedBlock.getBlock());
				}
			} else {
				if (gameController.isTopLevelBlock(selectedBlock.getBlock())) {
					gameController.removeTopLevelBlock(selectedBlock.getBlock());
				}
			}

			// Delete if over palette
			int paletteBorder = (int) (panelProportion * this.getWidth());
			if (mousePos.getX() < paletteBorder) {
				if (gameController.isTopLevelBlock(selectedBlock.getBlock())) {
					gameController.removeTopLevelBlock(selectedBlock.getBlock());
				}

				selectedBlock.getBlock().removeFromProgramAreaPresentationRecursively(programAreaP);
			}

		}

		this.mouseDown = false;
		this.selectedBlock = null;
		repaint();
	}
	
	public void handleKeyPressed(int keyCode) {
		GameController gameController = blockrPanel.getGameController();
		
		switch (keyCode) {
		case 27: // Esc
			gameController.stopExecution();
			gameController.resetWorld();
			blockrPanel.redrawGameWorld();
			break;

		case 115: // F4
			gameController.stopExecution();
			break;

		case 116: // F5
			try {
				gameController.execute();
				blockrPanel.redrawGameWorld();
			} catch (Exception e1) {
				System.out.println("Execute in keyPressed failed");
			}
			break;

		case 117: // F6
			System.out.println("Changed gameWorld");
			int width = blockrPanel.getPreferredGameWorldWidth();
			int height = blockrPanel.getPreferredGameWorldHeight();
			gameController.setGameWorld(new GameWorld(width, height));
			blockrPanel.redrawGameWorld();
			break;

		default:
			break;
		}
		repaint();
	}

	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		this.handleMousePressed(e.getX(), e.getY());	
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		this.handleMouseReleased(e.getX(), e.getY());
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		this.handleMouseDragged(e.getX(), e.getY());
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		handleKeyPressed(e.getKeyCode());
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
