package foodappbackend.model;

import java.io.Serializable;
import java.time.LocalDate;

public class CompositeKey implements Serializable {
    public LocalDate date;
    public String username;
}
