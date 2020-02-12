package io.cryptex.ms.wrapper.ripple.service;

import io.cryptex.ms.wrapper.ripple.exception.InnerServiceException;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.properties.RippleBlockchainProperties;
import io.cryptex.ms.wrapper.ripple.integration.properties.SignatureProperties;
import io.cryptex.ms.wrapper.ripple.integration.properties.WalletProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SignatureServiceImpl implements SignatureService {

    private static final String NODE = "/usr/local/bin/node";
    private final RippleBlockchainProperties rippleBlockchainProperties;
    private final WalletProperties walletProperties;
    private final SignatureProperties signatureProperties;
    private final ReentrantLock reentrantLock = new ReentrantLock();

    @Override
    public String signTransaction(String to, Double amount, String memo) {
        reentrantLock.lock();
        try {
            String[] args = {NODE, signatureProperties.getFileForSigning(), rippleBlockchainProperties.getMethod().getSign(),
                    walletProperties.getUri(), walletProperties.getAccount(), to, amount.toString(), memo, walletProperties.getSecretKey()};

            log.info("Signing transaction | to : {} , amount : {} , memo: {}", to, amount.toString(), memo);

            Process process = Runtime.getRuntime().exec(args);

//            if (!process.waitFor(signatureProperties.getTimeout(), TimeUnit.SECONDS)) {
//                String cliExecCommand = String.format("Timeout while ripple sign-transaction execution: command = %s.", Arrays.toString(args));
//                log.error(cliExecCommand);
//                process.destroy();
//                throw new InnerServiceException(cliExecCommand);
//            }

            try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                 BufferedReader errorInput = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

                String error = errorInput.lines().collect(Collectors.joining());
                if (StringUtils.isNotBlank(error)) {
                    String errorInputMessage = String.format("Command %s throw Error: %s", Arrays.toString(args), error);
                    log.error(errorInputMessage);
                    throw new InnerServiceException(errorInputMessage);
                }
                String signature = input.lines().collect(Collectors.joining());
                log.info("Transaction signed | signature : {}", signature);

                return signature;
            }
        } catch (Exception  e) {
            log.error("Error while calling for sign-transaction.js | Message : {}", e.getMessage());
            throw new InnerServiceException(e.getMessage());
        } finally {
            reentrantLock.unlock();
        }
    }
}
