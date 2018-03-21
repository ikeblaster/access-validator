package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbTableRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ChoiceProperty;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ComparisonOperator;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;

/**
 * @author ike
 */
public class AllTablesHaveRowsRule extends Rule {

    private ChoiceProperty<ComparisonOperator> countOp;
    private Property<Integer> count;

    public AllTablesHaveRowsRule() {
        super();

        this.countOp = this.addProperty(new ChoiceProperty<>(
                ComparisonOperator.class,
                "count_op", ComparisonOperator.GTE, ComparisonOperator.getChoices(),
                "Počet řádků", "Operátor pro ověření počtu řádků v tabulkách", this.getGenericLabel()
        ));
        this.count = this.addProperty(new Property<>(
                Integer.class,
                "count", Integer.valueOf(1),
                "...", "Všechny tabulky v databázi musí obsahovat zadaný počet řádků", this.getGenericLabel()
        ));

    }

    @Override
    public boolean check(Accdb accdb) {
        AccdbTableRepository repository = accdb.getTableRepository();

        int tablesInDb = repository.getTables().size();
        repository.filterByRowsCount(this.count.getValue(), this.countOp.getValue());

        return repository.getTables().size() == tablesInDb;
    }

    @Override
    public String getGenericLabel() {
        return "Počet řádků v každé tabulce";
    }

    @Override
    public String toString() {
        return "Počet řádků v každé tabulce " + this.countOp.getValue() + " " + this.count.getValue();
    }

}
