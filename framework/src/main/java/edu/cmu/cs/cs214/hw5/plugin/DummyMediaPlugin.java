package edu.cmu.cs.cs214.hw5.plugin;

import edu.cmu.cs.cs214.hw5.core.MediaFramework;
import edu.cmu.cs.cs214.hw5.core.MediaInfo;
import edu.cmu.cs.cs214.hw5.core.MediaPlugin;

import java.util.ArrayList;

public class DummyMediaPlugin implements MediaPlugin {
    /**
     * This method is meant to be executed when a new MediaPlugin is
     * registered with a MediaFramework. Ideally, it should set the
     * MediaFramework instance in the MediaPlugin. In addition, it can do
     * other initializations.
     *
     * @param mediaFramework The mediaFramework with which the current
     *                       MediaPlugin is registered.
     */
    @Override
    public void onRegister(MediaFramework mediaFramework) {
        return;
    }

    /**
     * This method is meant to be executed when a MediaPlugin is closed/
     * destroyed.
     */
    @Override
    public void onClose() {
        return;
    }

    /**
     * Fetches the media based on the media title. The Plugin controls the
     * source from which the data about the media is to be fetched.
     *
     * @param mediaTitle The title of the queried media.
     * @return
     */
    @Override
    public MediaInfo fetchData(String mediaTitle) {
        return new MediaInfo("Gattaca", 1988, "Random", new ArrayList<String>(), "www.google.com", "Horror");
    }

    /**
     * Gets the name of the media plugin.
     */
    @Override
    public String getPluginName() {
        return "Dummy";
    }
}
