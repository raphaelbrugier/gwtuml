package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.HashMap;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.objetdirect.gwt.umlapi.client.editors.RelationFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLComponent;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * This abstract class represents any relation artifact between two classes
 *  
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public abstract class RelationLinkArtifact extends LinkArtifact {

    /**
     * This enumeration list all text part of a RelationLinkArtifact
     * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
     */
    /**
     * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
     *
     */
    public enum RelationLinkArtifactPart {
	/**
	 * Left end cardinality
	 */
	LEFT_CARDINALITY("Cardinality", true) {
	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation)
	     */
	    @Override
	    public String getText(final Relation relation) {
		return relation.getLeftCardinality();
	    }

	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation, java.lang.String)
	     */
	    @Override
	    public void setText(final Relation relation, final String text) {
		relation.setLeftCardinality(text);
	    }
	},
	/**
	 * Left end constraint
	 */
	LEFT_CONSTRAINT("Constraint", true) {
	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation)
	     */
	    @Override
	    public String getText(final Relation relation) {
		return relation.getLeftConstraint();
	    }

	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation, java.lang.String)
	     */
	    @Override
	    public void setText(final Relation relation, final String text) {
		relation.setLeftConstraint(text);
	    }
	},
	/**
	 * Left end role
	 */
	LEFT_ROLE("Role", true) {
	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation)
	     */
	    @Override
	    public String getText(final Relation relation) {
		return relation.getLeftRole();
	    }

	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation, java.lang.String)
	     */
	    @Override
	    public void setText(final Relation relation, final String text) {
		relation.setLeftRole(text);
	    }
	},
	/**
	 * The relation name
	 */
	NAME("Name", false) {
	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation)
	     */
	    @Override
	    public String getText(final Relation relation) {
		return relation.getName();
	    }

	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation, java.lang.String)
	     */
	    @Override
	    public void setText(final Relation relation, final String text) {
		relation.setName(text);
	    }
	},

	/**
	 * Right end cardinality
	 */
	RIGHT_CARDINALITY("Cardinality", false) {
	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation)
	     */
	    @Override
	    public String getText(final Relation relation) {
		return relation.getRightCardinality();
	    }

	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation, java.lang.String)
	     */
	    @Override
	    public void setText(final Relation relation, final String text) {
		relation.setRightCardinality(text);
	    }
	},
	/**
	 * Right end constraint
	 */
	RIGHT_CONSTRAINT("Constraint", false) {
	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation)
	     */
	    @Override
	    public String getText(final Relation relation) {
		return relation.getRightConstraint();
	    }

	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation, java.lang.String)
	     */
	    @Override
	    public void setText(final Relation relation, final String text) {
		relation.setRightConstraint(text);
	    }
	},
	/**
	 * Right end role
	 */
	RIGHT_ROLE("Role", false) {
	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation)
	     */
	    @Override
	    public String getText(final Relation relation) {
		return relation.getRightRole();
	    }

	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation, java.lang.String)
	     */
	    @Override
	    public void setText(final Relation relation, final String text) {
		relation.setRightRole(text);
	    }
	};
	
	private static HashMap<GfxObject, RelationLinkArtifactPart> textGfxObject = new HashMap<GfxObject, RelationLinkArtifactPart>();

	/**
	 * Getter for the part represented by the graphical object
	 * 
	 * @param gfxObjectText The graphical object to retrieve the relationLinkArtifactPart
	 * @return The RelationLinkArtifactPart represented by the graphical object
	 */
	public static RelationLinkArtifactPart getPartForGfxObject(final GfxObject gfxObjectText) {
	    return textGfxObject.get(gfxObjectText);
	}

	/**
	 * Setter of the relation between a RelationLinkArtifactPart and his graphical object
	 * 
	 * @param gfxObjectText The graphical object representing the relationLinkArtifactPart
	 * @param relationLinkArtifactPart the relationLinkArtifactPart represented by gfxObjecttext
	 */
	public static void setGfxObjectTextForPart(final GfxObject gfxObjectText, final RelationLinkArtifactPart relationLinkArtifactPart) {
	    textGfxObject.put(gfxObjectText, relationLinkArtifactPart);
	}

	private boolean isLeft;

	private String name;

	private RelationLinkArtifactPart(final String name, final boolean isLeft) {
	    this.name = name;
	    this.isLeft = isLeft;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
	    return this.name;
	}

	/**
	 * Getter for the text contained by the graphical object for a part
	 * 
	 * @param relation The relation {@link UMLComponent} this enumeration is about
	 * @return the text corresponding to this part
	 */
	public abstract String getText(Relation relation);

	/**
	 * Determine if this part is "Left"
	 * 
	 * @return <ul>
	 *         <li><b>True</b> if it is actually "Left"</li>
	 *         <li><b>False</b> otherwise</li>
	 *         </ul>
	 */
	public boolean isLeft() {
	    return this.isLeft;
	}

	/**
	 * Setter to affect a text to a part of the relation {@link UMLComponent}
	 * 
	 * @param relation The relation {@link UMLComponent} this enumeration is about
	 * @param text The text corresponding to this part
	 */
	public abstract void setText(Relation relation, String text);
    }

    /**
     * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
     */
    protected enum Anchor {
	BOTTOM, LEFT, RIGHT, TOP, UNKNOWN;
    }

    /**
     * This class method is an easy way to create a {@link RelationLinkArtifact} by knowing its {@link RelationKind}
     * 
     * @param left The left {@link ClassArtifact} of the relation
     * @param right The right {@link ClassArtifact} of the relation
     * @param kind The type of its relation
     * 
     * @return The created new {@link RelationLinkArtifact} in type of kind
     */
    public static RelationLinkArtifact makeLinkArtifact(final ClassArtifact left, final ClassArtifact right, final RelationKind kind) {
	switch (kind) {
	case AGGREGATION:
	    return new RelationLinkAggregationArtifact(left, right);
	case COMPOSITION:
	    return new RelationLinkCompositionArtifact(left, right);
	case DEPENDENCY:
	    return new RelationLinkDependencyArtifact(left, right);
	case GENERALIZATION:
	    return new RelationLinkGeneralizationArtifact(left, right);
	case ASSOCIATION:
	    return new RelationLinkAssociationArtifact(left, right);
	case REALIZATION:
	    return new RelationLinkRealizationArtifact(left, right);
	default:
	    return new RelationLinkAggregationArtifact(left, right);
	}

    }

    protected GfxObject arrowVirtualGroup;
    protected ClassArtifact leftClassArtifact;
    protected GfxObject line;
    protected Relation relation;
    protected ClassArtifact relationClass;
    protected ClassArtifact rightClassArtifact;
    protected GfxObject textVirtualGroup;
    private int current_delta;
    private final HashMap<RelationLinkArtifactPart, GfxObject> gfxObjectPart = new HashMap<RelationLinkArtifactPart, GfxObject>();

    /**
     * Constructor of {@link RelationLinkArtifact}
     * 
     * @param left The left {@link ClassArtifact} of the relation
     * @param right The right {@link ClassArtifact} of the relation
     */
    public RelationLinkArtifact(final ClassArtifact left, final ClassArtifact right) {
	this.leftClassArtifact = left;
	left.addDependency(this, right);
	this.rightClassArtifact = right;
	if (right != left) {
	    right.addDependency(this, left);
	}
    }

    @Override
    public void edit(final GfxObject editedGfxObject) {
	final RelationLinkArtifactPart editPart = RelationLinkArtifactPart
		.getPartForGfxObject(editedGfxObject);
	if (editPart == null) {
	    edit(RelationLinkArtifactPart.NAME);
	} else {
	    final RelationFieldEditor editor = new RelationFieldEditor(this.canvas,
		    this, editPart);
	    editor
		    .startEdition(editPart.getText(this.relation), GfxManager
			    .getPlatform().getLocationFor(editedGfxObject).getX(), GfxManager
			    .getPlatform().getLocationFor(editedGfxObject).getY()
			    - GfxManager.getPlatform().getHeightFor(
				    editedGfxObject), GfxManager.getPlatform()
			    .getWidthFor(editedGfxObject)
			    + OptionsManager.getRectangleXTotalPadding(), false);
	}
    }

    /**
     * Request an edition on a {@link RelationLinkArtifactPart}  and set if the text is empty a default text
     * @param part The {@link RelationLinkArtifactPart} to edit
     */
    public void edit(final RelationLinkArtifactPart part) {
	String defaultText;
	switch (part) {
	case NAME:
	    defaultText = this.leftClassArtifact.getClassName() + "-"
		    + this.rightClassArtifact.getClassName();
	    break;
	case LEFT_CARDINALITY:
	case RIGHT_CARDINALITY:
	    defaultText = "0..*";
	    break;
	case LEFT_CONSTRAINT:
	case RIGHT_CONSTRAINT:
	    defaultText = "{union}";
	    break;
	case LEFT_ROLE:
	case RIGHT_ROLE:
	    defaultText = "role";
	    break;
	default:
	    defaultText = "?";

	}
	part.setText(this.relation, defaultText);
	rebuildGfxObject();
	edit(this.gfxObjectPart.get(part));
    }

    /**
     * Getter for the left {@link ClassArtifact} of this relation 
     * 
     * @return the left {@link ClassArtifact} of this relation
     */
    public ClassArtifact getLeftClassArtifact() {
	return this.leftClassArtifact;
    }

    /**
     * Getter for the right {@link ClassArtifact} of this relation 
     * 
     * @return the right {@link ClassArtifact} of this relation
     */
    public ClassArtifact getRightClassArtifact() {
	return this.rightClassArtifact;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.objetdirect.gwt.umlapi.client.artifacts.links.RelationshipLinkArtifact
     * #getRightMenu()
     */
    @Override
    public MenuBarAndTitle getRightMenu() {
	final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
	rightMenu.setName(this.relation.getRelationKind().getName() + " "
		+ this.leftClassArtifact.getClassName() + " "
		+ this.adornmentLeft.getShape().getIdiom() + "-"
		+ this.adornmentRight.getShape().getIdiom(true) + " "
		+ this.rightClassArtifact.getClassName());
	final MenuBar leftSide = new MenuBar(true);
	final MenuBar rightSide = new MenuBar(true);

	for (final RelationLinkArtifactPart relationLinkArtifactPart : RelationLinkArtifactPart
		.values()) {

	    final MenuBar editDelete = new MenuBar(true);
	    if (relationLinkArtifactPart.getText(this.relation) != "") {
		editDelete.addItem("Edit",
			editCommand(relationLinkArtifactPart));
		editDelete.addItem("Delete",
			deleteCommand(relationLinkArtifactPart));
	    } else {
		editDelete.addItem("Create",
			createCommand(relationLinkArtifactPart));
	    }
	    if (relationLinkArtifactPart.isLeft) {
		leftSide
			.addItem(relationLinkArtifactPart.toString(), editDelete);
	    } else {
		if (relationLinkArtifactPart != RelationLinkArtifactPart.NAME) {
		    rightSide.addItem(relationLinkArtifactPart.toString(),
			    editDelete);
		} else {
		    rightMenu.addItem(relationLinkArtifactPart.toString(),
			    editDelete);
		}
	    }
	}
	rightMenu.addItem(this.leftClassArtifact.getClassName() + " side", leftSide);
	rightMenu.addItem(this.rightClassArtifact.getClassName() + " side",
		rightSide);

	return rightMenu;
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact#removeCreatedDependency()
     */
    @Override
    public void removeCreatedDependency() {
	this.leftClassArtifact.removeDependency(this);
	this.rightClassArtifact.removeDependency(this);
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#select()
     */
    @Override
    public void select() {
	GfxManager.getPlatform().moveToFront(this.textVirtualGroup);
	GfxManager.getPlatform().setStroke(this.line,
		ThemeManager.getHighlightedForegroundColor(), 2);
	GfxManager.getPlatform().setStroke(this.arrowVirtualGroup,
		ThemeManager.getHighlightedForegroundColor(), 2);
    }

    /**
     * Setter for the leftCardinality in {@link Relation}
     * This does not update the graphical object
     * 
     * @param leftCardinality The leftCardinality text to be set
     */
    public void setLeftCardinality(final String leftCardinality) {
	this.relation.setLeftCardinality(leftCardinality);
    }

    /**
     * Setter for the leftConstraint in {@link Relation}
     * This does not update the graphical object
     * 
     * @param leftConstraint The leftConstraint text to be set
     */
    public void setLeftConstraint(final String leftConstraint) {
	this.relation.setLeftConstraint(leftConstraint);
    }
    
    /**
     * Setter for the leftRole in {@link Relation}
     * This does not update the graphical object
     * 
     * @param leftRole The leftRole text to be set
     */
    public void setLeftRole(final String leftRole) {
	this.relation.setLeftRole(leftRole);
    }

    /**
     * Setter for the name in {@link Relation}
     * This does not update the graphical object
     * 
     * @param name The name text to be set
     */
    public void setName(final String name) {
	this.relation.setName(name);
    }

    /**
     * Setter of a part text
     * @param part The {@link RelationLinkArtifactPart} in which the text is to be updated 
     * @param newContent The new text to set for this part 
     */
    public void setPartContent(final RelationLinkArtifactPart part,
	    final String newContent) {
	part.setText(this.relation, newContent);
    }

    /**
     * Setter for the rightCardinality in {@link Relation}
     * This does not update the graphical object
     * 
     * @param rightCardinality The rightCardinality text to be set
     */
    public void setRightCardinality(final String rightCardinality) {
	this.relation.setRightCardinality(rightCardinality);
    }

    /**
     * Setter for the rightConstraint in {@link Relation}
     * This does not update the graphical object
     * 
     * @param rightConstraint The rightConstraint text to be set
     */
    public void setRightConstraint(final String rightConstraint) {
	this.relation.setRightConstraint(rightConstraint);
    }
    
    /**
     * Setter for the rightRole in {@link Relation}
     * This does not update the graphical object
     * 
     * @param rightRole The rightRole text to be set
     */
    public void setRightRole(final String rightRole) {
	this.relation.setRightRole(rightRole);
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#unselect()
     */
    @Override
    public void unselect() {
	GfxManager.getPlatform().setStroke(this.line,
		ThemeManager.getForegroundColor(), 1);
	GfxManager.getPlatform().setStroke(this.arrowVirtualGroup,
		ThemeManager.getForegroundColor(), 1);
    }

    Anchor getAnchorType(final ClassArtifact classArtifact, final Point point) {
	if (point.getX() == classArtifact.getLocation().getX()) {
	    return Anchor.LEFT;
	} else if (point.getY() == classArtifact.getLocation().getY()) {
	    return Anchor.TOP;
	} else if (point.getX() == classArtifact.getLocation().getX()
		+ classArtifact.getWidth()) {
	    return Anchor.RIGHT;
	} else if (point.getY() == classArtifact.getLocation().getY()
		+ classArtifact.getHeight()) {
	    return Anchor.BOTTOM;
	}
	return Anchor.UNKNOWN;
    }

    int getTextX(final GfxObject text, final boolean isLeft) {

	Point relative_point1 = this.leftPoint;
	Point relative_point2 = this.rightPoint;
	final int textWidth = GfxManager.getPlatform().getWidthFor(text);
	if (!isLeft) {
	    relative_point1 = this.rightPoint;
	    relative_point2 = this.leftPoint;
	}
	switch (getAnchorType(isLeft ? this.leftClassArtifact : this.rightClassArtifact,
		relative_point1)) {
	case LEFT:
	    return relative_point1.getX() - textWidth
		    - OptionsManager.getRectangleLeftPadding();
	case RIGHT:
	    return relative_point1.getX()
		    + OptionsManager.getRectangleRightPadding();
	case TOP:
	case BOTTOM:
	case UNKNOWN:
	    if (relative_point1.getX() < relative_point2.getX()) {
		return relative_point1.getX() - textWidth
			- OptionsManager.getRectangleLeftPadding();
	    }
	    return relative_point1.getX()
		    + OptionsManager.getRectangleRightPadding();
	}
	return 0;
    }

    int getTextY(final GfxObject text, final boolean isLeft) {
	Point relative_point1 = this.leftPoint;
	Point relative_point2 = this.rightPoint;
	if (!isLeft) {
	    relative_point1 = this.rightPoint;
	    relative_point2 = this.leftPoint;
	}
	final int textHeight = GfxManager.getPlatform().getHeightFor(text);
	final int delta = this.current_delta;
	this.current_delta += 8; // TODO : Fix Height
	switch (getAnchorType(isLeft ? this.leftClassArtifact : this.rightClassArtifact,
		relative_point1)) {
	case LEFT:
	case RIGHT:
	    if (relative_point1.getY() > relative_point2.getY()) {
		return relative_point1.getY()
			+ OptionsManager.getRectangleBottomPadding()
			+ textHeight + delta;
	    }
	    return relative_point1.getY()
		    - OptionsManager.getRectangleTopPadding() - delta;
	case TOP:
	    return relative_point1.getY()
		    - OptionsManager.getRectangleTopPadding() - delta;
	case BOTTOM:
	case UNKNOWN:
	    return relative_point1.getY() + textHeight
		    + OptionsManager.getRectangleBottomPadding() + delta;
	}
	return 0;
    }

    @Override
    protected void buildGfxObject() {
	this.gfxObjectPart.clear();
	ArrayList<Point> linePoints = new ArrayList<Point>();
	final boolean isComputationNeededOnLeft = this.adornmentLeft != LinkAdornment.NONE
		|| this.adornmentLeft.isCrossed()
		|| this.relation.getLeftCardinality() + this.relation.getLeftConstraint()
			+ this.relation.getLeftRole() != "";
	final boolean isComputationNeededOnRight = this.adornmentRight != LinkAdornment.NONE
		|| this.adornmentRight.isCrossed()
		|| this.relation.getRightCardinality()
			+ this.relation.getRightConstraint()
			+ this.relation.getRightRole() != "";
	if (this.leftClassArtifact != this.rightClassArtifact) {
	    if (isComputationNeededOnLeft && isComputationNeededOnRight) {
		linePoints = GeometryManager.getPlatform().getLineBetween(
			this.leftClassArtifact, this.rightClassArtifact);
		this.leftPoint = linePoints.get(0);
		this.rightPoint = linePoints.get(1);
	    } else if (isComputationNeededOnLeft) {
		this.rightPoint = this.rightClassArtifact.getCenter();
		this.leftPoint = GeometryManager.getPlatform().getPointForLine(
			this.leftClassArtifact, this.rightPoint);
	    } else if (isComputationNeededOnRight) {
		this.leftPoint = this.leftClassArtifact.getCenter();
		this.rightPoint = GeometryManager.getPlatform().getPointForLine(
			this.leftClassArtifact, this.leftPoint);
	    } else {
		this.leftPoint = this.leftClassArtifact.getCenter();
		this.rightPoint = this.rightClassArtifact.getCenter();
	    }
	    this.line = GfxManager.getPlatform().buildLine(this.leftPoint, this.rightPoint);
	} else {
	    linePoints = GeometryManager.getPlatform().getReflexiveLineFor(
		    this.leftClassArtifact);
	    this.leftPoint = linePoints.get(1);
	    this.rightPoint = linePoints.get(2);
	    this.line = GfxManager.getPlatform().buildPath();
	    GfxManager.getPlatform().moveTo(this.line, linePoints.get(0));
	    GfxManager.getPlatform().lineTo(this.line, linePoints.get(1));
	    GfxManager.getPlatform().lineTo(this.line, linePoints.get(2));
	    GfxManager.getPlatform().lineTo(this.line, linePoints.get(3));
	    GfxManager.getPlatform().lineTo(this.line, linePoints.get(4));
	    GfxManager.getPlatform().setOpacity(this.line, 0, true);
	}

	GfxManager.getPlatform().setStroke(this.line,
		ThemeManager.getForegroundColor(), 1);
	GfxManager.getPlatform().setStrokeStyle(this.line, this.style.getGfxStyle());
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.line);

	// Making arrows group :
	this.arrowVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
	GfxManager.getPlatform()
		.addToVirtualGroup(this.gfxObject, this.arrowVirtualGroup);
	if (this.leftClassArtifact != this.rightClassArtifact) {
	    if (isComputationNeededOnLeft) {
		GfxManager.getPlatform().addToVirtualGroup(
			this.arrowVirtualGroup,
			GeometryManager.getPlatform().buildAdornment(this.leftPoint,
				this.rightPoint, this.adornmentLeft));
	    }
	    if (isComputationNeededOnRight) {
		GfxManager.getPlatform().addToVirtualGroup(
			this.arrowVirtualGroup,
			GeometryManager.getPlatform().buildAdornment(
				this.rightPoint, this.leftPoint, this.adornmentRight));
	    }
	} else {
	    if (isComputationNeededOnLeft) {
		GfxManager.getPlatform().addToVirtualGroup(
			this.arrowVirtualGroup,
			GeometryManager.getPlatform().buildAdornment(
				linePoints.get(4), linePoints.get(3),
				this.adornmentLeft));
	    }
	}

	// Making the text group :
	this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.textVirtualGroup);
	if (this.relation.getName() != "") {
	    Log.trace("Creating name");
	    final GfxObject nameGfxObject = GfxManager.getPlatform().buildText(
		    this.relation.getName());

	    GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup,
		    nameGfxObject);

	    GfxManager.getPlatform().setStroke(nameGfxObject,
		    ThemeManager.getBackgroundColor(), 0);
	    GfxManager.getPlatform().setFillColor(nameGfxObject,
		    ThemeManager.getForegroundColor());
	    GfxManager.getPlatform().translate(
		    nameGfxObject, new Point(
		    (this.leftPoint.getX() + this.rightPoint.getX() - GfxManager
			    .getPlatform().getWidthFor(nameGfxObject)) / 2,
		    (this.leftPoint.getY() + this.rightPoint.getY()) / 2));
	    RelationLinkArtifactPart.setGfxObjectTextForPart(nameGfxObject,
		    RelationLinkArtifactPart.NAME);
	    this.gfxObjectPart.put(RelationLinkArtifactPart.NAME, nameGfxObject);
	}
	this.current_delta = 0;
	if (this.relation.getLeftCardinality() != "") {
	    GfxManager.getPlatform().addToVirtualGroup(
		    this.textVirtualGroup,
		    createText(this.relation.getLeftCardinality(),
			    RelationLinkArtifactPart.LEFT_CARDINALITY));
	}
	if (this.relation.getLeftConstraint() != "") {
	    GfxManager.getPlatform().addToVirtualGroup(
		    this.textVirtualGroup,
		    createText(this.relation.getLeftConstraint(),
			    RelationLinkArtifactPart.LEFT_CONSTRAINT));
	}
	if (this.relation.getLeftRole() != "") {
	    GfxManager.getPlatform().addToVirtualGroup(
		    this.textVirtualGroup,
		    createText(this.relation.getLeftRole(),
			    RelationLinkArtifactPart.LEFT_ROLE));
	}
	this.current_delta = 0;
	if (this.relation.getRightCardinality() != "") {
	    GfxManager.getPlatform().addToVirtualGroup(
		    this.textVirtualGroup,
		    createText(this.relation.getRightCardinality(),
			    RelationLinkArtifactPart.RIGHT_CARDINALITY));
	}
	if (this.relation.getRightConstraint() != "") {
	    GfxManager.getPlatform().addToVirtualGroup(
		    this.textVirtualGroup,
		    createText(this.relation.getRightConstraint(),
			    RelationLinkArtifactPart.RIGHT_CONSTRAINT));
	}
	if (this.relation.getRightRole() != "") {
	    GfxManager.getPlatform().addToVirtualGroup(
		    this.textVirtualGroup,
		    createText(this.relation.getRightRole(),
			    RelationLinkArtifactPart.RIGHT_ROLE));
	}
	GfxManager.getPlatform().moveToBack(this.gfxObject);
    }

    private Command createCommand(
	    final RelationLinkArtifactPart relationArtifactPart) {
	return new Command() {
	    public void execute() {
		edit(relationArtifactPart);
	    }
	};
    }

    private GfxObject createText(final String text,
	    final RelationLinkArtifactPart part) {
	final GfxObject textGfxObject = GfxManager.getPlatform()
		.buildText(text);

	GfxManager.getPlatform().setStroke(textGfxObject,
		ThemeManager.getBackgroundColor(), 0);
	GfxManager.getPlatform().setFillColor(textGfxObject,
		ThemeManager.getForegroundColor());

	Log.trace("Creating text : " + text + " at "
		+ getTextX(textGfxObject, part.isLeft) + " : "
		+ getTextY(textGfxObject, part.isLeft));
	GfxManager.getPlatform().translate(textGfxObject, new Point(
		getTextX(textGfxObject, part.isLeft),
		getTextY(textGfxObject, part.isLeft)));
	RelationLinkArtifactPart.setGfxObjectTextForPart(textGfxObject, part);
	this.gfxObjectPart.put(part, textGfxObject);
	return textGfxObject;
    }

    private Command deleteCommand(
	    final RelationLinkArtifactPart relationArtifactPart) {
	return new Command() {
	    public void execute() {
		relationArtifactPart.setText(RelationLinkArtifact.this.relation, "");
		rebuildGfxObject();
	    }
	};
    }

    private Command editCommand(
	    final RelationLinkArtifactPart relationArtifactPart) {
	return new Command() {
	    public void execute() {
		edit(RelationLinkArtifact.this.gfxObjectPart.get(relationArtifactPart));
	    }
	};
    }
}
