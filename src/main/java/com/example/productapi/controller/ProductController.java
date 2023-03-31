package com.example.productapi.controller;

import com.example.productapi.controller.dto.ProductDto;
import com.example.productapi.model.Product;
import com.example.productapi.service.ProductService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> productsDto = productService.getProducts()
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(productsDto);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto,
                                                    BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            List <String> errors = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(errors);
        }

        Product productToSave = productService.saveProduct(modelMapper.map(productDto, Product.class));

        ProductDto productSaved = modelMapper.map(productToSave, ProductDto.class);

        return new ResponseEntity<>(productSaved, HttpStatusCode.valueOf(201));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                            @RequestBody ProductDto productDto) {

        Product product = productService.getProductById(productId);
        this.buildProductToUpdate(productDto, product);



        return ResponseEntity.ok().body(this.productService.updateProduct(product));
    }

    // Lo mas optimo es que se haga un mapper personalizado para mapear los diferentes atributos que puedan llegar en el patch
    // Esta opcion se hizo con fines de rapidez, sin embargo no es la mas optima.
    private void buildProductToUpdate(ProductDto productDto, Product product) {

        if(productDto.getCategory() != null) {
            product.setCategory(productDto.getCategory());
        }

        if(productDto.getDescription() != null){
            product.setDescription(productDto.getDescription());
        }

        if(productDto.getPrice() != null){
            product.setPrice(productDto.getPrice());
        }

        if(productDto.getName() != null) {
            product.setName(productDto.getName());
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {

        productService.deleteProduct(productId);

        return new ResponseEntity<String>("Product deleted", HttpStatus.OK);
    }


}
