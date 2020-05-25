package domain.block;

import java.util.ArrayList;
import java.util.List;

import domain.GameController;
import presentation.block.PresentationBlock;

/**
 * An abstract class of Blocks that has a presentation block and a surrounding block.
 * 
 * @version 3.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public abstract class Block {
	protected PresentationBlock<?> presentationBlock = null;
	protected SurroundingBlock surroundingBlock = null;

	/**
	 * Presentation of this block.
	 * 
	 * @return The presentation of this block. Returns null if none has been set.
	 */
	protected PresentationBlock<?> getPresentationBlock() {
		return this.presentationBlock;
	}

	/**
	 * Set the Presentation of this block.
	 * 
	 * @param presentationblock 
	 * 		  The desired presentation for this Block.
	 * @post The presentationBlock will be set
	 * 		 | new.getPresentationBlock = presentationBlock
	 */
	protected void setPresentationBlock(PresentationBlock<?> presentationBlock) {
		this.presentationBlock = presentationBlock;
	}

	/**
	 * Execute this block.
	 * 
	 * @param  gameController
	 * 		   The gamecontroller that executes the current block.
	 * @return next block in sequence
	 * @Post   every necessary command in the block is executed.
	 * @throws Exception 
	 * 		   If a block in the sequence is not executable.
	 */
	protected abstract Block execute(GameController GC) throws Exception;

	/**
	 * Return true if this block and entire column of blocks following this block can be executed
	 * 
	 * @return true if this block and entire column of blocks following this block can be executed.
	 */
	protected abstract boolean hasValidExecutionColumn();
	
	/**
	 * New block of this type without connections.
	 * 
	 * @return A new block of the same type without any connections.
	 */
	protected abstract Block getNewBlockOfThisType();

	/**
	 *  All of the blocks connected after this one.
	 * 
	 * @return all of the blocks connected after this one.
	 */
	protected List<Block> getAllNextBlocks() {
		List<Block> list = new ArrayList<Block>();
		list.add(this);
		if (this.getNextBlock() != null) {
			list.addAll(getNextBlock().getAllNextBlocks());
		}
		return list;
	}

	// get info about Connection functions and block attributes
	// _______________________________________________________//

	/**
	 * The name of the block.
	 * 
	 * @return The name of the block as a string
	 */
	protected abstract String getName();

	/**
	 * The block surrounding this block.
	 * 
	 * @return The surroundingBlock that surrounds this block. This block can be a
	 *         conditionBlock or a sequenceBlock. If there is no surroundingBlock,
	 *         null is returned.
	 */
	protected SurroundingBlock getSurroundingBlock() {
		return this.surroundingBlock;
	}

	/**
	 * Set the surrounding block to the given block.
	 * 
	 * @param block
	 * 		  The block that is set to surround this block.
	 * @Post block is set as the surrounding block of this block and all the next
	 *       ones after this block.
	 */
	protected void setSurroundingBlock(SurroundingBlock block) {
		Block iterator = this;
		while (iterator != null) {
			iterator.surroundingBlock = block;
			iterator = iterator.getNextBlock();
		}
	}

	/**
	 * Get the block that is connected after this this one.
	 * This can be on the right side or below the current one.
	 * 
	 * @return The block that is connected after this block (on the right side for
	 *         conditionBlocks or on the down side of sequenceBlocks).
	 */
	protected abstract Block getNextBlock();

	/**
	 * Set the next block to the given next block.
	 * 
	 * @param block
	 * 		  The block to become the next one.
	 * @return True if possible, else false.
	 * @Post (This->this.next->this.next.next...) will become
	 *       (this->Block->block.next->block.next.next...->this.next->this.next.next...)
	 */
	protected abstract boolean setNextBlock(Block block);

	/**
	 * Remove the next block.
	 * 
	 * @Post the next block will be removed. This.next.previous is set to null and
	 *       this.next is set to null.
	 */
	protected abstract void removeNextBlock();

	/**
	 * Return the block that is connected before this block.
	 * This can be on the left side or above the current one.
	 * 
	 * @return The block that is connected before this block (on the left side for
	 *         conditionBlocks or on the upper side of sequenceBlocks).
	 */
	protected abstract Block getBlockAbove();

	/**
	 * Return the last block of the connection chain to this block.
	 * 
	 * @return The last block of the connection chain to this block. (Most right
	 *         block for conditionBlocks and lowest block for sequenceBlocks)
	 */
	protected abstract Block getLastBlock();

	// Connect functions
	// _____________________________________________________________//

	/**
	 * Disconnect this block from all other blocks.
	 * 
	 * @Post Disconnects all of the connections between this block and its previous
	 *       block and surrounding block.
	 */
	protected abstract void disconnect();

	protected abstract Block getPrevious();
}
