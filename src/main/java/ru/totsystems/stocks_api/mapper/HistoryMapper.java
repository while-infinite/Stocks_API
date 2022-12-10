package ru.totsystems.stocks_api.mapper;

import org.mapstruct.Mapper;
import ru.totsystems.stocks_api.dto.HistoryDto;
import ru.totsystems.stocks_api.model.History;

@Mapper(componentModel = "spring")
public interface HistoryMapper {
    History historyDtoToHistory(HistoryDto historyDto);
}
