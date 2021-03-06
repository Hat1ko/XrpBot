package com.hatiko.ripple.wrapper.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.hatiko.ripple.wrapper.constants.TransactionType;
import com.hatiko.ripple.wrapper.converter.RippleBalanceConverter;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.RippleTransactionsResponse;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.TransactionBody;
import com.hatiko.ripple.wrapper.integration.cryptopayment.dto.LastProcessedTransactionData;
import com.hatiko.ripple.wrapper.web.model.TransactionResponse;

public class TransactionSchedulerUtil {

    public static final String ACCOUNT_INFO_METHOD = "account_info";

    public static final String RESULT_ACCOUNT = "r4AQFNpxUbEYgpG7t8GsUHQpkY4JcNKZRC";
    public static final Long RESULT_LEDGER_INDEX_MIN = 2262362L;
    public static final Long RESULT_LEDGER_INDEX_MAX = 1358266L;
    public static final String RESULT_STATUS = "success";

    public static final String EXPECTED_ACCOUNT = RESULT_ACCOUNT;
    public static final Long LAST_PROCESSED_SEQUENCE = 20L;
    public static final Long LAST_ACTUAL_ACCOUNT_SEQUENCE = 33L;

    public static final String TX1_ACCOUNT = "r4AQFNpxUbEYgpG7t8GsUHQpkY4JcNKZRC";
    public static final String TX1_AMOUNT = "15000000";
    public static final String TX1_DESTINATION = "rLBeWGaYFKQ1AeszPyB9AH8YfNXAcq97xB";
    public static final String TX1_FEE = "10";
    public static final String TX1_MEMO = "CA9FB5FE289215AD54B9D448E45C34C17939565D5B6CD872238BF19A5A4DB395";
    public static final String TX1_MEMO_DATA = "43413946423546453238393231354144353442394434343845343543333443313739333935363544354236434438373232333842463139413541344442333935";
    public static final Long TX1_SEQUENCE = 25L;
    public static final String TX1_SIGNING_PUB_KEY = "0296BFE89289E1CA79B0A0C602CBB0207CEC7DF05B4BE142E54354DF0A3002F889";
    public static final String TX1_TRANSACTION_TYPE = "Payment";
    public static final String TX1_TXN_SIGNATURE = "3044022029E9B6CEF8AB0FBF8B148EC720088576F6B181A136B4E4FF76CA4CB4A05A884D02205A7C9486B680391D155606E7C44292CFF228AE52C3E31BE943F5F9C1D60CF18D";
    public static final Long TX1_DATE = 627574340L;
    public static final String TX1_HASH = "14E99BAE7D0AA4CEBBC6A3B83DB8C88ED77A4FB1B10D43B43D13ED1C4658B64E";
    public static final Boolean TX1_VALIDATED = true;

    public static final String TX2_ACCOUNT = "r4AQFNpxUbEYgpG7t8GsUHQpkY4JcNKZRC";
    public static final String TX2_AMOUNT = "15000000";
    public static final String TX2_DESTINATION = "rLBeWGaYFKQ1AeszPyB9AH8YfNXAcq97xB";
    public static final String TX2_FEE = "10";
    public static final String TX2_MEMO = "982F0512236547C62CDF6A982ADADA378E52D51852437C5715E7DA672E5A658F";
    public static final String TX2_MEMO_DATA = "39383246303531323233363534374336324344463641393832414441444133373845353244353138353234333743353731354537444136373245354136353846";
    public static final Long TX2_SEQUENCE = 24L;
    public static final String TX2_SIGNING_PUB_KEY = "0296BFE89289E1CA79B0A0C602CBB0207CEC7DF05B4BE142E54354DF0A3002F889";
    public static final String TX2_TRANSACTION_TYPE = "Payment";
    public static final String TX2_TXN_SIGNATURE = "30450221009C8D2ABE902A9C6D4503AC0BDECD9A83657CF64B5F72A69796CF35478049E55F02203A4836E954C55EAAA82403EDDB4DD931FA7D5722A17C66AFD6F76BA7762751EE";
    public static final Long TX2_DATE = 627565182L;
    public static final String TX2_HASH = "196F5FC5C4DF52E7E59A0D386A2C65D4552D838EF53C1F3CA49D98484BAFDD6C";
    public static final Boolean TX2_VALIDATED = true;

