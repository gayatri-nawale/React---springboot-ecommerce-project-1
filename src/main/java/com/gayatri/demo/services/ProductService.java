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

    public Product addorUpdateproduct(Product product, MultipartFile imageFile) throws IOException {
        // 🔵 ADD CASE (New Product)
        if (product.getId() == 0) {

            if (imageFile != null && !imageFile.isEmpty()) {
                product.setImageName(imageFile.getOriginalFilename());
                product.setImageType(imageFile.getContentType());
                product.setImageData(imageFile.getBytes());
            }

            return repo.save(product);
        }

        // 🔵 UPDATE CASE
        Product existingProduct = repo.findById(product.getId()).orElse(null);

        if (existingProduct == null) {
            return null;
        }

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setReleaseDate(product.getReleaseDate());
        existingProduct.setAvailable(product.isAvailable());
        existingProduct.setStockQty(product.getStockQty());

        if (imageFile != null && !imageFile.isEmpty()) {
            existingProduct.setImageName(imageFile.getOriginalFilename());
            existingProduct.setImageType(imageFile.getContentType());
            existingProduct.setImageData(imageFile.getBytes());
        }

        return repo.save(existingProduct);
    }


    public void deleteproduct(int id) {
        repo.deleteById(id);
    }


    public List<Product> searchProducts(String keyword) {
        return repo.searchProduct(keyword);
    }
}
