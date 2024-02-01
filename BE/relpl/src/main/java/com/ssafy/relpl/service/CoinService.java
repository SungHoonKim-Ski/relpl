package com.ssafy.relpl.service;

import com.ssafy.relpl.db.postgre.entity.Coin;
import com.ssafy.relpl.db.postgre.entity.User;
import com.ssafy.relpl.db.postgre.repository.CoinRepository;
import com.ssafy.relpl.db.postgre.repository.UserRepository;
import com.ssafy.relpl.dto.response.CoinBarcodeResponseDto;
import com.ssafy.relpl.dto.response.CoinScoreDataResponseDto;
import com.ssafy.relpl.dto.response.CoinScoreResponseDto;
import com.ssafy.relpl.service.result.SingleResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoinService {

    private final CoinRepository coinRepository;
    private final UserRepository userRepository;
    private final ResponseService responseService;


    // 코인 지급 내역 조회 메서드
    public SingleResult<CoinScoreDataResponseDto> coinScore(Long userId) {
        log.info("여기는 서비스단이다. 코인 지급 내역을 조회한다.");

        // userId 체크
        if (userId == null) {
            // userId가 null인 경우 처리
            return responseService.getFailResult(400, "유저 정보가 없습니다.", new CoinScoreDataResponseDto());
        }

        // 등록되어 존재하는 유저인지 확인
        User existingUser = userRepository.findById(String.valueOf(userId)).orElse(null);

        if (existingUser != null) {
            // 등록되어 존재하는 유저인 경우
            // 코인 지급 내역 조회
            List<Coin> coins = coinRepository.findAllByUserUserId(userId);

            CoinScoreDataResponseDto responseDto = null;
            if (!coins.isEmpty()) {
                // 총 코인 액수 계산
                int totalCoin = 0;

                // 응답 데이터 구성
                responseDto = new CoinScoreDataResponseDto();
                List<CoinScoreResponseDto> coinScoreResponseDtos = new ArrayList<>();

                for (Coin coin : coins) {
                    totalCoin += coin.getCoinAmount();

                    CoinScoreResponseDto coinScoreResponseDto = CoinScoreResponseDto.convertFromCoin(coin);
                    coinScoreResponseDtos.add(coinScoreResponseDto);
                }

                responseDto.setUserTotalCoin(totalCoin);
                responseDto.setEventList(coinScoreResponseDtos);

                return responseService.getSingleResult(responseDto, "코인 지급 내역 조회 성공", 200);
            } else {
                // 코인 지급 내역이 없는 경우
                return responseService.getSingleResult(responseDto, "코인 지급 내역이 없습니당.", 200);
            }
        } else {
            // 등록되지 않은 유저인 경우
            return (SingleResult<CoinScoreDataResponseDto>) responseService.getFailResult(400, "등록되지 않은 유저입니다.");
        }
    }


    // coinBarcode 조회 메서드
    public SingleResult<CoinBarcodeResponseDto> coinBarcode(Long userId) {
        log.info("여기는 서비스단이다. 코인 바코드를 조회한다.");

        // userId 체크
        if (userId == null) {
            // userId가 null인 경우 처리
            return responseService.getFailResult(400, "유저 정보가 없습니다.", new CoinBarcodeResponseDto());
        }

        // 등록되어 존재하는 유저인지 확인
        User existingUser = userRepository.findById(String.valueOf(userId)).orElse(null);

        if (existingUser != null) {
            // 등록되어 존재하는 유저인 경우
            // 코인 지급 내역 조회
            List<Coin> coins = coinRepository.findAllByUserUserId(userId);

            // 총 코인 액수 계산
            int totalCoin = 0;
            for (Coin coin : coins) {
                totalCoin += coin.getCoinAmount();
            }

            // 응답 데이터 구성
            CoinBarcodeResponseDto responseDto = new CoinBarcodeResponseDto();
            responseDto.setUserTotalCoin(totalCoin);

            return responseService.getSingleResult(responseDto, "바코드 조회 성공", 200);
        } else {
            // 등록되지 않은 유저인 경우
            return (SingleResult<CoinBarcodeResponseDto>) responseService.getFailResult(400, "등록되지 않은 유저입니다.");
        }
    }
}