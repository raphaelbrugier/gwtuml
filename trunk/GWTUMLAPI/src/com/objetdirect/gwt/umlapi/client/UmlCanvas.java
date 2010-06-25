/*
 * This file is part of the Gwt-Uml project and was written by Raphaël Brugier <raphael dot brugier at gmail dot com > for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2010 Objet Direct
 * 
 * Gwt-Uml is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Gwt-Uml is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Gwt-Generator. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client;

import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLLink.LinkKind;

/**
 * Interface for the UMLCanvas.
 * 
 * @author Raphael Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public interface UmlCanvas {

	public Widget getDrawingCanvas();

	public void addNewClass();

	public void addNewObject();

	public void addNewLifeLine();

	public void addNewNote();

	public void selectAll();

	public void removeSelected();

	public void rebuildAllGFXObjects();

	public void cut();

	public void copy();

	public void paste();

	public void toLinkMode(final LinkKind linkType);

	public void dropContextualMenu(GfxObject gfxObject, Point location);
}
