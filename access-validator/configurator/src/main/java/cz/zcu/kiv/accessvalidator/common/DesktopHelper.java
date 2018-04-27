package cz.zcu.kiv.accessvalidator.common;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Helper class for opening file explorer.
 *
 * @author ike
 */
public class DesktopHelper {

    /**
     * Open file using associated application.
     *
     * @param file File to be opened.
     */
    public static void openFile(File file) {
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Open file explorer in parent directory of given file, possibly with highlighted that file.
     *
     * @param file Path to file for which the parent directory should be opened.
     */
    public static void browseParentDirectory(File file) {
        try {
            // try open file explorer on windows with highlighted file
            try {
                if (System.getProperty("os.name").startsWith("Windows")) {
                    Runtime.getRuntime().exec("explorer.exe /select, " + file.getAbsolutePath().replaceAll("/", "\\\\"));
                    return;
                }
            }
            catch (Exception e)  {
                e.printStackTrace();
            }

            // otherwise just open parent directory.
            Desktop.getDesktop().open(file.getParentFile());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
