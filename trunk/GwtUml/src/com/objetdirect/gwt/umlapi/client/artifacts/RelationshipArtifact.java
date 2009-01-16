package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.Geometry;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;


public class RelationshipArtifact extends LineArtifact {

	public RelationshipArtifact(ClassArtifact left, ClassArtifact right) {
		this.left = left;
		left.addRelationship(this);
		this.right = right;
		right.addRelationship(this);
	}

	public ClassArtifact getLeftArtifact() {
		return left;
	}
	
	public ClassArtifact getRightArtifact() {
		return right;
	}

	public void setName(String name) {
		this.name = name;
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
	}

	public void setRightCardinality(String rightCardinality) {
		this.rightCardinality = rightCardinality;
	}
	
	public void setLeftRole(String leftRole) {
		this.leftRole = leftRole;
	}

	public void setRightRole(String rightRole) {
		this.rightRole = rightRole;
	}

	public void setLeftConstraint(String leftConstraint) {
		this.leftConstraint = leftConstraint;
	}

	public void setRightConstraint(String rightConstraint) {
		this.rightConstraint = rightConstraint;
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

		float[] lineBounds = Geometry.computeLineBounds(left, right);
		setBounds((int)Math.round(lineBounds[0]),(int)Math.round(lineBounds[1]),(int)Math.round(lineBounds[2]),(int)Math.round(lineBounds[3]));
		line = gPlatform.buildLine(getX1(), getY1(), getX2(), getY2());
		gPlatform.setStroke(line, ThemeManager.getForegroundColor(), 1);
		gPlatform.addToVirtualGroup(vg, line);
		if (relationClass!=null)
			relationLink = buildRelationLink(vg, getX1(), getY1(), getX2(), getY2(), relationClass);
		if (name!=null)
			nameText = buildName(vg,name, getX1(), getY1(), getX2(), getY2()); 
		if (arrowOnLeft) {
			leftArrow = Geometry.buildArrow(getX1(), getY1(), getX2(), getY2());
			gPlatform.addToVirtualGroup(vg, leftArrow);
		}
		if (arrowOnRight) {
			rightArrow = Geometry.buildArrow(getX2(), getY2(), getX1(), getY1());
			gPlatform.addToVirtualGroup(vg, rightArrow);
		}
		int leftDelta = 0;
		int rightDelta = 0;
		if (leftCardinality!=null || leftConstraint!=null) {
			leftCardinalityText = buildTextWhithAnchor(vg, getX1(), getY1(), getX2(), getY2(), leftDelta, left, leftCardinality, leftConstraint);
			gPlatform.setFillColor(leftCardinalityText, ThemeManager.getForegroundColor());
			leftDelta += gPlatform.getHeightFor(leftCardinalityText)+TEXT_MARGIN;
		}
		if (rightCardinality!=null || rightConstraint!=null) {
			rightCardinalityText = buildTextWhithAnchor(vg, getX2(), getY2(), getX1(), getY1(), rightDelta, right, rightCardinality, rightConstraint);
			gPlatform.setFillColor(rightCardinalityText, ThemeManager.getForegroundColor());
			rightDelta +=  gPlatform.getHeightFor(rightCardinalityText)+TEXT_MARGIN;
		}
		if (leftRole!=null) {
			leftRoleText = buildTextWhithAnchor(vg, getX1(), getY1(), getX2(), getY2(), leftDelta, left, leftRole, null);
			gPlatform.setFillColor(leftRoleText, ThemeManager.getForegroundColor());
			leftDelta +=  gPlatform.getHeightFor(leftRoleText)+TEXT_MARGIN;
		}
		if (rightRole!=null) {
			rightRoleText = buildTextWhithAnchor(vg, getX2(), getY2(), getX1(), getY1(), rightDelta, right, rightRole, null);
			gPlatform.setFillColor(rightRoleText, ThemeManager.getForegroundColor());
			rightDelta +=  gPlatform.getHeightFor(rightRoleText)+TEXT_MARGIN;
		}
		return vg;
	}

