package ru.totsystems.stocks_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecurityWithHistoryResponse {
    private Long id;
    private String secId;
    private int regNumber;
    private String name;
    private String emitentTitle;
    private LocalDate tradeDate;
    private int numTrades;
    private String open;

}
