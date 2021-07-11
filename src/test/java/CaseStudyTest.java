import com.farhan.case_study.packer.exceptions.FileException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.farhan.case_study.Main.pack;

public class CaseStudyTest {

    @Test
    public void testDefaultInput() throws FileException {
        String result = pack(samplePath("default_input"));
        evaluate(result, "4\n-\n2,7\n8,9");
    }

    @Test
    public void testEmptyInput() throws FileException {
        String result = pack(samplePath("empty_input"));
        evaluate(result, "");
    }

    @Test(expected = FileException.class)
    public void invalidCapacity() throws Exception {
        pack(samplePath("invalid_capacity"));
    }

    @Test(expected = FileException.class)
    public void invalidCost() throws Exception {
        pack(samplePath("invalid_cost"));
    }

    @Test(expected = FileException.class)
    public void invalidIndex() throws Exception {
        pack(samplePath("invalid_index"));
    }

    @Test(expected = FileException.class)
    public void invalidWeight() throws Exception {
        pack(samplePath("invalid_weight"));
    }

    public String samplePath(String s) {
        return new File("src/test/resources/" + s).getAbsolutePath();
    }

    public void evaluate(String result, String expected) {



        Set<Set<String>> x = Stream.of(result.split("\n"))
                .map(a -> Stream.of(a.split(",")).collect(Collectors.toSet()))
                .collect(Collectors.toSet());
        Set<Set<String>> y = Stream.of(expected.split("\n"))
                .map(a -> Stream.of(a.split(",")).collect(Collectors.toSet()))
                .collect(Collectors.toSet());


        Assert.assertEquals(x, y);
    }
}
