package nick1st.fancyvideo.forge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.EventSubclassTransformer;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import nick1st.fancyvideo.Constants;
import nick1st.fancyvideo.FancyVideoAPI;
import net.minecraftforge.fml.common.Mod;
import nick1st.fancyvideo.FancyVideoConfig;
import nick1st.fancyvideo.config.SimpleConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;

import java.util.Arrays;

@Mod(FancyVideoAPI.MOD_ID)
public class FancyVideoAPIForge {

    // Config Holder
    private SimpleConfig config;

    public FancyVideoAPIForge() {
        // Client only
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, ()-> new IExtensionPoint.DisplayTest(() -> "ANY", (a, b) -> true));
        if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
            Constants.LOG.warn("## WARNING ## 'FancyVideo API' is a client mod and has no effect when loaded on a server!");
            return;
        }

        // Init Config
        config = new FancyVideoConfig();

        // Ignore the silly NullPointers caused by ModLauncher // TODO Make this actually STOP the error
        if (LogManager.getLogger(EventSubclassTransformer.class) instanceof org.apache.logging.log4j.core.Logger && !config.getAsBool("debugLog")) {
            org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getLogger(EventSubclassTransformer.class);
            logger.warn("## WARNING ## 'FancyVideo-API' is modifying this log! Disable this behavior in its config BEFORE reporting bugs!");
            logger.addFilter(new AbstractFilter() {
                @Override
                public Result filter(LogEvent event) {
                    if (event.getMessage() != null && event.getThrown() != null && event.getMarker() != null) {
                        if (event.getMarker().getName().equals("EVENTBUS") && event.getMessage().getFormattedMessage().equals("An error occurred building event handler")) {
                            if (Arrays.stream(event.getThrown().getStackTrace()).anyMatch(sTE -> sTE.getClassName().startsWith("uk.co.caprica.vlcj."))) {
                                return Result.DENY;
                            }
                        }
                    }
                    return Result.NEUTRAL;
                }
            });
        }

        FancyVideoAPI.init(config);
    }
}
