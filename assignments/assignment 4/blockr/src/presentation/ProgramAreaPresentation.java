package presentation;

import java.awt.Graphics;
import java.util.List;

import command.Command;
import domain.GameController;
import domain.block.Block;
import domain.block.ImplementationBlock;
import game_world.api.Vector;
import presentation.block.ImplementationPresentationBlock;
import presentation.block.PresentationBlock;

/**
 * ProgramAreaPresentation is intended to be used as a part of a
 * BlockAreaCanvas. ProgramAreaPresentation handles activities concerning the
 * programArea.
 * 
 * @version 4.0
 * @author Andreas Awouters Thomas Van Erum Dirk Vanbeveren Geert Wesemael
 *
 */
public class ProgramAreaPresentation {

	private ImplementationPresentationBlock BFP = new ImplementationPresentationBlock();
	private ImplementationBlock BF = new ImplementationBlock();
	private GameController GC;

	/**
	 * Create a new instance of ProgramAreaPresentation
	 * 
	 * @param gameController | gameController that will handle action executions
	 */
	public ProgramAreaPresentation(GameController gameController) {
		this.GC = gameController;
	}

	/**
	 * Draw the blocks from the programArea on the given Graphics object
	 * 
	 * @param g | Graphics object to draw the programArea on
	 */
	public void paint(Graphics g) {
		List<Block> programAreaBlocks = GC.getCopyOfAllBlocks();
		for (Block pBlock : programAreaBlocks) {
			BFP.draw(g, BF.getPresentationBlock(pBlock));
		}
	}

	/**
	 * Returns the presentationBlock from the programArea if the presentationBlock
	 * covers this position
	 * 
	 * @param position | position to get the block from
	 * @return | presentationBlock at given position | null if no block exists at
	 *         given position
	 */
	public PresentationBlock<?> getBlockAtPosition(Vector position) {
		for (Block block : GC.getCopyOfAllBlocks()) {
			if (BFP.collidesWithPosition(position, BF.getPresentationBlock(block))) {
				return BF.getPresentationBlock(block);
			}
		}

		return null;
	}

	/**
	 * Tries to snap block to any block in the programarea. If the plug is close
	 * enough to a corresponding socket of another block in programArea, these
	 * blocks will snap and this function will return a Command that holds all the
	 * information to undo and redo this action.
	 * 
	 * 
	 * @param block
	 * @return Command that holds all the information to undo and redo this action.
	 * @post block is snapped if there is a corresponding socket close enough to the plug of the given block.
	 */
	public Command snapBlock(PresentationBlock<?> block) {
		Command cmd = null;
		for (Block blockListElement : GC.getCopyOfAllBlocks()) {
			PresentationBlock<?> pBlock = BF.getPresentationBlock(blockListElement);
			cmd = BFP.canSnap(pBlock, block, this.GC);
			if (cmd != null) {
				return cmd;
			}
		}
		return cmd;
	}

}
