package co.ke.emtechhouse.es.OutStation;

import co.ke.emtechhouse.es.Auth.Members.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface OutStationRepository extends JpaRepository<OutStation,Long> {

    @Transactional
    @Query(nativeQuery = true,value = "SELECT  o.out_station_name AS name, o.out_station_location AS location, (SELECT COUNT(DISTINCT id) FROM members WHERE o.id = out_station_id) AS member,(SELECT COUNT(DISTINCT id) FROM community WHERE o.id = out_station_id) AS communities, (SELECT COUNT(DISTINCT id) FROM family WHERE o.id = out_station_id) AS families, (SELECT COUNT(DISTINCT id) FROM groups WHERE o.id = out_station_id) AS groups FROM  out_station o;")
    List<Stations> findStations();
    List<OutStation> findAll();


}
//    @Query(nativeQuery = true,value = "SELECT count(m.id) as member,count(f.id) as families,count(c.id) as communities, o.out_station_name as name,o.out_station_location as location FROM out_station o, community c,members m,family f where o.id=m.out_station_id")