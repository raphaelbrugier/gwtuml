package com.objetdirect.gwt.umlapi.client.gfx;

public class GfxManager {

	private static GfxPlatform instance;

	public static void setPlatform(GfxPlatform gp) {
		instance = gp;
	}

	public static GfxPlatform getPlatform() {
		return instance;
	}

}
