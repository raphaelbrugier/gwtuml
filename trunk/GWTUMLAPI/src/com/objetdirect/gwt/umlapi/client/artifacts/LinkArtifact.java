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
package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.Collections;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.engine.Direction;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;

/**
 * This abstract class specialize an {@link UMLArtifact} in a link type artifact
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
/**
 * @author Raphael
 *
 */
public abstract class LinkArtifact extends UMLArtifact {

	/**
	 * Represent a pair of UMLArtifact linked together.
	 */
	public class UMLArtifactPeer {
		UMLArtifact	uMLArtifact1;
		UMLArtifact	uMLArtifact2;
		
		/**
		 * Default constructor ONLY for gwt-rpc serialization. 
		 */
		public UMLArtifactPeer() {
		}
		
		UMLArtifactPeer(final UMLArtifact uMLArtifact1, final UMLArtifact uMLArtifact2) {
			super();
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

//		@Override
//		public boolean equals(final Object obj) {
//			final UMLArtifactPeer peer = (UMLArtifactPeer) obj;
//			return ((this.uMLArtifact1 == peer.uMLArtifact1) && (this.uMLArtifact2 == peer.uMLArtifact2))
//			|| ((this.uMLArtifact1 == peer.uMLArtifact2) && (this.uMLArtifact2 == peer.uMLArtifact1));
//		}
	}

	/**
	 * Make a link between two {@link UMLArtifact}
	 * 
	 * @param uMLArtifact
	 *            The first one of the two {@link UMLArtifact} to be linked
	 * @param uMLArtifactNew
	 *            The second one of the two {@link UMLArtifact} to be linked
	 * @param linkKind
	 *            The {@link LinkKind} of this link
	 * @return The created {@link LinkArtifact} linking uMLArtifact and uMLArtifactNew
	 */
	public static LinkArtifact makeLinkBetween(final UMLArtifact uMLArtifact, final UMLArtifact uMLArtifactNew, final LinkKind linkKind) {
		if (linkKind == LinkKind.NOTE) {
			if (uMLArtifactNew.getClass() == NoteArtifact.class) {
				return new LinkNoteArtifact((NoteArtifact) uMLArtifactNew, uMLArtifact);
			}
			if (uMLArtifact.getClass() == NoteArtifact.class) {
				return new LinkNoteArtifact((NoteArtifact) uMLArtifact, uMLArtifactNew);
			}
			return null;
		} else if (linkKind == LinkKind.CLASSRELATION) {
			if ((uMLArtifactNew.getClass() == ClassRelationLinkArtifact.class) && (uMLArtifact.getClass() == ClassArtifact.class)) {
				return new LinkClassRelationArtifact((ClassArtifact) uMLArtifact, (ClassRelationLinkArtifact) uMLArtifactNew);
			}
			if ((uMLArtifact.getClass() == ClassRelationLinkArtifact.class) && (uMLArtifactNew.getClass() == ClassArtifact.class)) {
				return new LinkClassRelationArtifact((ClassArtifact) uMLArtifactNew, (ClassRelationLinkArtifact) uMLArtifact);
			}
			return null;

		} else if ((uMLArtifact.getClass() == ClassArtifact.class) && (uMLArtifactNew.getClass() == ClassArtifact.class)) {
			return new ClassRelationLinkArtifact((ClassArtifact) uMLArtifactNew, (ClassArtifact) uMLArtifact, linkKind);

		} else if ((linkKind != LinkKind.GENERALIZATION_RELATION) && (linkKind != LinkKind.REALIZATION_RELATION)
				&& (uMLArtifact.getClass() == ObjectArtifact.class) && (uMLArtifactNew.getClass() == ObjectArtifact.class)) {
			return new ObjectRelationLinkArtifact((ObjectArtifact) uMLArtifactNew, (ObjectArtifact) uMLArtifact, linkKind);
		} else if ((linkKind == LinkKind.INSTANTIATION)
				&& ((uMLArtifact.getClass() == ClassArtifact.class) && (uMLArtifactNew.getClass() == ObjectArtifact.class))) {
			return new InstantiationRelationLinkArtifact((ClassArtifact) uMLArtifact, (ObjectArtifact) uMLArtifactNew, linkKind);
		} else if ((linkKind == LinkKind.INSTANTIATION)
				&& ((uMLArtifact.getClass() == ObjectArtifact.class) && (uMLArtifactNew.getClass() == ClassArtifact.class))) {
			return new InstantiationRelationLinkArtifact((ClassArtifact) uMLArtifactNew, (ObjectArtifact) uMLArtifact, linkKind);
		} else if ((uMLArtifact.getClass() == LifeLineArtifact.class) && (uMLArtifactNew.getClass() == LifeLineArtifact.class)) {
			return new MessageLinkArtifact((LifeLineArtifact) uMLArtifactNew, (LifeLineArtifact) uMLArtifact, linkKind);

		}
		return null;

	}

	private UMLArtifact	leftUMLArtifact;
	
	private UMLArtifact	rightUMLArtifact;
	
	protected Point				leftPoint			= Point.getOrigin();

	protected Point				rightPoint			= Point.getOrigin();
	protected int				order;

	protected boolean			isSelfLink			= false;
	protected Direction			leftDirection		= Direction.UNKNOWN;
	protected Direction			rightDirection		= Direction.UNKNOWN;

	protected boolean			isTheOneRebuilding	= false;

	private boolean				doesntHaveToBeComputed;

	
	/** Default constructor ONLY for GWT-RPC serialization. */
	public LinkArtifact() {}
	
