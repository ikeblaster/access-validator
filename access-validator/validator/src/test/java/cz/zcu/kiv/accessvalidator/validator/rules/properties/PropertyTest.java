package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import cz.zcu.kiv.accessvalidator.validator.BaseTestClass;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author ike
 */
class PropertyTest extends BaseTestClass {

    private Property<String> stringProperty;

    @BeforeEach
    void setUp() {
        this.stringProperty = new Property<>(String.class, "id", "value", "name", "desc", "cat");
    }

    @Test
    void getId__EqualsToCtorPar() {
        assertEquals("id", this.stringProperty.getId());
    }

    @Test
    void getName__EqualsToCtorPar() {
        assertEquals("name", this.stringProperty.getName());
    }

    @Test
    void getDescription__EqualsToCtorPar() {
        assertEquals("desc", this.stringProperty.getDescription());
    }

    @Test
    void getCategory__EqualsToCtorPar() {
        assertEquals("cat", this.stringProperty.getCategory());
    }

    @Test
    void onChange_ValueChanged_ListenerTriggered() {
        InvalidationListener listener = mock(InvalidationListener.class);
        this.stringProperty.onChange(listener);

        this.stringProperty.setValue("newValue");

        verify(listener, times(1)).invalidated(any(Observable.class));
    }

    @Test
    void onChange_ValueSetToTheSame_ListenerNotTriggered() {
        InvalidationListener listener = mock(InvalidationListener.class);
        this.stringProperty.onChange(listener);

        this.stringProperty.setValue("value");

        verify(listener, never()).invalidated(any(Observable.class));
    }


    @ParameterizedTest
    @MethodSource("generateProperty")
    void getType__EqualsToCtorParType(Property<Object> property, Class<?> type, String id, Object value, String name, String desc, String cat) {
        assertEquals(type, property.getType());
    }

    @ParameterizedTest
    @MethodSource("generateProperty")
    void getValue__EqualsToCtorPar(Property<Object> property, Class<?> type, String id, Object value, String name, String desc, String cat) {
        assertEquals(value, property.getValue());
    }

    @ParameterizedTest
    @MethodSource("generateProperty")
    void setValue_ValueSetToTheSame_GetValueReturnsTheValue(Property<Object> property, Class<?> type, String id, Object value, String name, String desc, String cat) {
        property.setValue(value);
        assertEquals(value, property.getValue());
    }

    @ParameterizedTest
    @MethodSource("generateProperty")
    void setRawValue_ValueSetToTheSame_GetValueReturnsTheValue(Property<Object> property, Class<?> type, String id, Object value, String name, String desc, String cat) {
        property.setRawValue(value);
        assertEquals(value, property.getValue());
    }

    @ParameterizedTest
    @MethodSource("generateProperty")
    void setValue_ValueSetToNull_GetValueIsNull(Property<Object> property, Class<?> type, String id, Object value, String name, String desc, String cat) {
        property.setValue(null);
        assertNull(property.getValue());
    }

    @ParameterizedTest
    @MethodSource("generateProperty")
    void setRawValue_ValueSetToNull_GetValueIsNull(Property<Object> property, Class<?> type, String id, Object value, String name, String desc, String cat) {
        property.setRawValue(null);
        assertNull(property.getValue());
    }



    static Stream<Arguments> generateProperty() {
        return Stream.of(
                generateArgumentProperty(String.class, "id1", String.valueOf("value1"), "name1", "desc1", "cat1"),
                generateArgumentProperty(Integer.class, "id2", Integer.valueOf(1), "name2", "desc2", "cat2"),
                generateArgumentProperty(Boolean.class, "id3", Boolean.valueOf(true), "name3", "desc3", "cat3")
        );
    }

    static<T> Arguments generateArgumentProperty(Class<T> type, String id, T value, String name, String desc, String cat) {
        return Arguments.of(new Property<>(type, id, value, name, desc, cat), value.getClass(), id, value, name, desc, cat);
    }
}