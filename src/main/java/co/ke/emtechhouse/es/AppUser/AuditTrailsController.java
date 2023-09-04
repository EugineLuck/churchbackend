/*
 * Copyright (c) 2022. Omukubwa Software Solutions (OSS)
 */

package co.ke.emtechhouse.es.AppUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/church/audit")
public class AuditTrailsController {

@Autowired
private AuditingRepository repository;

    //List all audit trails for a single day
    @GetMapping("/todaylogs")
    public ResponseEntity<List<Auditing>> getTodayLogs(@RequestParam("uname") String username, @RequestParam("stime") String starttime) {
        List<Auditing> logs = repository.todayTrails(username,starttime);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    //List all audit trails for a user
    @GetMapping("/alllogs/{username}")
    public ResponseEntity<List<Auditing>> getAllLogs(@PathVariable("username") String username){
        List<Auditing> logs = repository.allTrails(username);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
}
