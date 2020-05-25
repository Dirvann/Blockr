package domain.block;

import command.ExecutionCommand;
import domain.GameController;
import game_world.api.Action;
import game_world.api.ActionResult;
/**
 * A class of Actionblocks that extends SequenceBlock.
 * An action block can perform actions and has a name.
 * 
 * @version 4.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public class ActionBlock extends SequenceBlock{
	
	String name;
	Action action;
	/**
	 * Create a ActionBlock with the given action.
	 * 
	 * @param action
	 * 		  The action for the actionblock.
	 * @post  The action is equal to the given action.
	 * 		  |new.action = action
	 * @post  The name is equal to the name of the given action.
	 * 		  |new.getName() = action.getName()
	 */
	protected ActionBlock(Action action) {
		this.action = action;
		this.name = action.getName();
	}
	
	/**
	 * Perform the action of this block.
	 * 
	 * @param  GC 
	 * 		   The GameController where the block is in.
	 * @throws Exception If action is not possible.
	 * @post   The action of the block will be performed.
	 */
	protected void performAction(GameController GC) throws Exception {
		if (GC == null) 
			System.out.println(this.name); //TODO: Thomas
		else {
			ActionResult result = GC.getGameWorldImplementation().executeAction(action);
			if (result == ActionResult.Illegal) throw new Exception("illegal move");
			GC.setExecutionCommand(new ExecutionCommand(null, null, null, GC));
		}
	}
	
	@Override
	protected Block execute(GameController GC) throws Exception {
		performAction(GC);
		return super.execute(GC);
	}
	

	@Override
	protected Block getNewBlockOfThisType() {
		return new ActionBlock(action);
	}

	@Override
	protected String getName() {
		return this.name;
	}
}
