package ru.totsystems.stocks_api.validate;

import com.google.common.io.Files;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.totsystems.stocks_api.dto.FilesDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FileValidator {

    private FileValidator() {
    }

    public static Map<String, FilesDto> validateRequest(MultipartFile[] multipartFiles) {
        Map<String, FilesDto> response = new HashMap<>();
        FilesDto filesDto = new FilesDto();

        if (multipartFiles.length < 1) {
            response.put("Please select a file to upload.", null);
            return response;
        }

        if (multipartFiles.length == 1) {
            MultipartFile file = multipartFiles[0];
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String extension = Files.getFileExtension(fileName);

            if (!isProperExtension(extension)) {
                response.put("Wrong file extension.", null);
                return response;
            }
            if(fileName.matches("^(security)+"))
                filesDto.setSecurityFile(file);
            else
                filesDto.setHistoryFile(file);
        }


        if (multipartFiles.length == 2) {
            MultipartFile securityFile = multipartFiles[0];
            MultipartFile historyFile = multipartFiles[1];
            String securityFileName = StringUtils.cleanPath(Objects.requireNonNull(securityFile.getOriginalFilename()));
            String historyFileName = StringUtils.cleanPath(Objects.requireNonNull(historyFile.getOriginalFilename()));
            String securityExtension = Files.getFileExtension(securityFileName);
            String historyExtension = Files.getFileExtension(historyFileName);

            if (!isProperExtension(historyExtension)) {
                response.put("Wrong file extension.", null);
                return response;
            }
            else
                filesDto.setSecurityFile(securityFile);

            if (!isProperExtension(securityExtension)) {
                response.put("Wrong file extension.", null);
                return response;
            }
            else
                filesDto.setHistoryFile(historyFile);
        }


        response.put("You successfully uploaded", filesDto);
        return response;
    }

    private static boolean isProperExtension(String extension){
        return "xml".equals(extension);
    }
}
