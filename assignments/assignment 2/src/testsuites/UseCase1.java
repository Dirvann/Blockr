package testsuites;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.junit.jupiter.api.Test;

import domain.GameController;
import domain.game_world.GameWorld;
import domain.game_world.Vector;
import facade.Implementation;
import presentation.PalettePresentation;
import presentation.Presentation;
import presentation.ProgramAreaPresentation;
import presentation.block.PresentationBlock;
/**
 * Our current implementation does not allow to test this without opening a window.
 * Planned changes:
 * 	1. Make a class that can recieve input in between the key events and the presentation class.
 *  2. Everything needs to be independent of canvas for testing. 
 *  	I should be able to use the class from (1) and block locations to test without the GUI.
 */
class UseCase1 {
	
//	private static final long serialVersionUID = 1109041362094124173L;
//	static int width = 800;
//	static int height = 600;
//	static Canvas canvas;
//	
//	double panelProportion = 0.2;
//	double codeProportion = 0.5;
//	double worldProportion = 0.3;
//		
//	GameWorld gameWorld;
//	
//	Vector mouseDownStartPosition = new Vector(0,0);
//	boolean mouseDown = false;
//	PresentationBlock selectedBlock = null;
//	Vector previousMousePos = null;
//	
//	GameController gameController;
//	PalettePresentation paletteP;
//	ProgramAreaPresentation programAreaP;
//	Implementation GA; // GameInterface
	
	@Test
	void test() {
		fail("Unfinished structure");
//        JFrame frame = new JFrame("Blockr");
//        canvas = new Presentation();
//        canvas.setSize(width, height);
//        frame.add(canvas);
//        frame.pack();
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//    	try {
//            Robot robot = new Robot();
//            // Simulate a mouse click
//            robot.mousePress(InputEvent.BUTTON1_MASK);
//            robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
//            // Simulate a key press
//        	robot.mouseMove(11,11);
//        	robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
//        	robot.mouseMove(11,11);
//        	robot.mouseMove(300,10);
//        	System.out.println("qsfd");
//        	robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
//        	System.out.println("qsfd");
//
//
//	    } catch (AWTException e) {
//	            e.printStackTrace();
//	        	System.out.println("qdfqsdf");
//
//	    }
    	
	}
}