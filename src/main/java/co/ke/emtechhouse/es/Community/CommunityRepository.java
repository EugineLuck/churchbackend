package co.ke.emtechhouse.es.Community;

import co.ke.emtechhouse.es.Groups.Groups;
import co.ke.emtechhouse.es.OutStation.Stations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community,Long> {
    List<Community> findByOutStationId(Long outStationId);
    @Transactional
    @Query(nativeQuery = true,value = "SELECT c.id as communityId, c.community_name AS CommunityName, c.community_location AS location,o.out_station_name as OutstationName,(SELECT COUNT(DISTINCT id) FROM members WHERE c.id = community_id) AS members,(SELECT COUNT(DISTINCT id) FROM family WHERE c.id = community_id) AS families FROM community c, out_station o where c.out_station_id=o.id;  ")
    List<CommunityDetails> getAllCommunity();

    @Transactional
    @Query(nativeQuery = true,value = "SELECT c.id as communityId, c.community_name AS CommunityName, c.community_location AS location,o.out_station_name as OutstationName,(SELECT COUNT(DISTINCT id) FROM members WHERE c.id = community_id) AS members,(SELECT COUNT(DISTINCT id) FROM family WHERE c.id = community_id) AS families FROM community c, out_station o where c.out_station_id= :id;  ")
    List<CommunityDetails> getAllCommunitiesOutstation(Long id);


}
//SELECT  c.id as communityId,c.community_name as communities,c.community_location as location,o.out_station_name as station from community c, out_station o where c.out_station_id=o.id"