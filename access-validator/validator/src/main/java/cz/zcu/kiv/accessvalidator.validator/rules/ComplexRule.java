package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.rules.properties.ChoiceProperty;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;

import java.util.Arrays;
import java.util.List;

/**
 * @author ike
 */
public class ComplexRule extends Rule {

    public enum ComparisonOperator {
        GTE("=>"), EQ("="), LTE("<=");

        private String label;

        ComparisonOperator(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return this.label;
        }

        public static List<Object> asList() {
            return Arrays.asList((Object[]) values());
        }

    }


    public ComplexRule() {
        super();

        this.properties.add(new ChoiceProperty("tables_count_op", ComparisonOperator.GTE, ComparisonOperator.asList(), "Počet tabulek", "Operátor pro ověření počtu tabulek", "1. Počet tabulek"));
        this.properties.add(new Property("tables_count", Integer.valueOf(1), "...", "Počet hledaných tabulek", "1. Počet tabulek"));

        this.properties.add(new Property("tables_byname", "", "Název tabulky", "Název tabulky", "2. Filtrování dle názvu tabulky"));

        this.properties.add(new ChoiceProperty("columns_count_op", ComparisonOperator.GTE, ComparisonOperator.asList(), "Počet sloupců v tabulce", "Operátor pro ověření počtu tabulek", "3. Počet sloupců"));
        this.properties.add(new Property("columns_count", Integer.valueOf(1), "...", "Alespoň jedna nalezená tabulka má zadaný počet sloupců", "3. Počet sloupců"));
        this.properties.add(new Property("columns_byname", "", "Název sloupce", "Existuje tabulka se sloupcem dle zadaného názvu.", "4. Existence sloupce"));
        this.properties.add(new Property("columns_bytype", "", "Datový typ sloupce", "Existuje tabulka se sloupcem dle zadaného datového typu.", "4. Existence sloupce"));
    }

    @Override
    public String toString() {
        return "Komplexní kontrola";
    }

}
