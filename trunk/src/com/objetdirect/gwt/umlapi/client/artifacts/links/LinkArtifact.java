package com.objetdirect.gwt.umlapi.client.artifacts.links;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
public abstract class LinkArtifact extends UMLArtifact {
    
    public enum LinkAdornment {
        NONE(Shape.NONE, false),
        CROSS(Shape.CROSS, false),
        WIRE_ARROW(Shape.ARROW, false),
        SOLID_ARROW(Shape.ARROW, true),
        SOLID_DIAMOND(Shape.DIAMOND, true),
        INVERTED_SOLID_DIAMOND(Shape.DIAMOND, true, true);
        
        public enum Shape {
            NONE, CROSS, ARROW, DIAMOND;
        }
        
        private Shape shape;
        private boolean isSolid;
        private boolean isInverted;
        
        private LinkAdornment(Shape shape, boolean isSolid) {
            this(shape, isSolid, false);
        }
        private LinkAdornment(Shape shape, boolean isSolid, boolean isInverted) {
            this.shape = shape;
            this.isSolid = isSolid;
            this.isInverted = isInverted;
            
        }
        /**
         * @return the shape
         */
        public Shape getShape() {
            return shape;
        }
        /**
         * @return the isSolid
         */
        public boolean isSolid() {
            return isSolid;
        }
        /**
         * @return the isInverted
         */
        public boolean isInverted() {
            return isInverted;
        }
        
    }
    public enum LinkStyle {
        SOLID(GfxStyle.NONE), DASHED(GfxStyle.DASH), LONG_DASHED(GfxStyle.LONGDASH);
        
        private GfxStyle style;
        private LinkStyle (GfxStyle style) {
            this.style = style;
        }
        public GfxStyle getGfxStyle() {
            return style;
        }
    }
    
    protected Point point1 = new Point(0,0);
    protected Point point2 = new Point(0,0);
    protected LinkAdornment adornmentLeft;
    protected LinkAdornment adornmentRight;
    protected LinkStyle style;
    
    @Override
	public int getHeight() {
		return point1.getY() < point2.getY() ? point2.getY() - point1.getY() : point1.getY() - point2.getY();
	}
	@Override
	public int[] getOpaque() {
		return null;
	}
	public GfxObject getOutline() {
		return null;
	}
	@Override
	public int getWidth() {
		return point1.getX() < point2.getX() ? point2.getX() - point1.getX() : point1.getX() - point2.getX();
	}
	@Override
	public int getX() {
		return point1.getX() < point2.getX() ? point1.getX() : point2.getX();
	}
	@Override
	public int getY() {
		return point1.getY() < point2.getY() ? point1.getY() : point2.getY();
	}
	public boolean isDraggable() {
		return false;
	}
	public void setLocation(int x, int y) {
		throw new UMLDrawerException(
		"invalid operation : setLocation on a line");
	}
	public void moveTo(int x, int y) {
		throw new UMLDrawerException(
		"invalid operation : setLocation on a line");
	}
	public void moved() {
		throw new UMLDrawerException(
		"can't move a line !");
	}
}
