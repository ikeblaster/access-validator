package cz.zcu.kiv.accessvalidator.components.validator;

import cz.zcu.kiv.accessvalidator.components.validator.treeobjects.FileTreeObject;
import cz.zcu.kiv.accessvalidator.components.validator.treeobjects.TreeObject;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Validator pane root item for files.
 *
 * @author ike
 */
public class TreeItemRoot extends TreeItem<TreeObject> {

    /**
     * Adds a file into the tree wrapped into {@code FileTreeObject}.
     *
     * @param file File.
     * @return Added {@code TreeObject}.
     */
    public FileTreeObject addFile(File file) {
        FileTreeObject treeObject = new FileTreeObject(file);
        this.getChildren().add(new TreeItemObjectAdaptor(treeObject));
        return treeObject;
    }

    /**
     * Gets a collection of all tree items, i.e. all files wrapped in {@code FileTreeObject}.
     *
     * @return Collection of all tree items.
     */
    public List<FileTreeObject> getFileWrappers() {
        return this.getChildren()
                .stream()
                .map(treeItem -> (FileTreeObject) treeItem.getValue())
                .collect(Collectors.toList());
    }

    /**
     * Gets a collection of all files added into the tree.
     *
     * @return Collection of files.
     */
    public List<File> getFiles() {
        return this.getChildren()
                .stream()
                .map(treeItem -> ((FileTreeObject) treeItem.getValue()).getFile())
                .collect(Collectors.toList());
    }

}