    public static final String TX3_ACCOUNT = "r4AQFNpxUbEYgpG7t8GsUHQpkY4JcNKZRC";
    public static final String TX3_AMOUNT = "15000000";
    public static final String TX3_DESTINATION = "rLBeWGaYFKQ1AeszPyB9AH8YfNXAcq97xB";
    public static final String TX3_FEE = "10";
    public static final String TX3_MEMO = "1F700C9AB093B3B21678879FE857B20B95CCF8D76DD8508A369D4274A8DBA41F";
    public static final String TX3_MEMO_DATA = "31463730304339414230393342334232313637383837394645383537423230423935434346384437364444383530384133363944343237344138444241343146";
    public static final Long TX3_SEQUENCE = 23L;
    public static final String TX3_SIGNING_PUB_KEY = "0296BFE89289E1CA79B0A0C602CBB0207CEC7DF05B4BE142E54354DF0A3002F889";
    public static final String TX3_TRANSACTION_TYPE = "Payment";
    public static final String TX3_TXN_SIGNATURE = "3045022100BF3FE439AE710F2F731B4EAFDBC31E9CFF1BE72EAE9B153F017E44021D37EFD002207BBA9D43A33EF881B7FE880A27E78A839D7558A8D8AFD38EC7C6745E8808E2F8";
    public static final Long TX3_DATE = 627466110L;
    public static final String TX3_HASH = "68E2EC641902F47B24985B33DDD7DF650B81B5A25A7E7FF6A5B6AE42C4EEE8FA";
    public static final Boolean TX3_VALIDATED = true;

    public static final String TX4_ACCOUNT = "r4AQFNpxUbEYgpG7t8GsUHQpkY4JcNKZRC";
    public static final String TX4_AMOUNT = "23000000";
    public static final String TX4_DESTINATION = "rLBeWGaYFKQ1AeszPyB9AH8YfNXAcq97xB";
    public static final String TX4_FEE = "10";
    public static final String TX4_MEMO = "489E2F17F4F24371D88FC36E31CCB08959EDCECDE13E2C4DB055B6F312F6E74E";
    public static final String TX4_MEMO_DATA = "34383945324631374634463234333731443838464333364533314343423038393539454443454344453133453243344442303535423646333132463645373445";
    public static final Long TX4_SEQUENCE = 22L;
    public static final String TX4_SIGNING_PUB_KEY = "0296BFE89289E1CA79B0A0C602CBB0207CEC7DF05B4BE142E54354DF0A3002F889";
    public static final String TX4_TRANSACTION_TYPE = "Payment";
    public static final String TX4_TXN_SIGNATURE = "304402200F1D82FF27F7AA5F6DD1C71B536DFFFBFBAD46DCED5F781840CD1B7DEA9537EB02205235DBDBA4FF616F21531B7AC6D87FFC1B4DBD21EAB285C22E243B60C0F204D2";
    public static final Long TX4_DATE = 627463932L;
    public static final String TX4_HASH = "209249EDD0B031FD3E001DD4E742680C41BBD2F11F649046F90C5A1BFD4AB8A9";
    public static final Boolean TX4_VALIDATED = true;

    public static final String TX5_ACCOUNT = "r4AQFNpxUbEYgpG7t8GsUHQpkY4JcNKZRC";
    public static final String TX5_AMOUNT = "150000000";
    public static final String TX5_DESTINATION = "rLBeWGaYFKQ1AeszPyB9AH8YfNXAcq97xB";
    public static final String TX5_FEE = "10";
    public static final String TX5_MEMO = "4BDE6F9BE08A61CCF3ACABE79D64EC573FCD517FC40EC49EA4B9D1DC2B3558D1";
    public static final String TX5_MEMO_DATA = "34424445364639424530384136314343463341434142453739443634454335373346434435313746433430454334394541344239443144433242333535384431";
    public static final Long TX5_SEQUENCE = 21L;
    public static final String TX5_SIGNING_PUB_KEY = "0296BFE89289E1CA79B0A0C602CBB0207CEC7DF05B4BE142E54354DF0A3002F889";
    public static final String TX5_TRANSACTION_TYPE = "Payment";
    public static final String TX5_TXN_SIGNATURE = "304402204C9E2A6C4C8ECC77DF326091087ED2BAE0FC5C4A9A53E2E05E863E0016F4E710022076D0FAE69A27C4DFA574B7A93AAEE5D8EC38B46C89BCEE28C81A0EF1E934EE08";
    public static final Long TX5_DATE = 627029813L;
    public static final String TX5_HASH = "2011F46987F95B6125FD011036A758F8EC93A1AA4ADEE4835B4B936EAB2FDDC6";
    public static final Boolean TX5_VALIDATED = true;

    public static final TransactionType DTO_TRANSACTION_TYPE = TransactionType.DEPOSIT;

