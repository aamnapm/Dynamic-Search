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
public class AddressTelDTO {

    private String tel;
    private Long addressId;


    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("AddressTelInfo")
    public static class Info extends AddressTelDTO {
        private Long id;
        private AddressDTO address;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("AddressTelCreate")
    public static class Create extends AddressTelDTO {

    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("AddressTelUpdate")
    public static class Update extends AddressTelDTO {

    }
}