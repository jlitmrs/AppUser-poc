package com.tmrs.poc.app.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChangePasswordException extends RuntimeException {
    private static final String MESSAGE = "User with id[%s] password was not changed new password[%s].";
    private String newPassword;
    private Long userId;

    public ChangePasswordException(Throwable error, Long userId, String newPassword) {
        super(error);
        this.userId = userId;
        this.newPassword = newPassword;
    }
    public ChangePasswordException(String message, Long userId, String newPassword) {
        super(message);
        this.userId = userId;
        this.newPassword = newPassword;
    }

    @Override
    public String getMessage() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(super.getMessage()).append("\n");
        buffer.append(MESSAGE.formatted(userId, newPassword)).append("\n");
        return buffer.toString();
    }
}
