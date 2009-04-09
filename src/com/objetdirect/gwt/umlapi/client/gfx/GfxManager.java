package com.objetdirect.gwt.umlapi.client.gfx;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class GfxManager {
    private static GfxPlatform instance;

    public static GfxPlatform getPlatform() {
	return instance;
    }

    public static void setPlatform(final GfxPlatform gp) {
	instance = gp;
    }
}
