package co.ke.emtechhouse.es.Settings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MetaFieldRequest {
    private String metaField;
    private String metaValue;

}
