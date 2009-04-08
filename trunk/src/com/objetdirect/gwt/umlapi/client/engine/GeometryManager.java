/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.engine;

/**
 * @author florian
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
