package co.ke.emtechhouse.es.MpesaIntergration.Services;


import co.ke.emtechhouse.es.MpesaIntergration.B2C_Transaction.B2CTransactionSyncResponse;
import co.ke.emtechhouse.es.MpesaIntergration.B2C_Transaction.InternalB2CTransactionRequest;
import co.ke.emtechhouse.es.MpesaIntergration.C2B_Transaction.C2BRequest;
import co.ke.emtechhouse.es.MpesaIntergration.C2B_Transaction.C2BResponse;
import co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express.InternalStkPushRequest;
import co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express.InternalStkPushStatusRequest;
import co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express.StkPushStatusResponse;
import co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express.StkPushSyncResponse;
import co.ke.emtechhouse.es.MpesaIntergration.OAUTH_Token.AccessTokenResponse;
import co.ke.emtechhouse.es.MpesaIntergration.Register_URL.RegisterUrlResponse;

public interface DarajaApi {
    AccessTokenResponse getAccessToken();
    RegisterUrlResponse registerUrl();
    C2BResponse C2BTransaction(C2BRequest c2BRequest);
    B2CTransactionSyncResponse b2CTransaction(InternalB2CTransactionRequest internalB2CTransactionRequest);
    StkPushSyncResponse stkPushTransaction(InternalStkPushRequest internalStkPushRequest);
    StkPushStatusResponse stkPushStatus(InternalStkPushStatusRequest internalStkPushStatusRequest);
}
