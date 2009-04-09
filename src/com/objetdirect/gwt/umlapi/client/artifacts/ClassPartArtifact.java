package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;

/**
 * This abstract class represent a part of a {@link ClassArtifact}
 * 
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
    /**
     * Like {@link UMLArtifact#edit(GfxObject)} this method requests an edition but for a new object
     */
    public abstract void edit();

    /**
     * Getter for the classArtifact
     * 
     * @return the classArtifact
     */
    public ClassArtifact getClassArtifact() {
        return this.classArtifact;
    }

    @Override
    public boolean isDraggable() {
	return false;
    }


    /**
     * This method creates the graphical object for the text to determine the size of this part
     */
    abstract void computeBounds();
    
    /**
     * Setter for the classArtifact
     * 
     * @param classArtifact the classArtifact to set
     */
    void setClassArtifact(final ClassArtifact classArtifact) {
        this.classArtifact = classArtifact;
    }
    
    abstract void setClassWidth(int width);
}
