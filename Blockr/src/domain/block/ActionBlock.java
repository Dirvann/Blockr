package domain.block;

import command.ActionBlockCommand;
import domain.GameController;
import domain.ImplementationGameController;
import game_world.api.ActionResult;
import game_world.api.FacadeGameWorld;

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
	protected void performAction(GameController GC) throws Exception {
		ImplementationGameController GCF = new ImplementationGameController();
		FacadeGameWorld iGameWorld = GCF.getGameWorldImplementation(GC);
		
		
		if (iGameWorld == null) 
			System.out.println(this.name);
		else {
			ActionResult result = iGameWorld.executeAction(getName());
			if (result == ActionResult.Illegal) throw new Exception("illegal move");
			IGC.setExecutionCommand(new ActionBlockCommand(null, null, null, GC), GC);
		}
	}
	
	@Override
	protected Block execute(GameController GC) throws Exception {
		performAction(GC);
		
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
