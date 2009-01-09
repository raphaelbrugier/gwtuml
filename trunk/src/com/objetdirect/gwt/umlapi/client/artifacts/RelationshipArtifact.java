package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.Geometry;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.tatami.client.gfx.Color;
import com.objetdirect.tatami.client.gfx.GraphicObject;
import com.objetdirect.tatami.client.gfx.Line;
import com.objetdirect.tatami.client.gfx.Path;
import com.objetdirect.tatami.client.gfx.Text;
import com.objetdirect.tatami.client.gfx.VirtualGroup;

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
	
	protected static final int ANCHOR_TOP = 0;
	protected static final int ANCHOR_BOTTOM = 1;
	protected static final int ANCHOR_LEFT = 2;
	protected static final int ANCHOR_RIGHT = 3;
		
	protected GraphicObject buildGraphicObject() {
		VirtualGroup vg = new VirtualGroup();

		float[] lineBounds = Geometry.computeLineBounds(left, right);
		setBounds((int)Math.round(lineBounds[0]),(int)Math.round(lineBounds[1]),(int)Math.round(lineBounds[2]),(int)Math.round(lineBounds[3]));
		line = new Line(getX1(), getY1(), getX2(), getY2());
		line.setStroke(Color.BLACK, 1);
		vg.add(line);
		if (relationClass!=null)
			relationLink = buildRelationLink(vg, getX1(), getY1(), getX2(), getY2(), relationClass);
		if (name!=null)
			nameText = buildName(vg,name, getX1(), getY1(), getX2(), getY2()); 
		if (arrowOnLeft) {
			leftArrow = Geometry.buildArrow(getX1(), getY1(), getX2(), getY2());
			vg.add(leftArrow);
		}
		if (arrowOnRight) {
			rightArrow = Geometry.buildArrow(getX2(), getY2(), getX1(), getY1());
			vg.add(rightArrow);
		}
		int leftDelta = 0;
		int rightDelta = 0;
		if (leftCardinality!=null || leftConstraint!=null) {
			leftCardinalityText = buildText(vg, getX1(), getY1(), getX2(), getY2(), leftDelta, left, leftCardinality, leftConstraint);
			leftDelta += leftCardinalityText.getHeight()+TEXT_MARGIN;
		}
		if (rightCardinality!=null || rightConstraint!=null) {
			rightCardinalityText = buildText(vg, getX2(), getY2(), getX1(), getY1(), rightDelta, right, rightCardinality, rightConstraint);
			rightDelta += rightCardinalityText.getHeight()+TEXT_MARGIN;
		}
		if (leftRole!=null) {
			leftRoleText = buildText(vg, getX1(), getY1(), getX2(), getY2(), leftDelta, left, leftRole, null);
			leftDelta += leftRoleText.getHeight()+TEXT_MARGIN;
		}
		if (rightRole!=null) {
			rightRoleText = buildText(vg, getX2(), getY2(), getX1(), getY1(), rightDelta, right, rightRole, null);
			rightDelta += rightRoleText.getHeight()+TEXT_MARGIN;
		}
		return vg;
	}

	Line buildRelationLink(VirtualGroup vg, int x1, int y1, int x2, int y2, ClassArtifact clazz) {
		float[] lineBounds = Geometry.computeLineBounds(clazz, (x1+x2)/2, (y1+y2)/2);
		Line relLine = new Line((int)lineBounds[0], (int)lineBounds[1], (int)lineBounds[2], (int)lineBounds[3]);
		relLine.setStrokeStyle(GraphicObject.DASH);
		vg.add(relLine);
		return relLine;
	}
	
	Text buildName(VirtualGroup vg, String name, int x1, int y1, int x2, int y2) {
		Text nameText = new Text(name);
		int x = (x1+x2)/2 - (int)(nameText.getWidth()/3*2)/2;
		int y = (y1+y2)/2;
		nameText.translate(x, y);
		vg.add(nameText);
		return nameText;
	}
	
	Text buildText(
		VirtualGroup vg, int x1, int y1, int x2, int y2, int delta, 
		ClassArtifact clazz, String label1, String label2) 
	{
		Text text = null;
		int at = getAnchorType(clazz, x1, y1);
		int x = 0;
		int y = 0;
		int width = 0;
		int height = 0;
		switch (at) {
		case ANCHOR_LEFT:
			text = new Text(getLabel(label2, label1));
			width = (int)text.getWidth()/3*2;
			x = x1-width-TEXT_MARGIN;
			height = (int)text.getHeight();
			if (y1>y2)
				y = y1+height+TEXT_MARGIN+delta;
			else
				y = y1-TEXT_MARGIN-delta;
			break;
		case ANCHOR_RIGHT:
			text = new Text(getLabel(label1, label2));
			width = (int)text.getWidth()/3*2;
			x = x1+TEXT_MARGIN;
			height = (int)text.getHeight();
			if (y1>y2)
				y = y1+height+TEXT_MARGIN+delta;
			else
				y = y1-TEXT_MARGIN-delta;
			break;
		case ANCHOR_TOP:
			if (x1<x2) {
				text = new Text(getLabel(label2, label1));
				width = (int)text.getWidth()/3*2;
				x = x1-width-TEXT_MARGIN;
			} else {
				text = new Text(getLabel(label1, label2));
				width = (int)text.getWidth()/3*2;
				x = x1+TEXT_MARGIN;
			} 
			height = (int)text.getHeight();
			y = y1-TEXT_MARGIN-delta;
			break;
		case ANCHOR_BOTTOM:
			if (x1<x2) {
				text = new Text(getLabel(label2, label1));
				width = (int)text.getWidth()/3*2;
				x = x1-width-TEXT_MARGIN;
			} else {
				text = new Text(getLabel(label1, label2));
				width = (int)text.getWidth()/3*2;
				x = x1+TEXT_MARGIN;
			}
			height = (int)text.getHeight();
			y = y1+height+TEXT_MARGIN+delta;
			break;
		}
		text.translate(x, y);
		vg.add(text);
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
		throw new UMLDrawerException("Unable to find anchor type");
	}

	
	public List<GraphicObject> getComponents() {
		List<GraphicObject> comps = new ArrayList<GraphicObject>();
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
		line.setStroke(Color.BLUE, 2);
		if (leftArrow!=null)
			leftArrow.setStroke(Color.BLUE, 2);
		if (rightArrow!=null)
			rightArrow.setStroke(Color.BLUE, 2);
	}

	public void unselect() {
		line.setStroke(Color.BLACK, 1);
		if (leftArrow!=null)
			leftArrow.setStroke(Color.BLACK, 1);
		if (rightArrow!=null)
			rightArrow.setStroke(Color.BLACK, 1);
	}
	
	public Object getSubPart(GraphicObject o) {
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
	Line line = null;
	Line relationLink = null;
	Path leftArrow = null;
	Path rightArrow = null;
	Text nameText = null;
	Text leftCardinalityText = null;
	Text rightCardinalityText = null;
	Text leftRoleText = null;
	Text rightRoleText = null;

}
