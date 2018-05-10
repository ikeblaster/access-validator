package cz.zcu.kiv.accessvalidator.configurator.components.validator;

import cz.zcu.kiv.accessvalidator.configurator.components.validator.treeobjects.TreeObject;
import javafx.scene.control.TreeItem;

/**
 * {@code TreeObject} adaptor for use as {@code TreeItem}.
 *
 * @author Vojtech Kinkor
 */
public class TreeItemObjectAdaptor extends TreeItem<TreeObject> {

    private TreeObject treeObject;

    /**
     * {@code TreeObject} adaptor for use as {@code TreeItem}.
     * Automatically adds children of {@code treeObject} as
     * {@code TreeItem} children.
     *
     * @param treeObject Object for adding into a tree.
     */
    public TreeItemObjectAdaptor(TreeObject treeObject) {
        super(treeObject);

        this.treeObject = treeObject;
        this.treeObject.setChildren(this.getChildren());
    }

}
