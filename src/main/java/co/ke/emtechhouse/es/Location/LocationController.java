package co.ke.emtechhouse.es.Location;

import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Mentors.Mentors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("api/v1/location")
public class LocationController {

    Random random = new Random();

    // Generate a random number
    Long randomNumber = Long.valueOf(random.nextInt());

    @Autowired
    CountyRepo countyRepo;

    @Autowired
    SubcountyRepo subcountyRepo;

    @Autowired
    ConstituencyRepo constituencyRepo;

    @Autowired
    WardRepo wardRepo;

    @Autowired
    VillageRepo villageRepo;


    public LocationController() {

    }


    @PostMapping("/add")
    public ResponseEntity<Object> addLocation(@RequestBody LocationDTO locationDTO) {
        ApiResponse response = new ApiResponse();
        try {
            Long countyID = 0L;
            Long subcountyID =0L;
            Long constituencyID =0L;
            Long wardID = 0L;


//            Counties

            Optional<County> chckCounty = countyRepo.findByCountyName(locationDTO.getCountyName());
            if (chckCounty.isEmpty()) {
                County county = new County();
                county.setCountyName(locationDTO.getCountyName());

                County savedcounty = countyRepo.save(county);
                countyID += savedcounty.getId();
            } else {
                County savedcounty = chckCounty.get();
                countyID += savedcounty.getId();
            }

//          Subcounties

            Optional<Subcounty> chckSubcounty = subcountyRepo.findBySubcountyName(locationDTO.getSubcountyName());
            if (chckSubcounty.isEmpty()) {
                Subcounty subcounty = new Subcounty();
                subcounty.setSubcountyName(locationDTO.getSubcountyName());

                subcounty.setCountyID(countyID);
                Subcounty savedcounty = subcountyRepo.save(subcounty);
                subcountyID += savedcounty.getId();
            } else {
                Subcounty savedSubCounty = chckSubcounty.get();
                subcountyID += savedSubCounty.getId();
            }


//            Constituencies
            Optional<Constituency> chckConstituency = constituencyRepo.findByConstituencyName(locationDTO.getConstituencyName());
            if (chckConstituency.isEmpty()) {
                Constituency constituency1 = new Constituency();
                constituency1.setConstituencyName(locationDTO.getConstituencyName());
                constituency1.setSubcountyID(subcountyID);

                Constituency savedcounty = constituencyRepo.save(constituency1);
                constituencyID += savedcounty.getId();
            } else {
                Constituency savedCon = chckConstituency.get();
                constituencyID += savedCon.getId();
            }

//            Wards
            Optional<Ward> chckWard = wardRepo.findByWardName(locationDTO.getWardName());
            if (chckWard.isEmpty()) {
                Ward ward = new Ward();
                ward.setWardName(locationDTO.getWardName());
                ward.setConstituencyID(constituencyID);

                Ward savedWard = wardRepo.save(ward);
                wardID += savedWard.getId();
            } else {
                Ward savedWard = chckWard.get();
                wardID += savedWard.getId();
            }

            //            Villages
            Optional<Village> chckVillage = villageRepo.findByVillageName(locationDTO.getVillageName());
            if (chckVillage.isEmpty()) {
                Village village = new Village();
                village.setVillageName(locationDTO.getVillageName());
                village.setWardID(wardID);
                Village savedVIllage = villageRepo.save(village);
            }
            response.setMessage("Location added Successfully");
            response.setEntity(locationDTO);
            response.setStatusCode(HttpStatus.CREATED.value());
            return new ResponseEntity<>(response, HttpStatus.CREATED);


        }catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/counties/all")
    public ResponseEntity<Object> getAllCOunties() {
        ApiResponse response = new ApiResponse();
        try {

            List<County> allCounties = countyRepo.findAll();
            response.setEntity(allCounties);
            response.setMessage("All counties");
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/subcounties/all")
    public ResponseEntity<Object> getAllSubs() {
        ApiResponse response = new ApiResponse();
        try {
            List<Subcounty> allSubs = subcountyRepo.findAll();
            response.setEntity(allSubs);
            response.setMessage("All Sub counties");
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/constituencies/all")
    public ResponseEntity<Object> getAllconstituencies() {
        ApiResponse response = new ApiResponse();
        try {
            List<Constituency> allconstituencies = constituencyRepo.findAll();
            response.setEntity(allconstituencies);
            response.setMessage("All constituencies");
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/wards/all")
    public ResponseEntity<Object> getAllWards() {
        ApiResponse response = new ApiResponse();
        try {
            List<Ward> allWards = wardRepo.findAll();
            response.setEntity(allWards);
            response.setMessage("All Wards");
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @GetMapping("/village/all")
    public ResponseEntity<Object> getAllVillage() {
        ApiResponse response = new ApiResponse();
        try {
            List<Village> allVillage = villageRepo.findAll();
            response.setEntity(allVillage);
            response.setMessage("All Villages");
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }


    @GetMapping("/all")
    public ResponseEntity<Object> allLocations() {
        ApiResponse response = new ApiResponse();
        try {
            List<Locations> locs = countyRepo.findAllLocs();
            response.setEntity(locs);
            response.setMessage("All Locations");
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }






}