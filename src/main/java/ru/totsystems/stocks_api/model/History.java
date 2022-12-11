package ru.totsystems.stocks_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.totsystems.stocks_api.adapter.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "history")
@XmlRootElement(name = "History")
@XmlAccessorType(XmlAccessType.FIELD)
public class History {

    @Id
    @XmlElement(name = "secid")
    @Column(name = "secid")
    private String secId;

    @XmlElement(name = "tradedate")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    @Column(name = "tradedate")
    private LocalDate tradeDate;

    @XmlElement(name = "numtrades")
    @Column(name = "numtrades")
    private int numTrades;

    private String open;
}
