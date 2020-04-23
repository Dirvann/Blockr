package simpleui;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import impl.root.ImplementationGameWorld;
import game_world.api.PredicateResult;
import simpleui.buttons.ActionButton;
import simpleui.buttons.PredicateButton;
import simpleui.buttons.ResetGameWorldButton;
import simpleui.buttons.SnapshotButton;
import simpleui.buttons.Button;
import simpleui.buttons.CreateSnapshotButton;
import simpleui.buttons.NewGameWorldButton;

public class CommandCanvas extends Canvas implements MouseListener{

	private static final long serialVersionUID = 1L;

	private ImplementationGameWorld iGameWorld;
	private GameWorldCanvas gameWorldC;

	private ArrayList<Button<?>> buttons = new ArrayList<Button<?>>();
	private ArrayList<SnapshotButton> snapshots = new ArrayList<SnapshotButton>();
	
	private int topOffset = Button.height * 2 + 50;
	private int actionXOffset = 20;
	private int predicateXOffset = Button.width + actionXOffset + 30;
	
	private int seperation = 10;

	public CommandCanvas(ImplementationGameWorld iGameWorld, GameWorldCanvas gameWorldC) {
		this.iGameWorld = iGameWorld;
		this.gameWorldC = gameWorldC;

		List<String> actions = iGameWorld.getAllActions();
		List<String> predicates = iGameWorld.getAllPRedicates();

		int index = 0;
		
		this.addMouseListener(this);

		for (String action : actions) {
			buttons.add(new ActionButton(action, new Vector(actionXOffset, topOffset + (ActionButton.height + seperation)*index++)));
		}
		
		index = 0;
		for (String predicate : predicates) {
			buttons.add(new PredicateButton(predicate, new Vector(predicateXOffset, topOffset + (PredicateButton.height + seperation)*index++)));
		}
		
		buttons.add(new NewGameWorldButton(new Vector(20, 20)));
		buttons.add(new ResetGameWorldButton(new Vector(20,20 + Button.height + 10)));
		
		buttons.add(new CreateSnapshotButton(new Vector(60 + Button.width * 2, 20)));

	}
	
	@Override
	public void paint(Graphics g) {
		for(Button<?> b : buttons) {
			b.draw(g);
		}
		
		List<String> snapNames = iGameWorld.getAllSnapshots();
		
		snapshots.clear();
		int index = 1;
		for(String name: snapNames) {
			snapshots.add(new SnapshotButton(name, new Vector(60 + Button.width * 2, 30 + (Button.height + 10)*index++)));
		}
		
		for(SnapshotButton b : snapshots) {
			b.draw(g);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Vector pos = new Vector(e.getX(), e.getY());
		if(e.getButton() == 3) {
			for(SnapshotButton b: snapshots) {
				if(b.collidesWith(pos)) {
					iGameWorld.removeSnapshot(b.getName());
					repaint();
					gameWorldC.repaint();
				}
			}
		} else {
			for(Button<?> b: buttons) {
				if(b.collidesWith(pos)) {
					b.execute(iGameWorld);
					repaint();
					gameWorldC.repaint();
				}
			}
			for(SnapshotButton b: snapshots) {
				if(b.collidesWith(pos)) {
					b.execute(iGameWorld);
					repaint();
					gameWorldC.repaint();
				}
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
