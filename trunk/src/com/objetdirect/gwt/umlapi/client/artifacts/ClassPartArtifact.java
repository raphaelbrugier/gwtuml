package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public abstract class ClassPartArtifact extends BoxArtifact {

    protected ClassArtifact classArtifact;

    protected int classWidth;
    protected int height;

    protected GfxObject textVirtualGroup;

    protected int width;

    @Override
    public void buildGfxObjectWithAnimation() {
	buildGfxObject();
    }
    @Override
    public boolean isDraggable() {
	return false;
    }

    public abstract void computeBounds();

    public abstract void edit();

    public ClassArtifact getClassArtifact() {
	return classArtifact;
    }

    public void setClassArtifact(final ClassArtifact classArtifact) {
	this.classArtifact = classArtifact;
    }

    public abstract void setClassWidth(int width);

    public abstract void setHeight(int height);

    public abstract void setWidth(int width);
}
