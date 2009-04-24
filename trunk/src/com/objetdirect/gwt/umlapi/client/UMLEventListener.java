package com.objetdirect.gwt.umlapi.client;

import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;

/**
 * This listener allow subscription on UML related events
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public interface UMLEventListener {

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
    
    /**
     * This method is called when a link kind change
     * 
     * @param linkArtifact The link which type is changing
     * @param oldKind The old {@link RelationKind}
     * @param newKind The old {@link RelationKind}
     * 
     * @return <ul>
     *         <li><b>True</b> to confirm the link kind change</li>
     *         <li><b>False</b> to prevent it</li>
     *         </ul>
     */
    public boolean onLinkKindChange(LinkArtifact linkArtifact, RelationKind oldKind, RelationKind newKind);

}
