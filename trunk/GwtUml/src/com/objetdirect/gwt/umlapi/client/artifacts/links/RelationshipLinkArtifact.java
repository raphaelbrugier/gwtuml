package com.objetdirect.gwt.umlapi.client.artifacts.links;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.editors.RelationshipLinkFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Geometry;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * @author  florian
 */
public class RelationshipLinkArtifact extends LinkArtifact {

	/**
	 * @author   florian
	 */
	public enum RelationshipArtifactPart {
		/**
		 * @uml.property  name="lEFT_CARDINALITY"
		 * @uml.associationEnd  
		 */
		LEFT_CARDINALITY {
			@Override
			public String getText(Relation relation) { return relation.getLeftCardinality(); }
			@Override
			public void setText(Relation relation, String text) { relation.setLeftCardinality(text); }
		}, /**
		 * @uml.property  name="lEFT_CONSTRAINT"
		 * @uml.associationEnd  
		 */
		LEFT_CONSTRAINT {
			@Override
			public String getText(Relation relation) { return relation.getLeftConstraint(); } 
			@Override
			public void setText(Relation relation, String text) { relation.setLeftConstraint(text); }
		}, /**
		 * @uml.property  name="lEFT_ROLE"
		 * @uml.associationEnd  
		 */
		LEFT_ROLE {
			@Override
			public String getText(Relation relation) { return relation.getLeftRole(); } 
			@Override
			public void setText(Relation relation, String text) { relation.setLeftRole(text); }
		}, /**
		 * @uml.property  name="nAME"
		 * @uml.associationEnd  
		 */
		NAME {
			@Override
			public String getText(Relation relation) { return relation.getName(); } 
			@Override
			public void setText(Relation relation, String text) { relation.setName(text); }
		}, /**
		 * @uml.property  name="rIGHT_CARDINALITY"
		 * @uml.associationEnd  
		 */
		RIGHT_CARDINALITY {
			@Override
			public String getText(Relation relation) { return relation.getRightCardinality(); }
			@Override
			public void setText(Relation relation, String text) { relation.setRightCardinality(text); }
		}, /**
		 * @uml.property  name="rIGHT_CONSTRAINT"
		 * @uml.associationEnd  
		 */
		RIGHT_CONSTRAINT {
			@Override
			public String getText(Relation relation) { return relation.getRightConstraint(); } 
			@Override
			public void setText(Relation relation, String text) { relation.setRightConstraint(text); }
		}, /**
		 * @uml.property  name="rIGHT_ROLE"
		 * @uml.associationEnd  
		 */
		RIGHT_ROLE  {
			@Override
			public String getText(Relation relation) { return relation.getRightRole(); } 
			@Override
			public void setText(Relation relation, String text) { relation.setRightRole(text); }
		};

		private static HashMap<GfxObject, RelationshipArtifactPart> textGfxObject = new HashMap<GfxObject, RelationshipArtifactPart>();

		public abstract String getText(Relation relation);
		public abstract void setText(Relation relation, String text);

		public static void setGfxObjectTextForPart(GfxObject text, RelationshipArtifactPart part) {
			textGfxObject.put(text, part);
		}
		
		public static RelationshipArtifactPart getPartForGfxObject(GfxObject text) {
			return textGfxObject.get(text);
		}
	}

	/**
	 * @author   florian
	 */
	protected enum Anchor {
		/**
		 * @uml.property  name="tOP"
		 * @uml.associationEnd  
		 */
		TOP, /**
		 * @uml.property  name="bOTTOM"
		 * @uml.associationEnd  
		 */
		BOTTOM, /**
		 * @uml.property  name="lEFT"
		 * @uml.associationEnd  
		 */
		LEFT, /**
		 * @uml.property  name="rIGHT"
		 * @uml.associationEnd  
		 */
		RIGHT, /**
		 * @uml.property  name="uNKNOWN"
		 * @uml.associationEnd  
		 */
		UNKNOWN;
	}

	boolean arrowOnLeft = false;
	boolean arrowOnRight = false;
	private int current_delta;
	/**
	 * @uml.property  name="relation"
	 * @uml.associationEnd  
	 */
	private Relation relation;

	/**
	 * @uml.property  name="line"
	 * @uml.associationEnd  
	 */
	GfxObject line;

	/**
	 * @uml.property  name="arrowVirtualGroup"
	 * @uml.associationEnd  
	 */
	GfxObject arrowVirtualGroup;

	/**
	 * @uml.property  name="textVirtualGroup"
	 * @uml.associationEnd  
	 */
	GfxObject textVirtualGroup;

