package edu.cmu.cs.cs214.hw5.core;

import javax.swing.*;

public interface VisualizationPlugin {

    /**
     * This method is meant to be executed when a new VisualizationPlugin is
     * registered with a MediaFramework. Ideally, it should set the
     * MediaFramework instance in the VisualizationPlugin. In addition, it can
     * do other initializations.
     *
     * @param mediaFramework The mediaFramework with which the current
     *                       VisualizationPlugin is registered.
     */
    void onRegister(MediaFramework mediaFramework);

    /**
     * This method is meant to be executed when a VisualizationPlugin is closed/
     * destroyed.
     */
    void onClose();

    /**
     * Sets the name of the display
     */
    void setName();

    /**
     * Visualize the data.
     *
     * @param mediaInfo
     * @return
     */
    JPanel visualize(MediaInfo mediaInfo);

    /**
     * Gets the name of the visual plugin.
     */
    String getPluginName();
}