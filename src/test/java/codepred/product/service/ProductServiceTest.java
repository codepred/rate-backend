package codepred.product.service;

import codepred.UnitTest;
import codepred.enums.ResponseStatus;
import codepred.product.dto.ProductDto;
import codepred.product.dto.ResponseObj;
import codepred.product.model.ProductModel;
import codepred.product.repository.ProductRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ProductServiceTest implements UnitTest {

    @InjectMocks
    private ProductService systemUnderTest;
    @Mock
    private ProductRepository productRepositoryMock;
    @Mock
    private ModelMapper modelMapperMock;
    private final ProductModel productModelMock = mock(ProductModel.class);
    private final ProductDto productDtoMock = mock(ProductDto.class);
    private final Long id = 1L;
    private final List<ProductModel> productModelList = new ArrayList<>();

    @Nested
    class AddProductTests {

        @Test
        void shouldAddProduct() {
            when(modelMapperMock.map(productDtoMock, ProductModel.class)).thenReturn(productModelMock);

            var result = systemUnderTest.addProduct(productDtoMock);

            verify(productRepositoryMock).save(productModelMock);
            assertEquals(ResponseStatus.ACCEPTED, result.getCode());
            assertEquals("ADDED", result.getMessage());
        }
    }

    @Nested
    class EditProductTests {


        @Test
        void shouldEditProduct() {
            when(productRepositoryMock.findById(id)).thenReturn(Optional.of(productModelMock));
            when(modelMapperMock.map(productDtoMock, ProductModel.class)).thenReturn(productModelMock);
            when(productRepositoryMock.save(productModelMock)).thenReturn(productModelMock);

            var result = callService();

            verify(productModelMock).setId(id);
            assertEquals(ResponseStatus.ACCEPTED, result.getCode());
            assertEquals("EDITED", result.getMessage());
        }

        @Test
        void shouldNotEditProductWhenIdNotExist() {
            var result = callService();

            assertEquals(ResponseStatus.BAD_REQUEST, result.getCode());
            assertEquals("ID_NOT_EXIST", result.getMessage());
        }

        private ResponseObj callService() {
            return systemUnderTest.editProduct(id, productDtoMock);
        }
    }

    @Nested
    class DeleteProductTests {
        @Test
        void shouldDeleteProduct() {
            when(productRepositoryMock.findById(id)).thenReturn(Optional.of(productModelMock));

            var result = callService(id);

            verify(productRepositoryMock).deleteById(id);
            assertEquals(ResponseStatus.ACCEPTED, result.getCode());
            assertEquals("DELETED", result.getMessage());
        }

        @Test
        void shouldNotDeleteProductWhenIdNotExist() {
            var result = systemUnderTest.deleteProduct(null);

            assertEquals(ResponseStatus.BAD_REQUEST, result.getCode());
            assertEquals("ID_NOT_EXIST", result.getMessage());
        }

        private ResponseObj callService(Long id) {
            return systemUnderTest.deleteProduct(id);
        }
    }

    @Nested
    class ShowPublicTests {
        @Test
        void shouldShowPublicList() {
            productModelList.add(productModelMock);
            given(productRepositoryMock.findAll()).willReturn(productModelList);
            when(productRepositoryMock.findPublicProducts()).thenReturn(productModelList);
            var productDtoList = convertToDtoList(productModelList);

            var result = callService();

            assertEquals(Optional.of(productDtoList), result);
        }

        @Test
        void shouldNotShowPublicListWhenListIsEmpty() {
            var result = callService();

            assertEquals(Optional.of(productModelList), result);
        }

        private Optional<List<ProductDto>> callService() {
            return systemUnderTest.showPublicList();
        }
    }

    @Nested
    class ShowOwnTests {
        @Test
        void shouldShowOwnList() {
            productModelList.add(productModelMock);
            given(productRepositoryMock.findAll()).willReturn(productModelList);
            when(productRepositoryMock.findOwnProducts()).thenReturn(productModelList);
            var productDtoList = convertToDtoList(productModelList);

            var result = callService();

            assertEquals(Optional.of(productDtoList), result);
        }

        @Test
        void shouldNotShowOwnListWhenListIsEmpty() {
            var result = callService();

            assertEquals(Optional.of(productModelList), result);
        }

        private Optional<List<ProductDto>> callService() {
            return systemUnderTest.showOwnList();
        }
    }

    private List<ProductDto> convertToDtoList(List<ProductModel> productModelList) {
        return productModelList.stream()
                .map(this::convertToDto)
                .toList();
    }

    private ProductDto convertToDto(ProductModel productModel) {
        return modelMapperMock.map(productModel, ProductDto.class);
    }
}