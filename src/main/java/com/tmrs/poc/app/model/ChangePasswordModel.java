package com.tmrs.poc.app.model;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordModel {
    @NotNull(message="User Id can not be null.")
    private Long userId;

    @NotNull(message="Old password can not be null.")
    private String oldPassword;

    @NotNull(message="New password can not be null.")
    private String newPassword;
}
