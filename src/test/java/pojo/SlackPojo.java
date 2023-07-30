package pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true) // it ignore unused
public class SlackPojo {

    private boolean ok;
    private String ts;

    private SlackMessagePojo message;
}
