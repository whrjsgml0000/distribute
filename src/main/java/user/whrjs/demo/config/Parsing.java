package user.whrjs.demo.config;

public enum Parsing {
    URL("url"),
    QUERY("query"),
    CSS("cssSelector"),
    ;
    private final String data;
    Parsing(String data){
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
