package com.objetdirect.gwt.umlapi.client.gfx;
/**
 * @author  florian
 */
public class GfxManager {
private static GfxPlatform instance;
	public static GfxPlatform getPlatform() {
		return instance;
	}
	public static void setPlatform(GfxPlatform gp) {
		instance = gp;
	}
}