	GfxObject buildRelationLink(GfxObject vg, int x1, int y1, int x2, int y2, ClassArtifact clazz) {
		float[] lineBounds = Geometry.computeLineBounds(clazz, (x1+x2)/2, (y1+y2)/2);
		GfxObject relLine = GfxManager.getInstance().buildLine((int)lineBounds[0], (int)lineBounds[1], (int)lineBounds[2], (int)lineBounds[3]);
		GfxManager.getInstance().setStrokeStyle(relLine, GfxStyle.DASH);
		GfxManager.getInstance().addToVirtualGroup(vg, relLine);
		return relLine;
	}
	
	GfxObject buildName(GfxObject vg, String name, int x1, int y1, int x2, int y2) {
		GfxObject nameText = GfxManager.getInstance().buildText(name);
		int x = (x1+x2)/2 - (int)( GfxManager.getInstance().getWidthFor(nameText) /3*2)/2;
		int y = (y1+y2)/2;
		GfxManager.getInstance().setFillColor(nameText, ThemeManager.getForegroundColor());
		GfxManager.getInstance().translate(nameText, x, y);
		GfxManager.getInstance().addToVirtualGroup(vg, nameText);
		return nameText;
	}
	
	GfxObject buildTextWhithAnchor(
			GfxObject vg, int x1, int y1, int x2, int y2, int delta, 
		ClassArtifact clazz, String label1, String label2) 
	{
		GfxPlatform gPlatform = GfxManager.getInstance();
		GfxObject text = null;
		int at = getAnchorType(clazz, x1, y1);
		int x = 0;
		int y = 0;
		int width = 0;
		int height = 0;
		switch (at) {
		case ANCHOR_LEFT:
			text = gPlatform.buildText(getLabel(label2, label1));
			width = (int)gPlatform.getWidthFor(text)/3*2;
			x = x1-width-TEXT_MARGIN;
			height = (int)gPlatform.getHeightFor(text);
			if (y1>y2)
				y = y1+height+TEXT_MARGIN+delta;
			else
				y = y1-TEXT_MARGIN-delta;
			break;
		case ANCHOR_RIGHT:
			text = gPlatform.buildText(getLabel(label1, label2));
			width = (int)gPlatform.getWidthFor(text)/3*2;
			x = x1+TEXT_MARGIN;
			height = (int)gPlatform.getHeightFor(text);
			if (y1>y2)
				y = y1+height+TEXT_MARGIN+delta;
			else
				y = y1-TEXT_MARGIN-delta;
			break;
		case ANCHOR_TOP:
			if (x1<x2) {
				text = gPlatform.buildText(getLabel(label2, label1));
				width = (int)gPlatform.getWidthFor(text)/3*2;
				x = x1-width-TEXT_MARGIN;
			} else {
				text = gPlatform.buildText(getLabel(label1, label2));
				width = (int)gPlatform.getWidthFor(text)/3*2;
				x = x1+TEXT_MARGIN;
			} 
			height = (int)gPlatform.getHeightFor(text);
			y = y1-TEXT_MARGIN-delta;
			break;
		case ANCHOR_NONE:
			
		case ANCHOR_BOTTOM:
			if (x1<x2) {
				text = gPlatform.buildText(getLabel(label2, label1));
				width = (int)gPlatform.getWidthFor(text)/3*2;
				x = x1-width-TEXT_MARGIN;
			} else {
				text = gPlatform.buildText(getLabel(label1, label2));
				width = (int)gPlatform.getWidthFor(text)/3*2;
				x = x1+TEXT_MARGIN;
			}
			height = (int)gPlatform.getHeightFor(text);
			y = y1+height+TEXT_MARGIN+delta;
			break;
		
		}
		gPlatform.setFillColor(text, ThemeManager.getForegroundColor());
		gPlatform.translate(text, x, y);
		gPlatform.addToVirtualGroup(vg, text);
		
		return text;
	}
	
	String getLabel(String label1, String label2) {
		if (label1==null)
			return label2==null ? null : label2;
		else
			return label2==null ? label1 : label1+" "+label2;
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
	
	public Object getSubPart(GfxObject o) {
		// TODO Auto-generated method stub
		return null;
	}
	
	boolean arrowOnLeft = false;
	boolean arrowOnRight = false;
	String name = null;
	String leftCardinality = null;
	String rightCardinality = null;
	String leftRole = null;
	String rightRole = null;
	String leftConstraint = null;
	String rightConstraint = null;
	ClassArtifact left;
	ClassArtifact right;
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

}
