package com.devh.micro.lotto.controller;

import com.devh.micro.lotto.result.ResultToJsonConverter;
import com.devh.micro.lotto.scheduler.LottoScheduler;
import com.devh.micro.lotto.scheduler.constant.LottoScheduleConstant;
import com.devh.micro.lotto.service.LottoResultService;
import com.devh.micro.lotto.service.LottoResultStoreService;
import com.devh.micro.lotto.util.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("micro-lotto")
@RequiredArgsConstructor
public class LottoController {
    private final Logger logger = LoggerFactory.getLogger(LottoController.class);

    /* DI */
    private final LottoScheduler lottoScheduler;
    private final LottoResultStoreService lottoResultStoreService;
    private final LottoResultService lottoResultService;

    @PostMapping("schedule/start")
    public ResponseEntity<Object> postScheduleStart() {
        logger.info("[POST] /micro-lotto/schedule/start");
        lottoScheduler.scheduleStart();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", getSchedulerStatusMap());
        return ResponseEntity.ok().body(resultMap);
    }

    @GetMapping("number-count-list")
    public ResponseEntity<Object> getAllNumberList() {
        logger.info("[GET] /micro-lotto/number-count-list");
        ResponseEntity<Object> result;
        ResultToJsonConverter resultToJsonConverter = ResultToJsonConverter.init();
        try {
            resultToJsonConverter.putResultMapToResultJson(lottoResultService.getLottoNumberCountDTO());
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.OK);
        } catch (Exception e) {
            ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
            resultToJsonConverter.putResultMapToExceptionInformation(e);
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    @PostMapping("aggregation/store-address")
    public ResponseEntity<Object> postAggregationStoreAddress() {
        logger.info("[GET] /micro-lotto/aggregation/store-address");
        ResponseEntity<Object> result;
        ResultToJsonConverter resultToJsonConverter = ResultToJsonConverter.init();
        try {
            resultToJsonConverter.putResultMapToResultJson(lottoResultStoreService.getTotalAggregationStoreAddressMap());
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.OK);
        } catch (Exception e) {
            ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
            resultToJsonConverter.putResultMapToExceptionInformation(e);
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;

    }

    @GetMapping("store-information/address1")
    public ResponseEntity<Object> getStoreInformationAboutAddress1() {
        logger.info("[GET] /micro-lotto/store-information/address1");
        ResponseEntity<Object> result;
        ResultToJsonConverter resultToJsonConverter = ResultToJsonConverter.init();
        try {
            resultToJsonConverter.putResultMapToResultJson(lottoResultStoreService.getAddress1StoreCountMapList());
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.OK);
        } catch (Exception e) {
            ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
            resultToJsonConverter.putResultMapToExceptionInformation(e);
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    @GetMapping("store-information/address2/{address1}")
    public ResponseEntity<Object> getStoreInformationAboutAddress2(@PathVariable("address1") String address1) {
        logger.info("[GET] /micro-lotto/store-information/address2/"+address1);
        ResponseEntity<Object> result;
        ResultToJsonConverter resultToJsonConverter = ResultToJsonConverter.init();
        try {
            resultToJsonConverter.putResultMapToResultJson(lottoResultStoreService.getAddress2StoreCountMapList(address1));
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.OK);
        } catch (Exception e) {
            ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
            resultToJsonConverter.putResultMapToExceptionInformation(e);
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    @GetMapping("store-information/address3/{address1}/{address2}")
    public ResponseEntity<Object> getStoreInformationAboutAddress3(@PathVariable("address1") String address1, @PathVariable("address2") String address2) {
        logger.info("[GET] /micro-lotto/store-information/address3/"+address1+"/"+address2);
        ResponseEntity<Object> result;
        ResultToJsonConverter resultToJsonConverter = ResultToJsonConverter.init();
        try {
            resultToJsonConverter.putResultMapToResultJson(lottoResultStoreService.getAddress3StoreDTOList(address1, address2));
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.OK);
        } catch (Exception e) {
            ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
            resultToJsonConverter.putResultMapToExceptionInformation(e);
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    private Map<LottoScheduleConstant.Status, LottoScheduleConstant.Status> getSchedulerStatusMap() {
        Map<LottoScheduleConstant.Status, LottoScheduleConstant.Status> statusMap = new HashMap<>();
        statusMap.put(LottoScheduleConstant.Status.CURRENT_STATUS, lottoScheduler.getSchedulerStatus());
        return statusMap;
    }
}
