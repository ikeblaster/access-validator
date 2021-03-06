package cz.zcu.kiv.accessvalidator.validator.database;

import cz.zcu.kiv.accessvalidator.validator.BaseTestClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Vojtech Kinkor
 */
class SimilarityElementTest extends BaseTestClass {

    private SimilarityElement similarityElement;

    @BeforeEach
    void setUp() {
        this.similarityElement = new SimilarityElement("label", "value");
    }


    @Test
    void newInstance_CtorWithInvalidParameter_ExceptionThrown() {
        assertThrows(Exception.class, () -> new SimilarityElement("", "", 101));
        assertThrows(Exception.class, () -> new SimilarityElement("", "", -1));
    }

    @Test
    void getLabel__EqualsToCtorPar() {
        assertEquals("label", this.similarityElement.getLabel());
    }

    @Test
    void getValue__EqualsToCtorPar() {
        assertEquals("value", this.similarityElement.getValue());
    }

    @Test
    void getFiles_ByDefault_IsEmpty() {
        assertTrue(this.similarityElement.getFiles().isEmpty());
    }

    @Test
    void add_File_ReturnedInGetFiles() {
        File file = Mockito.mock(File.class);
        this.similarityElement.add(file);

        assertEquals(1, this.similarityElement.getFiles().size());
        assertTrue(this.similarityElement.getFiles().contains(file));
    }

    @Test
    void getSeverity_ByDefault_1() {
        assertEquals(1, this.similarityElement.getSeverity());
    }

    @Test
    void toString__ContainsLabel() {
        assertTrue(this.similarityElement.toString().contains("label"));
    }

    @Test
    void equals_SameFile_True() {
        SimilarityElement other = new SimilarityElement("label", "value");
        assertTrue(this.similarityElement.equals(other));
    }

    @Test
    void equals_SameFileDifferentSimilarFiles_True() {
        SimilarityElement other = new SimilarityElement("label", "value");
        File file = Mockito.mock(File.class);
        this.similarityElement.add(file);

        assertTrue(this.similarityElement.equals(other));
    }

    @Test
    void hashCode_SameFile_Equals() {
        SimilarityElement other = new SimilarityElement("label", "value");
        assertEquals(this.similarityElement.hashCode(), other.hashCode());
    }

    @Test
    void hashCode_SameFileDifferentSimilarFiles_True() {
        SimilarityElement other = new SimilarityElement("label", "value");
        File file = Mockito.mock(File.class);
        this.similarityElement.add(file);

        assertEquals(this.similarityElement.hashCode(), other.hashCode());
    }
}