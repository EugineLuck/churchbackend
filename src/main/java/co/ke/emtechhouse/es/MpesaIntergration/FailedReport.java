package co.ke.emtechhouse.es.MpesaIntergration;

import java.util.Date;

public interface FailedReport {





    Long getGivingId();
    Long getTransId();

    String getFullName();
    String getNumber();

    String getTitle();
    String getLevel();
    String getAmount();
    String getMemberNumber();

    Date getPostedTime();
}
