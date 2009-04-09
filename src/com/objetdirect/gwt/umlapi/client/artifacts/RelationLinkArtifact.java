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
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public abstract class RelationLinkArtifact extends LinkArtifact {

    /**
     * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
     */
    public enum RelationLinkArtifactPart {
	LEFT_CARDINALITY("Cardinality", true) {
	    @Override
	    public String getText(final Relation relation) {
		return relation.getLeftCardinality();
	    }

	    @Override
	    public void setText(final Relation relation, final String text) {
		relation.setLeftCardinality(text);
	    }
	},
	LEFT_CONSTRAINT("Constraint", true) {
	    @Override
	    public String getText(final Relation relation) {
		return relation.getLeftConstraint();
	    }

	    @Override
	    public void setText(final Relation relation, final String text) {
		relation.setLeftConstraint(text);
	    }
	},
	LEFT_ROLE("Role", true) {
	    @Override
	    public String getText(final Relation relation) {
		return relation.getLeftRole();
	    }

	    @Override
	    public void setText(final Relation relation, final String text) {
		relation.setLeftRole(text);
	    }
	},
	NAME("Name", false) {
	    @Override
	    public String getText(final Relation relation) {
		return relation.getName();
	    }

	    @Override
	    public void setText(final Relation relation, final String text) {
		relation.setName(text);
	    }
	},
	RIGHT_CARDINALITY("Cardinality", false) {
	    @Override
	    public String getText(final Relation relation) {
		return relation.getRightCardinality();
	    }

	    @Override
	    public void setText(final Relation relation, final String text) {
		relation.setRightCardinality(text);
	    }
	},
	RIGHT_CONSTRAINT("Constraint", false) {
	    @Override
	    public String getText(final Relation relation) {
		return relation.getRightConstraint();
	    }

	    @Override
	    public void setText(final Relation relation, final String text) {
		relation.setRightConstraint(text);
	    }
	},
	RIGHT_ROLE("Role", false) {
	    @Override
	    public String getText(final Relation relation) {
		return relation.getRightRole();
	    }

	    @Override
	    public void setText(final Relation relation, final String text) {
		relation.setRightRole(text);
	    }
	};
	private static HashMap<GfxObject, RelationLinkArtifactPart> textGfxObject = new HashMap<GfxObject, RelationLinkArtifactPart>();

	public static RelationLinkArtifactPart getPartForGfxObject(
		final GfxObject text) {
	    return textGfxObject.get(text);
	}

	public static void setGfxObjectTextForPart(final GfxObject text,
		final RelationLinkArtifactPart part) {
	    textGfxObject.put(text, part);
	}

	private boolean isLeft;

	private String name;

	private RelationLinkArtifactPart(final String name, final boolean isLeft) {
	    this.name = name;
	    this.isLeft = isLeft;
	}

	/**
	 * @return the name
	 */
	public String getName() {
	    return name;
	}

	public abstract String getText(Relation relation);

	/**
	 * @return the isLeft
	 */
	public boolean isLeft() {
	    return isLeft;
	}

	public abstract void setText(Relation relation, String text);
    }

    /**
     * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
     */
    protected enum Anchor {
	BOTTOM, LEFT, RIGHT, TOP, UNKNOWN;
    }

    public static RelationLinkArtifact makeLinkArtifact(
	    final ClassArtifact left, final ClassArtifact right,
	    final RelationKind kind) {
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

    public RelationLinkArtifact(final ClassArtifact left,
	    final ClassArtifact right) {
	leftClassArtifact = left;
	left.addDependency(this, right);
	rightClassArtifact = right;
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
	    final RelationFieldEditor editor = new RelationFieldEditor(canvas,
		    this, editPart);
	    editor
		    .startEdition(editPart.getText(relation), GfxManager
			    .getPlatform().getLocationFor(editedGfxObject).getX(), GfxManager
			    .getPlatform().getLocationFor(editedGfxObject).getY()
			    - GfxManager.getPlatform().getHeightFor(
				    editedGfxObject), GfxManager.getPlatform()
			    .getWidthFor(editedGfxObject)
			    + OptionsManager.getRectangleXTotalPadding(), false);
	}
    }

    public void edit(final RelationLinkArtifactPart part) {
	String defaultText;
	switch (part) {
	case NAME:
	    defaultText = leftClassArtifact.getClassName() + "-"
		    + rightClassArtifact.getClassName();
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
	part.setText(relation, defaultText);
	rebuildGfxObject();
	edit(gfxObjectPart.get(part));
    }

    public ClassArtifact getLeftClassArtifact() {
	return leftClassArtifact;
    }

    public ClassArtifact getRightClassArtifact() {
	return rightClassArtifact;
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
	rightMenu.setName(relation.getRelationKind().getName() + " "
		+ leftClassArtifact.getClassName() + " "
		+ adornmentLeft.getShape().getIdiom() + "-"
		+ adornmentRight.getShape().getIdiom(true) + " "
		+ rightClassArtifact.getClassName());
	final MenuBar leftSide = new MenuBar(true);
	final MenuBar rightSide = new MenuBar(true);

	for (final RelationLinkArtifactPart relationLinkArtifactPart : RelationLinkArtifactPart
		.values()) {

	    final MenuBar editDelete = new MenuBar(true);
	    if (relationLinkArtifactPart.getText(relation) != "") {
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
			.addItem(relationLinkArtifactPart.getName(), editDelete);
	    } else {
		if (relationLinkArtifactPart != RelationLinkArtifactPart.NAME) {
		    rightSide.addItem(relationLinkArtifactPart.getName(),
			    editDelete);
		} else {
		    rightMenu.addItem(relationLinkArtifactPart.getName(),
			    editDelete);
		}
	    }
	}
	rightMenu.addItem(leftClassArtifact.getClassName() + " side", leftSide);
	rightMenu.addItem(rightClassArtifact.getClassName() + " side",
		rightSide);

	return rightMenu;
    }

    @Override
    public void removeCreatedDependency() {
	leftClassArtifact.removeDependency(this);
	rightClassArtifact.removeDependency(this);
    }

    @Override
    public void select() {
	GfxManager.getPlatform().moveToFront(textVirtualGroup);
	GfxManager.getPlatform().setStroke(line,
		ThemeManager.getHighlightedForegroundColor(), 2);
	GfxManager.getPlatform().setStroke(arrowVirtualGroup,
		ThemeManager.getHighlightedForegroundColor(), 2);
    }

    public void setLeftCardinality(final String leftCardinality) {
	relation.setLeftCardinality(leftCardinality);
    }

    public void setLeftConstraint(final String leftConstraint) {
	relation.setLeftConstraint(leftConstraint);
    }

    public void setLeftRole(final String leftRole) {
	relation.setLeftRole(leftRole);
    }

    public void setName(final String name) {
	relation.setName(name);
    }

    public void setPartContent(final RelationLinkArtifactPart part,
	    final String newContent) {
	part.setText(relation, newContent);
    }

    public void setRightCardinality(final String rightCardinality) {
	relation.setRightCardinality(rightCardinality);
    }

    public void setRightConstraint(final String rightConstraint) {
	relation.setRightConstraint(rightConstraint);
    }

    public void setRightRole(final String rightRole) {
	relation.setRightRole(rightRole);
    }

    @Override
    public void unselect() {
	GfxManager.getPlatform().setStroke(line,
		ThemeManager.getForegroundColor(), 1);
	GfxManager.getPlatform().setStroke(arrowVirtualGroup,
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

	Point relative_point1 = leftPoint;
	Point relative_point2 = rightPoint;
	final int textWidth = GfxManager.getPlatform().getWidthFor(text);
	if (!isLeft) {
	    relative_point1 = rightPoint;
	    relative_point2 = leftPoint;
	}
	switch (getAnchorType(isLeft ? leftClassArtifact : rightClassArtifact,
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
	Point relative_point1 = leftPoint;
	Point relative_point2 = rightPoint;
	if (!isLeft) {
	    relative_point1 = rightPoint;
	    relative_point2 = leftPoint;
	}
	final int textHeight = GfxManager.getPlatform().getHeightFor(text);
	final int delta = current_delta;
	current_delta += 8; // TODO : Fix Height
	switch (getAnchorType(isLeft ? leftClassArtifact : rightClassArtifact,
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
	gfxObjectPart.clear();
	ArrayList<Point> linePoints = new ArrayList<Point>();
	final boolean isComputationNeededOnLeft = adornmentLeft != LinkAdornment.NONE
		|| adornmentLeft.isCrossed()
		|| relation.getLeftCardinality() + relation.getLeftConstraint()
			+ relation.getLeftRole() != "";
	final boolean isComputationNeededOnRight = adornmentRight != LinkAdornment.NONE
		|| adornmentRight.isCrossed()
		|| relation.getRightCardinality()
			+ relation.getRightConstraint()
			+ relation.getRightRole() != "";
	if (leftClassArtifact != rightClassArtifact) {
	    if (isComputationNeededOnLeft && isComputationNeededOnRight) {
		linePoints = GeometryManager.getPlatform().getLineBetween(
			leftClassArtifact, rightClassArtifact);
		leftPoint = linePoints.get(0);
		rightPoint = linePoints.get(1);
	    } else if (isComputationNeededOnLeft) {
		rightPoint = rightClassArtifact.getCenter();
		leftPoint = GeometryManager.getPlatform().getPointForLine(
			leftClassArtifact, rightPoint);
	    } else if (isComputationNeededOnRight) {
		leftPoint = leftClassArtifact.getCenter();
		rightPoint = GeometryManager.getPlatform().getPointForLine(
			leftClassArtifact, leftPoint);
	    } else {
		leftPoint = leftClassArtifact.getCenter();
		rightPoint = rightClassArtifact.getCenter();
	    }
	    line = GfxManager.getPlatform().buildLine(leftPoint, rightPoint);
	} else {
	    linePoints = GeometryManager.getPlatform().getReflexiveLineFor(
		    leftClassArtifact);
	    leftPoint = linePoints.get(1);
	    rightPoint = linePoints.get(2);
	    line = GfxManager.getPlatform().buildPath();
	    GfxManager.getPlatform().moveTo(line, linePoints.get(0));
	    GfxManager.getPlatform().lineTo(line, linePoints.get(1));
	    GfxManager.getPlatform().lineTo(line, linePoints.get(2));
	    GfxManager.getPlatform().lineTo(line, linePoints.get(3));
	    GfxManager.getPlatform().lineTo(line, linePoints.get(4));
	    GfxManager.getPlatform().setOpacity(line, 0, true);
	}

	GfxManager.getPlatform().setStroke(line,
		ThemeManager.getForegroundColor(), 1);
	GfxManager.getPlatform().setStrokeStyle(line, style.getGfxStyle());
	GfxManager.getPlatform().addToVirtualGroup(gfxObject, line);

	// Making arrows group :
	arrowVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
	GfxManager.getPlatform()
		.addToVirtualGroup(gfxObject, arrowVirtualGroup);
	if (leftClassArtifact != rightClassArtifact) {
	    if (isComputationNeededOnLeft) {
		GfxManager.getPlatform().addToVirtualGroup(
			arrowVirtualGroup,
			GeometryManager.getPlatform().buildAdornment(leftPoint,
				rightPoint, adornmentLeft));
	    }
	    if (isComputationNeededOnRight) {
		GfxManager.getPlatform().addToVirtualGroup(
			arrowVirtualGroup,
			GeometryManager.getPlatform().buildAdornment(
				rightPoint, leftPoint, adornmentRight));
	    }
	} else {
	    if (isComputationNeededOnLeft) {
		GfxManager.getPlatform().addToVirtualGroup(
			arrowVirtualGroup,
			GeometryManager.getPlatform().buildAdornment(
				linePoints.get(4), linePoints.get(3),
				adornmentLeft));
	    }
	}

	// Making the text group :
	textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
	GfxManager.getPlatform().addToVirtualGroup(gfxObject, textVirtualGroup);
	if (relation.getName() != "") {
	    Log.trace("Creating name");
	    final GfxObject nameGfxObject = GfxManager.getPlatform().buildText(
		    relation.getName());

	    GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup,
		    nameGfxObject);

	    GfxManager.getPlatform().setStroke(nameGfxObject,
		    ThemeManager.getBackgroundColor(), 0);
	    GfxManager.getPlatform().setFillColor(nameGfxObject,
		    ThemeManager.getForegroundColor());
	    GfxManager.getPlatform().translate(
		    nameGfxObject, new Point(
		    (leftPoint.getX() + rightPoint.getX() - GfxManager
			    .getPlatform().getWidthFor(nameGfxObject)) / 2,
		    (leftPoint.getY() + rightPoint.getY()) / 2));
	    RelationLinkArtifactPart.setGfxObjectTextForPart(nameGfxObject,
		    RelationLinkArtifactPart.NAME);
	    gfxObjectPart.put(RelationLinkArtifactPart.NAME, nameGfxObject);
	}
	current_delta = 0;
	if (relation.getLeftCardinality() != "") {
	    GfxManager.getPlatform().addToVirtualGroup(
		    textVirtualGroup,
		    createText(relation.getLeftCardinality(),
			    RelationLinkArtifactPart.LEFT_CARDINALITY));
	}
	if (relation.getLeftConstraint() != "") {
	    GfxManager.getPlatform().addToVirtualGroup(
		    textVirtualGroup,
		    createText(relation.getLeftConstraint(),
			    RelationLinkArtifactPart.LEFT_CONSTRAINT));
	}
	if (relation.getLeftRole() != "") {
	    GfxManager.getPlatform().addToVirtualGroup(
		    textVirtualGroup,
		    createText(relation.getLeftRole(),
			    RelationLinkArtifactPart.LEFT_ROLE));
	}
	current_delta = 0;
	if (relation.getRightCardinality() != "") {
	    GfxManager.getPlatform().addToVirtualGroup(
		    textVirtualGroup,
		    createText(relation.getRightCardinality(),
			    RelationLinkArtifactPart.RIGHT_CARDINALITY));
	}
	if (relation.getRightConstraint() != "") {
	    GfxManager.getPlatform().addToVirtualGroup(
		    textVirtualGroup,
		    createText(relation.getRightConstraint(),
			    RelationLinkArtifactPart.RIGHT_CONSTRAINT));
	}
	if (relation.getRightRole() != "") {
	    GfxManager.getPlatform().addToVirtualGroup(
		    textVirtualGroup,
		    createText(relation.getRightRole(),
			    RelationLinkArtifactPart.RIGHT_ROLE));
	}
	GfxManager.getPlatform().moveToBack(gfxObject);
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
	gfxObjectPart.put(part, textGfxObject);
	return textGfxObject;
    }

    private Command deleteCommand(
	    final RelationLinkArtifactPart relationArtifactPart) {
	return new Command() {
	    public void execute() {
		relationArtifactPart.setText(relation, "");
		rebuildGfxObject();
	    }
	};
    }

    private Command editCommand(
	    final RelationLinkArtifactPart relationArtifactPart) {
	return new Command() {
	    public void execute() {
		edit(gfxObjectPart.get(relationArtifactPart));
	    }
	};
    }
}
