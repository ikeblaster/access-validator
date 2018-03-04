package cz.zcu.kiv.accessvalidator.common;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * @author ike
 */
public class FileChooserEx {

    private static final String PREF_INITIAL_DIRECTORY = "initial_directory";

    private FileChooser fileChooser = new FileChooser();
    private Stage stage;
    private File file;

    private Preferences prefs;

    public FileChooserEx() {
    }

    public FileChooserEx(String persistenceKey) {

        // load initial directory from preferences
        Preferences prefs = Preferences.userNodeForPackage(this.getClass()).node(persistenceKey); // HKCU\Software\JavaSoft\Prefs

        String initial = prefs.get(PREF_INITIAL_DIRECTORY, null);
        if(initial != null) {
            this.setInitialDirectory(new File(initial));
        }

        this.prefs = prefs;

    }

    public ObservableList<FileChooser.ExtensionFilter> getExtensionFilters() {
        return this.fileChooser.getExtensionFilters();
    }

    public void addExtensionFilter(FileChooser.ExtensionFilter extensionFilter) {
        this.fileChooser.getExtensionFilters().add(extensionFilter);
    }

    public boolean openFile() {
        File file = this.fileChooser.showOpenDialog(this.stage);

        if(file == null || !file.canRead() || !file.canWrite()) {
            return false;
        }

        this.file = file;
        this.setInitialDirectory(file.getParentFile());
        return true;
    }

    public List<File> openMultipleFiles() {
        List<File> files = this.fileChooser.showOpenMultipleDialog(this.stage);

        if(files == null || files.isEmpty()) {
            return Collections.emptyList();
        }

        this.file = files.get(0);
        this.setInitialDirectory(this.file.getParentFile());
        return files;
    }

    public boolean saveFile() {
        File file = this.fileChooser.showSaveDialog(this.stage);

        if(file == null) {
            return false;
        }

        this.file = file;
        this.setInitialDirectory(this.file.getParentFile());
        return true;
    }

    public boolean hasFile() {
        return this.file != null;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void setInitialDirectory(File path) {
        this.fileChooser.setInitialDirectory(path);
        if(this.prefs != null) {
            this.prefs.put(PREF_INITIAL_DIRECTORY, path.getAbsolutePath());
        }
    }

}
