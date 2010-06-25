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
package com.objetdirect.gwt.umlapi.client.umlcomponents;

import java.io.Serializable;

import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkAdornment;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkStyle;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public abstract class UMLLink implements Serializable {
	/**
	 * This enumeration lists all the relations type between two {@link UMLClass}es
	 * 
	 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
	 * 
	 */
	public enum LinkKind {
		//
		// CLASS TO CLASS RELATIONS
		//
		/**
		 * Aggregation relation
		 */
		AGGREGATION_RELATION("Aggregation", LinkAdornment.SOLID_DIAMOND, LinkAdornment.WIRE_ARROW, "1", "0..*", LinkStyle.SOLID),
		/**
		 * Association relation
		 */
		ASSOCIATION_RELATION("Association", LinkAdornment.NONE, LinkAdornment.NONE, "1", "1", LinkStyle.SOLID),
		/**
		 * Composition relation
		 */
		COMPOSITION_RELATION("Composition", LinkAdornment.INVERTED_SOLID_DIAMOND, LinkAdornment.WIRE_ARROW, "", "1", LinkStyle.SOLID),
		/**
		 * Dependency relation
		 */
		DEPENDENCY_RELATION("Dependency", LinkAdornment.WIRE_ARROW, LinkAdornment.WIRE_CROSS, "", "", LinkStyle.DASHED),
		/**
		 * Generalization relation
		 */
		GENERALIZATION_RELATION("Generalization", LinkAdornment.SOLID_ARROW, LinkAdornment.NONE, "", "", LinkStyle.SOLID),

		//
		// OBJECT TO CLASS
		//
		/**
		 * Class Object instantiation
		 */
		INSTANTIATION("Instantiation", LinkAdornment.WIRE_ARROW, LinkAdornment.NONE, "", "", LinkStyle.DASHED_DOTTED),

		//
		// LIFE LINE TO LIFE LINE
		//
		/**
		 * Asynchronous message
		 */
		ASYNCHRONOUS_MESSAGE("Asynchronous", LinkAdornment.WIRE_ARROW, LinkAdornment.NONE, "", "", LinkStyle.SOLID),
		/**
		 * Synchronous message
		 */
		SYNCHRONOUS_MESSAGE("Synchronous", LinkAdornment.INVERTED_SOLID_ARROW, LinkAdornment.NONE, "", "", LinkStyle.SOLID),
		/**
		 * Reply message
		 */
		REPLY_MESSAGE("Reply", LinkAdornment.NONE, LinkAdornment.NONE, "", "", LinkStyle.DASHED),
		/**
		 * Object Creation message
		 */
		OBJECT_CREATION_MESSAGE("Object Creation", LinkAdornment.WIRE_ARROW, LinkAdornment.NONE, "", "", LinkStyle.DASHED),
		/**
		 * Lost message
		 */
		LOST_MESSAGE("Lost", LinkAdornment.INVERTED_SOLID_CIRCLE, LinkAdornment.NONE, "", "", LinkStyle.SOLID),
		/**
		 * Found message
		 */
		FOUND_MESSAGE("Found", LinkAdornment.WIRE_ARROW, LinkAdornment.INVERTED_SOLID_CIRCLE, "", "", LinkStyle.SOLID),

		//
		// NOTE TO ANY UMLCOMPONENT
		//
		/**
		 * Note relation
		 */
		NOTE("Note link", LinkAdornment.NONE, LinkAdornment.NONE, "", "", LinkStyle.SOLID),

		//
		// CLASS TO ANY CLASS-TO-CLASS RELATION
		// 
		/**
		 * Class relation
		 */
		CLASSRELATION("Class Relation", LinkAdornment.NONE, LinkAdornment.NONE, "", "", LinkStyle.SOLID);

		/**
		 * Static getter of a {@link LinkKind} by its name
		 * 
		 * @param relationKindName
		 *            The name of the {@link LinkKind} to retrieve
		 * @return The {@link LinkKind} that has relationKindName for name or null if not found
		 */
		public static LinkKind getRelationKindFromName(final String relationKindName) {
			for (final LinkKind relationKind : LinkKind.values()) {
				if (relationKind.getName().equals(relationKindName)) {
					return relationKind;
				}
			}
			return null;
		}

		private String name;
		private LinkAdornment defaultLeftAdornment;
		private LinkAdornment defaultRightAdornment;
		private String defaultLeftCardinality;
		private String defaultRightCardinality;
		private LinkStyle defaultLinkStyle;

		/**
		 * Default constructor ONLY for gwt-rpc serialization
		 */
		private LinkKind() {
		}

		private LinkKind(final String name, final LinkAdornment defaultLeftAdornment, final LinkAdornment defaultRightAdornment,
				final String defaultLeftCardinality, final String defaultRightCardinality, final LinkStyle defaultLinkStyle) {

			this.name = name;
			this.defaultLeftAdornment = defaultLeftAdornment;
			this.defaultRightAdornment = defaultRightAdornment;
			this.defaultLeftCardinality = defaultLeftCardinality;
			this.defaultRightCardinality = defaultRightCardinality;
			this.defaultLinkStyle = defaultLinkStyle;
		}

		/**
		 * Getter for the defaultLeftAdornment
		 * 
		 * @return the defaultLeftAdornment
		 */
		public LinkAdornment getDefaultLeftAdornment() {
			return defaultLeftAdornment;
		}

		/**
		 * Getter for the defaultLeftCardinality
		 * 
		 * @return the defaultLeftCardinality
		 */
		public String getDefaultLeftCardinality() {
			return defaultLeftCardinality;
		}

		/**
		 * Getter for the defaultLinkStyle
		 * 
		 * @return the defaultLinkStyle
		 */
		public LinkStyle getDefaultLinkStyle() {
			return defaultLinkStyle;
		}

		/**
		 * Getter for the defaultRightAdornment
		 * 
		 * @return the defaultRightAdornment
		 */
		public LinkAdornment getDefaultRightAdornment() {
			return defaultRightAdornment;
		}

		/**
		 * Getter for the defaultRightCardinality
		 * 
		 * @return the defaultRightCardinality
		 */
		public String getDefaultRightCardinality() {
			return defaultRightCardinality;
		}

		/**
		 * Getter for the name
		 * 
		 * @return the name
		 */
		public String getName() {
			return name;
		}
	}

	protected LinkKind linkKind;

	/**
	 * Default constructor ONLY for gwt-rpc serialization
	 */
	public UMLLink() {
	}

	/**
	 * Constructor of UMLLink
	 * 
	 * @param linkKind
	 *            The {@link LinkKind} of the {@link UMLLink}
	 */
	public UMLLink(final LinkKind linkKind) {
		this.linkKind = linkKind;
	}

	/**
	 * Getter for the linkKind
	 * 
	 * @return the linkKind
	 */
	public LinkKind getLinkKind() {
		return linkKind;
	}

	/**
	 * Setter for the linkKind
	 * 
	 * @param linkKind
	 *            the linkKind to set
	 */
	public void setLinkKind(final LinkKind linkKind) {
		this.linkKind = linkKind;
	}

}
