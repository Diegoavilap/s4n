package com.diegoavilap.two;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompletableFutureTest {
	private final static Logger LOGGER = LoggerFactory.getLogger(CompletableFutureTest.class);

    private ExecutorService executor;

    @BeforeEach
    public void setUp() {
        executor = Executors.newFixedThreadPool(5);
    }
    
    @AfterEach
    public void shutDown() throws InterruptedException {
        Sleep.sleepSeconds(2);
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
    
    //Creating CompletableFuture
    
    // completedFuture()
    @Disabled
    @Test
    public void givenAString_whenCompletedFutureIsCalled_thenAFutureIsReturned() throws InterruptedException, ExecutionException {
        CompletableFuture<String> future = CompletableFuture.completedFuture("S4N");
        // future.whenCompleteAsync((s, e) -> LOGGER.info("Result: " + s), executor);
        assertEquals("S4N", future.get());
    }
    
    // complete()
    @Disabled
    @Test
    public void givenAString_whenCompleteIsCalled_thenAFutureIsReturned() throws InterruptedException, ExecutionException {
    	CompletableFuture<String> future = new CompletableFuture<>();
    	future.complete("S4N");
    	assertEquals("S4N", future.get());
    }
    
    //runAsync()
    //Recibe un Runnable como parametro
    @Disabled
    @Test
    public void whenRunAsyncIsCalled_thenTheFutureIsDone() throws InterruptedException, ExecutionException {
    	
    	CompletableFuture<Void> futureRunAsync = CompletableFuture.runAsync(() -> {
    	    LOGGER.info("Comenzando runAsync...");
    	    LOGGER.info("Terminado runAsync!");
    	}, executor);
    	 
    	Sleep.sleepSeconds(2);
    	assertTrue(futureRunAsync.isDone());
    }
    //supplyAsync()
    @Disabled
    @Test
    public void whenSupplyAsyncIsCalled_thenTheCompletableFuture() throws InterruptedException, ExecutionException {
    	CompletableFuture<String> futureSupplyAsync = CompletableFuture.supplyAsync(() -> {
    	    LOGGER.info("Comenzando supplyAsync...");
    	    LOGGER.info("Terminado supplyAsync!");
    	    return "S4N";
    	}, executor);
    	assertEquals("S4N", futureSupplyAsync.get());
    }
    
    @Test
    public void whenCompleteAsyncIsCalled_thenIsCorrected() {
    	CompletableFuture<String> futureSupplyAsync = CompletableFuture.supplyAsync(() -> {
    	    LOGGER.info("Comenzando supplyAsync...");
    	    LOGGER.info("Terminado supplyAsync!");
    	    return "S4N";
    	}, executor);
    	
    	// whenCompleteAsync acepta una lambda BiConsumer,en la que 
    	// el primer parametro es el value del Future y 
    	// el segundo parametro es el exception
    	futureSupplyAsync.whenCompleteAsync((s, e) -> LOGGER.info("Resultado supplyAsync: " + s),executor);
    	LOGGER.info("Terminado main thread");
    }

	
}
