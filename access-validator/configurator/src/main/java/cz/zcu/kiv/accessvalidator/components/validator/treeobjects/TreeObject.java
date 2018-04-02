package cz.zcu.kiv.accessvalidator.components.validator.treeobjects;

import cz.zcu.kiv.accessvalidator.components.validator.TreeItemObjectAdaptor;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;

import java.util.Collection;
import java.util.Collections;

/**
 * @author ike
 */
public abstract class TreeObject {

    protected ObservableList<TreeItem<TreeObject>> children;

    protected ContextMenu contextMenu = null;

    public boolean hasTooltipText() {
        return false;
    }

    public String getTooltipText() {
        return null;
    }

    public Collection<String> getStyleClasses() {
        return Collections.emptyList();
    }

    public void setChildren(ObservableList<TreeItem<TreeObject>> children) {
        this.children = children;
    }

    protected <T extends TreeObject> T addChild(T treeObject) {
        this.children.add(new TreeItemObjectAdaptor(treeObject));
        return treeObject;
    }

    public ContextMenu getContextMenu() {
        return this.contextMenu;
    }

}
