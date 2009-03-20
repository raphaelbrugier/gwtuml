package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.LinkedHashMap;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Geometry;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

public class RelationshipArtifact extends LineArtifact {

	static final int TEXT_MARGIN = 4;

	public enum RelationShipArtifactPart {
		LEFT_CARDINALITY(true), LEFT_CONSTRAINT(true), LEFT_ROLE(
				true), NAME(false), RIGHT_CARDINALITY(false), RIGHT_CONSTRAINT(
						false), RIGHT_ROLE(false);

		private final boolean isLeft;

		private RelationShipArtifactPart(boolean isLeft) {
			this.isLeft = isLeft;
		}

		public boolean isLeft() {
			return this.isLeft;
		}
	}

	protected enum Anchor {
		TOP, BOTTOM, LEFT, RIGHT, UNKNOWN;
	}

	boolean arrowOnLeft = false;
	boolean arrowOnRight = false;

	private Relation relation;

	GfxObject line = null;
	GfxObject relationLink = null;

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
		left.addRelationship(this);
		this.rightClassArtifact = right;
		right.addRelationship(this);
		relation = new Relation();

		//this.editor = new RelationshipEditor(this);
	}

	public void edit(GfxObject gfxObject, int x, int y) {
		/*RelationShipArtifactPart subPart = getSubPart(gfxObject);
		editor.editPart(subPart);*/
	}
	/*
	public int getHeight(RelationShipArtifactPart relationShipArtifactPart) {
		GfxObject text = getTextForPart(relationShipArtifactPart);
		if (text != null)
			return (int) GfxManager.getPlatform().getHeightFor(text);
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
			return (int) GfxManager.getPlatform().getWidthFor(text);
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
	/*
	public void setLeftCardinality(String leftCardinality) {
		this.leftCardinality = leftCardinality;
		if (leftCardinalityText != null) {
			GfxManager.getPlatform().removeFromVirtualGroup(getGfxObject(),
					leftCardinalityText, false);
			leftCardinalityText = null;

			if (leftCardinality != "") {
				leftCardinalityText = createLeftCardinalityText();
				GfxManager.getPlatform().addToVirtualGroup(getGfxObject(),
						leftCardinalityText);
			}
		}
	}
	public void setLeftConstraint(String leftConstraint) {
		this.leftConstraint = leftConstraint;
		if (leftConstraintText != null) {
			GfxManager.getPlatform().removeFromVirtualGroup(getGfxObject(),
					leftConstraintText, false);
			leftConstraintText = null;

			if (leftConstraint != "") {
				leftConstraintText = createLeftConstraintText();
				GfxManager.getPlatform().addToVirtualGroup(getGfxObject(),
						leftConstraintText);
			}
		}
	}
	public void setLeftRole(String leftRole) {
		this.leftRole = leftRole;
		if (leftRoleText != null) {
			GfxManager.getPlatform().removeFromVirtualGroup(getGfxObject(),
					leftRoleText, false);
			leftRoleText = null;

			if (leftRole != "") {
				leftRoleText = createLeftRoleText();
				GfxManager.getPlatform().addToVirtualGroup(getGfxObject(),
						leftRoleText);
			}
		}
	}
	public void setName(String name) {
		this.name = name;
		if (nameText != null) {
			GfxManager.getPlatform().removeFromVirtualGroup(getGfxObject(),
					nameText, false);
			nameText = null;

			if (name != "") {
				nameText = createNameText();
				GfxManager.getPlatform().addToVirtualGroup(getGfxObject(),
						nameText);
			}
		}
	}


	public void setRightCardinality(String rightCardinality) {
		this.rightCardinality = rightCardinality;
		if (rightCardinalityText != null) {
			GfxManager.getPlatform().removeFromVirtualGroup(getGfxObject(),
					rightCardinalityText, false);
			rightCardinalityText = null;

			if (rightCardinality != "") {
				rightCardinalityText = createRightCardinalityText();
				GfxManager.getPlatform().addToVirtualGroup(getGfxObject(),
						rightCardinalityText);
			}
		}
	}
	public void setRightConstraint(String rightConstraint) {
		this.rightConstraint = rightConstraint;
		if (rightConstraintText != null) {
			GfxManager.getPlatform().removeFromVirtualGroup(getGfxObject(),
					rightConstraintText, false);
			rightConstraintText = null;

			if (rightConstraint != "") {
				rightConstraintText = createRightConstraintText();
				GfxManager.getPlatform().addToVirtualGroup(getGfxObject(),
						rightConstraintText);
			}
		}
	}
	public void setRightRole(String rightRole) {
		this.rightRole = rightRole;
		if (rightRoleText != null) {
			GfxManager.getPlatform().removeFromVirtualGroup(getGfxObject(),
					rightRoleText, false);
			rightRoleText = null;

			if (rightRole != "") {
				rightRoleText = createRightRoleText();
				GfxManager.getPlatform().addToVirtualGroup(getGfxObject(),
						rightRoleText);
			}
		}
	}*/
	public void unselect() {
		GfxManager.getPlatform().setStroke(line, ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(arrowVirtualGroup, ThemeManager.getForegroundColor(), 1);
	}

	GfxObject buildRelationLink(GfxObject vg, int x1, int y1, int x2, int y2, ClassArtifact clazz) {
		float[] lineBounds = Geometry.computeLineBounds(clazz, (x1 + x2) / 2, (y1 + y2) / 2);
		GfxObject relLine = GfxManager.getPlatform().buildLine((int) lineBounds[0], (int) lineBounds[1], (int) lineBounds[2], (int) lineBounds[3]);
		GfxManager.getPlatform().setStrokeStyle(relLine, GfxStyle.DASH);
		GfxManager.getPlatform().addToVirtualGroup(vg, relLine);
		return relLine;
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
		int textWidth = (int) GfxManager.getPlatform().getWidthFor(text);
		if(isLeft) {
			relative_x1 = x2;
			relative_x2 = x1;
			relative_y1 = y2;
		}
		switch (getAnchorType(isLeft ? leftClassArtifact : rightClassArtifact, relative_x1, relative_y1)) {
		case LEFT: return relative_x1 - textWidth - TEXT_MARGIN;
		case RIGHT:	return relative_x1 + TEXT_MARGIN;
		case TOP:
			if (relative_x1 < relative_x2)
				return relative_x1 - textWidth - TEXT_MARGIN;
			else
				return relative_x1 + TEXT_MARGIN;
		case BOTTOM: case UNKNOWN: 
			if (relative_x1 < relative_x2)
				return relative_x1 - textWidth - TEXT_MARGIN;
			else
				return relative_x1 + TEXT_MARGIN;
		}
		return 0;
	}

	int getTextY(boolean isLeft, int delta) {
		int relative_x1 = x1;		
		int relative_y1 = y1;
		int relative_y2 = y2;
		if(isLeft) {
			relative_x1 = x2;
			relative_y1 = y2;
			relative_y2 = y1;
		}
		switch (getAnchorType(isLeft ? leftClassArtifact : rightClassArtifact, relative_x1, relative_y1)) {
		case LEFT:
			if (relative_y1 > relative_y2) return relative_y1 + TEXT_MARGIN + delta;
			else return relative_y1 - TEXT_MARGIN - delta;
		case RIGHT:
			if (relative_y1 > relative_y2) return relative_y1 + TEXT_MARGIN + delta;
			else return relative_y1 - TEXT_MARGIN - delta;
		case TOP: return relative_y1 - TEXT_MARGIN - delta;
		case BOTTOM: case UNKNOWN: return relative_y1 + TEXT_MARGIN + delta;
		}
		return 0;
	}

	@Override
	protected void buildGfxObject() {

		float[] lineBounds = Geometry.computeLineBounds(leftClassArtifact, rightClassArtifact);
		setBounds(Math.round(lineBounds[0]), Math.round(lineBounds[1]), Math.round(lineBounds[2]),	Math.round(lineBounds[3]));
		line = GfxManager.getPlatform().buildLine(x1, y1, x2, y2);
		//line = Geometry.getLineBetween(leftClassArtifact, rightClassArtifact);
		GfxManager.getPlatform().setStroke(line, ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, line);

		// Making arrows group :
		arrowVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		if (arrowOnLeft)
			GfxManager.getPlatform().addToVirtualGroup(arrowVirtualGroup, Geometry.buildArrow(x1, y1, x2, y2));
		if (arrowOnRight) 
			GfxManager.getPlatform().addToVirtualGroup(arrowVirtualGroup, Geometry.buildArrow(x2, y2, x1, y1));
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, arrowVirtualGroup);
		// Making the text group
		textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		if (relation.getName() != null) {
			Log.trace("Creating name");
			GfxObject nameGfxObject = GfxManager.getPlatform().buildText(relation.getName());
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, nameGfxObject);
			GfxManager.getPlatform().setFillColor(nameGfxObject, ThemeManager.getForegroundColor());
			GfxManager.getPlatform().translate(nameGfxObject, 
					(x1 + x2 - (int) (GfxManager.getPlatform().getWidthFor(nameGfxObject))) / 2, 
					(y1 + y2) / 2);
		}

		int delta = 0;		
		if (relation.getLeftCardinality() != null)
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getLeftCardinality(), true, delta));
		if (relation.getLeftConstraint() != null) 
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getLeftConstraint(), true, delta));
		if (relation.getLeftRole() != null) 
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getLeftRole(), true, delta));

		delta = 0;
		if (relation.getRightCardinality() != null) 
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getRightCardinality(), false, delta));
		if (relation.getRightConstraint() != null) 
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getRightConstraint(), false, delta));
		if (relation.getRightRole() != null) 
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getRightRole(), false, delta));
		
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, textVirtualGroup);
		
		if (relationClass != null)
			relationLink = buildRelationLink(gfxObject, x1, y1, x2, y2, relationClass);

	}

	private GfxObject createText(String text, boolean isLeft, int delta) {
		
		GfxObject textGfxObject = GfxManager.getPlatform().buildText(text);
		GfxManager.getPlatform().setFillColor(textGfxObject, ThemeManager.getForegroundColor());
		Log.info("Creating text : " + text + " at " +  getTextX(textGfxObject, isLeft) + " : " + getTextY(isLeft, delta));
		GfxManager.getPlatform().translate(textGfxObject, getTextX(textGfxObject, isLeft), getTextY(isLeft, delta));
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
		if (relationClass != null)
			relationClass.removeRelationshipDependency(this);
		this.relationClass = relationClass;
		if (relationClass != null)
			relationClass.addRelationshipDependency(this);
	}

	/*private int getCurrentDeltaFor(
			RelationShipArtifactPart relationShipArtifactPart) {

		int right_delta = 0;
		int left_delta = 0;

		if (relationShipArtifactPart == RelationShipArtifactPart.LEFT_CARDINALITY)
			return left_delta;

		if (relationShipArtifactPart == RelationShipArtifactPart.RIGHT_CARDINALITY)
			return right_delta;

		if (leftCardinalityText != null)
			left_delta += (int) GfxManager.getPlatform().getHeightFor(
					leftCardinalityText)
					+ TEXT_MARGIN;

		if (rightCardinalityText != null)
			right_delta += (int) GfxManager.getPlatform().getHeightFor(
					rightCardinalityText)
					+ TEXT_MARGIN;

		if (relationShipArtifactPart == RelationShipArtifactPart.LEFT_CONSTRAINT)
			return left_delta;

		if (relationShipArtifactPart == RelationShipArtifactPart.RIGHT_CONSTRAINT)
			return right_delta;

		if (leftConstraintText != null)
			left_delta += (int) GfxManager.getPlatform().getHeightFor(
					leftConstraintText)
					+ TEXT_MARGIN;

		if (rightConstraintText != null)
			right_delta += (int) GfxManager.getPlatform().getHeightFor(
					rightConstraintText)
					+ TEXT_MARGIN;

		if (relationShipArtifactPart == RelationShipArtifactPart.LEFT_ROLE)
			return left_delta;

		if (relationShipArtifactPart == RelationShipArtifactPart.RIGHT_ROLE)
			return right_delta;

		return 0;
	}*/
}
