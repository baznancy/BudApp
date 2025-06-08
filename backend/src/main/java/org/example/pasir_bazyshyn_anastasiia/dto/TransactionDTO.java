package org.example.pasir_bazyshyn_anastasiia.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.pasir_bazyshyn_anastasiia.model.TransactionType;
import org.example.pasir_bazyshyn_anastasiia.model.Transaction;


@Setter
@Getter
public class TransactionDTO {


    @NotNull(message = "Kwota nie moze byc pusta")
    @Min(value = 1, message = "Kwota musi byc wieksza od 0")
    private Double amount;


    @NotNull(message = "Typ transakcji jest wymagany")
    @Pattern(regexp = "INCOME|EXPENSE", message = "Typ musi byc INCOME lub EXPENSE")
    private String type;


    @Size(max = 50, message = "Tagi nie moga przekraczac 50 znaków")
    private String tags;


    @Size(max = 255, message = "Notatka nie moze przekroczyc 255 znaków")
    private String notes;
}
