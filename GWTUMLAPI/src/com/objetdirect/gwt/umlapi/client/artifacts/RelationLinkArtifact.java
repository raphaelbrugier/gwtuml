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

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLComponent;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkKind;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLRelation;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public abstract class RelationLinkArtifact extends LinkArtifact {

	/**
	 * This enumeration list all text part of a RelationLinkArtifact
	 * 
	 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
	 * 
	 */
	public enum RelationLinkArtifactPart {
		/**
		 * Left end cardinality
		 */
		LEFT_CARDINALITY("Cardinality", true) {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com
			 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation)
			 */
			@Override
			public String getText(final UMLRelation relation) {
				return relation.getLeftCardinality();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com
			 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation, java.lang.String)
			 */
			@Override
			public void setText(final UMLRelation relation, final String text) {
				relation.setLeftCardinality(text);
			}
		},
		/**
		 * Left end constraint
		 */
		LEFT_CONSTRAINT("Constraint", true) {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com
			 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation)
			 */
			@Override
			public String getText(final UMLRelation relation) {
				return relation.getLeftConstraint();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com
			 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation, java.lang.String)
			 */
			@Override
			public void setText(final UMLRelation relation, final String text) {
				relation.setLeftConstraint(text);
			}
		},
		/**
		 * Left end role
		 */
		LEFT_ROLE("Role", true) {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com
			 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation)
			 */
			@Override
			public String getText(final UMLRelation relation) {
				return relation.getLeftRole();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com
			 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation, java.lang.String)
			 */
			@Override
			public void setText(final UMLRelation relation, final String text) {
				relation.setLeftRole(text);
			}
		},
		/**
		 * Left stereotype
		 */
		LEFT_STEREOTYPE("Stereotype", true) {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com
			 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation)
			 */
			@Override
			public String getText(final UMLRelation relation) {
				return relation.getLeftStereotype();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com
			 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation, java.lang.String)
			 */
			@Override
			public void setText(final UMLRelation relation, final String text) {
				relation.setLeftStereotype(text);
			}
		},
		/**
		 * The relation name
		 */
		NAME("Name", false) {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com
			 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation)
			 */
			@Override
			public String getText(final UMLRelation relation) {
				return relation.getName();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com
			 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation, java.lang.String)
			 */
			@Override
			public void setText(final UMLRelation relation, final String text) {
				relation.setName(text);
			}
		},

		/**
		 * Right end cardinality
		 */
		RIGHT_CARDINALITY("Cardinality", false) {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com
			 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation)
			 */
			@Override
			public String getText(final UMLRelation relation) {
				return relation.getRightCardinality();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com
			 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation, java.lang.String)
			 */
			@Override
			public void setText(final UMLRelation relation, final String text) {
				relation.setRightCardinality(text);
			}
		},
		/**
		 * Right end constraint
		 */
		RIGHT_CONSTRAINT("Constraint", false) {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com
			 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation)
			 */
			@Override
			public String getText(final UMLRelation relation) {
				return relation.getRightConstraint();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com
			 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation, java.lang.String)
			 */
			@Override
			public void setText(final UMLRelation relation, final String text) {
				relation.setRightConstraint(text);
			}
		},
		/**
		 * Right end role
		 */
		RIGHT_ROLE("Role", false) {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com
			 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation)
			 */
			@Override
			public String getText(final UMLRelation relation) {
				return relation.getRightRole();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com
			 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation, java.lang.String)
			 */
			@Override
			public void setText(final UMLRelation relation, final String text) {
				relation.setRightRole(text);
			}
		},
		/**
		 * Left end role
		 */
		RIGHT_STEREOTYPE("Stereotype", false) {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com
			 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation)
			 */
			@Override
			public String getText(final UMLRelation relation) {
				return relation.getRightStereotype();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com
			 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation, java.lang.String)
			 */
			@Override
			public void setText(final UMLRelation relation, final String text) {
				relation.setRightStereotype(text);
			}
		};

		private boolean isLeft;

		private String name;

		/** Default constructor ONLY for gwt-rpc serialization. */
		private RelationLinkArtifactPart() {
		}

		private RelationLinkArtifactPart(final String name, final boolean isLeft) {
			this.name = name;
			this.isLeft = isLeft;
		}

		/**
		 * Getter for the text contained by the graphical object for a part
		 * 
		 * @param relation
		 *            The relation {@link UMLComponent} this enumeration is about
		 * @return the text corresponding to this part
		 */
		public abstract String getText(UMLRelation relation);

		/**
		 * Determine if this part is "Left"
		 * 
		 * @return <ul>
		 *         <li><b>True</b> if it is actually "Left"</li>
		 *         <li><b>False</b> otherwise</li>
		 *         </ul>
		 */
		public boolean isLeft() {
			return isLeft;
		}

		/**
		 * Setter to affect a text to a part of the relation {@link UMLComponent}
		 * 
		 * @param relation
		 *            The relation {@link UMLComponent} this enumeration is about
		 * @param text
		 *            The text corresponding to this part
		 */
		public abstract void setText(UMLRelation relation, String text);

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return name;
		}
	}


	/**
	 * /!\ Don't forget to increment the serialVersionUID if you change any of the fields above /!\
	 */
	private static final long serialVersionUID = 1L;

	protected UMLRelation relation;
	protected Point leftDirectionPoint;
	protected Point rightDirectionPoint;
	protected Point nameAnchorPoint;
	private NodeArtifact rightNodeArtifact;
	private NodeArtifact leftNodeArtifact;

	/** Default constructor ONLY for gwt rpc serialization. */
	@Deprecated
	protected RelationLinkArtifact() {
	}

	/**
	 * Constructor of RelationLinkArtifact
	 * 
	 * @param canvas
	 *            Where the gfxObjects are displayed
	 * @param id
	 *            The artifacts's id
	 * @param nodeArtifact1
	 * @param nodeArtifact2
	 * @param relationKind
	 */
	public RelationLinkArtifact(final UMLCanvas canvas, int id, final NodeArtifact nodeArtifact1, final NodeArtifact nodeArtifact2, final LinkKind relationKind) {
		super(canvas, id, nodeArtifact1, nodeArtifact2);

		leftDirectionPoint = Point.getOrigin();
		rightDirectionPoint = Point.getOrigin();
		nameAnchorPoint = Point.getOrigin();

		leftNodeArtifact = nodeArtifact1;
		rightNodeArtifact = nodeArtifact2;
		if ((relationKind == LinkKind.NOTE) || (relationKind == LinkKind.CLASSRELATION)) {
			Log.error("Making a relation artifact for : " + relationKind.getName());
		}
		relation = new UMLRelation(relationKind);
	}

	/**
	 * Setter of a part text
	 * 
	 * @param part
	 *            The {@link RelationLinkArtifactPart} in which the text is to be updated
	 * @param newContent
	 *            The new text to set for this part
	 */
	public void setPartContent(final RelationLinkArtifactPart part, final String newContent) {
		part.setText(relation, newContent);
	}

	/**
	 * Build a GfxObject to represent the line between the two nodes artifacts.
	 * 
	 * @return a new GfxObject fore the line.
	 */
	protected GfxObject buildLine() {
		if (isSelfLink) {
			return this.getSelfLine();
		}
		return this.getPeerLine();
	}

	private GfxObject getPeerAngularLine() {

		leftPoint = Point.add(leftNodeArtifact.getCenter(), new Point(Math.abs(leftDirection.getYDirection())
				* (-leftNodeArtifact.getWidth() / 2 + ((leftNodeArtifact.getWidth() / (leftNodeArtifact.getDependenciesCount(leftDirection) + 1)))
						* (leftNodeArtifact.getDependencyIndexOf(this, leftDirection) + 1)), Math.abs(leftDirection.getXDirection())
						* (-leftNodeArtifact.getHeight() / 2 + ((leftNodeArtifact.getHeight() / (leftNodeArtifact.getDependenciesCount(leftDirection) + 1)))
								* (leftNodeArtifact.getDependencyIndexOf(this, leftDirection) + 1))));
		rightPoint = Point.add(rightNodeArtifact.getCenter(), new Point(Math.abs(rightDirection.getYDirection())
				* (-rightNodeArtifact.getWidth() / 2 + ((rightNodeArtifact.getWidth() / (rightNodeArtifact.getDependenciesCount(rightDirection) + 1)))
						* (rightNodeArtifact.getDependencyIndexOf(this, rightDirection) + 1)), Math.abs(rightDirection.getXDirection())
						* (-rightNodeArtifact.getHeight() / 2 + ((rightNodeArtifact.getHeight() / (rightNodeArtifact.getDependenciesCount(rightDirection) + 1)))
								* (rightNodeArtifact.getDependencyIndexOf(this, rightDirection) + 1))));

		leftPoint.translate(leftDirection.getXDirection() * leftNodeArtifact.getWidth() / 2, leftDirection.getYDirection() * leftNodeArtifact.getHeight() / 2);
		rightPoint.translate(rightDirection.getXDirection() * rightNodeArtifact.getWidth() / 2, rightDirection.getYDirection() * rightNodeArtifact.getHeight()
				/ 2);

		final Point intermediate = Point.abs(Point.substract(rightPoint, leftDirection.isOppositeOf(rightDirection) ? Point.getMiddleOf(leftPoint, rightPoint)
				: leftPoint));
		final GfxObject line = GfxManager.getPlatform().buildPath();

		leftDirectionPoint = Point.add(leftPoint, new Point(leftDirection.getXDirection() * intermediate.getX(), leftDirection.getYDirection()
				* intermediate.getY()));
		rightDirectionPoint = Point.add(rightPoint, new Point(rightDirection.getXDirection() * intermediate.getX(), rightDirection.getYDirection()
				* intermediate.getY()));

		GfxManager.getPlatform().moveTo(line, leftPoint);
		GfxManager.getPlatform().lineTo(line, leftDirectionPoint);
		GfxManager.getPlatform().lineTo(line, rightDirectionPoint);
		GfxManager.getPlatform().lineTo(line, rightPoint);
		line.setOpacity(0, true);

		return line;
	}

	private GfxObject getPeerLine() {
		final ArrayList<Point> points = GeometryManager.getPlatform().getLineBetween(leftNodeArtifact, rightNodeArtifact);
		leftPoint = points.get(0);
		rightPoint = points.get(1);
		this.computeDirectionsType();
		return OptionsManager.get("AngularLinks") == 1 ? this.getPeerAngularLine() : this.getPeerRightLine();
	}

	private GfxObject getPeerRightLine() {
		GfxObject line;
		nameAnchorPoint = Point.getMiddleOf(leftPoint, rightPoint);
		if (order == 0) {
			line = GfxManager.getPlatform().buildLine(leftPoint, rightPoint);
			leftDirectionPoint = rightPoint;
			rightDirectionPoint = leftPoint;
		} else {
			final Point curveControl = GeometryManager.getPlatform().getShiftedCenter(leftPoint, rightPoint,
					50 * ((order + 1) / 2) * ((order % 2) == 0 ? -1 : 1));
			leftDirectionPoint = curveControl;
			rightDirectionPoint = curveControl;
			line = GfxManager.getPlatform().buildPath();
			GfxManager.getPlatform().moveTo(line, leftPoint);
			GfxManager.getPlatform().curveTo(line, rightPoint, curveControl);
			line.setOpacity(0, true);
			nameAnchorPoint = Point.getMiddleOf(curveControl, nameAnchorPoint);
		}

		return line;
	}

	private GfxObject getSelfAngularLine() {
		leftPoint = leftNodeArtifact.getCenter().translate(0, -leftNodeArtifact.getHeight() / 2);
		rightPoint = leftNodeArtifact.getCenter().translate(leftNodeArtifact.getWidth() / 2, 0);
		this.computeDirectionsType();
		final GfxObject line = GfxManager.getPlatform().buildPath();
		final Point rightShiftedPoint = Point.add(rightPoint, new Point((order + 1) * REFLEXIVE_PATH_X_GAP, 0));
		final Point leftShiftedPoint = Point.add(leftPoint, new Point(0, -(order + 1) * REFLEXIVE_PATH_Y_GAP));
		leftDirectionPoint = leftShiftedPoint;
		rightDirectionPoint = rightShiftedPoint;
		GfxManager.getPlatform().moveTo(line, leftPoint);
		GfxManager.getPlatform().lineTo(line, leftShiftedPoint);
		GfxManager.getPlatform().lineTo(line, new Point(rightShiftedPoint.getX(), leftShiftedPoint.getY()));
		GfxManager.getPlatform().lineTo(line, rightShiftedPoint);
		GfxManager.getPlatform().lineTo(line, rightPoint);
		line.setOpacity(0, true);
		nameAnchorPoint = new Point((leftPoint.getX() + rightPoint.getX() + (order + 1) * REFLEXIVE_PATH_X_GAP) / 2, leftPoint.getY() - (order + 1)
				* REFLEXIVE_PATH_Y_GAP);
		return line;
	}

	private GfxObject getSelfLine() {
		return OptionsManager.get("AngularLinks") == 1 ? this.getSelfAngularLine() : this.getSelfRightLine();
	}

	private GfxObject getSelfRightLine() {
		final int radius = (order + 1) * REFLEXIVE_PATH_X_GAP;
		leftPoint = leftNodeArtifact.getLocation().clonePoint().translate(leftNodeArtifact.getWidth() - radius, 0);
		rightPoint = leftNodeArtifact.getLocation().clonePoint().translate(leftNodeArtifact.getWidth(), radius);
		this.computeDirectionsType();
		final Point edge = new Point(rightPoint.getX(), leftPoint.getY());
		final GfxObject line = GfxManager.getPlatform().buildCircle((order + 1) * REFLEXIVE_PATH_X_GAP);
		leftDirectionPoint = Point.add(leftPoint, new Point(0, -REFLEXIVE_PATH_X_GAP));
		rightDirectionPoint = Point.add(rightPoint, new Point(REFLEXIVE_PATH_X_GAP, 0));
		nameAnchorPoint = Point.add(edge, new Point(0, -(order + 1) * REFLEXIVE_PATH_X_GAP));
		line.translate(edge);
		line.setOpacity(0, true);
		return line;
	}

}
