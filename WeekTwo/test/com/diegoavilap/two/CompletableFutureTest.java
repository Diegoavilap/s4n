package com.diegoavilap.two;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
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
        Sleep.sleepSeconds(5);
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
    	    LOGGER.info("Pasando por el supplyAsync...");
    	    return "S4N";
    	}, executor);
    	assertEquals("S4N", futureSupplyAsync.get());
    }
    
    
    //whenCompleteAsync() ---> Callback
    @Disabled
    @Test
    public void whenCompleteAsyncIsCalled_thenIsCorrected() throws InterruptedException, ExecutionException {
    	CompletableFuture<String> futureSupplyAsync = CompletableFuture.supplyAsync(() -> {
    	    LOGGER.info("Pasando por el supplyAsync...");
    	    return "S4N";
    	}, executor);
    	
    	// whenCompleteAsync acepta una lambda BiConsumer,en la que 
    	// el primer parametro es el value del Future y 
    	// el segundo parametro es el exception
    	futureSupplyAsync.whenCompleteAsync((s, e) -> LOGGER.info("Resultado supplyAsync: " + s),executor);
    	LOGGER.info("Terminado main thread");
    	assertEquals("S4N", futureSupplyAsync.get());
    }
    
    // --- Processing Results of Asynchronous Computations --
    
    //thenApplyAsync --> Map();
    @Disabled
    @Test
    public void whenThenApplyAsyncIsCalled_thenCorrect() throws InterruptedException, ExecutionException {
    	CompletableFuture<String> futureAsync = CompletableFuture.supplyAsync(() -> {
    	    System.out.println("Pasando por supplyAsync ...");
    	    return "s4n";
    	}, executor);
    	 
    	CompletableFuture<String> futureApply = futureAsync.thenApplyAsync(s -> {
    	    System.out.println("Pasando por thenApplyAsync...");
    	    return s.toUpperCase();
    	}, executor);
    	 
    	//futureApply.whenCompleteAsync((s, e) -> System.out.println("Resultado applyAsync: " + s), executor);
    	assertEquals("S4N", futureApply.get());
    }
    
    //thenAcceptAsync
    @Disabled
    @Test
    public void whenThenAcceptAsyncIsCalled_thenCorrect() throws InterruptedException, ExecutionException {
    	// thenAccept
    	CompletableFuture<String> futureAsync = CompletableFuture.supplyAsync(() -> {
    		LOGGER.info("Pasando por el supplyAsync...");
    		LOGGER.info("Running method1... thread id: " + Thread.currentThread().getId());
    	    Sleep.sleepSeconds(2);
    	    return "S4N";
    	}, executor);
    	 
    	futureAsync.thenAcceptAsync(s -> {
    	    LOGGER.info("Pasando por el thenAccept...");
    	    LOGGER.info("corriendo en el thread id: " + Thread.currentThread().getId());
    	    Sleep.sleepSeconds(2);
    	    LOGGER.info("Este es el resultado del thenAcceptAsync(): {}", s);
    	}, executor);
    	
    	assertEquals("S4N", futureAsync.get());
    }

	// thenRun
    @Disabled
    @Test
    public void whenThenRunAsyncIsCalled_thenIsCorrect() {
    	CompletableFuture<Void> futureRun = CompletableFuture.runAsync(() -> {
    	    LOGGER.info("Comenzando runAsync");
    	    LOGGER.info("corriendo en el thread id: " + Thread.currentThread().getId());
    	}, executor);
    	 
    	futureRun.thenRunAsync(() -> {
    	    LOGGER.info("Comenzando thenRunAsync...");
    	    LOGGER.info("corriendo en el thread id: " + Thread.currentThread().getId());
    	}, executor);
    	Sleep.sleepSeconds(1);
    	assertTrue(futureRun.isDone());
    }
    
    //Handling Errors
    @Disabled
	@Test
	public void whenExceptionally_thenIsCorrect() throws InterruptedException, ExecutionException {
		CompletableFuture<String> futureAsync = CompletableFuture.supplyAsync(() -> {
		    LOGGER.info("Pasando por supplyAsync...");
		    throw new RuntimeException("Error en el futuro");
		}, executor);
		 
		CompletableFuture<String> futureEx = futureAsync.exceptionally(e -> {
		    LOGGER.error("Resultado con excepción!!", e);
		    return "Mensaje de Error";
		});
		 
		futureEx.whenCompleteAsync((s, e) -> LOGGER.info("Resultado futureEx: {}", s), executor);
		
		//------------//
		assertEquals("Mensaje de Error", futureEx.get());
	}
	
    //handledAsync()
    @Disabled
	@Test
	public void whenHandledFutuure_thenCorrect() throws InterruptedException, ExecutionException {
		CompletableFuture<String> futureAsync = CompletableFuture.supplyAsync(() -> {
		    LOGGER.info("Pasando por supplyAsync...");
		    throw new RuntimeException("Error en el futuro");
		}, executor);
		 
		CompletableFuture<String> handledFuture = futureAsync.handleAsync((s, e) -> {
		    if (e != null) {
		        LOGGER.error("Resultado con excepción!!", e);
		        return "Mensaje de Error";
		    } else {
		        LOGGER.info("Resultado: {}", s);
		        return s;
		    }
		}, executor);
		
		handledFuture.whenCompleteAsync((s, e) -> LOGGER.info("Resultado handle: {}", s), executor);
		assertEquals("Mensaje de Error", handledFuture.get());
	}
    //whenCompleteAsync() for handling errors
    @Disabled
    @Test
    public void whenCompleteAsync_thenCorrect() throws InterruptedException, ExecutionException {
    	CompletableFuture<String> futureAsync = CompletableFuture.supplyAsync(() -> {
    	    LOGGER.info("Pasando por supplyAsync with exception...");
    	    throw new RuntimeException("Error en el futuro");
    	}, executor);
    	 
    	futureAsync.whenCompleteAsync((s, e) -> {
    	    if (e != null) {
    	        LOGGER.error("Resultado con excepción!!", e);
    	    } else {
    	        LOGGER.info("Resultado applyAsync: {}", s);
    	    }
    	}, executor);
    	assertThrows(ExecutionException.class, () -> {
    		futureAsync.get();
	    }); 
    }
    
    //thenComposeAsync()
    @Disabled
    @Test
    public void whenThenComposeAsync_thenCorrect() throws InterruptedException, ExecutionException {
    	CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
    	    LOGGER.info("Pasando por supplyAsync...");
    	    return "Colaboreme profe no sea así";
    	}, executor);
    	 
    	CompletableFuture<String> fCompose =
    	        future1.thenComposeAsync(s -> CompletableFuture.supplyAsync(() -> {
    	                    LOGGER.info("Pasando por thenCompose...");
    	                    return s.concat(", le doy mil pesos");
    	                }, executor),
    	                executor);
    	 
    	fCompose.whenCompleteAsync((s, e) -> LOGGER.info("Resultado thenCompose: {}", s),
    	        executor);
    	
    	assertEquals("Colaboreme profe no sea así, le doy mil pesos", fCompose.get());
    }
    
    @Test
    public void givenTwoCompletableFuture_whenThenCombineAsync_thenANewCompletableFuture() throws InterruptedException, ExecutionException {
    	CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
    	    LOGGER.info("Creando el future1 con supplyAsync...");
    	    return "Terminado";
    	}, executor);
    	 
    	CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
    	    LOGGER.info("Creando el  future2 con supplyAsync...");
    	    return "Terminado other";
    	}, executor);
    	 
    	CompletableFuture<String> fCombine =
    	        future1.thenCombineAsync(future2, (s1, s2) -> {
    	            LOGGER.info("En el thenCombine, recibidos los resultados: {}, {}", s1, s2);
    	            return s1 + s2;
    	        }, executor);
    	 
    	fCombine.whenCompleteAsync((s, e) -> LOGGER.info("Resultado thenCombine: {}", s),
    	        executor);
    	assertEquals("TerminadoTerminado other", fCombine.get());
    }
    
    
    
}
