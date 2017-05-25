package Test;

/**
 * Created by oradchykova on 5/25/17.
 */
public class TestingSample {
    private final Double expectedResult;
    private final Boolean actualResult;

    public TestingSample(Boolean actual, Double expected) {
        this.expectedResult = expected;
        this.actualResult = actual;
    }

    public Double getExpectedResult() {
        return expectedResult;
    }

    public Boolean getActualResult() {
        return actualResult;
    }
}
