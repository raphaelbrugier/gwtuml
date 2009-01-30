package com.objetdirect.gwt.umlapi.client.webinterface;

import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;

public class ThemeManager {

	public enum Theme {

		NORMAL("Normal", GfxColor.WHITE, GfxColor.BLACK, GfxColor.BLUE), DARK(
				"Dark", GfxColor.BLACK, GfxColor.WHITE, GfxColor.GREEN), AERO(
				"Aero", GfxColor.BLUE, GfxColor.NAVY, GfxColor.WHITE);

		private final String themeName;
		private final GfxColor backgroundColor;
		private final GfxColor foregroundColor;
		private final GfxColor highlightedForegroundColor;

		private Theme(String themeName, GfxColor backgroundColor,
				GfxColor foregroundColor, GfxColor highlightedForegroundColor) {
			this.themeName = themeName;
			this.backgroundColor = backgroundColor;
			this.foregroundColor = foregroundColor;
			this.highlightedForegroundColor = highlightedForegroundColor;
		}

		public String getThemeName() {
			return this.themeName;
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

	};

	private static Theme current_theme = Theme.NORMAL;

	public static String getThemeName(Theme theme) {
		return theme.getThemeName();
	}

	public static Theme getThemeFromName(String themeName) {
		for (Theme theme : Theme.values()) {
			if (theme.getThemeName().equalsIgnoreCase(themeName))
				return theme;
		}
		return Theme.NORMAL;
	}

	public static void setCurrentTheme(Theme theme) {
		current_theme = theme;
	}

	public static GfxColor getBackgroundColor() {
		return current_theme.getThemeBackgroundColor();
	}

	public static GfxColor getForegroundColor() {
		return current_theme.getThemeForegroundColor();
	}

	public static GfxColor getHighlightedForegroundColor() {
		return current_theme.getThemeHighlightedForegroundColor();
	}

}
