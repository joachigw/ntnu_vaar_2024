package services;

import data.Session;
import data.Transaction;
import db.TransactionDao;
import db.UserDao;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import util.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Service handling user transactions in the bank
 * @author nilstes
 */
@Path("user/{email}/transaction")
public class TransactionService {

    private static final Logger log = Logger.getLogger();

    private UserDao userDao = new UserDao();
    private TransactionDao transactionDao = new TransactionDao();

    @Context
    private HttpServletRequest request;

    @POST
    @Consumes("application/json")
    public void add(Transaction transaction) {
        log.info("Adding transaction from " + transaction.getFromEmail() + " with amount " + transaction.getAmount());

        // Check that the transaction sender is the logged user
        Session session = (Session) request.getSession().getAttribute("session");
        if (!transaction.getFromEmail().equals(session.getEmail())) {
            throw new NotAuthorizedException("Cannot access this account", Response.Status.FORBIDDEN);
        }

        // Check if trans ok
        double currentBalance = getCurrentBalance(transaction.getFromEmail());
        checkTransactionAmount(transaction, currentBalance);

        // Add transaction
        try {
            transactionDao.addTransaction(transaction);
            log.info("Added transaction!");
        } catch (SQLException e) {
            log.error("Failed to add transaction", e);
            throw new ServerErrorException("Failed to add transaction", Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    private void checkTransactionAmount(Transaction transaction, double currentBalance) {
        // Check if transaction amount is ok
        boolean isFamily = isFamily(transaction);
        boolean isOverDrawn = currentBalance < 0;
        boolean balanceWillBeUnderLimit = (currentBalance - transaction.getAmount()) < -5000;

        log.info("Current balance=" + currentBalance + ", isFamily=" + isFamily);
        log.info("isOverDrawn=" + isOverDrawn + ", balanceWillBeUnderLimit=" + balanceWillBeUnderLimit);

        boolean ok = (!isFamily && !isOverDrawn && !balanceWillBeUnderLimit) || (isFamily && !(isOverDrawn && balanceWillBeUnderLimit));

        if (!ok) {
            log.info("Transaction was not accepted");
            throw new BadRequestException("Transaction amount was too large");
        }
    }

    private double getCurrentBalance(String fromEmail) {
        try {
            double balance = 0;
            List<Transaction> transactions = transactionDao.getTransactions(fromEmail);
            for (Transaction t : transactions) {
                if (t.getFromEmail().equals(fromEmail)) {
                    balance -= t.getAmount();
                } else {
                    balance += t.getAmount();
                }
            }
            return balance;
        } catch (SQLException e) {
            log.error("Failed to add transaction", e);
            throw new ServerErrorException("Failed to add transaction", Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    // Dummy method to determine whether sender and receiver of payment is in the same family
    private boolean isFamily(Transaction transaction) {
        String toDomain = transaction.getToEmail().substring(transaction.getToEmail().indexOf("@"));
        String fromDomain = transaction.getFromEmail().substring(transaction.getFromEmail().indexOf("@"));
        return toDomain.equals(fromDomain);
    }

    @GET
    @Produces("application/json")
    public List<Transaction> get(@PathParam("email") String currentUserEmail) {
        // Check that it is the logged user's account that is being accessed
        Session session = (Session) request.getSession().getAttribute("session");
        if (!currentUserEmail.equals(session.getEmail())) {
            throw new NotAuthorizedException("Cannot access this account", Response.Status.FORBIDDEN);
        }

        // Get transaction
        try {
            return transactionDao.getTransactions(currentUserEmail);
        } catch (SQLException e) {
            log.error("Failed to read user transactions", e);
            throw new ServerErrorException("Failed to read user transactions", Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    @DELETE
    public void delete(@PathParam("email") String currentUserEmail) {
        // Check that it is the logged user's account that is being accessed
        Session session = (Session)request.getSession().getAttribute("session");
        if(!currentUserEmail.equals(session.getEmail())) {
            throw new NotAuthorizedException("Cannot access this account", Response.Status.FORBIDDEN);
        }

        // Get transaction
        try {
            transactionDao.deleteTransactions(currentUserEmail);
            log.info("Deleted transactions for user " + currentUserEmail);
        } catch(SQLException e) {
            log.error("Failed to delete user transactions", e);
            throw new ServerErrorException("Failed to delete user transactions", Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    public static void main(String[] args) throws Exception {
        Transaction trans = new Transaction("nils@nilsen.no", "nils@ntnu.no", "yo", 6001d, new Date());
        new TransactionService().checkTransactionAmount(trans, 1000d);
    }
}