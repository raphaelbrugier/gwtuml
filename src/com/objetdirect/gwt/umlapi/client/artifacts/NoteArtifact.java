package com.objetdirect.gwt.umlapi.client.artifacts;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.editors.NoteFieldEditor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Note;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager.QualityLevel;

/**
 * @author florian
 */
public class NoteArtifact extends BoxArtifact {
    GfxObject borderPath;
    GfxObject contentText;
    GfxObject cornerPath;
    int height;
    private final Note note;
    int width;

    public NoteArtifact(final String content) {
	this.height = 0;
	this.width = 0;
	this.note = new Note(content);
    }

    @Override
    protected void buildGfxObject() {
	createNoteText();
	this.borderPath = getBorderPath();
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.borderPath);
	this.cornerPath = getCornerPath();
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.cornerPath);
	GfxManager.getPlatform().translate(this.contentText,
		OptionsManager.getRectangleLeftPadding(),
		OptionsManager.getRectangleTopPadding());
	GfxManager.getPlatform().moveToFront(this.contentText);
    }

    void createNoteText() {
	this.height = 0;
	this.width = 0;
	final String[] noteMultiLine = this.note.getText().split("\n");
	this.contentText = GfxManager.getPlatform().buildVirtualGroup();
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.contentText);

	for (final String noteLine : noteMultiLine) {
	    final GfxObject textLine = GfxManager.getPlatform().buildText(
		    noteLine);
	    GfxManager.getPlatform().addToVirtualGroup(this.contentText, textLine);
	    GfxManager.getPlatform()
		    .setFont(textLine, OptionsManager.getFont());
	    GfxManager.getPlatform().setStroke(textLine,
		    ThemeManager.getBackgroundColor(), 0);
	    GfxManager.getPlatform().setFillColor(textLine,
		    ThemeManager.getForegroundColor());
	    int thisLineWidth = GfxManager.getPlatform().getWidthFor(textLine);
	    int thisLineHeight = GfxManager.getPlatform()
		    .getHeightFor(textLine);

	    GfxManager.getPlatform().translate(
		    textLine,
		    OptionsManager.getTextLeftPadding(),
		    OptionsManager.getTextTopPadding() + this.height
			    + thisLineHeight);
	    thisLineWidth += OptionsManager.getTextXTotalPadding();
	    thisLineHeight += OptionsManager.getTextYTotalPadding();
	    this.width = thisLineWidth > this.width ? thisLineWidth : this.width;
	    this.height += thisLineHeight;
	}
	this.height += OptionsManager.getRectangleYTotalPadding();
	this.width += OptionsManager.getRectangleXTotalPadding() + getCornerWidth();

    }

    @Override
    public void edit(final GfxObject editedGfxObject) {
	final NoteFieldEditor editor = new NoteFieldEditor(this.canvas, this);
	editor.setHeightForMultiLine(this.height
		- OptionsManager.getTextYTotalPadding()
		- OptionsManager.getRectangleYTotalPadding());
	editor.startEdition(this.note.getText(), this.x
		+ OptionsManager.getTextLeftPadding()
		+ OptionsManager.getRectangleLeftPadding(), this.y
		+ OptionsManager.getTextYTotalPadding()
		+ OptionsManager.getRectangleTopPadding(), this.width
		- OptionsManager.getTextXTotalPadding()
		- OptionsManager.getRectangleXTotalPadding(), true);

    }

    private Command editCommand() {
	return new Command() {
	    public void execute() {
		edit(null);
	    }
	};
    }

    protected GfxObject getBorderPath() {
	final GfxObject thisBorderPath = GfxManager.getPlatform().buildPath();
	GfxManager.getPlatform().moveTo(thisBorderPath, 0, 0);
	GfxManager.getPlatform().lineTo(thisBorderPath,
		this.width - getCornerWidth(), 0);
	GfxManager.getPlatform().lineTo(thisBorderPath, this.width,
		getCornerHeight());
	GfxManager.getPlatform().lineTo(thisBorderPath, this.width, this.height);
	GfxManager.getPlatform().lineTo(thisBorderPath, 0, this.height);
	GfxManager.getPlatform().lineTo(thisBorderPath, 0, 0);
	GfxManager.getPlatform().setFillColor(thisBorderPath,
		ThemeManager.getBackgroundColor());
	GfxManager.getPlatform().setStroke(thisBorderPath,
		ThemeManager.getForegroundColor(), 1);
	return thisBorderPath;
    }

    public String getContent() {
	return this.note.getText();
    }

    private int getCornerHeight() {
	return this.height / 3;
    }

    protected GfxObject getCornerPath() {
	final GfxObject thisCornerPath = GfxManager.getPlatform().buildPath();
	GfxManager.getPlatform().moveTo(thisCornerPath,
		this.width - getCornerWidth(), 0);
	GfxManager.getPlatform().lineTo(thisCornerPath,
		this.width - getCornerWidth(), getCornerHeight());
	GfxManager.getPlatform().lineTo(thisCornerPath, this.width,
		getCornerHeight());
	GfxManager.getPlatform().setFillColor(thisCornerPath,
		ThemeManager.getBackgroundColor());
	GfxManager.getPlatform().setStroke(thisCornerPath,
		ThemeManager.getForegroundColor(), 1);
	return thisCornerPath;
    }

    private int getCornerWidth() {
	return this.height / 3;
    }

    @Override
    public int getHeight() {
	return this.height;
    }

    @Override
    public int[] getOpaque() {
	final int[] opaque = new int[] { getX(), getY(), getX(),
		getY() + getHeight(), getX() + getWidth(),
		getY() + getHeight(), getX() + getWidth(),
		getY() + getCornerHeight(),
		getX() + getWidth() - getCornerWidth(), getY() };
	return opaque;
    }

    @Override
    public GfxObject getOutline() {
	if (OptionsManager.qualityLevelIsAlmost(QualityLevel.NORMAL)) {
	    final GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
	    final GfxObject outlineBorderPath = getBorderPath();
	    final GfxObject outlineCornerPath = getCornerPath();
	    GfxManager.getPlatform().addToVirtualGroup(vg, outlineBorderPath);
	    GfxManager.getPlatform().addToVirtualGroup(vg, outlineCornerPath);
	    GfxManager.getPlatform().setStrokeStyle(outlineBorderPath,
		    GfxStyle.DASH);
	    GfxManager.getPlatform().setStrokeStyle(outlineCornerPath,
		    GfxStyle.DASH);
	    GfxManager.getPlatform().setStroke(outlineBorderPath,
		    ThemeManager.getHighlightedForegroundColor(), 1);
	    GfxManager.getPlatform().setStroke(outlineCornerPath,
		    ThemeManager.getHighlightedForegroundColor(), 1);
	    return vg;
	}
	return super.getOutline();

    }

    @Override
    public MenuBarAndTitle getRightMenu() {
	final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
	rightMenu.setName("Note");
	rightMenu.addItem("Edit content", editCommand());
	return rightMenu;
    }

    @Override
    public int getWidth() {
	return this.width;
    }

    @Override
    public void select() {
	GfxManager.getPlatform().moveToFront(this.gfxObject);
	GfxManager.getPlatform().setStroke(this.borderPath,
		ThemeManager.getHighlightedForegroundColor(), 2);
	GfxManager.getPlatform().setStroke(this.cornerPath,
		ThemeManager.getHighlightedForegroundColor(), 2);
    }

    public void setContent(final String content) {
	this.note.setText(content);
    }

    @Override
    public void unselect() {
	GfxManager.getPlatform().setStroke(this.borderPath,
		ThemeManager.getForegroundColor(), 1);
	GfxManager.getPlatform().setStroke(this.cornerPath,
		ThemeManager.getForegroundColor(), 1);
    }
}
