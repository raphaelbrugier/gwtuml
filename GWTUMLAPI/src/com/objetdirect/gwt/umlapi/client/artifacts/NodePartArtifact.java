/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas;

/**
 * This abstract class represent a part of a {@link NodeArtifact}
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public abstract class NodePartArtifact extends BoxArtifact {

	protected transient GfxObject textVirtualGroup;

	/**
	 * /!\ Don't forget to increment the serialVersionUID if you change any of the fields above /!\
	 */
	private static final long serialVersionUID = 1L;

	protected NodeArtifact nodeArtifact;
	protected int nodeWidth;
	protected int height;
	protected int width;

	/** Default constructor ONLY for gwt-rpc serialization. */
	@Deprecated
	protected NodePartArtifact() {
	}

	/**
	 * Constructor of NodePartArtifact
	 * 
	 * @param canvas
	 *            Where the gfxObject are displayed
	 */
	public NodePartArtifact(final UMLCanvas canvas) {
		super(canvas);
	}

	@Override
	public void buildGfxObjectWithAnimation() {
		this.buildGfxObject();
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
		return nodeArtifact;
	}

	@Override
	public boolean isDraggable() {
		return false;
	}

	/**
	 * This method creates the graphical object for the text to determine the size of this part
	 */
	public abstract void computeBounds();

	/**
	 * Setter for the nodeArtifact
	 * 
	 * @param nodeArtifact
	 *            the nodeArtifact to set
	 */
	public void setNodeArtifact(final NodeArtifact nodeArtifact) {
		this.nodeArtifact = nodeArtifact;
	}

	public abstract void setNodeWidth(int width);
}
