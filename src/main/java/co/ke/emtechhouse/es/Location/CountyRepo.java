package co.ke.emtechhouse.es.Location;

import co.ke.emtechhouse.es.MpesaIntergration.SuccessfullyTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CountyRepo extends JpaRepository<County, Long> {
    Optional<County> findByCountyName(String countyName);

    @Transactional
    @Query(nativeQuery = true,value = "SELECT c.county_name AS county, sb.subcounty_name AS subCounty, con.constituency_name AS constituency, w.ward_name AS ward, v.village_name AS village FROM county c JOIN subcounty sb ON c.id = sb.countyid JOIN constituency con ON con.subcountyid= sb.id JOIN ward w ON w.constituencyid = con.id JOIN village v ON v.wardid= w.id group by c.id")
    List<Locations> findAllLocs();
}