    public static RippleTransactionsResponse getDefaultGetTransactionsResponse() {
        List<RippleTransactionsResponse.Result.Trx> transactions = Arrays.asList(
                createTrx(createTransactionBody(TX1_DESTINATION, TX1_ACCOUNT, TX1_AMOUNT, TX1_DATE, TX1_HASH, createMemos(createMemo(TX1_MEMO_DATA)), TX1_SEQUENCE, TX1_FEE)),
                createTrx(createTransactionBody(TX2_DESTINATION, TX2_ACCOUNT, TX2_AMOUNT, TX2_DATE, TX2_HASH, createMemos(createMemo(TX2_MEMO_DATA)), TX2_SEQUENCE, TX2_FEE)),
                createTrx(createTransactionBody(TX3_DESTINATION, TX3_ACCOUNT, TX3_AMOUNT, TX3_DATE, TX3_HASH, createMemos(createMemo(TX3_MEMO_DATA)), TX3_SEQUENCE, TX3_FEE)),
                createTrx(createTransactionBody(TX4_DESTINATION, TX4_ACCOUNT, TX4_AMOUNT, TX4_DATE, TX4_HASH, createMemos(createMemo(TX4_MEMO_DATA)), TX4_SEQUENCE, TX4_FEE)),
                createTrx(createTransactionBody(TX5_DESTINATION, TX5_ACCOUNT, TX5_AMOUNT, TX5_DATE, TX5_HASH, createMemos(createMemo(TX5_MEMO_DATA)), TX5_SEQUENCE, TX5_FEE))
        );

        RippleTransactionsResponse.Result result = RippleTransactionsResponse.Result.builder()
                .account(RESULT_ACCOUNT)
                .ledgerIndexMax(RESULT_LEDGER_INDEX_MAX)
                .ledgerIndexMin(RESULT_LEDGER_INDEX_MIN)
                .status(RESULT_STATUS)
                .transactions(transactions)
                .build();

        return RippleTransactionsResponse.builder().result(result).build();
    }

    public static List<TransactionResponse> getDefaultRippleTransactionDtoList() {
        return Arrays.asList(
                createTransactionResponse(TX5_DESTINATION, TX5_ACCOUNT, RippleBalanceConverter.toDouble(TX5_AMOUNT), TX5_DATE, TX5_HASH, TX5_MEMO, TX5_SEQUENCE),
                createTransactionResponse(TX4_DESTINATION, TX4_ACCOUNT, RippleBalanceConverter.toDouble(TX4_AMOUNT), TX4_DATE, TX4_HASH, TX4_MEMO, TX4_SEQUENCE),
                createTransactionResponse(TX3_DESTINATION, TX3_ACCOUNT, RippleBalanceConverter.toDouble(TX3_AMOUNT), TX3_DATE, TX3_HASH, TX3_MEMO, TX3_SEQUENCE),
                createTransactionResponse(TX2_DESTINATION, TX2_ACCOUNT, RippleBalanceConverter.toDouble(TX2_AMOUNT), TX2_DATE, TX2_HASH, TX2_MEMO, TX2_SEQUENCE),
                createTransactionResponse(TX1_DESTINATION, TX1_ACCOUNT, RippleBalanceConverter.toDouble(TX1_AMOUNT), TX1_DATE, TX1_HASH, TX1_MEMO, TX1_SEQUENCE)
        );
    }

    public static LastProcessedTransactionData getDefaultLastProcessedTransactionData() {
        return LastProcessedTransactionData.builder().transactionIndex(LAST_PROCESSED_SEQUENCE).build();
    }

    private static TransactionResponse createTransactionResponse(
            String from, String to, Double amount,
            Long timestamp, String trxId, String memo,
            Long transactionIndex
    ) {
        return TransactionResponse.builder()
                .from(from)
                .amount(amount)
                .timestamp(timestamp)
                .to(to)
                .trxId(trxId)
                .memo(memo)
                .transactionType(DTO_TRANSACTION_TYPE)
                .transactionIndex(transactionIndex)
                .build();
    }

    private static TransactionBody createTransactionBody(
            String account, String to, String amount,
            Long date, String hash, TransactionBody.Memos memos,
            Long sequence, String fee
    ) {
        return TransactionBody.builder()
                .account(account)
                .amount(amount)
                .date(date)
                .destination(to)
                .fee(fee)
                .hash(hash)
                .memos(Collections.singletonList(memos))
                .ledgerIndex(sequence)
                .build();
    }

    private static RippleTransactionsResponse.Result.Trx createTrx(TransactionBody transactionBody) {
        return RippleTransactionsResponse.Result.Trx.builder().tx(transactionBody).build();
    }

	private static TransactionBody.Memos createMemos(TransactionBody.Memos.Memo memo) {
		return TransactionBody.Memos.builder().memo(memo).build();
	}

	private static TransactionBody.Memos.Memo createMemo(String memo) {
		return TransactionBody.Memos.Memo.builder().memoData(memo).build();
	}

}
