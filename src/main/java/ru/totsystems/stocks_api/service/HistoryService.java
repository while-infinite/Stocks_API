package ru.totsystems.stocks_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.totsystems.stocks_api.convert.XMLConverter;
import ru.totsystems.stocks_api.model.History;
import ru.totsystems.stocks_api.repository.HistoryRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;


    public History addHistory(MultipartFile file, Long stockId){
        History history = XMLConverter.convertXMLtoObject(file);
        return null;
    }


    @Transactional(readOnly = true)
    public History getHistoryByTradedate(LocalDate date){
        return historyRepository.findHistoryByTradedate(date).orElseThrow(RuntimeException::new);
    }

    public void deleteHistory(Long secid){
        historyRepository.deleteById(secid);
    }

}
