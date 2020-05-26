package simpleui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import game_world.api.Action;
import game_world.api.FacadeGameWorld;
import game_world.api.Predicate;
import game_world.api.Snapshot;
import simpleui.buttons.ActionButton;
import simpleui.buttons.Button;
import simpleui.buttons.CreateSnapshotButton;
import simpleui.buttons.NewGameWorldButton;
import simpleui.buttons.PredicateButton;
import simpleui.buttons.ResetGameWorldButton;
import simpleui.buttons.SnapshotButton;
import game_world.api.Vector;

public class CommandCanvas extends Canvas implements MouseListener {

	private static final long serialVersionUID = 1L;

	private FacadeGameWorld iGameWorld;
	private ArrayList<Button<?>> buttons = new ArrayList<Button<?>>();
	private ArrayList<SnapshotButton> snapshots = new ArrayList<SnapshotButton>();
	private ArrayList<Snapshot> snapshotData = new ArrayList<Snapshot>();

	private int topOffset = Button.height * 2 + 50;
	private int actionXOffset = 20;
	private int predicateXOffset = Button.width + actionXOffset + 30;

	private int seperation = 10;
	
	private String message = "";

	public CommandCanvas(FacadeGameWorld iGameWorld, GameWorldCanvas gameWorldC) {
		this.iGameWorld = iGameWorld;
		List<Action> actions = iGameWorld.getAllActions();
		List<Predicate> predicates = iGameWorld.getAllPRedicates();

		int index = 0;

		this.addMouseListener(this);

		for (Action action : actions) {
			buttons.add(new ActionButton(action,
					new Vector(actionXOffset, topOffset + (ActionButton.height + seperation) * index++)));
		}

		index = 0;
		for (Predicate predicate : predicates) {
			buttons.add(new PredicateButton(predicate,
					new Vector(predicateXOffset, topOffset + (PredicateButton.height + seperation) * index++)));
		}

		buttons.add(new NewGameWorldButton(new Vector(20, 20)));
		buttons.add(new ResetGameWorldButton(new Vector(20, 20 + Button.height + 10)));

		buttons.add(new CreateSnapshotButton(new Vector(60 + Button.width * 2, 20)));

	}

	@Override
	public void paint(Graphics g) {
		for (Button<?> b : buttons) {
			b.draw(g);
		}

		snapshots.clear();
		int index = 1;
		for (Snapshot snap : snapshotData) {
			snapshots.add(new SnapshotButton(Integer.toString(snapshotData.indexOf(snap)),
					new Vector(60 + Button.width * 2, 30 + (Button.height + 10) * index++), snap));
		}

		for (SnapshotButton b : snapshots) {
			b.draw(g);
		}
		
		if(message != "") {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.PLAIN, 30));
			g.drawString(message,20, getHeight() - 30);
			message = "";
		
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Vector pos = new Vector(e.getX(), e.getY());
		if (e.getButton() == 3) {
			for (SnapshotButton b : snapshots) {
				if (b.collidesWith(pos)) {
					snapshotData.remove(b.getSnapshot());
					repaint();
				}
			}
		} else {
			for (Button<?> b : buttons) {
				if (b.collidesWith(pos)) {
					if (b instanceof CreateSnapshotButton) {
						snapshotData.add((Snapshot)b.execute(iGameWorld));
					} else if (b instanceof PredicateButton){
						this.message = b.execute(iGameWorld).toString();
					} else {
						b.execute(iGameWorld);
						
					}
					repaint();

				}
			}
			for (SnapshotButton b : snapshots) {
				if (b.collidesWith(pos)) {
					b.execute(iGameWorld);
					repaint();
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
