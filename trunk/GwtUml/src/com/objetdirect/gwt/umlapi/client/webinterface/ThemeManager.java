package com.objetdirect.gwt.umlapi.client.webinterface;

import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;

public class ThemeManager {

	public enum Theme {
		NORMAL("Normal", GfxColor.WHITE, GfxColor.BLACK, GfxColor.BLUE),
		AERO("Aero", GfxColor.BLUE, GfxColor.NAVY, GfxColor.WHITE), 
		DARK("Dark", GfxColor.BLACK, GfxColor.WHITE, GfxColor.GREEN);

		private final GfxColor backgroundColor;
		private final GfxColor foregroundColor;
		private final GfxColor highlightedForegroundColor;
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

		public String getThemeName() {
			return this.themeName;
		}

	};

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
