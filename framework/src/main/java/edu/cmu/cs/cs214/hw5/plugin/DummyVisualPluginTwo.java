package edu.cmu.cs.cs214.hw5.plugin;

import edu.cmu.cs.cs214.hw5.core.MediaFramework;
import edu.cmu.cs.cs214.hw5.core.MediaInfo;
import edu.cmu.cs.cs214.hw5.core.VisualizationPlugin;

import javax.swing.*;

public class DummyVisualPluginTwo implements VisualizationPlugin {
    public void onRegister(MediaFramework mediaFramework) {
        return;
    }

    /**
     * This method is meant to be executed when a VisualizationPlugin is closed/
     * destroyed.
     */
    public void onClose() {
        return;
    }

    /**
     * Sets the name of the display
     */
    public void setName() {
        return;
    }

    /**
     * Visualizes the data.
     */
    public JPanel visualize(MediaInfo mediaInfo) {
        return new JPanel();
    }

    /**
     * Gets the name of the visual plugin.
     */
    public String getPluginName() {
        return "Visual2";
    }
}
