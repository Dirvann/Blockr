package domain.block;

import command.ActionBlockCommand;
import domain.GameController;
import domain.ImplementationGameController;
import game_world.api.ActionResult;
import game_world.api.FacadeGameWorld;
/**
 * A class of Actionblocks that extends SequenceBlock.
 * An action block can perform actions and has a name.
 * 
 * @version 3.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public class ActionBlock extends SequenceBlock{
	
	String name;
	/**
	 * Create a ActionBlock with the given name.
	 * 
	 * @param name
	 * 		  The name for the actionblock.
	 * @post  The name is equal to the given name.
	 * 		  |new.getName() = name
	 */
	protected ActionBlock(String name) {
		this.name = name;
	}
	
	/**
	 * Perform the action of this block.
	 * 
	 * @param gameController 
	 * 		  The gameController where the block is in.
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
