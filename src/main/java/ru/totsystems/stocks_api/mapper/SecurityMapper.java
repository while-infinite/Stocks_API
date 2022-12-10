package ru.totsystems.stocks_api.mapper;

import org.mapstruct.Mapper;
import ru.totsystems.stocks_api.dto.SecurityDto;
import ru.totsystems.stocks_api.model.Security;

@Mapper(componentModel = "spring")
public interface SecurityMapper {
    Security securityDtoToSecurity(SecurityDto securityDto);
    SecurityDto securityToSecurityDto(Security security);
}
