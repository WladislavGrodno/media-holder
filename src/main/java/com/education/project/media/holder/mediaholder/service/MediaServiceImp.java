package com.education.project.media.holder.mediaholder.service;

import com.education.project.media.holder.mediaholder.dto.request.MediaInfoRequest;
import com.education.project.media.holder.mediaholder.dto.request.MediaRequest;
import com.education.project.media.holder.mediaholder.dto.response.MediaInfoResponse;
import com.education.project.media.holder.mediaholder.dto.response.MediaResponse;
import com.education.project.media.holder.mediaholder.mapper.MediaMapper;
import com.education.project.media.holder.mediaholder.model.Media;
import com.education.project.media.holder.mediaholder.repository.MediaRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class MediaServiceImp implements MediaService{

    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private MediaMapper mediaMapper;

    @Autowired
    @Qualifier("storageServiceImp")
    private StorageService storageService;

    //private StorageServiceImp storageService = new StorageServiceImp();

    @Override
    public ResponseEntity<MediaInfoResponse> createMedia(
            @NotNull MediaRequest mediaRequest)
            throws IOException {

        MultipartFile file = mediaRequest.fileBody();

        log.info("{\"add file\": {" +
                        "\"media name\": {}," +
                        "\"media description\": {}," +
                        "\"media type\": {}," +
                        "\"file type\": {}," +
                        "\"file name\": {}," +
                        "\"file original name\": {}," +
                        "\"file size\": {}" +
                        "}}",
                mediaRequest.name(),
                mediaRequest.description(),
                mediaRequest.type(),
                file.getContentType(), file.getName(),
                file.getOriginalFilename(), file.getSize()
        );

        Media mediaResult = mediaRepository.save(
                mediaMapper.toMedia(mediaRequest));

        mediaResult.setFilePath(
                storageService.save(mediaResult.getId(), file));

        return new ResponseEntity<>(
                mediaMapper.toDtoInfo(mediaRepository.save(mediaResult)),
                HttpStatus.OK);
    }

    /*
    @Override
    public ResponseEntity<MediaResponse> getMediaById(
            @NotNull Long id)
            throws Exception {
        log.info("{\"return file\": {\"id\": {}}}", id);

        //        boolean jpg = true;
//    MediaType contentType = jpg ? MediaType..IMAGE_JPEG : MediaType.IMAGE_PNG;

        Optional<Media> mediaOptional = mediaRepository.findById(id);
        if (mediaOptional.isEmpty()) throw new Exception("NOT FOUND");

        Media media = mediaOptional.get();

        Resource resourceMedia = storageService.load(
                media.getFilePath(), media.getFileName());

        MediaResponse mediaResponse = mediaMapper.toDto(
                media, resourceMedia);

        return new ResponseEntity<>(mediaResponse, HttpStatus.OK);
    }
     */

    @Override
    public ResponseEntity<Resource> getMediaById(
            @NotNull Long id)
            throws Exception {
        log.info("{\"return file\": {\"id\": {}}}", id);

        Optional<Media> mediaOptional = mediaRepository.findById(id);
        if (mediaOptional.isEmpty()) throw new Exception("NOT FOUND");

        Media media = mediaOptional.get();
        return new ResponseEntity<>(
                storageService.load(media.getFilePath(), media.getFileName()),
                HttpStatus.OK);

    }

    @Override
    public ResponseEntity<MediaInfoResponse> getMediaInfoById(
            @NotNull Long id)
            throws Exception {
        log.info("{\"return file info\": {\"id\": {}}}", id);

        Optional<Media> mediaOptional = mediaRepository.findById(id);
        if (mediaOptional.isEmpty()) throw new Exception(
                "MEDIA NOT FOUND ON THIS SERVER");

        return new ResponseEntity<>(
                mediaMapper.toDtoInfo(mediaOptional.get()),
                HttpStatus.OK);

    }

    @Override
    public ResponseEntity<MediaInfoResponse> updateMediaById(
            @NotNull Long id,
            @NotNull MediaRequest media)
            throws Exception {
        log.info("{\"update file\": {\"id\": {}}}", id);
        return null;
    }

    @Override
    public ResponseEntity<MediaInfoResponse> updateMediaInfoById(
            @NotNull Long id,
            @NotNull MediaInfoRequest mediaInfo)
            throws Exception {
        log.info("{\"update file info\": {\"id\": {}}}", id);

        Optional<Media> mediaOptional = mediaRepository.findById(id);
        if (mediaOptional.isEmpty()) throw new Exception("NOT FOUND");

        Media media = mediaOptional.get();
        media.setName(mediaInfo.name());
        media.setDescription(mediaInfo.description());

        return new ResponseEntity<>(
                mediaMapper.toDtoInfo(mediaRepository.save(media)),
                HttpStatus.OK);

        /*
        @Override
    public ResponseEntity<CarResponse> carUpdate(
            @NotNull Long idc,
            @NotNull CarRequest car)
            throws Exception {
        log.info("{\"updateCar\": {\"id\": {}, \"car\": \"{}\"}}", idc, car);
        Optional<Car> carControl = carRepository.findById(idc);
        if (carControl.isEmpty()) throw new Exception("NOT FOUND");
        return new ResponseEntity<>(
                mapper.toDto(carRepository.save(mapper.toCar(idc, car))),
                HttpStatus.OK);
    }

         */

    }

    @Override
    public void eraseMedia(@NotNull Long id) throws Exception {
        log.info("{\"delete file\": {\"id\": {}}}", id);

        Optional<Media> mediaOptional = mediaRepository.findById(id);
        if (mediaOptional.isEmpty()) return;

        Media media = mediaOptional.get();
        if (media.getType() != -1) {
            media.setType(-1);
            mediaRepository.save(media);
        }

        if (storageService.delete(media.getFilePath(), media.getFileName()))
            storageService.cleanPath(media.getId());

    }
}
