package com.education.project.media.holder.mediaholder.mapper;

import com.education.project.media.holder.mediaholder.dto.request.MediaRequest;
import com.education.project.media.holder.mediaholder.dto.response.MediaInfoResponse;
import com.education.project.media.holder.mediaholder.model.Media;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MediaMapper {

    /*
    default MediaResponse toDto(Media media, Resource fileBody){
        return new MediaResponse(
                media.getId(),
                media.getUploadTime(),
                media.getName(),
                media.getDescription(),
                media.getType(),
                media.getFileName(),
                media.getFileSize(),
                fileBody
        );
    };

     */

    @Mapping(source = "name", target = "fileName")
    @Mapping(source = "size", target = "fileSize")
    Media toMedia(Media media, String name, Long size);

    MediaInfoResponse toDtoInfo(Media media);

//    Page<MediaInfoResponse> toDtoInfo(Page<Media> media);
    List<MediaInfoResponse> toDtoInfo(List<Media> media);

    /*{
        return new MediaInfoResponse(
                media.getId(),
                media.getUploadTime(),
                media.getName(),
                media.getDescription(),
                media.getType(),
                media.getFileName(),
                media.getFileSize()
        );
    }*/

    /*
    default MediaInfoResponse toDtoInfo(Media media){
        return new MediaInfoResponse(
                media.getId(),
                media.getUploadTime(),
                media.getName(),
                media.getDescription(),
                media.getType(),
                media.getFileName(),
                media.getFileSize()
                );
    }

     */

/*
    @Mapping(target = "fileName",
            expression = "java(request.fileBody().getOriginalFilename())")
    @Mapping(target = "fileSize",
            expression = "java(request.fileBody().getSize())")

 */
    //@Mapping(source = "request.file.size", target = "file_size")
    //@Mapping(source = "request.file.originalFilename", target = "file_name")
    // @Mapping(source = "request.file.size", target = "file_size")
    //@Mapping(source = "request.type", target = "specialization")
    //@Mapping(source = "request.name", target = "specialization")
    Media toMedia(MediaRequest request, String fileName, Long fileSize);

    /*
    default Media toMedia(MediaRequest request){
        MultipartFile file = request.fileBody();

        return new Media(
                null, null,
                request.name(), request.description(), request.type(),
                file.getOriginalFilename(), file.getSize(), ""
        );
    }
     */

}
