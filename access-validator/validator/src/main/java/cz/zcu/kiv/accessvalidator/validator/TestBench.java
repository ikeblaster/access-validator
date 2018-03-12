package cz.zcu.kiv.accessvalidator.validator;

import com.healthmarketscience.jackcess.*;

import java.io.File;
import java.io.IOException;

/**
 * @author ike
 */
public class TestBench {
    public static void main(String[] args) throws IOException {

        if(true) {
            Database db = DatabaseBuilder.open(new File("data/databaze - kopie_11_mn.accdb"));

            Table table = db.getSystemTable("MSysObjects");

            for (Column column : table.getColumns()) {
                System.out.format("%-50s", column.getName());
            }

            System.out.println();

            for (Row row : table) {

                for (Column column : table.getColumns()) {
                    System.out.format("%-50.38s", row.get(column.getName()));
                }
                System.out.println();

            }

            return;
        }

        //List<File> files = Arrays.asList(new File("data/databaze.accdb"), new File("data/databaze.accdb"), new File("data/db2.accdb"), new File("data/db3.accdb"));
//
        //AccdbSimilarityChecker simchecker = new AccdbSimilarityChecker(files);
//
        //Map<SimilarityElement, Set<Accdb>> groups = simchecker.getGroups();
//
        //for(Map.Entry<SimilarityElement, Set<Accdb>> entry : groups.entrySet()) {
        //    SimilarityElement element = entry.getKey();
//
        //    System.out.println(element.getLabel() + "(" + element.getValue().toString() + ")");
//
        //    for(Accdb dbs : entry.getValue()) {
        //        System.out.println(" - " + dbs.getFile().getName());
        //    }
        //    System.out.println();
        //}

    }
}
