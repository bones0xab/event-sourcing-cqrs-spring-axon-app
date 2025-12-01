import './App.css';
import {useEffect, useState} from "react";
import AcoountService from "./services/AcoountService";

function App() {
    const [accounts, setAccounts] = useState([]);
    const [addAmount, setAddAmount] = useState(0);
    const [addCurrency, setAddCurrency] = useState('EUR');
    const [transAmount, setTransAmount] = useState(0);
    const [transCurrency, setTransCurrency] = useState('EUR');
    const [selectedAccountId, setSelectedAccountId] = useState(null);

    const [question, setQuestion] = useState('');
    const [loading, setLoading] = useState(false);
    const [aiResponse, setAiResponse] = useState('');

    const loadAccounts = () => {
        AcoountService.getAllAccounts()
            .then((response) => {
                setAccounts(response.data);
            })
            .catch((error) => {
                console.error("There was an error fetching the accounts!", error);
            });
    }

    useEffect(() => {
        loadAccounts();
    }, []);


    const handleAddAccount = (amount, currency) => {
        AcoountService.createAccount(amount, currency)
            .then(() => {
                alert("Account Succeffuly Created");
                setTimeout(() => loadAccounts(), 500);
            })
    }

    const handleDebitAccount = (accountId, amount, currency) => {
        if(amount <= 0)
        {
            alert("Amount not Valid");
            return;
        }
        AcoountService.debitAccount(accountId, amount, currency)
            .then(() => {
                alert("Account Succeffuly Debited");
                loadAccounts();
            })
            .catch(error => alert("Incorrect amount"));
    }

    const handleCreditAccount = (accountId, amount, currency) => {
        if(amount <= 0)
        {
            alert("Amount not Valid");
            return;
        }
        AcoountService.creditAccount(accountId, amount, currency)
            .then(() => {
                alert("Account succeffuly Credited");
                 loadAccounts();
            })
            .catch(error => alert("Incorrect amount"));
    }


    const handleChatBot = () => {
        if (!question || question.trim() === '') {
            alert("Please enter a valid question.");
            return;
        }
        setLoading(true);
        setAiResponse('');
        AcoountService.chatAi(question)
            .then((response) => {
                setAiResponse(response.data.response);
            })
            .catch((error) => {
                setAiResponse("⚠️ Erreur : Je n'ai pas pu joindre le serveur.");
            })
            .finally(() => setLoading(false));
    }

    return (
        <div className="app-container">
            <header className="app-header">
                <h1>Banque Demo</h1>
                <p className="total">Solde total: {accounts.reduce((s,a)=>s + (a.balance || 0), 0).toFixed(2)}</p>
            </header>

            <main className="main-grid">
                <section className="accounts">
                    <h2>Comptes</h2>
                    <div className="account-list">
                        {accounts.map(acc => (
                            <div key={acc.id} className={"account-card " + (selectedAccountId === acc.id ? "selected" : "")}>
                                <div className="account-info">
                                    <strong>{acc.owner || `Compte : ${acc.id}`}</strong>
                                    <span className="balance">{(acc.balance ?? 0).toFixed(2)} {acc.currency || 'EUR'}</span>
                                </div>
                                <div className="account-actions">
                                    <button className="btn" onClick={() => setSelectedAccountId(acc.id)}>Sélectionner</button>
                                </div>
                            </div>
                        ))}
                        {accounts.length === 0 && <p className="empty">Aucun compte</p>}
                    </div>
                </section>

                <aside className="controls">
                    <div className="card">
                        <h3>Créer un compte</h3>
                        <input className="input" type="number" placeholder="Montant initial" value={addAmount} onChange={e => setAddAmount(e.target.value)} />
                        <select className="input" value={addCurrency} onChange={e => setAddCurrency(e.target.value)}>
                            <option>EUR</option>
                            <option>USD</option>
                            <option>GBP</option>
                        </select>
                        <button className="btn primary" onClick={() => handleAddAccount(parseFloat(addAmount || 0), addCurrency)}>Créer</button>
                    </div>

                    <div className="card">
                        <h3>Transaction</h3>
                        <p className="small">Compte sélectionné: {selectedAccountId ?? '—'}</p>
                        <input className="input" type="number" placeholder="Montant" value={transAmount} onChange={e => setTransAmount(e.target.value)} />
                        <select className="input" value={transCurrency} onChange={e => setTransCurrency(e.target.value)}>
                            <option>EUR</option>
                            <option>USD</option>
                            <option>GBP</option>
                        </select>
                        <div className="tx-buttons">
                            <button className="btn" onClick={() => selectedAccountId && handleDebitAccount(selectedAccountId, parseFloat(transAmount || 0), transCurrency)}>Débiter</button>
                            <button className="btn" onClick={() => selectedAccountId && handleCreditAccount(selectedAccountId, parseFloat(transAmount || 0), transCurrency)}>Créditer</button>
                        </div>
                    </div>


                </aside>
                <div className="card ai-card">
                    <h3>🤖 Assistant IA</h3>

                    <div className="chat-input-area">
                            <textarea
                                className="input"
                                rows="2"
                                placeholder="Posez une question sur vos comptes..."
                                value={question}
                                onChange={e => setQuestion(e.target.value)}
                            />
                        <button
                            className="btn primary"
                            onClick={handleChatBot}
                            disabled={loading}
                        >
                            {loading ? "..." : "Envoyer"}
                        </button>
                    </div>

                    {/* Zone d'affichage de la réponse (Le DOM ajouté) */}
                    {aiResponse && (
                        <div className="ai-response-box">
                            <strong>Réponse :</strong>
                            <p>{aiResponse}</p>
                        </div>
                    )}
                </div>
            </main>
        </div>
  );
}

export default App;
