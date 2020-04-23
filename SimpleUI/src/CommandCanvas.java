import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import game_world.ImplementationGameWorld;
import game_world.api.PredicateResult;

public class CommandCanvas extends Canvas implements MouseListener{

	private static final long serialVersionUID = 1L;

	private ImplementationGameWorld iGameWorld;
	private GameWorldCanvas gameWorldC;

	private ArrayList<ActionButton> actionButtons = new ArrayList<ActionButton>();
	private ArrayList<PredicateButton> predicateButtons = new ArrayList<PredicateButton>();
	
	private int topOffset = 100;
	private int actionXOffset = 100;
	private int predicateXOffset = ActionButton.width + actionXOffset + 30;
	
	private int seperation = 10;

	public CommandCanvas(ImplementationGameWorld iGameWorld, GameWorldCanvas gameWorldC) {
		this.iGameWorld = iGameWorld;
		this.gameWorldC = gameWorldC;

		List<String> actions = iGameWorld.getAllActions();
		List<String> predicates = iGameWorld.getAllPRedicates();

		int index = 0;
		
		this.addMouseListener(this);

		for (String action : actions) {
			actionButtons.add(new ActionButton(action, new Vector(actionXOffset, topOffset + (ActionButton.height + seperation)*index++)));
		}
		
		index = 0;
		for (String predicate : predicates) {
			predicateButtons.add(new PredicateButton(predicate, new Vector(predicateXOffset, topOffset + (PredicateButton.height + seperation)*index++)));
		}
		
		System.out.println("TEST");
	}
	
	@Override
	public void paint(Graphics g) {
		for(ActionButton b : actionButtons) {
			b.draw(g);
		}
		for(PredicateButton p : predicateButtons) {
			p.draw(g);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Mouse pressed " + e.getX());
		Vector pos = new Vector(e.getX(), e.getY());
		for(ActionButton b: actionButtons) {
			if(b.collidesWith(pos)) {
				b.execute(iGameWorld);
				gameWorldC.repaint();
			}
		}
		for(PredicateButton p: predicateButtons) {
			if(p.collidesWith(pos)) {
				PredicateResult ans = p.evaluate(iGameWorld);
				System.out.println("predicateResult: " + ans.toString());
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
