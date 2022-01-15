package pl.nullreference.bankstatement.sourceObserver.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RestObserverRetrofitService {
    @GET
    public Call<RestObserverModel> getModel(@Url String url);
}
