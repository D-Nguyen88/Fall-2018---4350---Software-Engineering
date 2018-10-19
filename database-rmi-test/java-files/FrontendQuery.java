import java.io.Serializable;

public class FrontendQuery implements Serializable {
    private String queryFieldName,
            queryValue;

    public FrontendQuery() {

    }

    public FrontendQuery(String queryFieldName, String queryValue) {
        this.queryFieldName = queryFieldName;
        this.queryValue = queryValue;
    }

    public String getQueryFieldName() {
        return queryFieldName;
    }

    public void setQueryFieldName(String queryFieldName) {
        this.queryFieldName = queryFieldName;
    }

    public String getQueryValue() {
        return queryValue;
    }

    public void setQueryValue(String queryValue) {
        this.queryValue = queryValue;
    }
}