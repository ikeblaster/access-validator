package cz.zcu.kiv.accessvalidator.validator.database;

import cz.zcu.kiv.accessvalidator.validator.BaseTestClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author ike
 */
class SimilarFilesTest extends BaseTestClass {

    private SimilarFiles similarFiles;
    private File file;

    @BeforeEach
    void setUp() throws URISyntaxException {
        this.file = this.getTestDBFile();
        this.similarFiles = new SimilarFiles(this.file);
    }

    @Test
    void getFile__EqualsToCtorPar() {
        assertEquals(this.file, this.similarFiles.getFile());
    }

    @Test
    void getSimilarFiles_ByDefault_IsEmpty() {
        assertTrue(this.similarFiles.getSimilarFiles().isEmpty());
    }

    @Test
    void getFileSimilarities_OtherFile_Found0() {
        File other = Mockito.mock(File.class);
        assertEquals(0, this.similarFiles.getFileSimilarities(other).size());
    }

    @Test
    void add__AddedFileIsAccessibleViaGetters() {
        File other = Mockito.mock(File.class);
        SimilarityElement similarityElement = Mockito.mock(SimilarityElement.class);

        this.similarFiles.add(other, similarityElement);

        assertEquals(1, this.similarFiles.getSimilarFiles().size());
        assertTrue(this.similarFiles.getSimilarFiles().contains(other));
        assertTrue(this.similarFiles.getFileSimilarities(other).contains(similarityElement));
    }

    @Test
    void equals_SameFile_True() {
        SimilarFiles other = new SimilarFiles(this.file);
        assertTrue(this.similarFiles.equals(other));
    }

    @Test
    void equals_SameFileDifferentSimilarFiles_True() {
        SimilarFiles other = new SimilarFiles(this.file);
        other.add(Mockito.mock(File.class), null);

        assertTrue(this.similarFiles.equals(other));
    }

    @Test
    void hashCode_SameFile_Equals() {
        SimilarFiles other = new SimilarFiles(this.file);
        assertEquals(this.similarFiles.hashCode(), other.hashCode());
    }

    @Test
    void hashCode_SameFileDifferentSimilarFiles_True() {
        SimilarFiles other = new SimilarFiles(this.file);
        other.add(Mockito.mock(File.class), null);

        assertEquals(this.similarFiles.hashCode(), other.hashCode());
    }

}