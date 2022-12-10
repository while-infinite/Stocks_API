package ru.totsystems.stocks_api.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseWebResponse {
    private String errorMessage;
}
