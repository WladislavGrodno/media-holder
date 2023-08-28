package com.education.project.media.holder.mediaholder.tools;

import jakarta.validation.constraints.NotNull;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathChain {
    private final Path storageRootPath;

    public PathChain(Path storageRootPath) {
        this.storageRootPath = storageRootPath;
    }

    public Path path(Long id) throws IOException {
        return createChain(chain(id));
    }

    public Path path(@NotNull String filePath,
                     @NotNull String fileName) throws IOException {
        return Paths.get(filePath).resolve(fileName);
    }

    public void cleanPath(Long id) throws IOException {
        cleanChain(chain(id));
    }

    private byte[] chain(Long id){
        byte[] chain = new byte[8];
        for (int pos = 0; pos < 8; pos++) {
            chain[pos] = (byte) (id & 255);
            id >>>= 8;
        }
        return chain;
    }

    private Path createChain(byte[] chain) throws IOException {
        Path path = storageRootPath;
        if (!Files.exists(path)) Files.createDirectories(path);

        for (int pos = 7; pos > -1; pos--) {
            path = path.resolve(String.format("%02x", chain[pos]));
            if (!Files.exists(path)) Files.createDirectories(path);
        }
        return path;
    }

    private void cleanChain(byte[] chain) throws IOException {
        Path rootPath = storageRootPath;
        Path[] path = new Path[8];

        path[0] = storageRootPath.resolve(String.format("%02x", chain[0]));
        int pos = 1;
        for (; pos < 8; pos++)
            path[pos] = path[pos-1].resolve(String.format("%02x", chain[pos]));

        for (pos = 7; pos > -1; pos--) {
            if (Files.exists(path[pos])) {
                try (DirectoryStream<Path> directory =
                             Files.newDirectoryStream(path[pos])) {
                    if (!directory.iterator().hasNext())
                        Files.delete(path[pos]);
                    else pos = -1;
                }
            }
        }

    }


}

