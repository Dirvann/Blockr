package domain.block;

import domain.block.abstract_classes.SingleSurroundingBlock;
import domain.block.block_types.*;

public class IfBlock extends SingleSurroundingBlock {

	public IfBlock() {

	}

	private ConditionBlock condition = null;
	private SequenceBlock next = null;

	/**
	 * 
	 * @param block the conditionBlock that needs to be evaluated.
	 * @return True if the condition can be evaluated, false if evaluation isn't
	 *         possible.
	 */
	public boolean hasValidCondition(ConditionBlock block) {
		if (block == null)
			return false;
		return block.isValidCondition();
	}

	/**
	 * 
	 * @return True if the complete condition is true, false otherwise.
	 * @throws Exception Condition is not valid. For example condition is 'null' or
	 *                   'not'
	 */
	public boolean evaluateCondition() throws Exception {
		if (condition == null)
			throw new Exception("No condition available!");
		return condition.evaluate();
	}

	//TODO: add is on the condition block -> get and set
	/**
	 * 
	 * @param blockToAdd The block that needs to be added.
	 * @param blockLeft The block just left to the position where blockToAdd needs to be added.
	 * @	puts blockToAdd between blockLeft and all the blocks to the right of blockLeft.
	 */
	public void addConditionBlock(ConditionBlock blockToAdd, ConditionBlock blockLeft) {

	}

	/**
	 * 
	 * @param block The first block (of a sequence) that needs to be removed.
	 * @	removes block and all of its successors (it keeps the sequence).
	 */
	public void removeConditionBlock(ConditionBlock block) {

	}

	//TODO: get and set -> add on sequenceBlock && Conditionblock -> sequenceBlock
	/**
	 * 
	 * @param blockToAdd The block which gets added.
	 * @param blockAbove The block just above the desired position where blockToAdd
	 *                   needs to be placed. @ Adds blockToAdd just under blockAbove
	 *                   and the blocks initially under blockAbove will be placed
	 *                   under blockToAdd.
	 */
	public void addBodyBlock(ConditionBlock blockToAdd, ConditionBlock blockAbove) {

	}

	/**
	 * 
	 * @param block The first block (of a sequence) that needs to be removed 
	 * @	Removes the given block and its successors and keeps the sequence of the successors.
	 */
	void removeBodyBlock(ConditionBlock block) {

	}
}
