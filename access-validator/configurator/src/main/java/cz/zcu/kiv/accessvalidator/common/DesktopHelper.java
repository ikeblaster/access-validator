package cz.zcu.kiv.accessvalidator.common;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author ike
 */
public class DesktopHelper {

    public static void openFile(File file) {
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void browseParentDirectory(File file) {
        try {
            try {
                if (System.getProperty("os.name").startsWith("Windows")) {
                    Runtime.getRuntime().exec("explorer.exe /select, " + file.getAbsolutePath().replaceAll("/", "\\\\"));
                    return;
                }
            }
            catch (Exception e)  {
                e.printStackTrace();
            }

            Desktop.getDesktop().open(file.getParentFile());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
