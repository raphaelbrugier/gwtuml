/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.objetdirect.gwt.umlapi.client.umlcomponents;

import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkAdornment;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram.Type;

/**
 * This class represent an uml message between two {@link UMLClass}es
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class UMLMessage extends UMLComponent {
    /**
     * This enumeration lists all the messages type between two {@link UMLClass}es
     * 
     * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
     *
     */
    public enum MessageKind {
	/**
	 * Asynchronous message
	 */
	ASYNCHRONOUS("Asynchronous", LinkAdornment.WIRE_ARROW, LinkAdornment.NONE,  LinkStyle.SOLID),
	/**
	 * Synchronous message
	 */
	SYNCHRONOUS("Synchronous", LinkAdornment.SOLID_ARROW, LinkAdornment.NONE,  LinkStyle.SOLID),
	/**
	 * Reply message
	 */
	REPLY("Reply", LinkAdornment.NONE, LinkAdornment.NONE,  LinkStyle.DASHED),
	/**
	 * Object Creation message
	 */
	OBJECT_CREATION("Object Creation", LinkAdornment.WIRE_ARROW, LinkAdornment.NONE, LinkStyle.DASHED),
	/**
	 * Lost message
	 */
	LOST("Lost", LinkAdornment.INVERTED_SOLID_CIRCLE, LinkAdornment.NONE, LinkStyle.SOLID),
	/**
	 * Found message
	 */
	FOUND("Found", LinkAdornment.WIRE_ARROW, LinkAdornment.INVERTED_SOLID_CIRCLE, LinkStyle.SOLID);

	private String name;
	private LinkAdornment defaultLeftAdornment;
	private LinkAdornment defaultRightAdornment;
	private LinkStyle defaultLinkStyle;

	private MessageKind(final String name, 
		final LinkAdornment defaultLeftAdornment, final LinkAdornment defaultRightAdornment,
		final LinkStyle defaultLinkStyle) {

	    this.name = name;
	    this.defaultLeftAdornment = defaultLeftAdornment;
	    this.defaultRightAdornment = defaultRightAdornment;
	    this.defaultLinkStyle = defaultLinkStyle;
	}

	/**
	 * Static getter of a {@link MessageKind} by its name
	 *  
	 * @param messageKindName The name of the {@link MessageKind} to retrieve
	 * @return The {@link MessageKind} that has messageKindName for name or null if not found
	 */
	public static MessageKind getMessageKindFromName(String messageKindName) {
	    for(MessageKind messageKind : MessageKind.values()) {
		if(messageKind.getName().equals(messageKindName)) {
		    return messageKind;
		}
	    }
	    return null;
	}
	/**
	 * Getter for the defaultLeftAdornment
	 *
	 * @return the defaultLeftAdornment
	 */
	public LinkAdornment getDefaultLeftAdornment() {
	    return this.defaultLeftAdornment;
	}

	/**
	 * Getter for the defaultRightAdornment
	 *
	 * @return the defaultRightAdornment
	 */
	public LinkAdornment getDefaultRightAdornment() {
	    return this.defaultRightAdornment;
	}

	/**
	 * Getter for the defaultLinkStyle
	 *
	 * @return the defaultLinkStyle
	 */
	public LinkStyle getDefaultLinkStyle() {
	    return this.defaultLinkStyle;
	}

	/**
	 * Getter for the name
	 * 
	 * @return the name
	 */
	public String getName() {
	    return this.name;
	}


    }


    private String name;
    private MessageKind messageKind;

    private LinkAdornment leftAdornment;
    private LinkAdornment rightAdornment;
    private LinkStyle linkStyle;

    /**
     * Constructor of Message
     *
     * @param messageKind : The type of this message
     */
    public UMLMessage(final MessageKind messageKind) {
	super();
	this.messageKind = messageKind;
	this.linkStyle = messageKind.getDefaultLinkStyle();
	this.leftAdornment = messageKind.getDefaultLeftAdornment();
	this.rightAdornment = messageKind.getDefaultRightAdornment();
	this.name = "";
    }

    /**
     * Getter for the link style
     *
     * @return the link style
     */
    public LinkStyle getLinkStyle() {
	return this.linkStyle;
    }

    /**
     * Setter for the link style
     *
     * @param linkStyle the link style to set
     */
    public void setLinkStyle(LinkStyle linkStyle) {
	this.linkStyle = linkStyle;
    }

    /**
     * Getter for the left adornment
     *
     * @return the left adornment
     */
    public LinkAdornment getLeftAdornment() {
	return this.leftAdornment;
    }

    /**
     * Getter for the right adornment
     *
     * @return the right adornment
     */
    public LinkAdornment getRightAdornment() {
	return this.rightAdornment;
    }


    /**
     * Getter for the name
     * 
     * @return the name
     */
    public String getName() {
	return this.name;
    }

    /**
     * Getter for the messageKind
     * 
     * @return the messageKind
     */
    public MessageKind getMessageKind() {
	return this.messageKind;
    }

    /**
     * Setter for the left adornment
     *
     * @param leftAdornment the left adornment to set
     */
    public void setLeftAdornment(LinkAdornment leftAdornment) {
	this.leftAdornment = leftAdornment;
    }

    /**
     * Setter for the right adornment
     *
     * @param rightAdornment the right adornment to set
     */
    public void setRightAdornment(LinkAdornment rightAdornment) {
	this.rightAdornment = rightAdornment;
    }


    /**
     * Setter for the name
     * 
     * @param name
     *            the name to set
     */
    public void setName(final String name) {
	this.name = name;
    }

    /**
     * Setter for the messageKind
     * 
     * @param messageKind
     *            the messageKind to set
     */
    public void setMessageKind(final MessageKind messageKind) {
	this.messageKind = messageKind;
    }

    /**
     * Reverse the current message
     */
    public void reverse() {
	LinkAdornment tempAdornment = this.leftAdornment;
	this.leftAdornment = this.rightAdornment;
	this.rightAdornment = tempAdornment;
    }

}
