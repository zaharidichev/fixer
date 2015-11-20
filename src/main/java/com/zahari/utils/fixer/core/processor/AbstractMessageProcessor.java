package com.zahari.utils.fixer.core.processor;

import com.zahari.utils.fixer.core.parser.types.RawFixMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by zdichev on 16/11/2015.
 */
public abstract class AbstractMessageProcessor {

    private BlockingQueue<RawFixMessage> messageQueue;
    private final Logger logger = LoggerFactory.getLogger(AbstractMessageProcessor.class);


    public AbstractMessageProcessor() {
        this.messageQueue = new LinkedBlockingDeque<RawFixMessage>();
        new Thread(new Runnable() {
            public void run() {
                while(true) {
                    try {
                        RawFixMessage m = messageQueue.take();
                        processMessage(m);
                    } catch (InterruptedException e) {
                        logger.error("interrupting current thread.",e);
                        Thread.currentThread().interrupt();                    }
                }
            }
        }).start();

    }

    public void enqueueMessageForProcessing(RawFixMessage m) {

        try {
            this.messageQueue.put(m);
        } catch (InterruptedException e) {
            logger.error("interrupting current thread.",e);
            Thread.currentThread().interrupt();
        }

    }


    public abstract void processMessage(RawFixMessage m);




}
