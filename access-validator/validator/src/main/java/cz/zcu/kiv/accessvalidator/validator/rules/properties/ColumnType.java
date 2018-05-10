package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.DataType;

import java.util.Arrays;
import java.util.List;

/**
 * Represents type of database table column.
 *
 * @author Vojtech Kinkor
 */
public enum ColumnType {
    _ANY(""),
    AUTO_NUMBER("Aut. číslo"),
    NUMBER("Číslo"),
    TEXT("Text"),
    DATETIME("Datum a čas"),
    CURRENCY("Měna"),
    YESNO("Ano/Ne"),
    LINK("Odkaz");

    /**
     * Label for column type.
     */
    private String label;

    /**
     * Represents type of database table column.
     *
     * @param label Label for column type.
     */
    ColumnType(String label) {
        this.label = label;
    }

    /**
     * Gets label for column type.
     *
     * @return Label for column type.
     */
    @Override
    public String toString() {
        return this.label;
    }

    /**
     * Gets all column types as collection.
     *
     * @return Collection of column types.
     */
    public static List<ColumnType> getChoices() {
        return Arrays.asList(values());
    }

    /**
     * Compares with database column.
     *
     * @param column Database column.
     * @return {@code true} when equal, {@code} false otherwise.
     */
    public boolean compare(Column column) {
        DataType type = column.getType();

        if(this == AUTO_NUMBER) {
            return column.isAutoNumber();
        }
        if(this == LINK) {
            return column.isHyperlink();
        }
        if(this == NUMBER) {
            return !column.isAutoNumber() &&
                    (type == DataType.NUMERIC
                    || type == DataType.INT || type == DataType.BIG_INT || type == DataType.LONG || type == DataType.COMPLEX_TYPE
                    || type == DataType.BINARY
                    || type == DataType.DOUBLE || type == DataType.FLOAT);
        }
        if(this == TEXT) {
            return type.isTextual();
        }
        if(this == DATETIME) {
            return type == DataType.SHORT_DATE_TIME;
        }
        if(this == CURRENCY) {
            return type == DataType.MONEY;
        }
        if(this == YESNO) {
            return type == DataType.BOOLEAN;
        }

        return true;
    }

}