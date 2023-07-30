package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true) // it ignore unused
public class AirportFirstData {

    private AirportSecondData attributes;
    private String miles;

}

