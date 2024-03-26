package org.ulisesgc.accounts.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(
        name = "Response API",
        description = "Schema to hold successful response information"
)
@Data @AllArgsConstructor
public class ResponseDTO {
    @Schema(
            description = "Status ode in the response"
    )
    private String statusCode;

    @Schema(

            description = "Status message in the response"
    )
    private String statusMsg;
}
