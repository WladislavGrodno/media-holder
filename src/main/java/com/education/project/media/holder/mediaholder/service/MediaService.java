package com.education.project.media.holder.mediaholder.service;

import com.education.project.media.holder.mediaholder.dto.request.MediaRequest;
import com.education.project.media.holder.mediaholder.dto.request.MediaInfoRequest;
import com.education.project.media.holder.mediaholder.dto.response.MediaInfoResponse;
import com.education.project.media.holder.mediaholder.dto.response.MediaResponse;

import jakarta.validation.constraints.NotNull;
import org.apache.coyote.Request;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface MediaService {

    ResponseEntity<MediaInfoResponse> createMedia(
            @NotNull MediaRequest media
    ) throws IOException;

 /*   ResponseEntity<MediaResponse> getMediaById(
            @NotNull Long id
    ) throws Exception;
  */

    ResponseEntity<Resource> getMediaById(
            @NotNull Long id
    ) throws Exception;


    ResponseEntity<MediaInfoResponse> getMediaInfoById(
            @NotNull Long id
    ) throws Exception;


    ResponseEntity<MediaInfoResponse> updateMediaById(
            @NotNull Long id,
            @NotNull MediaRequest media)
            throws Exception;

    ResponseEntity<MediaInfoResponse> updateMediaInfoById(
            @NotNull Long id,
            @NotNull MediaInfoRequest mediaInfo)
            throws Exception;

    void eraseMedia(@NotNull Long id) throws Exception;

}
