package gin.featuresjson;

public class ResultSummary {
    public int Total;
    public int Passing;
    public int Failing;
    public int Inconclusive;

    public static ResultSummary create(int total, int passing, int failing, int inconclusive) {
        ResultSummary pResultSummary = new ResultSummary();
        pResultSummary.Total = total;
        pResultSummary.Passing = passing;
        pResultSummary.Failing = failing;
        pResultSummary.Inconclusive = inconclusive;
        return pResultSummary;
    }
}
