package cz.zcu.kiv.accessvalidator.components.validator;

import cz.zcu.kiv.accessvalidator.components.validator.treeobjects.FileTreeObject;
import cz.zcu.kiv.accessvalidator.components.validator.treeobjects.TreeObject;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ike
 */
public class TreeItemRoot extends TreeItem<TreeObject> {

    public FileTreeObject addFile(File file) {
        FileTreeObject treeObject = new FileTreeObject(file);
        this.getChildren().add(new TreeItemObjectAdaptor(treeObject));
        return treeObject;
    }

    public List<FileTreeObject> getFileWrappers() {
        return this.getChildren()
                .stream()
                .map(treeItem -> (FileTreeObject) treeItem.getValue())
                .collect(Collectors.toList());
    }

    public List<File> getFiles() {
        return this.getChildren()
                .stream()
                .map(treeItem -> ((FileTreeObject) treeItem.getValue()).getFile())
                .collect(Collectors.toList());
    }

}
