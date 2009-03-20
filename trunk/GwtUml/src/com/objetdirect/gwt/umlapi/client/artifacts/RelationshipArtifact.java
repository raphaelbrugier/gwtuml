package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Geometry;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

public class RelationshipArtifact extends LineArtifact {

	public enum RelationShipArtifactPart {
		LEFT_CARDINALITY, LEFT_CONSTRAINT, LEFT_ROLE, NAME, RIGHT_CARDINALITY, RIGHT_CONSTRAINT, RIGHT_ROLE;
		
		private static HashMap<GfxObject, RelationShipArtifactPart> attributeGfxObjects = new HashMap<GfxObject, RelationShipArtifactPart>();
		public static GfxObject setGfxObjectTextForPart(GfxObject text, RelationShipArtifactPart part) {
			attributeGfxObjects.put(text, part);
			return text;
		}
		public static RelationShipArtifactPart getPartForGfxObject(GfxObject text) {
			return attributeGfxObjects.get(text);
		}
	}

	protected enum Anchor {
		TOP, BOTTOM, LEFT, RIGHT, UNKNOWN;
	}

	boolean arrowOnLeft = false;
	boolean arrowOnRight = false;
	private int current_delta;
	private Relation relation;

	GfxObject line = null;

	/*GfxObject leftArrow = null;
	GfxObject rightArrow = null;*/
	GfxObject arrowVirtualGroup;

	/*
	GfxObject leftCardinalityText = null;
	GfxObject leftConstraintText = null;
	GfxObject leftRoleText = null;
	GfxObject nameText = null;	
	GfxObject rightCardinalityText = null;
	GfxObject rightConstraintText = null;
	GfxObject rightRoleText = null;	
	 */
	GfxObject textVirtualGroup;

	ClassArtifact leftClassArtifact;
	ClassArtifact relationClass = null;
	ClassArtifact rightClassArtifact;


	//private RelationshipEditor editor;

	public RelationshipArtifact(ClassArtifact left, ClassArtifact right) {
		this.leftClassArtifact = left;
		left.addDependency(this);
		this.rightClassArtifact = right;
		right.addDependency(this);
		relation = new Relation();

		//this.editor = new RelationshipEditor(this);
	}

	public void edit(GfxObject gfxObject, int x, int y) {
		/*RelationShipArtifactPart subPart = getSubPart(gfxObject);
		editor.editPart(subPart);*/
		Log.fatal("------> " + RelationShipArtifactPart.getPartForGfxObject(gfxObject));
	}
	/*
	public int getHeight(RelationShipArtifactPart relationShipArtifactPart) {
		GfxObject text = getTextForPart(relationShipArtifactPart);
		if (text != null)
			return  GfxManager.getPlatform().getHeightFor(text);
		return 0;
	}*/

	public ClassArtifact getLeftClassArtifact() {
		return leftClassArtifact;
	}

	public ClassArtifact getRightClassArtifact() {
		return rightClassArtifact;
	}

	public LinkedHashMap<String, Command> getRightMenu() {

		LinkedHashMap<String, Command> rightMenu = new LinkedHashMap<String, Command>();

		Command doNothing = new Command() {
			public void execute() {
			}
		};
		Command remove = new Command() {
			public void execute() {
				getCanvas().removeSelected();
			}
		};
		rightMenu.put("RelationShip " + leftClassArtifact.getClassName() + " <-> "
				+ rightClassArtifact.getClassName(), doNothing);
		rightMenu.put("-", null);
		rightMenu.put("> Edit", doNothing);
		rightMenu.put("> Reverse", doNothing);
		rightMenu.put("> Delete", remove);

		return rightMenu;
	}


	/*public String getStringForPart(
			RelationShipArtifactPart relationShipArtifactPart) {
		switch (relationShipArtifactPart) {
		case NAME:
			return name;
		case LEFT_CARDINALITY:
			return leftCardinality;
		case RIGHT_CARDINALITY:
			return rightCardinality;
		case LEFT_ROLE:
			return leftRole;
		case RIGHT_ROLE:
			return rightRole;
		case LEFT_CONSTRAINT:
			return leftConstraint;
		case RIGHT_CONSTRAINT:
			return rightConstraint;
		}
		return null;
	}

	public RelationShipArtifactPart getSubPart(GfxObject gfxObject) {
		if (gfxObject == nameText)
			return RelationShipArtifactPart.NAME;
		if (gfxObject == leftCardinalityText)
			return RelationShipArtifactPart.LEFT_CARDINALITY;
		if (gfxObject == rightCardinalityText)
			return RelationShipArtifactPart.RIGHT_CARDINALITY;
		if (gfxObject == leftConstraintText)
			return RelationShipArtifactPart.LEFT_CONSTRAINT;
		if (gfxObject == rightConstraintText)
			return RelationShipArtifactPart.RIGHT_CONSTRAINT;
		if (gfxObject == leftRoleText)
			return RelationShipArtifactPart.LEFT_ROLE;
		if (gfxObject == rightRoleText)
			return RelationShipArtifactPart.RIGHT_ROLE;
		return null;

	}

	public GfxObject getTextForPart(
			RelationShipArtifactPart relationShipArtifactPart) {
		switch (relationShipArtifactPart) {
		case NAME:
			return nameText;
		case LEFT_CARDINALITY:
			return leftCardinalityText;
		case RIGHT_CARDINALITY:
			return rightCardinalityText;
		case LEFT_ROLE:
			return leftRoleText;
		case RIGHT_ROLE:
			return rightRoleText;
		case LEFT_CONSTRAINT:
			return leftConstraintText;
		case RIGHT_CONSTRAINT:
			return rightConstraintText;
		}
		return null;
	}

	public int getTextX(RelationShipArtifactPart relationShipArtifactPart) {
		return getTextX(relationShipArtifactPart,
				getTextForPart(relationShipArtifactPart));
	}

	public int getTextY(RelationShipArtifactPart relationShipArtifactPart) {
		return getTextY(relationShipArtifactPart,
				getTextForPart(relationShipArtifactPart));
	}

	public int getWidth(RelationShipArtifactPart relationShipArtifactPart) {
		GfxObject text = getTextForPart(relationShipArtifactPart);
		if (text != null)
			return  GfxManager.getPlatform().getWidthFor(text);
		return 0;
	}*/

