package ru.totsystems.stocks_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.totsystems.stocks_api.convert.XMLConverter;
import ru.totsystems.stocks_api.dto.HistoryDto;
import ru.totsystems.stocks_api.exception.NotFoundException;
import ru.totsystems.stocks_api.mapper.HistoryMapper;
import ru.totsystems.stocks_api.model.History;
import ru.totsystems.stocks_api.model.Security;
import ru.totsystems.stocks_api.repository.HistoryRepository;
import ru.totsystems.stocks_api.repository.SecurityRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final HistoryMapper mapper;
    private final SecurityRepository securityRepository;


    @Transactional
    public History updateHistoryFromFile(MultipartFile file, String secId) {
        History history = XMLConverter.convertXMLtoObject(file, History.class);
        History retrievedHistory = historyRepository.findById(secId)
                .orElseThrow(() -> new NotFoundException("History not found"));
        history.setSecId(retrievedHistory.getSecId());
        History savedHistory = historyRepository.save(history);
        log.info("History wsa saved {}", savedHistory);
        return savedHistory;
    }

    @Transactional(readOnly = true)
    public History getHistoryById(String secId) {
        return historyRepository.findById(secId).orElseThrow(() -> new NotFoundException("History not found"));
    }

    public History updateHistory(HistoryDto historyDto) {
        History history = mapper.historyDtoToHistory(historyDto);
        return historyRepository.save(history);
    }

    public History save(History history){
        return historyRepository.save(history);
    }

    @Transactional
    public void deleteHistory(String secId) {
        Security security = securityRepository.findBySecId(secId).
                orElseThrow(() -> new NotFoundException("Security not found"));
        security.setHistory(null);
        securityRepository.save(security);
        historyRepository.deleteById(secId);
    }

    public Boolean isPersist(String secId){
        History history = historyRepository.findById(secId).orElse(null);
        return history != null;
    }

}
