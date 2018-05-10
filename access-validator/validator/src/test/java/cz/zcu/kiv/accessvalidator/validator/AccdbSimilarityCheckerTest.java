package cz.zcu.kiv.accessvalidator.validator;

import cz.zcu.kiv.accessvalidator.validator.database.AccdbTest;
import cz.zcu.kiv.accessvalidator.validator.database.SimilarityElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Vojtech Kinkor
 */
class AccdbSimilarityCheckerTest extends BaseTestClass {

    private AccdbSimilarityChecker similarityChecker;
    private File file;
    private File similarFile;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        this.file = this.getTestDBFile();
        this.similarFile = Mockito.spy(this.getTestDBFile());

        this.similarityChecker = new AccdbSimilarityChecker(Arrays.asList(this.file, this.similarFile));
    }

    @Test
    void getFileSimilarities_SameFile_ContainsFile() {
        assertTrue(this.similarityChecker.getFileSimilarities().containsKey(this.file));
    }

    @Test
    void getFileSimilarities_SameFile_FoundAllExpected() {
        assertEquals(AccdbTest.FIND_SIMILARITIES_CHECKS_COUNT, this.similarityChecker.getFileSimilarities().get(this.file).size());
    }

    @Test
    void getSimilarFiles_SameFile_ContainsFile() {
        assertTrue(this.similarityChecker.getSimilarFiles().containsKey(this.file));
        assertEquals(this.similarFile, this.similarityChecker.getSimilarFiles().get(this.file).getSimilarFiles().toArray()[0]);
    }

    @Test
    void getSimilarFiles_SameFile_FoundAllExpected() {
        assertEquals(AccdbTest.FIND_SIMILARITIES_CHECKS_COUNT, this.similarityChecker.getSimilarFiles().get(this.file).getFileSimilarities(this.similarFile).size());
    }

    @Test
    void setIgnoreSimilarities__SimilarityExcluded() {
        SimilarityElement similarity = this.similarityChecker.getFileSimilarities().get(this.file).iterator().next();
        this.similarityChecker.setIgnoreSimilarities(Collections.singleton(similarity));

        assertFalse(this.similarityChecker.getFileSimilarities().get(this.file).contains(similarity));
        assertFalse(this.similarityChecker.getSimilarFiles().get(this.file).getFileSimilarities(this.similarFile).contains(similarity));
    }
}