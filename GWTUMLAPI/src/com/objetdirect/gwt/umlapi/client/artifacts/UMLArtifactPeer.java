/*
 * This file is part of the GWTUML project and was written by Raphael Brugier <raphael-dot-brugier.at.gmail'dot'com> for Objet Direct
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
package com.objetdirect.gwt.umlapi.client.artifacts;

import java.io.Serializable;

/**
 * Represent a pair of UMLArtifact linked together.
 * @author Raphael Brugier < raphael dot brugier at gmail dot com >
 */
public class UMLArtifactPeer implements Serializable {
	public UMLArtifact uMLArtifact1;
	public UMLArtifact uMLArtifact2;

	/** Default constructor ONLY for gwt-rpc serialization.  */
	@Deprecated
	UMLArtifactPeer() { }
	
	public UMLArtifactPeer(final UMLArtifact uMLArtifact1, final UMLArtifact uMLArtifact2) {
		this.uMLArtifact1 = uMLArtifact1;
		this.uMLArtifact2 = uMLArtifact2;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((uMLArtifact1 == null) ? 0 : uMLArtifact1.hashCode());
		result = prime * result
				+ ((uMLArtifact2 == null) ? 0 : uMLArtifact2.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UMLArtifactPeer other = (UMLArtifactPeer) obj;
		if (uMLArtifact1 == null) {
			if (other.uMLArtifact1 != null)
				return false;
		} else if (!uMLArtifact1.equals(other.uMLArtifact1))
			return false;
		if (uMLArtifact2 == null) {
			if (other.uMLArtifact2 != null)
				return false;
		} else if (!uMLArtifact2.equals(other.uMLArtifact2))
			return false;
		return true;
	}
}