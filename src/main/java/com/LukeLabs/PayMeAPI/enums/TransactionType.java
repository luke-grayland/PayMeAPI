package com.LukeLabs.PayMeAPI.enums;

public enum TransactionType {
    SPEND("Spend", 0) { @Override  public int sign() { return Sign.Debit.value; } },
    REFUND("Refund", 1) { @Override public int sign() { return Sign.Credit.value; } },
    REVERSAL("Reversal", 2) { @Override public int sign() { return Sign.Credit.value; } },
    CHARGEBACK("Chargeback", 3) { @Override public int sign() { return Sign.Credit.value; } },
    ACCOUNT_TRANSFER("Account Transfer", 4) { @Override public int sign() { return Sign.Debit.value; } },
    UNKNOWN("Unknown", 5) { @Override public int sign() { return Sign.Debit.value; } };

    public final String label;
    public final int code;

    TransactionType(String label, int code) {
        this.label = label;
        this.code = code;
    }

    @Override
    public String toString() { return label; }

    public abstract int sign();

    private static final int Debit = 1;
    private static final int Credit = -1;

    enum Sign {
        Debit(1),
        Credit(-1);

        public final int value;

        Sign(int sign) { this.value = sign; }
    }
}
