package codepred.rate.controller;

import codepred.UnitTest;
import codepred.enums.ResponseStatus;
import codepred.rate.dto.RateDto;
import codepred.rate.dto.ResponseObj;
import codepred.rate.service.RateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RateControllerTest implements UnitTest {

    @InjectMocks
    private RateController systemUnderTest;
    @Mock
    private RateService rateServiceMock;

    @Nested
    class AddRatingTest {

        private final RateDto rateDtoMock = mock(RateDto.class);

        @Test
        void shouldAddRating() {
            given(rateDtoMock.getProductName()).willReturn("test");

            var result = callController();

            verify(rateServiceMock).addRating(rateDtoMock);
            Assertions.assertEquals(ResponseEntity.ok(rateServiceMock.addRating(rateDtoMock)), result);
        }

        @Test
        void shouldNotAddRatingWhenNameIsNull() {
            given(rateDtoMock.getProductName()).willReturn(null);

            var result = callController();

            Assertions.assertEquals(ResponseEntity.status(rateServiceMock.getResponseCode(ResponseStatus.BAD_REQUEST))
                                        .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_NULL")), result);
        }

        @Test
        void shouldNotAddRatingWhenNameIsBlank() {
            given(rateDtoMock.getProductName()).willReturn("");

            var result = callController();

            Assertions.assertEquals(ResponseEntity.status(rateServiceMock.getResponseCode(ResponseStatus.BAD_REQUEST))
                                        .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_BLANK")), result);
        }

        private ResponseEntity<Object> callController() {
            return systemUnderTest.addRating(rateDtoMock);
        }
    }

    @Nested
    class EditRatingTest {

        private final Long id = 1L;
        private final RateDto rateDtoMock = mock(RateDto.class);

        @Test
        void shouldEditRating() {
            given(rateDtoMock.getProductName()).willReturn("test");

            var result = callController(id, rateDtoMock);

            verify(rateServiceMock).editRating(id, rateDtoMock);
            Assertions.assertEquals(ResponseEntity.ok(rateServiceMock.editRating(id, rateDtoMock)), result);
        }

        @Test
        void shouldNotEditRatingWhenNameIsBlank() {
            given(rateDtoMock.getProductName()).willReturn("");

            var result = callController(id, rateDtoMock);

            Assertions.assertEquals(ResponseEntity.status(rateServiceMock.getResponseCode(ResponseStatus.BAD_REQUEST))
                                        .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_BLANK")), result);
        }

        @Test
        void shouldNotEditRatingWhenNameIsNull() {
            given(rateDtoMock.getProductName()).willReturn(null);

            var result = callController(id, rateDtoMock);

            Assertions.assertEquals(ResponseEntity.status(rateServiceMock.getResponseCode(ResponseStatus.BAD_REQUEST))
                                        .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_NULL")), result);
        }

        @Test
        void shouldNotEditRatingWhenIdIsNull() {
            given(rateDtoMock.getProductName()).willReturn("test");

            var result = callController(null, rateDtoMock);

            Assertions.assertEquals(ResponseEntity.status(rateServiceMock.getResponseCode(ResponseStatus.BAD_REQUEST))
                                        .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "ID_IS_NULL")), result);
        }

        private ResponseEntity<Object> callController(Long id, RateDto rateDtoMock) {
            return systemUnderTest.editRating(id, rateDtoMock);
        }
    }

    @Nested
    class DeleteRatingTest {

        @Test
        void shouldDeleteRating() {
            final var id = 1L;

            var result = systemUnderTest.deleteRating(id);
            verify(rateServiceMock).deleteRating(id);
            Assertions.assertEquals(ResponseEntity.ok(rateServiceMock.deleteRating(id)), result);
        }

        @Test
        void shouldNotDeleteRating() {
            var result = systemUnderTest.deleteRating(null);

            Assertions.assertEquals(ResponseEntity.status(rateServiceMock.getResponseCode(ResponseStatus.BAD_REQUEST))
                                        .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "ID_IS_NULL")), result);
        }
    }

}