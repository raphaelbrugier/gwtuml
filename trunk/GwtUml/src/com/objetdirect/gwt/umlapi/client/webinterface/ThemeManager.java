package com.objetdirect.gwt.umlapi.client.webinterface;

import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;

/**
 * @author  florian
 */
public class ThemeManager {

	/**
	 * @author   florian
	 */
	public enum Theme {
		/**
		 * @uml.property  name="nORMAL"
		 * @uml.associationEnd  
		 */
		NORMAL("Normal", GfxColor.WHITE, GfxColor.BLACK, GfxColor.BLUE),
		/**
		 * @uml.property  name="aERO"
		 * @uml.associationEnd  
		 */
		AERO("Aero", GfxColor.BLUE, GfxColor.NAVY, GfxColor.WHITE), 
		/**
		 * @uml.property  name="dARK"
		 * @uml.associationEnd  
		 */
		DARK("Dark", GfxColor.BLACK, GfxColor.WHITE, GfxColor.GREEN);

		/**
		 * @uml.property  name="backgroundColor"
		 * @uml.associationEnd  
		 */
		private final GfxColor backgroundColor;
		/**
		 * @uml.property  name="foregroundColor"
		 * @uml.associationEnd  
		 */
		private final GfxColor foregroundColor;
		/**
		 * @uml.property  name="highlightedForegroundColor"
		 * @uml.associationEnd  
		 */
		private final GfxColor highlightedForegroundColor;
		/**
		 * @uml.property  name="themeName"
		 */
		private final String themeName;

		private Theme(String themeName, GfxColor backgroundColor,
				GfxColor foregroundColor, GfxColor highlightedForegroundColor) {
			this.themeName = themeName;
			this.backgroundColor = backgroundColor;
			this.foregroundColor = foregroundColor;
			this.highlightedForegroundColor = highlightedForegroundColor;
		}

		public GfxColor getThemeBackgroundColor() {
			return this.backgroundColor;
		}

		public GfxColor getThemeForegroundColor() {
			return this.foregroundColor;
		}

		public GfxColor getThemeHighlightedForegroundColor() {
			return this.highlightedForegroundColor;
		}

		/**
		 * @return
		 * @uml.property  name="themeName"
		 */
		public String getThemeName() {
			return this.themeName;
		}

	};

	/**
	 * @uml.property  name="current_theme"
	 * @uml.associationEnd  
	 */
	private static Theme current_theme = Theme.NORMAL;

	public static GfxColor getBackgroundColor() {
		return current_theme.getThemeBackgroundColor();
	}

	public static GfxColor getForegroundColor() {
		return current_theme.getThemeForegroundColor();
	}

	public static GfxColor getHighlightedForegroundColor() {
		return current_theme.getThemeHighlightedForegroundColor();
	}

	public static Theme getThemeFromName(String themeName) {
		for (Theme theme : Theme.values()) {
			if (theme.getThemeName().equalsIgnoreCase(themeName))
				return theme;
		}
		return Theme.NORMAL;
	}

	public static String getThemeName(Theme theme) {
		return theme.getThemeName();
	}

	public static void setCurrentTheme(Theme theme) {
		current_theme = theme;
	}

}
