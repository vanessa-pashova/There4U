package com.example.there4u.model.user;

import com.example.there4u.model.badge.Badge;
import com.example.there4u.service.geo.OSMBatchAddressValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Abstract class representing a generic user in the system.
 * Provides common fields and validation logic for all user types.
 * This class is intended to be extended by specific user roles.
 *
 * Fields:
 * - name: User's full name, formatted with capitalization and validated for alphabetic characters only.
 * - email: User's email address, validated for standard email format.
 * - password: Must be at least 8 characters, including at least one uppercase letter, one lowercase letter, and one digit.
 * - phone: User's phone number, validated using custom logic.
 * - address: User's address, validated through OpenStreetMap API or equivalent validator.
 * - typeOfUser: Enum defining the type of user (e.g., anonymous, registered, moderator, etc.).
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_table")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @Column(name = "id")
    protected long id;

    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{4,19}$", message = "!> Invalid username. Username must contain only letters, numbers or underscores. And must be 4-19 characters long.")
    @Column(name = "username")
    protected String username;

    @Pattern(regexp = "^[A-Z][a-z]+( [A-Z][a-z]+)*$", message = "!> Invalid name")
    @Column(name = "name")
    protected String name;

    @Pattern(
            regexp = "^[\\w.%+-]+@[\\w.-]+\\.(com|bg|net|org)$",
            message = "!> Invalid email domain. Allowed domains are: .com, .bg, .net, .org"
    )
    @Column(name = "email")
    protected String email;


    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "!> Invalid password. Password must contain at least 8 symbols from which at least one uppercase letter, at least one lowercase letter, and at least one number")
    @Column(name = "password")
    protected String password;

    @Pattern(regexp = "^(?:(?:\\+359|00359|0)8[7-9][0-9]{7}|2[0-9]{7})$", message = "!> Invalid phone")
    @Column(name = "phone")
    protected String phone;

    @Column(name = "address")
    protected String address;

    @Enumerated(EnumType.STRING)
    @Setter
    @Column(name = "type")
    protected TypeOfUser typeOfUser;

    @ManyToMany
    @JoinTable(
            name = "user_badge",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "badge_id")
    )
    protected Set<Badge> badges = new HashSet<>();

    public User(String username, String name, String email, String password, String phone, String address) {
        this.id = generateId();
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.setAddress(address);
    }

    public void setAddress(String address) {
        if(!OSMBatchAddressValidator.isValidAddress(address)) {
            throw new IllegalArgumentException(">! Invalid address [setAddress(), User]");
        }

        this.address = address;
    }

    protected abstract long generateId();
}
