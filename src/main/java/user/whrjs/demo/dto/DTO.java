package user.whrjs.demo.dto;

import lombok.Getter;
import lombok.Setter;

// todo 이름 나중에 바꾸셈.
@Getter
@Setter
public class DTO {
    private String name;
    private String url;
    private String query;
    private String cssSelector;
    private String title;
    private Boolean absolute;
    private String prefix;
}
