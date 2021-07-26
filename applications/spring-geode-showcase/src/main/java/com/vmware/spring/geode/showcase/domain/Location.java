package com.vmware.spring.geode.showcase.domain;

import lombok.Data;

@Data
public class Location
{
    private String id;
    private String address;
    private String city;
    private String stateCode;
    private String zipCode;
}
