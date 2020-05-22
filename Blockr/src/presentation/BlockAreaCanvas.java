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
import command.DeleteFunction;
import command.ExecutionCommand;
import command.MakeBlock;
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
	private FacadeGameWorld iGameWorld;
	private ImplementationGameController GC = new ImplementationGameController();

	private BlockrPanel blockrPanel;

	private PresentationBlock<?> selectedBlock = null;

	private final String defaultMessage = "The error message will appear here!";
	String errorMessage = defaultMessage;

	Vector previousMousePos = null;
	boolean mouseDown = false;

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
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
	}

	/**
	 * Draw the blockArea Canvas This includes the blocks from the palette and the
	 * blocks currently forming the program.
	 */
	public void paint(Graphics g) {
		// Draw vertical line to mark end of palette
		g.setColor(Color.BLACK);
		g.drawLine((int) (panelProportion * this.getWidth()), 0, (int) (panelProportion * this.getWidth()),
				this.getHeight());

		// Draw number of blocks left
		g.setFont(new Font("Arial", Font.PLAIN, (int) (this.getHeight() / 20)));
		g.drawString("" + GC.getAmountOfBlocksLeft(blockrPanel.getGameController()), getWidth() / 18,
				17 * getHeight() / 18);
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
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		// repaint();
	}

	/**
	 * Handle a mouse button press at the given location
	 * 
	 * @param x | horizontal value of the location of the mouse press
	 * @param y | vertical value of the location of the mouse press
	 */
	public void handleMousePressed(int x, int y) {
		this.errorMessage = "";
		Vector mousePos = new Vector(x, y);

		PresentationBlock<?> paletteBlockP = paletteP.GetClickedPaletteBlock(mousePos);
		// Clicked block in palette
		// Create functional copy of paletteBlock and add to programArea
		if (paletteBlockP != null && GC.getAmountOfBlocksLeft(blockrPanel.getGameController()) > 0) {
			PresentationBlock<?> presentationCopy = BFP.makeCopy(paletteBlockP);
			if (presentationCopy instanceof FunctionDefinitionBlockPresentation) {
				paletteP.addFunctionCallToPalette((FunctionDefinition) BFP.getBlock(presentationCopy), iGameWorld);
			}
			GC.addBlockToProgramArea(blockrPanel.getGameController(), presentationCopy);
			selectedBlock = presentationCopy;

			// redo undo info collecting about current situation
			this.preCommand = new MakeBlock(blockrPanel.getGameController(), presentationCopy);
			this.oldPos = BFP.getPosition(presentationCopy);

			System.out.println("New Block made of type: " + BF.getName(BFP.getBlock(selectedBlock)));
			this.stopExecution();
		} else {
			PresentationBlock<?> programBlockP = programAreaP.getBlockAtPosition(mousePos);
			if (programBlockP != null) {
				selectedBlock = programBlockP;

				// info collecting redo undo
				this.preCommand = new disconnectCommand(BF.getPreviousBlock(BFP.getBlock(programBlockP)),
						BFP.getBlock(programBlockP), blockrPanel.getGameController());
				this.oldPos = BFP.getPosition(programBlockP);

				GC.disconnect(BFP.getBlock(selectedBlock), blockrPanel.getGameController());
				this.stopExecution();
			}
		}

		previousMousePos = mousePos;
		this.mouseDown = true;
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
			// undo redo info collect
			this.newPos = BFP.getPosition(selectedBlock);

			// Check for snapping

			// Delete if over palette
			int paletteBorder = (int) (panelProportion * this.getWidth());
			if (mousePos.getX() < paletteBorder) {
				if (BFP.getBlock(selectedBlock) instanceof FunctionDefinition) {
					this.postCommand = new DeleteFunction(blockrPanel.getGameController(), selectedBlock);
					paletteP.removeFunctionCallFromPalette((FunctionDefinition) BFP.getBlock(selectedBlock),
							iGameWorld);
				} else {
					this.postCommand = new DeleteBlock(blockrPanel.getGameController(), selectedBlock);
				}
				GC.removeBlockFromProgramArea(blockrPanel.getGameController(), selectedBlock);
			} else {
				// makes the command for snapping (undo/redo). null if not snapped
				this.postCommand = programAreaP.snapBlock(selectedBlock);
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
	 * Handle a key press
	 * 
	 * @param key | KeyEvent to be handled
	 */
	public void handleKeyPressed(KeyEvent key) {
		int keyCode = key.getKeyCode();
		this.errorMessage = "";
		GameController gameController = blockrPanel.getGameController();

		switch (keyCode) {
		case KeyEvent.VK_ESCAPE: // Esc
			this.stopExecution();
			iGameWorld.loadSnapshot(startSnapshot);
			blockrPanel.redrawGameWorld();
			break;

		case KeyEvent.VK_F4: // F4
			this.stopExecution();
			break;

		case KeyEvent.VK_F5: // F5
			try {
				setErrorMessage("");
				ExecutionCommand exeCmd = GC.execute(gameController);
				exe.addExecutionStep(exeCmd);
				blockrPanel.redrawGameWorld();

				/*
				 * if (iGameWorld.robotOnGoal(GC.getGameWorldImplementation(blockrPanel.
				 * getGameController()))){
				 * setErrorMessage("congratiolations!! You have beaten this level! \n Press F6 to start a new one. "
				 * ); }
				 */
				// if (!GC.isExecuting(gameController)) {
				// this.stopExecution();
				// }
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
			// TODO create new gameworld
			iGameWorld.makeNewGameWorld();
			this.startSnapshot = iGameWorld.makeSnapshot();
			GC.setGameWorldImplementation(gameController, iGameWorld);
			this.stopExecution();
			blockrPanel.redrawGameWorld();
			break;

		default:
			break;
		}
		// redo ctrl + shift + z
		if (key.getKeyCode() == KeyEvent.VK_Z && key.isControlDown() && key.isShiftDown()) {
			if (GC.isExecuting(blockrPanel.getGameController())) {
				exe.redo();
				blockrPanel.redrawGameWorld();
			} else {
				this.cmd.redo();
			}
		}
		// undo ctrl + z
		else if (key.getKeyCode() == KeyEvent.VK_Z && key.isControlDown() && !key.isShiftDown()) {
			if (GC.isExecuting(blockrPanel.getGameController())) {
				exe.undo();
				blockrPanel.redrawGameWorld();
			} else {
				this.cmd.undo();
			}
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
