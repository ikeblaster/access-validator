package cz.zcu.kiv.accessvalidator.validator;

import com.healthmarketscience.jackcess.*;
import cz.zcu.kiv.accessvalidator.validator.rules.Rule;
import cz.zcu.kiv.accessvalidator.validator.rules.serialization.RulesSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author ike
 */
public class Validator {
    public static void main(String[] args) throws Exception {

        if(true) {
            Database db = DatabaseBuilder.open(new File("data/databaze.accdb"));

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

        if(args.length < 2) {
            System.out.println("Usage: app <rules XML file> <Access database file>");
            return;
        }

        File fileRules = new File(args[0]);
        File fileDatabase = new File(args[1]);

        RulesSerializer serializer = new RulesSerializer();
        Rule root = serializer.deserialize(new FileInputStream(fileRules));

        try {
            AccdbValidator validator = new AccdbValidator(fileDatabase);

            if (validator.validate(root)) {
                System.out.println("VALID");
            } else {
                System.out.println("INVALID");
            }

        } catch (IOException e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }


    }



}
