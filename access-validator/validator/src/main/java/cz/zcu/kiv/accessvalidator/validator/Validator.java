package cz.zcu.kiv.accessvalidator.validator;

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
                System.err.println("INVALID");
                System.exit(100);
            }

        } catch (IOException e) {
            System.err.println("ERROR");
            e.printStackTrace();
            System.exit(101);
        }


    }



}
