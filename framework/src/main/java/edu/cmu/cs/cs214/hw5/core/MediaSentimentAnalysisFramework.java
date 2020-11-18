package edu.cmu.cs.cs214.hw5.core;

import com.aliasi.classify.Classification;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.LMClassifier;
import com.aliasi.lm.NGramProcessLM;
import com.aliasi.stats.MultivariateEstimator;
import com.aliasi.util.AbstractExternalizable;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class represents a concrete implementation of MediaFramework. This
 * framework implementation analyses the sentiment in the reviews of
 * different types of media (such as movies, TV shows, music, video games, etc).
 */
public class MediaSentimentAnalysisFramework implements MediaFramework{
    // Constants
    private static final String[] SENTIMENTS = {"NEGATIVE", "POSITIVE"};
    private static final int NGRAM_SIZE = 8;

    // The path of the trained sentiment analysis model file.
    private static final String SENTIMENT_MODEL_PATH =
            "framework/src/main/java/edu/cmu/cs/cs214/hw5/model" +
                    "/sentimentAnalysis.model";
    private static final String FINAL_SENTIMENT_MODEL_PATH = System.getProperty(
            "user.dir") + SENTIMENT_MODEL_PATH;

    // Listeners
    private MediaFrameworkChangeListener frameworkChangeListener;

    // Attributes
    private MediaPlugin mediaPlugin;
    private List<VisualizationPlugin> visualPluginList = new ArrayList<>();
    private String mediaTitle;
    private MediaInfo mediaInfo;

    // Maps
    private final Map<String, MediaPlugin> mediaTypeToMediaPluginMap =
            new HashMap<>();
    private final Map<String, VisualizationPlugin> mediaTypeToVisualPluginMap =
            new HashMap<>();

    /**
     * Registers a new {@link MediaPlugin} with the framework and notifies
     * the GUI about the change.
     */
    public void registerMediaPlugin(MediaPlugin plugin) {
        plugin.onRegister(this);
        notifyMediaPluginRegistered(plugin);
        mediaTypeToMediaPluginMap.put(plugin.getPluginName(), plugin);
    }

    /**
     * Registers a new {@link VisualizationPlugin} with the framework and notifies
     * the GUI about the change.
     */
    public void registerVisualPlugin(VisualizationPlugin plugin) {
        plugin.onRegister(this);
        notifyVisualPluginRegistered(plugin);
        mediaTypeToVisualPluginMap.put(plugin.getPluginName(), plugin);
    }

    /**
     * Notify GUI that a new MediaPlugin is registered.
     *
     * @param plugin Newly registered MediaPlugin.
     */
    private void notifyMediaPluginRegistered(MediaPlugin plugin){
        frameworkChangeListener.mediaPluginRegistered(plugin);
    }

    /**
     * Notify GUI that a new VisualizationPlugin is registered.
     *
     * @param plugin Newly registered VisualizationPlugin.
     */
    private void notifyVisualPluginRegistered(VisualizationPlugin plugin){
        frameworkChangeListener.visualPluginRegistered(plugin);
    }

    @Override
    public void setMediaType(String mediaType) {
        if(mediaTypeToMediaPluginMap.containsKey(mediaType)){
            mediaPlugin = mediaTypeToMediaPluginMap.get(mediaType);
        } else {
            // Intimate GUI with appropriate message.
            frameworkChangeListener.errorMessage("This media type is not " +
                    "supported by the framework. Please select a different " +
                    "one.");
        }
    }

    @Override
    public void setVisualType(List<String> visualTypeList) {
        this.visualPluginList.clear();
        for(String visualType : visualTypeList) {
            if (mediaTypeToVisualPluginMap.containsKey(visualType)) {
                VisualizationPlugin curVisualPlugin =
                        mediaTypeToVisualPluginMap.get(visualType);
                this.visualPluginList.add(curVisualPlugin);
            }
        }
    }

    @Override
    public void setMediaTitle(String mediaTitle) {
        this.mediaTitle = mediaTitle;
        initFlow();
    }

