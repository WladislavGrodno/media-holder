package com.education.project.media.holder.mediaholder.service;

import com.education.project.media.holder.mediaholder.tools.PathChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service

public class StorageServiceImp implements StorageService{
    private final Path storageRootPath;
    private final PathChain pathChain;

    //@Autowired
    //StorageConfiguration storageConfiguration;
    //StorageProperties storageProperties;// = new StorageProperties();

    public StorageServiceImp() {
        this.storageRootPath = Paths.get(
                //storageProperties.getLocation()
                "./media-storage"
                ).normalize();
        pathChain = new PathChain(storageRootPath);
    }

    /*
    @Override
    @PostConstruct
    public void init() throws IOException {
        if (!Files.exists(storageRootPath)) {
            try {
                Files.createDirectories(storageRootPath);
                log.info("Storage {} was created", storageRootPath);
            }
            catch (IOException noStorage){
                log.error("Can't create storage {}", storageRootPath);
                throw noStorage;
            }
        }
        else log.info("Storage already exist");
    }

     */

    @Override
    public String save(Long id, MultipartFile file) throws IOException {
        Path path = pathChain.path(id);
        String name = file.getOriginalFilename();
        assert name != null;
        Files.copy(file.getInputStream(),
                path.resolve(name),
                StandardCopyOption.REPLACE_EXISTING);
        return path.toString();
    }

    @Override
    public Resource load(String filePath,
                         String fileName) throws Exception {

        try {
            Path file = pathChain.path(filePath, fileName);
            log.info("{\"return file\": " +
                    "{\"file\": \"{}\"}, " +
                    "{\"filePath\": \"{}\"}, " +
                    "{\"fileName\": \"{}\"}" +
                    "}", file, filePath, fileName);

            //Path file = this.storageRootPath.resolve(fileName).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new Exception("Could not find file");
            }
        }
        catch (MalformedURLException e){
            throw new Exception("Could not download file");
        }
    }

    @Override
    public boolean delete(String filePath,
                          String fileName) throws IOException {
            File file = pathChain.path(filePath, fileName).toFile();
            if (file.exists()) return file.delete();
            return true;
    }

    @Override
    public void cleanPath(Long id) throws IOException {
        pathChain.cleanPath(id);
    }
}
