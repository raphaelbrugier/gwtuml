package com.objetdirect.gwt.umlapi.client.artifacts.links;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks.AggregationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks.AssociationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks.CompositionLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks.DependencyLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks.GeneralizationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks.RealizationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.editors.RelationshipLinkFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
/**
 * @author  florian
 */
public abstract class RelationshipLinkArtifact extends LinkArtifact {

    public static RelationshipLinkArtifact makeLinkArtifact(ClassArtifact left, ClassArtifact right, RelationKind kind) {
        switch (kind) {
        case AGGREGATION: return new AggregationLinkArtifact(left, right);
        case COMPOSITION: return new CompositionLinkArtifact(left, right); 
        case DEPENDENCY: return new DependencyLinkArtifact(left, right); 
        case GENERALIZATION: return new GeneralizationLinkArtifact(left, right); 
        case ASSOCIATION: return new AssociationLinkArtifact(left, right); 
        case REALIZATION: return new RealizationLinkArtifact(left, right); 
        default:
            return new AggregationLinkArtifact(left, right);
        }

    }

    /**
     * @author   florian
     */
    public enum RelationshipArtifactPart {
        LEFT_CARDINALITY {
            @Override
            public String getText(Relation relation) { return relation.getLeftCardinality(); }
            @Override
            public void setText(Relation relation, String text) { relation.setLeftCardinality(text); }
        },  
        LEFT_CONSTRAINT {
            @Override
            public String getText(Relation relation) { return relation.getLeftConstraint(); } 
            @Override
            public void setText(Relation relation, String text) { relation.setLeftConstraint(text); }
        },  
        LEFT_ROLE {
            @Override
            public String getText(Relation relation) { return relation.getLeftRole(); } 
            @Override
            public void setText(Relation relation, String text) { relation.setLeftRole(text); }
        },  
        NAME {
            @Override
            public String getText(Relation relation) { return relation.getName(); } 
            @Override
            public void setText(Relation relation, String text) { relation.setName(text); }
        },  
        RIGHT_CARDINALITY {
            @Override
            public String getText(Relation relation) { return relation.getRightCardinality(); }
            @Override
            public void setText(Relation relation, String text) { relation.setRightCardinality(text); }
        },  
        RIGHT_CONSTRAINT {
            @Override
            public String getText(Relation relation) { return relation.getRightConstraint(); } 
            @Override
            public void setText(Relation relation, String text) { relation.setRightConstraint(text); }
        },  
        RIGHT_ROLE  {
            @Override
            public String getText(Relation relation) { return relation.getRightRole(); } 
            @Override
            public void setText(Relation relation, String text) { relation.setRightRole(text); }
        };
        private static HashMap<GfxObject, RelationshipArtifactPart> textGfxObject = new HashMap<GfxObject, RelationshipArtifactPart>();
        public abstract String getText(Relation relation);
        public abstract void setText(Relation relation, String text);
        public static void setGfxObjectTextForPart(GfxObject text, RelationshipArtifactPart part) {
            textGfxObject.put(text, part);
        }

        public static RelationshipArtifactPart getPartForGfxObject(GfxObject text) {
            return textGfxObject.get(text);
        }
    }
    /**
     * @author   florian
     */
    protected enum Anchor { 
        TOP,  
        BOTTOM,  
        LEFT,  
        RIGHT,  
        UNKNOWN;
    }

    private int current_delta;	 
    protected Relation relation;
    protected GfxObject line;
    protected GfxObject arrowVirtualGroup;
    protected GfxObject textVirtualGroup;
    protected ClassArtifact leftClassArtifact;
    protected ClassArtifact relationClass;
    protected ClassArtifact rightClassArtifact;
    private HashMap<RelationshipArtifactPart, GfxObject> gfxObjectPart = new HashMap<RelationshipArtifactPart, GfxObject>();

