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
package com.objetdirect.gwt.umlapi.client;

/**
 * This class defines specific application exception
 * 
 * @author Henri Darmet
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class GWTUMLAPIException extends RuntimeException {
	private static final long	serialVersionUID	= 1L;

	/**
	 * Constructor of the exception
	 * 
	 * @param msg
	 *            The String exception
	 */
	public GWTUMLAPIException(final String msg) {
		super(msg);
	}
}
