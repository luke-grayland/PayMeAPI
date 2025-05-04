package com.LukeLabs.PayMeAPI.constants;

public class TransactionFileDefinition {
  private TransactionFileDefinition() {}

  public static final Integer TransactionId_Start = 0;
  public static final Integer TransactionId_End = 36;

  public static final Integer TransactionType_Start = 37;
  public static final Integer TransactionType_End = 39;

  public static final Integer Amount_Start = 40;
  public static final Integer Amount_End = 53;

  public static final Integer Currency_Start = 54;
  public static final Integer Currency_End = 58;

  public static final Integer MerchantName_Start = 59;
  public static final Integer MerchantName_End = 95;

  public static final Integer Date_Start = 96;
  public static final Integer Date_End = 106;

  public static final Integer Time_Start = 107;
  public static final Integer Time_End = 113;

  public static final Integer CardId_Start = 114;
  public static final Integer CardId_End = 150;
}
