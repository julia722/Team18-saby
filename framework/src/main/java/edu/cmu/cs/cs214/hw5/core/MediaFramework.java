package edu.cmu.cs.cs214.hw5.core;

import java.util.List;

/**
 *  The interface for the media framework. Ths interface can be implemented
 *  to create Frameworks which support data plugins that fetch media
 *  information (such as movies, music, TC Show, video games, etc), processes
 *  the data in some way (such as analyse the sentiment of the reviews), and
 *  finally call visualization plugins to visualize the processed data.
 */
public interface MediaFramework {
    void setMediaType(String mediaType);
    void setVisualType(List<String> visualTypeList);
    void setMediaTitle(String mediaTitle);
}
