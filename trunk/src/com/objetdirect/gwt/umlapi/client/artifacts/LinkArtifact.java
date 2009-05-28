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
package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.Collections;

import com.objetdirect.gwt.umlapi.client.engine.Direction;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLRelation.RelationKind;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;

/**
 * This abstract class specialize an {@link UMLArtifact} in a link type artifact
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public abstract class LinkArtifact extends UMLArtifact {

    static class UMLArtifactPeer {
	UMLArtifact uMLArtifact1;
	UMLArtifact uMLArtifact2;

	UMLArtifactPeer(UMLArtifact uMLArtifact1, UMLArtifact uMLArtifact2) {
	    super();
	    this.uMLArtifact1 = uMLArtifact1;
	    this.uMLArtifact2 = uMLArtifact2;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	    UMLArtifactPeer peer = (UMLArtifactPeer) obj;
	    return ((this.uMLArtifact1 == peer.uMLArtifact1) && (this.uMLArtifact2 == peer.uMLArtifact2)) || ((this.uMLArtifact1 == peer.uMLArtifact2) && (this.uMLArtifact2 == peer.uMLArtifact1));
	}
    }
    static final ArrayList<UMLArtifactPeer> uMLArtifactRelations = new ArrayList<UMLArtifactPeer>();
    private UMLArtifact leftUMLArtifact;
    private UMLArtifact rightUMLArtifact;



    /**
     * Constructor of RelationLinkArtifact
     * 
     * @param uMLArtifact1 First {@link UMLArtifact}
     * @param uMLArtifact2 Second {@link UMLArtifact}
     *
     */
    public LinkArtifact(UMLArtifact uMLArtifact1, UMLArtifact uMLArtifact2) {
	super(true);
	this.leftUMLArtifact = uMLArtifact1;
	this.rightUMLArtifact = uMLArtifact2;
	LinkArtifact.UMLArtifactPeer newPeer = new LinkArtifact.UMLArtifactPeer(uMLArtifact1, uMLArtifact2);
	this.order = Collections.frequency(LinkArtifact.uMLArtifactRelations, newPeer);
	LinkArtifact.uMLArtifactRelations.add(newPeer);
    }

    /**
     * Make a link between two {@link UMLArtifact}
     * 
     * @param uMLArtifact The first one of the two {@link UMLArtifact} to be linked
     * @param uMLArtifactNew The second one of the two {@link UMLArtifact} to be linked
     * @param relationKind The {@link RelationKind} of this link
     * @return The created {@link LinkArtifact} linking uMLArtifact and uMLArtifactNew
     */
    public static LinkArtifact makeLinkBetween(UMLArtifact uMLArtifact, UMLArtifact uMLArtifactNew, RelationKind relationKind) {
	if (relationKind == RelationKind.NOTE) {
	    if (uMLArtifactNew.getClass() == NoteArtifact.class) {
		return new LinkNoteArtifact((NoteArtifact) uMLArtifactNew,
			uMLArtifact);
	    } 
	    if (uMLArtifact.getClass() == NoteArtifact.class) {
		return new LinkNoteArtifact((NoteArtifact) uMLArtifact,
			uMLArtifactNew);
	    }
	    return null;
	} else if (relationKind == RelationKind.CLASSRELATION) {
	    if (uMLArtifactNew.getClass() == ClassRelationLinkArtifact.class
		    && uMLArtifact.getClass() == ClassArtifact.class) {
		return new LinkClassRelationArtifact(
			(ClassArtifact) uMLArtifact,
			(ClassRelationLinkArtifact) uMLArtifactNew);
	    } 
	    if (uMLArtifact.getClass() == ClassRelationLinkArtifact.class
		    && uMLArtifactNew.getClass() == ClassArtifact.class) {
		return new LinkClassRelationArtifact(
			(ClassArtifact) uMLArtifactNew,
			(ClassRelationLinkArtifact) uMLArtifact);
	    }
	    return null;

	}
	else if (uMLArtifact.getClass() == ClassArtifact.class
		&& uMLArtifactNew.getClass() == ClassArtifact.class) {
	    return new ClassRelationLinkArtifact((ClassArtifact) uMLArtifactNew, (ClassArtifact) uMLArtifact, relationKind);

	} 	    
	else if (relationKind != RelationKind.GENERALIZATION && relationKind != RelationKind.REALIZATION && uMLArtifact.getClass() == ObjectArtifact.class
		&& uMLArtifactNew.getClass() == ObjectArtifact.class) {
	    return new ObjectRelationLinkArtifact((ObjectArtifact) uMLArtifactNew, (ObjectArtifact) uMLArtifact, relationKind);
	}
	else if (relationKind == RelationKind.INSTANTIATION && (uMLArtifact.getClass() == ClassArtifact.class
		&& uMLArtifactNew.getClass() == ObjectArtifact.class)) {		
	    return new InstantiationRelationLinkArtifact((ClassArtifact) uMLArtifact, (ObjectArtifact) uMLArtifactNew, relationKind);
	}
	else if (relationKind == RelationKind.INSTANTIATION && (uMLArtifact.getClass() == ObjectArtifact.class
		&& uMLArtifactNew.getClass() == ClassArtifact.class)) {
	    return new InstantiationRelationLinkArtifact((ClassArtifact) uMLArtifactNew, (ObjectArtifact) uMLArtifact, relationKind);
	}
	return null;

    }

    /**
     * This enumeration list all the adornments that a relation could have
     * 
     * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
     */
    public enum LinkAdornment {

	/**
	 * No adornment -
	 */
	NONE("None", Shape.UNSHAPED, false, true),
	/**
	 * A cross: -x
	 */
	WIRE_CROSS("WireCross", Shape.CROSS, false, true),

	/**
	 * A wire arrow : -&gt;
	 */
	WIRE_ARROW("WireArrow", Shape.ARROW, false, true),

	/**
	 * A simple filled arrow : -|&gt;
	 */
	SOLID_ARROW("SolidArrow", Shape.ARROW, true, false),

	/**
	 * A filled diamond : -&lt;&gt;
	 */
	SOLID_DIAMOND("SolidDiamond", Shape.DIAMOND, true, false),
	/**
	 * A filled circle : -o;
	 */
	SOLID_CIRCLE("SolidCircle", Shape.CIRCLE, true, false),
	/**
	 * A filled diamond with foreground color : -&lt;@&gt;
	 */
	INVERTED_SOLID_DIAMOND("InvertedSolidDiamond", Shape.DIAMOND, true, false, true), 
	/**
	 * A filled circle with foreground color : -@;
	 */
	INVERTED_SOLID_CIRCLE("InvertedSolidCircle", Shape.CIRCLE, true, false, true) ;



	/**
	 * This sub enumeration specify the global shape of the adornment
	 *  
	 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
	 *
	 */
	public enum Shape {
	    /**
	     * Arrow type
	     */
	    ARROW("<"),
	    /**
	     * Cross type
	     */
	    CROSS("x"),
	    /**
	     * Diamond type
	     */
	    DIAMOND("<>"),
	    /**
	     * Circle type
	     */
	    CIRCLE("o"),
	    /**
	     * No shape
	     */
	    UNSHAPED("");

	    private final String idiom;

	    private Shape(final String idiom) {
		this.idiom = idiom;
	    }

	    /**
	     * Getter for the idiom
	     * 
	     * @return a string that represent the shape textually : for arrow  &lt; 
	     */
	    public String getIdiom() {
		return getIdiom(false);
	    }

	    /**
	     * Specific Getter for the idiom which change shape orientation between left &lt; and right &gt; 
	     * 
	     * @param isRight : if the shape is oriented to the rightDIAMOND
	     * @return a string that represent the shape textually in the right orientation : for right arrow  &gt; 
	     */
	    public String getIdiom(final boolean isRight) {
		if (this.idiom.equals("<") && isRight) {
		    return ">";
		}
		return this.idiom;
	    }
	}

	private final boolean isInverted;
	private final boolean isSolid;
	private final Shape shape;
	private final boolean isNavigabilityAdornment;
	private final String name;


	/**
	 * Getter for the name
	 *
	 * @return the name
	 */
	public String getName() {
	    return this.name;
	}
	/**
	 * Static getter of a {@link LinkAdornment} by its name
	 *  
	 * @param linkAdornmentName The name of the {@link LinkAdornment} to retrieve
	 * @return The {@link LinkAdornment} that has linkAdornmentName for name or null if not found
	 */
	public static LinkAdornment getLinkAdornmentFromName(String linkAdornmentName) {
	    for(LinkAdornment linkAdornment : LinkAdornment.values()) {
		if(linkAdornment.getName().equals(linkAdornmentName)) {
		    return linkAdornment;
		}
	    }
	    return null;
	}

	private LinkAdornment(final String name, final Shape shape, final boolean isSolid, final boolean isNavigabilityAdronment) {
	    this(name, shape, isSolid, isNavigabilityAdronment, false);
	}

	private LinkAdornment(final String name, final Shape shape, final boolean isSolid, final boolean isNavigabilityAdornment,
		final boolean isInverted) {
	    this.name = name;
	    this.shape = shape;
	    this.isSolid = isSolid;
	    this.isNavigabilityAdornment = isNavigabilityAdornment;
	    this.isInverted = isInverted;
	}

	/** Getter for the shape
	 * @return the shape
	 */
	public Shape getShape() {
	    return this.shape;
	}


	/** 
	 * Determine if the shape is inverted or not (ie : the fill color is the foreground color)
	 * 
	 * @return <ul>
	 *         <li><b>True</b> if it is inverted</li>
	 *         <li><b>False</b> otherwise</li>
	 *         </ul>
	 */
	public boolean isInverted() {
	    return this.isInverted;
	}

	/**
	 * Determine if the adornment is a navigability adornment : &gt; x or none
	 * 
	 * @return <ul>
	 *         <li><b>True</b> if the adornment is a navigability one</li>
	 *         <li><b>False</b> otherwise</li>
	 *         </ul>
	 */
	public boolean isNavigabilityAdornment() {
	    return this.isNavigabilityAdornment;
	}
	/** 
	 * Determine if the shape is filled or not 
	 * 
	 * @return <ul>
	 *         <li><b>True</b> if it is filled</li>
	 *         <li><b>False</b> otherwise</li>
	 *         </ul>
	 */
	public boolean isSolid() {
	    return this.isSolid;
	}

    }
    /**
     * This enumeration list all the style that a link could have
     * 
     * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
     */
    public enum LinkStyle {

	/**
	 * Dash style : - - - - - 
	 */
	DASHED("Dashed", GfxStyle.DASH),
	/**
	 * Long dash style : -- -- -- -- --
	 */
	LONG_DASHED("LongDashed", GfxStyle.LONGDASH),
	/**
	 * Dash dot style : -.-.-.-.-.- 
	 */
	DASHED_DOTTED("DashedDotted", GfxStyle.DASHDOT),
	/**
	 * Solid style : ------------ 
	 */
	SOLID("Solid", GfxStyle.NONE);

	private final GfxStyle style;
	private final String name;

	/**
	 * Getter for the {@link LinkStyle} name
	 *
	 * @return the {@link LinkStyle} name
	 */
	public String getName() {
	    return this.name;
	}
	/**
	 * Static getter of a {@link LinkStyle} by its name
	 *  
	 * @param linkStyleName The name of the {@link LinkStyle} to retrieve
	 * @return The {@link LinkStyle} that has linkStyleName for name or null if not found
	 */
	public static LinkStyle getLinkStyleFromName(String linkStyleName) {
	    for(LinkStyle linkStyle : LinkStyle.values()) {
		if(linkStyle.getName().equals(linkStyleName)) {
		    return linkStyle;
		}
	    }
	    return null;
	}

	private LinkStyle(final String name, final GfxStyle style) {
	    this.name = name;
	    this.style = style;
	}

	/**
	 * Getter for the {@link GfxStyle}
	 * 
	 * @return the {@link GfxStyle} to set to a line
	 */
	public GfxStyle getGfxStyle() {
	    return this.style;
	}
    }


    protected Point leftPoint = Point.getOrigin();
    protected Point rightPoint = Point.getOrigin();

    protected int order;
    protected boolean isSelfLink = false;
    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#getCenter()
     */
    @Override
    public Point getCenter() {
	return Point.getMiddleOf(this.leftPoint, this.rightPoint);
    }

    @Override
    public int getHeight() {
	return this.leftPoint.getY() < this.rightPoint.getY() ? this.rightPoint.getY()
		- this.leftPoint.getY() : this.leftPoint.getY() - this.rightPoint.getY();
    }

    @Override
    public int[] getOpaque() {
	return null;
    }

    @Override
    public GfxObject getOutline() {
	return null;
    }

    @Override
    public int getWidth() {
	return this.leftPoint.getX() < this.rightPoint.getX() ? this.rightPoint.getX()
		- this.leftPoint.getX() : this.leftPoint.getX() - this.rightPoint.getX();
    }

    @Override
    public boolean isALink() {
	return true;
    }

    @Override
    public boolean isDraggable() {
	return false;
    }

    protected Direction leftDirection = Direction.UNKNOWN;
    protected Direction rightDirection = Direction.UNKNOWN;

    protected void computeDirectionsType() {	
	this.leftDirection = computeDirectionType(this.leftPoint, this.leftUMLArtifact);
	this.rightDirection = computeDirectionType(this.rightPoint, this.rightUMLArtifact);	
    }

    private Direction computeDirectionType(Point point, UMLArtifact uMLArtifact) {
	if (point.getX() == uMLArtifact.getLocation().getX()) {
	    return Direction.LEFT;
	} else if (point.getY() == uMLArtifact.getLocation().getY()) {
	    return Direction.UP;
	} else if (point.getX() == uMLArtifact.getLocation().getX()
		+ uMLArtifact.getWidth()) {
	    return Direction.RIGHT;
	} else if (point.getY() == uMLArtifact.getLocation().getY()
		+ uMLArtifact.getHeight()) {
	    return Direction.DOWN;
	} 
	return Direction.UNKNOWN;

    }
    /**
     * This method add an extra dependency removal for link <br> 
     * to tell other artifact that they don't need to be still dependent on this line
     */
    public abstract void removeCreatedDependency();


}
