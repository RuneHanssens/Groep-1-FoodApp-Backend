package foodappbackend.model;

import java.io.Serializable;
import java.time.LocalDate;

public class CompositeKey implements Serializable {
    private LocalDate date;
    private long id;
}
