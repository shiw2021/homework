package hziee.spc.entity.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import hziee.spc.entity.ApplicantDtoWithRecords;
import hziee.spc.entity.Record;
import hziee.spc.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * A DTO for the {@link hziee.spc.entity.Record} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RecordAndUserDto implements Serializable {
    private Integer signer;
    @JsonProperty("newRecord")
    private Record record;
    @JsonProperty("userinfo")
    private User user;
}