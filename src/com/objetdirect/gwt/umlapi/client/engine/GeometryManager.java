/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.engine;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class GeometryManager {
    private static GeometryPlatform instance;

    public static GeometryPlatform getPlatform() {
	return instance;
    }

    public static void setPlatform(final GeometryPlatform gp) {
	instance = gp;
    }
}
