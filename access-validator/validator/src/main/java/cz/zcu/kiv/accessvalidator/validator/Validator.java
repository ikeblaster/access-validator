package cz.zcu.kiv.accessvalidator.validator;

import cz.zcu.kiv.accessvalidator.validator.rules.Rule;
import cz.zcu.kiv.accessvalidator.validator.rules.serialization.RulesSerializer;

import java.io.File;
import java.io.FileInputStream;

/**
 * Entry point for console based application for validating
 * a single database against a XML file with serialized rule(s).
 *
 * @author ike
 */
public class Validator {

    /**
     * Entry point for console based application for validating
     * a single database against a XML file with serialized rule(s).
     *
     * <p>Expects a XML file with rules as first argument
     * and ACCDB database file as second argument.
     *
     * <p>Prints {@code VALID} when database is successfully validated against the rules.<br>
     * Prints {@CODE INVALID} and possible errors into {@code System.err} otherwise.
     *
     * @param args Application arguments.
     * @throws Exception
     */
    public static void main(String[] args) {

        if(args.length < 2) {
            System.out.println("Usage: validator <XML rules file> <ACCDB database file>");
            return;
        }

        try {
            File fileRules = new File(args[0]);
            File fileDatabase = new File(args[1]);

            RulesSerializer serializer = new RulesSerializer();
            Rule root = serializer.deserialize(new FileInputStream(fileRules));

            AccdbValidator validator = new AccdbValidator(fileDatabase, root);

            if (validator.validate()) {
                System.out.println("VALID");
            } else {
                System.err.println("INVALID");
                System.err.println();
                for(Rule rule : validator.getFailedRules()) {
                    System.err.println("Databáze nesplňuje pravidlo: " + fixUnicode(rule.toString()));
                }
                System.exit(100);
            }

        } catch (Exception e) {
            System.err.println("ERROR");
            e.printStackTrace();
            System.exit(101);
        }

    }

    /**
     * Helper method for fixing unicode symbols to be written into console output.
     *
     * @param str Input string.
     * @return Fixed string.
     */
    private static String fixUnicode(String str) {
        str = str.replaceAll("≤", "<=");
        str = str.replaceAll("≥", ">=");
        return str;
    }
}
