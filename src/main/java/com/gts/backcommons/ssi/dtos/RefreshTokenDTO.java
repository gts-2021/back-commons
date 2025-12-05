package com.gts.backcommons.ssi.dtos;

import com.gts.backcommons.jwtauth.Constants;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenDTO {

    @NotBlank(message = Constants.REFRESH_TOKEN_REQUIRED)
    private String refreshToken;
}
