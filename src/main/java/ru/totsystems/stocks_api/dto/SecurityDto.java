package ru.totsystems.stocks_api.dto;

import lombok.Data;

@Data
public class SecurityDto {

    private long id;
    private String secId;
    private int regNumber;
    private String name;
    private String emitentTitle;
}
