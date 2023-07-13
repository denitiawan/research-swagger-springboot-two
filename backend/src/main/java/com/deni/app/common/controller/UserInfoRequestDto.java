package com.deni.app.common.controller;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoRequestDto {
    String username;
    Date requestDate;
}
