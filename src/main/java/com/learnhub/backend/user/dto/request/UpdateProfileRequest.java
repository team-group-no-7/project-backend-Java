package com.learnhub.backend.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProfileRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String headline;

    private String location;

    private String avatarUrl;
}