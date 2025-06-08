package org.example.pasir_bazyshyn_anastasiia.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nazwa użytkownika jest wymagana")
    private String username;

    @Email(message = "POdaj poprawny adres e-mail")
    @NotBlank(message = "Emaill jest wymagany")
    private String email;

    @NotBlank(message = "haso nie może być puste")
    private String password;

    private String currency = "PLN";
}
