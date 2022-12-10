package ru.totsystems.stocks_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.totsystems.stocks_api.convert.XMLConverter;
import ru.totsystems.stocks_api.dto.FilesDto;
import ru.totsystems.stocks_api.dto.SecurityDto;
import ru.totsystems.stocks_api.exception.NotFoundException;
import ru.totsystems.stocks_api.mapper.SecurityMapper;
import ru.totsystems.stocks_api.model.History;
import ru.totsystems.stocks_api.model.Security;
import ru.totsystems.stocks_api.repository.SecurityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final SecurityRepository securityRepository;
    private final SecurityMapper mapper;
    private final HistoryService historyService;

    @Transactional
    public Security addSecurityAndHistory(FilesDto filesDto){
        if(filesDto.getSecurityFile() != null || !filesDto.getSecurityFile().isEmpty()){
            Security security = XMLConverter.convertXMLtoObject(filesDto.getSecurityFile(), Security.class);
            if(filesDto.getHistoryFile() != null || !filesDto.getHistoryFile().isEmpty()){
                History history = XMLConverter.convertXMLtoObject(filesDto.getHistoryFile(), History.class);
                security.setHistory(history);
            }
           return securityRepository.save(security);
        }
        return null;

    }

    @Transactional(readOnly = true)
    public List<Security> getSecurityList() {
        return securityRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Security getSecurity(Long id) {
        return securityRepository.findById(id).orElseThrow(() -> new NotFoundException("Security not found"));
    }

    @Transactional
    public Security save(SecurityDto securityDto) {
        Security security = mapper.securityDtoToSecurity(securityDto);
        History history = historyService.getHistoryById(securityDto.getSecId());
        security.setHistory(history);
        return securityRepository.save(security);
    }

    public void deleteSecurity(Long id){
        securityRepository.deleteById(id);
    }



}
