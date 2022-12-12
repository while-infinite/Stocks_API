package ru.totsystems.stocks_api.convert;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import ru.totsystems.stocks_api.exception.MultipartFileConvertException;
import ru.totsystems.stocks_api.exception.XMLConvertException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

@Slf4j
public class XMLConverter {
    private final static String XML_DIRECTORY_PATH = "src/main/resources/xml";

    private XMLConverter() {
    }

    public static <T> T convertXMLtoObject(MultipartFile file, Class<T> className){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(className);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Path path = multipartFileToFile(file);
            return className.cast(unmarshaller.unmarshal(new File(String.valueOf(path))));
        } catch (JAXBException e) {
            throw new XMLConvertException("Failed convert XML to Object");
        }
    }

    public static Path multipartFileToFile(MultipartFile multipart)  {

        try {
            Path path = Path.of(XML_DIRECTORY_PATH + '/'
                    +(Objects.requireNonNull(multipart.getOriginalFilename())));
            File file = new File(path.toString());
            multipart.transferTo(new File(file.getAbsolutePath()));
            return Path.of(path.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new MultipartFileConvertException("Failed convert Multipart file to xml file");

    }

}