    public RelationshipLinkArtifact(ClassArtifact left, ClassArtifact right) {
        this.leftClassArtifact = left;
        left.addDependency(this, right);
        this.rightClassArtifact = right;
        if(right != left) right.addDependency(this, left);
    }

    public void edit(RelationshipArtifactPart part) {
        part.setText(relation, "link");
        rebuildGfxObject();
        edit(gfxObjectPart.get(part), 0, 0);		
    }

    public void edit(GfxObject gfxObject, int x, int y) {
        RelationshipArtifactPart editPart = RelationshipArtifactPart.getPartForGfxObject(gfxObject);
        if(editPart ==  null) {
            edit(RelationshipArtifactPart.NAME);
        } else {
            RelationshipLinkFieldEditor editor = new RelationshipLinkFieldEditor(canvas, this, editPart);
            editor.startEdition(editPart.getText(relation), 
                    GfxManager.getPlatform().getXFor(gfxObject),
                    GfxManager.getPlatform().getYFor(gfxObject) - GfxManager.getPlatform().getHeightFor(gfxObject),
                    GfxManager.getPlatform().getWidthFor(gfxObject) + OptionsManager.getRectangleXTotalPadding(), false);
        }
    }
    public void setPartContent(RelationshipArtifactPart part, String newContent) {
        part.setText(relation, newContent);
    }
    public ClassArtifact getLeftClassArtifact() {
        return leftClassArtifact;
    }
    public ClassArtifact getRightClassArtifact() {
        return rightClassArtifact;
    }
    public abstract LinkedHashMap<String, Command> getRightMenu();

    public void select() {
        GfxManager.getPlatform().moveToFront(gfxObject/*textVirtualGroup*/);
        GfxManager.getPlatform().setStroke(line, ThemeManager.getHighlightedForegroundColor(), 2);
        GfxManager.getPlatform().setStroke(arrowVirtualGroup, ThemeManager.getHighlightedForegroundColor(), 2);
    }
    public void unselect() {
        GfxManager.getPlatform().setStroke(line, ThemeManager.getForegroundColor(), 1);
        GfxManager.getPlatform().setStroke(arrowVirtualGroup, ThemeManager.getForegroundColor(), 1);
    }
    Anchor getAnchorType(ClassArtifact classArtifact, Point point) {
        if (point.getX() == classArtifact.getX())	return Anchor.LEFT;
        else if (point.getY() == classArtifact.getY()) return Anchor.TOP;
        else if (point.getX() == classArtifact.getX() + classArtifact.getWidth())	return Anchor.RIGHT;
        else if (point.getY() == classArtifact.getY() + classArtifact.getHeight()) return Anchor.BOTTOM;
        return Anchor.UNKNOWN;
    }
    int getTextX(GfxObject text, boolean isLeft) {

        Point relative_point1 = point1;
        Point relative_point2 = point2;
        int textWidth =  GfxManager.getPlatform().getWidthFor(text);
        if(!isLeft) {
            relative_point1 = point2;
            relative_point2 = point1;
        }
        switch (getAnchorType(isLeft ? leftClassArtifact : rightClassArtifact, relative_point1)) {
        case LEFT: return relative_point1.getX() - textWidth - OptionsManager.getRectangleLeftPadding();
        case RIGHT:	return relative_point1.getX() + OptionsManager.getRectangleRightPadding();
        case TOP: case BOTTOM: case UNKNOWN: 
            if (relative_point1.getX() < relative_point2.getX()) return relative_point1.getX() - textWidth - OptionsManager.getRectangleLeftPadding();
            else return relative_point1.getX() + OptionsManager.getRectangleRightPadding();
        }
        return 0;
    }
    int getTextY(GfxObject text, boolean isLeft) {
        Point relative_point1 = point1;
        Point relative_point2 = point2;
        if(!isLeft) {
            relative_point1 = point2;
            relative_point2 = point1;
        }
        int textHeight =   GfxManager.getPlatform().getHeightFor(text);
        int delta = current_delta;
        current_delta += textHeight;
        switch (getAnchorType(isLeft ? leftClassArtifact : rightClassArtifact, relative_point1)) {
        case LEFT: case RIGHT:
            if (relative_point1.getY() > relative_point2.getY()) return relative_point1.getY() + OptionsManager.getRectangleBottomPadding() + textHeight + delta;
            else return relative_point1.getY() - OptionsManager.getRectangleTopPadding()  - delta;
        case TOP: return relative_point1.getY() - OptionsManager.getRectangleTopPadding() - delta;
        case BOTTOM: case UNKNOWN: return relative_point1.getY() + textHeight + OptionsManager.getRectangleBottomPadding() + delta;
        }
        return 0;
    }

