package com.bbs.cart.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.Date;

@Data
@Document
@ApiModel(description = "Cart Model")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Cart {

    @Id
    @EqualsAndHashCode.Include
    private String cartId;
    @Field
    private String userType;
    @Field
    private String userId;
    @Field
    private Date createdDate;
    @Field
    private Date lastUpdatedTime;
    @Field
    private String itemCount;
}
