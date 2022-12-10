package ru.totsystems.stocks_api.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class HistoryDto {
    private  Long stockId;
    private MultipartFile file;
}
