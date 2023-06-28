package com.bank.controlller;

import com.bank.model.entity.Product;
import com.bank.model.response.ReportProductResponse;
import com.bank.service.ProductService;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bank/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Single<Product> save(@Validated @RequestBody Product product) {
        return productService.save(product);

    }

    @PostMapping("/vip")
    public Maybe<Product> saveAccountPersonalVip(@RequestBody Product request) {
        return productService.saveAccountPersonalVip(request);
    }

    @GetMapping("/{id}")
    public Single<Product> fingProductById(@PathVariable String id) {
        return productService.findById(id);
    }

    @GetMapping("/customer/{idCustomer}")
    public Flowable<Product> findAllProductsByCustomer(@PathVariable String idCustomer) {
        return productService.findAllProductByIdCustomer(idCustomer);
    }

    @GetMapping("/{idProduct}/commissions")
    public Maybe<ReportProductResponse> findProductWhitCommissions(@PathVariable String idProduct) {
        return productService.findProductWhitTransactionCommission(idProduct);
    }
}
