package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author ike
 */
public class Property {

    private final String id;
    private ObjectProperty<Object> value;

    private String name;
    private String description;
    private String category;


    public Property(String id, Object value, String name, String description, String category) {
        this.id = id;
        this.value = new SimpleObjectProperty<>(value);
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
        return value.getValue();
    }

    public void setValue(Object value) {
        this.value.setValue(value);
    }

    public void onChange(InvalidationListener listener) {
        this.value.addListener(listener);
    }

}