	/**
	 * @uml.property  name="leftClassArtifact"
	 * @uml.associationEnd  
	 */
	ClassArtifact leftClassArtifact;
	/**
	 * @uml.property  name="relationClass"
	 * @uml.associationEnd  
	 */
	ClassArtifact relationClass;
	/**
	 * @uml.property  name="rightClassArtifact"
	 * @uml.associationEnd  
	 */
	ClassArtifact rightClassArtifact;

	private HashMap<RelationshipArtifactPart, GfxObject> gfxObjectPart = new HashMap<RelationshipArtifactPart, GfxObject>();

	
	public RelationshipLinkArtifact(ClassArtifact left, ClassArtifact right) {
		this.leftClassArtifact = left;
		left.addDependency(this);
		this.rightClassArtifact = right;
		right.addDependency(this);
		relation = new Relation();
	}

	public void edit(RelationshipArtifactPart part) {
		part.setText(relation, "link");
		rebuildGfxObject();
		edit(gfxObjectPart.get(part), 0, 0);		
	}

	public void edit(GfxObject gfxObject, int x, int y) {
		RelationshipArtifactPart editPart = RelationshipArtifactPart.getPartForGfxObject(gfxObject);
		if(editPart ==  null) {
			edit(RelationshipArtifactPart.NAME);
		} else {
			RelationshipLinkFieldEditor editor = new RelationshipLinkFieldEditor(canvas, this, editPart);
			editor.startEdition(editPart.getText(relation), 
					GfxManager.getPlatform().getXFor(gfxObject),
					GfxManager.getPlatform().getYFor(gfxObject) - GfxManager.getPlatform().getHeightFor(gfxObject),
					GfxManager.getPlatform().getWidthFor(gfxObject) + OptionsManager.getRectangleXTotalPadding());
		}
	}

	public void setPartContent(RelationshipArtifactPart part, String newContent) {
		part.setText(relation, newContent);
	}
	/**
	 * @return
	 * @uml.property  name="leftClassArtifact"
	 */
	public ClassArtifact getLeftClassArtifact() {
		return leftClassArtifact;
	}

	/**
	 * @return
	 * @uml.property  name="rightClassArtifact"
	 */
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
		gfxObjectPart.clear();
		
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
		if (relation.getName() != "") {
			Log.trace("Creating name");
			GfxObject nameGfxObject = GfxManager.getPlatform().buildText(relation.getName());
			
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, nameGfxObject);
			GfxManager.getPlatform().setFillColor(nameGfxObject, ThemeManager.getForegroundColor());
			GfxManager.getPlatform().translate(nameGfxObject, 
					(x1 + x2 -  (GfxManager.getPlatform().getWidthFor(nameGfxObject))) / 2, 
					(y1 + y2) / 2);
			RelationshipArtifactPart.setGfxObjectTextForPart(nameGfxObject, RelationshipArtifactPart.NAME);
			gfxObjectPart.put(RelationshipArtifactPart.NAME, nameGfxObject);
		}

		current_delta = 0;
		if (relation.getLeftCardinality() != "")
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getLeftCardinality(), true, RelationshipArtifactPart.LEFT_CARDINALITY));
		if (relation.getLeftConstraint() != "") 
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getLeftConstraint(), true, RelationshipArtifactPart.LEFT_CONSTRAINT));
		if (relation.getLeftRole() != "") 
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getLeftRole(), true, RelationshipArtifactPart.LEFT_ROLE));

		current_delta = 0;
		if (relation.getRightCardinality() != "") 
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getRightCardinality(), false, RelationshipArtifactPart.RIGHT_CARDINALITY));
		if (relation.getRightConstraint() != "") 
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getRightConstraint(), false, RelationshipArtifactPart.RIGHT_CONSTRAINT));
		if (relation.getRightRole() != "") 
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getRightRole(), false, RelationshipArtifactPart.RIGHT_ROLE));

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

	private GfxObject createText(String text, boolean isLeft, RelationshipArtifactPart part) {
		GfxObject textGfxObject = GfxManager.getPlatform().buildText(text);
		GfxManager.getPlatform().setFillColor(textGfxObject, ThemeManager.getForegroundColor());
		Log.info("Creating text : " + text + " at " +  getTextX(textGfxObject, isLeft) + " : " + getTextY(textGfxObject, isLeft));
		GfxManager.getPlatform().translate(textGfxObject, getTextX(textGfxObject, isLeft), getTextY(textGfxObject, isLeft));
		RelationshipArtifactPart.setGfxObjectTextForPart(textGfxObject, part);
		gfxObjectPart.put(part, textGfxObject);
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

	/**
	 * @param relationClass
	 * @uml.property  name="relationClass"
	 */
	public void setRelationClass(ClassArtifact relationClass) {
		this.relationClass = relationClass;
		if (relationClass != null)
			relationClass.addDependency(this);
	}
}
