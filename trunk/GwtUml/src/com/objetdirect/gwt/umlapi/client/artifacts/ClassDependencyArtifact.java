package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.engine.Geometry;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

public abstract class ClassDependencyArtifact extends LineArtifact {

	public static class Extension extends ClassDependencyArtifact {

		public Extension(ClassArtifact left, ClassArtifact right) {
			super(left, right);
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
			rightMenu.put("Extension", doNothing);
			rightMenu.put("-", null);
			rightMenu.put("Change to simple", doNothing);
			rightMenu.put("Change to implementation", doNothing);
			rightMenu.put("Reverse", doNothing);
			rightMenu.put("Delete", remove);
			return rightMenu;
		}

		@Override
		protected GfxObject buildArrow(float[] lineBounds) {
			return Geometry.buildFilledArrow((int) lineBounds[2],
					(int) lineBounds[3], (int) lineBounds[0],
					(int) lineBounds[1]);
		}

		@Override
		protected GfxObject buildLine(float[] lineBounds) {

			GfxObject line = GfxManager.getPlatform().buildLine(
					(int) lineBounds[0], (int) lineBounds[1],
					(int) lineBounds[2], (int) lineBounds[3]);
			GfxManager.getPlatform().setStroke(line,
					ThemeManager.getForegroundColor(), 1);
			GfxManager.getPlatform().setStrokeStyle(line, GfxStyle.SOLID);
			return line;
		}

	}

	public static class Implementation extends ClassDependencyArtifact {

		public Implementation(ClassArtifact left, ClassArtifact right) {
			super(left, right);
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
			rightMenu.put("Implementation", doNothing);
			rightMenu.put("-", null);
			rightMenu.put("> Change to simple", doNothing);
			rightMenu.put("> Change to extension", doNothing);
			rightMenu.put("> Reverse", doNothing);
			rightMenu.put("> Delete", remove);
			return rightMenu;
		}

		@Override
		protected GfxObject buildArrow(float[] lineBounds) {
			return Geometry.buildFilledArrow((int) lineBounds[2],
					(int) lineBounds[3], (int) lineBounds[0],
					(int) lineBounds[1]);
		}

		@Override
		protected GfxObject buildLine(float[] lineBounds) {

			GfxObject line = GfxManager.getPlatform().buildLine(
					(int) lineBounds[0], (int) lineBounds[1],
					(int) lineBounds[2], (int) lineBounds[3]);
			GfxManager.getPlatform().setStroke(line,
					ThemeManager.getForegroundColor(), 1);
			GfxManager.getPlatform().setStrokeStyle(line, GfxStyle.DASH);
			return line;
		}

	}

	public static class Simple extends ClassDependencyArtifact {

		public Simple(ClassArtifact left, ClassArtifact right) {
			super(left, right);
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
			rightMenu.put("Simple", doNothing);
			rightMenu.put("-", null);
			rightMenu.put("Change to extension", doNothing);
			rightMenu.put("Change to implementation", doNothing);
			rightMenu.put("Reverse", doNothing);
			rightMenu.put("Delete", remove);
			return rightMenu;
		}

		@Override
		protected GfxObject buildArrow(float[] lineBounds) {
			return Geometry.buildArrow((int) lineBounds[2],
					(int) lineBounds[3], (int) lineBounds[0],
					(int) lineBounds[1]);
		}

		@Override
		protected GfxObject buildLine(float[] lineBounds) {

			GfxObject line = GfxManager.getPlatform().buildLine(
					(int) lineBounds[0], (int) lineBounds[1],
					(int) lineBounds[2], (int) lineBounds[3]);
			GfxManager.getPlatform().setStroke(line,
					ThemeManager.getForegroundColor(), 1);
			GfxManager.getPlatform().setStrokeStyle(line, GfxStyle.DASH);

			return line;
		}
	}

	GfxObject arrow = null;

	ClassArtifact left;

	GfxObject line = null;

	ClassArtifact right;

	public ClassDependencyArtifact(ClassArtifact left, ClassArtifact right) {
		this.left = left;
		left.addClassDependency(this);
		this.right = right;
		right.addClassDependency(this);
	}

	public void edit(GfxObject gfxObject, int x, int y) {
		// TODO Auto-generated method stub

	}

	public List<GfxObject> getComponents() {
		List<GfxObject> comps = new ArrayList<GfxObject>();
		comps.add(line);
		comps.add(arrow);
		return comps;
	}
	public Object getSubPart(GfxObject o) {
		// TODO Auto-generated method stub
		return null;
	}
	public void select() {
		GfxManager.getPlatform().setStroke(line,
				ThemeManager.getHighlightedForegroundColor(), 2);
		GfxManager.getPlatform().setStroke(arrow,
				ThemeManager.getHighlightedForegroundColor(), 2);
	}
	public void unselect() {
		GfxManager.getPlatform().setStroke(line,
				ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(arrow,
				ThemeManager.getForegroundColor(), 1);
	}

	protected abstract GfxObject buildArrow(float[] lineBounds);

	@Override
	protected GfxObject buildGfxObject() {

		GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
		float[] lineBounds = Geometry.computeLineBounds(left, right);
		setBounds((int) lineBounds[0], (int) lineBounds[1],
				(int) lineBounds[2], (int) lineBounds[3]);
		line = buildLine(lineBounds);
		GfxManager.getPlatform().addToVirtualGroup(vg, line);
		arrow = buildArrow(lineBounds);
		GfxManager.getPlatform().addToVirtualGroup(vg, arrow);
		return vg;
	}

	protected abstract GfxObject buildLine(float[] lineBounds);
}
