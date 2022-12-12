package ru.totsystems.stocks_api.service;

import com.google.common.net.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import ru.totsystems.stocks_api.convert.XMLConverter;
import ru.totsystems.stocks_api.dto.FilesDto;
import ru.totsystems.stocks_api.dto.SecurityDto;
import ru.totsystems.stocks_api.dto.SecurityWithHistoryResponse;
import ru.totsystems.stocks_api.exception.NotFoundException;
import ru.totsystems.stocks_api.mapper.SecurityMapper;
import ru.totsystems.stocks_api.model.History;
import ru.totsystems.stocks_api.model.Security;
import ru.totsystems.stocks_api.repository.SecurityRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService {
    private final SecurityRepository securityRepository;
    private final SecurityMapper mapper;
    private final HistoryService historyService;
    private final WebClient webClient;

    @Transactional
    public Security addSecurityAndHistory(FilesDto filesDto) {

        if (filesDto.getSecurityFile() != null) {
            Security security = XMLConverter.convertXMLtoObject(filesDto.getSecurityFile(), Security.class);
            if (filesDto.getHistoryFile() != null) {
                History history = XMLConverter.convertXMLtoObject(filesDto.getHistoryFile(), History.class);
                if (!historyService.isPersist(history.getSecId())) {
                    History savedHistory = historyService.save(history);
                    log.info("History was saved {}", savedHistory);
                    security.setHistory(savedHistory);
                    log.info("History was added to security {}", savedHistory);
                }
            }
            Security savedSecurity = securityRepository.save(security);
            log.info("Security was saved {}", savedSecurity);
            return security;
        }

        History history = XMLConverter.convertXMLtoObject(filesDto.getHistoryFile(), History.class);
        Security security = webClient.get()
                .uri("http://iss.moex.com/iss/securities.xml",
                        uriBuilder -> uriBuilder.queryParam("q", history.getSecId())
                                .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(Security.class)
                .block();

        if (security != null) {
            security.setHistory(history);
            log.info("History was added to security {}", history);
            Security savedSecurity = securityRepository.save(security);
            log.info("Security was saved {}", savedSecurity);
            return savedSecurity;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Security> getSecurityList() {
        List<Security> securityList = securityRepository.findAll();
        log.info("List of security was given");
        return securityList;
    }

    @Transactional(readOnly = true)
    public Security getSecurity(Long id) {
        Security retrievedSecurity = securityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Security not found"));
        log.info("Security was given {}", retrievedSecurity);
        return retrievedSecurity;
    }

    @Transactional
    public Security save(SecurityDto securityDto) {
        Security security = mapper.securityDtoToSecurity(securityDto);
        History history = historyService.getHistoryById(securityDto.getSecId());
        log.info("History was retrieved {}", history);
        security.setHistory(history);
        log.info("History was added to security");
        Security savedSecurity = securityRepository.save(security);
        log.info("Security was saved {}", savedSecurity);
        return savedSecurity;
    }

    public void deleteSecurity(Long id) {
        securityRepository.deleteById(id);
        log.info("Security was deleted");
    }

    public List<SecurityWithHistoryResponse> securityToResponse(List<Security> securityList) {
        return securityList.stream()
                .map(security -> {
                    SecurityWithHistoryResponse response = new SecurityWithHistoryResponse();
                    if (security.getHistory() != null) {
                        response.setId(security.getId());
                        response.setSecId(security.getHistory().getSecId());
                        response.setRegNumber(security.getRegNumber());
                        response.setName(security.getName());
                        response.setEmitentTitle(security.getEmitentTitle());
                        response.setTradeDate(security.getHistory().getTradeDate());
                        response.setNumTrades(security.getHistory().getNumTrades());
                        response.setOpen(security.getHistory().getOpen());
                    } else {
                        response.setId(security.getId());
                        response.setRegNumber(security.getRegNumber());
                        response.setName(security.getName());
                        response.setEmitentTitle(security.getEmitentTitle());
                    }
                    return response;
                }).toList();
    }


}
