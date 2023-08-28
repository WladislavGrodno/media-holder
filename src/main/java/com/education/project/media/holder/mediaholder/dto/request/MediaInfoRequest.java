package com.education.project.media.holder.mediaholder.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MediaInfoRequest(

        @Schema(description = "File nickname",
                example = "Media1")
        @NotNull(message = "Blank value name is denied")
        @Size(max = 128)
        String name,

        @Schema(description = "File description",
                example = "Very interesting media")
        @Size(max = 128)
        String description

){}
