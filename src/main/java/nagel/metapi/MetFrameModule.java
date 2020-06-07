package nagel.metapi;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class MetFrameModule extends AbstractModule {
    @Provides
    static MetService providesMetService() {
        return new MetServiceFactory().getInstance();
    }
}
