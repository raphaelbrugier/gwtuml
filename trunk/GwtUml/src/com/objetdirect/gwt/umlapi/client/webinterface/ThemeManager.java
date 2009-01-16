package com.objetdirect.gwt.umlapi.client.webinterface;

import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;

public class ThemeManager {

	public static enum Theme {NORMAL, DARK, AERO };
	
	private static Theme current_theme = Theme.NORMAL;
	
	public static String getThemeName(Theme theme) {
		switch(theme) {
		case NORMAL:	return "Normal";
		case DARK:		return "Dark";	
		case AERO:		return "Aero";		
		}
		return "Don't exist";
	}
	
	public static Theme getThemeFromName(String theme) {
		if(theme == "Normal")	return Theme.NORMAL;
		if(theme == "Dark")		return Theme.DARK;	
		if(theme == "Aero")		return Theme.AERO;		
		return Theme.NORMAL;
	}
	
	public static void setCurrentTheme(Theme theme) {
		current_theme = theme;
	}
	
	
	public static GfxColor getBackgroundColor()	{
		
		switch(current_theme) {
		case NORMAL:	return GfxColor.WHITE;
		case DARK:		return GfxColor.BLACK;	
		case AERO:		return GfxColor.BLUE;		
		}
		return GfxColor.WHITE;
	}
	public static GfxColor getForegroundColor()	{
		
		switch(current_theme) {
		case NORMAL:	return GfxColor.BLACK;
		case DARK:		return GfxColor.WHITE;	
		case AERO:		return GfxColor.NAVY;		
		}
		return GfxColor.BLACK;
	}
	public static GfxColor getHighlightedForegroundColor()	{
		
		switch(current_theme) {
		case NORMAL:	return GfxColor.BLUE;
		case DARK:		return GfxColor.GREEN;	
		case AERO:		return GfxColor.WHITE;		
		}
		return GfxColor.BLUE;
	}
	
}
