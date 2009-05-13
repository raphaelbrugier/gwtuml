/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.engine;

import com.objetdirect.gwt.umlapi.client.gfx.GWTCanvasGfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.IncubatorGfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.TatamiGfxPlatfrom;

/**
 * This class permits to load one implementation of the geometry system and use it
 *  
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class GeometryManager {
    private static GeometryPlatform instance;

    /**
     * Getter of the current {@link GeometryPlatform} instance
     * 
     * @return The {@link GeometryPlatform} instance set by {@link GeometryManager#setPlatform(GeometryPlatform)}
     */
    public static GeometryPlatform getPlatform() {
	return instance;
    }

    /**
     * Setter of the current {@link GeometryPlatform} instance
     * 
     * @param geometryPlatform The current {@link GeometryPlatform} instance to be set
     */
    public static void setPlatform(final GeometryPlatform geometryPlatform) {
	instance = geometryPlatform;
    }
    /**
     * Set the current {@link GeometryPlatform} from its index 
     * 
     * @param platformIndex The platform index of the new one
     */
    public static void setPlatform(int platformIndex) {
	//FIXME: Find a better way
	switch(platformIndex) {
	case 0:
	    setPlatform(new LinearGeometry());
	    break;
	case 1:
	    setPlatform(new ShapeGeometry());
	    break;
	default:
	    setPlatform(new LinearGeometry());
	}
    }
}
