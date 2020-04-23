package domain.block;

import game_world.ImplementationGameWorld;
import game_world.api.ActionResult;

public class ActionBlock extends SequenceBlock{
	
	String name;
	
	protected ActionBlock(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @param gameController The gameController where the block is in.
	 * @throws Exception If action is not possible.
	 * @post The action of the block will be performed
	 */
	protected void performAction(ImplementationGameWorld iGameWorld) throws Exception {
		
		
		if (iGameWorld == null) 
			System.out.println(this.name);
		else {
			ActionResult result = iGameWorld.executeAction(getName());
			if (result == ActionResult.Illegal) throw new Exception("illegal move");
			//TODO implement undo
			//IGC.setExecutionCommand(new turnRightCommand(null, null, null, gameController), gameController);
		}
	}
	
	@Override
	protected Block execute(ImplementationGameWorld iGameWorld) throws Exception {
		performAction(iGameWorld);
		
		if (this.getNextBlock() == null) {
			if (this.getSurroundingBlock() == null) {
				return null;
			}
			
			return this.getSurroundingBlock().getNextAfterLoop();
		}
		return this.getNextBlock();
	}
	
	@Override
	protected boolean hasValidExecutionColumn() {
		if (this.getNextBlock() != null) {
			return this.getNextBlock().hasValidExecutionColumn();
		} else {
			return true;
		}
	}

	@Override
	protected Block getNewBlockOfThisType() {
		// TODO Auto-generated method stub
		return new ActionBlock(this.getName());
	}

	@Override
	protected String getName() {
		return this.name;
	}
}
