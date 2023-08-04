package ee.katrina.webshop.controller;

import ee.katrina.webshop.cache.ProductCache;
import ee.katrina.webshop.entity.Product;
import ee.katrina.webshop.exception.NotEnoughInStockException;
import ee.katrina.webshop.exception.ProductNotFoundException;
import ee.katrina.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCache productCache;

    @GetMapping("products")
    public List<Product> getProducts() {
        return productRepository.findAllByOrderById();
    }

    @GetMapping("public-products") // localhost:8080/public-products?size=2&page=0
    public Page<Product> getProductsByPage(Pageable pageable) {
        return productRepository.findAllByOrderById(pageable);
    }

    @PostMapping ("products")
    public List<Product> addProducts(@RequestBody Product product) {
        productRepository.save(product);
        return productRepository.findAllByOrderById();
    }

    @DeleteMapping ("products/{id}")
    public List<Product> deleteProducts(@PathVariable Long id) {
        productRepository.deleteById(id);
        return productRepository.findAllByOrderById();
    }

    @GetMapping("products/{id}")
    public Product getProduct(@PathVariable Long id) throws ProductNotFoundException, ExecutionException {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found");
        }
//        return productRepository.findById(id).get();
        return productCache.getProduct(id);
    }

    @PutMapping("products/{id}")
    public List<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) throws ExecutionException {
        if (productRepository.existsById(id)) {
            product.setId(productRepository.findById(id).get().getId());
            productRepository.save(product);
            productCache.refreshProduct(id, product);
        }
        return productRepository.findAllByOrderById();
    }

    @PatchMapping("increase-stock/{id}")
    public List<Product> increaseStock(@PathVariable("id") Long id) throws ExecutionException {
//        Product product = productRepository.findById(id).get();
        Product product = productCache.getProduct(id);
        product.setStock(product.getStock()+1);
        productRepository.save(product);
        productCache.refreshProduct(id, product);
        return productRepository.findAllByOrderById();
    }

    @PatchMapping("decrease-stock/{id}")
    public List<Product> decreaseStock(@PathVariable("id") Long id) throws NotEnoughInStockException, ExecutionException {
//        Product product = productRepository.findById(id).get();
        Product product = productCache.getProduct(id);
        if (product.getStock() > 0) {
            product.setStock(product.getStock()-1);
            productRepository.save(product);
            productCache.refreshProduct(id, product);
        } else {
            throw new NotEnoughInStockException("Kogus ei saa miinusesse minna");
        }
        return productRepository.findAllByOrderById();
    }
}
