package com.education.project.media.holder.mediaholder.service;

import com.education.project.media.holder.mediaholder.dto.request.MediaRequest;
import com.education.project.media.holder.mediaholder.dto.request.MediaInfoRequest;
import com.education.project.media.holder.mediaholder.dto.response.MediaInfoResponse;

import com.education.project.media.holder.mediaholder.model.DataPage;
import com.education.project.media.holder.mediaholder.model.Media;
import com.education.project.media.holder.mediaholder.model.MediaSearchCriteria;
import jakarta.validation.constraints.NotNull;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface MediaService {

    ResponseEntity<MediaInfoResponse> createMedia(
            @NotNull MediaRequest media
    ) throws Exception;


    ResponseEntity<Resource> getMediaById(
            @NotNull UUID id
    ) throws Exception;


    ResponseEntity<MediaInfoResponse> getMediaInfoById(
            @NotNull UUID id
    ) throws Exception;


    ResponseEntity<MediaInfoResponse> updateMediaById(
            @NotNull UUID id,
            @NotNull MultipartFile mediaFile)
            throws Exception;

    ResponseEntity<MediaInfoResponse> updateMediaInfoById(
            @NotNull UUID id,
            @NotNull MediaInfoRequest mediaInfo)
            throws Exception;

    void eraseMedia(@NotNull UUID id) throws Exception;

    public ResponseEntity<Page<MediaInfoResponse>> mediaListCustomRead(
            @NotNull DataPage carPage,
            @NotNull MediaSearchCriteria carSearchCriteria
    );


    }
