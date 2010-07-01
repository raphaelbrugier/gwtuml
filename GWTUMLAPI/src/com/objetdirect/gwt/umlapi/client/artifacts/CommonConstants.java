/*
 * This file is part of the GWTUML project and was written by Raphaël Brugier (raphael-dot-brugier.at.gmail'dot'com) for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright Â© 2010 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;

/**
 * Just an interface to access easily to static constants used in the artifacts.
 * 
 * @author Raphaël Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public interface CommonConstants {

	static int REFLEXIVE_PATH_X_GAP = OptionsManager.get("ReflexivePathXGap");

	static int REFLEXIVE_PATH_Y_GAP = OptionsManager.get("ReflexivePathYGap");

	static int TEXT_TOP_PADDING = OptionsManager.get("TextTopPadding");

	static int TEXT_BOTTOM_PADDING = OptionsManager.get("TextBottomPadding");

	static int TEXT_LEFT_PADDING = OptionsManager.get("TextLeftPadding");

	static int TEXT_RIGHT_PADDING = OptionsManager.get("TextRightPadding");

	static int RECTANGLE_LEFT_PADDING = OptionsManager.get("RectangleLeftPadding");

	static int RECTANGLE_RIGHT_PADDING = OptionsManager.get("RectangleRightPadding");

	static int RECTANGLE_TOP_PADDING = OptionsManager.get("RectangleTopPadding");

	static int RECTANGLE_BOTTOM_PADDING = OptionsManager.get("RectangleBottomPadding");

	static int NOTE_CORNER_HEIGHT = OptionsManager.get("NoteCornerHeight");

	static int NOTE_CORNER_WIDTH = OptionsManager.get("NoteCornerWidth");
}
