package cz.zcu.kiv.accessvalidator.components.validator.treeobjects;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.SimilarityElement;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public String toString() {
        String ret = this.file.getName();

        if(this.isChecked()) {
            ret += " " + (this.isValid() ? "[OK]" : "[nevalidní]");
        }

        return ret;
    }

    public void setDuplicates(List<Map.Entry<SimilarityElement, Set<Accdb>>> duplicates) {
        this.children.clear();

        for (Map.Entry<SimilarityElement, Set<Accdb>> entry : duplicates) {
            StringTreeObject node = new StringTreeObject(entry.getKey().toString());
            this.addChild(node);

            for (Accdb accdb : entry.getValue()) {
                node.addChild(new FileTreeObject(accdb.getFile()));
            }
        }
    }
}
