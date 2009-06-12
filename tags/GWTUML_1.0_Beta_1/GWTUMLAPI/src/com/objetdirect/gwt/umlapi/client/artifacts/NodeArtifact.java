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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.GWTUMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.helpers.QualityLevel;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;

/**
 * This class is an artifact used to represent a class. <br>
 * A class is divided in three {@link NodePartArtifact} :
 * <ul>
 * <li>{@link ClassPartNameArtifact} For the name and stereotype part</li>
 * <li>{@link ClassPartAttributesArtifact} For the attribute list part</li>
 * <li>{@link ClassPartMethodsArtifact} For the method list part</li>
 * </ul>
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public abstract class NodeArtifact extends BoxArtifact {

	LinkedList<NodePartArtifact>	nodeParts	= new LinkedList<NodePartArtifact>();
	private int						width;

	/**
	 * Constructor of NodeArtifact
	 * 
	 */
	public NodeArtifact() {
		super(true);
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		for (final NodePartArtifact nodePart : this.nodeParts) {
			if (editedGfxObject.equals(nodePart.getGfxObject())) {
				nodePart.edit(editedGfxObject);
				return;
			}
		}

		if (editedGfxObject.equals(this.getGfxObject())) {
			Log.warn("Selecting a virtual group : this should not happen !");
			this.nodeParts.peek().edit(editedGfxObject);
		} else {
			GfxObject gfxObjectGroup = GfxManager.getPlatform().getGroup(editedGfxObject);
			if (gfxObjectGroup != null) {
				for (final NodePartArtifact nodePart : this.nodeParts) {
					if (gfxObjectGroup.equals(nodePart.getGfxObject())) {
						nodePart.edit(editedGfxObject);
						return;
					}
				}
				gfxObjectGroup = GfxManager.getPlatform().getGroup(gfxObjectGroup);
				if (gfxObjectGroup != null) {
					for (final NodePartArtifact nodePart : this.nodeParts) {
						if (gfxObjectGroup.equals(nodePart.getGfxObject())) {
							nodePart.edit(editedGfxObject);
							return;
						}
					}
					if (gfxObjectGroup.equals(this.getGfxObject())) {
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
		for (final NodePartArtifact nodePart : this.nodeParts) {
			height += nodePart.getHeight();
		}
		return height;
	}

	/**
	 * Getter for the name
	 * 
	 * @return the name of this node
	 */
	public abstract String getName();

	@Override
	public GfxObject getOutline() {
		if (QualityLevel.IsAlmost(QualityLevel.NORMAL)) {
			final GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
			final List<Integer> widthList = new ArrayList<Integer>();
			// Computing text bounds :
			for (final NodePartArtifact nodePart : this.nodeParts) {

				nodePart.computeBounds();
				widthList.add(nodePart.getWidth());
			}

			// Searching largest width :
			final int maxWidth = GWTUMLDrawerHelper.getMaxOf(widthList);
			this.width = maxWidth;
			int heightDelta = 0;
			for (final NodePartArtifact nodePart : this.nodeParts) {
				nodePart.setNodeWidth(maxWidth);
				final GfxObject outline = nodePart.getOutline();
				GfxManager.getPlatform().addToVirtualGroup(vg, outline);
				GfxManager.getPlatform().translate(outline, new Point(0, heightDelta));
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
		for (final NodePartArtifact nodePart : this.nodeParts) {
			GfxManager.getPlatform().clearVirtualGroup(nodePart.getGfxObject());
		}
		GfxManager.getPlatform().clearVirtualGroup(this.gfxObject);
		super.rebuildGfxObject();
	}

	@Override
	public void setCanvas(final UMLCanvas canvas) {
		this.canvas = canvas;
		for (final NodePartArtifact nodePart : this.nodeParts) {
			nodePart.setCanvas(canvas);
		}
	}

	@Override
	public void unselect() {
		super.unselect();
		for (final NodePartArtifact nodePart : this.nodeParts) {
			nodePart.unselect();
		}
	}

	@Override
	protected void buildGfxObject() {
		for (final NodePartArtifact nodePart : this.nodeParts) {
			GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, nodePart.initializeGfxObject());
		}
		final List<Integer> widthList = new ArrayList<Integer>();
		// Computing text bounds :
		for (final NodePartArtifact nodePart : this.nodeParts) {
			nodePart.computeBounds();
			widthList.add(nodePart.getWidth());
		}

		// Searching largest width :
		final int maxWidth = GWTUMLDrawerHelper.getMaxOf(widthList);
		this.width = maxWidth;
		int heightDelta = 0;
		for (final NodePartArtifact nodePart : this.nodeParts) {
			nodePart.setNodeWidth(maxWidth);
			GfxManager.getPlatform().translate(nodePart.getGfxObject(), new Point(0, heightDelta));
			heightDelta += nodePart.getHeight();
		}
	}

	@Override
	protected void select() {
		super.select();
		for (final NodePartArtifact nodePart : this.nodeParts) {
			nodePart.select();
		}
	}
}
