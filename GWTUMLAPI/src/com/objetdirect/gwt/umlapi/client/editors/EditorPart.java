/*
 * This file is part of the GWTUML project and was written by Rapha�l Brugier (raphael-dot-brugier.at.gmail'dot'com) for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2010 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.editors;

/**
 * Interface to edit a part of an umlComponent. The recommended utilisation is to create an anonymous class and passed
 * it to the SimpleFieldEditor.
 * 
 * @author Rapha�l Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public interface EditorPart {

	public String getText();

	public void setText(String newText);
}
