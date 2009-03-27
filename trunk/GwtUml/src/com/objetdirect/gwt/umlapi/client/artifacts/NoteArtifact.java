package com.objetdirect.gwt.umlapi.client.artifacts;
import java.util.LinkedHashMap;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.editors.NoteFieldEditor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Note;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
/**
 * @author  florian
 */
public class NoteArtifact extends BoxArtifact {
	/**
	 * @uml.property  name="note"
	 * @uml.associationEnd  
	 */
	private Note note;
	/**
	 * @uml.property  name="borderPath"
	 * @uml.associationEnd  
	 */
	GfxObject borderPath;
	/**
	 * @uml.property  name="contentText"
	 * @uml.associationEnd  
	 */
	GfxObject contentText;
	/**
	 * @uml.property  name="cornerPath"
	 * @uml.associationEnd  
	 */
	GfxObject cornerPath;
	int width;
	int height;


	public NoteArtifact(String content) {
		height = 0;
		width = 0;
		note = new Note(content);
	}
	public String getContent() {
		return note.getText();
	}
	@Override
	public int getHeight() {
		return height;
	}
	@Override
	public int[] getOpaque() {
		int[] opaque = new int[] { getX(), getY(), getX(),
				getY() + getHeight(), getX() + getWidth(),
				getY() + getHeight(), getX() + getWidth(),
				getY() + getCornerHeight(),
				getX() + getWidth() - getCornerWidth(), getY() };
		return opaque;
	}
	@Override
	public GfxObject getOutline() {
		GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
		GfxObject outlineBorderPath = getBorderPath();
		GfxObject outlineCornerPath = getCornerPath();
		GfxManager.getPlatform().addToVirtualGroup(vg, outlineBorderPath);
		GfxManager.getPlatform().addToVirtualGroup(vg, outlineCornerPath);
		GfxManager.getPlatform().setStrokeStyle(outlineBorderPath, GfxStyle.DASH);
		GfxManager.getPlatform().setStrokeStyle(outlineCornerPath, GfxStyle.DASH);
		GfxManager.getPlatform().setStroke(outlineBorderPath, ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(outlineCornerPath, ThemeManager.getHighlightedForegroundColor(), 1);
		return vg;
	}
	public LinkedHashMap<String, Command> getRightMenu() {
		LinkedHashMap<String, Command> rightMenu = new LinkedHashMap<String, Command>();
		Command doNothing = new Command() {
			public void execute() {
			}
		};
		Command remove = new Command() {
			public void execute() {
				getCanvas().removeSelected();
			}
		};
		rightMenu.put("Note", doNothing);
		rightMenu.put("-", null);
		rightMenu.put("> Edit", doNothing);
		rightMenu.put("> Delete", remove);
		return rightMenu;
	}
	@Override
	public int getWidth() {
		return width;
	}
	public void select() {
		GfxManager.getPlatform().moveToFront(gfxObject);
		GfxManager.getPlatform().setStroke(borderPath, ThemeManager.getHighlightedForegroundColor(), 2);
		GfxManager.getPlatform().setStroke(cornerPath, ThemeManager.getHighlightedForegroundColor(), 2);
	}
	public void setContent(String content) {		
		note.setText(content);
	}
	public void unselect() {
		GfxManager.getPlatform().setStroke(borderPath, ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(cornerPath, ThemeManager.getForegroundColor(), 1);
	}
	void createNoteText() {
		height = 0;
		width = 0;
		String[] noteMultiLine = note.getText().split("\n");
		contentText = GfxManager.getPlatform().buildVirtualGroup();
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, contentText);

		for(String noteLine : noteMultiLine) {				
			GfxObject textLine = GfxManager.getPlatform().buildText(noteLine);
			GfxManager.getPlatform().addToVirtualGroup(contentText, textLine);	
			GfxManager.getPlatform().setFont(textLine, OptionsManager.getFont());
			GfxManager.getPlatform().setFillColor(textLine, ThemeManager.getForegroundColor());
			int thisLineWidth =  GfxManager.getPlatform().getWidthFor(textLine);
			int thisLineHeight =  GfxManager.getPlatform().getHeightFor(textLine);
			
			GfxManager.getPlatform().translate(textLine, OptionsManager.getTextLeftPadding(), OptionsManager.getTextTopPadding() + height + thisLineHeight);
			thisLineWidth += OptionsManager.getTextXTotalPadding();
			thisLineHeight += OptionsManager.getTextYTotalPadding();
			width  = thisLineWidth > width ? thisLineWidth : width;
			height += thisLineHeight;	
		}
		height += OptionsManager.getRectangleYTotalPadding();
		width += OptionsManager.getRectangleXTotalPadding() + getCornerWidth();
		
	}
	@Override
	protected void buildGfxObject() {
		createNoteText();
		borderPath = getBorderPath();	
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, borderPath);
		cornerPath = getCornerPath();
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, cornerPath);
		GfxManager.getPlatform().translate(contentText, OptionsManager.getRectangleLeftPadding(), OptionsManager.getRectangleTopPadding());
		GfxManager.getPlatform().moveToFront(contentText);
	}
	/**
	 * @return
	 * @uml.property  name="borderPath"
	 */
	protected GfxObject getBorderPath() {
		GfxObject thisBorderPath = GfxManager.getPlatform().buildPath();		
		GfxManager.getPlatform().moveTo(thisBorderPath, 0, 0);
		GfxManager.getPlatform().lineTo(thisBorderPath, width - getCornerWidth(), 0);
		GfxManager.getPlatform().lineTo(thisBorderPath, width, getCornerHeight());
		GfxManager.getPlatform().lineTo(thisBorderPath, width, height);
		GfxManager.getPlatform().lineTo(thisBorderPath, 0, height);
		GfxManager.getPlatform().lineTo(thisBorderPath, 0, 0);
		GfxManager.getPlatform().setFillColor(thisBorderPath, ThemeManager.getBackgroundColor());
		GfxManager.getPlatform().setStroke(thisBorderPath, ThemeManager.getForegroundColor(), 1);
		return thisBorderPath;
	}
	/**
	 * @return
	 * @uml.property  name="cornerPath"
	 */
	protected GfxObject getCornerPath() {
		GfxObject thisCornerPath = GfxManager.getPlatform().buildPath();		
		GfxManager.getPlatform().moveTo(thisCornerPath, width - getCornerWidth(), 0);
		GfxManager.getPlatform().lineTo(thisCornerPath, width - getCornerWidth(),getCornerHeight());
		GfxManager.getPlatform().lineTo(thisCornerPath, width, getCornerHeight());
		GfxManager.getPlatform().setFillColor(thisCornerPath, ThemeManager.getBackgroundColor());
		GfxManager.getPlatform().setStroke(thisCornerPath, ThemeManager.getForegroundColor(), 1);
		return thisCornerPath;
	}
	private int getCornerHeight() {
		return height / 3;
	}
	private int getCornerWidth() {
		return height / 3;
	}
	public void edit(GfxObject gfxObject, int x, int y) {
		NoteFieldEditor editor = new NoteFieldEditor(canvas, this);
		editor.setHeightForMultiLine(height - OptionsManager.getTextYTotalPadding() - OptionsManager.getRectangleYTotalPadding());
		editor.startEdition(note.getText(), this.x + OptionsManager.getTextLeftPadding() + OptionsManager.getRectangleLeftPadding(),
				this.y + OptionsManager.getTextYTotalPadding() + OptionsManager.getRectangleTopPadding(), 
				width - OptionsManager.getTextXTotalPadding() - OptionsManager.getRectangleXTotalPadding(), true);

	}
}
