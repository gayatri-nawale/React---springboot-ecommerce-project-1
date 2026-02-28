package com.gayatri.demo.controller;

import com.gayatri.demo.models.Product;
import com.gayatri.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestPart;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService service;


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getallproducts(){
        return new ResponseEntity<>(service.getallproducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getproductbyId(@PathVariable int id){
        Product p=service.getproductbyId(id);
        if(p!=null){
            return new ResponseEntity<>(p,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

        // get product image
        @GetMapping("product/{id}/image")
        public ResponseEntity<byte[]> getproductimage(@PathVariable int id){
            Product p=service.getproductbyId(id);
            return new ResponseEntity<>(p.getImageData(),HttpStatus.CREATED);

        }
        @PostMapping("/product")
        public ResponseEntity<?> addproduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
            Product p= null;
            try {
                p = service.addorUpdateproduct(product,imageFile);
                return new ResponseEntity<>(p,HttpStatus.CREATED);
            } catch (IOException e) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PutMapping("/product/{id}")
         public ResponseEntity<String> addorUpdateproduct(@RequestPart Product product,@RequestPart MultipartFile imageFile){
        Product p=null;
        try{
            p = service.addorUpdateproduct(product,imageFile);
            return new ResponseEntity<>("Updated",HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }

        @DeleteMapping("/product/{id}")
          public ResponseEntity<String> deleteproduct(@PathVariable int id){
        Product p=service.getproductbyId(id);
        if(p!=null){
            service.deleteproduct(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
        }

        @GetMapping("/products/search")
        public ResponseEntity<List<Product>> searchproduct(@RequestParam String keyword){
            List<Product>products=service.searchProducts(keyword);
            System.out.println("Searching with "+keyword);
            return new ResponseEntity<>(products,HttpStatus.OK);
        }
}
