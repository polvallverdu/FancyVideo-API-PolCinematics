package nick1st.fancyvideo.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import nick1st.fancyvideo.Constants;
import nick1st.fancyvideo.FancyVideoAPI;
import net.fabricmc.api.ModInitializer;
import nick1st.fancyvideo.FancyVideoConfig;
import nick1st.fancyvideo.config.SimpleConfig;

public class FancyVideoAPIFabric implements ModInitializer {

    private static FancyVideoAPIFabric instance;

    public FancyVideoAPIFabric() {
        if (instance == null) {
            instance = this;
        } else {
            Constants.LOG.error("Called FancyVideo-API constructor a second time! This will cause serious problems!");
        }
    }

    // Config Holder
    public SimpleConfig config;

    @Override
    public void onInitialize() {
        // Init Config
        config = new FancyVideoConfig();
        FancyVideoAPI.init(config);
    }

    public static FancyVideoAPIFabric getInstance() {
        return instance;
    }

    public void firstRenderTick() {
        // Ensure this only runs on the client (Not sure if this is required)
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT && !Constants.renderTick) {
            FancyVideoAPI.apiSetup();
            Constants.renderTick = true;
        }
    }
}
