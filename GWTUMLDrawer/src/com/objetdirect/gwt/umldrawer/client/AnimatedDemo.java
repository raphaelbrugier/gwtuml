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
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.engine.Scheduler;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.Keyboard;
import com.objetdirect.gwt.umlapi.client.helpers.Mouse;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;

/**
 * This class is an exemple of an animated contruction of an uml diagram It shows how to use {@link Keyboard} and {@link Mouse}.
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class AnimatedDemo {

	private static class Cursor {

		private static GfxObject	cursor		= GfxManager.getPlatform().buildImage("cursor.png");
		private static Point		location	= Point.getOrigin();

		public static void add() {
			GfxManager.getPlatform().addToCanvas(Session.getActiveCanvas().getDrawingCanvas(), Cursor.cursor, new Point(0, 0));
		}

		public static void move(final Point newLocation) {
			GfxManager.getPlatform().translate(Cursor.cursor, Point.substract(newLocation, Cursor.location));
			Cursor.location = newLocation.clonePoint();
		}

		public static void rem() {
			GfxManager.getPlatform().removeFromCanvas(Session.getActiveCanvas().getDrawingCanvas(), Cursor.cursor);
		}
	}

	private static final int	DELAY	= 100;

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
		
		DeferredCommand.addCommand(new Command() {

			@Override
			public void execute() {
				drawer.setHotKeysEnabled(false);
				Session.getActiveCanvas().setMouseEnabled(false);
				Cursor.add();

				final Point classOrigin = new Point(50, 50);
				final Point classTarget = new Point(350, 150);

				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						Mouse.move(classOrigin, NativeEvent.BUTTON_LEFT, false, false, false, false);
						Cursor.move(classOrigin);
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
						Mouse.press(null, Point.getOrigin(), NativeEvent.BUTTON_LEFT, false, false, false, false);
						Mouse.release(null, Point.getOrigin(), NativeEvent.BUTTON_LEFT, false, false, false, false);
						Cursor.move(Point.getOrigin());
					}
				};

				final Point objectOrigin = new Point(500, 500);
				final Point objectTarget = new Point(50, 400);
				final Point object = new Point(55, 410);

				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						Mouse.move(objectOrigin, NativeEvent.BUTTON_LEFT, false, false, false, false);
						Cursor.move(objectOrigin);
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
						Mouse.press(Session.getActiveCanvas().getArtifactAt(object), object, NativeEvent.BUTTON_LEFT, false, false, false, false);
						Mouse.release(Session.getActiveCanvas().getArtifactAt(object), object, NativeEvent.BUTTON_LEFT, false, false, false, false);
						Cursor.move(object);
						Mouse.press(Session.getActiveCanvas().getArtifactAt(classTarget), classTarget, NativeEvent.BUTTON_LEFT, false, false, false, false);
						Mouse.release(Session.getActiveCanvas().getArtifactAt(classTarget), classTarget, NativeEvent.BUTTON_LEFT, false, false, false, false);
						Cursor.move(classTarget);
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
						Mouse.press(Session.getActiveCanvas().getArtifactAt(object), object, NativeEvent.BUTTON_LEFT, false, false, false, false);
						Mouse.release(Session.getActiveCanvas().getArtifactAt(object), object, NativeEvent.BUTTON_LEFT, false, false, false, false);
						Cursor.move(object);
					}
				};

				final Point selectBoxOrigin = new Point(40, 100);
				final Point selectBoxTarget = new Point(500, 500);
				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						Mouse.press(null, selectBoxOrigin, NativeEvent.BUTTON_LEFT, false, false, false, false);
						Cursor.move(selectBoxOrigin);
					}
				};

				AnimatedDemo.this.move(selectBoxOrigin, selectBoxTarget);

				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						Mouse.release(null, selectBoxTarget, NativeEvent.BUTTON_LEFT, false, false, false, false);
						Cursor.move(selectBoxTarget);
					}
				};

				final Point allTarget = new Point(500, 350);

				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						Mouse.press(Session.getActiveCanvas().getArtifactAt(classTarget), classTarget, NativeEvent.BUTTON_LEFT, false, false, false, false);
						Cursor.move(classTarget);
					}
				};
				AnimatedDemo.this.move(classTarget, allTarget);

				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						Mouse.release(Session.getActiveCanvas().getArtifactAt(allTarget), allTarget, NativeEvent.BUTTON_LEFT, false, false, false, false);
						Cursor.move(allTarget);
					}
				};
				new Scheduler.Task("AnimatedDemo", AnimatedDemo.DELAY) {
					@Override
					public void process() {
						Cursor.rem();
						Session.getActiveCanvas().setMouseEnabled(true);
						drawer.setHotKeysEnabled(true);
					}
				};
			}
		});

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
						Mouse.move(to, NativeEvent.BUTTON_LEFT, false, false, false, false);
						Cursor.move(to);

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
						Mouse.move(to, NativeEvent.BUTTON_LEFT, false, false, false, false);
						Cursor.move(to);
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
						Mouse.move(to, NativeEvent.BUTTON_LEFT, false, false, false, false);
						Cursor.move(to);
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
						Mouse.move(to, NativeEvent.BUTTON_LEFT, false, false, false, false);
						Cursor.move(to);
					}
				};
			}
		}
	}
}
