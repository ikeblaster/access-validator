package cz.zcu.kiv.accessvalidator.components.validator.treeobjects;

import cz.zcu.kiv.accessvalidator.components.validator.TreeItemObjectAdaptor;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * @author ike
 */
public abstract class TreeObject {

    protected ObservableList<TreeItem<TreeObject>> children;

    public boolean hasTooltipText() {
        return false;
    }

    public String getTooltipText() {
        return null;
    }

    public String getStyleclass() {
        return "";
    }

    public void setChildren(ObservableList<TreeItem<TreeObject>> children) {
        this.children = children;
    }

    protected void addChild(TreeObject treeObject) {
        this.children.add(new TreeItemObjectAdaptor(treeObject));
    }



}
