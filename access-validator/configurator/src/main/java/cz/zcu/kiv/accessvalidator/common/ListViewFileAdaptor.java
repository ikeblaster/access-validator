package cz.zcu.kiv.accessvalidator.common;

import java.io.File;

/**
 * @author ike
 */
public class ListViewFileAdaptor {

    private File file;
    private Boolean valid;

    public ListViewFileAdaptor(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Boolean isValid() {
        return this.valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        String ret = this.file.getName();

        if(this.valid != null) {
            ret += " " + (this.valid ? "[OK]" : "[nevalidní]");
        }

        return ret;
    }

}
