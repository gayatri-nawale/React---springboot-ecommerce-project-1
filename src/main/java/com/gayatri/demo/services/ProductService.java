package com.gayatri.demo.services;

import com.gayatri.demo.models.Product;
import com.gayatri.demo.repository.Productrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private Productrepo repo;

    public List<Product> getallproducts(){
        return repo.findAll();
    }


    public Product getproductbyId(int id) {
        return repo.findById(id).orElse(new Product());
    }

    public Product addorUpdateproduct(Product product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
        return repo.save(product);
    }


    public void deleteproduct(int id) {
        repo.deleteById(id);
    }


    public List<Product> searchProducts(String keyword) {
        return repo.searchProduct(keyword);
    }
}
