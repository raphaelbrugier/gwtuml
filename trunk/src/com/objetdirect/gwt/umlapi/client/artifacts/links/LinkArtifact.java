package com.objetdirect.gwt.umlapi.client.artifacts.links;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
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
		return this.idiom;
	    }

	    public String getIdiom(final boolean isRight) {
		if (this.idiom.equals("<") && isRight) {
		    return ">";
		}
		return this.idiom;
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
	    return this.shape;
	}

	/**
	 * @return the isCrossed
	 */
	public boolean isCrossed() {
	    return this.isCrossed;
	}

	/**
	 * @return the isInverted
	 */
	public boolean isInverted() {
	    return this.isInverted;
	}

	/**
	 * @return the isSolid
	 */
	public boolean isSolid() {
	    return this.isSolid;
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
	    return this.style;
	}
    }

    protected LinkAdornment adornmentLeft;
    protected LinkAdornment adornmentRight;
    protected Point leftPoint = new Point(0, 0);
    protected Point rightPoint = new Point(0, 0);
    protected LinkStyle style;

    @Override
    public int getHeight() {
	return this.leftPoint.getY() < this.rightPoint.getY() ? this.rightPoint.getY()
		- this.leftPoint.getY() : this.leftPoint.getY() - this.rightPoint.getY();
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
	return this.leftPoint.getX() < this.rightPoint.getX() ? this.rightPoint.getX()
		- this.leftPoint.getX() : this.leftPoint.getX() - this.rightPoint.getX();
    }

    @Override
    public int getX() {
	return this.leftPoint.getX() < this.rightPoint.getX() ? this.leftPoint.getX()
		: this.rightPoint.getX();
    }

    @Override
    public int getY() {
	return this.leftPoint.getY() < this.rightPoint.getY() ? this.leftPoint.getY()
		: this.rightPoint.getY();
    }

    @Override
    public boolean isALink() {
	return true;
    }

    @Override
    public boolean isDraggable() {
	return false;
    }

    public void moved() {
	throw new UMLDrawerException("can't move a line !");
    }

    @Override
    public void moveTo(final int x, final int y) {
	throw new UMLDrawerException(
		"invalid operation : setLocation on a line");
    }

    public abstract void removeCreatedDependency();

    public void setLocation(final int x, final int y) {
	throw new UMLDrawerException(
		"invalid operation : setLocation on a line");
    }
}
