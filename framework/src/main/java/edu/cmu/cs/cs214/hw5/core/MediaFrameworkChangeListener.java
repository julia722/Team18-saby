package edu.cmu.cs.cs214.hw5.core;

import javax.swing.*;
import java.util.List;

/**
 * This is the interface using which MediaFramework sends messages to the
 * MediaFrameworkGUI.
 */
public interface MediaFrameworkChangeListener {
    /**
     * Communication from MediaFramework to the MediaFrameworkGUI when a new
     * MediaPlugin is registered.
     *
     * @param mediaPlugin The newly registered MediaPlugin.
     */
    void mediaPluginRegistered(MediaPlugin mediaPlugin);

    /**
     * Communication from MediaFramework to the MediaFrameworkGUI when a new
     * VisualizationPlugin is registered.
     *
     * @param visualPlugin The newly registered VisualizationPlugin.
     */
    void visualPluginRegistered(VisualizationPlugin visualPlugin);

    /**
     * Communication from MediaFramework to the MediaFrameworkGUI to display
     * the visualizations.
     *
     * @param jPanelList A list of JPanels collected form the visualPlugins.
     */
    void updateGUIWithJPanelList(List<JPanel> jPanelList);

    /**
     * Populate the GUI with an error message.
     *
     * @param message The error message.
     */
    void errorMessage(String message);
}