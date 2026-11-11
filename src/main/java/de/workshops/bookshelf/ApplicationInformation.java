package de.workshops.bookshelf;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name = "app")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class ApplicationInformation {

    @XmlElement(name = "name")
    private String name;

}
