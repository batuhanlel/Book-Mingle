package com.lel.bookmingle.model;

import com.lel.bookmingle.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = "email")

)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 7836560715022247050L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotBlank(message = "Name Can Not be Empty")
    private String name;

    @Column
    @NotBlank(message = "Surname Can Not be Empty")
    private String surname;

    @Column
//    @NotBlank(message = "Birth Date Can Not be Empty")
//    @Pattern(regexp="^[0-9]{2}/[0-9]{2}/[0-9]{4}", message = "Enter dd/MM/yyyy date!")
    private String birthDate;

    @Column
    @NotBlank(message = "Phone number can not be blank!")
    @Size(min = 10, max = 10, message = "Please enter 10 digits!")
    @Pattern(regexp = "^[0-9]{10}", message = "Number Must Contain Only Digits!")
    private String phoneNumber;

    @Column
    @NotBlank(message = "Email address can not be blank!")
    @Email(message = "Please enter valid email address!")
    private String email;

    @Column
    @NotBlank(message = "Password Can Not Be Empty")
    private String password;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_book",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")})
    private List<Book> books = new ArrayList<>();

    @OneToMany(mappedBy = "requestedUser")
    private List<BookExchange> requestedBookExchanges = new ArrayList<>();

    @OneToMany(mappedBy = "requesterUser")
    private List<BookExchange> proposedBookExchanges = new ArrayList<>();

    @OneToMany(mappedBy = "user1")
    private List<Chat> chats1 = new ArrayList<>();

    @OneToMany(mappedBy = "user2")
    private List<Chat> chats2 = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    private List<Message> receivedMessages = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
