package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.editors.RelationshipEditor;
import com.objetdirect.gwt.umlapi.client.engine.Geometry;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;


public class RelationshipArtifact extends LineArtifact {


	private RelationshipEditor editor;

	public RelationshipArtifact(ClassArtifact left, ClassArtifact right) {
		this.leftClass = left;
		left.addRelationship(this);
		this.rightClass = right;
		right.addRelationship(this);
		this.editor = new RelationshipEditor(this);
	}

	public ClassArtifact getLeftArtifact() {
		return leftClass;
	}

	public ClassArtifact getRightArtifact() {
		return rightClass;
	}

	public void setName(String name) {
		this.name = name;
		if (nameText != null) {
			GfxManager.getInstance().removeFromVirtualGroup(getGfxObject(), nameText, false);
			getCanvas().unregister(nameText);
			nameText = null;

			if(name != "") {
				nameText = createNameText();
				getCanvas().register(nameText, this);
				GfxManager.getInstance().addToVirtualGroup(getGfxObject(), nameText);
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setRelationClass(ClassArtifact relationClass) {
		if (relationClass!=null)
			relationClass.removeRelationshipDependency(this);
		this.relationClass = relationClass;
		if (relationClass!=null)
			relationClass.addRelationshipDependency(this);
	}

	public void setLeftArrow(boolean arrowOnLeft) {
		this.arrowOnLeft = arrowOnLeft;
	}

	public void setRightArrow(boolean arrowOnRight) {
		this.arrowOnRight = arrowOnRight;
	}

	public void setLeftCardinality(String leftCardinality) {
		this.leftCardinality = leftCardinality;
		if (leftCardinalityText != null) {
			GfxManager.getInstance().removeFromVirtualGroup(getGfxObject(), leftCardinalityText, false);
			getCanvas().unregister(leftCardinalityText);
			leftCardinalityText = null;

			if(leftCardinality != "") {
				leftCardinalityText = createLeftCardinalityText();
				getCanvas().register(leftCardinalityText, this);
				GfxManager.getInstance().addToVirtualGroup(getGfxObject(), leftCardinalityText);
			}}
	}

	public void setRightCardinality(String rightCardinality) {
		this.rightCardinality = rightCardinality;
		if (rightCardinalityText != null) {
			GfxManager.getInstance().removeFromVirtualGroup(getGfxObject(), rightCardinalityText, false);
			getCanvas().unregister(leftCardinalityText);
			rightCardinalityText = null;

			if(rightCardinality != "") {
				rightCardinalityText = createRightCardinalityText();
				getCanvas().register(rightCardinalityText, this);
				GfxManager.getInstance().addToVirtualGroup(getGfxObject(), rightCardinalityText);
			}}
	}

	public void setLeftRole(String leftRole) {
		this.leftRole = leftRole;
		if (leftRoleText != null) {
			GfxManager.getInstance().removeFromVirtualGroup(getGfxObject(), leftRoleText, false);
			getCanvas().unregister(leftRoleText);
			leftRoleText = null;

			if(leftRole != "") {
				leftRoleText = createLeftRoleText();
				getCanvas().register(leftRoleText, this);
				GfxManager.getInstance().addToVirtualGroup(getGfxObject(), leftRoleText);
			}}
	}

	public void setRightRole(String rightRole) {
		this.rightRole = rightRole;
		if (rightRoleText != null) {
			GfxManager.getInstance().removeFromVirtualGroup(getGfxObject(), rightRoleText, false);
			getCanvas().unregister(rightRoleText);
			rightRoleText = null;

			if(rightRole != "") {
				rightRoleText = createRightRoleText();
				getCanvas().register(rightRoleText, this);
				GfxManager.getInstance().addToVirtualGroup(getGfxObject(), rightRoleText);
			}}
	}

	public void setLeftConstraint(String leftConstraint) {
		this.leftConstraint = leftConstraint;
		if (leftConstraintText != null) {
			GfxManager.getInstance().removeFromVirtualGroup(getGfxObject(), leftConstraintText, false);
			getCanvas().unregister(leftConstraintText);
			leftConstraintText = null;

			if(leftConstraint != "") {
				leftConstraintText = createLeftConstraintText();
				getCanvas().register(leftConstraintText, this);
				GfxManager.getInstance().addToVirtualGroup(getGfxObject(), leftConstraintText);
			}}
	}

	public void setRightConstraint(String rightConstraint) {
		this.rightConstraint = rightConstraint;
		if (rightConstraintText != null) {
			GfxManager.getInstance().removeFromVirtualGroup(getGfxObject(), rightConstraintText, false);
			getCanvas().unregister(rightConstraintText);
			rightConstraintText = null;

			if(rightConstraint != "") {
				rightConstraintText = createRightConstraintText();
				getCanvas().register(rightConstraintText, this);
				GfxManager.getInstance().addToVirtualGroup(getGfxObject(), rightConstraintText);
			}}
	}

	public String getLeftCardinality() {
		return leftCardinality;
	}

	public String getRightCardinality() {
		return rightCardinality;
	}

	public String getLeftRole() {
		return leftRole;
	}

	public String getRightRole() {
		return rightRole;
	}

	public String getLeftConstraint() {
		return leftConstraint;
	}

	public String getRightConstraint() {
		return rightConstraint;
	}
	// implementation

	static final int TEXT_MARGIN = 4;

	protected static final int ANCHOR_NONE = -1;
	protected static final int ANCHOR_TOP = 0;
	protected static final int ANCHOR_BOTTOM = 1;
	protected static final int ANCHOR_LEFT = 2;
	protected static final int ANCHOR_RIGHT = 3;

	protected GfxObject buildGfxObject() {
		GfxPlatform gPlatform = GfxManager.getInstance();
		GfxObject vg = gPlatform.buildVirtualGroup();

		float[] lineBounds = Geometry.computeLineBounds(leftClass, rightClass);
		setBounds((int)Math.round(lineBounds[0]),(int)Math.round(lineBounds[1]),(int)Math.round(lineBounds[2]),(int)Math.round(lineBounds[3]));
		line = gPlatform.buildLine(getX1(), getY1(), getX2(), getY2());
		gPlatform.setStroke(line, ThemeManager.getForegroundColor(), 1);
		gPlatform.addToVirtualGroup(vg, line);

		if (relationClass!=null)
			relationLink = buildRelationLink(vg, getX1(), getY1(), getX2(), getY2(), relationClass);

		if (name!=null) {
			nameText =  createNameText();
			gPlatform.addToVirtualGroup(vg, nameText);
		}

		if (arrowOnLeft) {
			leftArrow = Geometry.buildArrow(getX1(), getY1(), getX2(), getY2());
			gPlatform.addToVirtualGroup(vg, leftArrow);
		}
		if (arrowOnRight) {
			rightArrow = Geometry.buildArrow(getX2(), getY2(), getX1(), getY1());
			gPlatform.addToVirtualGroup(vg, rightArrow);
		}

		if (leftCardinality!=null) {
			leftCardinalityText = createLeftCardinalityText();	
			gPlatform.addToVirtualGroup(vg, leftCardinalityText);
		}

		if (rightCardinality!=null) {
			rightCardinalityText = createRightCardinalityText();
			gPlatform.addToVirtualGroup(vg, rightCardinalityText);
		}

		if (leftConstraint!=null) {
			leftConstraintText = createLeftConstraintText();
			gPlatform.addToVirtualGroup(vg, leftConstraintText);
		}

		if (rightConstraint!=null) {
			rightConstraintText = createRightConstraintText();
			gPlatform.addToVirtualGroup(vg, rightConstraintText);
		}

		if (leftRole!=null) {
			leftRoleText = createLeftRoleText();
			gPlatform.addToVirtualGroup(vg, leftRoleText);
		}

		if (rightRole!=null) {			
			rightRoleText = createRightRoleText();
			gPlatform.addToVirtualGroup(vg, rightRoleText);
		}

		return vg;
	}

	private GfxObject createLeftCardinalityText() {		
		GfxObject leftCardinalityText =  GfxManager.getInstance().buildText(leftCardinality);
		GfxManager.getInstance().setFillColor(leftCardinalityText, ThemeManager.getForegroundColor());
		GfxManager.getInstance().translate(leftCardinalityText, getTextX(RelationShipArtifactPart.LEFT_CARDINALITY, leftCardinalityText), getTextY(RelationShipArtifactPart.LEFT_CARDINALITY, leftCardinalityText));
		return leftCardinalityText;
	}	
	private GfxObject createRightCardinalityText() {
		GfxObject rightCardinalityText =  GfxManager.getInstance().buildText(rightCardinality);
		GfxManager.getInstance().setFillColor(rightCardinalityText, ThemeManager.getForegroundColor());
		GfxManager.getInstance().translate(rightCardinalityText, getTextX(RelationShipArtifactPart.RIGHT_CARDINALITY, rightCardinalityText), getTextY(RelationShipArtifactPart.RIGHT_CARDINALITY, rightCardinalityText));
		return rightCardinalityText;
	}

	private GfxObject createLeftConstraintText() {		
		GfxObject leftConstraintText =  GfxManager.getInstance().buildText(leftConstraint);
		GfxManager.getInstance().setFillColor(leftConstraintText, ThemeManager.getForegroundColor());
		GfxManager.getInstance().translate(leftConstraintText, getTextX(RelationShipArtifactPart.LEFT_CONSTRAINT, leftConstraintText), getTextY(RelationShipArtifactPart.LEFT_CONSTRAINT, leftConstraintText));
		return leftConstraintText;
	}	
	private GfxObject createRightConstraintText() {
		GfxObject rightConstraintText =  GfxManager.getInstance().buildText(rightConstraint);
		GfxManager.getInstance().setFillColor(rightConstraintText, ThemeManager.getForegroundColor());
		GfxManager.getInstance().translate(rightConstraintText, getTextX(RelationShipArtifactPart.RIGHT_CONSTRAINT, rightConstraintText), getTextY(RelationShipArtifactPart.RIGHT_CONSTRAINT, rightConstraintText));
		return rightConstraintText;
	}
	private GfxObject createLeftRoleText() {		
		GfxObject leftRoleText =  GfxManager.getInstance().buildText(leftRole);
		GfxManager.getInstance().setFillColor(leftRoleText, ThemeManager.getForegroundColor());
		GfxManager.getInstance().translate(leftRoleText, getTextX(RelationShipArtifactPart.LEFT_ROLE, leftRoleText), getTextY(RelationShipArtifactPart.LEFT_ROLE, leftRoleText));
		return leftRoleText;
	}	
	private GfxObject createRightRoleText() {
		GfxObject rightRoleText =  GfxManager.getInstance().buildText(rightRole);
		GfxManager.getInstance().setFillColor(rightRoleText, ThemeManager.getForegroundColor());
		GfxManager.getInstance().translate(rightRoleText, getTextX(RelationShipArtifactPart.RIGHT_ROLE, rightRoleText), getTextY(RelationShipArtifactPart.RIGHT_ROLE, rightRoleText));
		return rightRoleText;
	}

	private GfxObject createNameText() {
		GfxObject nameText =  GfxManager.getInstance().buildText(name);
		GfxManager.getInstance().setFillColor(nameText, ThemeManager.getForegroundColor());
		GfxManager.getInstance().translate(nameText, getTextX(RelationShipArtifactPart.NAME, nameText), getTextY(RelationShipArtifactPart.NAME, nameText));
		return nameText;
	}



	GfxObject buildRelationLink(GfxObject vg, int x1, int y1, int x2, int y2, ClassArtifact clazz) {
		float[] lineBounds = Geometry.computeLineBounds(clazz, (x1+x2)/2, (y1+y2)/2);
		GfxObject relLine = GfxManager.getInstance().buildLine((int)lineBounds[0], (int)lineBounds[1], (int)lineBounds[2], (int)lineBounds[3]);
		GfxManager.getInstance().setStrokeStyle(relLine, GfxStyle.DASH);
		GfxManager.getInstance().addToVirtualGroup(vg, relLine);
		return relLine;
	}

	public int getTextX(RelationShipArtifactPart relationShipArtifactPart) {
		return getTextX(relationShipArtifactPart, getTextForPart(relationShipArtifactPart));
	}

	public int getTextY(RelationShipArtifactPart relationShipArtifactPart) {
		return getTextY(relationShipArtifactPart, getTextForPart(relationShipArtifactPart));
	}


	int getTextX(RelationShipArtifactPart relationShipArtifactPart, GfxObject text) 
	{
		GfxPlatform gPlatform = GfxManager.getInstance();
		int x1 = relationShipArtifactPart.isLeft() ? getX1() : getX2();
		int y1 = relationShipArtifactPart.isLeft() ? getY1() : getY2();
		int x2 = relationShipArtifactPart.isLeft() ? getX2() : getX1();
		//int y2 = relationShipArtifactPart.isLeft() ? getY2() : getY1();
		
		if(text == null) return 0;
		if(relationShipArtifactPart == RelationShipArtifactPart.NAME) {
			return (x1+x2)/2 - (int)( GfxManager.getInstance().getWidthFor(text))/2;
		}
		else switch (getAnchorType(relationShipArtifactPart.isLeft() ? leftClass : rightClass, x1, y1)) {
		case ANCHOR_LEFT:
			return x1-(int)gPlatform.getWidthFor(text)-TEXT_MARGIN;

		case ANCHOR_RIGHT:
			return x1+TEXT_MARGIN;

		case ANCHOR_TOP:
			if (x1<x2) 
				return x1-(int)gPlatform.getWidthFor(text)-TEXT_MARGIN;
			else 
				return x1+TEXT_MARGIN;

		case ANCHOR_NONE:
		case ANCHOR_BOTTOM:
			if (x1<x2)
				return x1-(int)gPlatform.getWidthFor(text)-TEXT_MARGIN;
			else
				return x1+TEXT_MARGIN;
		}
		return 0;
	}
	int getTextY(RelationShipArtifactPart relationShipArtifactPart, GfxObject text) 
	{
		int x1 = relationShipArtifactPart.isLeft() ? getX1() : getX2();
		int y1 = relationShipArtifactPart.isLeft() ? getY1() : getY2();
		//int x2 = relationShipArtifactPart.isLeft() ? getX2() : getX1();
		int y2 = relationShipArtifactPart.isLeft() ? getY2() : getY1();
		GfxPlatform gPlatform = GfxManager.getInstance();
		if(text == null) return 0;
		if(relationShipArtifactPart == RelationShipArtifactPart.NAME) {		
			return (y1+y2)/2;
		}
		else {
			int delta = getCurrentDeltaFor(relationShipArtifactPart);
			switch (getAnchorType(relationShipArtifactPart.isLeft() ? leftClass : rightClass, x1, y1)) {

			case ANCHOR_LEFT:
				if (y1>y2)
					return y1 + (int)gPlatform.getHeightFor(text) + TEXT_MARGIN + delta;
				else
					return y1-TEXT_MARGIN - delta;

			case ANCHOR_RIGHT:

				if (y1>y2)
					return y1 + (int)gPlatform.getHeightFor(text) + TEXT_MARGIN + delta;
				else
					return y1 - TEXT_MARGIN - delta;

			case ANCHOR_TOP:
				return y1 - TEXT_MARGIN - delta;

			case ANCHOR_NONE:
			case ANCHOR_BOTTOM:
				return y1+(int)gPlatform.getHeightFor(text)+TEXT_MARGIN+delta;

			}
			return 0;
		}}

	private int getCurrentDeltaFor(RelationShipArtifactPart relationShipArtifactPart) {
		GfxPlatform gPlatform = GfxManager.getInstance();
		int right_delta = 0;
		int left_delta = 0;

		if (relationShipArtifactPart == RelationShipArtifactPart.LEFT_CARDINALITY)
			return left_delta;

		if (relationShipArtifactPart == RelationShipArtifactPart.RIGHT_CARDINALITY)
			return right_delta;

		if(leftCardinalityText != null)
			left_delta += (int)gPlatform.getHeightFor(leftCardinalityText) + TEXT_MARGIN;

		if(rightCardinalityText != null)
			right_delta += (int)gPlatform.getHeightFor(rightCardinalityText) + TEXT_MARGIN;

		if (relationShipArtifactPart == RelationShipArtifactPart.LEFT_CONSTRAINT)
			return left_delta;

		if (relationShipArtifactPart == RelationShipArtifactPart.RIGHT_CONSTRAINT)
			return right_delta;

		if(leftConstraintText != null)
			left_delta += (int)gPlatform.getHeightFor(leftConstraintText) + TEXT_MARGIN;

		if(rightConstraintText != null)
			right_delta += (int)gPlatform.getHeightFor(rightConstraintText) + TEXT_MARGIN;

		if (relationShipArtifactPart == RelationShipArtifactPart.LEFT_ROLE)
			return left_delta;

		if (relationShipArtifactPart == RelationShipArtifactPart.RIGHT_ROLE)
			return right_delta;

		return 0;
	}

	int getAnchorType(ClassArtifact clazz, int x1, int y1) {
		if (x1==clazz.getX())
			return ANCHOR_LEFT;
		else if (y1==clazz.getY())
			return ANCHOR_TOP;
		else if (x1==clazz.getX()+clazz.getWidth())
			return ANCHOR_RIGHT;
		else if (y1==clazz.getY()+clazz.getHeight())
			return ANCHOR_BOTTOM;

		return ANCHOR_NONE;
		//throw new UMLDrawerException("Unable to find anchor type");
	}


	public List<GfxObject> getComponents() {
		List<GfxObject> comps = new ArrayList<GfxObject>();
		comps.add(line);
		if (relationLink!=null)
			comps.add(relationLink);
		if (leftArrow!=null)
			comps.add(leftArrow);
		if (rightArrow!=null)
			comps.add(rightArrow);
		if (nameText!=null)
			comps.add(nameText);
		if (leftCardinalityText!=null)
			comps.add(leftCardinalityText);
		if (rightCardinalityText!=null)
			comps.add(rightCardinalityText);
		if (leftConstraintText!=null)
			comps.add(leftConstraintText);
		if (rightConstraintText!=null)
			comps.add(rightConstraintText);
		if (leftRoleText!=null)
			comps.add(leftRoleText);
		if (rightRoleText!=null)
			comps.add(rightRoleText);
		return comps;
	}

	public void select() {
		GfxManager.getInstance().setStroke(line, ThemeManager.getHighlightedForegroundColor(), 2);
		if (leftArrow!=null)
			GfxManager.getInstance().setStroke(leftArrow, ThemeManager.getHighlightedForegroundColor(), 2);
		if (rightArrow!=null)
			GfxManager.getInstance().setStroke(rightArrow, ThemeManager.getHighlightedForegroundColor(), 2);
	}

	public void unselect() {
		GfxManager.getInstance().setStroke(line, ThemeManager.getForegroundColor(), 1);
		if (leftArrow!=null)
			GfxManager.getInstance().setStroke(leftArrow, ThemeManager.getForegroundColor(), 1);
		if (rightArrow!=null)
			GfxManager.getInstance().setStroke(rightArrow, ThemeManager.getForegroundColor(), 1);
	}

	public int getWidth(RelationShipArtifactPart relationShipArtifactPart) {
		GfxObject text = getTextForPart(relationShipArtifactPart); 
		if (text != null) return (int) GfxManager.getInstance().getWidthFor(text);
		return 0;
	}
	public int getHeight(RelationShipArtifactPart relationShipArtifactPart) {
		GfxObject text = getTextForPart(relationShipArtifactPart); 
		if (text != null) return (int) GfxManager.getInstance().getHeightFor(text);
		return 0;
	}

	public RelationShipArtifactPart getSubPart(GfxObject gfxObject) {
		if (gfxObject==nameText)
			return RelationShipArtifactPart.NAME;
		if (gfxObject==leftCardinalityText)
			return RelationShipArtifactPart.LEFT_CARDINALITY;
		if (gfxObject==rightCardinalityText)
			return RelationShipArtifactPart.RIGHT_CARDINALITY;
		if (gfxObject==leftConstraintText)
			return RelationShipArtifactPart.LEFT_CONSTRAINT;
		if (gfxObject==rightConstraintText)
			return RelationShipArtifactPart.RIGHT_CONSTRAINT;        
		if (gfxObject==leftRoleText)
			return RelationShipArtifactPart.LEFT_ROLE;
		if (gfxObject==rightRoleText)
			return RelationShipArtifactPart.RIGHT_ROLE;        
		return null;

	}

	public void edit(GfxObject gfxObject, int x, int y) {
		RelationShipArtifactPart subPart = getSubPart(gfxObject);
		editor.editPart(subPart);
	}

	public GfxObject getTextForPart(RelationShipArtifactPart relationShipArtifactPart) {
		switch(relationShipArtifactPart) {
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
	public String getStringForPart(RelationShipArtifactPart relationShipArtifactPart) {
		switch(relationShipArtifactPart) {
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

	boolean arrowOnLeft = false;
	boolean arrowOnRight = false;
	String name = "name";//null;
	String leftCardinality = "lc"; //null;
	String rightCardinality = "rc"; //null;
	String leftRole = "lr"; //null;
	String rightRole = "rr"; //null;
	String leftConstraint = "lct"; //null;
	String rightConstraint = "rct"; //null;
	ClassArtifact leftClass;
	ClassArtifact rightClass;
	ClassArtifact relationClass = null;
	GfxObject line = null;
	GfxObject relationLink = null;
	GfxObject leftArrow = null;
	GfxObject rightArrow = null;
	GfxObject nameText = null;
	GfxObject leftCardinalityText = null;
	GfxObject rightCardinalityText = null;
	GfxObject leftRoleText = null;
	GfxObject rightRoleText = null;
	GfxObject leftConstraintText = null;
	GfxObject rightConstraintText = null;

	public enum RelationShipArtifactPart {
		NAME(false),
		LEFT_CARDINALITY(true),
		RIGHT_CARDINALITY(false),
		LEFT_ROLE(true),
		RIGHT_ROLE(false),
		LEFT_CONSTRAINT(true),
		RIGHT_CONSTRAINT(false);

		private final boolean isLeft;		

		private RelationShipArtifactPart(boolean isLeft) {
			this.isLeft = isLeft;
		}
		public boolean isLeft() {
			return this.isLeft;
		}
	}

	public LinkedHashMap <String, Command> getRightMenu() {
		
		LinkedHashMap <String, Command> rightMenu = new LinkedHashMap<String, Command>();
		
		Command doNothing = new Command() { 
			public void execute() {
			}

		};	
		rightMenu.put("RelationShip " + leftClass.className + " <-> " + rightClass.className, doNothing);
		rightMenu.put("-", null);
		rightMenu.put("> Edit", doNothing);
		rightMenu.put("> Reverse", doNothing);
		rightMenu.put("> Delete", doNothing);
		
		return rightMenu;
	}
}
