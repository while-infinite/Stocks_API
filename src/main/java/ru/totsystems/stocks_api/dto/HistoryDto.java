package ru.totsystems.stocks_api.dto;

import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class HistoryDto {
    @NonNull
    private Long secId;
    private LocalDate tradedate;
    private int numtrades;
    private String open;
    private MultipartFile file;
}
