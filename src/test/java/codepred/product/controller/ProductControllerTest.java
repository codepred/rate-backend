package codepred.product.controller;

import codepred.UnitTest;
import codepred.enums.ResponseStatus;
import codepred.product.dto.ProductDto;
import codepred.product.service.ProductService;
import codepred.rate.dto.ResponseObj;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductControllerTest implements UnitTest {

    @InjectMocks
    ProductController systemUnderTest;

    @Mock
    ProductService productService;

    private final ProductDto productDtoMock = mock(ProductDto.class);

    private final Long id = 1L;

    @Nested
    class AddProductTest {
        @Test
        void shouldAddProduct() {
            setProductDtoMockValues("product", "category", new byte[]{(byte) 0x00, (byte) 0x00});

            var result = callController();

            verify(productService).addProduct(productDtoMock);
            Assertions.assertEquals(ResponseEntity.ok(productService.addProduct(productDtoMock)), result);
        }

        @Test
        void shouldNotAddProductWhenNameIsNull() {
            setProductDtoMockValues(null, "category", new byte[]{(byte) 0x00, (byte) 0x00});

            var result = callController();

            Assertions.assertEquals(ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_NULL")), result);
        }

        @Test
        void shouldNotAddProductWhenCategoryIsNull() {
            setProductDtoMockValues("product", null, new byte[]{(byte) 0x00, (byte) 0x00});

            var result = callController();

            Assertions.assertEquals(ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "CATEGORY_IS_NULL")), result);
        }

        @Test
        void shouldNotAddProductWhenFilesIsNull() {
            setProductDtoMockValues("product", "category", null);

            var result = callController();

            Assertions.assertEquals(ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "FILES_CAN_NOT_BE_NULL")), result);
        }

        @Test
        void shouldNotAddProductWhenNameIsBlank() {
            setProductDtoMockValues("", "category", new byte[]{(byte) 0x00, (byte) 0x00});

            var result = callController();

            Assertions.assertEquals(ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_BLANK")), result);
        }

        @Test
        void shouldNotAddProductWhenCategoryIsBlank() {
            setProductDtoMockValues("product", "", new byte[]{(byte) 0x00, (byte) 0x00});

            var result = callController();

            Assertions.assertEquals(ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "CATEGORY_IS_BLANK")), result);
        }

        private ResponseEntity<Object> callController() {
            return systemUnderTest.addProduct(productDtoMock);
        }
    }

    @Nested
    class EditProductTest {
        @Test
        void shouldEditProduct() {
            setProductDtoMockValues("product", "category", new byte[]{(byte) 0x00, (byte) 0x00});

            var result = callController(id, productDtoMock);

            verify(productService).editProduct(id, productDtoMock);
            Assertions.assertEquals(ResponseEntity.ok(productService.editProduct(id, productDtoMock)), result);
        }

        @Test
        void shouldNotEditProductWhenIdIsNull() {
            setProductDtoMockValues("product", "category", new byte[]{(byte) 0x00, (byte) 0x00});

            var result = callController(null, productDtoMock);

            Assertions.assertEquals(ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "ID_IS_NULL")), result);
        }

        @Test
        void shouldNotEditProductWhenNameIsNull() {
            setProductDtoMockValues(null, "category", new byte[]{(byte) 0x00, (byte) 0x00});

            var result = callController(id, productDtoMock);

            Assertions.assertEquals(ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_NULL")), result);
        }

        @Test
        void shouldNotEditProductWhenCategoryIsNull() {
            setProductDtoMockValues("product", null, new byte[]{(byte) 0x00, (byte) 0x00});

            var result = callController(id, productDtoMock);

            Assertions.assertEquals(ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "CATEGORY_IS_NULL")), result);
        }

        @Test
        void shouldNotEditProductWhenFilesIsNull() {
            setProductDtoMockValues("product", "category", null);

            var result = callController(id, productDtoMock);

            Assertions.assertEquals(ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "FILES_CAN_NOT_BE_NULL")), result);
        }

        @Test
        void shouldNotEditProductWhenNameIsBlank() {
            setProductDtoMockValues("", "category", new byte[]{(byte) 0x00, (byte) 0x00});

            var result = callController(id, productDtoMock);

            Assertions.assertEquals(ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_BLANK")), result);
        }

        @Test
        void shouldNotEditProductWhenCategoryIsBlank() {
            setProductDtoMockValues("product", "", new byte[]{(byte) 0x00, (byte) 0x00});

            var result = callController(id, productDtoMock);

            Assertions.assertEquals(ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "CATEGORY_IS_BLANK")), result);
        }

        private ResponseEntity<Object> callController(Long id, ProductDto productDtoMock) {
            return systemUnderTest.editProduct(id, productDtoMock);
        }
    }

    @Nested
    class DeleteProductTest {
        @Test
        void shouldDeleteRating() {
            var result = systemUnderTest.deleteProduct(id);

            verify(productService).deleteProduct(id);
            Assertions.assertEquals(ResponseEntity.ok(productService.deleteProduct(id)), result);
        }

        @Test
        void shouldNotDeleteRating() {
            var result = systemUnderTest.deleteProduct(null);

            Assertions.assertEquals(ResponseEntity.status(productService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "ID_IS_NULL")), result);
        }
    }

    @Nested
    class ShowPublicListTest {
        @Test
        void shouldShowPublicList() {
            var result = systemUnderTest.showPublicList();

            verify(productService).showPublicList();
            Assertions.assertEquals(ResponseEntity.ok(productService.showPublicList()), result);
        }
    }

    @Nested
    class ShowOwnListTest {
        @Test
        void shouldShowOwnList() {
            var result = systemUnderTest.showOwnList();

            verify(productService).showOwnList();
            Assertions.assertEquals(ResponseEntity.ok(productService.showOwnList()), result);
        }
    }

    private void setProductDtoMockValues(String productName, String category, byte[] file1) {
        given(productDtoMock.getProductName()).willReturn(productName);
        given(productDtoMock.getCategory()).willReturn(category);
        given(productDtoMock.isEnabled()).willReturn(true);
        given(productDtoMock.getFile1()).willReturn(file1);
        given(productDtoMock.getFile2()).willReturn(null);
    }
}