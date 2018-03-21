package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.DataType;

import java.util.Arrays;
import java.util.List;

public enum ColumnType {
    _ANY(""),
    AUTO_NUMBER("Aut. číslo"),
    NUMBER("Číslo"),
    TEXT("Text"),
    DATETIME("Datum a čas"),
    CURRENCY("Měna"),
    YESNO("Ano/Ne"),
    LINK("Odkaz");

    private String label;

    ColumnType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }

    public static List<ColumnType> getChoices() {
        return Arrays.asList(values());
    }

    public boolean compare(Column column) {
        DataType type = column.getType();

        if(this == AUTO_NUMBER) {
            return column.isAutoNumber() && type.mayBeAutoNumber();
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

        return false;
    }

}