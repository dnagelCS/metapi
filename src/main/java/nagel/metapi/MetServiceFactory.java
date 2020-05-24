package nagel.metapi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MetServiceFactory {

    public MetService getInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://collectionapi.metmuseum.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MetService service = retrofit.create(MetService.class);
        return service;
    }
}
