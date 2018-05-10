package cz.zcu.kiv.accessvalidator.configurator.components.validator.treeobjects;

import cz.zcu.kiv.accessvalidator.configurator.components.validator.TreeItemObjectAdaptor;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents a general object in a tree view.
 *
 * @author Vojtech Kinkor
 */
public abstract class TreeObject {

    /**
     * Collection of children.
     */
    protected ObservableList<TreeItem<TreeObject>> children;

    /**
     * Associated context menu.
     */
    protected ContextMenu contextMenu = null;

    /**
     * Checks whether the item provides tooltip text. By default false.
     *
     * @return {@code true} when the item has tooltip text defined, {@code false} otherwise.
     */
    public boolean hasTooltipText() {
        return false;
    }

    /**
     * Gets the tooltip text for treeview item. By default null.
     *
     * @return Tooltip text.
     */
    public String getTooltipText() {
        return null;
    }

    /**
     * Gets collection of item classes.
     *
     * @return Collection of classes.
     */
    public Collection<String> getStyleClasses() {
        return Collections.emptyList();
    }

    /**
     * Gets context menu.
     *
     * @return Context menu for item.
     */
    public ContextMenu getContextMenu() {
        return this.contextMenu;
    }

    /**
     * Sets children of item (replaces old one).
     *
     * @param children Children of the item.
     */
    public void setChildren(ObservableList<TreeItem<TreeObject>> children) {
        this.children = children;
    }

    /**
     * Adds an object as a child. Returns added object for chaining.
     *
     * @param treeObject Object to be added.
     * @param <T> Type of object.
     * @return {@code treeObject}
     */
    protected <T extends TreeObject> T addChild(T treeObject) {
        this.children.add(new TreeItemObjectAdaptor(treeObject));
        return treeObject;
    }

}
