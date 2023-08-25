package co.ke.emtechhouse.es.Auth.Members;

import java.util.Date;

public interface MemberDetails {
    String getNumber();
    String getFirstname();
    String getLastname();
    String getChurch();
    String getCommunity();
    String getAppId();
    String getFamily();

    Long getFamilyId();
    Long getCommunityId();
    Long getGroupsId();
    Long getChurchId();
    Long getMid();


    String getPostedTime();
    String getMor();
    String getLogged_in();

    String getGroups();

    String getEmail();
    String getNation();
    String getPhone();
    String getDob();
    Long getId();
    Long getRole();
}
