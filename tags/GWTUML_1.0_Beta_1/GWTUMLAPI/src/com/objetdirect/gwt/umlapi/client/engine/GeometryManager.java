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
package com.objetdirect.gwt.umlapi.client.engine;

/**
 * This class permits to load one implementation of the geometry system and use it
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class GeometryManager {
	private static GeometryPlatform	instance;

	/**
	 * Getter of the current {@link GeometryPlatform} instance
	 * 
	 * @return The {@link GeometryPlatform} instance set by {@link GeometryManager#setPlatform(GeometryPlatform)}
	 */
	public static GeometryPlatform getPlatform() {
		return GeometryManager.instance;
	}

	/**
	 * Setter of the current {@link GeometryPlatform} instance
	 * 
	 * @param geometryPlatform
	 *            The current {@link GeometryPlatform} instance to be set
	 */
	public static void setPlatform(final GeometryPlatform geometryPlatform) {
		GeometryManager.instance = geometryPlatform;
	}

	/**
	 * Set the current {@link GeometryPlatform} from its index
	 * 
	 * @param platformIndex
	 *            The platform index of the new one
	 */
	public static void setPlatform(final int platformIndex) {
		// FIXME: Find a better way
		switch (platformIndex) {
			case 0:
				GeometryManager.setPlatform(new LinearGeometry());
				break;
			case 1:
				GeometryManager.setPlatform(new ShapeGeometry());
				break;
			default:
				GeometryManager.setPlatform(new LinearGeometry());
		}
	}
}
