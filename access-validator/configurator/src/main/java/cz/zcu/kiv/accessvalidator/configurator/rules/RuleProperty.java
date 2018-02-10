package cz.zcu.kiv.accessvalidator.configurator.rules;

/**
 * @author ike
 */
public class RuleProperty {

    private final String id;
    private Object value;

    private String name;
    private String description;
    private String category;

    public RuleProperty(String id, Object value, String name, String description, String category) {
        this.id = id;
        this.value = value;
        this.name = name;
        this.description = description;
        this.category = category;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }


    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
