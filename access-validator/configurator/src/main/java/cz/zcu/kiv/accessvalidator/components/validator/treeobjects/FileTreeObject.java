package cz.zcu.kiv.accessvalidator.components.validator.treeobjects;

import cz.zcu.kiv.accessvalidator.validator.database.SimilarFiles;
import cz.zcu.kiv.accessvalidator.validator.database.SimilarityElement;
import cz.zcu.kiv.accessvalidator.validator.rules.Rule;
import javafx.collections.FXCollections;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author ike
 */
public class FileTreeObject extends TreeObject {

    private File file;
    private boolean checked = false;
    private boolean valid = false;
    private boolean foundSimilarFiles = false;
    private Consumer<SimilarityElement> hideSimilarityHandler;

    public FileTreeObject(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public void setValid(boolean valid) {
        this.checked = true;
        this.valid = valid;
    }

    public void clearInfo() {
        this.children.clear();
        this.valid = false;
        this.foundSimilarFiles = false;
    }

    public void onHideSimilarity(Consumer<SimilarityElement> hideSimilarityHandler) {
        this.hideSimilarityHandler = hideSimilarityHandler;
    }

    public void setSimilarFiles(SimilarFiles similarFiles) {
        this.foundSimilarFiles = false;

        if(similarFiles == null || similarFiles.getSimilarFiles().isEmpty()) {
            return;
        }

        this.foundSimilarFiles = true;

        StringTreeObject parent = new StringTreeObject("Nalezené podobné databáze");
        this.addChild(parent);

        for (File similar : similarFiles.getSimilarFiles()) {
            FileTreeObject node = new FileTreeObject(similar);
            parent.addChild(node);

            for (SimilarityElement similarity : similarFiles.getFileSimilarities(similar)) {
                StringTreeObject simObject = new StringTreeObject(similarity.toString());
                simObject.contextMenu = new ContextMenu(new MenuItem("Skrýt tento druh podobnosti"){{
                    this.setOnAction(event -> FileTreeObject.this.hideSimilarityHandler.accept(similarity));
                }});
                node.addChild(simObject);
            }

            FXCollections.sort(node.children, Comparator.comparing(TreeItem::toString, String.CASE_INSENSITIVE_ORDER));
        }
    }

    public void setFailedRules(Set<Rule> failedRules) {
        if(failedRules.size() == 0) {
            return;
        }

        StringTreeObject parent = new StringTreeObject("Pravidla, která selhala");
        this.addChild(parent);

        for(Rule rule : failedRules) {
            StringTreeObject node = new StringTreeObject(rule.toString());
            parent.addChild(node);
        }
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
    public Collection<String> getStyleClasses() {
        List<String> classes = new ArrayList<>();

        if(this.checked) {
            classes.add(this.valid ? "icon-valid" : "icon-invalid");
        }
        if(this.foundSimilarFiles) {
            classes.add("icon-similar");
        }

        return classes;
    }

    @Override
    public String toString() {
        String ret = this.file.getName();

        if(this.checked && !this.foundSimilarFiles) {
            ret += " " + (this.valid ? "[OK]" : "[nevalidní]");
        }

        return ret;
    }


}
