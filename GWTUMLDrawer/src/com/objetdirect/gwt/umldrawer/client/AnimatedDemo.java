/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umldrawer.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.objetdirect.gwt.umlapi.client.Drawer;
import com.objetdirect.gwt.umlapi.client.controls.CanvasListener;
import com.objetdirect.gwt.umlapi.client.controls.Keyboard;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.engine.Scheduler;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;

/**
 * This class is an exemple of an animated contruction of an uml diagram It shows how to use {@link Keyboard} and {@link Mouse}.
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class AnimatedDemo {

	private static final int DELAY	= 100;
	
	private final UMLCanvas umlCanvas;
	private final CanvasListener mouseCanvasListener;
	
	private GfxObject cursor = GfxManager.getPlatform().buildImage("cursor.png");
	private Point location = Point.getOrigin();


	/**
	 * Constructor of the animated demo, used to test the
	 * 
	 * @param canvas
	 *            The {@link UMLCanvas} where to add the demo uml artifacts
	 */
	public AnimatedDemo(DrawerContainer drawerContainer) {
		Log.trace("Creating Animated demo");
		final Keyboard keyboard = drawerContainer.getDrawer().getKeyboard();
		final Drawer drawer = drawerContainer.getDrawer();
		umlCanvas = drawer.getUmlCanvas();
		mouseCanvasListener = umlCanvas.getCanvasMouseListener();
		
		DeferredCommand.addCommand(new Command() {

			@Override
			public void execute() {
				drawer.setHotKeysEnabled(false);
				umlCanvas.setMouseEnabled(false);
				add();

				final Point classOrigin = new Point(50, 50);
				final Point classTarget = new Point(350, 150);

				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						mouseCanvasListener.move(classOrigin, NativeEvent.BUTTON_LEFT, false, false);
						move(classOrigin);
					}
				};
				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						keyboard.push('c');
					}
				};

				AnimatedDemo.this.move(classOrigin, classTarget);

				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						mouseCanvasListener.press(null, Point.getOrigin(), NativeEvent.BUTTON_LEFT, false, false);
						mouseCanvasListener.release(null, Point.getOrigin(), NativeEvent.BUTTON_LEFT, false, false);
						move(Point.getOrigin());
					}
				};

				final Point objectOrigin = new Point(500, 500);
				final Point objectTarget = new Point(50, 400);
				final Point object = new Point(55, 410);

				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						mouseCanvasListener.move(objectOrigin, NativeEvent.BUTTON_LEFT, false, false);
						move(objectOrigin);
					}
				};

				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						keyboard.push('O');
					}
				};

				AnimatedDemo.this.move(objectOrigin, objectTarget);

				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						mouseCanvasListener.press(umlCanvas.getArtifactAt(object), object, NativeEvent.BUTTON_LEFT, false, false);
						mouseCanvasListener.release(umlCanvas.getArtifactAt(object), object, NativeEvent.BUTTON_LEFT, false, false);
						move(object);
						mouseCanvasListener.press(umlCanvas.getArtifactAt(classTarget), classTarget, NativeEvent.BUTTON_LEFT, false, false);
						mouseCanvasListener.release(umlCanvas.getArtifactAt(classTarget), classTarget, NativeEvent.BUTTON_LEFT, false, false);
						move(classTarget);
					}
				};

				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						keyboard.push('I');
					}
				};

				AnimatedDemo.this.move(classTarget, object);

				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						mouseCanvasListener.press(umlCanvas.getArtifactAt(object), object, NativeEvent.BUTTON_LEFT, false, false);
						mouseCanvasListener.release(umlCanvas.getArtifactAt(object), object, NativeEvent.BUTTON_LEFT, false, false);
						move(object);
					}
				};

				final Point selectBoxOrigin = new Point(40, 100);
				final Point selectBoxTarget = new Point(500, 500);
				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						mouseCanvasListener.press(null, selectBoxOrigin, NativeEvent.BUTTON_LEFT, false, false);
						move(selectBoxOrigin);
					}
				};

				AnimatedDemo.this.move(selectBoxOrigin, selectBoxTarget);

				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						mouseCanvasListener.release(null, selectBoxTarget, NativeEvent.BUTTON_LEFT, false, false);
						move(selectBoxTarget);
					}
				};

				final Point allTarget = new Point(500, 350);

				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						mouseCanvasListener.press(umlCanvas.getArtifactAt(classTarget), classTarget, NativeEvent.BUTTON_LEFT, false, false);
						move(classTarget);
					}
				};
				AnimatedDemo.this.move(classTarget, allTarget);

				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						mouseCanvasListener.release(umlCanvas.getArtifactAt(allTarget), allTarget, NativeEvent.BUTTON_LEFT, false, false);
						move(allTarget);
					}
				};
				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						remove();
						umlCanvas.setMouseEnabled(true);
						drawer.setHotKeysEnabled(true);
					}
				};
			}
		});

	}
	
	public void add() {
		GfxManager.getPlatform().addToCanvas(umlCanvas.getDrawingCanvas(), cursor, new Point(0, 0));
	}

	public void move(final Point newLocation) {
		GfxManager.getPlatform().translate(cursor, Point.substract(newLocation, location));
		location = newLocation.clonePoint();
	}

	public void remove() {
		GfxManager.getPlatform().removeFromCanvas(umlCanvas.getDrawingCanvas(), cursor);
	}

	private void move(final Point origin, final Point target) {
		final int step = 5;
		final int delay = 5;

		if (origin.getX() < target.getX()) {
			for (int x = origin.getX(); x < target.getX(); x += step) {
				final int xx = x;
				new Scheduler.Task("AnimatedDemo", delay) {
					@Override
					public void process() {
						final Point to = new Point(xx, origin.getY());
						mouseCanvasListener.move(to, NativeEvent.BUTTON_LEFT, false, false);
						move(to);
					}
				};
			}
		} else {
			for (int x = origin.getX(); x > target.getX(); x -= step) {
				final int xx = x;
				new Scheduler.Task("AnimatedDemo", delay) {
					@Override
					public void process() {
						final Point to = new Point(xx, origin.getY());
						mouseCanvasListener.move(to, NativeEvent.BUTTON_LEFT, false, false);
						move(to);
					}
				};
			}
		}
		if (origin.getY() < target.getY()) {
			for (int y = origin.getY(); y < target.getY(); y += step) {
				final int yy = y;
				new Scheduler.Task("AnimatedDemo", delay) {
					@Override
					public void process() {
						final Point to = new Point(target.getX(), yy);
						mouseCanvasListener.move(to, NativeEvent.BUTTON_LEFT, false, false);
						move(to);
					}
				};
			}
		} else {
			for (int y = origin.getY(); y > target.getY(); y -= step) {
				final int yy = y;
				new Scheduler.Task("AnimatedDemo", delay) {
					@Override
					public void process() {
						final Point to = new Point(target.getX(), yy);
						mouseCanvasListener.move(to, NativeEvent.BUTTON_LEFT, false, false);
						move(to);
					}
				};
			}
		}
	}
}
