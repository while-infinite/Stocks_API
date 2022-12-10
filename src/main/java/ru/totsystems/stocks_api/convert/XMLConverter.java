package ru.totsystems.stocks_api.convert;

import org.springframework.web.multipart.MultipartFile;
import ru.totsystems.stocks_api.model.History;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XMLConverter {
    public static History convertXMLtoObject(MultipartFile file){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(History.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (History) unmarshaller.unmarshal((File) file);
        } catch (JAXBException e) {
            throw new RuntimeException();
        }
    }

}
