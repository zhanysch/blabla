package pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
@Setter
@Getter
public class PetStorePojo {

    private int id;

    private PetstoreCategoryPojo category;

    private String name;

    private List<String> photoUrls;

    private List<Object> tags;

    private String status;
}
