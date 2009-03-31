package com.objetdirect.gwt.umlapi.client.artifacts.links;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
/**
 * @author  florian
 */
public abstract class ClassDependencyLinkArtifact extends LinkArtifact {
	public static class Extension extends ClassDependencyLinkArtifact {
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
			return GeometryManager.getPlatform().buildFilledArrow(x1, y1, x2, y2);
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
	public static class Implementation extends ClassDependencyLinkArtifact {
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
			return GeometryManager.getPlatform().buildFilledArrow(x1, y1, x2, y2);
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
	public static class Simple extends ClassDependencyLinkArtifact {
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
			return GeometryManager.getPlatform().buildArrow(x1, y1, x2, y2);
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
	/**
	 * @uml.property  name="arrow"
	 * @uml.associationEnd  
	 */
	GfxObject arrow = null;
	/**
	 * @uml.property  name="left"
	 * @uml.associationEnd  
	 */
	ClassArtifact left;
	/**
	 * @uml.property  name="line"
	 * @uml.associationEnd  
	 */
	GfxObject line = null;
	/**
	 * @uml.property  name="right"
	 * @uml.associationEnd  
	 */
	ClassArtifact right;
	public ClassDependencyLinkArtifact(ClassArtifact left, ClassArtifact right) {
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
		
		ArrayList<Point> linePoints = GeometryManager.getPlatform().getLineBetween(left, right); 
		
		x1 = linePoints.get(0).getX();
		y1 = linePoints.get(0).getY();
		x2 = linePoints.get(1).getX();
		y2 = linePoints.get(1).getY();
		
		line = buildLine();
		GfxManager.getPlatform().addToVirtualGroup(vg, line);
		arrow = buildArrow();
		GfxManager.getPlatform().addToVirtualGroup(vg, arrow);
	}
	protected abstract GfxObject buildLine();
}
