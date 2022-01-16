package pl.nullreference.bankstatement;

import pl.nullreference.bankstatement.model.provider.Provider;
import pl.nullreference.bankstatement.model.source.BankStatementSource;
import pl.nullreference.bankstatement.sourceObserver.base.BaseSourceObserver;
import pl.nullreference.bankstatement.sourceObserver.dto.SourceObserverResultDto;
import rx.Observable;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FolderSourceObserver extends BaseSourceObserver {

    private final String SEPARATOR = System.getProperty("file.separator");
    private final String DIR = "bankstatementsource" + SEPARATOR;
    private final LinkedList<SourceObserverResultDto> filesToUpdate = new LinkedList<>();
    Map<WatchKey, Path> keyMap = new HashMap<>();
    Map<String, Provider> providerMap = new HashMap<>();
    WatchService watchService;


    public FolderSourceObserver(List<BankStatementSource> bankStatementSources) {
        super(bankStatementSources);
        try {
            watchService = FileSystems.getDefault().newWatchService();
            initObservers();
            startObserveFolders();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void initObservers() {
        for (BankStatementSource source: this.bankStatementSources) {
            registerObserver(source);
        }
    }

    public Observable<SourceObserverResultDto> getSourceObservable() {
        return subject.asObservable()
                .filter(dto -> dto.getFile() != null && dto.getFile().exists());
    }

    @Override
    public void refresh() {
        filesToUpdate.forEach(file -> {
            subject.onNext(file);
        });
        filesToUpdate.clear();
    }

    @Override
    public void addBankStatementSource(BankStatementSource bankStatementSource) {
        bankStatementSources.add(bankStatementSource);
        registerObserver(bankStatementSource);
    }

    private void startObserveFolders() {
        new Thread (() -> {
            try {
                WatchKey key;
                while ((key = watchService.take()) != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE && event.context().toString().charAt(0) != '.') {
//                            System.out.println(
//                                    "Event kind:" + event.kind()
//                                            + ". File affected: " + event.context() + ".");
                            filesToUpdate.add(createSourceObserverResultDto(key, event));
                        }
                    }
                    key.reset();
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }

    private SourceObserverResultDto createSourceObserverResultDto(WatchKey key, WatchEvent event) {
        Path dir = keyMap.get(key);
        Path relativePath = (Path) event.context();
        String fileName = dir.resolve(relativePath).toString();
        File file = new File(fileName);
        String sourcePath = getSourcePath(fileName, relativePath.toString());
        Provider provider = providerMap.get(sourcePath);
        return SourceObserverResultDto.builder()
                .provider(provider)
                .file(file)
                .build();
    }

    private void registerObserver(BankStatementSource source) {
        try {
            Path path = Paths.get(DIR + source.getSourcePath()).toAbsolutePath();

            WatchKey key = path.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            keyMap.put(key, path);
            providerMap.put(source.getSourcePath(), source.getProvider());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    private String getSourcePath(String fileName, String relativePath) {
        return fileName.split(DIR)[1].split( SEPARATOR + relativePath)[0];
    }
}
