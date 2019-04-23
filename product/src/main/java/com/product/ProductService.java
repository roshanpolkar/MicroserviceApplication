package com.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product getProduct(Long productId){
       return productRepository.findByProductId(productId);
    }

    @Transactional
    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long productId, Product product) throws Exception {
        if(this.getProduct(productId) != null){
            return productRepository.save(product);
        }
        else{
            throw new Exception("Product not present by product Id "+productId);
        }
    }

    @Transactional
    public void deleteProduct(Long productId) throws Exception{
        Product product = this.getProduct(productId);
        if(product != null) {
            productRepository.delete(product);
        }
        else{
            throw new Exception("Product not present by product Id "+productId);
        }
    }

}
