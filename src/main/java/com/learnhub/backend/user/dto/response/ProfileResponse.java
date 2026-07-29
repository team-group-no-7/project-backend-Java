package com.learnhub.backend.user.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {

    private Long id;

    private String name;

    private String email;

    private String role;

    private String avatarUrl;

    private String headline;

    private String location;
}