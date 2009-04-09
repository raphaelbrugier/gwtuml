/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.engine;

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
     * @param geometryPlatform The current {@link GeometryPlatform} instance to be set
     */
    public static void setPlatform(final GeometryPlatform geometryPlatform) {
	instance = geometryPlatform;
    }
}
