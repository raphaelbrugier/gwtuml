package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.QualityLevel;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * This class is an artifact used to represent a class. <br>
 * A class is divided in three {@link NodePartArtifact} : <ul>
 * <li>{@link ClassPartNameArtifact} For the name and stereotype part</li>
 * <li>{@link ClassPartAttributesArtifact} For the attribute list part</li>
 * <li>{@link ClassPartMethodsArtifact} For the method list part</li>
 * </ul>
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public abstract class NodeArtifact extends BoxArtifact {
    LinkedList<NodePartArtifact> nodeParts = new LinkedList<NodePartArtifact>();
    
    static class NodePeer {
	NodeArtifact c1;
	NodeArtifact c2;

	NodePeer(NodeArtifact c1, NodeArtifact c2) {
	    this.c1 = c1;
	    this.c2 = c2;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	    NodePeer cp = (NodePeer) obj;
	    return ((this.c1 == cp.c1) && (this.c2 == cp.c2)) || ((this.c1 == cp.c2) && (this.c2 == cp.c1));
	}
    }
    static final ArrayList<NodePeer> nodeRelations = new ArrayList<NodePeer>();
    
    private int width;



    @Override
    public void edit(final GfxObject editedGfxObject) {
	for(NodePartArtifact nodePart : this.nodeParts) {
	    if (editedGfxObject.equals(nodePart.getGfxObject())) {
		nodePart.edit(editedGfxObject);
		return;
	    }		
	}

	if (editedGfxObject.equals(getGfxObject())) {
	    Log.warn("Selecting a virtual group : this should not happen !");
	    this.nodeParts.peek().edit(editedGfxObject);
	} else {
	    GfxObject gfxObjectGroup = GfxManager.getPlatform().getGroup(editedGfxObject);
	    if (gfxObjectGroup != null) {
		for(NodePartArtifact nodePart : this.nodeParts) {
		    if (gfxObjectGroup.equals(nodePart.getGfxObject())) {
			nodePart.edit(editedGfxObject);
			return;
		    }		
		}
		gfxObjectGroup = GfxManager.getPlatform().getGroup(gfxObjectGroup);
		if (gfxObjectGroup != null) {
		    for(NodePartArtifact nodePart : this.nodeParts) {
			if (gfxObjectGroup.equals(nodePart.getGfxObject())) {
			    nodePart.edit(editedGfxObject);
			    return;
			}		
		    }
		    if (gfxObjectGroup.equals(getGfxObject())) {
			Log.warn("Selecting the master virtual group : this should NOT happen !");
			this.nodeParts.peek().edit(editedGfxObject);
		    } else {
			Log.warn("No editable part found");
		    }
		} else {
		    Log.warn("No editable part found");
		}
	    }
	}
    }



    @Override
    public int getHeight() {
	int height = 0;
	for(NodePartArtifact nodePart : this.nodeParts) {
	    height += nodePart.getHeight();
	}
	return height;
    }



    @Override
    public GfxObject getOutline() {
	if (QualityLevel.IsAlmost(QualityLevel.NORMAL)) {
	    final GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
		final List<Integer> widthList = new ArrayList<Integer>();
		// Computing text bounds :
		for(NodePartArtifact nodePart : this.nodeParts) {
		    
		    nodePart.computeBounds();
		    widthList.add(nodePart.getWidth());
		}

		// Searching largest width :
		final int maxWidth = UMLDrawerHelper.getMaxOf(widthList);
		this.width = maxWidth;	
		int heightDelta = 0;
		for(NodePartArtifact nodePart : this.nodeParts) {		    
		    nodePart.setNodeWidth(maxWidth);
		    GfxObject outline = nodePart.getOutline();
		    GfxManager.getPlatform().addToVirtualGroup(vg, outline);
		    GfxManager.getPlatform().translate(outline, new Point(0,heightDelta));
		    heightDelta += nodePart.getHeight();
		}
	    return vg;
	}
	return super.getOutline();

    }



    @Override
    public int getWidth() {
	return this.width;
    }

    @Override
    public void rebuildGfxObject() {
	for(NodePartArtifact nodePart : this.nodeParts) {
	    GfxManager.getPlatform().clearVirtualGroup(nodePart.getGfxObject());
	}
	GfxManager.getPlatform().clearVirtualGroup(this.gfxObject);
	super.rebuildGfxObject();
    }

    @Override
    protected void select() {
	for(NodePartArtifact nodePart : this.nodeParts) {
	    nodePart.select();
	}
    }

    @Override
    public void setCanvas(final UMLCanvas canvas) {
	this.canvas = canvas;
	for(NodePartArtifact nodePart : this.nodeParts) {
	    nodePart.setCanvas(canvas);
	}
    }

    @Override
    public void unselect() {
	for(NodePartArtifact nodePart : this.nodeParts) {
	    nodePart.unselect();
	}
    }

    @Override
    protected void buildGfxObject() {
	for(NodePartArtifact nodePart : this.nodeParts) {
	    GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, nodePart.initializeGfxObject());
	}
	final List<Integer> widthList = new ArrayList<Integer>();
	// Computing text bounds :
	for(NodePartArtifact nodePart : this.nodeParts) {
	    nodePart.computeBounds();
	    widthList.add(nodePart.getWidth());
	}

	// Searching largest width :
	final int maxWidth = UMLDrawerHelper.getMaxOf(widthList);
	this.width = maxWidth;	
	int heightDelta = 0;
	for(NodePartArtifact nodePart : this.nodeParts) {
	    nodePart.setNodeWidth(maxWidth);
	    GfxManager.getPlatform().translate(nodePart.getGfxObject(), new Point(0,heightDelta));
	    heightDelta += nodePart.getHeight();
	}
    }
    /**
     * Getter for the name
     * 
     * @return the name of this node
     */
    public abstract String getName();
}
