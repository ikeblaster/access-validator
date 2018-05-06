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
 * Represents a file object in a tree view.
 *
 * @author ike
 */
public class FileTreeObject extends TreeObject {

    /**
     * Associated file.
     */
    private File file;

    /**
     * Suffix of label.
     */
    private String labelSuffix = null;

    /**
     * State of file - already checked.
     */
    private boolean checked = false;

    /**
     * State of file - valid after a check.
     */
    private boolean valid = false;

    /**
     * State of file - found similar files to it.
     */
    private boolean foundSimilarFiles = false;

    /**
     * State of file - found highly similar files to it (possibly plagiarism).
     */
    private boolean foundHighlySimilarFile = false;

    /**
     * Handler for hiding similarities between files.
     * Handler is called when user clicks "hide" in context menu of this item.
     */
    private Consumer<SimilarityElement> hideSimilarityHandler;

    /**
     * Represents a file object in a tree view.
     *
     * @param file Associated file.
     */
    public FileTreeObject(File file) {
        this.file = file;
        this.contextMenu = this.getDefaultFileContextMenu();
    }

    /**
     * Gets the associated file.
     *
     * @return Associated file.
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Sets label suffix.
     *
     * @param labelSuffix Suffix of label.
     */
    public void setLabelSuffix(String labelSuffix) {
        this.labelSuffix = labelSuffix;
    }

    /**
     * Sets state of a file. Also sets {@code checked} to {@code true}.
     *
     * @param valid Validness of the file.
     */
    public void setValid(boolean valid) {
        this.checked = true;
        this.valid = valid;
    }

    /**
     * Resets state of a file - sets {@code checked}, {@code valid},
     * {@code foundSimilarFiles} to {@code false} and removes all children.
     */
    public void resetState() {
        this.children.clear();
        this.checked = false;
        this.valid = false;
        this.foundSimilarFiles = false;
        this.foundHighlySimilarFile = false;
    }

    /**
     * Sets handler for hiding similarities between files.
     * Handler is called when user clicks "hide" in context menu of this item.
     *
     * @param hideSimilarityHandler Handler for hiding similarities.
     */
    public void onHideSimilarity(Consumer<SimilarityElement> hideSimilarityHandler) {
        this.hideSimilarityHandler = hideSimilarityHandler;
    }

    /**
     * Processes similar files to this file.
     *
     * @param similarFiles Object holding informations about similar files.
     */
    public void setSimilarFiles(SimilarFiles similarFiles) {
        this.foundSimilarFiles = false;
        this.foundHighlySimilarFile = false;

        if (similarFiles == null || similarFiles.getSimilarFiles().isEmpty()) {
            return;
        }

        this.foundSimilarFiles = true;

        StringTreeObject parent = this.addChild(new StringTreeObject("Nalezené podobné databáze"));

        for (File similar : similarFiles.getSimilarFiles()) {
            FileTreeObject node = parent.addChild(new FileTreeObject(similar));
            int severity = 0;

            for (SimilarityElement similarity : similarFiles.getFileSimilarities(similar)) {
                StringTreeObject simObject = node.addChild(new StringTreeObject(similarity.toString()));
                simObject.contextMenu = this.createHideSimilarityContextMenu(similarity);
                severity += similarity.getSeverity();
            }

            if(severity >= 100) {
                this.foundHighlySimilarFile = true;
                node.setLabelSuffix(" [plagiát]");
            }

            node.children.sort(Comparator.comparing(TreeItem::toString, String.CASE_INSENSITIVE_ORDER));
        }
        parent.children.sort(Comparator.comparing(TreeItem::toString, String.CASE_INSENSITIVE_ORDER));
    }

    /**
     * Processes a set of failed rules during check.
     *
     * @param failedRules Set of failed rules.
     */
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

    /**
     * Checks whether the item provides tooltip text.
     *
     * @return {@code true}
     */
    @Override
    public boolean hasTooltipText() {
        return true;
    }

    /**
     * Gets the tooltip text for treeview item.
     *
     * @return Path to the file.
     */
    @Override
    public String getTooltipText() {
        return this.file.getPath();
    }

    /**
     * Gets collection of item classes.
     *
     * @return Combination of {@code icon-valid}, {@code icon-invalid} and {@code icon-similar}.
     */
    @Override
    public Collection<String> getStyleClasses() {
        List<String> classes = new ArrayList<>();

        if (this.checked) {
            classes.add(this.valid ? "icon-valid" : "icon-invalid");
        }
        if (this.foundSimilarFiles) {
            classes.add("icon-similar");
        }
        if (this.foundHighlySimilarFile) {
            classes.add("cls-plagiarism");
        }

        return classes;
    }

    /**
     * Gets label for object shown in tree.
     *
     * @return Filename and validity state.
     */
    @Override
    public String toString() {
        String ret = this.file.getName();

        if(this.labelSuffix != null && !this.labelSuffix.isEmpty()) {
            ret += this.labelSuffix;
        }

        if (this.checked) {
            if(this.foundHighlySimilarFile) {
                ret += " [plagiarismus]";
            }
            else {
                ret += " " + (this.valid ? "[OK]" : "[nevalidní]");
            }
        }

        return ret;
    }

    /**
     * Creates a context menu with "hide similarity" action.
     *
     * @param similarity Similarity element which can be hidden.
     * @return Context menu.
     */
    private ContextMenu createHideSimilarityContextMenu(SimilarityElement similarity) {
        return new ContextMenu(
                new MenuItem("Skrýt tento druh podobnosti") {{
                    this.setOnAction(event -> FileTreeObject.this.hideSimilarityHandler.accept(similarity));
                }}
        );
    }

    /**
     * Gets default context menu for files with "open file" and "open folder" actions.
     *
     * @return Context menu.
     */
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
