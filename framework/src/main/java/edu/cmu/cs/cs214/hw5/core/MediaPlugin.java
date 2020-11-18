package edu.cmu.cs.cs214.hw5.core;

public interface MediaPlugin {
    /**
     * This method is meant to be executed when a new MediaPlugin is
     * registered with a MediaFramework. Ideally, it should set the
     * MediaFramework instance in the MediaPlugin. In addition, it can do
     * other initializations.
     *
     * @param mediaFramework The mediaFramework with which the current
     *                       MediaPlugin is registered.
     */
    void onRegister(MediaFramework mediaFramework);

    /**
     * This method is meant to be executed when a MediaPlugin is closed/
     * destroyed.
     */
    void onClose();

    /**
     * Fetches the media based on the media title. The Plugin controls the
     * source from which the data about the media is to be fetched.
     *
     * @param mediaTitle The title of the queried media.
     * @return
     */
    MediaInfo fetchData(String mediaTitle);

    /**
     * Gets the name of the media plugin.
     */
    String getPluginName();
}
