package sadad.com.jibonomy.biz.dto;

public class Result {

    private String error;
    private Response response;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
