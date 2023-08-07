package co.ke.emtechhouse.es.HelpComponent.ContactInfo;

import co.ke.emtechhouse.es.Auth.utils.HttpInterceptor.UserRequestContext;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ContactService {
    @Autowired
    ContactRepo contactRepo;
    public ApiResponse getContactInfo() {
        ApiResponse response = new ApiResponse();
        List<Contact> contactInfoList = contactRepo.findAll();
        if (UserRequestContext.getCurrentUser().isEmpty()) {
            response.setMessage("User Name not present in the Request Header");
            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
        } else {

            if (contactInfoList.size() > 0) {
                response.setEntity(contactInfoList);
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }

        }
        return response;
    }

    ;

    public ApiResponse addContactInfo(Contact contactInfo) {
        ApiResponse response = new ApiResponse();
        List<Contact> contactInfoList = contactRepo.findAll(); //fetching contact info if it exists
        if (UserRequestContext.getCurrentUser().isEmpty()) {
            response.setMessage("User Name not present in the Request Header");
            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
            return response;
        } else {
            if (contactInfoList.size() < 1) { //proceed to add contactInfo if it does not exist.
                Contact contactEntity = new Contact(); //create new contact entity


                contactEntity.setEmail(contactInfo.getEmail());
                contactEntity.setPhoneNumber(contactInfo.getPhoneNumber());
                contactEntity.setFacebook(contactInfo.getFacebook());
                contactEntity.setTwitter(contactInfo.getTwitter());
                contactEntity.setInstagram(contactInfo.getInstagram());
                contactEntity.setWebsite(contactInfo.getWebsite());
                Contact addcontactInfo=contactRepo.save(contactEntity);
                response.setMessage(HttpStatus.CREATED.getReasonPhrase());
                response.setStatusCode(HttpStatus.CREATED.value());
                response.setEntity(addcontactInfo);

            } else {

                response.setMessage("Contact information already exists");
                response.setMessage(HttpStatus.FORBIDDEN.getReasonPhrase());
                response.setStatusCode(HttpStatus.FORBIDDEN.value());
                response.setEntity(contactInfoList);
                //updateContactInfo(contactInfo);
            }
        }

        return response;
    }

    public ApiResponse updateContactInfo(Contact newContactInfo) {
        ApiResponse response = new ApiResponse();
        //fetching contact info if it exists from db
        Optional<Contact> optionalContactInfo=contactRepo.findByEmail(newContactInfo.getEmail());
        if (UserRequestContext.getCurrentUser().isEmpty()) {
            response.setMessage("User Name not present in the Request Header");
            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
        } else {
            if (optionalContactInfo.isPresent()) {
                Contact contactInfoList=optionalContactInfo.get();
                contactInfoList.setPhoneNumber(newContactInfo.getPhoneNumber());
                contactInfoList.setEmail(newContactInfo.getEmail());
                contactInfoList.setFacebook(newContactInfo.getFacebook());
                contactInfoList.setInstagram(newContactInfo.getInstagram());
                contactInfoList.setTwitter(newContactInfo.getTwitter());
                contactInfoList.setWebsite(newContactInfo.getWebsite());
                Contact updatedContactInfo=contactRepo.save(contactInfoList);
                response.setMessage(HttpStatus.CREATED.getReasonPhrase());
                response.setStatusCode(HttpStatus.CREATED.value());
                response.setMessage("Contact information has been updated");

                response.setEntity(updatedContactInfo);


            } else {
                response.setMessage("Contact update non-existing contact information.");
                response.setMessage(HttpStatus.FORBIDDEN.getReasonPhrase());
                response.setStatusCode(HttpStatus.FORBIDDEN.value());
            }

        }

        return response;
    }

    public ApiResponse deleteContactInfo() {
        ApiResponse response = new ApiResponse();
        List<Contact> contactInfoList = contactRepo.findAll(); //fetching contact info if it exists

        if (UserRequestContext.getCurrentUser().isEmpty()) {
            response.setMessage("User Name not present in the Request Header");
            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
        } else {
            if (contactInfoList.size() > 0) {
                contactRepo.deleteAllInBatch();
                response.setMessage("Contact information has been deleted");
                response.setStatusCode(HttpStatus.OK.value());

            } else {
                response.setMessage("Contact information does not exist");
            }

        }

        return response;
    }
}