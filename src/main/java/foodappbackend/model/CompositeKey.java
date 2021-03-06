package foodappbackend.model;

import foodappbackend.user.ApplicationUser;

import java.io.Serializable;
import java.time.LocalDate;

public class CompositeKey implements Serializable {
    public LocalDate date;
    public ApplicationUser user;
}
