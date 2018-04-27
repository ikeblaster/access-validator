package cz.zcu.kiv.accessvalidator.components.validator.treeobjects;

/**
 * Represents basic object in a tree view.
 * Contains only text (label).
 *
 * @author ike
 */
public class StringTreeObject extends TreeObject {

    /**
     * Label of item.
     */
    private String value;

    /**
     * Represents basic object in a tree view.
     * Contains only text (label).
     *
     * @param value Label of item.
     */
    public StringTreeObject(String value) {
        this.value = value;
    }

    /**
     * Gets label for object shown in tree.
     * @return Label of item.
     */
    @Override
    public String toString() {
        return this.value;
    }

}
