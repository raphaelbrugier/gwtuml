package com.objetdirect.gwt.umlapi.client.artifacts.links;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
public abstract class LinkArtifact extends UMLArtifact {

    public enum LinkAdornment {
        NONE(Shape.NONE, false),
        WIRE_ARROW(Shape.ARROW, false),
        SOLID_ARROW(Shape.ARROW, true),
        SOLID_DIAMOND(Shape.DIAMOND, true),
        INVERTED_SOLID_DIAMOND(Shape.DIAMOND, true, true);

        public enum Shape {
            NONE(""), CROSS("x"), ARROW("<"), DIAMOND("<>");
            private String idiom;

            private Shape (String idiom) {
                this.idiom = idiom;
            }

            public String getIdiom() {
                return idiom;
            }
            
            public String getIdiom(boolean isRight) {
                if(idiom.equals("<") && isRight) return ">"; 
                return idiom;
            }
        }

        private Shape shape;
        private boolean isSolid;
        private boolean isInverted;
        private boolean isCrossed;

        /**
         * @return the isCrossed
         */
        public boolean isCrossed() {
            return isCrossed;
        }
        /**
         * @param isCrossed the isCrossed to set
         */
        public void setCrossed(boolean isCrossed) {
            this.isCrossed = isCrossed;
        }
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

    protected Point leftPoint = new Point(0,0);
    protected Point rightPoint = new Point(0,0);
    protected LinkAdornment adornmentLeft;
    protected LinkAdornment adornmentRight;
    protected LinkStyle style;
    
    @Override
    public boolean isALink() {
        return true;
    }
    @Override
    public int getHeight() {
        return leftPoint.getY() < rightPoint.getY() ? rightPoint.getY() - leftPoint.getY() : leftPoint.getY() - rightPoint.getY();
    }
    @Override
    public int[] getOpaque() {
        return null;
    }
    @Override
    public GfxObject getOutline() {
        return null;
    }
    @Override
    public int getWidth() {
        return leftPoint.getX() < rightPoint.getX() ? rightPoint.getX() - leftPoint.getX() : leftPoint.getX() - rightPoint.getX();
    }
    @Override
    public int getX() {
        return leftPoint.getX() < rightPoint.getX() ? leftPoint.getX() : rightPoint.getX();
    }
    @Override
    public int getY() {
        return leftPoint.getY() < rightPoint.getY() ? leftPoint.getY() : rightPoint.getY();
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
