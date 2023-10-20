package codepred.rate.service;

import codepred.UnitTest;
import codepred.enums.ResponseStatus;
import codepred.rate.dto.RateDto;
import codepred.rate.dto.ResponseObj;
import codepred.rate.model.RateModel;
import codepred.rate.repository.RateRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RateServiceTest implements UnitTest {

    @InjectMocks
    private RateService systemUnderTest;

    @Mock
    private RateRepository rateRepositoryMock;

    @Mock
    private ModelMapper modelMapperMock;

    @Nested
    class AddRatingTests {

        @Test
        void shouldAddRating() {
            var rateDtoMock = mock(RateDto.class);
            var rateModelMock = mock(RateModel.class);
            when(modelMapperMock.map(rateDtoMock, RateModel.class)).thenReturn(rateModelMock);

            var result = systemUnderTest.addRating(rateDtoMock);

            verify(rateRepositoryMock).save(rateModelMock);
            assertEquals(ResponseStatus.ACCEPTED, result.getCode());
            assertEquals("ADDED", result.getMessage());
        }
    }

    @Nested
    class EditRatingTests {
        private final Long id = 1L;
        private final RateModel rateModelMock = mock(RateModel.class);
        private final RateDto rateDtoMock = mock(RateDto.class);

        @Test
        void shouldEditRating() {
            when(rateRepositoryMock.findById(id)).thenReturn(Optional.of(rateModelMock));
            when(modelMapperMock.map(rateDtoMock, RateModel.class)).thenReturn(rateModelMock);
            when(rateRepositoryMock.save(rateModelMock)).thenReturn(rateModelMock);

            var result = callService();

            verify(rateModelMock).setId(id);
            assertEquals(ResponseStatus.ACCEPTED, result.getCode());
            assertEquals("EDITED", result.getMessage());
        }

        @Test
        void shouldNotEditRatingWhenIdNotExist() {
            var result = callService();

            assertEquals(ResponseStatus.BAD_REQUEST, result.getCode());
            assertEquals("ID_NOT_EXIST", result.getMessage());
        }

        private ResponseObj callService() {
            return systemUnderTest.editRating(id, rateDtoMock);
        }
    }

    @Nested
    class DeleteRatingTests {
        private final RateModel rateModelMock = mock(RateModel.class);

        @Test
        void shouldDeleteRating() {
            final var id = 1L;
            when(rateRepositoryMock.findById(id)).thenReturn(Optional.of(rateModelMock));

            var result = callService(id);

            verify(rateRepositoryMock).deleteById(id);
            assertEquals(ResponseStatus.ACCEPTED, result.getCode());
            assertEquals("DELETED", result.getMessage());
        }

        @Test
        void shouldNotDeleteRatingWhenIdNotExist() {
            var result = systemUnderTest.deleteRating(null);

            assertEquals(ResponseStatus.BAD_REQUEST, result.getCode());
            assertEquals("ID_NOT_EXIST", result.getMessage());
        }

        private ResponseObj callService(Long id) {
            return systemUnderTest.deleteRating(id);
        }
    }
}