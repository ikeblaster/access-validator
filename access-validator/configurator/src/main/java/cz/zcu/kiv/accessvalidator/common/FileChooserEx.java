package cz.zcu.kiv.accessvalidator.common;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * Wrapper for {@code FileChooser}, extending its functionality and providing simpler usage.
 * Provides simpler interface and remembers last used directory (using {@code Preferences} storage).
 *
 * @author ike
 */
public class FileChooserEx {

    /**
     * Key to preferences for rememebering the directory.
     */
    private static final String PREF_INITIAL_DIRECTORY = "initial_directory";

    /**
     * File chooser itself.
     */
    private final FileChooser fileChooser = new FileChooser();

    /**
     * Parent stage of file chooser.
     */
    private Stage stage;

    /**
     * Last selected file.
     */
    private File file;

    /**
     * Object with preferences, used for saving last used directory.
     */
    private Preferences prefs;

    /**
     * Wrapper for {@code FileChooser}, extending its functionality and providing simpler usage.
     */
    public FileChooserEx() {
    }

    /**
     * Wrapper for {@code FileChooser}, extending its functionality and providing simpler usage.
     * Remember last used directory.
     *
     * @param persistenceKey Key into preferences storage for saving last used directory.
     */
    public FileChooserEx(String persistenceKey) {
        // load initial directory from preferences
        Preferences prefs = Preferences.userNodeForPackage(this.getClass()).node(persistenceKey); // Win: HKCU\Software\JavaSoft\Prefs

        String initial = prefs.get(PREF_INITIAL_DIRECTORY, null);
        if(initial != null) {
            this.setInitialDirectory(new File(initial));
        }

        this.prefs = prefs;
    }

    /**
     * Gets the extension filters used in the displayed file dialog.
     *
     * @return An observable list of the extension filters used in this dialog.
     */
    public ObservableList<FileChooser.ExtensionFilter> getExtensionFilters() {
        return this.fileChooser.getExtensionFilters();
    }

    /**
     * Adds new extension filter into the file chooser.
     *
     * @param extensionFilter Extension filter to be added.
     */
    public void addExtensionFilter(FileChooser.ExtensionFilter extensionFilter) {
        this.fileChooser.getExtensionFilters().add(extensionFilter);
    }

    /**
     * Shows a file open dialog.
     *
     * @return {@code true} when readable and writable file was selected, {@code false} otherwise.
     */
    public boolean openFile() {
        File file = this.fileChooser.showOpenDialog(this.stage);

        if(file == null || !file.canRead() || !file.canWrite()) {
            return false;
        }

        this.file = file;
        this.setInitialDirectory(file.getParentFile());
        return true;
    }

    /**
     * Shows a file open dialog with ability for selecting multiple files.
     *
     * @return Collection of selected files.
     */
    public List<File> openMultipleFiles() {
        List<File> files = this.fileChooser.showOpenMultipleDialog(this.stage);

        if(files == null || files.isEmpty()) {
            return Collections.emptyList();
        }

        this.file = files.get(0);
        this.setInitialDirectory(this.file.getParentFile());
        return files;
    }

    /**
     * Shows a file save dialog.
     *
     * @return {@code true} when file was selected, {@code false} otherwise.
     */
    public boolean saveFile() {
        File file = this.fileChooser.showSaveDialog(this.stage);

        if(file == null) {
            return false;
        }

        this.file = file;
        this.setInitialDirectory(this.file.getParentFile());
        return true;
    }

    /**
     * Tests whether a file has already been selected.
     *
     * @return {@code true} if file has been already selected.
     */
    public boolean hasFile() {
        return this.file != null;
    }

    /**
     * Gets the selected file. Returns first one when multiple files was selected.
     *
     * @return Selected file.
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Sets a file as selected. Can be {@code null} for clearing the state.
     *
     * @param file A file to be set as selected.
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Sets parent stage for file chooser.
     *
     * @param stage A stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets an initial directory of file chooser for next operation.
     * Saves that path into preferences storage when possible.
     *
     * @param path Path to the initial directory.
     */
    private void setInitialDirectory(File path) {
        this.fileChooser.setInitialDirectory(path);
        if(this.prefs != null) {
            this.prefs.put(PREF_INITIAL_DIRECTORY, path.getAbsolutePath());
        }
    }

}
