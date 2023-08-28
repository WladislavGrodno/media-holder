package com.education.project.media.holder.mediaholder.mapper;

import com.education.project.media.holder.mediaholder.dto.request.MediaRequest;
import com.education.project.media.holder.mediaholder.dto.response.MediaInfoResponse;
import com.education.project.media.holder.mediaholder.dto.response.MediaResponse;
import com.education.project.media.holder.mediaholder.model.Media;
import org.mapstruct.Mapper;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring")
public interface MediaMapper {

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

    default Media toMedia(MediaRequest request){
        MultipartFile file = request.fileBody();

        return new Media(
                null, null,
                request.name(), request.description(), request.type(),
                file.getOriginalFilename(), file.getSize(), ""
        );
    }

}
