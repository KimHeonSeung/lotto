package com.devh.micro.lotto.scheduler;

import com.devh.micro.lotto.constant.LottoConstant;
import com.devh.micro.lotto.dto.LottoResultDTO;
import com.devh.micro.lotto.dto.LottoResultDetailDTO;
import com.devh.micro.lotto.dto.LottoResultStoreDTO;
import com.devh.micro.lotto.scheduler.constant.LottoScheduleConstant;
import com.devh.micro.lotto.service.LottoResultDetailService;
import com.devh.micro.lotto.service.LottoResultService;
import com.devh.micro.lotto.service.LottoResultStoreService;
import com.devh.micro.lotto.util.ExceptionUtils;
import com.devh.micro.lotto.util.JsonFileUtils;
import com.devh.micro.lotto.util.JsoupUtils;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <pre>
 * Description :
 *     로또 정보를 수집하는 스케줄러
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-02-28
 * </pre>
 */
@Component
@RequiredArgsConstructor
public class LottoScheduler {

    /* 스케줄 스레드 */
    private Thread mScheduleThread;

    /* DI */
    private final LottoResultService lottoResultService;
    private final LottoResultDetailService lottoResultDetailService;
    private final LottoResultStoreService lottoResultStoreService;
    private final JsoupUtils jsoupUtils;
    private final LottoParser lottoParser;

    /* 홈페이지에 빈 데이터가 먼저 올라오는 문제로 임시 조치 */
    @Scheduled(cron = "0/30 45-59 21 * * 6")
    public void scheduleStart() {
        if(mScheduleThread == null || !mScheduleThread.isAlive()) {
            mScheduleThread = new Thread(new LottoSchedulerThread());
            mScheduleThread.start();
        }
    }

    public LottoScheduleConstant.Status getSchedulerStatus() {
        return mScheduleThread == null || !mScheduleThread.isAlive() ?
                LottoScheduleConstant.Status.NOT_RUNNING :
                LottoScheduleConstant.Status.RUNNING;
    }
    
    /**
     * <pre>
     * Description : 
     *     실제 데이터를 파싱 및 적재하는 이너 클래스 스레드
     * ===============================================
     * Member fields : 
     *     
     * ===============================================
     * 
     * Author : HeonSeung Kim
     * Date   : 2021-04-10
     * </pre>
     */
    class LottoSchedulerThread implements Runnable {
        private final Logger logger = LoggerFactory.getLogger(LottoSchedulerThread.class);

        @Override
        public void run() {
            try {

                boolean isLatest = false;

                while(!isLatest) {
                    /* DB 최신 회차 */
                    final Integer latestTurnInDB = lottoResultService.getLatestTurn();

                    /* 웹 페이지 최신 회차 */
                    final Document latestDocument = jsoupUtils.getDocumentFromURL(LottoConstant.LottoURL.LATEST.getUrl());
                    if(latestDocument == null) {
                        logger.error("Schedule will stop. Cause : Failed to get latest turn.");
                        break;
                    }
                    final Integer latestTurnInWeb = lottoParser.getTurnFromDocument(latestDocument);
                    if(latestTurnInDB.intValue() == latestTurnInWeb.intValue()) {
                        logger.warn("Database Information is up to date.");
                        break;
                    }

                    /* 파싱할 회차 */
                    final int targetTurn = latestTurnInDB + 1;
                    final String targetUrl = LottoConstant.LottoURL.PAST_PREFIX.getUrl() + targetTurn;
                    final Document targetDocument = jsoupUtils.getDocumentFromURL(targetUrl);
                    if(targetDocument == null) {
                        logger.error("Schedule will stop. Cause : Failed to get target turn. - " + targetTurn);
                        break;
                    }
                    LottoResultDTO lottoResultDTO = lottoParser.getLottoResultDTOFromDocument(targetDocument);

                    List<LottoResultDetailDTO> lottoResultDetailDTOList = lottoParser.getLottoResultDetailDTOListFromURL(targetDocument);
                    if(lottoResultDetailDTOList == null || lottoResultDetailDTOList.size() == 0) {
                        logger.error("Schedule will stop. Cause : Failed to parse LottoResultDetailDTO list. - " + targetUrl);
                        break;
                    }

                    lottoResultService.saveDTO(lottoResultDTO);
                    lottoResultDetailService.saveDTOList(lottoResultDetailDTOList);

                    List<LottoResultStoreDTO> lottoResultStoreList = lottoParser.getLottoResultStoreDTOListFromTurn(targetTurn);
                    if(lottoResultStoreList != null && lottoResultStoreList.size() > 0) {
                        boolean bulkResult = lottoResultStoreService.bulkLottoResultStoreDTOList(lottoResultStoreList);
                        logger.info("bulkRequest : " + bulkResult);
                        JsonFileUtils.getInstance().createLottoResultStoreListJsonFile(lottoResultStoreList);
                    }

                    JsonFileUtils.getInstance().createLottoResultJsonFile(lottoResultDTO);
                    JsonFileUtils.getInstance().createLottoResultDetailListJsonFile(lottoResultDetailDTOList);
                    logger.info(targetTurn + " : Success to save.");

                    try {Thread.sleep(500L);} catch (InterruptedException ignored) {}
                }

            } catch (Exception e) {
                ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
            }
        }
    }
}
