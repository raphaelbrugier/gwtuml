package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.Geometry;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;


public abstract class ClassDependencyArtifact extends LineArtifact  {

	public ClassDependencyArtifact(ClassArtifact left, ClassArtifact right) {
		this.left = left;
		left.addClassDependency(this);
		this.right = right;
		right.addClassDependency(this);
	}
	
	protected GfxObject buildGfxObject() {
		GfxPlatform gPlatform = GfxManager.getInstance();
		GfxObject vg = gPlatform.buildVirtualGroup();
		float[] lineBounds = Geometry.computeLineBounds(left, right);
		setBounds((int)lineBounds[0], (int)lineBounds[1], (int)lineBounds[2], (int)lineBounds[3]);
		line = buildLine(lineBounds);
		gPlatform.addToVirtualGroup(vg, line);
		arrow = buildArrow(lineBounds); 
		gPlatform.addToVirtualGroup(vg, arrow);
		return vg;
	}

	public List<GfxObject> getComponents() {
		List<GfxObject> comps = new ArrayList<GfxObject>();
		comps.add(line);
		comps.add(arrow);
		return comps;
	}
	
	public void select() {
		GfxManager.getInstance().setStroke(line, ThemeManager.getHighlightedForegroundColor(), 2);
		GfxManager.getInstance().setStroke(arrow, ThemeManager.getHighlightedForegroundColor(), 2);
	}

	public void unselect() {
		GfxManager.getInstance().setStroke(line, ThemeManager.getForegroundColor(), 1);
		GfxManager.getInstance().setStroke(arrow, ThemeManager.getForegroundColor(), 1);
	}

	public Object getSubPart(GfxObject o) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected abstract GfxObject buildLine(float[] lineBounds);
	
	protected abstract GfxObject buildArrow(float[] lineBounds);

	ClassArtifact left;
	ClassArtifact right;
	GfxObject line = null;
	GfxObject arrow = null;

	public static class Simple extends ClassDependencyArtifact {

		public Simple(ClassArtifact left, ClassArtifact right) {
			super(left, right);
		}

		protected GfxObject buildLine(float[] lineBounds) {
			GfxPlatform gPlatform = GfxManager.getInstance();
			GfxObject line = gPlatform.buildLine((int)lineBounds[0],(int)lineBounds[1],(int)lineBounds[2],(int)lineBounds[3]);
			gPlatform.setStroke(line, ThemeManager.getForegroundColor(), 1);
			gPlatform.setStrokeStyle(line, GfxStyle.DASH);

			return line;
		}
		
		protected GfxObject buildArrow(float[] lineBounds) {
			return Geometry.buildArrow((int)lineBounds[2],(int)lineBounds[3],(int)lineBounds[0],(int)lineBounds[1]);
		}
		
	}
	
	public static class Extension extends ClassDependencyArtifact {

		public Extension(ClassArtifact left, ClassArtifact right) {
			super(left, right);
		}

		protected GfxObject buildLine(float[] lineBounds) {
			GfxPlatform gPlatform = GfxManager.getInstance();
			GfxObject line = gPlatform.buildLine((int)lineBounds[0],(int)lineBounds[1],(int)lineBounds[2],(int)lineBounds[3]);
			gPlatform.setStroke(line, ThemeManager.getForegroundColor(), 1);
			gPlatform.setStrokeStyle(line, GfxStyle.SOLID);
			return line;
		}
		
		protected GfxObject buildArrow(float[] lineBounds) {
			return Geometry.buildFilledArrow((int)lineBounds[2],(int)lineBounds[3],(int)lineBounds[0],(int)lineBounds[1]);
		}
		
	}

	public static class Implementation extends ClassDependencyArtifact {

		public Implementation(ClassArtifact left, ClassArtifact right) {
			super(left, right);
		}

		protected GfxObject buildLine(float[] lineBounds) {
			GfxPlatform gPlatform = GfxManager.getInstance();
			GfxObject line = gPlatform.buildLine((int)lineBounds[0],(int)lineBounds[1],(int)lineBounds[2],(int)lineBounds[3]);
			gPlatform.setStroke(line, ThemeManager.getForegroundColor(), 1);
			gPlatform.setStrokeStyle(line, GfxStyle.DASH);
			return line;
		}
		
		protected GfxObject buildArrow(float[] lineBounds) {
			return Geometry.buildFilledArrow((int)lineBounds[2],(int)lineBounds[3],(int)lineBounds[0],(int)lineBounds[1]);
		}
		
	}
}