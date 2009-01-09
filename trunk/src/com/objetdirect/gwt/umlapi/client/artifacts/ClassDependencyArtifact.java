package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.Geometry;
import com.objetdirect.tatami.client.gfx.Color;
import com.objetdirect.tatami.client.gfx.GraphicObject;
import com.objetdirect.tatami.client.gfx.Line;
import com.objetdirect.tatami.client.gfx.VirtualGroup;

public abstract class ClassDependencyArtifact extends LineArtifact  {

	public ClassDependencyArtifact(ClassArtifact left, ClassArtifact right) {
		this.left = left;
		left.addClassDependency(this);
		this.right = right;
		right.addClassDependency(this);
	}
	
	protected GraphicObject buildGraphicObject() {
		VirtualGroup vg = new VirtualGroup();
		float[] lineBounds = Geometry.computeLineBounds(left, right);
		setBounds((int)lineBounds[0], (int)lineBounds[1], (int)lineBounds[2], (int)lineBounds[3]);
		line = buildLine(lineBounds);
		vg.add(line);
		arrow = buildArrow(lineBounds); 
		vg.add(arrow);
		return vg;
	}

	public List<GraphicObject> getComponents() {
		List<GraphicObject> comps = new ArrayList<GraphicObject>();
		comps.add(line);
		comps.add(arrow);
		return comps;
	}
	
	public void select() {
		line.setStroke(Color.BLUE, 2);
		arrow.setStroke(Color.BLUE, 2);
	}

	public void unselect() {
		line.setStroke(Color.BLACK, 1);
		arrow.setStroke(Color.BLACK, 1);
	}

	public Object getSubPart(GraphicObject o) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected abstract GraphicObject buildLine(float[] lineBounds);
	
	protected abstract GraphicObject buildArrow(float[] lineBounds);

	ClassArtifact left;
	ClassArtifact right;
	GraphicObject line = null;
	GraphicObject arrow = null;

	public static class Simple extends ClassDependencyArtifact {

		public Simple(ClassArtifact left, ClassArtifact right) {
			super(left, right);
		}

		protected GraphicObject buildLine(float[] lineBounds) {
			Line line = new Line((int)lineBounds[0],(int)lineBounds[1],(int)lineBounds[2],(int)lineBounds[3]);
			line.setStroke(Color.BLACK, 1);
			line.setStrokeStyle(GraphicObject.DASH);
			return line;
		}
		
		protected GraphicObject buildArrow(float[] lineBounds) {
			return Geometry.buildArrow((int)lineBounds[2],(int)lineBounds[3],(int)lineBounds[0],(int)lineBounds[1]);
		}
		
	}
	
	public static class Extension extends ClassDependencyArtifact {

		public Extension(ClassArtifact left, ClassArtifact right) {
			super(left, right);
		}

		protected GraphicObject buildLine(float[] lineBounds) {
			Line line = new Line((int)lineBounds[0],(int)lineBounds[1],(int)lineBounds[2],(int)lineBounds[3]);
			line.setStroke(Color.BLACK, 1);
			line.setStrokeStyle(GraphicObject.SOLID);
			return line;
		}
		
		protected GraphicObject buildArrow(float[] lineBounds) {
			return Geometry.buildFilledArrow((int)lineBounds[2],(int)lineBounds[3],(int)lineBounds[0],(int)lineBounds[1]);
		}
		
	}

	public static class Implementation extends ClassDependencyArtifact {

		public Implementation(ClassArtifact left, ClassArtifact right) {
			super(left, right);
		}

		protected GraphicObject buildLine(float[] lineBounds) {
			Line line = new Line((int)lineBounds[0],(int)lineBounds[1],(int)lineBounds[2],(int)lineBounds[3]);
			line.setStroke(Color.BLACK, 1);
			line.setStrokeStyle(GraphicObject.DASH);
			return line;
		}
		
		protected GraphicObject buildArrow(float[] lineBounds) {
			return Geometry.buildFilledArrow((int)lineBounds[2],(int)lineBounds[3],(int)lineBounds[0],(int)lineBounds[1]);
		}
		
	}
}
