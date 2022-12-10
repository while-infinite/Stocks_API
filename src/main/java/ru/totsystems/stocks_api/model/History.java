package ru.totsystems.stocks_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "history")
@XmlRootElement
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(name = "secid")
    @Column(name = "secid")
    private Long secId;

    @XmlElement(name = "tradedate")
    @Column(name = "tradedate")
    private LocalDate tradeDate;

    @XmlElement(name = "numtrades")
    @Column(name = "numtrades")
    private int numTrades;

    private String open;
}
