package presentation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import command.Command;
import command.DeleteBlock;
import command.DeleteFunctionDefinition;
import command.ExecutionCommand;
import command.MakeBlock;
import command.MakeFunctionCommand;
import command.disconnectCommand;
import domain.CommandProcessor;
import domain.ExecutionProcessor;
import domain.GameController;
import domain.ImplementationGameController;
import domain.Vector;
import domain.block.Block;
import domain.block.FunctionDefinition;
import domain.block.ImplementationBlock;
import game_world.api.FacadeGameWorld;
import game_world.api.Snapshot;
import presentation.block.FunctionCallBlockPresentation;
import presentation.block.FunctionDefinitionBlockPresentation;
import presentation.block.ImplementationPresentationBlock;
import presentation.block.PresentationBlock;

/**
 * Canvas used to draw the program blocks on. Also contains logic to handle
 * mouse and key presses.
 * 
 * @version 3.0
 * @author Andreas Awouters Thomas Van Erum Dirk Vanbeveren Geert Wesemael
 *
 */
public class BlockAreaCanvas extends Canvas {

	private static final long serialVersionUID = -6133416834652649432L;

	private final double panelProportion = 0.2;
	private PalettePresentation paletteP;
	private ProgramAreaPresentation programAreaP;

	private ImplementationBlock BF = new ImplementationBlock();
	private ImplementationPresentationBlock BFP = new ImplementationPresentationBlock();
	private FacadeGameWorld iGameWorld;
	private ImplementationGameController GC = new ImplementationGameController();

	private BlockrPanel blockrPanel;
	private PresentationBlock<?> selectedBlock = null;
	private String errorMessage = "";

	private Vector previousMousePos = null;
	private boolean mouseDown = false;

	// these are needed for redo and undo dragging commands
	private ExecutionProcessor exe = new ExecutionProcessor();
	private CommandProcessor cmd = new CommandProcessor();
	private Command preCommand = null;
	private Command postCommand = null;
	private Vector newPos = null;
	private Vector oldPos = null;

	private Snapshot startSnapshot;

	/**
	 * 
	 * 
	 * @param blockrPanel | panel to attach this blockAreaCanvas to
	 * @param iGameWorld  | Interface used by the panel
	 */
	public BlockAreaCanvas(BlockrPanel blockrPanel, FacadeGameWorld iGameWorld) {
		paletteP = new PalettePresentation(iGameWorld);
		programAreaP = new ProgramAreaPresentation(blockrPanel.getGameController());
		this.iGameWorld = iGameWorld;
		this.startSnapshot = iGameWorld.makeSnapshot();
		this.blockrPanel = blockrPanel;
		setErrorMessage("The error message will appear here!");
		MouseEventListener mel = new MouseEventListener(this);
		addMouseListener(mel);
		addMouseMotionListener(mel);
		addKeyListener(mel);
	}

