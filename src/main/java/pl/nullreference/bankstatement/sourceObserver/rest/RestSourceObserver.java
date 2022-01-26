package pl.nullreference.bankstatement.sourceObserver.rest;

import javafx.util.Pair;
import okhttp3.OkHttpClient;
import pl.nullreference.bankstatement.model.provider.Provider;
import pl.nullreference.bankstatement.model.source.BankStatementSource;
import pl.nullreference.bankstatement.sourceObserver.base.BaseSourceObserver;
import pl.nullreference.bankstatement.sourceObserver.dto.SourceObserverResultDto;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RestSourceObserver extends BaseSourceObserver {

    private final List<Pair<Provider, RestObserverRetrofitService>> services = new ArrayList<>();
    private final OkHttpClient httpClient = new OkHttpClient.Builder().build();

    public RestSourceObserver(List<BankStatementSource> bankStatementSources) {
        super(bankStatementSources);
        initEndpoints();
    }

    @Override
    public void resetList(List<BankStatementSource> bankStatementSources) {
        this.bankStatementSources.clear();
        this.bankStatementSources.addAll(bankStatementSources);
        initEndpoints();
    }

    @Override
    public void addBankStatementSource(BankStatementSource bankStatementSource) {
        bankStatementSources.add(bankStatementSource);
        Retrofit retrofit = buildRetrofit(bankStatementSource.getSourcePath());
        services.add(new Pair<>(bankStatementSource.getProvider(), buildEndpoint(retrofit)));
    }

    private File getEndpointResult(RestObserverRetrofitService endpoint) {
        try {
            Response<RestObserverModel> response = endpoint.getModel("").execute();
            return new File(Objects.requireNonNull(response.body()).getPath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void initEndpoints() {
        try {
            this.bankStatementSources.forEach(source ->
                    services.add(new Pair<>(
                            source.getProvider(),
                            buildEndpoint(buildRetrofit(source.getSourcePath()))
                    ))
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void refresh() {
        services.forEach(endpoint -> {
            var x = getEndpointResult(endpoint.getValue());
            if (x != null) {
                notifySourceObserverSubject(SourceObserverResultDto.builder()
                        .provider(endpoint.getKey())
                        .file(x)
                        .build());
            }

        });

    }

    private Retrofit buildRetrofit(String endpointPath) {

        endpointPath += endpointPath.endsWith("/")
                ? ""
                : "/";

        return new Retrofit.Builder()
                .baseUrl(endpointPath)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }

    private RestObserverRetrofitService buildEndpoint(Retrofit retrofit) {
        return retrofit.create(RestObserverRetrofitService.class);
    }
}
