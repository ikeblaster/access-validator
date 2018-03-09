package cz.zcu.kiv.accessvalidator.components.validator.treeobjects;

/**
 * @author ike
 */
public class StringTreeObject extends TreeObject {

    private String value;

    public StringTreeObject(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
