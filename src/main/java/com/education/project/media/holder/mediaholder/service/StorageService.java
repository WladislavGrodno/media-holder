package com.education.project.media.holder.mediaholder.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

//    void init() throws IOException;

    String save(@NotNull Long id,
                @NotNull MultipartFile file) throws IOException;

    Resource load(
            @NotNull String filePath,
            @NotNull String fileName) throws Exception;

    boolean delete(
            @NotNull String filePath,
            @NotNull String fileName) throws IOException;

    void cleanPath(Long id) throws IOException;
        //Path load(@NotNull String filename);

    //Stream<Path> loadAll();
    //void deleteAll();
}
