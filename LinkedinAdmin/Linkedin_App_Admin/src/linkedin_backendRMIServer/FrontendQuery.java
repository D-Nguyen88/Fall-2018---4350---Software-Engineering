package linkedin_backendRMIServer;

import java.io.Serializable;

public class FrontendQuery implements Serializable {

    private String firstName, lastName, industry, position, state;

    public FrontendQuery() {

    }

    public FrontendQuery(String firstName, String lastName, String industry, String position, String state) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.industry = industry;
        this.position = position;
        this.state = state;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
