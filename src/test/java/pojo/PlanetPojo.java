package pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true) // it ignore unused
public class PlanetPojo {


    private String population;
    private String terrain;
    private String name;
   /* private String name;

    private String rotation_period;
    private String orbital_period;

    private String diameter;

    private String climate;

    private String gravity;

    private String population;
    private String terrain;

    private String surface_water;

    private String created;
    private String edited;
    private String url;

    private List<String> residents;
    private List<String> films;*/
}
