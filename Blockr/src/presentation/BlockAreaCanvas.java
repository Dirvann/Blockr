package presentation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import command.Command;
import command.DeleteBlock;
import command.DeleteFunctionDefinition;
import command.MakeBlock;
import command.MakeFunctionCommand;
import command.DisconnectCommand;
import domain.CommandProcessor;
import domain.ExecutionProcessor;
import domain.GameController;
import game_world.api.Vector;
import domain.block.Block;
import domain.block.FunctionDefinitionBlock;
import domain.block.ImplementationBlock;
import game_world.api.FacadeGameWorld;
import game_world.api.Snapshot;
import presentation.block.FunctionDefinitionBlockPresentation;
import presentation.block.ImplementationPresentationBlock;
import presentation.block.PresentationBlock;

/**
 * Canvas used to draw the program blocks on. Also contains logic to handle
 * mouse and key presses.
 * 
 * @version 4.0
 * @author Andreas Awouters 
 * 	       Thomas Van Erum 
 * 		   Dirk Vanbeveren 
 * 		   Geert Wesemael
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
	private GameController GC;

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
	 * Initialize the BlockAreaCanvas with the given Blockr Panel and Gameworld Facade.
	 * 
	 * @param  blockrPanel
	 * 		   The panel to attach this blockAreaCanvas to.
	 * @param  iGameWorld
	 * 		   Interface used by the panel.
	 * @post   The Palette Presentation is set to a new Palette Presentation with 
	 * 		   the given GameWorld Facade.
	 * 		   | new.PaletteP = new PalettePresentation(iGameWorld)
	 * @post   The ProgramArea Presentation is set to a new ProgramArea Presentation
	 * 		    with the GameController from the given BlockrPanel.
	 * 		   | new.programAreaP = new ProgramAreaPresentation(blockrPanel.getGameController())
	 * @post   The GameController is equal to the GameController of the given blockrPanel.
	 * 		   | new.GC == blockrPanel.getGameController()
	 * @post   The GameWorld Implementation is equal to the given iGameWorld.
	 * 		   | new.iGameWorld == iGameWorld
	 * @post   A snapshot of the given GameWorld is saved.
	 * 		   | new.startSnapshot == iGameWorld.makeSnapshot()
	 * @post   The error message is equal to "The error message will appear here!"
	 * 		   | new.errorMessage == "The error message will appear here!"
	 * @effect A MouseEventListener is set to this class. 
	 */
	public BlockAreaCanvas(BlockrPanel blockrPanel, FacadeGameWorld iGameWorld) {
		this.GC = blockrPanel.getGameController();
		this.paletteP = new PalettePresentation(iGameWorld);
		this.programAreaP = new ProgramAreaPresentation(blockrPanel.getGameController());
		this.iGameWorld = iGameWorld;
		this.startSnapshot = iGameWorld.makeSnapshot();
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
		g.drawString("" + GC.getAmountOfBlocksLeft(), getWidth() / 18, 17 * getHeight() / 18);
		g.setFont(new Font("Arial", Font.PLAIN, (int) (getHeight() / 40)));
		g.drawString(errorMessage, getWidth() / 4, 17 * getHeight() / 18);
		// Draw palette only if max number of blocks not reached
		if (GC.getAmountOfBlocksLeft() > 0) {
			paletteP.paint(g);
		}
		// Draw programArea
		programAreaP.paint(g);
		// Highlight block
		Block nextToExecute = GC.getNextBlockToExecute();
		if (nextToExecute != null) {
			BFP.highLight(BF.getPresentationBlock(nextToExecute), g);
		}
	}

	/**
	 * Set the error message shown by the blockAreaCanvas
	 * 
	 * @param errorMessage
	 * 		  String of the error message to be shown.
	 * @post  The error message is equal to the given errorMessage.
	 * 		  | new.errorMessage == errorMessage
	 */
	private void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Handle a mouse button press at the given location.
	 * 
	 * When a block is clicked in the palette a functional copy of
	 * that block is created at the mouse location. When a block in 
	 * the programArea is clicked, the block is picked up.
	 * 
	 * @param  x 
	 * 		   Horizontal value of the location of the mouse press.
	 * @param  y
	 * 		   Vertical value of the location of the mouse press.
	 * @post   The execution is stopped if the mouse is located where
	 * 		   a Palette Block or Program Block is located.
	 * 		   |if (paletteBlock on mouse position != null
	 * 		   |    && GC.getAmountOfBlocksLeft() > 0) 
	 * 		   |    || programBlock on mouse position != null)
	 * 		   |then stopExecution()
	 * @post   The previousMousePosition is the current position.
	 * 		   | new.previousMousePos == new Vector(x, y)
	 * @post   The mouse is down.
	 * 		   | new.mouseDown
	 * @effect Copy the presentation block where the mouse is located
	 * 		   and add it to the programArea if there are enough
	 * 		   blocks left.
	 * 		   |if (paletteBlock Presentation on mouse position != null
	 * 		   |    && GC.getAmountOfBlocksLeft() > 0))
	 * 		   |then copyPaletteBlockIntoProgramArea(paletteBlockP)
	 * @effect Pick the presentation block up where the mouse is located.
	 * 		   |if programBlock Presentation on mouse position != null
	 * 		   |then copyPaletteBlockIntoProgramArea(paletteBlockP)
	 */
	public void handleMousePressed(int x, int y) {
		setErrorMessage("");
		Vector mousePos = new Vector(x, y);
		// Clicked block in palette
		PresentationBlock<?> paletteBlockP = paletteP.GetClickedPaletteBlock(mousePos);
		if (paletteBlockP != null && GC.getAmountOfBlocksLeft() > 0) {
			copyPaletteBlockIntoProgramArea(paletteBlockP);
			System.out.println("New Block made of type: " + BF.getName(BFP.getBlock(selectedBlock)));
			stopExecution();
		}
		// Clicked block in programArea
		else {
			PresentationBlock<?> programBlockP = programAreaP.getBlockAtPosition(mousePos);
			if (programBlockP != null) {
				pickBlockUpFromProgramArea(programBlockP);
				stopExecution();
			}
		}
		this.previousMousePos = mousePos;
		this.mouseDown = true;
	}
	
	/**
	 * Make a copy from the given PresentationBlock and add it to the ProgramArea.
	 * If the given block is a Presentation of the FunctionDefinitonBlock, create
	 * a FunctionCall PresentationBlock in the Palette with the same ID.
	 * 
	 * @param  paletteBlockP
	 * 		   The block that is getting copied in the ProgramArea.
	 * @post   The oldPos is equal to the position of the given programBlockP.
	 *		   | new.oldPos == BFP.getPosition(programBlockP)
	 * @post   The selectedBlock is not connected to any blocks.
	 * 		   | selectedBlock.getPrevious() == null
	 * @post   If the given paletteBlockP is a FunctionDefinition PresentationBlock then
	 * 		   a FunctionCall with the same ID is in the palette.
	 * @effect The selectedBlock is a copy of the given paletteBlockP.
	 * 		   | selectedBlock = BFP.makeCopy(paletteBlockP)
	 * @effect A copy of the given paletteBlockP is added to the ProgramArea, 
	 * 		   the action is then saved as a preCommand.
	 * 		   If the given paletteBlockP is a FunctionDefinition PresentationBlock then
	 * 		   | preCommand = new MakeFunctionCommand(GC, presentationCopy, paletteP)
	 * 		   Otherwise
	 * 		   | preCommand = new MakeBlock(GC, presentationCopy)
	 */
	private void copyPaletteBlockIntoProgramArea(PresentationBlock<?> paletteBlockP) {
		PresentationBlock<?> presentationCopy = BFP.makeCopy(paletteBlockP);
		selectedBlock = presentationCopy;
		if (presentationCopy instanceof FunctionDefinitionBlockPresentation) {
			paletteP.addFunctionCallToPalette((FunctionDefinitionBlock) BFP.getBlock(presentationCopy));
			this.preCommand = new MakeFunctionCommand(GC, (FunctionDefinitionBlockPresentation) presentationCopy, paletteP);
		}
		else {
			this.preCommand = new MakeBlock(GC, presentationCopy);
		}
		GC.addBlockToProgramArea(presentationCopy);
		this.oldPos = BFP.getPosition(presentationCopy);
	}
	
	/**
	 * Pick up the selected block from the programArea.
	 * 
	 * @param  programBlockP
	 * 		   The presentation Block in the ProgramArea that we are going to pick up.
	 * @post   The selected block is equal to the given programBlockP.
	 * 		   | new.selectedBlock == programBlockP
	 * @post   The selectedBlock is not connected to any blocks.
	 * 		   |selectedBlock.getPrevious() == null
	 * @post   The oldPos is equal to the position of the given programBlockP.
	 *		   |new.oldPos == BFP.getPosition(programBlockP)
	 * @effect The selected block is disconnected if possible, the action is then
	 * 		   saved as a preCommand.
	 * 		   | preCommand = new disconnectCommand(BFP.getBlock(programBlockP),GC)
	 */
	private void pickBlockUpFromProgramArea(PresentationBlock<?> programBlockP) {
		selectedBlock = programBlockP;
		this.preCommand = new DisconnectCommand(BFP.getBlock(programBlockP), GC);
		GC.disconnect(BFP.getBlock(selectedBlock));
		this.oldPos = BFP.getPosition(programBlockP);
	}
	
	/**
	 * Handle the mouse being dragged to the given location.
	 * 
	 * When a block is selected, the block will be repainted at the new location.
	 * 
	 * @param x
	 * 	      horizontal value of the location the mouse is dragged to.
	 * @param y
	 * 	      vertical value of the location the mouse is dragged to.
	 * @post  The previous mouse position equal to the given position,
	 * 		  if the mouse is down and a block is selected.
	 * 		  | if (this.mouseDown && this.selectedBlock != null)
	 * 		  | then new.previousMousePos == new Vector(x, y)
	 * @post  The selectedBlock's location is moved to the current location,
	 * 		  if there is a block selected and the mouse is down.
	 * 		  | if (this.mouseDown && this.selectedBlock != null)
	 * 		  | then selectedBlock.getPosition().getX += x-previousMousePos.getX()
	 * 		  |  and selectedBlock.getPosition().getY += y-previousMousePos.getY()
	 * @effect repaint()
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
	 * Handle a mouse being released at the given location.
	 * 
	 * Reset all the command and information about the mouse.
	 * When a block is selected delete it when the given location
	 * is over the palette, drop the block otherwise.
	 * 
	 * @param  x
	 * 		   Horizontal value of the location of the mouse release.
	 * @param  y 
	 * 		   Vertical value of the location of the mouse release.
	 * @post   The mouse is not down.
	 * 		   | new.mouseDown
	 * @post   There is no block selected.
	 * 		   | new.selectedBlock == null
	 * @post   There are no pre and post commands.
	 * 		   | new.preCommand == null
	 *	       | new.postCommand == null
	 * @post   Theres is no old and new position for the mouse.
	 * 		   | new.oldPos == null;
	 *         | new.newPos == null;
	 * @effect When a block is selected and the given location is over the palette,
	 *         then the selected block is removed from the program area.
	 *         | if (this.selectedBlock != null && 
	 *         |	 mousePos.getX() < (panelProportion * this.getWidth()))
	 *         | then removeSelectedBlockFromProgramArea()
	 * @effect When a block is selected and the given location is not over the palette,
	 *         then the selected block is removed from the program area.
	 *         | if (this.selectedBlock != null && 
	 *         |	 !(mousePos.getX() < (panelProportion * this.getWidth())))
	 *         | then dropBlockInProgramArea()	
	 * @effect When a block is selected a dragCommand is added to the Command Processor.
	 * 		   | if (this.selectedBlock != null)
	 * 		   | then this.cmd.dragCommand(oldPos, newPos, selectedBlock, preCommand, postCommand)
	 * @effect repaint()
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
		this.oldPos = null;
		this.newPos = null;
		this.preCommand = null;
		this.postCommand = null;

		this.mouseDown = false;
		this.selectedBlock = null;
		repaint();
	}
	
	/**
	 * Remove the selected block from the programArea.
	 * 
	 * @post   the newPos is equal to the position of the selected block.
	 * 		   | new.newPos == BFP.getPosition(selectedBlock)
	 * @post   The selected block is not in the ProgramArea.
	 * @post   If the selectedBlock is a FunctionDefinition Block Presentation then there are no FunctionCalls
	 * 		   with the same ID as the selectedBlock in the ProgramArea.
	 * 		   | if selectedBlock instanceof FunctionDefinitionBlockPresentation
	 * 		   | then 0 ==  GC.getAllFunctionCallsOfID(ID of slectedBlock).size()
	 * @effect The selected block is removed if possible, the action is then
	 * 		   saved as a postCommand.
	 * 		   | postCommand = new DeleteBlock(GC, selectedBlock)
	 * 		   | OR postCommand = new DeleteFunctionDefinition(GC,selectedBlock)
	 */
	private void removeSelectedBlockFromProgramArea() {
		this.newPos = BFP.getPosition(selectedBlock);
		if (selectedBlock instanceof FunctionDefinitionBlockPresentation) {
			this.postCommand = new DeleteFunctionDefinition(GC, (FunctionDefinitionBlockPresentation) selectedBlock, paletteP);
			paletteP.removeFunctionCallFromPalette((FunctionDefinitionBlock) BFP.getBlock(selectedBlock));
		} else {
			this.postCommand = new DeleteBlock(GC, selectedBlock);
		}
		GC.removeBlockFromProgramArea(selectedBlock);
	}
	
	/**
	 * Drop the selected block in the ProgramArea.
	 * 
	 * @post   the newPos is equal to the position of the selected block.
	 * 		   |new.newPos == BFP.getPosition(selectedBlock)
	 * @effect The selected block is snapped if possible, the action is then
	 * 		   saved as a postCommand.
	 * 		   | postCommand = programAreaP.snapBlock(selectedBlock)
	 */
	private void dropBlockInProgramArea() {
		this.newPos = BFP.getPosition(selectedBlock);
		// makes the command for snapping (undo/redo). null if not snapped
		this.postCommand = programAreaP.snapBlock(selectedBlock);
	}

	/**
	 * Handle a key press.
	 * 
	 * @param key
	 * 		  KeyEvent to be handled.
	 * @post  When the keycode was equal to escape or F4, the game is not running.
	 * 		 | !GC.isExecuting()
	 * @post The GameWorld is equal to the GameWorld from the startSnapshot.
	 * 		 | new.iGameWorld.makeSnapshot() == startSnapshot
	 * @effect When the keycode is equal to F5 then run()
	 * @effect When the keycode is equal to F6 then newGameWorld()
	 * @effect When the keycode is equal to Z and CTRL and SHIFT are down then redo()
	 * @effect When the keycode is equal to Z and CTRL is down and SHIFT is not then redo()
	 */
	public void handleKeyPressed(KeyEvent key) {
		setErrorMessage("");
		switch (key.getKeyCode()) {
		case KeyEvent.VK_ESCAPE: // Esc
			stopExecution();
			iGameWorld.loadSnapshot(startSnapshot);
			break;

		case KeyEvent.VK_F4: // F4
			stopExecution();
			break;

		case KeyEvent.VK_F5: // F5
			run();
			break;

		case KeyEvent.VK_F6: // F6
			newGameWorld();
			break;
		
		case KeyEvent.VK_Z: //CTRL + Z / CTRL + SHIFT + Z
			if(key.isControlDown()) {
				if(key.isShiftDown()) {
					redo();
				}
				else {
					undo();
				}
			}
			break;
			
		default:
			break;
		}
		repaint();
	}
	
	/**
	 * When possible run the program, otherwise set the error message 
	 * to the reason why the game can't run.
	 *
	 * @post   The errorMessage is equal to
	 * 		   "congratiolations!! You have beaten this level! \n Press F6 to start a new one. "
	 * 		   when the goal is reached.
	 * 		   | if iGameWorld.goalReached()
	 * 		   | then new.errorMessage == 
	 * 		   |      "congratiolations!! You have beaten this level! \n Press F6 to start a new one. "
	 * @effect Execute the highlighted block and add the step to the Execution Processor.
	 * 		   | exe.addExecutionStep(GC.execute())
	 * @catch  Exception and set the message it as errorMessage.
	 * 		   | setErrorMessage(exception.getMessage())
	 */
	void run() {
		try {
			setErrorMessage("");
			exe.addExecutionStep(GC.execute());
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
	}
	
	/**
	 * Set a new random GameWorld.
	 * 
	 * @post   The startSnapshot is equal to a snapshot of the new gameworld.
	 * 		   |new.startSnapshot != old.startSnapshot
	 * @post   The GameControllers GameWorld Implementation is set to iGameWorld.
	 * 		   | GC.getGameWorldImplementation == iGameWorld
	 * @post   The game is not running.
	 * 		   | !GC.isExecuting()
	 * @effect A new GameWorld is set for the iGameWorld.
	 * 		   | iGameWorld.makeNewGameWorld()
	 */
	void newGameWorld() {
		System.out.println("Changed gameWorld");
		iGameWorld.makeNewGameWorld();
		this.startSnapshot = iGameWorld.makeSnapshot();
		GC.setGameWorldImplementation(iGameWorld);
		stopExecution();
	}
	
	/**
	 * Undo the last change.
	 * 
	 *@effect When the game is running undo the last execution.
	 *   	  | if GC.isExecuting()
	 * 		  | then exe.undo()
	 * 		  Otherwise undo the last command.
	 * 		  | cmd.undo()
	 */
	void undo() {
		if (GC.isExecuting()) {
			exe.undo();
		} else {
			cmd.undo();
		}
	}
	
	/**
	 * Redo the last undo.
	 * 
	 * @effect When the game is running redo the last undone execution.
	 * 		   | if GC.isExecuting()
	 * 		   | then exe.redo()
	 * 		   Otherwise redo the last undone command.
	 * 		   | cmd.redo()
	 */
	void redo() {
		if (GC.isExecuting()) {
			exe.redo();
		} else {
			cmd.redo();
		}
	}
		
	/**
	 * Stop the execution of the current program.
	 * 
	 * @post The game is not running.
	 * 		 | !GC.isExecuting()
	 * @post The exe is set to a new ExecutionProcessor.
	 * 		 | new.exe = new ExecutionProcessor()
	 * @post The GameWorld is equal to the GameWorld from the startSnapshot.
	 * 		 | new.iGameWorld.makeSnapshot() == startSnapshot
	 */
	private void stopExecution() {
		GC.stopExecution();
		this.exe = new ExecutionProcessor();
		iGameWorld.loadSnapshot(this.startSnapshot);
	}
}
