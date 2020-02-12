const program = require('commander');
const RippleAPI = require('ripple-lib').RippleAPI;

program.command('sign <server> <from> <to> <amount> <memo> <secret>')
    .action((server, from, to, amount, memo, secret) => {
        const api = new RippleAPI({server: server});
        const payment = {
            source: {
                address: from,
                maxAmount: {
                    currency: 'XRP',
                    value: amount
                }
            },
            destination: {
                address: to,
                amount: {
                    currency: 'XRP',
                    value: amount
                }
            },
            memos: [{
                data: memo
            }]
        };

        const changeState = (prepared, num) => {
            const transaction = JSON.parse(prepared.txJSON);
            const oldLedgerSequence = transaction.LastLedgerSequence;
            transaction.LastLedgerSequence = oldLedgerSequence * num;
            return JSON.stringify(transaction);
        };

        api.connect()
            .then(() => {
                api.preparePayment(from, payment)
                    .then((prepared) => {
                        const txJSON = changeState(prepared, 100);
                        const {signedTransaction} = api.sign(txJSON, secret.toString());
                        console.log(signedTransaction);
                        process.exit();
                    });
            })
            .catch(() => {
                api.preparePayment(from, payment)
                    .then((prepared) => {
                        const txJSON = changeState(prepared, 2000000);
                        const {signedTransaction} = api.sign(txJSON, secret.toString());
                        console.log(signedTransaction);
                        process.exit();
                    });
            });
    });

program.parse(process.argv);