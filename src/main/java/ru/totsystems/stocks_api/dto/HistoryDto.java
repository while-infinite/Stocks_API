package ru.totsystems.stocks_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDto {
    @NonNull
    private String secId;
    private LocalDate tradeDate;
    private int numTrades;
    private String open;
    private MultipartFile file;
}
