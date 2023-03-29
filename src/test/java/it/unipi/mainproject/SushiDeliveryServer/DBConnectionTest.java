package it.unipi.mainproject.SushiDeliveryServer;

import it.unipi.mainproject.SushiDeliveryServer.beans.ParametriRicorrenti;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DBConnectionTest {
    private static final Logger logger = LogManager.getLogger(DBConnectionTest.class);
	@Test
	void contextLoads() {
            try{
                Connection c = DriverManager.getConnection(ParametriRicorrenti.connectionURL, ParametriRicorrenti.user, ParametriRicorrenti.pass);

                if(c.isValid(0)){
                    logger.info("OK");
                    System.out.println("+------------------------------+");
                    System.out.println("| Test - Connessione al DB: OK |");
                    System.out.println("+------------------------------+");
                } else {
                    logger.info("ERR");
                    System.out.println("+-------------------------------------------------------------+");
                    fail("| Connessione non valida al momento del test di connettivita` |");
                    System.out.println("+-------------------------------------------------------------+");
                }
            } catch(SQLException e){
                logger.error(" - " + e.getMessage() + " [" + e.getSQLState() + ']');
                fail("Errore - Connessione al DB: " + e.getMessage());
            }
	}
}
