package com.devh.micro.lotto.service;

import com.devh.micro.lotto.dto.LottoNumberCountDTO;
import com.devh.micro.lotto.dto.LottoResultAllNumberDTO;
import com.devh.micro.lotto.dto.LottoResultDTO;
import com.devh.micro.lotto.entity.LottoResult;
import com.devh.micro.lotto.projection.LottoResultNumberArrayProjection;
import com.devh.micro.lotto.repository.LottoResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Description :
 *     LottoResultService 구현체
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-02-28
 * </pre>
 */
@Service
@RequiredArgsConstructor
public class LottoResultServiceImpl implements LottoResultService {
    /* DI */
    private final LottoResultRepository lottoResultRepository;

    @Override
    public Boolean saveDTO(LottoResultDTO lottoResultDTO) {
        LottoResult lottoResult = lottoResultRepository.save(dtoToEntity(lottoResultDTO));
        return lottoResult.getTurn().intValue() == lottoResultDTO.getTurn().intValue();
    }

    @Override
    public Integer getLatestTurn() {
        LottoResult lottoResult = lottoResultRepository.findFirstByOrderByTurnDesc();
        return lottoResult == null ? 0 : lottoResult.getTurn();
    }

    @Override
    public LottoNumberCountDTO getLottoNumberCountDTO() {
        List<LottoResultAllNumberDTO> lottoResultAllNumberDTOList = getLottoResultDTOWithAllNumber();

        Map<Integer, Integer> numberCountMap = new HashMap<>();

        lottoResultAllNumberDTOList.forEach(lottoResultAllNumberDTO -> {
            String allNumber = lottoResultAllNumberDTO.getAllNumber();
            String[] numberArray = allNumber.split(",");

            for(String number : numberArray) {
                int intNumber = Integer.parseInt(number);
                Integer numberCount = numberCountMap.get(intNumber);

                if(numberCount == null)
                    numberCountMap.put(intNumber, 1);
                else
                    numberCountMap.put(intNumber, ++numberCount);

            }

        });

        return LottoNumberCountDTO.builder()
                .numberCountData(numberCountMap)
                .build();
    }

    /**
     * <pre>
     * Description
     *     ,로 연결된 모든 숫자 문자열과 회차정보를 담는 DTO 리스트 반환
     * ===============================================
     * Parameters
     *
     * Returns
     *     List<LottoResultAllNumberDTO>
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021/04/01
     * </pre>
     */
    @Override
    public List<LottoResultAllNumberDTO> getLottoResultDTOWithAllNumber() {
        List<LottoResultNumberArrayProjection> numberArrayProjectionList = lottoResultRepository.findAllBy();

        List<LottoResultAllNumberDTO> result = new ArrayList<>();

        numberArrayProjectionList.forEach(projection -> {
            result.add(
                    LottoResultAllNumberDTO.builder()
                            .turn(Integer.parseInt(projection.getTurn()))
                            .allNumber(projection.getAllNumber())
                            .build()
            );
        });

        return result;
    }
}
