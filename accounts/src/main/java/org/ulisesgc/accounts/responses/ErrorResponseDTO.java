package org.ulisesgc.accounts.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(
        name = "Error Response API",
        description = "Schema to hold error response information"
)
@Data @AllArgsConstructor
public class ErrorResponseDTO {

    @Schema(
            description = "path of endpoint"
    )
    private String apiPath;

    @Schema(
            description = "error code of cause",
            example = "500"
    )
    private String errorCode;

    @Schema(
            description = "description of error",
            example = "Not Found"
    )
    private String errorMsg;

    @Schema(
            description = "time of error",
            example = "12:00:00"
    )
    private LocalDateTime errorTime;
}
