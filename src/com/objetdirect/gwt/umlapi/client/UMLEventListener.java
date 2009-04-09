/**
 * 
 */
package com.objetdirect.gwt.umlapi.client;

import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;

/**
 * This listener allow subscription on UML related events
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public interface UMLEventListener {

    /**
     * This method is called when a new link is made
     * 
     * @param linkArtifact
     *            The new link being created
     * @return <ul>
     *         <li><b>True</b> to confirm the link creation</li>
     *         <li><b>False</b> to prevent it</li>
     *         </ul>
     * 
     */
    public boolean onNewLink(LinkArtifact linkArtifact);

    /**
     * This method is called when a new link is made
     * 
     * @param umlArtifact
     *            The new artifact being created
     * @return <ul>
     *         <li><b>True</b> to confirm the link creation</li>
     *         <li><b>False</b> to prevent it</li>
     *         </ul>
     */
    public boolean onNewUMLArtifact(UMLArtifact umlArtifact);

}
