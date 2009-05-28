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



    /**
     * Constructor of RelationLinkArtifact
     * 
     * @param uMLArtifact1 First {@link UMLArtifact}
     * @param uMLArtifact2 Second {@link UMLArtifact}
     *
     */
    public LinkArtifact(UMLArtifact uMLArtifact1, UMLArtifact uMLArtifact2) {
	super(true);
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
    protected Point leftDirectionPoint = Point.getOrigin();
    protected Point rightDirectionPoint = Point.getOrigin();
    protected int order;
    Point curveControl;
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
    
    
    
    /**
     * This method add an extra dependency removal for link <br> 
     * to tell other artifact that they don't need to be still dependent on this line
     */
    public abstract void removeCreatedDependency();

    protected GfxObject getLine() {
	if(this.isSelfLink) this.order ++;
	int factor = 50 * ((this.order + 1)/2);
	factor *= (this.order % 2) == 0 ? -1 : 1;
	this.curveControl = GeometryManager.getPlatform().getShiftedCenter(this.leftPoint, this.rightPoint, factor);
	GfxObject line;
	if(!this.isSelfLink) {
	    if(OptionsManager.get("AngularLinks") == 1) {
		line = GfxManager.getPlatform().buildPath();
		GfxManager.getPlatform().moveTo(line, this.leftPoint);
		if(this.leftPoint.getY() < this.rightPoint.getY()) {
		    this.leftDirectionPoint = this.rightDirectionPoint = new Point(this.leftPoint.getX(), this.rightPoint.getY());
		} else {
		    this.leftDirectionPoint = this.rightDirectionPoint = new Point(this.rightPoint.getX(), this.leftPoint.getY());
		}
		GfxManager.getPlatform().lineTo(line, this.leftDirectionPoint);
		GfxManager.getPlatform().lineTo(line, this.rightPoint);
		GfxManager.getPlatform().setOpacity(line, 0, true);
	    } else {
		
		if(this.order == 0) {
		    line = GfxManager.getPlatform().buildLine(this.leftPoint, this.rightPoint);
		    this.leftDirectionPoint = this.rightPoint;
		    this.rightDirectionPoint = this.leftPoint;
		} else {
		    this.leftDirectionPoint = this.curveControl;
		    this.rightDirectionPoint = this.curveControl;
		    line = GfxManager.getPlatform().buildPath();
		    GfxManager.getPlatform().moveTo(line, this.leftPoint);
		    GfxManager.getPlatform().curveTo(line, this.rightPoint, this.curveControl);
		    GfxManager.getPlatform().setOpacity(line, 0, true);
		}
	    }
	} else {
	    if(OptionsManager.get("AngularLinks") == 1) {
		line = GfxManager.getPlatform().buildPath();
		Point rightShiftedPoint =  Point.add(this.rightPoint, new Point(OptionsManager.get("ReflexivePathXGap"), 0));
		Point leftShiftedPoint = Point.add(this.leftPoint, new Point(0, -OptionsManager.get("ReflexivePathYGap")));
		this.leftDirectionPoint = leftShiftedPoint;
		this.rightDirectionPoint = rightShiftedPoint;
		GfxManager.getPlatform().moveTo(line, this.leftPoint);
		GfxManager.getPlatform().lineTo(line, leftShiftedPoint);
		GfxManager.getPlatform().lineTo(line, new Point(rightShiftedPoint.getX(), leftShiftedPoint.getY()));
		GfxManager.getPlatform().lineTo(line, rightShiftedPoint);
		GfxManager.getPlatform().lineTo(line, this.rightPoint);
	    } else {		
		Point edge = new Point(this.rightPoint.getX(), this.leftPoint.getY());
		Point pointsDiff = Point.substract(this.rightPoint, this.leftPoint);
		if(pointsDiff.getX() < pointsDiff.getY()) {
		    this.rightPoint.translate(0, pointsDiff.getX() - pointsDiff.getY());
		    line = GfxManager.getPlatform().buildCircle(pointsDiff.getX());
		} else {
		    this.leftPoint.translate(pointsDiff.getX() - pointsDiff.getY(), 0);
		    line = GfxManager.getPlatform().buildCircle(pointsDiff.getY());
		}
		Point rightShiftedPoint =  Point.add(this.rightPoint, new Point(OptionsManager.get("ReflexivePathXGap"), 0));
		Point leftShiftedPoint = Point.add(this.leftPoint, new Point(0, -OptionsManager.get("ReflexivePathYGap")));
		this.leftDirectionPoint = leftShiftedPoint;
		this.rightDirectionPoint = rightShiftedPoint;
		GfxManager.getPlatform().translate(line, edge);		
	    }
	    GfxManager.getPlatform().setOpacity(line, 0, true);
	}
	return line;
    }

}
