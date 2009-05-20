package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLRelation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLRelation.RelationKind;

/**
 * This abstract class specialize an {@link UMLArtifact} in a link type artifact
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public abstract class LinkArtifact extends UMLArtifact {

    /**
     * Constructor of LinkArtifact
     */
    public LinkArtifact() {
	super(true);
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
	    else if (uMLArtifact.getClass() == ObjectArtifact.class
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
	 * A filled diamond with foreground color : -&lt;@&gt;
	 */
	INVERTED_SOLID_DIAMOND("InvertedSolidDiamond", Shape.DIAMOND, true, false, true);
	
	

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
	     * @param isRight : if the shape is oriented to the right
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

    protected UMLRelation relation;
    protected Point leftPoint = Point.getOrigin();
    protected Point rightPoint = Point.getOrigin();

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
}
