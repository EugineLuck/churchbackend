package co.ke.emtechhouse.es.IDNO;

import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.MpesaIntergration.Utils.HelperUtility;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1/idno")
public class IDNOController {
    public  IDNOController(){

    }




    // Your authentication URL
    private static final String AUTH_URL = "https://uat.finserve.africa/authentication/api/v3/authenticate/merchant";
    private static final String CHARSET = "UTF-8";

    private String apiKey = "zqvJU2bej2BogGQ5UpymNevyPWBJszW64JLuhsal/pjJsSLWEsDNJ5+ln5idsrJodpAFN2cHuZqlWf3IXNagpw==";
    private String merchantCode = "6146647020";
    private String customerSecret = "VAbaPCD1eb6o6ECv248A8xCf7y69dG";

    private String  url = "https://uat.finserve.africa/v3-apis/v3.0/validate/identity";

    // Assuming you have a RestTemplate bean
    private RestTemplate restTemplate;


        @PostMapping("/verify")
    public ResponseEntity<Object> verifyNow(@RequestBody IDNOdto details) {
        ApiResponse responsex = new ApiResponse();
        try {
            String accessToken = getAccessToken(apiKey,merchantCode,customerSecret);
            JSONObject json = new JSONObject(accessToken);
            String accessTokenx = json.getString("accessToken");
            OkHttpClient client = new OkHttpClient();
            String requestBody = String.format("{\"identity\":{\"documentType\":\"%s\",\"firstName\":\"%s\",\"lastName\":\"%s\",\"dateOfBirth\":\"%s\",\"documentNumber\":\"%s\",\"countryCode\":\"%s\"}}",
                    "NATIONALID", details.getFirstName(), details.getLastName(), details.getDateOfBirth(), details.getDocumentNumber(), "KE");

            // Define the media type for JSON
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

            // Build the request
            Request request = new Request.Builder()
                    .url(url)
                    .post(okhttp3.RequestBody.create(requestBody, mediaType))
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", accessTokenx)
                    .build();

            // Execute the request
            Response response = client.newCall(request).execute();
            responsex.setMessage("OKay");
            responsex.setEntity(response);
            return new ResponseEntity<>(responsex, HttpStatus.OK);

        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }

    }

//    import okhttp3.*;

    public String getAccessToken(String apiKey, String merchantCode, String consumerSecret) {
        try {
            // Create an OkHttpClient
            OkHttpClient client = new OkHttpClient();

            String requestBody = String.format("{\"merchantCode\":\"%s\",\"consumerSecret\":\"%s\"}", merchantCode, consumerSecret);
//
            // Define the media type for JSON
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

            // Build the request
            Request request = new Request.Builder()
                    .url(AUTH_URL)
                    .post(okhttp3.RequestBody.create(requestBody, mediaType))
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Api-Key", apiKey)
                    .build();

            // Execute the request
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return responseBody;
            } else {
                return null;
            }
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace(); // Log the exception for debugging
            return null;
        }
    }


    public String generateSignature(String privateKeyBase64, String... components) {

        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);

            String plainText = String.join("", components);
            byte[] data = plainText.getBytes(CHARSET);

            signature.update(data);

            byte[] signedBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signedBytes);
        } catch (Exception e) {
            // Handle exceptions
        }
        return null;
    }
}




