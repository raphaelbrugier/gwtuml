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

import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;

/**
 * This listener allow subscription on UML related events
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public interface GWTUMLEventListener {

	/**
	 * This method is called when an artifact is removed from the canvas
	 * 
	 * @param umlArtifact
	 *            The artifact being removed from the canvas
	 * @return <ul>
	 *         <li><b>True</b> to confirm the artifact deletion</li>
	 *         <li><b>False</b> to prevent it</li>
	 *         </ul>
	 * 
	 */
	public boolean onDeleteUMLArtifact(UMLArtifact umlArtifact);

	/**
	 * This method is called when an artifact is edited
	 * 
	 * @param umlArtifact
	 *            The edited artifact
	 * @return <ul>
	 *         <li><b>True</b> to confirm the edition</li>
	 *         <li><b>False</b> to prevent it</li>
	 *         </ul>
	 */
	public boolean onEditUMLArtifact(UMLArtifact umlArtifact);

	/**
	 * This method is called when a link kind change
	 * 
	 * @param linkArtifact
	 *            The link which type is changing
	 * @param oldKind
	 *            The old {@link LinkKind}
	 * @param newKind
	 *            The old {@link LinkKind}
	 * 
	 * @return <ul>
	 *         <li><b>True</b> to confirm the link kind change</li>
	 *         <li><b>False</b> to prevent it</li>
	 *         </ul>
	 */
	public boolean onLinkKindChange(LinkArtifact linkArtifact, LinkKind oldKind, LinkKind newKind);

	/**
	 * This method is called when a new artifact is made
	 * 
	 * @param umlArtifact
	 *            The new artifact being created
	 * @return <ul>
	 *         <li><b>True</b> to confirm the artifact creation</li>
	 *         <li><b>False</b> to prevent it</li>
	 *         </ul>
	 */
	public boolean onNewUMLArtifact(UMLArtifact umlArtifact);

}
