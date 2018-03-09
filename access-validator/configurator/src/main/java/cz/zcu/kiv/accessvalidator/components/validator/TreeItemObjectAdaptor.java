package cz.zcu.kiv.accessvalidator.components.validator;

import cz.zcu.kiv.accessvalidator.components.validator.treeobjects.TreeObject;
import javafx.scene.control.TreeItem;

/**
 * @author ike
 */
public class TreeItemObjectAdaptor extends TreeItem<TreeObject> {

    private TreeObject treeObject;

    public TreeItemObjectAdaptor(TreeObject treeObject) {
        super(treeObject);

        this.treeObject = treeObject;
        this.treeObject.setChildren(this.getChildren());
    }

}
