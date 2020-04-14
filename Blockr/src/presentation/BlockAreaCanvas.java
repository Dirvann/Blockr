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

import command.Command;
import command.DeleteBlock;
import command.MakeBlock;
import command.disconnectCommand;
import domain.CommandProcessor;
import domain.GameController;
import domain.ImplementationGameController;
import domain.Vector;
import domain.block.Block;
import domain.block.ImplementationBlock;
import domain.game_world.ImplementationGameWorld;
import presentation.block.ImplementationPresentationBlock;
import presentation.block.PresentationBlock;

public class BlockAreaCanvas extends Canvas implements MouseListener, MouseMotionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6133416834652649432L;
	
	private final double panelProportion = 0.2;
	private PalettePresentation paletteP;
	private ProgramAreaPresentation programAreaP;
	
	private ImplementationBlock BF = new ImplementationBlock();
	private ImplementationPresentationBlock BFP = new ImplementationPresentationBlock();
	private ImplementationGameWorld GW = new ImplementationGameWorld();
	private ImplementationGameController GC = new ImplementationGameController(); 
	
	private BlockrPanel blockrPanel;
	
	private PresentationBlock<?> selectedBlock = null;
	
	private final String defaultMessage = "The error message will appear here!";
	String errorMessage = defaultMessage;

	Vector previousMousePos = null;
	boolean mouseDown = false;
	
	//these are needed for redo and undo dragging commands
	private CommandProcessor cmd = new CommandProcessor();
	private Command preCommand = null;
	private Command postCommand = null;
	private Vector newPos = null;
	private Vector oldPos = null;
	
	
	public BlockAreaCanvas(BlockrPanel blockrPanel) {
		paletteP = new PalettePresentation();
		programAreaP = new ProgramAreaPresentation(blockrPanel.getGameController());
		
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
		g.drawString("" + GC.getAmountOfBlocksLeft(blockrPanel.getGameController()), getWidth() / 18, 17 * getHeight() / 18);
		g.setFont(new Font("Arial", Font.PLAIN, (int) (getHeight() / 40)));
		g.drawString(errorMessage, getWidth() / 4, 17 * getHeight() / 18);
		
		// Draw palette only if max number of blocks not reached
		if (GC.getAmountOfBlocksLeft(blockrPanel.getGameController()) > 0) {
			paletteP.paint(g);
		}
		
		// Draw programArea
		programAreaP.paint(g);
		
		Block nextToExecute = GC.getNextBlockToExecute(blockrPanel.getGameController());
		if (nextToExecute != null) {
			BFP.highLight(BF.getPresentationBlock(nextToExecute), g);
		}
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		//repaint();
	}
	
	public void handleMousePressed(int x, int y) {
		this.errorMessage = "";
		Vector mousePos = new Vector(x, y);

		PresentationBlock<?> paletteBlockP = paletteP.GetClickedPaletteBlock(mousePos);
		// Clicked block in palette
		// Create functional copy of paletteBlock and add to programArea
		if (paletteBlockP != null && GC.getAmountOfBlocksLeft(blockrPanel.getGameController()) >= 0) {
			PresentationBlock<?> presentationCopy = BFP.makeCopy(paletteBlockP);
			GC.addBlockToProgramArea(blockrPanel.getGameController(), presentationCopy);
			selectedBlock = presentationCopy;
			
			//redo undo info collecting about current situation
			this.preCommand = new MakeBlock(blockrPanel.getGameController(), presentationCopy);
			this.oldPos = BFP.getPosition(presentationCopy);
			 
			System.out.println("New Block made of type: " + BF.getName(BFP.getBlock(selectedBlock) ));
			GC.stopExecution(blockrPanel.getGameController());
		}
		else {
			PresentationBlock<?> programBlockP = programAreaP.getBlockAtPosition(mousePos);
			if (programBlockP != null) {
				selectedBlock = programBlockP;
				
				//info collecting redo undo
				if (BF.getPreviousBlock(BFP.getBlock(programBlockP)) != null)
					this.preCommand = new disconnectCommand(BF.getPreviousBlock(BFP.getBlock(programBlockP)), BFP.getBlock(programBlockP), blockrPanel.getGameController());
				this.oldPos = BFP.getPosition(programBlockP);
						
						
				GC.disconnect(BFP.getBlock(selectedBlock), blockrPanel.getGameController());
			GC.stopExecution(blockrPanel.getGameController());
			}
		}

		previousMousePos = mousePos;
		this.mouseDown = true;
	}
	
	
	public void handleMouseDragged(int x, int y) {
		if (this.mouseDown && this.selectedBlock != null) {
			Vector moveDifference = new Vector(x - previousMousePos.getX(), y - previousMousePos.getY());
			BFP.addToPosition(selectedBlock, moveDifference);
			this.previousMousePos = new Vector(x, y);
			repaint();
		}
	}
	
	
	public void handleMouseReleased(int x, int y) {
		Vector mousePos = new Vector(x, y);

		if (this.selectedBlock != null) {
			//undo redo info collect
			this.newPos = BFP.getPosition(selectedBlock);
			
			// Check for snapping
			
			//makes the command for snapping (undo/redo). null if not snapped
			this.postCommand = programAreaP.snapBlock(selectedBlock);
			if (postCommand != null) {
				GC.removeTopLevelBlock(blockrPanel.getGameController(), BFP.getBlock(selectedBlock));
			}

			// Delete if over palette
			int paletteBorder = (int) (panelProportion * this.getWidth());
			if (mousePos.getX() < paletteBorder) {
				this.postCommand = new DeleteBlock(blockrPanel.getGameController(), selectedBlock);
				GC.removeBlockFromProgramArea(blockrPanel.getGameController(), selectedBlock);
			}


			this.cmd.dragCommand(oldPos, newPos, selectedBlock, preCommand, postCommand);

		}
		
		//resetting undo redo info, command construction is finished
		oldPos = null;
		newPos = null;
		preCommand = null;
		postCommand = null;
		
		this.mouseDown = false;
		this.selectedBlock = null;
		repaint();
	}
	
	public void handleKeyPressed(KeyEvent key) {
		int keyCode = key.getKeyCode();
		this.errorMessage = "";
		GameController gameController = blockrPanel.getGameController();
		
		switch (keyCode) {
		case KeyEvent.VK_ESCAPE: // Esc
			GC.stopExecution(gameController);
			GW.resetGameWorld(GC.getGameWorld(gameController));
			blockrPanel.redrawGameWorld();
			break;

		case KeyEvent.VK_F4: // F4
			GC.stopExecution(gameController);
			break;

		case KeyEvent.VK_F5: // F5
			try {
				setErrorMessage("The error message will appear here!");
				GC.execute(gameController);
				blockrPanel.redrawGameWorld();
			} catch (Exception e1) {
				if (e1.getMessage() == null) {
					setErrorMessage("null returned");
				}
				else {
					setErrorMessage(e1.getMessage());
				}
				System.out.println("Execute in keyPressed failed");
			}
			break;

		case KeyEvent.VK_F6: // F6
			System.out.println("Changed gameWorld");
			int width = blockrPanel.getPreferredGameWorldWidth();
			int height = blockrPanel.getPreferredGameWorldHeight();
			GC.setGameWorld(gameController, GW.makeRandomGameWorld(width, height));
			GC.stopExecution(gameController);
			blockrPanel.redrawGameWorld();
			break;

		default:
			break;
		}
		//redo ctrl + shift + z
		if (key.getKeyCode() == KeyEvent.VK_Z && key.isControlDown() && key.isShiftDown()) {
			this.cmd.redo();
		}
		// undo ctrl + z
		else if (key.getKeyCode() == KeyEvent.VK_Z && key.isControlDown() && !key.isShiftDown()){
			this.cmd.undo();
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
		handleKeyPressed(e);
		
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