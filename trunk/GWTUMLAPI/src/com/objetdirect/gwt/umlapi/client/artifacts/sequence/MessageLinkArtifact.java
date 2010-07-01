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
package com.objetdirect.gwt.umlapi.client.artifacts.sequence;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact;
import com.objetdirect.gwt.umlapi.client.contextMenu.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.editors.MessageFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLMessage;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkAdornment;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLLink.LinkKind;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public class MessageLinkArtifact extends LinkArtifact {

	/**
	 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
	 */
	protected transient GfxObject arrowVirtualGroup;
	protected transient GfxObject line;
	protected transient GfxObject text;

	protected LifeLineArtifact leftLifeLineArtifact;
	protected LifeLineArtifact rightLifeLineArtifact;
	private UMLMessage message;

	/** Default constructor ONLY for gwt-rpc serialization. */
	@Deprecated
	@SuppressWarnings("unused")
	private MessageLinkArtifact() {
	}

	/**
	 * Constructor of {@link MessageLinkArtifact}
	 * 
	 * @param canvas
	 *            Where the gfxObjects are displayed
	 * @param id
	 *            The artifacts's id
	 * @param left
	 *            The left {@link LifeLineArtifact} of the message
	 * @param right
	 *            The right {@link LifeLineArtifact} of the message
	 * @param messageKind
	 *            The kind of message this link is.
	 */
	public MessageLinkArtifact(final UMLCanvas canvas, int id, final LifeLineArtifact left, final LifeLineArtifact right, final LinkKind messageKind) {
		super(canvas, id, left, right);
		message = new UMLMessage(messageKind);
		leftLifeLineArtifact = left;
		left.addDependency(this, right);
		rightLifeLineArtifact = right;
		if (right != left) {
			right.addDependency(this, left);
		}

	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		final MessageFieldEditor editor = new MessageFieldEditor(canvas, this);
		editor.startEdition(message.getName(), text.getLocation().getX(), text.getLocation().getY(), GfxManager.getPlatform().getTextWidthFor(text)
				+ RECTANGLE_RIGHT_PADDING + RECTANGLE_LEFT_PADDING, false, true);
	}

	/**
	 * Getter for the left {@link LifeLineArtifact} of this message
	 * 
	 * @return the left {@link LifeLineArtifact} of this message
	 */
	public LifeLineArtifact getLeftLifeLineArtifact() {
		return leftLifeLineArtifact;
	}

	/**
	 * Getter for the right {@link LifeLineArtifact} of this message
	 * 
	 * @return the right {@link LifeLineArtifact} of this message
	 */
	public LifeLineArtifact getRightLifeLineArtifact() {
		return rightLifeLineArtifact;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.links.MessageshipLinkArtifact #getRightMenu()
	 */
	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		rightMenu.setName(message.getLinkKind().getName() + " " + leftLifeLineArtifact.getContent() + " " + message.getLeftAdornment().getShape().getIdiom()
				+ "-" + message.getRightAdornment().getShape().getIdiom(true) + " " + rightLifeLineArtifact.getContent());
		rightMenu.addItem("Edit", this.editCommand());
		rightMenu.addItem("Reverse", this.reverseCommand(message));
		return rightMenu;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact#removeCreatedDependency()
	 */
	@Override
	public void removeCreatedDependency() {
		leftLifeLineArtifact.removeDependency(this);
		rightLifeLineArtifact.removeDependency(this);
	}

	/**
	 * Setter for the message left {@link LinkArtifact.LinkAdornment}
	 * 
	 * @param leftAdornment
	 *            The left {@link LinkArtifact.LinkAdornment} to be set
	 */
	public void setLeftAdornment(final LinkAdornment leftAdornment) {
		message.setLeftAdornment(leftAdornment);
	}

	/**
	 * Setter for the message {@link LinkArtifact.LinkStyle}
	 * 
	 * @param linkStyle
	 *            The {@link LinkArtifact.LinkStyle} to be set
	 */
	public void setLinkStyle(final LinkStyle linkStyle) {
		message.setLinkStyle(linkStyle);
	}

	/**
	 * Setter for the message {@link LinkKind}
	 * 
	 * @param messageKind
	 *            The {@link LinkKind} to be set
	 */
	public void setMessageKind(final LinkKind messageKind) {
		message.setLinkKind(messageKind);
	}

	/**
	 * Setter for the name in {@link UMLMessage} This does not update the graphical object
	 * 
	 * @param name
	 *            The name text to be set
	 */
	public void setName(final String name) {
		message.setName(name);
	}

	/**
	 * Setter for the message right {@link LinkArtifact.LinkAdornment}
	 * 
	 * @param rightAdornment
	 *            The right{@link LinkArtifact.LinkAdornment} to be set
	 */
	public void setRightAdornment(final LinkAdornment rightAdornment) {
		message.setRightAdornment(rightAdornment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		return "MessageLink$<" + leftLifeLineArtifact.getId() + ">!<" + rightLifeLineArtifact.getId() + ">!" + message.getLinkKind().getName() + "!"
				+ message.getName() + "!" + message.getLinkStyle().getName() + "!" + message.getLeftAdornment().getName() + "!"
				+ message.getRightAdornment().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#unselect()
	 */
	@Override
	public void unselect() {
		super.unselect();
		line.setStroke(ThemeManager.getTheme().getLifeLineForegroundColor(), 1);
		arrowVirtualGroup.setStroke(ThemeManager.getTheme().getLifeLineForegroundColor(), 1);
	}

	@Override
	protected void buildGfxObject() {

		if (!leftLifeLineArtifact.hasThisAllDirectionsDependecy(this)) {
			leftLifeLineArtifact.addAllDirectionsDependecy(this);
			leftLifeLineArtifact.rebuildGfxObject();
		}
		if (!rightLifeLineArtifact.hasThisAllDirectionsDependecy(this)) {
			rightLifeLineArtifact.addAllDirectionsDependecy(this);
			rightLifeLineArtifact.rebuildGfxObject();
		}

		leftPoint = Point.add(leftLifeLineArtifact.getCenter(), new Point(0, leftLifeLineArtifact.getHeight() / 2));
		rightPoint = Point.add(rightLifeLineArtifact.getCenter(), new Point(0, rightLifeLineArtifact.getHeight() / 2));
		leftPoint.translate(0, (leftLifeLineArtifact.getAllDirectionsDependencyIndexOf(this) + 1) * OptionsManager.get("LifeLineSpacing"));
		rightPoint.translate(0, (rightLifeLineArtifact.getAllDirectionsDependencyIndexOf(this) + 1) * OptionsManager.get("LifeLineSpacing"));
		line = GfxManager.getPlatform().buildLine(leftPoint, rightPoint);
		line.addToVirtualGroup(gfxObject);
		line.setStroke(ThemeManager.getTheme().getLinkNoteForegroundColor(), 1);
		line.setStrokeStyle(message.getLinkStyle().getGfxStyle());

		// Making arrows group :
		arrowVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		arrowVirtualGroup.addToVirtualGroup(gfxObject);
		final GfxObject leftArrow = GeometryManager.getPlatform().buildAdornment(leftPoint, rightPoint, message.getLeftAdornment());
		final GfxObject rightArrow = GeometryManager.getPlatform().buildAdornment(rightPoint, leftPoint, message.getRightAdornment());

		if (leftArrow != null) {
			leftArrow.addToVirtualGroup(arrowVirtualGroup);
		}
		if (rightArrow != null) {
			rightArrow.addToVirtualGroup(arrowVirtualGroup);
		}

		text = GfxManager.getPlatform().buildText(message.getName(), Point.getMiddleOf(leftPoint, rightPoint));
		Log.trace("Creating name");

		text.setFont(OptionsManager.getSmallFont());
		text.addToVirtualGroup(gfxObject);
		text.setStroke(ThemeManager.getTheme().getClassRelationBackgroundColor(), 0); // TODO fix it
		text.setFillColor(ThemeManager.getTheme().getClassRelationForegroundColor()); // FIXME
		text.translate(new Point(-GfxManager.getPlatform().getTextWidthFor(text) / 2, -GfxManager.getPlatform().getTextHeightFor(text)
				- TEXT_BOTTOM_PADDING));

		gfxObject.moveToBack();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#select()
	 */
	@Override
	protected void select() {
		super.select();

		line.setStroke(ThemeManager.getTheme().getLifeLineHighlightedForegroundColor(), 2);
		arrowVirtualGroup.setStroke(ThemeManager.getTheme().getLifeLineHighlightedForegroundColor(), 2);
	}

	private Command editCommand() {
		return new Command() {
			public void execute() {
				MessageLinkArtifact.this.edit(null);
			}
		};
	}

	private Command reverseCommand(final UMLMessage linkMessage) {
		return new Command() {
			public void execute() {
				linkMessage.reverse();
				MessageLinkArtifact.this.rebuildGfxObject();
			}

		};
	}

	@Override
	public void setUpAfterDeserialization() {
		buildGfxObject();
	}
}
