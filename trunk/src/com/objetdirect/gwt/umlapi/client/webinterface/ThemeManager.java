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
 
		NORMAL("Normal", GfxColor.WHITE, GfxColor.BLACK, GfxColor.BLUE),
 
		AERO("Aero", GfxColor.BLUE, GfxColor.NAVY, GfxColor.WHITE), 
 
		DARK("Dark", GfxColor.BLACK, GfxColor.WHITE, GfxColor.GREEN);
 
		private final GfxColor backgroundColor;
 
		private final GfxColor foregroundColor;
 
		private final GfxColor highlightedForegroundColor;
		/**
		 * 
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
			if(opacity != 255) return new GfxColor(this.foregroundColor.getRed(), this.foregroundColor.getGreen(), this.foregroundColor.getBlue(), opacity);
			return this.foregroundColor;
		}
		public GfxColor getThemeHighlightedForegroundColor() {
			return this.highlightedForegroundColor;
		}
		/**
		 * @return
		 * 
		 */
		@Override
		public String toString() {
			return this.themeName;
		}
		  public static Theme getThemeFromName(String themeName) {
		        for (Theme theme : Theme.values()) {
		            if (theme.toString().equalsIgnoreCase(themeName))
		                return theme;
		        }
		        return Theme.NORMAL;
		    }
	};
private static Theme current_theme = Theme.NORMAL;
	private static int opacity = 255;
	public static GfxColor getBackgroundColor() {
		return current_theme.getThemeBackgroundColor();
	}
	public static GfxColor getForegroundColor() {
		return current_theme.getThemeForegroundColor();
	}
	public static GfxColor getHighlightedForegroundColor() {
		return current_theme.getThemeHighlightedForegroundColor();
	}

	public static String getThemeName(Theme theme) {
		return theme.toString();
	}
	public static void setCurrentTheme(Theme theme) {
		current_theme = theme;
	}
	public static void setForegroundOpacityTo(int i) {
		opacity = i;
		
	}
}
