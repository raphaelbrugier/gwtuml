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
public abstract class DependencyLinkArtifact extends LinkArtifact {
	public static class Extension extends DependencyLinkArtifact {
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
			return GeometryManager.getPlatform().buildArrow(point1, point2, LinkAdornment.WHITE_ARROW);
		}
		@Override
		protected GfxObject buildLine() {
			GfxObject line = GfxManager.getPlatform().buildLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
			GfxManager.getPlatform().setStroke(line,
					ThemeManager.getForegroundColor(), 1);
			GfxManager.getPlatform().setStrokeStyle(line, GfxStyle.SOLID);
			return line;
		}
	}
	public static class Implementation extends DependencyLinkArtifact {
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
			return GeometryManager.getPlatform().buildArrow(point1, point2, LinkAdornment.WHITE_DIAMOND);
		}
		@Override
		protected GfxObject buildLine() {
			GfxObject line = GfxManager.getPlatform().buildLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
			GfxManager.getPlatform().setStroke(line,
					ThemeManager.getForegroundColor(), 1);
			GfxManager.getPlatform().setStrokeStyle(line, GfxStyle.DASH);
			return line;
		}
	}
	public static class Simple extends DependencyLinkArtifact {
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
			return GeometryManager.getPlatform().buildArrow(point1, point2, LinkAdornment.ARROW);
		}
		@Override
		protected GfxObject buildLine() {
			GfxObject line = GfxManager.getPlatform().buildLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
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
	GfxObject arrow;
	/**
	 * @uml.property  name="left"
	 * @uml.associationEnd  
	 */
	ClassArtifact left;
	/**
	 * @uml.property  name="line"
	 * @uml.associationEnd  
	 */
	GfxObject line;
	/**
	 * @uml.property  name="right"
	 * @uml.associationEnd  
	 */
	ClassArtifact right;
	public DependencyLinkArtifact(ClassArtifact left, ClassArtifact right) {
		this.left = left;
		left.addDependency(this);
		this.right = right;
		right.addDependency(this);
	}
	
	public void edit(GfxObject gfxObject, int x, int y) {
		// TODO Auto-generated method stub
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
		
		point1 = linePoints.get(0);	
		point2 = linePoints.get(1);
		
		line = buildLine();
		GfxManager.getPlatform().addToVirtualGroup(vg, line);
		arrow = buildArrow();
		GfxManager.getPlatform().addToVirtualGroup(vg, arrow);
	}
	protected abstract GfxObject buildLine();
}
