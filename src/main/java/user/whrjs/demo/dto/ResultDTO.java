package user.whrjs.demo.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDTO {
    private String name;
    private List<String> links;
    private List<String> titles;
}
