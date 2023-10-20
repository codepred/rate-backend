package codepred.rate.service;

import codepred.enums.ResponseStatus;
import codepred.rate.dto.RateDto;
import codepred.rate.dto.ResponseObj;
import codepred.rate.model.RateModel;
import codepred.rate.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static codepred.enums.ResponseStatus.ACCEPTED;
import static codepred.enums.ResponseStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class RateService {

    private final RateRepository rateRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ResponseObj addRating(RateDto rateDto) {
        var rateModel = modelMapper.map(rateDto, RateModel.class);
        rateRepository.save(rateModel);
        return new ResponseObj(ACCEPTED, "ADDED");
    }

    @Transactional
    public ResponseObj editRating(Long id, RateDto rateDTO) {
        if (isIdExist(id)) {
            var rateModel = modelMapper.map(rateDTO, RateModel.class);
            rateModel.setId(id);
            rateRepository.save(rateModel);
            return new ResponseObj(ACCEPTED, "EDITED");
        } else {
            return new ResponseObj(BAD_REQUEST, "ID_NOT_EXIST");
        }
    }

    @Transactional
    public ResponseObj deleteRating(Long id) {
        if (isIdExist(id)) {
            rateRepository.deleteById(id);
            return new ResponseObj(ACCEPTED, "DELETED");
        } else {
            return new ResponseObj(BAD_REQUEST, "ID_NOT_EXIST");
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

    private boolean isIdExist(Long id) {
        return rateRepository.findById(id).isPresent();
    }
}
