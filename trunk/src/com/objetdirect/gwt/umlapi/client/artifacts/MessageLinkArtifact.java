/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.objetdirect.gwt.umlapi.client.artifacts;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.objetdirect.gwt.umlapi.client.editors.MessageFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLMessage;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram.Type;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class MessageLinkArtifact extends LinkArtifact {

    /**
     * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
     */
    protected GfxObject arrowVirtualGroup;
    protected LifeLineArtifact leftLifeLineArtifact;
    protected GfxObject line;
    protected LifeLineArtifact rightLifeLineArtifact;
    protected GfxObject text;
    private UMLMessage message;

    /**
     * Constructor of {@link MessageLinkArtifact}
     * 
     * @param left The left {@link LifeLineArtifact} of the message
     * @param right The right {@link LifeLineArtifact} of the message
     * @param messageKind The kind of message this link is.
     */
    public MessageLinkArtifact(LifeLineArtifact left, final LifeLineArtifact right, final LinkKind messageKind) {
	super(left, right);
	this.message = new UMLMessage(messageKind);
	this.leftLifeLineArtifact = left;
	left.addDependency(this, right);
	this.rightLifeLineArtifact = right;
	if (right != left) {
	    right.addDependency(this, left);
	}

    }

    @Override
    public void edit(final GfxObject editedGfxObject) {
	    final MessageFieldEditor editor = new MessageFieldEditor(this.canvas, this);
	    editor
	    .startEdition(this.message.getName(), GfxManager
		    .getPlatform().getLocationFor(this.text).getX(), GfxManager
		    .getPlatform().getLocationFor(this.text).getY(), GfxManager.getPlatform()
		    .getTextWidthFor(this.text)
		    + OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding"), false, true);
    }

    /**
     * Getter for the left {@link LifeLineArtifact} of this message 
     * 
     * @return the left {@link LifeLineArtifact} of this message
     */
    public LifeLineArtifact getLeftLifeLineArtifact() {
	return this.leftLifeLineArtifact;
    }

    /**
     * Getter for the right {@link LifeLineArtifact} of this message 
     * 
     * @return the right {@link LifeLineArtifact} of this message
     */
    public LifeLineArtifact getRightLifeLineArtifact() {
	return this.rightLifeLineArtifact;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.objetdirect.gwt.umlapi.client.artifacts.links.MessageshipLinkArtifact
     * #getRightMenu()
     */
    @Override
    public MenuBarAndTitle getRightMenu() {
	final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
	rightMenu.setName(this.message.getLinkKind().getName() + " "
		+ this.leftLifeLineArtifact.getContent() + " "
		+ this.message.getLeftAdornment().getShape().getIdiom() + "-"
		+ this.message.getRightAdornment().getShape().getIdiom(true) + " "
		+ this.rightLifeLineArtifact.getContent());

	rightMenu.addItem("Reverse", reverseCommand(this.message));
	final MenuBar linkSubMenu = new MenuBar(true);
	for (final LinkKind messageKind : LinkKind.values()) {
	    if(messageKind.isForDiagram(Type.SEQUENCE)) {
		linkSubMenu.addItem(messageKind.getName(), changeToCommand(this.message, messageKind));
	    }
	}
	rightMenu.addItem("Change to", linkSubMenu);
	return rightMenu;
    }



    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact#removeCreatedDependency()
     */
    @Override
    public void removeCreatedDependency() {
	this.leftLifeLineArtifact.removeDependency(this);
	this.rightLifeLineArtifact.removeDependency(this);
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#select()
     */
    @Override
    protected void select() {
	super.select();

	GfxManager.getPlatform().setStroke(this.line,
		ThemeManager.getTheme().getLifeLineHighlightedForegroundColor(), 2);
	GfxManager.getPlatform().setStroke(this.arrowVirtualGroup,
		ThemeManager.getTheme().getLifeLineHighlightedForegroundColor(), 2);
    }

    /**
     * Setter for the name in {@link UMLMessage}
     * This does not update the graphical object
     * 
     * @param name The name text to be set
     */
    public void setName(final String name) {
	this.message.setName(name);
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#unselect()
     */
    @Override
    public void unselect() {
	super.unselect();
	GfxManager.getPlatform().setStroke(this.line,
		ThemeManager.getTheme().getLifeLineForegroundColor(), 1);
	GfxManager.getPlatform().setStroke(this.arrowVirtualGroup,
		ThemeManager.getTheme().getLifeLineForegroundColor(), 1);
    }

    @Override
    protected void buildGfxObject() {
	
	if(!this.leftLifeLineArtifact.hasThisAllDirectionsDependecy(this)) {
	    this.leftLifeLineArtifact.addAllDirectionsDependecy(this);
		this.leftLifeLineArtifact.rebuildGfxObject();
	}
	if(!this.rightLifeLineArtifact.hasThisAllDirectionsDependecy(this)) {
	    this.rightLifeLineArtifact.addAllDirectionsDependecy(this);
		this.rightLifeLineArtifact.rebuildGfxObject();
	}
	
	this.leftPoint = Point.add(this.leftLifeLineArtifact.getCenter(), new Point(0, this.leftLifeLineArtifact.getHeight()/2));
	this.rightPoint = Point.add(this.rightLifeLineArtifact.getCenter(), new Point(0, this.rightLifeLineArtifact.getHeight()/2));
	this.leftPoint.translate(0, (this.leftLifeLineArtifact.getAllDirectionsDependencyIndexOf(this) + 1) * OptionsManager.get("LifeLineSpacing"));
	this.rightPoint.translate(0, (this.rightLifeLineArtifact.getAllDirectionsDependencyIndexOf(this) + 1) * OptionsManager.get("LifeLineSpacing"));
	this.line = GfxManager.getPlatform().buildLine(this.leftPoint, this.rightPoint);
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.line);
	GfxManager.getPlatform().setStroke(this.line,
		ThemeManager.getTheme().getLinkNoteForegroundColor(), 1);
	GfxManager.getPlatform().setStrokeStyle(this.line, this.message.getLinkStyle().getGfxStyle());
	
	// Making arrows group :
	this.arrowVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.arrowVirtualGroup);
	GfxObject leftArrow = GeometryManager.getPlatform().buildAdornment(this.leftPoint, this.rightPoint, this.message.getLeftAdornment());
	GfxObject rightArrow = GeometryManager.getPlatform().buildAdornment(this.rightPoint, this.leftPoint, this.message.getRightAdornment());
	
	if(leftArrow != null) { 
	    GfxManager.getPlatform().addToVirtualGroup(this.arrowVirtualGroup, leftArrow);
	}
	if(rightArrow != null) {
	    GfxManager.getPlatform().addToVirtualGroup(this.arrowVirtualGroup, rightArrow);
	}
	
	this.text = GfxManager.getPlatform().buildText(this.message.getName(), Point.getMiddleOf(this.leftPoint, this.rightPoint));
	    Log.trace("Creating name");
	    
	    GfxManager.getPlatform().setFont(this.text, OptionsManager.getSmallFont());
	    GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.text);
	    GfxManager.getPlatform().setStroke(this.text,
		    ThemeManager.getTheme().getClassRelationBackgroundColor(), 0); //TODO fix it
	    GfxManager.getPlatform().setFillColor(this.text,
		    ThemeManager.getTheme().getClassRelationForegroundColor()); //FIXME
	    GfxManager.getPlatform().translate(this.text, new Point(-GfxManager.getPlatform().getTextWidthFor(this.text) / 2, 0));
	
	
	GfxManager.getPlatform().moveToBack(this.gfxObject);


	
    }



    private Command changeToCommand(final UMLMessage linkMessage, final LinkKind messageKind) {
	return new Command() {
	    public void execute() {
		linkMessage.setLinkKind(messageKind);
		linkMessage.setLinkStyle(messageKind.getDefaultLinkStyle());
		linkMessage.setLeftAdornment(messageKind.getDefaultLeftAdornment());
		linkMessage.setRightAdornment(messageKind.getDefaultRightAdornment());
		rebuildGfxObject();
	    }
	};
    }

    private Command editCommand() {
	return new Command() {
	    public void execute() {
		edit(null);
	    }
	};
    }

    private Command reverseCommand(final UMLMessage linkMessage) {
	return new Command() {
	    public void execute() {
		linkMessage.reverse();
		rebuildGfxObject();
	    }

	};
    }


    private Command setNavigabilityCommand(final UMLMessage message, final boolean isLeft) {
	return new Command() {
	    public void execute() {
		if(isLeft) message.setLeftAdornment(LinkAdornment.NONE);
		else message.setRightAdornment(LinkAdornment.NONE);
		rebuildGfxObject();
	    }
	};
    }


    private Command setNavigabilityCommand(final UMLMessage message, final boolean isLeft, final boolean isNavigable) {
	return new Command() {
	    public void execute() {
		LinkAdornment adornment = isNavigable ? LinkAdornment.WIRE_ARROW : LinkAdornment.WIRE_CROSS;
		if(isLeft) message.setLeftAdornment(adornment);
		else message.setRightAdornment(adornment);
		rebuildGfxObject();
	    }
	};
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
     */
    @Override
    public String toURL() {
	return "MessageLink$" + this.leftLifeLineArtifact.getId() + "!" + this.rightLifeLineArtifact.getId() + "!"
	+ this.message.getLinkKind().getName() + "!"
	+ this.message.getName() + "!"	
	+ this.message.getLinkStyle().getName() + "!"	
	+ this.message.getLeftAdornment().getName() + "!"
	+ this.message.getRightAdornment().getName();
    }
    /**
     * Setter for the message {@link LinkKind}
     * 
     * @param messageKind The {@link LinkKind} to be set
     */
    public void setMessageKind(LinkKind messageKind) {
	this.message.setLinkKind(messageKind);	
    }
    /**
     * Setter for the message {@link LinkArtifact.LinkStyle}
     * 
     * @param linkStyle The {@link LinkArtifact.LinkStyle} to be set
     */
    public void setLinkStyle(LinkStyle linkStyle) {
	this.message.setLinkStyle(linkStyle);	
    }
    /**
     * Setter for the message left {@link LinkArtifact.LinkAdornment}
     * 
     * @param leftAdornment The left {@link LinkArtifact.LinkAdornment} to be set
     */
    public void setLeftAdornment(LinkAdornment leftAdornment) {
	this.message.setLeftAdornment(leftAdornment);
    }
    /**
     * Setter for the message right {@link LinkArtifact.LinkAdornment}
     * 
     * @param rightAdornment The right{@link LinkArtifact.LinkAdornment} to be set
     */
    public void setRightAdornment(LinkAdornment rightAdornment) {
	this.message.setRightAdornment(rightAdornment);
    }
}
