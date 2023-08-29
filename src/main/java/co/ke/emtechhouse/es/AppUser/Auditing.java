/*
 * Copyright (c) 2022. Omukubwa Software Solutions (OSS)
 */

package co.ke.emtechhouse.es.AppUser;

import javax.persistence.*;

@Entity
@Table(name = "audittrails")
public class Auditing {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

    public Auditing(Long id, String starttime, String username, String requestip, String activity) {
        this.id = id;
        this.starttime = starttime;
        this.username = username;
        this.requestip = requestip;
        this.activity = activity;
    }

        private String starttime;
        private String username;
        private String requestip;
        private String activity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRequestip() {
        return requestip;
    }

    public void setRequestip(String requestip) {
        this.requestip = requestip;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Auditing() {
    }
}


