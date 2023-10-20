package codepred.rate.controller;

import codepred.UnitTest;
import codepred.enums.ResponseStatus;
import codepred.rate.dto.RateDTO;
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
    RateController systemUnderTest;
    @Mock
    RateService rateServiceMock;

    @Nested
    class addRatingTest {
        @Test
        void givenRateDTO_whenAddRating_thenReturn200() {
            //given
            var rateDTOmock = mock(RateDTO.class);
            given(rateDTOmock.getProductName()).willReturn("test");
            //when
            var result = systemUnderTest.addRating(rateDTOmock);
            //then
            verify(rateServiceMock).addRating(rateDTOmock);
            Assertions.assertEquals(ResponseEntity.ok(rateServiceMock.addRating(rateDTOmock)), result);
        }

        @Test
        void givenRateDTOwithNullName_whenAddRating_thenReturn400() {
            //given
            var rateDTOmock = mock(RateDTO.class);
            given(rateDTOmock.getProductName()).willReturn(null);
            //when
            var result = systemUnderTest.addRating(rateDTOmock);
            //then
            Assertions.assertEquals(ResponseEntity
                    .status(rateServiceMock.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_NULL")), result);
        }

        @Test
        void givenRateDTOwithBlankName_whenAddRating_thenReturn400() {
            //given
            var rateDTOmock = mock(RateDTO.class);
            given(rateDTOmock.getProductName()).willReturn("");
            //when
            var result = systemUnderTest.addRating(rateDTOmock);
            //then
            Assertions.assertEquals(ResponseEntity
                    .status(rateServiceMock.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_BLANK")), result);
        }
    }

    @Nested
    class editRatingTest {
        @Test
        void givenRateDTOAndID_whenEditRating_thenReturn200() {
            //given
            Long id = 1L;
            var rateDTOmock = mock(RateDTO.class);
            given(rateDTOmock.getProductName()).willReturn("test");
            //when
            var result = systemUnderTest.editRating(id, rateDTOmock);
            //then
            verify(rateServiceMock).editRating(id, rateDTOmock);
            Assertions.assertEquals(ResponseEntity.ok(rateServiceMock.editRating(id, rateDTOmock)), result);
        }

        @Test
        void givenRateDTOwithBlankNameAndID_whenEditRating_thenReturn400() {
            //given
            Long id = 1L;
            var rateDTOmock = mock(RateDTO.class);
            given(rateDTOmock.getProductName()).willReturn("");
            //when
            var result = systemUnderTest.editRating(id, rateDTOmock);
            //then
            Assertions.assertEquals(ResponseEntity
                    .status(rateServiceMock.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_BLANK")), result);
        }

        @Test
        void givenRateDTOwithNullNameAndID_whenEditRating_thenReturn400() {
            //given
            Long id = 1L;
            var rateDTOmock = mock(RateDTO.class);
            given(rateDTOmock.getProductName()).willReturn(null);
            //when
            var result = systemUnderTest.editRating(id, rateDTOmock);
            //then
            Assertions.assertEquals(ResponseEntity
                    .status(rateServiceMock.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_NULL")), result);
        }

        @Test
        void givenRateDTOAndNullID_whenEditRating_thenReturn400() {
            //given
            Long id = null;
            var rateDTOmock = mock(RateDTO.class);
            given(rateDTOmock.getProductName()).willReturn("test");
            //when
            var result = systemUnderTest.editRating(id, rateDTOmock);
            //then
            Assertions.assertEquals(ResponseEntity
                    .status(rateServiceMock.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "ID_IS_NULL")), result);
        }
    }

    @Nested
    class deleteRatingTest {
        @Test
        void givenId_whenDeleteRating_thenReturn200() {
            //given
            Long id = 1L;
            //when
            var result = systemUnderTest.deleteRating(id);
            //then
            verify(rateServiceMock).deleteRating(id);
            Assertions.assertEquals(ResponseEntity.ok(rateServiceMock.deleteRating(id)), result);
        }

        @Test
        void givenId_whenDeleteRating_thenReturn400() {
            //given
            Long id = null;
            //when
            var result = systemUnderTest.deleteRating(id);
            //then
            Assertions.assertEquals(ResponseEntity
                    .status(rateServiceMock.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "ID_IS_NULL")), result);
        }
    }
}