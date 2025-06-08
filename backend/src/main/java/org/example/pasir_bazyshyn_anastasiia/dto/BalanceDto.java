package org.example.pasir_bazyshyn_anastasiia.dto;


import lombok.Data;

@Data
public class BalanceDto {
    private double totalIncome;

    private double totalExpense;

    private double balance;

    public BalanceDto(double totalIncome, double totalExpense, double balance) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = balance;
    }
}
