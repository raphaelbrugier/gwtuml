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

import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;

/**
 * This abstract class specialize an {@link UMLArtifact} in a box shape based artifact
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public abstract class BoxArtifact extends UMLArtifact {

	/**
	 * Constructor of BoxArtifact
	 * 
	 * @param toBeAdded
	 *            True if the artifact must be added in artifact list
	 */
	public BoxArtifact(final boolean toBeAdded) {
		super(toBeAdded);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#getOpaque()
	 */
	@Override
	public int[] getOpaque() {
		final int[] opaque = new int[] { this.getLocation().getX(), this.getLocation().getY(), this.getLocation().getX(),
				this.getLocation().getY() + this.getHeight(), this.getLocation().getX() + this.getWidth(), this.getLocation().getY() + this.getHeight(),
				this.getLocation().getX() + this.getWidth(), this.getLocation().getY() };
		return opaque;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#getOutline()
	 */
	@Override
	public GfxObject getOutline() {
		final GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
		final GfxObject rect = GfxManager.getPlatform().buildRect(this.getWidth(), this.getHeight());
		GfxManager.getPlatform().setStrokeStyle(rect, GfxStyle.DASH);
		GfxManager.getPlatform().setStroke(rect, ThemeManager.getTheme().getDefaultHighlightedForegroundColor(), 1);
		GfxManager.getPlatform().setFillColor(rect, ThemeManager.getTheme().getDefaultBackgroundColor());
		GfxManager.getPlatform().addToVirtualGroup(vg, rect);
		return vg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#isALink()
	 */
	@Override
	public boolean isALink() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#isDraggable()
	 */
	@Override
	public boolean isDraggable() {
		return true;
	}
}
