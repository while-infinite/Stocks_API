package ru.totsystems.stocks_api.convert;

import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XMLConverter {

    private XMLConverter() {
    }

    public static <T> T convertXMLtoObject(MultipartFile file, Class<T> className){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(className);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return className.cast(unmarshaller.unmarshal((File) file));
        } catch (JAXBException e) {
            throw new RuntimeException();
        }
    }

}