    @Override
    protected void buildGfxObject() {
        gfxObjectPart.clear();
        ArrayList<Point> linePoints;
        if(leftClassArtifact != rightClassArtifact) {
            linePoints = GeometryManager.getPlatform().getLineBetween(leftClassArtifact, rightClassArtifact); 
            point1 = linePoints.get(0);
            point2 = linePoints.get(1);
            line = GfxManager.getPlatform().buildLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
        } else {
            linePoints = GeometryManager.getPlatform().getReflexiveLineFor(leftClassArtifact);
            point1 = linePoints.get(1);
            point2 = linePoints.get(2);
            line = GfxManager.getPlatform().buildPath();
            GfxManager.getPlatform().moveTo(line, linePoints.get(0).getX(), linePoints.get(0).getY());
            GfxManager.getPlatform().lineTo(line, linePoints.get(1).getX(), linePoints.get(1).getY());
            GfxManager.getPlatform().lineTo(line, linePoints.get(2).getX(), linePoints.get(2).getY());
            GfxManager.getPlatform().lineTo(line, linePoints.get(3).getX(), linePoints.get(3).getY());
            GfxManager.getPlatform().lineTo(line, linePoints.get(4).getX(), linePoints.get(4).getY());
            GfxManager.getPlatform().setOpacity(line, 0, true);
        }

        GfxManager.getPlatform().setStroke(line, ThemeManager.getForegroundColor(), 1);
        GfxManager.getPlatform().setStrokeStyle(line, style.getGfxStyle());
        GfxManager.getPlatform().addToVirtualGroup(gfxObject, line);


        // Making arrows group :
        arrowVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
        GfxManager.getPlatform().addToVirtualGroup(gfxObject, arrowVirtualGroup);
        if(leftClassArtifact != rightClassArtifact) {
        if (adornmentLeft != LinkAdornment.NONE || adornmentLeft.isCrossed())
            GfxManager.getPlatform().addToVirtualGroup(arrowVirtualGroup, GeometryManager.getPlatform().buildAdornment(point1, point2, adornmentLeft));
        if (adornmentRight != LinkAdornment.NONE || adornmentLeft.isCrossed()) 
            GfxManager.getPlatform().addToVirtualGroup(arrowVirtualGroup, GeometryManager.getPlatform().buildAdornment(point2, point1, adornmentRight));
        } else {
            if (adornmentLeft != LinkAdornment.NONE || adornmentLeft.isCrossed())
                GfxManager.getPlatform().addToVirtualGroup(arrowVirtualGroup, GeometryManager.getPlatform().buildAdornment(linePoints.get(4), linePoints.get(3), adornmentLeft));
        }
            
        // Making the text group :        
        textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
        GfxManager.getPlatform().addToVirtualGroup(gfxObject, textVirtualGroup);
        if (relation.getName() != "") {
            Log.trace("Creating name");
            GfxObject nameGfxObject = GfxManager.getPlatform().buildText(relation.getName());

            GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, nameGfxObject);

            GfxManager.getPlatform().setStroke(nameGfxObject, ThemeManager.getBackgroundColor(), 0);
            GfxManager.getPlatform().setFillColor(nameGfxObject, ThemeManager.getForegroundColor());
            GfxManager.getPlatform().translate(nameGfxObject, 
                    (point1.getX() + point2.getX() -  (GfxManager.getPlatform().getWidthFor(nameGfxObject))) / 2, 
                    (point1.getY() + point2.getY()) / 2);
            RelationshipArtifactPart.setGfxObjectTextForPart(nameGfxObject, RelationshipArtifactPart.NAME);
            gfxObjectPart.put(RelationshipArtifactPart.NAME, nameGfxObject);
        }
        current_delta = 0;
        if (relation.getLeftCardinality() != "")
            GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getLeftCardinality(), true, RelationshipArtifactPart.LEFT_CARDINALITY));
        if (relation.getLeftConstraint() != "") 
            GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getLeftConstraint(), true, RelationshipArtifactPart.LEFT_CONSTRAINT));
        if (relation.getLeftRole() != "") 
            GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getLeftRole(), true, RelationshipArtifactPart.LEFT_ROLE));
        current_delta = 0;
        if (relation.getRightCardinality() != "") 
            GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getRightCardinality(), false, RelationshipArtifactPart.RIGHT_CARDINALITY));
        if (relation.getRightConstraint() != "") 
            GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getRightConstraint(), false, RelationshipArtifactPart.RIGHT_CONSTRAINT));
        if (relation.getRightRole() != "") 
            GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getRightRole(), false, RelationshipArtifactPart.RIGHT_ROLE));
        if (relationClass != null) {
            int xLineCenter = (point1.getX() + point2.getX()) / 2;
            int yLineCenter = (point1.getY() + point2.getY()) / 2;
            Point relationClasslinePoint  = GeometryManager.getPlatform().getPointForLine(relationClass, new Point(xLineCenter, xLineCenter));
            GfxObject relationLine = GfxManager.getPlatform().buildLine(relationClasslinePoint.getX(), relationClasslinePoint.getY(),
                    xLineCenter, yLineCenter);
            GfxManager.getPlatform().setStrokeStyle(relationLine, GfxStyle.DASH);
            GfxManager.getPlatform().addToVirtualGroup(gfxObject, relationLine);
        }
    }
    private GfxObject createText(String text, boolean isLeft, RelationshipArtifactPart part) {
        GfxObject textGfxObject = GfxManager.getPlatform().buildText(text);

        GfxManager.getPlatform().setStroke(textGfxObject, ThemeManager.getBackgroundColor(), 0);
        GfxManager.getPlatform().setFillColor(textGfxObject, ThemeManager.getForegroundColor());

        Log.trace("Creating text : " + text + " at " +  getTextX(textGfxObject, isLeft) + " : " + getTextY(textGfxObject, isLeft));
        GfxManager.getPlatform().translate(textGfxObject, getTextX(textGfxObject, isLeft), getTextY(textGfxObject, isLeft));
        RelationshipArtifactPart.setGfxObjectTextForPart(textGfxObject, part);
        gfxObjectPart.put(part, textGfxObject);
        return textGfxObject;
    }

    public void setName(String name) {
        relation.setName(name);
    }
    public void setLeftCardinality(String leftCardinality) {
        relation.setLeftCardinality(leftCardinality);
    }
    public void setRightCardinality(String rightCardinality) {
        relation.setRightCardinality(rightCardinality);
    }
    public void setRightRole(String rightRole) {
        relation.setRightRole(rightRole);
    }
    public void setLeftRole(String leftRole) {
        relation.setLeftRole(leftRole);
    }
    public void setLeftConstraint(String leftConstraint) {
        relation.setLeftConstraint(leftConstraint);
    }
    public void setRightConstraint(String rightConstraint) {
        relation.setRightConstraint(rightConstraint);
    }

    public void setRelationClass(ClassArtifact relationClass) {
        this.relationClass = relationClass;
        if (relationClass != null)
            relationClass.addDependency(this, null);
    }
}
