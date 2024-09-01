package podpivasniki.shortfy.site.branchedpipeline.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ObjectSettingConstructorParam {
    private String name;
    @JsonProperty("class")
    private String className;
    private String value;
    private String ref;
    @JsonIgnore
    private Setting setting;

    public ObjectSettingConstructorParam(String name, String ref) {
        this.name = name;
        this.ref = ref;
    }

    public ObjectSettingConstructorParam(String name, String className, String value) {
        this.name = name;
        this.className = className;
        this.value = value;
    }
    @JsonIgnore
    public Setting getSettingIfParamIsNotRef(){
        if(isRef())
            throw new IllegalStateException("This params in Object constructor or setter, has been called but it is ref");
        if(this.setting == null){
            this.setting = new Setting(name,className,value);
        }
        return this.setting;
    }

    public boolean isRef(){
        return ref != null;
    }
}
