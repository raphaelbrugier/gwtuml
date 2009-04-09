package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;

public abstract class LinkArtifact extends UMLArtifact {

    public enum LinkAdornment {
	INVERTED_SOLID_DIAMOND(Shape.DIAMOND, true, true), NONE(Shape.UNSHAPED,
		false), SOLID_ARROW(Shape.ARROW, true), SOLID_DIAMOND(
		Shape.DIAMOND, true), WIRE_ARROW(Shape.ARROW, false);

	public enum Shape {
	    ARROW("<"), CROSS("x"), DIAMOND("<>"), UNSHAPED("");
	    private final String idiom;

	    private Shape(final String idiom) {
		this.idiom = idiom;
	    }

	    public String getIdiom() {
		return idiom;
	    }

	    public String getIdiom(final boolean isRight) {
		if (idiom.equals("<") && isRight) {
		    return ">";
		}
		return idiom;
	    }
	}

	private boolean isCrossed;
	private final boolean isInverted;
	private final boolean isSolid;
	private final Shape shape;

	private LinkAdornment(final Shape shape, final boolean isSolid) {
	    this(shape, isSolid, false);
	}

	private LinkAdornment(final Shape shape, final boolean isSolid,
		final boolean isInverted) {
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
	 * @return the isCrossed
	 */
	public boolean isCrossed() {
	    return isCrossed;
	}

	/**
	 * @return the isInverted
	 */
	public boolean isInverted() {
	    return isInverted;
	}

	/**
	 * @return the isSolid
	 */
	public boolean isSolid() {
	    return isSolid;
	}

	/**
	 * @param isCrossed
	 *            the isCrossed to set
	 */
	public void setCrossed(final boolean isCrossed) {
	    this.isCrossed = isCrossed;
	}

    }

    public enum LinkStyle {
	DASHED(GfxStyle.DASH), LONG_DASHED(GfxStyle.LONGDASH), SOLID(
		GfxStyle.NONE);

	private final GfxStyle style;

	private LinkStyle(final GfxStyle style) {
	    this.style = style;
	}

	public GfxStyle getGfxStyle() {
	    return style;
	}
    }

    protected LinkAdornment adornmentLeft;
    protected LinkAdornment adornmentRight;
    protected Point leftPoint = Point.getOrigin();
    protected Point rightPoint = Point.getOrigin();
    protected LinkStyle style;

    @Override
    public int getHeight() {
	return leftPoint.getY() < rightPoint.getY() ? rightPoint.getY()
		- leftPoint.getY() : leftPoint.getY() - rightPoint.getY();
    }

    @Override
    public Point getLocation() {
	return Point.min(leftPoint, rightPoint);
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
	return leftPoint.getX() < rightPoint.getX() ? rightPoint.getX()
		- leftPoint.getX() : leftPoint.getX() - rightPoint.getX();
    }

    @Override
    public boolean isALink() {
	return true;
    }

    @Override
    public boolean isDraggable() {
	return false;
    }

   public abstract void removeCreatedDependency();
}
