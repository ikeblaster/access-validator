package cz.zcu.kiv.accessvalidator.components.validator.treeobjects;

import cz.zcu.kiv.accessvalidator.validator.database.SimilarFiles;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.File;
import java.util.List;

/**
 * @author ike
 */
public class FileTreeObject extends TreeObject {

    private File file;
    private boolean checked = false;
    private SimpleBooleanProperty valid = new SimpleBooleanProperty();


    public FileTreeObject(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public boolean isValid() {
        return this.valid.get();
    }

    public void setValid(Boolean valid) {
        this.checked = true;
        this.valid.set(valid);
    }

    @Override
    public boolean hasTooltipText() {
        return true;
    }

    @Override
    public String getTooltipText() {
        return this.file.getPath();
    }

    @Override
    public String getStyleclass() {
        return (this.isChecked() ? (this.isValid() ? "icon-valid" : "icon-invalid") : "icon-empty");
    }

    @Override
    public String toString() {
        String ret = this.file.getName();

        if(this.isChecked()) {
            ret += " " + (this.isValid() ? "[OK]" : "[nevalidní]");
        }

        return ret;
    }

    public void setSimilarFiles(List<SimilarFiles> similarFiles) {
        this.children.clear();

        if(similarFiles != null) {
            for (SimilarFiles similar : similarFiles) {
                StringTreeObject node = new StringTreeObject(similar.toString());
                this.addChild(node);

                for (File file : similar.getFiles()) {
                    node.addChild(new FileTreeObject(file));
                }
            }
        }
    }
}
