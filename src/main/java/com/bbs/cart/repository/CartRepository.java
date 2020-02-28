package com.bbs.cart.repository;

import com.bbs.cart.model.Cart;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
@ViewIndexed(designDoc = "cart")
public interface CartRepository extends ReactiveCouchbaseRepository<Cart, String> {

}