	/**
	 * Constructor of RelationLinkArtifact
	 * 
	 * @param uMLArtifact1
	 *            First {@link UMLArtifact}
	 * @param uMLArtifact2
	 *            Second {@link UMLArtifact}
	 * 
	 */
	public LinkArtifact(final UMLArtifact uMLArtifact1, final UMLArtifact uMLArtifact2) {
		super(true);
		this.leftUMLArtifact = uMLArtifact1;
		this.rightUMLArtifact = uMLArtifact2;
		final LinkArtifact.UMLArtifactPeer newPeer = new LinkArtifact.UMLArtifactPeer(uMLArtifact1, uMLArtifact2);
		this.canvas = Session.getActiveCanvas();
		this.order = Collections.frequency(this.canvas.getuMLArtifactRelations(), newPeer);
		this.canvas.getuMLArtifactRelations().add(newPeer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#getCenter()
	 */
	@Override
	public Point getCenter() {
		return Point.getMiddleOf(this.leftPoint, this.rightPoint);
	}

	@Override
	public int getHeight() {
		return this.leftPoint.getY() < this.rightPoint.getY() ? this.rightPoint.getY() - this.leftPoint.getY() : this.leftPoint.getY() - this.rightPoint.getY();
	}

	@Override
	public int[] getOpaque() {
		return null;
	}

	@Override
	public GfxObject getOutline() {
		return null;
	}

	@Override
	public int getWidth() {
		return this.leftPoint.getX() < this.rightPoint.getX() ? this.rightPoint.getX() - this.leftPoint.getX() : this.leftPoint.getX() - this.rightPoint.getX();
	}

	@Override
	public boolean isALink() {
		return true;
	}

	@Override
	public boolean isDraggable() {
		return false;
	}
	
	/**
	 * Getter for the leftUMLArtifact
	 * @return the leftUMLArtifact
	 */
	public UMLArtifact getLeftUMLArtifact() {
		return this.leftUMLArtifact;
	}

	/**
	 * Getter for the rightUMLArtifact
	 * @return the rightUMLArtifact
	 */
	public UMLArtifact getRightUMLArtifact() {
		return this.rightUMLArtifact;
	}

	/**
	 * This method add an extra dependency removal for link <br>
	 * to tell other artifact that they don't need to be still dependent on this line
	 */
	public abstract void removeCreatedDependency();

	void doesntHaveToBeComputed(final boolean state) {
		this.doesntHaveToBeComputed = state;
	}

	protected void computeDirectionsType() {
		if (this.doesntHaveToBeComputed) {
			return;
		}
		this.isTheOneRebuilding = true;

		final Direction oldLeftDirection = this.leftDirection;
		final Direction oldRightDirection = this.rightDirection;

		this.leftDirection = this.computeDirectionType(this.leftPoint, this.leftUMLArtifact);
		this.rightDirection = this.computeDirectionType(this.rightPoint, this.rightUMLArtifact);

		
		if (this.leftDirection != oldLeftDirection) {
			this.leftUMLArtifact.removeDirectionDependecy(oldLeftDirection, this);
			this.leftUMLArtifact.rebuildDirectionDependencies(oldLeftDirection);
			this.leftUMLArtifact.addDirectionDependecy(this.leftDirection, this);
			this.leftUMLArtifact.sortDirectionDependecy(this.leftDirection, this);
			this.leftUMLArtifact.rebuildDirectionDependencies(this.leftDirection);
		} else {
			if (this.canvas.isLinkArtifactsHaveAlreadyBeenSorted()) {
				this.canvas.setLinkArtifactsHaveAlreadyBeenSorted(true);
				this.leftUMLArtifact.sortDirectionDependecy(this.leftDirection, this);
				this.leftUMLArtifact.rebuildDirectionDependencies(this.leftDirection);
				this.canvas.setLinkArtifactsHaveAlreadyBeenSorted(false);
			}
		}
		if (this.rightDirection != oldRightDirection) {
			this.rightUMLArtifact.removeDirectionDependecy(this.rightDirection, this);
			this.rightUMLArtifact.rebuildDirectionDependencies(oldRightDirection);
			this.rightUMLArtifact.addDirectionDependecy(this.rightDirection, this);
			this.leftUMLArtifact.sortDirectionDependecy(this.rightDirection, this);
			this.rightUMLArtifact.rebuildDirectionDependencies(this.rightDirection);
		} else {
			if (this.canvas.isLinkArtifactsHaveAlreadyBeenSorted()) {
				this.canvas.setLinkArtifactsHaveAlreadyBeenSorted(true);
				this.leftUMLArtifact.sortDirectionDependecy(this.rightDirection, this);
				this.rightUMLArtifact.rebuildDirectionDependencies(this.rightDirection);
				this.canvas.setLinkArtifactsHaveAlreadyBeenSorted(false);
			}
		}
		this.isTheOneRebuilding = false;
	}

	private Direction computeDirectionType(final Point point, final UMLArtifact uMLArtifact) {
		if (point.getX() == uMLArtifact.getLocation().getX()) {
			return Direction.LEFT;
		} else if (point.getY() == uMLArtifact.getLocation().getY()) {
			return Direction.UP;
		} else if (point.getX() == uMLArtifact.getLocation().getX() + uMLArtifact.getWidth()) {
			return Direction.RIGHT;
		} else if (point.getY() == uMLArtifact.getLocation().getY() + uMLArtifact.getHeight()) {
			return Direction.DOWN;
		}
		return Direction.UNKNOWN;

	}

}
