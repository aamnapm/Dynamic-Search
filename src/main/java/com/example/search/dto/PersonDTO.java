package com.example.search.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDTO {

    private Long jobId;
    private Integer age;
    private String firstName;
    private String lastName;
    private LocalDateTime birthdate;


    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("PersonInfo")
    public static class Info extends PersonDTO {
        private Long id;
        private JobDTO job;
        private List<AddressDTO> addressList;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("PersonCreate")
    public static class Create extends PersonDTO {

    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("PersonUpdate")
    public static class Update extends PersonDTO {

    }
}