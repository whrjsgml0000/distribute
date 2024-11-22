package user.whrjs.demo.config;

public enum Category {
    BLOG("blog"),
    NEWS("news"),
    SHOPPING("shopping"),
    ;

    private final String category;
    Category(String category){
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
