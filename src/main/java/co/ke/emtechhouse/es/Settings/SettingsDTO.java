package co.ke.emtechhouse.es.Settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SettingsDTO {

    private Long id;
    private Map<String, String> settings;

//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Map<String, String> getSettings() {
//        return settings;
//    }
//
//    public void setSettings(Map<String, String> settings) {
//        this.settings = settings;
//    }
}
