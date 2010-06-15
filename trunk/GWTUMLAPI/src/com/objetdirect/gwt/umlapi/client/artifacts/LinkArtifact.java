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

import com.objetdirect.gwt.umlapi.client.engine.Direction;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;

/**
 * This abstract class specialize an {@link UMLArtifact} in a link type artifact
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public abstract class LinkArtifact extends UMLArtifact {

	private UMLArtifact	leftUMLArtifact;
	
	private UMLArtifact	rightUMLArtifact;
	
	protected Point leftPoint;

	protected Point rightPoint;
	protected int				order;

	protected boolean isSelfLink;
	protected Direction leftDirection;
	protected Direction rightDirection;

	protected boolean isTheOneRebuilding;

	private boolean	doesntHaveToBeComputed;

	
	/** Default constructor ONLY for GWT-RPC serialization. */
	@Deprecated
	protected LinkArtifact() {}
	
	/**
	 * Constructor of RelationLinkArtifact
	 * 
	 * @param canvas Where the gfxObject are displayed
	 * @param id The artifacts's id
	 * @param uMLArtifact1 First {@link UMLArtifact}
	 * @param uMLArtifact2 Second {@link UMLArtifact}
	 * 
	 */
	public LinkArtifact(UMLCanvas canvas, int id, final UMLArtifact uMLArtifact1, final UMLArtifact uMLArtifact2) {
		super(canvas, id);
		leftPoint = Point.getOrigin();
		rightPoint = Point.getOrigin();
		isSelfLink = false;
		leftDirection = Direction.UNKNOWN;
		rightDirection = Direction.UNKNOWN;
		isTheOneRebuilding	= false;
		
		this.leftUMLArtifact = uMLArtifact1;
		this.rightUMLArtifact = uMLArtifact2;
		final UMLArtifactPeer newPeer = new UMLArtifactPeer(uMLArtifact1, uMLArtifact2);
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
