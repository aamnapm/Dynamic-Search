package com.example.search.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobDTO {

    private String name;
    private String description;

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("JobInfo")
    public static class Info extends JobDTO {
        private Long id;
        private List<PersonDTO> personList;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("JobCreate")
    public static class Create extends JobDTO {

    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("JobUpdate")
    public static class Update extends JobDTO {

    }
}