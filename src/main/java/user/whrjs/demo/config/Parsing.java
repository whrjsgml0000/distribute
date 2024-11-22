package user.whrjs.demo.config;

public enum Parsing {
    NAME("name"),
    URL("url"),
    QUERY("query"),
    CSS_SELECTOR("cssSelector"),
    ;
    private final String data;

    Parsing(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
