package com.example.search.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {

    private String unit;
    private String city;
    private String street;
    private Long personId;


    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("AddressInfo")
    public static class Info extends AddressDTO {
        private Long id;
        private PersonDTO person;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("AddressCreate")
    public static class Create extends AddressDTO {

    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("AddressUpdate")
    public static class Update extends AddressDTO {

    }
}