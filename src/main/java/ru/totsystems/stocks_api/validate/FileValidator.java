package ru.totsystems.stocks_api.validate;

import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.totsystems.stocks_api.dto.FilesDto;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class FileValidator {

    private FileValidator() {
    }

    public static Map<String, FilesDto> validateRequest(MultipartFile[] multipartFiles) {
        Map<String, FilesDto> response = new HashMap<>();
        FilesDto filesDto = new FilesDto();

        if (multipartFiles[0].isEmpty() && multipartFiles[1].isEmpty()) {
            response.put("Please select a file to upload.", null);
            log.info("There is no any file!");
            return response;
        }

        if (!multipartFiles[0].isEmpty() && !multipartFiles[1].isEmpty()) {
            MultipartFile securityFile = multipartFiles[0];
            MultipartFile historyFile = multipartFiles[1];
            String securityFileName = StringUtils.cleanPath(Objects.requireNonNull(securityFile.getOriginalFilename()));
            String historyFileName = StringUtils.cleanPath(Objects.requireNonNull(historyFile.getOriginalFilename()));
            String securityExtension = Files.getFileExtension(securityFileName);
            String historyExtension = Files.getFileExtension(historyFileName);

            if (!isProperExtension(securityExtension)) {
                response.put("Wrong file extension.", null);
                log.info("Wrong file extension");
                return response;
            } else
                filesDto.setSecurityFile(securityFile);

            if (!isProperExtension(historyExtension)) {
                response.put("Wrong file extension.", null);
                log.info("Wrong file extension");
                return response;
            } else
                filesDto.setHistoryFile(historyFile);

        }

        if (!multipartFiles[0].isEmpty() && multipartFiles[1].isEmpty()) {
            return getResponse(multipartFiles[0], response, filesDto);
        }

        if (!multipartFiles[1].isEmpty() && multipartFiles[0].isEmpty()) {
            return getResponse(multipartFiles[1], response, filesDto);
        }

        response.put("You successfully uploaded!", filesDto);
        log.info("File(s) was/were uploaded");
        return response;
    }

    private static boolean isProperExtension(String extension) {
        return "xml".equals(extension.toLowerCase(Locale.ROOT));
    }

    private static Map<String, FilesDto> getResponse(MultipartFile file, Map<String, FilesDto> response,
                                                     FilesDto filesDto) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        log.info("File name {}", fileName);
        String extension = Files.getFileExtension(fileName);
        log.info("File extension {}", extension);

        if (!isProperExtension(extension)) {
            response.put("Wrong file extension.", null);
            log.info("Wrong file extension");
            return response;
        }
        if (fileName.matches("^[security].*"))
            filesDto.setSecurityFile(file);
        else
            if (fileName.matches("^[history].*"))
                filesDto.setHistoryFile(file);

        response.put("You successfully uploaded!", filesDto);
        log.info("File was uploaded");
        return response;
    }
}
