package ru.totsystems.stocks_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.totsystems.stocks_api.convert.XMLConverter;
import ru.totsystems.stocks_api.dto.HistoryDto;
import ru.totsystems.stocks_api.mapper.HistoryMapper;
import ru.totsystems.stocks_api.model.History;
import ru.totsystems.stocks_api.model.Security;
import ru.totsystems.stocks_api.repository.HistoryRepository;
import ru.totsystems.stocks_api.repository.SecurityRepository;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final HistoryMapper mapper;
    private final SecurityRepository securityRepository;


    @Transactional
    public History addHistory(MultipartFile file, Long secId) {
        History history = XMLConverter.convertXMLtoObject(file, History.class);
        Security security = securityRepository.findBySecId(secId).orElseThrow(RuntimeException::new);
        security.setHistory(history);
        securityRepository.save(security);
        return history;
    }

    @Transactional(readOnly = true)
    public History getHistoryById(Long secId) {
        return historyRepository.findById(secId).orElseThrow(RuntimeException::new);
    }

    public History updateHistory(HistoryDto historyDto) {
        History history = mapper.historyDtoToHistory(historyDto);
        return historyRepository.save(history);
    }

    public void deleteHistory(Long secId) {
        historyRepository.deleteById(secId);
    }

}
