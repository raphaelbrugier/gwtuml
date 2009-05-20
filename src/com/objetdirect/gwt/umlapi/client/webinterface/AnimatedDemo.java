package com.objetdirect.gwt.umlapi.client.webinterface;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.engine.Scheduler;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;

/**
 * This class is an exemple of an animated contruction of an uml diagram
 * It shows how to use {@link Keyboard} and {@link Mouse}.
 *   
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class AnimatedDemo extends AbsolutePanel {

    private static final int DELAY = 100;

    private static class Cursor {
	
	 private static GfxObject cursor = GfxManager.getPlatform().buildImage("cursor.png");
	 private static Point location = Point.getOrigin();
	 public static void add() {
	     GfxManager.getPlatform().addToCanvas(Session.getActiveCanvas().getDrawingCanvas(), cursor, new Point(0, 0));
	 }
	 public static void move(final Point newLocation) {
	     GfxManager.getPlatform().translate(cursor, Point.substract(newLocation, location));
	     location = newLocation.clonePoint();
	 }
    	 public static void rem() {
	     GfxManager.getPlatform().removeFromCanvas(Session.getActiveCanvas().getDrawingCanvas(), cursor);
	 }
    }
    /**
     * Constructor of the animated demo, used to test the  
     *
     * @param canvas The {@link UMLCanvas} where to add the demo uml artifacts 
     */
    public AnimatedDemo(final UMLCanvas canvas) {
	super();
	Log.trace("Creating Animated demo");

	DeferredCommand.addCommand(new Command() {

	    @Override
	    public void execute() {
		HotKeyManager.setInputEnabled(false);
		Session.getActiveCanvas().setMouseEnabled(false);
		Cursor.add();
		
		final Point classOrigin = new Point(50, 50);
		final Point classTarget = new Point(350, 150);
		
		new Scheduler.Task("AnimatedDemo", DELAY) {@Override public void process() {
		    Mouse.move(classOrigin, NativeEvent.BUTTON_LEFT, false, false, false, false);
		    Cursor.move(classOrigin);
		}};
		new Scheduler.Task("AnimatedDemo", DELAY) {@Override public void process() {
		    Keyboard.push('C');
		}};
		
		move(classOrigin, classTarget);			

		new Scheduler.Task("AnimatedDemo", DELAY) {@Override public void process() {
		    Mouse.press(null, Point.getOrigin(), NativeEvent.BUTTON_LEFT, false, false, false, false);
		    Mouse.release(null, Point.getOrigin(), NativeEvent.BUTTON_LEFT, false, false, false, false);
		    Cursor.move(Point.getOrigin());
		}};

		final Point objectOrigin = new Point(500, 500);
		final Point objectTarget = new Point(50, 400);
		final Point object = new Point(55, 410);
		
		new Scheduler.Task("AnimatedDemo", DELAY) {@Override public void process() {
		    Mouse.move(objectOrigin, NativeEvent.BUTTON_LEFT, false, false, false, false);
		    Cursor.move(objectOrigin);
		}};
		
		new Scheduler.Task("AnimatedDemo", DELAY) {@Override public void process() {
		    Keyboard.push('O');
		}};

		move(objectOrigin, objectTarget);
		
		new Scheduler.Task("AnimatedDemo", DELAY) {@Override public void process() {
		    Mouse.press(Session.getActiveCanvas().getArtifactAt(object), object, NativeEvent.BUTTON_LEFT, false, false, false, false);
		    Mouse.release(Session.getActiveCanvas().getArtifactAt(object), object, NativeEvent.BUTTON_LEFT, false, false, false, false);
		    Cursor.move(object);
		    Mouse.press(Session.getActiveCanvas().getArtifactAt(classTarget), classTarget, NativeEvent.BUTTON_LEFT, false, false, false, false);
		    Mouse.release(Session.getActiveCanvas().getArtifactAt(classTarget), classTarget, NativeEvent.BUTTON_LEFT, false, false, false, false);
		    Cursor.move(classTarget);
		}};

		
		
		new Scheduler.Task("AnimatedDemo", DELAY) {@Override public void process() {
		    Keyboard.push('I');
		}};
		
		move(classTarget, object);
		
		new Scheduler.Task("AnimatedDemo", DELAY) {@Override public void process() {
		    Mouse.press(Session.getActiveCanvas().getArtifactAt(object), object, NativeEvent.BUTTON_LEFT, false, false, false, false);
		    Mouse.release(Session.getActiveCanvas().getArtifactAt(object), object, NativeEvent.BUTTON_LEFT, false, false, false, false);
		    Cursor.move(object);
		}};

		final Point selectBoxOrigin = new Point(40, 100);
		final Point selectBoxTarget = new Point(500, 500);
		new Scheduler.Task("AnimatedDemo", DELAY) {@Override public void process() {
		    Mouse.press(null, selectBoxOrigin, NativeEvent.BUTTON_LEFT, false, false, false, false);
		    Cursor.move(selectBoxOrigin);
		}};
		
		move(selectBoxOrigin, selectBoxTarget);
		
		new Scheduler.Task("AnimatedDemo", DELAY) {@Override public void process() {
		    Mouse.release(null, selectBoxTarget, NativeEvent.BUTTON_LEFT, false, false, false, false);
		    Cursor.move(selectBoxTarget);
		}};

		final Point allTarget = new Point(500, 350);
		
		new Scheduler.Task("AnimatedDemo", DELAY) {@Override public void process() {
		    Mouse.press(Session.getActiveCanvas().getArtifactAt(classTarget), classTarget, NativeEvent.BUTTON_LEFT, false, false, false, false);
		    Cursor.move(classTarget);
		}};
		move(classTarget, allTarget);
		
		new Scheduler.Task("AnimatedDemo", DELAY) {@Override public void process() {
		    Mouse.release(Session.getActiveCanvas().getArtifactAt(allTarget), allTarget, NativeEvent.BUTTON_LEFT, false, false, false, false);
		    Cursor.move(allTarget);
		}};
		new Scheduler.Task("AnimatedDemo", DELAY) {@Override public void process() {
		Cursor.rem();
		Session.getActiveCanvas().setMouseEnabled(true);
		HotKeyManager.setInputEnabled(true);
		}};
	    }	    
	});

    }

    private void move(final Point origin, final Point target) {
	final int step = 5;
	final int delay = 5;

	if(origin.getX() < target.getX()) {
	    for(int x = origin.getX() ; x < target.getX() ; x+=step) {
		final int xx = x;
		new Scheduler.Task("AnimatedDemo", delay) {@Override public void process() {
		    Point to = new Point(xx, origin.getY());
		    Mouse.move(to, NativeEvent.BUTTON_LEFT, false, false, false, false);
		    Cursor.move(to);
		    
		}};
	    }
	} else {
	    for(int x = origin.getX() ; x > target.getX() ; x-=step) {
		final int xx = x;
		new Scheduler.Task("AnimatedDemo", delay) {@Override public void process() {
		    Point to = new Point(xx, origin.getY());
		    Mouse.move(to, NativeEvent.BUTTON_LEFT, false, false, false, false);
		    Cursor.move(to);
		}};
	    }
	}
	if(origin.getY() < target.getY()) {
	    for(int y = origin.getY() ; y < target.getY() ; y+=step) {
		final int yy = y;
		new Scheduler.Task("AnimatedDemo", delay) {@Override public void process() {
		    Point to = new Point(target.getX(), yy);
		    Mouse.move(to, NativeEvent.BUTTON_LEFT, false, false, false, false);
		    Cursor.move(to);
		}};
	    }
	} else {
	    for(int y = origin.getY() ; y > target.getY() ; y-=step) {
		final int yy = y;
		new Scheduler.Task("AnimatedDemo", delay) {@Override public void process() {
		    Point to = new Point(target.getX(), yy);
		    Mouse.move(to, NativeEvent.BUTTON_LEFT, false, false, false, false);
		    Cursor.move(to);
		}};
	    }
	}
    }
}

