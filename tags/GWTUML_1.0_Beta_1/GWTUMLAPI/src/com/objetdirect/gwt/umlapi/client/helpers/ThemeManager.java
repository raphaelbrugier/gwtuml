/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.helpers;

import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;

/**
 * This class sets the current theme and give colors corresponding to it
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ThemeManager {
	/**
	 * This enumeration initialize all themes with their colors
	 * 
	 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
	 */
	public enum Theme {
		/**
		 * The normal theme color
		 */
		NORMAL("Normal", 0, new GfxColor(255, 255, 255, 255), // White
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 0, 0, 255), // Black
				new GfxColor(0, 0, 255, 255), // Blue
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 0, 0, 255), // Black
				new GfxColor(0, 0, 255, 255), // Blue
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 0, 0, 255), // Black
				new GfxColor(0, 0, 255, 255), // Blue
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 0, 0, 255), // Black
				new GfxColor(0, 0, 255, 255), // Blue
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 0, 0, 255), // Black
				new GfxColor(0, 0, 255, 255), // Blue
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 0, 0, 255), // Black
				new GfxColor(0, 0, 255, 255), // Blue
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 0, 0, 255), // Black
				new GfxColor(0, 0, 255, 255), // Blue
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 0, 0, 255), // Black
				new GfxColor(0, 0, 255, 255), // Blue
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 0, 0, 255), // Black
				new GfxColor(0, 0, 255, 255), // Blue
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 0, 0, 255), // Black
				new GfxColor(0, 0, 255, 255), // Blue
				new GfxColor(134, 171, 217, 255), // Soft Blue
				new GfxColor(134, 171, 217, 100), // Soft Blue Translucent
				new GfxColor(134, 171, 217, 255), // Soft Blue
				new GfxColor(217, 71, 217, 255)), // Harder Blue/Violet
		/**
		 * Very dark theme color
		 */
		DARK("Dark", 1, new GfxColor(0, 0, 0, 255), // Black
				new GfxColor(25, 25, 25, 255), // Dark grey
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 255, 0, 255), // Green
				new GfxColor(25, 25, 25, 255), // Dark grey
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 255, 0, 255), // Green
				new GfxColor(25, 25, 25, 255), // Dark grey
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 255, 0, 255), // Green
				new GfxColor(25, 25, 25, 255), // Dark grey
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 255, 0, 255), // Green
				new GfxColor(25, 25, 25, 255), // Dark grey
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 255, 0, 255), // Green
				new GfxColor(25, 25, 25, 255), // Dark grey
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 255, 0, 255), // Green
				new GfxColor(25, 25, 25, 255), // Dark grey
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 255, 0, 255), // Green
				new GfxColor(25, 25, 25, 255), // Dark grey
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 255, 0, 255), // Green
				new GfxColor(25, 25, 25, 255), // Dark grey
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 255, 0, 255), // Green
				new GfxColor(25, 25, 25, 255), // Dark grey
				new GfxColor(255, 255, 255, 255), // White
				new GfxColor(0, 255, 0, 255), // Green
				new GfxColor(134, 0, 217, 255), // Soft pink
				new GfxColor(134, 0, 217, 125), // Soft pink Translucent
				new GfxColor(134, 0, 217, 255), // Soft pink
				new GfxColor(245, 0, 217, 125)), // Harder pink
		/**
		 * Clear theme color
		 */
		CLEAR("Clear", 2, new GfxColor("fff"),// Canvas background
				new GfxColor("fff"), // Default background
				new GfxColor("000"), // Default foreground
				new GfxColor("888"), // Default highlight
				new GfxColor("F8D2CE"), // Class background
				new GfxColor("000"), // Class foreground
				new GfxColor("f0f"), // Class highlight
				new GfxColor("F8EBCE"), // Note background
				new GfxColor("000"), // Note foreground
				new GfxColor("f00"), // Note highlight
				new GfxColor("C8F4CF"), // Object background
				new GfxColor("000"), // Object foreground
				new GfxColor("00f"), // Object highlight
				new GfxColor("C8F4CF"), // LifeLine background
				new GfxColor("000"), // LifeLine foreground
				new GfxColor("00f"), // LifeLine highlight
				new GfxColor("fff"), // Instantiation link background
				new GfxColor("000"), // Instantiation link foreground
				new GfxColor("0f0"), // Instantiation link highlight
				new GfxColor("fff"), // Link class background
				new GfxColor("000"), // Link class foreground
				new GfxColor("0f0"), // Link class highlight
				new GfxColor("fff"), // Link Note background
				new GfxColor("000"), // Link Note foreground
				new GfxColor("0f0"), // Link Note highlight
				new GfxColor("fff"), // Class Relation background
				new GfxColor("000"), // Class Relation foreground
				new GfxColor("0f0"), // Class Relation highlight
				new GfxColor("fff"), // Object Relation background
				new GfxColor("000"), // Object Relation foreground
				new GfxColor("0f0"), // Object Relation highlight
				new GfxColor("2D0059"), // SelectBox foreground
				new GfxColor("65428822"),// SelectBox background
				new GfxColor("2D0059"), // DirectionPanel color
				new GfxColor("DD2D59")), // DirectionPanel color pressed

		/**
		 * Pinky theme color
		 */
		PINKY("Pinky", 3, new GfxColor("#C992FE"), new GfxColor("#FC00F1"), new GfxColor("#590055"), new GfxColor("#FE005E"), new GfxColor("#FC00F1"),
				new GfxColor("#590055"), new GfxColor("#FE005E"), new GfxColor("#FC00F1"), new GfxColor("#590055"), new GfxColor("#FE005E"), new GfxColor(
						"#FC00F1"), new GfxColor("#590055"), new GfxColor("#FE005E"), new GfxColor("#FC00F1"), new GfxColor("#590055"),
				new GfxColor("#FE005E"), new GfxColor("#FC00F1"), new GfxColor("#590055"), new GfxColor("#FE005E"), new GfxColor("#FC00F1"), new GfxColor(
						"#590055"), new GfxColor("#FE005E"), new GfxColor("#FC00F1"), new GfxColor("#590055"), new GfxColor("#FE005E"),
				new GfxColor("#FC00F1"), new GfxColor("#590055"), new GfxColor("#FE005E"), new GfxColor("#FC00F1"), new GfxColor("#590055"), new GfxColor(
						"#FE005E"), new GfxColor("#2D0059"), new GfxColor("#65428822"), new GfxColor("#2D0059"), new GfxColor("#DD2D59")),
		/**
		 * Grey-ish theme color
		 */
		GREYISH("Greyish", 4, new GfxColor("fff"), new GfxColor("222"), new GfxColor("999"), new GfxColor("d22"), new GfxColor("222"), new GfxColor("999"),
				new GfxColor("d22"), new GfxColor("222"), new GfxColor("999"), new GfxColor("d22"), new GfxColor("222"), new GfxColor("999"), new GfxColor(
						"d22"), new GfxColor("222"), new GfxColor("999"), new GfxColor("d22"), new GfxColor("222"), new GfxColor("999"), new GfxColor("d22"),
				new GfxColor("222"), new GfxColor("999"), new GfxColor("d22"), new GfxColor("222"), new GfxColor("999"), new GfxColor("d22"), new GfxColor(
						"222"), new GfxColor("999"), new GfxColor("d22"), new GfxColor("222"), new GfxColor("999"), new GfxColor("d22"),
				new GfxColor("2D0059"), new GfxColor("65428822"), new GfxColor("2D0059"), new GfxColor("DD2D59"));
		/**
		 * Getter for the {@link Theme} from his index
		 * 
		 * @param themeIndex
		 *            The index corresponding to a {@link ThemeManager}
		 * @return The {@link Theme} if it has been found the default theme otherwise
		 */
		public static Theme getThemeFromIndex(final int themeIndex) {
			for (final Theme theme : Theme.values()) {
				if (theme.index == themeIndex) {
					return theme;
				}
			}
			return Theme.NORMAL;
		}

		/**
		 * Getter for the {@link Theme} from his String name
		 * 
		 * @param themeName
		 *            The string corresponding to a {@link ThemeManager}
		 * @return The {@link Theme} if it has been found the default theme otherwise
		 */
		public static Theme getThemeFromName(final String themeName) {
			for (final Theme theme : Theme.values()) {
				if (theme.toString().equalsIgnoreCase(themeName)) {
					return theme;
				}
			}
			return Theme.NORMAL;
		}

		private final String	themeName;
		private final GfxColor	canvasColor;
		private final GfxColor	defaultBackgroundColor;
		private final GfxColor	defaultForegroundColor;
		private final GfxColor	defaultHighlightedForegroundColor;
		private final GfxColor	classBackgroundColor;
		private final GfxColor	classForegroundColor;
		private final GfxColor	classHighlightedForegroundColor;
		private final GfxColor	noteBackgroundColor;
		private final GfxColor	noteForegroundColor;
		private final GfxColor	noteHighlightedForegroundColor;
		private final GfxColor	objectBackgroundColor;
		private final GfxColor	objectForegroundColor;
		private final GfxColor	objectHighlightedForegroundColor;
		private final GfxColor	lifeLineBackgroundColor;
		private final GfxColor	lifeLineForegroundColor;
		private final GfxColor	lifeLineHighlightedForegroundColor;
		private final GfxColor	instantiationBackgroundColor;
		private final GfxColor	instantiationForegroundColor;
		private final GfxColor	instantiationHighlightedForegroundColor;
		private final GfxColor	linkClassBackgroundColor;
		private final GfxColor	linkClassForegroundColor;
		private final GfxColor	linkClassHighlightedForegroundColor;
		private final GfxColor	linkNoteBackgroundColor;
		private final GfxColor	linkNoteForegroundColor;
		private final GfxColor	linkNoteHighlightedForegroundColor;
		private final GfxColor	classRelationBackgroundColor;
		private final GfxColor	classRelationForegroundColor;
		private final GfxColor	classRelationHighlightedForegroundColor;
		private final GfxColor	objectRelationBackgroundColor;
		private final GfxColor	objectRelationForegroundColor;
		private final GfxColor	objectRelationHighlightedForegroundColor;
		private final GfxColor	selectBoxForegroundColor;
		private final GfxColor	selectBoxBackgroundColor;
		private final GfxColor	directionPanelColor;
		private final GfxColor	directionPanelPressedColor;
		private final int		index;

		private Theme(final String themeName, final int index, final GfxColor canvasColor, final GfxColor defaultBackgroundColor,
				final GfxColor defaultForegroundColor, final GfxColor defaultHighlightedForegroundColor, final GfxColor classBackgroundColor,
				final GfxColor classForegroundColor, final GfxColor classHighlightedForegroundColor, final GfxColor noteBackgroundColor,
				final GfxColor noteForegroundColor, final GfxColor noteHighlightedForegroundColor, final GfxColor objectBackgroundColor,
				final GfxColor objectForegroundColor, final GfxColor objectHighlightedForegroundColor, final GfxColor lifeLineBackgroundColor,
				final GfxColor lifeLineForegroundColor, final GfxColor lifeLineHighlightedForegroundColor, final GfxColor instantiationBackgroundColor,
				final GfxColor instantiationForegroundColor, final GfxColor instantiationHighlightedForegroundColor, final GfxColor linkClassBackgroundColor,
				final GfxColor linkClassForegroundColor, final GfxColor linkClassHighlightedForegroundColor, final GfxColor linkNoteBackgroundColor,
				final GfxColor linkNoteForegroundColor, final GfxColor linkNoteHighlightedForegroundColor, final GfxColor classRelationBackgroundColor,
				final GfxColor classRelationForegroundColor, final GfxColor classRelationHighlightedForegroundColor,
				final GfxColor objectRelationBackgroundColor, final GfxColor objectRelationForegroundColor,
				final GfxColor objectRelationHighlightedForegroundColor, final GfxColor selectBoxForegroundColor, final GfxColor selectBoxBackgroundColor,
				final GfxColor directionPanelColor, final GfxColor directionPanelPressedColor) {
			this.canvasColor = canvasColor;
			this.themeName = themeName;
			this.index = index;
			this.defaultBackgroundColor = defaultBackgroundColor;
			this.defaultForegroundColor = defaultForegroundColor;
			this.defaultHighlightedForegroundColor = defaultHighlightedForegroundColor;
			this.classBackgroundColor = classBackgroundColor;
			this.classForegroundColor = classForegroundColor;
			this.classHighlightedForegroundColor = classHighlightedForegroundColor;
			this.noteBackgroundColor = noteBackgroundColor;
			this.noteForegroundColor = noteForegroundColor;
			this.noteHighlightedForegroundColor = noteHighlightedForegroundColor;
			this.objectBackgroundColor = objectBackgroundColor;
			this.objectForegroundColor = objectForegroundColor;
			this.objectHighlightedForegroundColor = objectHighlightedForegroundColor;
			this.lifeLineBackgroundColor = lifeLineBackgroundColor;
			this.lifeLineForegroundColor = lifeLineForegroundColor;
			this.lifeLineHighlightedForegroundColor = lifeLineHighlightedForegroundColor;
			this.instantiationBackgroundColor = instantiationBackgroundColor;
			this.instantiationForegroundColor = instantiationForegroundColor;
			this.instantiationHighlightedForegroundColor = instantiationHighlightedForegroundColor;
			this.linkClassBackgroundColor = linkClassBackgroundColor;
			this.linkClassForegroundColor = linkClassForegroundColor;
			this.linkClassHighlightedForegroundColor = linkClassHighlightedForegroundColor;
			this.linkNoteBackgroundColor = linkNoteBackgroundColor;
			this.linkNoteForegroundColor = linkNoteForegroundColor;
			this.linkNoteHighlightedForegroundColor = linkNoteHighlightedForegroundColor;
			this.classRelationBackgroundColor = classRelationBackgroundColor;
			this.classRelationForegroundColor = classRelationForegroundColor;
			this.classRelationHighlightedForegroundColor = classRelationHighlightedForegroundColor;
			this.objectRelationBackgroundColor = objectRelationBackgroundColor;
			this.objectRelationForegroundColor = objectRelationForegroundColor;
			this.objectRelationHighlightedForegroundColor = objectRelationHighlightedForegroundColor;
			this.selectBoxForegroundColor = selectBoxForegroundColor;
			this.selectBoxBackgroundColor = selectBoxBackgroundColor;
			this.directionPanelColor = directionPanelColor;
			this.directionPanelPressedColor = directionPanelPressedColor;
		}

		/**
		 * Getter for the canvas background {@link GfxColor}
		 * 
		 * @return the canvas background {@link GfxColor}
		 */
		public GfxColor getCanvasColor() {
			return this.canvasColor;
		}

		/**
		 * Getter for the classBackgroundColor
		 * 
		 * @return the classBackgroundColor
		 */
		public GfxColor getClassBackgroundColor() {
			return this.classBackgroundColor;
		}

		/**
		 * Getter for the classForegroundColor
		 * 
		 * @return the classForegroundColor
		 */
		public GfxColor getClassForegroundColor() {
			return this.classForegroundColor;
		}

		/**
		 * Getter for the classHighlightedForegroundColor
		 * 
		 * @return the classHighlightedForegroundColor
		 */
		public GfxColor getClassHighlightedForegroundColor() {
			return this.classHighlightedForegroundColor;
		}

		/**
		 * Getter for the classClassRelationBackgroundColor
		 * 
		 * @return the classClassRelationBackgroundColor
		 */
		public GfxColor getClassRelationBackgroundColor() {
			return this.classRelationBackgroundColor;
		}

		/**
		 * Getter for the classClassRelationForegroundColor
		 * 
		 * @return the classClassRelationForegroundColor
		 */
		public GfxColor getClassRelationForegroundColor() {
			return this.classRelationForegroundColor;
		}

		/**
		 * Getter for the classClassRelationHighlightedForegroundColor
		 * 
		 * @return the classClassRelationHighlightedForegroundColor
		 */
		public GfxColor getClassRelationHighlightedForegroundColor() {
			return this.classRelationHighlightedForegroundColor;
		}

		/**
		 * Getter for the defaultBackgroundColor
		 * 
		 * @return the defaultBackgroundColor
		 */
		public GfxColor getDefaultBackgroundColor() {
			return this.defaultBackgroundColor;
		}

		/**
		 * Getter for the defaultForegroundColor
		 * 
		 * @return the defaultForegroundColor
		 */
		public GfxColor getDefaultForegroundColor() {
			return this.defaultForegroundColor;
		}

		/**
		 * Getter for the defaultHighlightedForegroundColor
		 * 
		 * @return the defaultHighlightedForegroundColor
		 */
		public GfxColor getDefaultHighlightedForegroundColor() {
			return this.defaultHighlightedForegroundColor;
		}

		/**
		 * Getter for the directionPanelColor
		 * 
		 * @return the directionPanelColor
		 */
		public GfxColor getDirectionPanelColor() {
			return this.directionPanelColor;
		}

		/**
		 * Getter for the directionPanelPressedColor
		 * 
		 * @return the directionPanelPressedColor
		 */
		public GfxColor getDirectionPanelPressedColor() {
			return this.directionPanelPressedColor;
		}

		/**
		 * Getter for the index
		 * 
		 * @return the index
		 */
		public int getIndex() {
			return this.index;
		}

		/**
		 * Getter for the instantiationBackgroundColor
		 * 
		 * @return the instantiationBackgroundColor
		 */
		public GfxColor getInstantiationBackgroundColor() {
			return this.instantiationBackgroundColor;
		}

		/**
		 * Getter for the instantiationForegroundColor
		 * 
		 * @return the instantiationForegroundColor
		 */
		public GfxColor getInstantiationForegroundColor() {
			return this.instantiationForegroundColor;
		}

		/**
		 * Getter for the instantiationHighlightedForegroundColor
		 * 
		 * @return the instantiationHighlightedForegroundColor
		 */
		public GfxColor getInstantiationHighlightedForegroundColor() {
			return this.instantiationHighlightedForegroundColor;
		}

		/**
		 * Getter for the lifeLineBackgroundColor
		 * 
		 * @return the lifeLineBackgroundColor
		 */
		public GfxColor getLifeLineBackgroundColor() {
			return this.lifeLineBackgroundColor;
		}

		/**
		 * Getter for the lifeLineForegroundColor
		 * 
		 * @return the lifeLineForegroundColor
		 */
		public GfxColor getLifeLineForegroundColor() {
			return this.lifeLineForegroundColor;
		}

		/**
		 * Getter for the lifeLineHighlightedForegroundColor
		 * 
		 * @return the lifeLineHighlightedForegroundColor
		 */
		public GfxColor getLifeLineHighlightedForegroundColor() {
			return this.lifeLineHighlightedForegroundColor;
		}

		/**
		 * Getter for the linkClassBackgroundColor
		 * 
		 * @return the linkClassBackgroundColor
		 */
		public GfxColor getLinkClassBackgroundColor() {
			return this.linkClassBackgroundColor;
		}

		/**
		 * Getter for the linkClassForegroundColor
		 * 
		 * @return the linkClassForegroundColor
		 */
		public GfxColor getLinkClassForegroundColor() {
			return this.linkClassForegroundColor;
		}

		/**
		 * Getter for the linkClassHighlightedForegroundColor
		 * 
		 * @return the linkClassHighlightedForegroundColor
		 */
		public GfxColor getLinkClassHighlightedForegroundColor() {
			return this.linkClassHighlightedForegroundColor;
		}

		/**
		 * Getter for the linkNoteBackgroundColor
		 * 
		 * @return the linkNoteBackgroundColor
		 */
		public GfxColor getLinkNoteBackgroundColor() {
			return this.linkNoteBackgroundColor;
		}

		/**
		 * Getter for the linkNoteForegroundColor
		 * 
		 * @return the linkNoteForegroundColor
		 */
		public GfxColor getLinkNoteForegroundColor() {
			return this.linkNoteForegroundColor;
		}

		/**
		 * Getter for the linkNoteHighlightedForegroundColor
		 * 
		 * @return the linkNoteHighlightedForegroundColor
		 */
		public GfxColor getLinkNoteHighlightedForegroundColor() {
			return this.linkNoteHighlightedForegroundColor;
		}

		/**
		 * Getter for the noteBackgroundColor
		 * 
		 * @return the noteBackgroundColor
		 */
		public GfxColor getNoteBackgroundColor() {
			return this.noteBackgroundColor;
		}

		/**
		 * Getter for the noteForegroundColor
		 * 
		 * @return the noteForegroundColor
		 */
		public GfxColor getNoteForegroundColor() {
			return this.noteForegroundColor;
		}

		/**
		 * Getter for the noteHighlightedForegroundColor
		 * 
		 * @return the noteHighlightedForegroundColor
		 */
		public GfxColor getNoteHighlightedForegroundColor() {
			return this.noteHighlightedForegroundColor;
		}

		/**
		 * Getter for the objectBackgroundColor
		 * 
		 * @return the objectBackgroundColor
		 */
		public GfxColor getObjectBackgroundColor() {
			return this.objectBackgroundColor;
		}

		/**
		 * Getter for the objectForegroundColor
		 * 
		 * @return the objectForegroundColor
		 */
		public GfxColor getObjectForegroundColor() {
			return this.objectForegroundColor;
		}

		/**
		 * Getter for the objectHighlightedForegroundColor
		 * 
		 * @return the objectHighlightedForegroundColor
		 */
		public GfxColor getObjectHighlightedForegroundColor() {
			return this.objectHighlightedForegroundColor;
		}

		/**
		 * Getter for the objectRelationBackgroundColor
		 * 
		 * @return the objectRelationBackgroundColor
		 */
		public GfxColor getObjectRelationBackgroundColor() {
			return this.objectRelationBackgroundColor;
		}

		/**
		 * Getter for the objectRelationForegroundColor
		 * 
		 * @return the objectRelationForegroundColor
		 */
		public GfxColor getObjectRelationForegroundColor() {
			return this.objectRelationForegroundColor;
		}

		/**
		 * Getter for the objectRelationHighlightedForegroundColor
		 * 
		 * @return the objectRelationHighlightedForegroundColor
		 */
		public GfxColor getObjectRelationHighlightedForegroundColor() {
			return this.objectRelationHighlightedForegroundColor;
		}

		/**
		 * Getter for the select box background {@link GfxColor}
		 * 
		 * @return the select box background {@link GfxColor}
		 */
		public GfxColor getSelectBoxBackgroundColor() {
			return this.selectBoxBackgroundColor;
		}

		/**
		 * Getter for the select box foreground {@link GfxColor}
		 * 
		 * @return the select box foreground {@link GfxColor}
		 */
		public GfxColor getSelectBoxForegroundColor() {
			return this.selectBoxForegroundColor;
		}

		/**
		 * Getter for the themeName
		 * 
		 * @return the themeName
		 */
		public String getThemeName() {
			return this.themeName;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return this.themeName;
		}
	}

	private static Theme	current_theme	= Theme.NORMAL;

	/**
	 * Getter for the current {@link Theme}
	 * 
	 * @return the current {@link Theme}
	 */
	public static Theme getTheme() {
		return ThemeManager.current_theme;
	}

	/**
	 * Getter for a theme name from a {@link Theme}
	 * 
	 * @param theme
	 *            The theme to get the name
	 * @return The {@link Theme} name of theme
	 */
	public static String getThemeName(final Theme theme) {
		return theme.toString();
	}

	/**
	 * Setter for the active {@link Theme}
	 * 
	 * @param theme
	 *            The theme to sets current
	 */
	public static void setCurrentTheme(final Theme theme) {
		if (theme == null) {
			ThemeManager.current_theme = Theme.NORMAL;
		} else {
			ThemeManager.current_theme = theme;
		}
	}
}
