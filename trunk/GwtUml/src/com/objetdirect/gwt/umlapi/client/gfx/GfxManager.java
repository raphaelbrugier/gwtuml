package com.objetdirect.gwt.umlapi.client.gfx;

public class GfxManager {
	
	
	private static GfxPlatform instance;
	
	public static void setInstance(GfxPlatform gp)
	{
		instance = gp;
	}
	public static GfxPlatform getInstance()
	{
		return instance;
	}
	
}
