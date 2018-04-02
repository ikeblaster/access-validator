package cz.zcu.kiv.accessvalidator.components.validator.treeobjects;

import cz.zcu.kiv.accessvalidator.common.DesktopHelper;
import cz.zcu.kiv.accessvalidator.validator.database.SimilarFiles;
import cz.zcu.kiv.accessvalidator.validator.database.SimilarityElement;
import cz.zcu.kiv.accessvalidator.validator.rules.Rule;
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
        this.contextMenu = this.getDefaultFileContextMenu();
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

        if (similarFiles == null || similarFiles.getSimilarFiles().isEmpty()) {
            return;
        }

        this.foundSimilarFiles = true;

        StringTreeObject parent = this.addChild(new StringTreeObject("Nalezené podobné databáze"));

        for (File similar : similarFiles.getSimilarFiles()) {
            FileTreeObject node = parent.addChild(new FileTreeObject(similar));

            for (SimilarityElement similarity : similarFiles.getFileSimilarities(similar)) {
                StringTreeObject simObject = node.addChild(new StringTreeObject(similarity.toString()));
                simObject.contextMenu = this.createHideSimilarityContextMenu(similarity);
            }

            node.children.sort(Comparator.comparing(TreeItem::toString, String.CASE_INSENSITIVE_ORDER));
        }
        parent.children.sort(Comparator.comparing(TreeItem::toString, String.CASE_INSENSITIVE_ORDER));
    }


    public void setFailedRules(Set<Rule> failedRules) {
        if (failedRules.size() == 0) {
            return;
        }

        StringTreeObject parent = this.addChild(new StringTreeObject("Pravidla, která selhala"));

        for (Rule rule : failedRules) {
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

        if (this.checked) {
            classes.add(this.valid ? "icon-valid" : "icon-invalid");
        }
        if (this.foundSimilarFiles) {
            classes.add("icon-similar");
        }

        return classes;
    }

    @Override
    public String toString() {
        String ret = this.file.getName();

        if (this.checked && !this.foundSimilarFiles) {
            ret += " " + (this.valid ? "[OK]" : "[nevalidní]");
        }

        return ret;
    }


    private ContextMenu createHideSimilarityContextMenu(SimilarityElement similarity) {
        return new ContextMenu(
                new MenuItem("Skrýt tento druh podobnosti") {{
                    this.setOnAction(event -> FileTreeObject.this.hideSimilarityHandler.accept(similarity));
                }}
        );
    }


    public ContextMenu getDefaultFileContextMenu() {
        return new ContextMenu(
                new MenuItem("Otevřít soubor") {{
                    this.setOnAction(event -> DesktopHelper.openFile(FileTreeObject.this.file));
                }},
                new MenuItem("Otevřít složku") {{
                    this.setOnAction(event -> DesktopHelper.browseParentDirectory(FileTreeObject.this.file));
                }}
        );
    }
}
