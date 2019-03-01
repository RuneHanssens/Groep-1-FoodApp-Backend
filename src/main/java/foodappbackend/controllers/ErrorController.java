package foodappbackend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorController {
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String renderErrorPage(HttpServletRequest httpRequest) {
        switch (getErrorCode(httpRequest)) {
            case 400:
                return "Bad Request";
            case 401:
                return "Unauthorized Access";
            case 404:
                return "Page not found";
            case 500:
                return "Internal server error";
            default:
                return "I honestly have no clue what went wrong ¯\\_(ツ)_/¯";
        }
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }
}
