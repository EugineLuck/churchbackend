package co.ke.emtechhouse.es.Advertisement;



import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AdsService {

    @Autowired
    private AdsRepo AdsRepo;

    public  Advertisement saveAdvert(Advertisement advert){
        try {
            Advertisement saveAdvertisement = AdsRepo.save(advert);
            return saveAdvertisement;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }


    public List<Advertisement> getAll() {
        try {
            List<Advertisement> adverts= AdsRepo.findAll();
            return adverts;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }

    public Advertisement findById(Long id) {
        try {
            Advertisement savedAd= AdsRepo.findById(id).get();
            return savedAd;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }

//Update

    public ApiResponse<Advertisement> updateAd(Advertisement advert, Long Id) {
        try {
            ApiResponse response = new ApiResponse<>();
            Optional<Advertisement> ad = AdsRepo.findById(Id);
            if (ad.isPresent()) {

                Advertisement existingAd = ad.get();
                existingAd.setCharges(advert.getCharges());
                existingAd.setBusinessName(advert.getBusinessName());
                existingAd.setBusinessDescription(advert.getBusinessDescription());

                Advertisement SavedAd = AdsRepo.save(existingAd);
                response.setMessage("Advertisement  updated successfully");
                response.setStatusCode(HttpStatus.OK.value());
                response.setEntity(SavedAd);
                return response;
            } else {
                response.setMessage("Advertisement Not found");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }


            return response;
        } catch (Exception e) {
            log.info("Catched Error {}" + e);
            return null;
        }

    }








//    Delete
public void  delete(Long id) {
    try {
        AdsRepo.deleteById(id);
    }catch (Exception e) {
        log.info("Catched Error {} " + e);
    }

}









}