	public void select() {
		GfxManager.getPlatform().moveToFront(gfxObject);
		GfxManager.getPlatform().setStroke(line, ThemeManager.getHighlightedForegroundColor(), 2);
		GfxManager.getPlatform().setStroke(arrowVirtualGroup, ThemeManager.getHighlightedForegroundColor(), 2);

	}
	public void setLeftArrow(boolean arrowOnLeft) {
		this.arrowOnLeft = arrowOnLeft;
	}
	
	public void unselect() {
		GfxManager.getPlatform().setStroke(line, ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(arrowVirtualGroup, ThemeManager.getForegroundColor(), 1);
	}

	Anchor getAnchorType(ClassArtifact classArtifact, int x1, int y1) {
		if (x1 == classArtifact.getX())	return Anchor.LEFT;
		else if (y1 == classArtifact.getY()) return Anchor.TOP;
		else if (x1 == classArtifact.getX() + classArtifact.getWidth())	return Anchor.RIGHT;
		else if (y1 == classArtifact.getY() + classArtifact.getHeight()) return Anchor.BOTTOM;
		return Anchor.UNKNOWN;
	}

	int getTextX(GfxObject text, boolean isLeft) {
		int relative_x1 = x1;
		int relative_x2 = x2;
		int relative_y1 = y1;
		int textWidth =  GfxManager.getPlatform().getWidthFor(text);
		if(!isLeft) {
			relative_x1 = x2;
			relative_x2 = x1;
			relative_y1 = y2;
		}
		switch (getAnchorType(isLeft ? leftClassArtifact : rightClassArtifact, relative_x1, relative_y1)) {
		case LEFT: return relative_x1 - textWidth - OptionsManager.getRectangleLeftPadding();
		case RIGHT:	return relative_x1 + OptionsManager.getRectangleRightPadding();
		case TOP: case BOTTOM: case UNKNOWN: 
			if (relative_x1 < relative_x2) return relative_x1 - textWidth - OptionsManager.getRectangleLeftPadding();
			else return relative_x1 + OptionsManager.getRectangleRightPadding();
		}
		return 0;
	}

	int getTextY(GfxObject text, boolean isLeft) {
		int relative_x1 = x1;		
		int relative_y1 = y1;
		int relative_y2 = y2;
		if(!isLeft) {
			relative_x1 = x2;
			relative_y1 = y2;
			relative_y2 = y1;
		}
		int textHeight =   GfxManager.getPlatform().getHeightFor(text);
		int delta = current_delta;
		current_delta += textHeight;
		switch (getAnchorType(isLeft ? leftClassArtifact : rightClassArtifact, relative_x1, relative_y1)) {
		case LEFT: case RIGHT:
			if (relative_y1 > relative_y2) return relative_y1 + OptionsManager.getRectangleBottomPadding() + textHeight + delta;
			else return relative_y1 - OptionsManager.getRectangleTopPadding()  - delta;
		case TOP: return relative_y1 - OptionsManager.getRectangleTopPadding() - delta;
		case BOTTOM: case UNKNOWN: return relative_y1 + textHeight + OptionsManager.getRectangleBottomPadding() + delta;
		}
		return 0;
	}

	@Override
	protected void buildGfxObject() {

		//int[] lineBounds = Geometry.computeLineBounds(leftClassArtifact, rightClassArtifact);
		//setBounds(Math.round(lineBounds[0]), Math.round(lineBounds[1]), Math.round(lineBounds[2]),	Math.round(lineBounds[3]));
		//line = GfxManager.getPlatform().buildLine(x1, y1, x2, y2);
		Point lineLeftPoint  = Geometry.getPointForLine(leftClassArtifact, new Point(rightClassArtifact.getCenterX(), rightClassArtifact.getCenterY()));
		Point lineRightPoint = Geometry.getPointForLine(rightClassArtifact, new Point(leftClassArtifact.getCenterX(), leftClassArtifact.getCenterY()));
		x1 = lineLeftPoint.getX();
		y1 = lineLeftPoint.getY();
		x2 = lineRightPoint.getX();
		y2 = lineRightPoint.getY();
		
		line = GfxManager.getPlatform().buildLine(x1, y1, x2, y2);
		GfxManager.getPlatform().setStroke(line, ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, line);

		// Making arrows group :
		arrowVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, arrowVirtualGroup);
		
		if (arrowOnLeft)
			GfxManager.getPlatform().addToVirtualGroup(arrowVirtualGroup, Geometry.buildArrow(x1, y1, x2, y2));
		if (arrowOnRight) 
			GfxManager.getPlatform().addToVirtualGroup(arrowVirtualGroup, Geometry.buildArrow(x2, y2, x1, y1));
		
		// Making the text group
		textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, textVirtualGroup);
		if (relation.getName() != null) {
			Log.trace("Creating name");
			GfxObject nameGfxObject = RelationShipArtifactPart.setGfxObjectTextForPart(GfxManager.getPlatform().buildText(relation.getName()), RelationShipArtifactPart.NAME);
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, nameGfxObject);
			GfxManager.getPlatform().setFillColor(nameGfxObject, ThemeManager.getForegroundColor());
			GfxManager.getPlatform().translate(nameGfxObject, 
					(x1 + x2 -  (GfxManager.getPlatform().getWidthFor(nameGfxObject))) / 2, 
					(y1 + y2) / 2);
		}

		current_delta = 0;
		if (relation.getLeftCardinality() != null)
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, RelationShipArtifactPart.setGfxObjectTextForPart(createText(relation.getLeftCardinality(), true), RelationShipArtifactPart.LEFT_CARDINALITY));

		if (relation.getLeftConstraint() != null) 
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, RelationShipArtifactPart.setGfxObjectTextForPart(createText(relation.getLeftConstraint(), true), RelationShipArtifactPart.LEFT_CONSTRAINT));
		if (relation.getLeftRole() != null) 
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, RelationShipArtifactPart.setGfxObjectTextForPart(createText(relation.getLeftRole(), true), RelationShipArtifactPart.LEFT_ROLE));

		current_delta = 0;
		if (relation.getRightCardinality() != null) 
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, RelationShipArtifactPart.setGfxObjectTextForPart(createText(relation.getRightCardinality(), false), RelationShipArtifactPart.RIGHT_CARDINALITY));
		if (relation.getRightConstraint() != null) 
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, RelationShipArtifactPart.setGfxObjectTextForPart(createText(relation.getRightConstraint(), false), RelationShipArtifactPart.RIGHT_CONSTRAINT));
		if (relation.getRightRole() != null) 
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, RelationShipArtifactPart.setGfxObjectTextForPart(createText(relation.getRightRole(), false), RelationShipArtifactPart.RIGHT_ROLE));
		
		
		
		if (relationClass != null) {
			int xLineCenter = (x1 + x2) / 2;
			int yLineCenter = (y1 + y2) / 2;
			Point relationClasslinePoint  = Geometry.getPointForLine(relationClass, new Point(xLineCenter, xLineCenter));
			GfxObject relationLine = GfxManager.getPlatform().buildLine(relationClasslinePoint.getX(), relationClasslinePoint.getY(),
					xLineCenter, yLineCenter);
			GfxManager.getPlatform().setStrokeStyle(relationLine, GfxStyle.DASH);
			GfxManager.getPlatform().addToVirtualGroup(gfxObject, relationLine);
		}

	}

	private GfxObject createText(String text, boolean isLeft) {
		
		GfxObject textGfxObject = GfxManager.getPlatform().buildText(text);
		GfxManager.getPlatform().setFillColor(textGfxObject, ThemeManager.getForegroundColor());
		Log.info("Creating text : " + text + " at " +  getTextX(textGfxObject, isLeft) + " : " + getTextY(textGfxObject, isLeft));
		GfxManager.getPlatform().translate(textGfxObject, getTextX(textGfxObject, isLeft), getTextY(textGfxObject, isLeft));
		return textGfxObject;
	}
	
	public void setRightArrow(boolean arrowOnRight) {
		this.arrowOnRight = arrowOnRight;
	}
	
	public void setName(String name) {
		relation.setName(name);
	}
	public void setLeftCardinality(String leftCardinality) {
		relation.setLeftCardinality(leftCardinality);
	}
	public void setRightCardinality(String rightCardinality) {
		relation.setRightCardinality(rightCardinality);
	}
	public void setRightRole(String rightRole) {
		relation.setRightRole(rightRole);
	}
	public void setLeftRole(String leftRole) {
		relation.setLeftRole(leftRole);
	}
	public void setLeftConstraint(String leftConstraint) {
		relation.setLeftConstraint(leftConstraint);
	}
	public void setRightConstraint(String rightConstraint) {
		relation.setRightConstraint(rightConstraint);
	}

	public void setRelationClass(ClassArtifact relationClass) {
		this.relationClass = relationClass;
		if (relationClass != null)
			relationClass.addDependency(this);
	}
}
