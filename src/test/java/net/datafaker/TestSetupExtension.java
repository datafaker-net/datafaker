package net.datafaker;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.opentest4j.TestAbortedException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static java.util.logging.LogManager.getLogManager;

public class TestSetupExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {
  private static final AtomicLong counter = new AtomicLong(1000);
  private static final ExtensionContext.Namespace namespace = ExtensionContext.Namespace.create(TestSetupExtension.class);

  @Override
  public void beforeAll(ExtensionContext context) throws IOException, URISyntaxException {
      String loggingConfig = new File(requireNonNull(getClass().getResource("/logging.properties")).toURI()).getAbsolutePath();
      System.setProperty("java.util.logging.config.file", loggingConfig);
      getLogManager().readConfiguration();

      Logger.getLogger(context.getDisplayName()).fine(() -> "Starting tests (%s)".formatted(memory()));
  }

  @Override
  public void afterAll(ExtensionContext context) {
      Logger.getLogger(context.getDisplayName()).fine(() -> "Finished tests - %s (%s)".formatted(verdict(context), memory()));
  }

  @Override
  public void beforeEach(ExtensionContext context) {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));
    long testId = counter.incrementAndGet();
    context.getStore(namespace).put("test-id", testId);
    context.getStore(namespace).put("original-thread-name", Thread.currentThread().getName());

    Thread.currentThread().setName("%s.%s#%d#".formatted(context.getRequiredTestClass().getSimpleName(), context.getRequiredTestMethod().getName(), testId));

    Logger.getLogger(context.getRequiredTestClass().getName()).fine(() -> "starting %s (%s)...".formatted(context.getDisplayName(), memory()));
    ThreadLocalLogHandler.start();
  }

  @Override
  public void afterEach(ExtensionContext context) {
      ThreadLocalLogHandler.finish(context.getExecutionException().isPresent());

      Logger.getLogger(context.getRequiredTestClass().getName())
          .fine(() -> "finished %s - %s (%s)".formatted(context.getDisplayName(), verdict(context), memory()));

      String originalThreadName = context.getStore(namespace).remove("original-thread-name", String.class);
      Thread.currentThread().setName(originalThreadName);
      context.getStore(namespace).remove("test-id");
      TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));
  }

  private String verdict(ExtensionContext context) {
    return context.getExecutionException().isPresent() ?
      (context.getExecutionException().get() instanceof TestAbortedException ? "skipped" : "NOK") :
      "OK";
  }

  private String memory() {
    long freeMemory = Runtime.getRuntime().freeMemory();
    long maxMemory = Runtime.getRuntime().maxMemory();
    long totalMemory = Runtime.getRuntime().totalMemory();
    long usedMemory = totalMemory - freeMemory;
    return "memory used:" + mb(usedMemory) + ", free:" + mb(freeMemory) + ", total:" + mb(totalMemory) + ", max:" + mb(maxMemory);
  }

  private long mb(long bytes) {
    return bytes / 1024 / 1024;
  }
}
