package com.objetdirect.gwt.umlapi.client.artifacts.links;
import java.util.ArrayList;
import java.util.HashMap;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks.AggregationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks.AssociationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks.CompositionLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks.DependencyLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks.GeneralizationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks.RealizationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.editors.RelationFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
/**
 * @author  florian
 */
public abstract class RelationLinkArtifact extends LinkArtifact {

    public static RelationLinkArtifact makeLinkArtifact(ClassArtifact left, ClassArtifact right, RelationKind kind) {
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
    public enum RelationLinkArtifactPart {
        LEFT_CARDINALITY("Cardinality", true) {
            @Override
            public String getText(Relation relation) { return relation.getLeftCardinality(); }
            @Override
            public void setText(Relation relation, String text) { relation.setLeftCardinality(text); }
        },  
        LEFT_CONSTRAINT("Constraint", true) {
            @Override
            public String getText(Relation relation) { return relation.getLeftConstraint(); } 
            @Override
            public void setText(Relation relation, String text) { relation.setLeftConstraint(text); }
        },  
        LEFT_ROLE("Role", true) {
            @Override
            public String getText(Relation relation) { return relation.getLeftRole(); } 
            @Override
            public void setText(Relation relation, String text) { relation.setLeftRole(text); }
        },  
        NAME("Name", false) {
            @Override
            public String getText(Relation relation) { return relation.getName(); } 
            @Override
            public void setText(Relation relation, String text) { relation.setName(text); }
        },  
        RIGHT_CARDINALITY("Cardinality", false) {
            @Override
            public String getText(Relation relation) { return relation.getRightCardinality(); }
            @Override
            public void setText(Relation relation, String text) { relation.setRightCardinality(text); }
        },  
        RIGHT_CONSTRAINT("Constraint", false) {
            @Override
            public String getText(Relation relation) { return relation.getRightConstraint(); } 
            @Override
            public void setText(Relation relation, String text) { relation.setRightConstraint(text); }
        },  
        RIGHT_ROLE("Role", false)  {
            @Override
            public String getText(Relation relation) { return relation.getRightRole(); } 
            @Override
            public void setText(Relation relation, String text) { relation.setRightRole(text); }
        };
        private static HashMap<GfxObject, RelationLinkArtifactPart> textGfxObject = new HashMap<GfxObject, RelationLinkArtifactPart>();
        private String name;        
        private boolean isLeft;
        private RelationLinkArtifactPart(String name, boolean isLeft) {
            this.name= name;
            this.isLeft = isLeft;
        }
        /**
         * @return the name
         */
        public String getName() {
            return name;
        }


        /**
         * @return the isLeft
         */
        public boolean isLeft() {
            return isLeft;
        }
        public abstract String getText(Relation relation);
        public abstract void setText(Relation relation, String text);
        public static void setGfxObjectTextForPart(GfxObject text, RelationLinkArtifactPart part) {
            textGfxObject.put(text, part);
        }

        public static RelationLinkArtifactPart getPartForGfxObject(GfxObject text) {
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
    private HashMap<RelationLinkArtifactPart, GfxObject> gfxObjectPart = new HashMap<RelationLinkArtifactPart, GfxObject>();

    public RelationLinkArtifact(ClassArtifact left, ClassArtifact right) {
        this.leftClassArtifact = left;
        left.addDependency(this, right);
        this.rightClassArtifact = right;
        if(right != left) right.addDependency(this, left);
    }

    public void edit(RelationLinkArtifactPart part) {
        String defaultText;
        switch(part) {
            case NAME: defaultText = leftClassArtifact.getClassName() + "-" + rightClassArtifact.getClassName(); break;
            case LEFT_CARDINALITY:
            case RIGHT_CARDINALITY:
                defaultText = "0..*"; break;
            case LEFT_CONSTRAINT:
            case RIGHT_CONSTRAINT:
                defaultText = "{union}"; break;
            case LEFT_ROLE:
            case RIGHT_ROLE:
                defaultText = "role"; break;
            default:
                defaultText = "?";
                    
               
        }
        part.setText(relation, defaultText);
        rebuildGfxObject();
        edit(gfxObjectPart.get(part));		
    }

    public void edit(GfxObject gfxObject) {
        RelationLinkArtifactPart editPart = RelationLinkArtifactPart.getPartForGfxObject(gfxObject);
        if(editPart ==  null) {
            edit(RelationLinkArtifactPart.NAME);
        } else {
            RelationFieldEditor editor = new RelationFieldEditor(canvas, this, editPart);
            editor.startEdition(editPart.getText(relation), 
                    GfxManager.getPlatform().getXFor(gfxObject),
                    GfxManager.getPlatform().getYFor(gfxObject) - GfxManager.getPlatform().getHeightFor(gfxObject),
                    GfxManager.getPlatform().getWidthFor(gfxObject) + OptionsManager.getRectangleXTotalPadding(), false);
        }
    }
    public void setPartContent(RelationLinkArtifactPart part, String newContent) {
        part.setText(relation, newContent);
    }
    public ClassArtifact getLeftClassArtifact() {
        return leftClassArtifact;
    }
    public ClassArtifact getRightClassArtifact() {
        return rightClassArtifact;
    }


    public void select() {
        GfxManager.getPlatform().moveToFront(textVirtualGroup);
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

        Point relative_point1 = leftPoint;
        Point relative_point2 = rightPoint;
        int textWidth =  GfxManager.getPlatform().getWidthFor(text);
        if(!isLeft) {
            relative_point1 = rightPoint;
            relative_point2 = leftPoint;
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
        Point relative_point1 = leftPoint;
        Point relative_point2 = rightPoint;
        if(!isLeft) {
            relative_point1 = rightPoint;
            relative_point2 = leftPoint;
        }
        int textHeight = GfxManager.getPlatform().getHeightFor(text);
        int delta = current_delta;
        current_delta += 8; //TODO : Fix Height
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
        ArrayList<Point> linePoints = new ArrayList<Point>();;
        boolean isComputationNeededOnLeft = adornmentLeft != LinkAdornment.NONE || adornmentLeft.isCrossed() || (relation.getLeftCardinality() + relation.getLeftConstraint() + relation.getLeftRole() != "");
        boolean isComputationNeededOnRight = adornmentRight != LinkAdornment.NONE || adornmentRight.isCrossed() || (relation.getRightCardinality() + relation.getRightConstraint() + relation.getRightRole() != "");;
        if(leftClassArtifact != rightClassArtifact) {
            if(isComputationNeededOnLeft && isComputationNeededOnRight) {
                linePoints = GeometryManager.getPlatform().getLineBetween(leftClassArtifact, rightClassArtifact); 
                leftPoint = linePoints.get(0);
                rightPoint = linePoints.get(1);
            }
            else if(isComputationNeededOnLeft) {
                rightPoint = rightClassArtifact.getCenter();
                leftPoint = GeometryManager.getPlatform().getPointForLine(leftClassArtifact, rightPoint); 
            }
            else if(isComputationNeededOnRight) {
                leftPoint = leftClassArtifact.getCenter();
                rightPoint = GeometryManager.getPlatform().getPointForLine(leftClassArtifact, leftPoint); 
            }
            else {
                leftPoint = leftClassArtifact.getCenter();
                rightPoint = rightClassArtifact.getCenter();
            }
            line = GfxManager.getPlatform().buildLine(leftPoint.getX(), leftPoint.getY(), rightPoint.getX(), rightPoint.getY());
        } else {
            linePoints = GeometryManager.getPlatform().getReflexiveLineFor(leftClassArtifact);
            leftPoint = linePoints.get(1);
            rightPoint = linePoints.get(2);
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
            if (isComputationNeededOnLeft)
                GfxManager.getPlatform().addToVirtualGroup(arrowVirtualGroup, GeometryManager.getPlatform().buildAdornment(leftPoint, rightPoint, adornmentLeft));
            if (isComputationNeededOnRight) 
                GfxManager.getPlatform().addToVirtualGroup(arrowVirtualGroup, GeometryManager.getPlatform().buildAdornment(rightPoint, leftPoint, adornmentRight));
        } else {
            if (isComputationNeededOnLeft)
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
                    (leftPoint.getX() + rightPoint.getX() -  (GfxManager.getPlatform().getWidthFor(nameGfxObject))) / 2, 
                    (leftPoint.getY() + rightPoint.getY()) / 2);
            RelationLinkArtifactPart.setGfxObjectTextForPart(nameGfxObject, RelationLinkArtifactPart.NAME);
            gfxObjectPart.put(RelationLinkArtifactPart.NAME, nameGfxObject);
        }
        current_delta = 0;
        if (relation.getLeftCardinality() != "")
            GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getLeftCardinality(), RelationLinkArtifactPart.LEFT_CARDINALITY));
        if (relation.getLeftConstraint() != "") 
            GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getLeftConstraint(), RelationLinkArtifactPart.LEFT_CONSTRAINT));
        if (relation.getLeftRole() != "") 
            GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getLeftRole(), RelationLinkArtifactPart.LEFT_ROLE));
        current_delta = 0;
        if (relation.getRightCardinality() != "") 
            GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getRightCardinality(), RelationLinkArtifactPart.RIGHT_CARDINALITY));
        if (relation.getRightConstraint() != "") 
            GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getRightConstraint(), RelationLinkArtifactPart.RIGHT_CONSTRAINT));
        if (relation.getRightRole() != "") 
            GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, createText(relation.getRightRole(), RelationLinkArtifactPart.RIGHT_ROLE));
        GfxManager.getPlatform().moveToBack(gfxObject);
    }
    private GfxObject createText(String text, RelationLinkArtifactPart part) {
        GfxObject textGfxObject = GfxManager.getPlatform().buildText(text);

        GfxManager.getPlatform().setStroke(textGfxObject, ThemeManager.getBackgroundColor(), 0);
        GfxManager.getPlatform().setFillColor(textGfxObject, ThemeManager.getForegroundColor());

        Log.trace("Creating text : " + text + " at " +  getTextX(textGfxObject, part.isLeft) + " : " + getTextY(textGfxObject, part.isLeft));
        GfxManager.getPlatform().translate(textGfxObject, getTextX(textGfxObject, part.isLeft), getTextY(textGfxObject, part.isLeft));
        RelationLinkArtifactPart.setGfxObjectTextForPart(textGfxObject, part);
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
    @Override
    public void removeCreatedDependency() {
        leftClassArtifact.removeDependency(this);
        rightClassArtifact.removeDependency(this);
    }
    private Command createCommand(final RelationLinkArtifactPart relationArtifactPart) {       
        return new Command() {
            public void execute() {
                edit(relationArtifactPart);
            }
        };
    }
    private Command editCommand(final RelationLinkArtifactPart relationArtifactPart) {       
        return new Command() {
            public void execute() {
                edit(gfxObjectPart.get(relationArtifactPart));
            }
        };
    }
    private Command deleteCommand(final RelationLinkArtifactPart relationArtifactPart) {       
        return new Command() {
            public void execute() {
                relationArtifactPart.setText(relation, "");
                rebuildGfxObject();
            }
        };
    }
    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.links.RelationshipLinkArtifact#getRightMenu()
     */
    @Override
    public MenuBarAndTitle getRightMenu() {
        MenuBarAndTitle rightMenu = new MenuBarAndTitle();
        rightMenu.setName(relation.getRelationKind().getName() + " " + leftClassArtifact.getClassName() + 
                " " + adornmentLeft.getShape().getIdiom() +  "-" + adornmentRight.getShape().getIdiom(true) + " "
                + rightClassArtifact.getClassName());
        MenuBar leftSide = new MenuBar(true);
        MenuBar rightSide = new MenuBar(true);

        for(RelationLinkArtifactPart relationLinkArtifactPart : RelationLinkArtifactPart.values()) {

            MenuBar editDelete = new MenuBar(true);
            if(relationLinkArtifactPart.getText(relation) != "") {
                editDelete.addItem("Edit", editCommand(relationLinkArtifactPart));
                editDelete.addItem("Delete", deleteCommand(relationLinkArtifactPart));
            }
            else {
                editDelete.addItem("Create", createCommand(relationLinkArtifactPart));
            }
            if(relationLinkArtifactPart.isLeft) {
                leftSide.addItem(relationLinkArtifactPart.getName(), editDelete);
            } else {
                if(relationLinkArtifactPart != RelationLinkArtifactPart.NAME)
                rightSide.addItem(relationLinkArtifactPart.getName(), editDelete);
                else
                    rightMenu.addItem(relationLinkArtifactPart.getName(), editDelete);
            }
        }
        rightMenu.addItem(leftClassArtifact.getClassName() + " side", leftSide);
        rightMenu.addItem(rightClassArtifact.getClassName() + " side", rightSide);

        return rightMenu;
    }
}