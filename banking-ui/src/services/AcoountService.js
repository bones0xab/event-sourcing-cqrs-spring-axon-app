import axios from 'axios';

const API_URL_COMMAND = 'http://localhost:1111/commands/accounts';
const API_URL_QUERY = 'http://localhost:1111/query/accounts';
const API_URL_AI = 'http://localhost:1111/query/ai';

class AccountService {

    //Commands
    createAccount(initialeBalance, currency) {
        return axios.post(API_URL_COMMAND + '/add', {
            initialeBalance: initialeBalance,
            currency: currency
        })};

    debitAccount(accountId, amount, currency){
        return axios.put(API_URL_COMMAND + '/debit', {
            accountId: accountId,
            amount: amount,
            currency: currency
        });
    }

    creditAccount(accountId, amount, currency){
        return axios.put(API_URL_COMMAND + '/credit', {
            accountId: accountId,
            amount: amount,
            currency: currency
        });
    }

    chatAi(question) {
        return axios.post(API_URL_AI + "/chat", {
            question: question
        });
    }




    //Queries
    getAllAccounts() {
        return axios.get(API_URL_QUERY + "/all");
    }

    getAccountOperations(accountId) {
        return axios.get(API_URL_QUERY + "/{accountId}/operations");
    }

    getAccount(accountId) {
        return axios.get(API_URL_QUERY + `/${accountId}`);
    }


}

export default new AccountService();