package edu.cmu.cs.cs214.hw5;

import edu.cmu.cs.cs214.hw5.core.MediaPlugin;
import edu.cmu.cs.cs214.hw5.core.MediaSentimentAnalysisFramework;
import edu.cmu.cs.cs214.hw5.core.VisualizationPlugin;
import edu.cmu.cs.cs214.hw5.gui.MediaFrameworkPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Main class to start the Framework with GUI.
 */
public class Main {
    private static final String MEDIA_FRAMEWORK = "MEDIA";
    /**
     * Main method to start the Framework with GUI.
     *
     * @param args None required (or expected).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowFramework);
    }

    /**
     * Create the Framework GUI.
     */
    private static void createAndShowFramework() {
        // Create and set-up the window.
        JFrame frame = new JFrame(MEDIA_FRAMEWORK);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Create concrete framework
        MediaSentimentAnalysisFramework mediaFramework =
                new MediaSentimentAnalysisFramework();

        // Create and set up the content pane.
        MediaFrameworkPanel frameworkPanel =
                new MediaFrameworkPanel(mediaFramework);

        // Add the Listener to the Framework
        mediaFramework.setFrameworkChangeListener(frameworkPanel);

        // Load data plugins
        List<MediaPlugin> mediaPlugins = loadMediaPlugins();
        mediaPlugins.forEach(mediaFramework::registerMediaPlugin);

        // Load visual plugins
        List<VisualizationPlugin> visualPlugins = loadVisualPlugins();
        visualPlugins.forEach(mediaFramework::registerVisualPlugin);

        frameworkPanel.setOpaque(true);
        frame.setContentPane(frameworkPanel);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Load media/data plugins listed in META-INF/services/...
     *
     * @return List of instantiated data plugins.
     */
    private static List<MediaPlugin> loadMediaPlugins() {
        ServiceLoader<MediaPlugin> plugins =
                ServiceLoader.load(MediaPlugin.class);
        List<MediaPlugin> result = new ArrayList<>();
        System.out.println(plugins.toString());
        /*MediaPlugin pluginOne = plugins.findFirst().get();*/
        for (MediaPlugin plugin : plugins) {
            System.out.println("Loaded plugin " + plugin.getPluginName());
            result.add(plugin);
        }
        return result;
    }

    /**
     * Load visual plugins listed in META-INF/services/...
     *
     * @return List of instantiated visual plugins.
     */
    private static List<VisualizationPlugin> loadVisualPlugins() {
        ServiceLoader<VisualizationPlugin> plugins =
                ServiceLoader.load(VisualizationPlugin.class);
        List<VisualizationPlugin> result = new ArrayList<>();
        for (VisualizationPlugin plugin : plugins) {
            System.out.println("Loaded plugin " + plugin.getPluginName());
            result.add(plugin);
        }
        return result;
    }
}