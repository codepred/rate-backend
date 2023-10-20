package codepred.rate.controller;

import codepred.enums.ResponseStatus;
import codepred.rate.dto.RateDto;
import codepred.rate.dto.ResponseObj;
import codepred.rate.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;

    @PostMapping("/add")
    ResponseEntity<Object> addRating(@RequestBody RateDto rateDTO) {
        if (isNullName(rateDTO)) {
            return ResponseEntity.status(rateService.getResponseCode(ResponseStatus.BAD_REQUEST))
                .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_NULL"));
        }
        if (isNameBlank(rateDTO)) {
            return ResponseEntity.status(rateService.getResponseCode(ResponseStatus.BAD_REQUEST))
                .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_BLANK"));
        }
        return ResponseEntity.ok(rateService.addRating(rateDTO));
    }

    @PutMapping("/edit/{id}")
    ResponseEntity<Object> editRating(@PathVariable("id") Long id, @RequestBody RateDto rateDTO) {
        if (isNullName(rateDTO)) {
            return ResponseEntity.status(rateService.getResponseCode(ResponseStatus.BAD_REQUEST))
                .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_NULL"));
        }
        if (isNullId(id)) {
            return ResponseEntity.status(rateService.getResponseCode(ResponseStatus.BAD_REQUEST))
                .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "ID_IS_NULL"));
        }
        if (isNameBlank(rateDTO)) {
            return ResponseEntity.status(rateService.getResponseCode(ResponseStatus.BAD_REQUEST))
                .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_BLANK"));
        }
        return ResponseEntity.ok(rateService.editRating(id, rateDTO));
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Object> deleteRating(@PathVariable("id") Long id) {
        if (isNullId(id)) {
            return ResponseEntity.status(rateService.getResponseCode(ResponseStatus.BAD_REQUEST))
                .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "ID_IS_NULL"));
        }
        return ResponseEntity.ok(rateService.deleteRating(id));
    }

    private boolean isNameBlank(RateDto rateDTO) {
        return rateDTO.getProductName().isBlank();
    }

    private boolean isNullName(RateDto rateDTO) {
        return rateDTO.getProductName() == null;
    }

    private boolean isNullId(Long id) {
        return id == null;
    }
}
