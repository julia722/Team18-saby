package edu.cmu.cs.cs214.hw5.gui;

import edu.cmu.cs.cs214.hw5.core.MediaFramework;
import edu.cmu.cs.cs214.hw5.core.MediaFrameworkChangeListener;
import edu.cmu.cs.cs214.hw5.core.MediaPlugin;
import edu.cmu.cs.cs214.hw5.core.VisualizationPlugin;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the GUI for the media framework.
 */
public class MediaFrameworkPanel extends JPanel implements
        MediaFrameworkChangeListener {
    // Constants
    private static final int MAX_VISUAL_ROW_VISIBLE_COUNT = 5;
    private static final int MAX_VISUALIZATIONS = 3;
    /* DynamicLMClassifier mClassifier;*/

    /*private static final String MOVIE_STRING = "MOVIE";
    private static final String TV_SERIES_STRING = "TV SERIES";
    private static final String DOCUMENTARY_STRING = "DOCUMENTARY";
    private static final String SENTIMENT_STRING = "SENTIMENT";
    private static final String TRAILER_STRING = "TRAILER";
    private static final String WORD_CLOUD_STRING = "WORD CLOUD";*/

    private static final int MAX_MEDIA_TITLE_SIZE = 50;

    // GUI Elements
    private	final JTextField mediaTitleField;
    private JComboBox<String> mediaDropDown;
    /*private final JComboBox<String> visualDropDown;*/
    private JList<String> visualMultipleSelectionList;

    // Lists
    private static final List<String> mediaChoices = new ArrayList<>();
    private static final List<String> visualChoices = new ArrayList<>();
    private final MediaFramework mediaFramework;

    JPanel topPanel;

    /**
     * The constructor for the MediaFrameworkGUI.
     *
     * @param mediaFramework The framework to be associated with the current
     *                       GUI.
     */
    public MediaFrameworkPanel(MediaFramework mediaFramework){
        this.mediaFramework = mediaFramework;
        /*populateMediaChoices();
        populateVisualChoices();*/

        mediaDropDown = createMediaDropDown();

        /*visualDropDown = createVisualDropDown();*/
        visualMultipleSelectionList = getVisualTypeList();
        JScrollPane visualTypeScrollPane =
                new JScrollPane(visualMultipleSelectionList);

        mediaTitleField = createMediaTitleTextField();

        setLayout(new BorderLayout());
        topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        //topPanel.add(mediaDropDown);
        /*topPanel.add(visualDropDown);*/
        //topPanel.add(visualTypeScrollPane);
        topPanel.add(mediaTitleField);
    }

    /*private void populateMediaChoices(){
        mediaChoices.add(MOVIE_STRING);
        mediaChoices.add(TV_SERIES_STRING);
        mediaChoices.add(DOCUMENTARY_STRING);
    }
    private void populateVisualChoices(){
        visualChoices.add(SENTIMENT_STRING);
        visualChoices.add(TRAILER_STRING);
        visualChoices.add(WORD_CLOUD_STRING);
    }*/

    /**
     * This method creates a dropdown for selecting the type of media the
     * user wants visualized.
     *
     * @return A dropdown object.
     */
    private JComboBox<String> createMediaDropDown(){
        JComboBox<String> mediaDropDown = new JComboBox<>(
                mediaChoices.toArray(new String[mediaChoices.size()]));
        mediaDropDown.addActionListener(e -> mediaFramework.setMediaType(
                mediaDropDown.getSelectedItem().toString()
        ));
        return mediaDropDown;
    }

    private void updateVisualPanel() {
        this.visualMultipleSelectionList.setVisible(false);
        this.visualMultipleSelectionList = getVisualTypeList();
        this.topPanel.add(visualMultipleSelectionList);
        this.topPanel.setVisible(true);
    }

    private void updateMediaPanel() {
        this.mediaDropDown.setVisible(false);
        this.mediaDropDown = createMediaDropDown();
        this.topPanel.add(mediaDropDown);
        this.topPanel.setVisible(true);
    }

    private JComboBox<String> createVisualDropDown(){
        JComboBox<String> visualDropDown = new JComboBox<>(
                visualChoices.toArray(new String[visualChoices.size()]));
        visualDropDown.addActionListener(e -> processVisualDropDown(
                visualDropDown.getSelectedObjects()
                )
        );
        return visualDropDown;
    }

    /**
     *  This methods creates gets the list of visual dropdowns selected by
     *  the user for visualization.
     *
     * @param visualTypeObjList
     */
    private void processVisualDropDown(Object[] visualTypeObjList){
        List<String> visualTypeStringList = new ArrayList<>();
        for(Object curObj : visualTypeObjList){
            visualTypeStringList.add(String.valueOf(curObj));
        }
        mediaFramework.setVisualType(visualTypeStringList);
    }

    /**
     * This method returns a JList with all the Visualization types available
     * to the user for visualizing the media data.
     *
     * @return A JList with all the Visualization types available.
     */
    private JList<String> getVisualTypeList(){
        JList<String> visualTypeList = new JList<>(
                visualChoices.toArray(new String[visualChoices.size()]));
        visualTypeList.setVisibleRowCount(MAX_VISUAL_ROW_VISIBLE_COUNT);
        visualTypeList.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
        return visualTypeList;
    }

    /**
     * This method creates a text field to input the name of the media the
     * user wants.
     *
     * @return A JTextField instance.
     */
    private JTextField createMediaTitleTextField(){
        setCurrentVisualSelections();
        JTextField mediaTitleField = new JTextField(MAX_MEDIA_TITLE_SIZE);
        mediaTitleField.addActionListener(e -> this.mediaFramework.setMediaTitle(
                mediaTitleField.getText()
        ));
        return mediaTitleField;
    }

    /**
     *  This methods sets the list of visualizations selected in the
     *  underlying media framework.
     */
    private void setCurrentVisualSelections(){
        List<String> curVisualSelectionList =
                visualMultipleSelectionList.getSelectedValuesList();
        if(curVisualSelectionList.isEmpty()){
            // Intimate GUI with appropriate message.
            errorMessage("Please select atleast one visualization.");
        } else {
            mediaFramework.setVisualType(curVisualSelectionList);
        }
    }

    /**
     * This method creates a JPanel which will hold the dropdown for media
     * type, the JList for visual type, and a text field for media title.
     *
     * @return The JPanel instance representing the top panel.
     */
    private JPanel createTopPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        return panel;
    }

    @Override public void mediaPluginRegistered(MediaPlugin mediaPlugin) {
        mediaChoices.add(mediaPlugin.getPluginName());
        updateMediaPanel();
    }

    @Override
    public void visualPluginRegistered(VisualizationPlugin visualPlugin) {
        visualChoices.add(visualPlugin.getPluginName());
        updateVisualPanel();
    }

    @Override
    public void updateGUIWithJPanelList(List<JPanel> jPanelList) {
        jPanelList = new ArrayList<>();
        jPanelList.add(new JPanel());
        jPanelList.add(new JPanel());
        jPanelList.add(new JPanel());
        int i = 0;
        while (i < MAX_VISUALIZATIONS && i < jPanelList.size()) {
            add(jPanelList.get(i));
            i++;
        }
    }

    @Override
    public void errorMessage(String message){

    }


}