package co.ke.emtechhouse.es.MpesaIntergration;

import com.google.firebase.database.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class fetchDTO {
    private Date startDate;
    private  Date endDate;

    @Nullable
    private String memberNumber;

    @Nullable
    private Integer familyID;

    @Nullable
    private Integer churchID;

    @Nullable
    private Integer communityID;


}
