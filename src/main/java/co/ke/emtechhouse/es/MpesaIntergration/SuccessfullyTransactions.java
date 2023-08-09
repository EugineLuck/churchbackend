package co.ke.emtechhouse.es.MpesaIntergration;

import java.util.Date;

public interface SuccessfullyTransactions {



    Long getGivingId();
    Long getTransId();

    String getFullName();

    String getTitle();
    String getLevel();
    String getAmount();
    String getMemberNumber();
    String getNumber();

    Date getPostedTime();

}
