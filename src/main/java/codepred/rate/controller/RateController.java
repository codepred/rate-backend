package codepred.rate.controller;

import codepred.enums.ResponseStatus;
import codepred.rate.dto.RateDTO;
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
    public ResponseEntity<Object> addRating(@RequestBody RateDTO rateDTO) {
        if(isNullName(rateDTO)){
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
    public ResponseEntity<Object> editRating(@RequestBody Long id, RateDTO rateDTO) {
        if(isNullName(rateDTO)){
            return ResponseEntity.status(rateService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "NAME_IS_NULL"));
        }
        if(isNullId(id)){
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
    public ResponseEntity<Object> deleteRating(@RequestBody Long id) {
        if(isNullId(id)){
            return ResponseEntity.status(rateService.getResponseCode(ResponseStatus.BAD_REQUEST))
                    .body(new ResponseObj(ResponseStatus.BAD_REQUEST, "ID_IS_NULL"));
        }
        return ResponseEntity.ok(rateService.deleteRating(id));
    }

    private boolean isNameBlank(RateDTO rateDTO) {
        return rateDTO.getProductName().isBlank();
    }
    private boolean isNullName(RateDTO rateDTO){
        return rateDTO.getProductName() == null;
    }
    private boolean isNullId(Long id){
        return id == null;
    }
}
