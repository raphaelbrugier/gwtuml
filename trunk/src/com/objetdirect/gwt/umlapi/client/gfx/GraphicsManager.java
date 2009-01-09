package com.objetdirect.gwt.umlapi.client.gfx;

public class GraphicsManager {
	
	
	private static GraphicsPlatform instance;
	
	public static void setInstance(GraphicsPlatform gp)
	{
		instance = gp;
	}
	public static GraphicsPlatform getInstance()
	{
		return instance;
	}
	
}
