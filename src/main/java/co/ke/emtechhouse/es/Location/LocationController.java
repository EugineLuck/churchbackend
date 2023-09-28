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
            Long countyID = locationDTO.getCountyID();
            Long subcountyID = locationDTO.getSubcountyID();
            Long constituencyID = locationDTO.getConstituencyID();
            Long wardID = locationDTO.getWardID();


//            Counties

            Optional<County> chckCounty = countyRepo.findByCountyName(locationDTO.getCountyName());
            if (chckCounty.isEmpty()) {
                County county = new County();
                county.setCountyName(locationDTO.getCountyName());
                county.setCountyID(locationDTO.getCountyID());
                County savedcounty = countyRepo.save(county);
                countyID = savedcounty.getCountyID();
            } else {
                County savedcounty = chckCounty.get();
                countyID = savedcounty.getCountyID();
            }

//          Subcounties

            Optional<Subcounty> chckSubcounty = subcountyRepo.findBySubcountyName(locationDTO.getSubcountyName());
            if (chckSubcounty.isEmpty()) {
                Subcounty subcounty = new Subcounty();
                subcounty.setSubcountyName(locationDTO.getSubcountyName());
                subcounty.setCountyID(countyID);
                subcounty.setSubcountyID(randomNumber);
                Subcounty savedcounty = subcountyRepo.save(subcounty);
                subcountyID = savedcounty.getId();
            } else {
                Subcounty savedSubCounty = chckSubcounty.get();
                subcountyID = savedSubCounty.getId();
            }


//            Constituencies
            Optional<Constituency> chckConstituency = constituencyRepo.findByConstituencyName(locationDTO.getConstituencyName());
            if (chckConstituency.isEmpty()) {
                Constituency constituency1 = new Constituency();
                constituency1.setConstituencyName(locationDTO.getConstituencyName());
                constituency1.setSubcountyID(subcountyID);
                constituency1.setConstituencyID(randomNumber);
                Constituency savedcounty = constituencyRepo.save(constituency1);
                constituencyID = savedcounty.getId();
            } else {
                Constituency savedCon = chckConstituency.get();
                constituencyID = savedCon.getId();
            }

//            Wards
            Optional<Ward> chckWard = wardRepo.findByWardName(locationDTO.getWardName());
            if (chckWard.isEmpty()) {
                Ward ward = new Ward();
                ward.setWardName(locationDTO.getWardName());
                ward.setConstituencyID(constituencyID);
                ward.setWardID(randomNumber);
                Ward savedWard = wardRepo.save(ward);
                wardID = savedWard.getId();
            } else {
                Ward savedWard = chckWard.get();
                wardID = savedWard.getId();
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
        try {
            List<County> allCounties = countyRepo.findAll();
            return new ResponseEntity<>(allCounties, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/subcounties/all")
    public ResponseEntity<Object> getAllSubs() {
        try {
            List<Subcounty> allSubs = subcountyRepo.findAll();
            return new ResponseEntity<>(allSubs, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/constituencies/all")
    public ResponseEntity<Object> getAllconstituencies() {
        try {
            List<Constituency> allconstituencies = constituencyRepo.findAll();
            return new ResponseEntity<>(allconstituencies, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/wards/all")
    public ResponseEntity<Object> getAllWards() {
        try {
            List<Ward> allWards = wardRepo.findAll();
            return new ResponseEntity<>(allWards, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @GetMapping("/village/all")
    public ResponseEntity<Object> getAllVillage() {
        try {
            List<Village> allVillage = villageRepo.findAll();
            return new ResponseEntity<>(allVillage, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }


    @GetMapping("/all")
    public ResponseEntity<Object> allLocations() {
        try {
            List<Locations> locs = countyRepo.findAllLocs();
            return new ResponseEntity<>(locs, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }






}