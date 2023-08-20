package co.ke.emtechhouse.es.PaymentsMethods;


import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PaymentsMethodsService {
    @Autowired
    PaymentsMethodRepository paymentsMethodRepository;

    public PaymentsMethods saveMethod(PaymentsMethods pmethod){
        try {
            PaymentsMethods save = paymentsMethodRepository.save(pmethod);
            return save;
        }catch (Exception e) {
            System.out.println("Catched Error {} " + e);
            return null;
        }
    }

    public List<PaymentsMethods> getAll() {
        try {
            List<PaymentsMethods> pMethods = paymentsMethodRepository.findAll();
            return pMethods;
        }catch (Exception e) {
            System.out.println("Catched Error {} " + e);
            return null;
        }

    }

    public PaymentsMethods findById(Long id) {
        try {
            PaymentsMethods savedMethod = paymentsMethodRepository.findById(id).get();
            return savedMethod;
        }catch (Exception e) {
            System.out.println("Catched Error {} " + e);
            return null;
        }

    }

//    Function to update
    public PaymentsMethods updateMethod(Long id, PaymentsMethods paymentsMethod) {
        try {
            PaymentsMethods savedMethod = paymentsMethodRepository.findById(id).get();
            savedMethod.setMethodName(paymentsMethod.getMethodName());
            PaymentsMethods save = paymentsMethodRepository.save(savedMethod);
            return save;
        }catch (Exception e) {
            System.out.println("Catched Error {} " + e);
            return null;
        }

    }


    public void  delete(Long id) {
        try {
            paymentsMethodRepository.deleteById(id);
        }catch (Exception e) {
            System.out.println("Catched Error {} " + e);
        }

    }





}
