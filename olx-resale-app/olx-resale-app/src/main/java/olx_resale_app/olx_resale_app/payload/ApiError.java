package olx_resale_app.olx_resale_app.payload;

import org.springframework.http.HttpStatus;
import java.util.*;

public class ApiError {
    private HttpStatus status;
    private int statusCodes;
    private String message;
    private List<String> subError;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public int getStatusCodes() {
        return statusCodes;
    }

    public void setStatusCodes(int statusCodes) {
        this.statusCodes = statusCodes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getSubError() {
        return subError;
    }

    public void setSubError(List<String> subError) {
        this.subError = subError;
    }
}