	/**
	 * Draw the blockArea Canvas This includes the blocks from the palette and the
	 * blocks currently forming the program.
	 */
	public void paint(Graphics g) {
		// Draw vertical line to mark end of palette
		g.setColor(Color.BLACK);
		g.drawLine((int) (panelProportion * this.getWidth()), 0, (int) (panelProportion * this.getWidth()),this.getHeight());

		// Draw number of blocks left
		g.setFont(new Font("Arial", Font.PLAIN, (int) (this.getHeight() / 20)));
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

	/**
	 * Set the error message shown by the blockAreaCanvas
	 * 
	 * @param errorMessage | String of the errormessage to be shown
	 */
	private void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Handle a mouse button press at the given location
	 * 
	 * @param x | horizontal value of the location of the mouse press
	 * @param y | vertical value of the location of the mouse press
	 */
	public void handleMousePressed(int x, int y) {
		setErrorMessage("");
		Vector mousePos = new Vector(x, y);
		// Clicked block in palette
		PresentationBlock<?> paletteBlockP = paletteP.GetClickedPaletteBlock(mousePos);
		if (paletteBlockP != null && GC.getAmountOfBlocksLeft(blockrPanel.getGameController()) > 0) {
			copyPaletteBlockIntoProgramArea(paletteBlockP);
			System.out.println("New Block made of type: " + BF.getName(BFP.getBlock(selectedBlock)));
			this.stopExecution();
		}
		// Clicked block in programArea
		else {
			PresentationBlock<?> programBlockP = programAreaP.getBlockAtPosition(mousePos);
			if (programBlockP != null) {
				pickBlockUpFromProgramArea(programBlockP);
				this.stopExecution();
			}
		}
		previousMousePos = mousePos;
		this.mouseDown = true;
	}
	/**
	 * TODO
	 * 
	 * @param paletteBlockP
	 */
	private void copyPaletteBlockIntoProgramArea(PresentationBlock<?> paletteBlockP) {
		PresentationBlock<?> presentationCopy = BFP.makeCopy(paletteBlockP);
		selectedBlock = presentationCopy;
		if (presentationCopy instanceof FunctionDefinitionBlockPresentation) {
			paletteP.addFunctionCallToPalette((FunctionDefinition) BFP.getBlock(presentationCopy));
			this.preCommand = new MakeFunctionCommand(blockrPanel.getGameController(), (FunctionDefinitionBlockPresentation) presentationCopy, paletteP);
		}
		else {
			this.preCommand = new MakeBlock(blockrPanel.getGameController(), presentationCopy);
		}
		GC.addBlockToProgramArea(blockrPanel.getGameController(), presentationCopy);
		this.oldPos = BFP.getPosition(presentationCopy);
	}
	/**
	 * TODO
	 * 
	 * @param programBlockP
	 */
	private void pickBlockUpFromProgramArea(PresentationBlock<?> programBlockP) {
		selectedBlock = programBlockP;
		this.preCommand = new disconnectCommand(BFP.getBlock(programBlockP), blockrPanel.getGameController());
		GC.disconnect(BFP.getBlock(selectedBlock), blockrPanel.getGameController());
		this.oldPos = BFP.getPosition(programBlockP);
	}
	
	/**
	 * Handle the mouse being dragged to the given location
	 * 
	 * @param x | horizontal value of the location the mouse is dragged to
	 * @param y | vertical value of the location the mouse is dragged to
	 */
	public void handleMouseDragged(int x, int y) {
		if (this.mouseDown && this.selectedBlock != null) {
			Vector moveDifference = new Vector(x - previousMousePos.getX(), y - previousMousePos.getY());
			BFP.addToPosition(selectedBlock, moveDifference);
			this.previousMousePos = new Vector(x, y);
			repaint();
		}
	}

	/**
	 * handle a mouse being released at the given location
	 * 
	 * @param x | horizontal value of the location of the mouse release
	 * @param y | vertical value of the location of the mouse release
	 */
	public void handleMouseReleased(int x, int y) {
		Vector mousePos = new Vector(x, y);
		if (this.selectedBlock != null) {
			// Delete if over palette
			if (mousePos.getX() < (int) (panelProportion * this.getWidth())) {
				removeSelectedBlockFromProgramArea();
			} 
			// Move block
			else {
				dropBlockInProgramArea();
			}
			this.cmd.dragCommand(oldPos, newPos, selectedBlock, preCommand, postCommand);
		}
		// resetting undo redo info, command construction is finished
		oldPos = null;
		newPos = null;
		preCommand = null;
		postCommand = null;

		this.mouseDown = false;
		this.selectedBlock = null;
		repaint();
	}
	
	/**
	 * TODO
	 */
	private void removeSelectedBlockFromProgramArea() {
		this.newPos = BFP.getPosition(selectedBlock);
		if (selectedBlock instanceof FunctionDefinitionBlockPresentation) {
			this.postCommand = new DeleteFunctionDefinition(blockrPanel.getGameController(), (FunctionDefinitionBlockPresentation) selectedBlock, paletteP);
			paletteP.removeFunctionCallFromPalette((FunctionDefinition) BFP.getBlock(selectedBlock));
		} else {
			this.postCommand = new DeleteBlock(blockrPanel.getGameController(), selectedBlock);
		}
		GC.removeBlockFromProgramArea(blockrPanel.getGameController(), selectedBlock);
	}
	
	/**
	 * TODO
	 */
	private void dropBlockInProgramArea() {
		this.newPos = BFP.getPosition(selectedBlock);
		// makes the command for snapping (undo/redo). null if not snapped
		this.postCommand = programAreaP.snapBlock(selectedBlock);
	}

	/**
	 * Handle a key press
	 * 
	 * @param key | KeyEvent to be handled
	 */
	public void handleKeyPressed(KeyEvent key) {
		int keyCode = key.getKeyCode();
		setErrorMessage("");
		GameController gameController = blockrPanel.getGameController();

		switch (keyCode) {
		case KeyEvent.VK_ESCAPE: // Esc
			this.stopExecution();
			iGameWorld.loadSnapshot(startSnapshot);
			break;

		case KeyEvent.VK_F4: // F4
			this.stopExecution();
			break;

		case KeyEvent.VK_F5: // F5
			try {
				setErrorMessage("");
				exe.addExecutionStep(GC.execute(blockrPanel.getGameController()));
				if (iGameWorld.goalReached()) {
					setErrorMessage("congratiolations!! You have beaten this level! \n Press F6 to start a new one. ");
				}
			} catch (Exception e1) {
				if (e1.getMessage() == null) {
					setErrorMessage("null returned");
				} else {
					setErrorMessage(e1.getMessage());
				}
				System.out.println("Execute in keyPressed failed");
			}
			break;

		case KeyEvent.VK_F6: // F6
			System.out.println("Changed gameWorld");
			blockrPanel.getPreferredGameWorldWidth();
			blockrPanel.getPreferredGameWorldHeight();
			iGameWorld.makeNewGameWorld();
			this.startSnapshot = iGameWorld.makeSnapshot();
			GC.setGameWorldImplementation(blockrPanel.getGameController(), iGameWorld);
			this.stopExecution();
			break;
		
		case KeyEvent.VK_Z: //CTRL + Z / CTRL + SHIFT + Z
			if(key.isControlDown()) {
				if(key.isShiftDown()) {
					if (GC.isExecuting(blockrPanel.getGameController())) {
						exe.redo();
					} else {
						this.cmd.redo();
					}
				}
				else {
					if (GC.isExecuting(blockrPanel.getGameController())) {
						exe.undo();
					} else {
						this.cmd.undo();
					}
				}
			}
			break;

		default:
			break;
		}
		repaint();
	}
	
	/**
	 * Stop the execution of the current program
	 */
	private void stopExecution() {
		GC.stopExecution(blockrPanel.getGameController());
		this.exe = new ExecutionProcessor();

		// TODO reset gameworld to original state snapshot
		iGameWorld.loadSnapshot(this.startSnapshot);

	}
}
