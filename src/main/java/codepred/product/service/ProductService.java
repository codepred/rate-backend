package codepred.product.service;

import codepred.enums.ResponseStatus;
import codepred.product.dto.ProductDto;
import codepred.product.dto.ResponseObj;
import codepred.product.model.ProductModel;
import codepred.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static codepred.enums.ResponseStatus.ACCEPTED;
import static codepred.enums.ResponseStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ResponseObj addProduct(ProductDto productDto) {
        var productModel = modelMapper.map(productDto, ProductModel.class);
        productRepository.save(productModel);
        return new ResponseObj(ACCEPTED, "ADDED");
    }

    @Transactional
    public ResponseObj editProduct(Long id, ProductDto productDto) {
        if (isIdExist(id)) {
            var productModel = modelMapper.map(productDto, ProductModel.class);
            productModel.setId(id);
            productRepository.save(productModel);
            return new ResponseObj(ACCEPTED, "EDITED");
        } else {
            return new ResponseObj(BAD_REQUEST, "ID_NOT_EXIST");
        }
    }

    @Transactional
    public ResponseObj deleteProduct(Long id) {
        if (isIdExist(id)) {
            productRepository.deleteById(id);
            return new ResponseObj(ACCEPTED, "DELETED");
        } else {
            return new ResponseObj(BAD_REQUEST, "ID_NOT_EXIST");
        }
    }

    @Transactional
    public Optional<List<ProductDto>> showPublicList() {
        if (isListEmpty()) {
            return Optional.of(new ArrayList<>());
        } else {
            var publicList = productRepository.findPublicProducts();
            return Optional.of(convertToDtoList(publicList));
        }
    }

    @Transactional
    public Optional<List<ProductDto>> showOwnList() {
        if (isListEmpty()) {
            return Optional.of(new ArrayList<>());
        } else {
            var publicList = productRepository.findOwnProducts();
            return Optional.of(convertToDtoList(publicList));
        }
    }

    public final Integer getResponseCode(ResponseStatus code) {
        if (code.equals(ResponseStatus.ACCEPTED)) {
            return 200;
        } else if (code.equals(ResponseStatus.BAD_REQUEST)) {
            return 400;
        }
        return 500;
    }

    private List<ProductDto> convertToDtoList(List<ProductModel> productModelList) {
        return productModelList.stream()
                .map(this::convertToDto)
                .toList();
    }

    private ProductDto convertToDto(ProductModel productModel) {
        return modelMapper.map(productModel, ProductDto.class);
    }

    private boolean isIdExist(Long id) {
        return productRepository.findById(id).isPresent();
    }

    private boolean isListEmpty() {
        return productRepository.findAll().isEmpty();
    }
}
