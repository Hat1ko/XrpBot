const program = require('commander');
const process = require('process');
const RippleAPI = require('ripple-lib').RippleAPI

program.command('sign <server> <transactionType> <from> <to> <amount> <fee> <sequence> <memo> <secret>').
    action((server, transactionType, from, to, amount, fee, sequence, memo, secret) => {
    api = new RippleAPI({ server: server });
    
    payment = '{'
        + '\"TransactionType\":\"' + transactionType + '\",'
        + '\"Account\":\"' + from+ '\",'
        + '\"Destination\":\"' + to + '\",'
        + '\"Amount\":\"' + amount + '\",'
        + '\"Fee\":\"' + fee +'\",'
        + '\"Sequence\":' + sequence + ','
        + '\"Memos\":[{'
        + '\"Memo\":{'
        + '\"MemoData\":\"' + memo
        +'\"}}]' 
        + '}';
    
    const { signedTransaction, id } = api.sign(payment.toString(), secret);
    console.log(signedTransaction);
})

program.parse(process.argv);

