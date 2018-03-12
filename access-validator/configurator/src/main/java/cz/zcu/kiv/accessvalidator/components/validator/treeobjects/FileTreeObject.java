package cz.zcu.kiv.accessvalidator.components.validator.treeobjects;

import cz.zcu.kiv.accessvalidator.validator.database.SimilarFiles;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author ike
 */
public class FileTreeObject extends TreeObject {

    private File file;
    private boolean checked = false;
    private boolean valid = false;
    private List<SimilarFiles> similarFiles = Collections.emptyList();


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
        return this.valid;
    }

    public void setValid(boolean valid) {
        this.checked = true;
        this.valid = valid;
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
    public Collection<String> getStyleclass() {
        List<String> classes = new ArrayList<>();

        if(this.checked) {
            classes.add(this.valid ? "icon-valid" : "icon-invalid");
        }
        if(this.similarFiles.size() > 0) {
            classes.add("icon-similar");
        }

        return classes;
    }

    @Override
    public String toString() {
        String ret = this.file.getName();

        if(this.isChecked()) {
            ret += " " + (this.valid ? "[OK]" : "[nevalidní]");
        }

        return ret;
    }

    public void setSimilarFiles(List<SimilarFiles> similarFiles) {
        this.similarFiles = similarFiles;
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
