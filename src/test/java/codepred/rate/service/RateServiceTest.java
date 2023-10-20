package codepred.rate.service;

import codepred.UnitTest;
import codepred.enums.ResponseStatus;
import codepred.rate.dto.RateDTO;
import codepred.rate.model.RateModel;
import codepred.rate.repository.RateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.Mockito.*;

class RateServiceTest implements UnitTest {
    @InjectMocks
    RateService systemUnderTest;
    @Mock
    RateRepository rateRepositoryMock;
    @Mock
    ModelMapper modelMapperMock;

    @Nested
    class addRatingTests {
        @Test
        void givenRateDTO_whenAddRating_thenReturnResponseObjWith200andMessage() {
            //given
            var rateDTOmock = mock(RateDTO.class);
            var rateModelMock = mock(RateModel.class);
            when(modelMapperMock.map(rateDTOmock, RateModel.class)).thenReturn(rateModelMock);
            //when
            var result = systemUnderTest.addRating(rateDTOmock);
            //then
            verify(rateRepositoryMock).save(rateModelMock);
            Assertions.assertTrue(result.getCode().equals(ResponseStatus.ACCEPTED));
            Assertions.assertTrue(result.getMessage().equals("ADDED"));
        }
    }

    @Nested
    class editRatingTests {
        @Test
        void givenExistedRate_whenEditRating_thenReturnResponseObjWith200andMessage() {
            //given
            Long id = 1L;
            var rateModelMock = mock(RateModel.class);
            var rateDTOmock = mock(RateDTO.class);
            when(rateRepositoryMock.findById(id)).thenReturn(Optional.of(rateModelMock));
            when(modelMapperMock.map(rateDTOmock, RateModel.class)).thenReturn(rateModelMock);
            when(rateRepositoryMock.save(rateModelMock)).thenReturn(rateModelMock);
            //when
            var result = systemUnderTest.editRating(id,rateDTOmock);
            //then
            verify(rateModelMock).setId(id);
            Assertions.assertTrue(result.getCode().equals(ResponseStatus.ACCEPTED));
            Assertions.assertTrue(result.getMessage().equals("EDITED"));
        }
        @Test
        void whenEditRating_thenReturnResponseObjWith400andMessage() {
            //given
            Long id = 1L;
            var rateDTOmock = mock(RateDTO.class);
            //when
            var result = systemUnderTest.editRating(id,rateDTOmock);
            //then
            Assertions.assertTrue(result.getCode().equals(ResponseStatus.BAD_REQUEST));
            Assertions.assertTrue(result.getMessage().equals("ID_NOT_EXIST"));
        }
    }

    @Nested
    class deleteRatingTests {
        @Test
        void givenExistedRate_whenDeleteRating_thenReturnResponseObjWith200andMessage() {
            //given
            Long id = 1L;
            var rateModelMock = mock(RateModel.class);
            when(rateRepositoryMock.findById(id)).thenReturn(Optional.of(rateModelMock));
            //when
            var result = systemUnderTest.deleteRating(id);
            //then
            verify(rateRepositoryMock).deleteById(id);
            Assertions.assertTrue(result.getCode().equals(ResponseStatus.ACCEPTED));
            Assertions.assertTrue(result.getMessage().equals("DELETED"));
        }
        @Test
        void whenDeleteRating_thenReturnResponseObjWith400andMessage() {
            //given
            Long id = 1L;
            //when
            var result = systemUnderTest.deleteRating(id);
            //then
            Assertions.assertTrue(result.getCode().equals(ResponseStatus.BAD_REQUEST));
            Assertions.assertTrue(result.getMessage().equals("ID_NOT_EXIST"));
        }
    }
}