    /**
     *  This method starts the flow of the Framework. It does 3 things:
     *  1. First, it calls the data plugin and gets an instance of MediaInfo.
     *  2. Then it performs sentiment analysis on the instance of MediaInfo
     *  returned by the data plugin.
     *  3. It visualizes the processed MediaInfo instance.
     */
    private void initFlow(){
        if(mediaInfo instanceof MediaPlugin){
            try {
                mediaInfo = mediaPlugin.fetchData(mediaTitle);
            } catch (IllegalStateException e) {
                frameworkChangeListener.errorMessage("There was some internal" +
                        " issue. Please try again.");
                return;
            } catch (IllegalArgumentException e) {
                frameworkChangeListener.errorMessage("The media title " +
                        "selected does nt exist in the datasource. Please try" +
                        " a different title.");
                return;
            }
        }
        try{
            extractSentiment();
        }catch(IllegalStateException e){
            return;
        }
        List<JPanel> visualFrameList = executeVisualPlugin();
        if(visualFrameList.isEmpty()){
            // Intimate GUI with appropriate Message.
            frameworkChangeListener.errorMessage("There are no visual plugins" +
                    " registered.");
        } else {
            // Intimate GUI with the correct Visuals.
            frameworkChangeListener.updateGUIWithJPanelList(visualFrameList);
        }
    }

    /**
     * Sets the framework's {@link MediaFrameworkChangeListener}  listener to
     * be notified about changes made to the framework's state.
     */
    public void setFrameworkChangeListener(MediaFrameworkChangeListener listener) {
        frameworkChangeListener = listener;
    }

    /**
     * This is an utility method which calls the appropriate visual plugin for
     * each type of visual plugin selected by the user.
     *
     * @return A list of JFrames, one for each visual plugin that the user
     * selected.
     */
    private List<JPanel> executeVisualPlugin(){
        List<JPanel> visualPanelList = new ArrayList<>();
        for(VisualizationPlugin visualPlugin : visualPluginList) {
            JPanel curVisualFrame = visualPlugin.visualize(mediaInfo);
            visualPanelList.add(curVisualFrame);
        }
        return visualPanelList;
    }

    /**
     * This method extracts the sentiment from the reviews of the movie which
     * the user has selected. It operates on the MediaInfo instance returned
     * by the data plugin and populates the sentiment info back in that
     * instance.
     */
    private void extractSentiment() {
        // Do Sentiment Analysis
        LMClassifier<NGramProcessLM, MultivariateEstimator> mClassifier =
                DynamicLMClassifier.createNGramProcess(SENTIMENTS,NGRAM_SIZE);
        try {
            mClassifier = (LMClassifier<NGramProcessLM, MultivariateEstimator>)
                    AbstractExternalizable.readObject(
                            new File(SENTIMENT_MODEL_PATH));
        } catch (IOException e){
            frameworkChangeListener.errorMessage("Cannot Perform sentiment " +
                    "Analysis. Please try again.");
            throw new IllegalStateException("The sentiment analysis model " +
                    "file cannot be found.");
        } catch (ClassNotFoundException e){
            frameworkChangeListener.errorMessage("Cannot Perform sentiment " +
                    "Analysis. Please try again.");
            throw  new IllegalStateException("The sentiment analysis model " +
                    "file is corrupted.");
        }

        List<String> reviews = mediaInfo.getReviews();
        Map<String, Integer> mediaSentimentMap = new HashMap<>();
        for(String review : reviews){
            Classification classification = mClassifier.classify(review);
            String resultCategory = classification.bestCategory();
            if(mediaSentimentMap.containsKey(resultCategory)){
                int curCount = mediaSentimentMap.get(resultCategory);
                mediaSentimentMap.put(resultCategory, curCount + 1);
            } else {
                mediaSentimentMap.put(resultCategory, 1);
            }
        }

        // Add the map to MediaInfo instance returned by MediaPlugin.
        mediaInfo.setSentimentData(mediaSentimentMap);
    }
}