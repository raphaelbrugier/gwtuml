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
package com.objetdirect.gwt.umlapi.client.gfx;

/**
 * This class permits to load one implementation of the graphic system and use it
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class GfxManager {
	private static GfxPlatform instance;

	/**
	 * Getter of the current {@link GfxPlatform} instance
	 * 
	 * @return The {@link GfxPlatform} instance set by {@link GfxManager#setPlatform(GfxPlatform)}
	 */
	public static GfxPlatform getPlatform() {
		if (instance == null) {
			setPlatform(0); // Default to tatami, btw tatami platform is the only working/maintained platform.
		}
		return GfxManager.instance;
	}

	/**
	 * Setter of the current {@link GfxPlatform} instance
	 * 
	 * @param gfxPlateform
	 *            The current {@link GfxPlatform} instance to be set
	 */
	public static void setPlatform(final GfxPlatform gfxPlateform) {
		GfxManager.instance = gfxPlateform;
	}

	/**
	 * Set the current {@link GfxPlatform} from its index
	 * 
	 * @param platformIndex
	 *            The platform index of the new one
	 */
	public static void setPlatform(final int platformIndex) {
		// FIXME: Find a better way
		switch (platformIndex) {
			case 0:
				GfxManager.setPlatform(new TatamiGfxPlatform());
				break;
			case 1:
				GfxManager.setPlatform(new IncubatorGfxPlatform());
				break;
			default:
				GfxManager.setPlatform(new TatamiGfxPlatform());
		}
	}
}
