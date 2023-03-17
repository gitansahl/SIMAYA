package com.a05.simaya.anggota.payload;

import lombok.Data;

@Data
public class UbahPasswordDTO {
    private String id;
    private String oldPassword;
    private String newPassword;
}
