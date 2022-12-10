package ru.totsystems.stocks_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "security")
@XmlRootElement
public class Security {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "secid")
    private History history;

    @Column(name = "regnumber")
    @XmlElement(name = "regnumber")
    private int regNumber;

    private String name;

    @Column(name = "element_title")
    @XmlElement(name = "element_title")
    private String elementTitle;

}
