package com.bbs.cart.controller;

import com.bbs.cart.common.CommonConstants;
import com.bbs.cart.model.Cart;
import com.bbs.cart.service.CartDataService;
import com.couchbase.client.java.error.DocumentDoesNotExistException;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.Date;

@RestController
public class CartController {

    @Autowired
    private CartDataService cartDataService;

    @ApiOperation(value = "Create Cart", produces = CommonConstants.STREAM_JSON_TYPE,
            notes = "This API used to create the cart", response = Cart.class)
    @ApiResponses(value = {@ApiResponse(code = 400, message = CommonConstants.BAD_REQUEST),
            @ApiResponse(code = 500, message = CommonConstants.SERVER_ERROR)})

    @PostMapping(value = "/carts/new",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE})
    @Timed
    public Mono<ResponseEntity<Cart>> createCart(@RequestBody(required = true) final Cart cart) {
        return cartDataService.createCart(cart)
                .map(cartObj -> new ResponseEntity<>(cartObj, HttpStatus.CREATED))
                .doOnError(Throwable::printStackTrace)
                .log();
    }


    @ApiOperation(value = "Delete Cart", produces = CommonConstants.STREAM_JSON_TYPE,
            notes = "This API used to delete the cart")
    @ApiResponses(value = {@ApiResponse(code = 400, message = CommonConstants.BAD_REQUEST),
            @ApiResponse(code = 500, message = CommonConstants.SERVER_ERROR)})

    @GetMapping(value = "/cart",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.OK)
    @Timed
    public Mono<ResponseEntity<Cart>> findCart(@PathVariable("cartId") final String cartId) {
        return cartDataService.findCartById(cartId)
                .map(ResponseEntity::ok)
                .doOnError(Throwable::printStackTrace)
                .log();
    }

    @ApiOperation(value = "Update Cart", produces = CommonConstants.STREAM_JSON_TYPE,
            notes = "This API used to update the cart", response = Cart.class)
    @ApiResponses(value = {@ApiResponse(code = 400, message = CommonConstants.BAD_REQUEST),
            @ApiResponse(code = 500, message = CommonConstants.SERVER_ERROR)})

    @PutMapping(value = "/carts/{cartId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE})
    @Timed
    public Mono<ResponseEntity<Cart>> updateCart(@PathVariable("cartId") final String cartId,
                                                 @ApiParam(required = true, name = "requestBodu", value = "Cart") @NotNull @RequestBody(required = true) Cart cart) {
        return cartDataService.updateCart(cartId, cart)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .onErrorReturn(DocumentDoesNotExistException.class, new ResponseEntity<>(HttpStatus.NOT_FOUND))
                .log();
    }

    @ApiOperation(value = "Delete Cart", produces = CommonConstants.STREAM_JSON_TYPE,
            notes = "This API used to get the cart", response = Cart.class)
    @ApiResponses(value = {@ApiResponse(code = 400, message = CommonConstants.BAD_REQUEST),
            @ApiResponse(code = 500, message = CommonConstants.SERVER_ERROR)})

    @DeleteMapping(value = "/carts/{cartId}")
    @Timed
    public Mono<ResponseEntity<Void>> deleteCart(@PathVariable("cartId") final String cartId) {
        return cartDataService.deleteCart(cartId)
                .thenReturn(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .onErrorReturn(DocumentDoesNotExistException.class, new ResponseEntity<>(HttpStatus.NOT_FOUND))
                .log();
    }


}
