package cz.zcu.kiv.accessvalidator.common;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * @author ike
 */
public class FileChooserEx {

    private FileChooser fileChooser = new FileChooser();
    private Stage stage;
    private File file;

    public FileChooserEx() {
    }

    public ObservableList<FileChooser.ExtensionFilter> getExtensionFilters() {
        return this.fileChooser.getExtensionFilters();
    }

    public boolean openFile() {
        File file = this.fileChooser.showOpenDialog(this.stage);

        if(file == null || !file.canRead() || !file.canWrite()) {
            return false;
        }

        this.fileChooser.setInitialDirectory(file.getParentFile());
        this.file = file;
        return true;
    }

    public List<File> openMultipleFiles() {
        List<File> files = this.fileChooser.showOpenMultipleDialog(this.stage);

        if(files == null || files.isEmpty()) {
            return Collections.emptyList();
        }

        this.file = files.get(0);
        this.fileChooser.setInitialDirectory(this.file.getParentFile());
        return files;
    }

    public boolean saveFile() {
        File file = this.fileChooser.showSaveDialog(this.stage);

        if(file == null) {
            return false;
        }

        this.file = file;
        this.fileChooser.setInitialDirectory(this.file.getParentFile());
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
}
