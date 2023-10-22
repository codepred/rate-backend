package codepred.product.controller;

import codepred.enums.ResponseStatus;
import codepred.product.dto.ProductDto;
import codepred.product.service.ProductService;
import codepred.rate.dto.ResponseObj;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    ResponseEntity<Object> addProduct(@RequestBody ProductDto productDto) {
        if (isNullName(productDto)) {
            return ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_NULL"));
        }
        if (isNullCategory(productDto)) {
            return ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "CATEGORY_IS_NULL"));
        }
        if (isFilesNull(productDto)) {
            return ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "FILES_CAN_NOT_BE_NULL"));
        }
        if (isNameBlank(productDto)) {
            return ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_BLANK"));
        }
        if (isCategoryBlank(productDto)) {
            return ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "CATEGORY_IS_BLANK"));
        }
        return ResponseEntity.ok(productService.addProduct(productDto));
    }

    @PutMapping("/edit/{id}")
    ResponseEntity<Object> editProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        if (isNullId(id)) {
            return ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "ID_IS_NULL"));
        }
        if (isNullName(productDto)) {
            return ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_NULL"));
        }
        if (isNullCategory(productDto)) {
            return ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "CATEGORY_IS_NULL"));
        }
        if (isFilesNull(productDto)) {
            return ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "FILES_CAN_NOT_BE_NULL"));
        }
        if (isNameBlank(productDto)) {
            return ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_BLANK"));
        }
        if (isCategoryBlank(productDto)) {
            return ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "CATEGORY_IS_BLANK"));
        }
        return ResponseEntity.ok(productService.editProduct(id, productDto));
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id) {
        if (isNullId(id)) {
            return ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "ID_IS_NULL"));
        }
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @GetMapping("/list")
    ResponseEntity<Optional<List<ProductDto>>> showPublicList() {
        return ResponseEntity.ok(productService.showPublicList());
    }

    @GetMapping("/own-list")
    ResponseEntity<Optional<List<ProductDto>>> showOwnList() {
        return ResponseEntity.ok(productService.showOwnList());
    }

    private boolean isNameBlank(ProductDto productDto) {
        return productDto.getProductName().isBlank();
    }

    private boolean isNullName(ProductDto productDto) {
        return productDto.getProductName() == null;
    }

    private boolean isCategoryBlank(ProductDto productDto) {
        return productDto.getCategory().isBlank();
    }

    private boolean isNullCategory(ProductDto productDto) {
        return productDto.getCategory() == null;
    }

    private boolean isFilesNull(ProductDto productDto) {
        return productDto.getFile1() == null && productDto.getFile2() == null;
    }

    private boolean isNullId(Long id) {
        return id == null;
    }
}
