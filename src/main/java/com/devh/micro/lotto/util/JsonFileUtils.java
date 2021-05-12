package com.devh.micro.lotto.util;

import com.devh.micro.lotto.constant.LottoConstant;
import com.devh.micro.lotto.dto.LottoResultDTO;
import com.devh.micro.lotto.dto.LottoResultDetailDTO;
import com.devh.micro.lotto.dto.LottoResultStoreDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * <pre>
 * Description : 
 *     JSON 객체를 파일로 저장하기 위한 유틸
 * ===============================================
 * Member fields : 
 *     
 * ===============================================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021-04-10
 * </pre>
 */
@SuppressWarnings("unchecked")
public class JsonFileUtils {
    private final Logger logger = LoggerFactory.getLogger(JsonFileUtils.class);

    private final String JSON_FILE_PATH = "lotto_json";
    private final String FILE_EXT = ".json";

    private final String fourDigitIntegerWithZeroFillFormat = "%04d";

    /* Singleton */
    private static JsonFileUtils instance;
    public static JsonFileUtils getInstance() {
        if(instance == null)
            instance = new JsonFileUtils();
        return instance;
    }

    /**
     * <pre>
     * Description
     *     LottoResultDTO 객체를 JSON 객체로 변환
     * ===============================================
     * Parameters
     *     LottoResultDTO lottoResultDTO
     * Returns
     *     JSONObject
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-04-10
     * </pre>
     */
    private JSONObject lottoResultDTOToJSONObject(LottoResultDTO lottoResultDTO) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(LottoConstant.LottoResultKey.TURN.getSnakeCase(), lottoResultDTO.getTurn());
        jsonObject.put(LottoConstant.LottoResultKey.DATE.getSnakeCase(), lottoResultDTO.getDate());
        jsonObject.put(LottoConstant.LottoResultKey.NUMBER1.getSnakeCase(), lottoResultDTO.getNumber1());
        jsonObject.put(LottoConstant.LottoResultKey.NUMBER2.getSnakeCase(), lottoResultDTO.getNumber2());
        jsonObject.put(LottoConstant.LottoResultKey.NUMBER3.getSnakeCase(), lottoResultDTO.getNumber3());
        jsonObject.put(LottoConstant.LottoResultKey.NUMBER4.getSnakeCase(), lottoResultDTO.getNumber4());
        jsonObject.put(LottoConstant.LottoResultKey.NUMBER5.getSnakeCase(), lottoResultDTO.getNumber5());
        jsonObject.put(LottoConstant.LottoResultKey.NUMBER6.getSnakeCase(), lottoResultDTO.getNumber6());
        jsonObject.put(LottoConstant.LottoResultKey.NUMBER7.getSnakeCase(), lottoResultDTO.getNumber7());
        jsonObject.put(LottoConstant.LottoResultKey.TOTAL_SALES_PRICE.getSnakeCase(), lottoResultDTO.getTotalSalesPrice());
        jsonObject.put(LottoConstant.LottoResultKey.AUTO_WINNER_COUNT.getSnakeCase(), lottoResultDTO.getAutoWinnerCount());
        jsonObject.put(LottoConstant.LottoResultKey.SEMI_AUTO_WINNER_COUNT.getSnakeCase(), lottoResultDTO.getSemiAutoWinnerCount());
        jsonObject.put(LottoConstant.LottoResultKey.MANUAL_WINNER_COUNT.getSnakeCase(), lottoResultDTO.getManualWinnerCount());
        return jsonObject;
    }

    /**
     * <pre>
     * Description
     *     LottoResultDetailDTO 객체를 JSON 객체로 변환
     * ===============================================
     * Parameters
     *     LottoResultDetailDTO lottoResultDetailDTO
     * Returns
     *     JSONObject
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-04-10
     * </pre>
     */
    private JSONObject lottoResultDetailDTOToJSONObject(LottoResultDetailDTO lottoResultDetailDTO) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(LottoConstant.LottoResultDetailKey.LOTTO_RESULT_TURN.getSnakeCase(), lottoResultDetailDTO.getTurn());
        jsonObject.put(LottoConstant.LottoResultDetailKey.RANK.getSnakeCase(), lottoResultDetailDTO.getRank());
        jsonObject.put(LottoConstant.LottoResultDetailKey.TOTAL_WINNER_COUNT.getSnakeCase(), lottoResultDetailDTO.getTotalWinnerCount());
        jsonObject.put(LottoConstant.LottoResultDetailKey.TOTAL_PRIZE.getSnakeCase(), lottoResultDetailDTO.getTotalPrize());
        jsonObject.put(LottoConstant.LottoResultDetailKey.PER_PERSON_PRIZE.getSnakeCase(), lottoResultDetailDTO.getPerPersonPrize());
        return jsonObject;
    }

    /**
     * <pre>
     * Description
     *     LottoResultStoreDTO 객체를 JSON 객체로 변환
     * ===============================================
     * Parameters
     *     LottoResultStoreDTO lottoResultStoreDTO
     * Returns
     *     JSONObject
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-04-10
     * </pre>
     */
    private JSONObject lottoResultStoreDTOToJSONObject(LottoResultStoreDTO lottoResultStoreDTO) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(LottoConstant.LottoResultStoreKey.ROW_ID.getSnakeCase(), lottoResultStoreDTO.getRowId());
        jsonObject.put(LottoConstant.LottoResultStoreKey.TURN.getSnakeCase(), lottoResultStoreDTO.getTurn());
        jsonObject.put(LottoConstant.LottoResultStoreKey.RANK.getSnakeCase(), lottoResultStoreDTO.getRank());
        jsonObject.put(LottoConstant.LottoResultStoreKey.METHOD.getSnakeCase(), lottoResultStoreDTO.getMethod());
        jsonObject.put(LottoConstant.LottoResultStoreKey.STORE_NUMBER.getSnakeCase(), lottoResultStoreDTO.getStoreNumber());
        jsonObject.put(LottoConstant.LottoResultStoreKey.STORE_NAME.getSnakeCase(), lottoResultStoreDTO.getStoreName());
        jsonObject.put(LottoConstant.LottoResultStoreKey.STORE_PHONE.getSnakeCase(), lottoResultStoreDTO.getStorePhone());
        jsonObject.put(LottoConstant.LottoResultStoreKey.STORE_ADDRESS.getSnakeCase(), lottoResultStoreDTO.getStoreAddress());
        jsonObject.put(LottoConstant.LottoResultStoreKey.STORE_ADDRESS1.getSnakeCase(), lottoResultStoreDTO.getStoreAddress1());
        jsonObject.put(LottoConstant.LottoResultStoreKey.STORE_ADDRESS2.getSnakeCase(), lottoResultStoreDTO.getStoreAddress2());
        jsonObject.put(LottoConstant.LottoResultStoreKey.STORE_ADDRESS3.getSnakeCase(), lottoResultStoreDTO.getStoreAddress3());
        jsonObject.put(LottoConstant.LottoResultStoreKey.STORE_MAP_ID.getSnakeCase(), lottoResultStoreDTO.getStoreMapId());
        JSONObject location = new JSONObject();
        location.put(LottoConstant.LottoResultStoreKey.STORE_LOCATION_LAT.getSnakeCase(), lottoResultStoreDTO.getStoreLatitude());
        location.put(LottoConstant.LottoResultStoreKey.STORE_LOCATION_LON.getSnakeCase(), lottoResultStoreDTO.getStoreLongitude());
        jsonObject.put(LottoConstant.LottoResultStoreKey.STORE_LOCATION.getSnakeCase(), location);
        return jsonObject;
    }

    /**
     * <pre>
     * Description
     *     LottoResultDTO를 JSON 파일로 저장
     * ===============================================
     * Parameters
     *     LottoResultDTO lottoResultDTO
     * Returns
     *     void
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-04-10
     * </pre>
     */
    public void createLottoResultJsonFile(LottoResultDTO lottoResultDTO) {
        JSONObject json = lottoResultDTOToJSONObject(lottoResultDTO);
        String RESULT_FILE_PATH = "result";
        final String filePath = JSON_FILE_PATH + File.separator + RESULT_FILE_PATH;
        final String fileName = String.format(fourDigitIntegerWithZeroFillFormat, lottoResultDTO.getTurn()) + FILE_EXT;
        writeContentToFile(filePath, fileName, json.toJSONString());
    }

    /**
     * <pre>
     * Description
     *     LottoResultDetail 리스트를 JSON 파일로 저장
     * ===============================================
     * Parameters
     *     List<LottoResultDetailDTO> lottoResultDetailDTOList
     * Returns
     *     void
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-04-10
     * </pre>
     */
    public void createLottoResultDetailListJsonFile(List<LottoResultDetailDTO> lottoResultDetailDTOList) {
        JSONArray json = new JSONArray();
        lottoResultDetailDTOList.forEach(lottoResultDetailDTO -> json.add(lottoResultDetailDTOToJSONObject(lottoResultDetailDTO)));
        String DETAIL_FILE_PATH = "detail";
        final String filePath = JSON_FILE_PATH + File.separator + DETAIL_FILE_PATH;
        final String fileName = String.format(fourDigitIntegerWithZeroFillFormat, lottoResultDetailDTOList.get(0).getTurn()) + FILE_EXT;
        writeContentToFile(filePath, fileName, json.toJSONString());
    }

    /**
     * <pre>
     * Description
     *     LottoResultStore 리스트를 JSON 파일로 저장
     * ===============================================
     * Parameters
     *     List<LottoResultStoreDTO> lottoResultStoreDTOList
     * Returns
     *     void
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-04-10
     * </pre>
     */
    public void createLottoResultStoreListJsonFile(List<LottoResultStoreDTO> lottoResultStoreDTOList) {
        JSONArray json = new JSONArray();
        lottoResultStoreDTOList.forEach(lottoResultStoreDTO -> json.add(lottoResultStoreDTOToJSONObject(lottoResultStoreDTO)));
        String STORE_FILE_PATH = "store";
        final String filePath = JSON_FILE_PATH + File.separator + STORE_FILE_PATH;
        final String fileName = String.format(fourDigitIntegerWithZeroFillFormat, lottoResultStoreDTOList.get(0).getTurn()) + FILE_EXT;
        writeContentToFile(filePath, fileName, json.toJSONString());
    }


    /**
     * <pre>
     * Description
     *     파일경로, 파일명, 파일에 쓸 내용을 전달하여 파일을 생성
     * ===============================================
     * Parameters
     *     String filePath
     *     String fileName
     *     String content
     * Returns
     *     void
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-04-10
     * </pre>
     */
    private void writeContentToFile(String filePath, String fileName, String content) {
        File dir = new File(filePath);
        if(!dir.exists())
            dir.mkdirs();

        File resultFile = new File(filePath + File.separator + fileName);

        try (
                FileWriter fileWriter = new FileWriter(resultFile)
        ) {
            fileWriter.write(content);
            fileWriter.flush();
        } catch (IOException e) {
            ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
        }
    }
}
