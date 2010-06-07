package com.objetdirect.gwt.umlapi.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.objetdirect.gwt.umlapi.client.engine.Direction;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.engine.Scheduler;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;

/**
 * Decorate the Canvas with the sides panel.
 * This decorator also permits to the canvas to display a Field Editor and a HelpText on the panel at the given position.
 * Raphael Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class DecoratorCanvas extends AbsolutePanel {
	private final int directionPanelSizes = OptionsManager.get("DirectionPanelSizes");
	private final double opacityValue = (double) OptionsManager.get("DirectionPanelOpacity") / 100;
	private final double opacityMax =  (double) OptionsManager.get("DirectionPanelMaxOpacity") / 100;
	private final String backgroundColor = ThemeManager.getTheme().getDirectionPanelColor().toString();

	private final UMLCanvas umlCanvas;

	private int height;
	private int width;
	
	private HashMap<Direction, FocusPanel> sidePanels;
	private GfxObject arrowsVirtualGroup;
	
	private Label helpTextLabel;
	
	public DecoratorCanvas(UMLCanvas umlCanvas) {
		this.umlCanvas = umlCanvas;
		umlCanvas.setDecoratorPanel(this);
		
		this.add(umlCanvas.getDrawingCanvas(), 0, 0);
		makeSidePanels();
		
		helpTextLabel = new Label("");
		helpTextLabel.setStylePrimaryName("contextual-help");
		this.add(helpTextLabel,0,0);
	}
	

	/**
	 * Make the set of sidePanels 
	 */
	private void makeSidePanels() {
		sidePanels = new HashMap<Direction, FocusPanel>();
		for(Direction direction: Direction.sideValues()) {
			FocusPanel sidePanel = new FocusPanel();
			sidePanels.put(direction, sidePanel);
			
			Point pixelSize = getSidePanelSize(direction);
			sidePanel.setPixelSize(pixelSize.getX(), pixelSize.getY());
			
			sidePanel.getElement().getStyle().setBackgroundColor(ThemeManager.getTheme().getDirectionPanelColor().toString());
			sidePanel.getElement().getStyle().setOpacity(opacityValue);
			sidePanel.getElement().setAttribute("oncontextmenu", "return false");
			
			addSidePanelHandlers(direction, sidePanel);
			
			this.add(sidePanel);
		}
		setSidePanelsPositionAndSize();
	}
	
	
	private void addSidePanelHandlers(final Direction direction, final FocusPanel sidePanel) {
		sidePanel.getElement().getStyle().setBackgroundColor(backgroundColor);
		sidePanel.getElement().getStyle().setOpacity(opacityValue);
		
		sidePanel.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(final MouseOverEvent event) {
				Log.debug("Mouse over sidePanel ");
				for (double d = opacityValue; d <= opacityMax; d += 0.05) {
					final double opacity = Math.ceil(d * 100) / 100;

					new Scheduler.Task("Opacifying") {
						@Override
						public void process() {
							sidePanel.getElement().getStyle().setOpacity(opacity);
						}
					};
				}
				new Scheduler.Task("MovingAllArtifacts") {
					@Override
					public void process() {
						Scheduler.cancel("MovingAllArtifactsRecursive");
						umlCanvas.moveAll(direction.withSpeed(Direction.getDependingOnQualityLevelSpeed()), true);
					}
				};
			}
		});
		

		sidePanel.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(final MouseOutEvent event) {
				Scheduler.cancel("Opacifying");
				Scheduler.cancel("MovingAllArtifacts");
				Scheduler.cancel("MovingAllArtifactsRecursive");
				
				sidePanel.getElement().getStyle().setOpacity(opacityValue);
				
			}
		});
		
		sidePanel.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(final MouseDownEvent event) {
				sidePanel.getElement().getStyle().setBackgroundColor(ThemeManager.getTheme().getDirectionPanelPressedColor().toString());
				Scheduler.cancel("MovingAllArtifactsRecursive");
			}
		});
		
		sidePanel.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(final MouseUpEvent event) {
				sidePanel.getElement().getStyle().setBackgroundColor(ThemeManager.getTheme().getDirectionPanelColor().toString());
				umlCanvas.moveAll(direction.withSpeed(Math.min(DecoratorCanvas.this.getOffsetHeight(), DecoratorCanvas.this.getOffsetWidth())), false);
			}
		});
	}

	/**
	 * For each side panel calculate its size and position and set it on the absolute Panel (this).
	 */
	private void setSidePanelsPositionAndSize() {
		for(Entry<Direction, FocusPanel> entry : sidePanels.entrySet()) {
			Direction direction = entry.getKey();
			FocusPanel sidePanel = entry.getValue();

			Point pixelSize = getSidePanelSize(direction);
			sidePanel.setPixelSize(pixelSize.getX(), pixelSize.getY());
			
			Point sidePanelPosition = getSidePanelPosition(direction);
			this.setWidgetPosition(sidePanel, sidePanelPosition.getX(), sidePanelPosition.getY());
		}
	}
	
	/**
	 * Get a side panel position from a direction.
	 * Ie : left up panel is positioned on the point 0,0 ; etc
	 * @param direction
	 * @return
	 */
	private Point getSidePanelPosition(Direction direction) {
		switch (direction) {
		case UP_LEFT: return Point.getOrigin();
			case UP : return new Point(directionPanelSizes, 0);
			case UP_RIGHT: return new Point(this.width - directionPanelSizes, 0);
			case RIGHT : return new Point(this.width - directionPanelSizes, directionPanelSizes);
			case DOWN_RIGHT : return new Point(this.width - directionPanelSizes, this.height - directionPanelSizes);
			case DOWN: return new Point(directionPanelSizes, this.height - directionPanelSizes);
			case DOWN_LEFT : return new Point(0, this.height - directionPanelSizes);
			case LEFT : return new Point(0, directionPanelSizes);
			default: throw new UnsupportedOperationException("Unknow direction for a side panel");
		}
	}
	
	/**
	 * Get a size for a Panel from its direction
	 * Ie : left up panel is of default size, 
	 * Up panel require a width of the with of the panel - the left up and right panel's width
	 * @param direction
	 * @return
	 */
	private Point getSidePanelSize(Direction direction) {
		switch (direction) {
			case UP_LEFT: return new Point(directionPanelSizes, directionPanelSizes);
			case UP : return new Point(this.width - 2 * directionPanelSizes, directionPanelSizes);
			case UP_RIGHT: return new Point(directionPanelSizes, directionPanelSizes);
			case RIGHT : return new Point(directionPanelSizes, this.height - 2 * directionPanelSizes);
			case DOWN_RIGHT : return new Point(directionPanelSizes, directionPanelSizes);
			case DOWN : return new Point(this.width - 2 * directionPanelSizes, directionPanelSizes);
			case DOWN_LEFT : return new Point(directionPanelSizes, directionPanelSizes);
			case LEFT : return new Point(directionPanelSizes, this.height - 2 * directionPanelSizes);
			default : throw new UnsupportedOperationException("Unknow direction for a side panel direction = " + direction);
		}
	}


	/**
	 * Draw the directions arrow on the canvas
	 */
	private void drawArrows() {
		if (arrowsVirtualGroup!=null)
			GfxManager.getPlatform().clearVirtualGroup(this.arrowsVirtualGroup);
		
		final int arrowSize = 6;
		this.arrowsVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		this.arrowsVirtualGroup.addToCanvas(umlCanvas.getDrawingCanvas(), Point.getOrigin());
		final ArrayList<GfxObject> arrowList = new ArrayList<GfxObject>();
		for (float f = 0; f < 360; f += 45) {
			final GfxObject arrow = GfxManager.getPlatform().buildPath();
			arrowList.add(arrow);
			GfxManager.getPlatform().moveTo(arrow, Point.getOrigin());
			GfxManager.getPlatform().lineTo(arrow, new Point(arrowSize, 0));
			GfxManager.getPlatform().lineTo(arrow, new Point(2 * arrowSize, arrowSize));
			GfxManager.getPlatform().lineTo(arrow, new Point(arrowSize, 2 * arrowSize));
			GfxManager.getPlatform().lineTo(arrow, new Point(0, 2 * arrowSize));
			GfxManager.getPlatform().lineTo(arrow, new Point(arrowSize, arrowSize));
			GfxManager.getPlatform().lineTo(arrow, Point.getOrigin());
			arrow.setFillColor(ThemeManager.getTheme().getDefaultForegroundColor());
			arrow.setStroke(ThemeManager.getTheme().getDefaultForegroundColor(), 1);
			arrow.rotate(f, new Point(arrowSize, arrowSize));
			arrow.addToVirtualGroup(this.arrowsVirtualGroup);
		}

		arrowList.get(0).translate(new Point(width - 2 * arrowSize - 2, height / 2 - arrowSize - 2)); // right
		arrowList.get(1).translate(new Point(width - 2 * arrowSize - 2, height - 2 * arrowSize - 2)); // bottom right
		arrowList.get(2).translate(new Point(width / 2 - arrowSize - 2, height - 2 * arrowSize - 2)); // bottom
		arrowList.get(3).translate(new Point(2, height - 2 * arrowSize - 2)); // bottom left
		arrowList.get(4).translate(new Point(2, height / 2 - arrowSize - 2)); // left
		arrowList.get(5).translate(new Point(2, 2)); // up left
		arrowList.get(6).translate(new Point(width / 2 - arrowSize - 2, 2)); // up
		arrowList.get(7).translate(new Point(width - 2 * arrowSize - 2, 2)); // up right
	}

	/**
	 * Change the size of the decorator panel.
	 * This will cause to resize the arrows and the encapsulated drawingCanvas.
	 * @param width the new width
	 * @param height the new height
	 */
	void reSize(int width, int height) {
		this.width = width;
		this.height = height;
		this.setPixelSize(width,height);
		setSidePanelsPositionAndSize();
		GfxManager.getPlatform().setSize(umlCanvas.getDrawingCanvas(), width, height);
		drawArrows();
	}
	
	/**
	 * Set a help Text on the canvas at a given position
	 * @param text
	 * @param location
	 */
	public void setHelpText(String text, Point location) {
		helpTextLabel.setText(text);
		moveHelpText(location);
	}

	public void moveHelpText(Point location) {
		int left = location.getX() + 5;
		int top = location.getY() - helpTextLabel.getOffsetHeight() - 5;
		
		this.setWidgetPosition(helpTextLabel, left, top);
	}
}
