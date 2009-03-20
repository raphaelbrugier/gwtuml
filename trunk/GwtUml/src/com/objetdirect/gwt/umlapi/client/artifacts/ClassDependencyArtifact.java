package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.LinkedHashMap;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Geometry;
import com.objetdirect.gwt.umlapi.client.engine.Point;
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
		protected GfxObject buildArrow() {
			return Geometry.buildFilledArrow(x1, y1, x2, y2);
		}

		@Override
		protected GfxObject buildLine() {

			GfxObject line = GfxManager.getPlatform().buildLine(x1, y1, x2, y2);
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
		protected GfxObject buildArrow() {
			return Geometry.buildFilledArrow(x1, y1, x2, y2);
		}

		@Override
		protected GfxObject buildLine() {

			GfxObject line = GfxManager.getPlatform().buildLine(x1, y1, x2, y2);
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
		protected GfxObject buildArrow() {
			return Geometry.buildArrow(x1, y1, x2, y2);
		}

		@Override
		protected GfxObject buildLine() {

			GfxObject line = GfxManager.getPlatform().buildLine(x1, y1, x2, y2);
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
		left.addDependency(this);
		this.right = right;
		right.addDependency(this);
	}

	public void edit(GfxObject gfxObject, int x, int y) {
		// TODO Auto-generated method stub

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

	protected abstract GfxObject buildArrow();

	@Override
	protected void buildGfxObject() {

		GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, vg);
		Point lineLeftPoint  = Geometry.getPointForLine(left, new Point(right.getCenterX(), right.getCenterY()));
		Point lineRightPoint = Geometry.getPointForLine(right, new Point(left.getCenterX(), left.getCenterY()));
		x1 = lineLeftPoint.getX();
		y1 = lineLeftPoint.getY();
		x2 = lineRightPoint.getX();
		y2 = lineRightPoint.getY();
		

		line = buildLine();
		GfxManager.getPlatform().addToVirtualGroup(vg, line);
		arrow = buildArrow();
		GfxManager.getPlatform().addToVirtualGroup(vg, arrow);
	}

	protected abstract GfxObject buildLine();
}
