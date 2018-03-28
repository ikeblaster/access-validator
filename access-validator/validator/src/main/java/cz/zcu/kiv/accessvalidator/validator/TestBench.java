package cz.zcu.kiv.accessvalidator.validator;

import com.healthmarketscience.jackcess.*;
import cz.zcu.kiv.accessvalidator.validator.database.SimilarFiles;
import cz.zcu.kiv.accessvalidator.validator.database.SimilarityElement;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author ike
 */
public class TestBench {
    public static void main(String[] args) throws IOException {

        if(false) {


            {
                Database db = DatabaseBuilder.open(new File("data/testdb.accdb"));

                Table table = db.getSystemTable("MSysObjects");

                for (Column column : table.getColumns()) {
                    System.out.format("%-50s", column.getName());
                }

                System.out.println();

                for (Row row : table) {
                    for (Column column : table.getColumns()) {
                        Object o = row.get(column.getName());
                        if ((column.getName().equals("LvProp") || column.getName().equals("LvExtra")) && o != null) {
                            String col = column.getName();
                            System.out.format("");
                        }
                        System.out.format("%-50.38s", o);

                    }
                    System.out.println();

                }
            }
//            {
//                Database db = DatabaseBuilder.open(new File("data/db3.accdb"));
//
//                Table table = db.getSystemTable("MSysObjects");
//
//                for (Row row : table) {
//                    if (row.getString("Name").equals("Admin")) {
//                        found2.add(Arrays.asList(row.getBytes("LvExtra")));
//                    }
//                }
//            }
//
//            System.out.println(found1.get(0).equals(found2.get(0)));

        }
        else {

            List<File> files = Arrays.asList(new File("data/databaze.accdb"), new File("data/databaze - kopie_11_mn.accdb"), new File("data/db2.accdb"), new File("data/db3.accdb"));

            AccdbSimilarityChecker simchecker = new AccdbSimilarityChecker(files);

            Map<File, SimilarFiles> sims = simchecker.getSimilarFiles();
            for (SimilarFiles similarFiles : sims.values()) {

                System.out.println(">>>>> " + similarFiles.getFile().getName() + " <<<<<");

                for (File similar : similarFiles.getSimilarFiles()) {

                    System.out.println("  - " + similar);
                    for (SimilarityElement similarityElement : similarFiles.getFileSimilarities(similar)) {
                        System.out.println("    " + similarityElement.toString());
                    }
                }

            }

            if(true) return;

            System.out.println("\n\n\n");

            Map<File, Collection<SimilarityElement>> groups = simchecker.getFileSimilarities();

            for (Map.Entry<File, Collection<SimilarityElement>> entry : groups.entrySet()) {
                File element = entry.getKey();
                Collection<SimilarityElement> similars = entry.getValue();

                System.out.println(">>>>> " + element.getName() + " <<<<<");

                for (SimilarityElement similar : similars) {
                    System.out.println("  " + similar.toString());

                    for (File dbs : similar.getFiles()) {
                        System.out.println("   - " + dbs.getName());
                    }
                }

                System.out.println();
            }
        }

    }
}
