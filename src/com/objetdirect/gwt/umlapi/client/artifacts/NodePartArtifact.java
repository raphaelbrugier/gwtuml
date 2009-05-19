package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * This abstract class represent a part of a {@link NodeArtifact}
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public abstract class NodePartArtifact extends BoxArtifact {

    protected NodeArtifact nodeArtifact;
    protected int nodeWidth;
    protected int height;
    protected GfxObject textVirtualGroup;
    protected int width;

    /**
     * Constructor of NodePartArtifact
     *
     */
    public NodePartArtifact() {
	super(false);
    }

    @Override
    public void buildGfxObjectWithAnimation() {
	buildGfxObject();
    }
    /**
     * Like {@link UMLArtifact#edit(GfxObject)} this method requests an edition but for a new object
     */
    public abstract void edit();

    /**
     * Getter for the nodeArtifact
     * 
     * @return the nodeArtifact
     */
    public NodeArtifact getNodeArtifact() {
        return this.nodeArtifact;
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
     * Setter for the nodeArtifact
     * 
     * @param nodeArtifact the nodeArtifact to set
     */
    void setNodeArtifact(final NodeArtifact nodeArtifact) {
        this.nodeArtifact = nodeArtifact;
    }
    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#getOutline()
     */
    @Override
    public GfxObject getOutline() {
	final GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
	final GfxObject rect = GfxManager.getPlatform().buildRect(this.nodeWidth, getHeight());
	GfxManager.getPlatform().setStrokeStyle(rect, GfxStyle.DASH);
	GfxManager.getPlatform().setStroke(rect,
		ThemeManager.getTheme().getHighlightedForegroundColor(), 1);
	GfxManager.getPlatform().setFillColor(rect,
		ThemeManager.getTheme().getBackgroundColor());
	GfxManager.getPlatform().addToVirtualGroup(vg, rect);
	return vg;
    }
    
    abstract void setNodeWidth(int width);
}
