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
 * 
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
		/**
		 * Aggregation relation
		 */
		AGGREGATION_RELATION("Aggregation", LinkAdornment.SOLID_DIAMOND, LinkAdornment.WIRE_ARROW, "1", "0..*", LinkStyle.SOLID, DiagramType.HYBRID),
		/**
		 * Association relation
		 */
		ASSOCIATION_RELATION("Association", LinkAdornment.NONE, LinkAdornment.NONE, "1", "1", LinkStyle.SOLID, DiagramType.HYBRID),
		/**
		 * Composition relation
		 */
		COMPOSITION_RELATION("Composition", LinkAdornment.INVERTED_SOLID_DIAMOND, LinkAdornment.WIRE_ARROW, "", "1", LinkStyle.SOLID, DiagramType.HYBRID),
		/**
		 * Dependency relation
		 */
		DEPENDENCY_RELATION("Dependency", LinkAdornment.WIRE_ARROW, LinkAdornment.WIRE_CROSS, "", "", LinkStyle.DASHED, DiagramType.HYBRID),
		/**
		 * Generalization relation
		 */
		GENERALIZATION_RELATION("Generalization", LinkAdornment.SOLID_ARROW, LinkAdornment.NONE, "", "", LinkStyle.SOLID, DiagramType.CLASS),
		/**
		 * Realization relation
		 */
		REALIZATION_RELATION("Realization", LinkAdornment.SOLID_ARROW, LinkAdornment.NONE, "", "", LinkStyle.LONG_DASHED, DiagramType.CLASS),
		/**
		 * Asynchronous message
		 */
		ASYNCHRONOUS_MESSAGE("Asynchronous", LinkAdornment.WIRE_ARROW, LinkAdornment.NONE, "", "", LinkStyle.SOLID, DiagramType.SEQUENCE),
		/**
		 * Synchronous message
		 */
		SYNCHRONOUS_MESSAGE("Synchronous", LinkAdornment.INVERTED_SOLID_ARROW, LinkAdornment.NONE, "", "", LinkStyle.SOLID, DiagramType.SEQUENCE),
		/**
		 * Reply message
		 */
		REPLY_MESSAGE("Reply", LinkAdornment.NONE, LinkAdornment.NONE, "", "", LinkStyle.DASHED, DiagramType.SEQUENCE),
		/**
		 * Object Creation message
		 */
		OBJECT_CREATION_MESSAGE("Object Creation", LinkAdornment.WIRE_ARROW, LinkAdornment.NONE, "", "", LinkStyle.DASHED, DiagramType.SEQUENCE),
		/**
		 * Lost message
		 */
		LOST_MESSAGE("Lost", LinkAdornment.INVERTED_SOLID_CIRCLE, LinkAdornment.NONE, "", "", LinkStyle.SOLID, DiagramType.SEQUENCE),
		/**
		 * Found message
		 */
		FOUND_MESSAGE("Found", LinkAdornment.WIRE_ARROW, LinkAdornment.INVERTED_SOLID_CIRCLE, "", "", LinkStyle.SOLID, DiagramType.SEQUENCE),
		/**
		 * Note relation
		 */
		NOTE("Note link", LinkAdornment.NONE, LinkAdornment.NONE, "", "", LinkStyle.SOLID, DiagramType.HYBRID),
		/**
		 * Class relation
		 */
		CLASSRELATION("Class Relation", LinkAdornment.NONE, LinkAdornment.NONE, "", "", LinkStyle.SOLID, DiagramType.CLASS),
		/**
		 * Class Object instantiation
		 */
		INSTANTIATION("Instantiation", LinkAdornment.WIRE_ARROW, LinkAdornment.NONE, "", "", LinkStyle.DASHED_DOTTED, DiagramType.HYBRID);

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

		private String			name;
		private LinkAdornment	defaultLeftAdornment;
		private LinkAdornment	defaultRightAdornment;
		private String			defaultLeftCardinality;
		private String			defaultRightCardinality;
		private LinkStyle		defaultLinkStyle;

		private DiagramType			requiredType;

		/**
		 * Default constructor ONLY for gwt-rpc serialization 
		 */
		private LinkKind() {
		}
		
		private LinkKind(final String name, final LinkAdornment defaultLeftAdornment, final LinkAdornment defaultRightAdornment,
				final String defaultLeftCardinality, final String defaultRightCardinality, final LinkStyle defaultLinkStyle, final DiagramType requiredType) {

			this.name = name;
			this.defaultLeftAdornment = defaultLeftAdornment;
			this.defaultRightAdornment = defaultRightAdornment;
			this.defaultLeftCardinality = defaultLeftCardinality;
			this.defaultRightCardinality = defaultRightCardinality;
			this.defaultLinkStyle = defaultLinkStyle;
			this.requiredType = requiredType;
		}

		/**
		 * Getter for the defaultLeftAdornment
		 * 
		 * @return the defaultLeftAdornment
		 */
		public LinkAdornment getDefaultLeftAdornment() {
			return this.defaultLeftAdornment;
		}

		/**
		 * Getter for the defaultLeftCardinality
		 * 
		 * @return the defaultLeftCardinality
		 */
		public String getDefaultLeftCardinality() {
			return this.defaultLeftCardinality;
		}

		/**
		 * Getter for the defaultLinkStyle
		 * 
		 * @return the defaultLinkStyle
		 */
		public LinkStyle getDefaultLinkStyle() {
			return this.defaultLinkStyle;
		}

		/**
		 * Getter for the defaultRightAdornment
		 * 
		 * @return the defaultRightAdornment
		 */
		public LinkAdornment getDefaultRightAdornment() {
			return this.defaultRightAdornment;
		}

		/**
		 * Getter for the defaultRightCardinality
		 * 
		 * @return the defaultRightCardinality
		 */
		public String getDefaultRightCardinality() {
			return this.defaultRightCardinality;
		}

		/**
		 * Getter for the name
		 * 
		 * @return the name
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * Tells if a {@link LinkKind} can be on a diagram depending on its type
		 * 
		 * @param diagramType
		 *            The current diagram type
		 * 
		 * @return True if this {@link LinkKind} can be put on this diagramType
		 */
		public boolean isForDiagram(final DiagramType diagramType) {
			if (this == INSTANTIATION) {
				return diagramType.isHybridType();
			}
			if (this.requiredType.isHybridType()) {
				return diagramType.isClassOrObjectType();
			}
			return diagramType.equals(this.requiredType);
		}
	}

	protected LinkKind	linkKind;

	
	/**
	 * Default constructor ONLY for gwt-rpc serialization
	 */
	public UMLLink() {}
	
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
		return this.linkKind;
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
