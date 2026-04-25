package com.avijeet.urlshortener.services.generators;

import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdGenerator implements IdGenerator {

    private long sequence = 0L;
    private long lastTimestamp = -1L;

    @Override
    public synchronized long generateId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            throw new IllegalStateException("Clock moved backwards.");
        }

        long sequenceBits = 12L;
        if (lastTimestamp == timestamp) {
            long sequenceMask = ~(-1L << sequenceBits);
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        long epoch = 1704067200000L;
        long workerIdBits = 5L;
        long datacenterIdShift = sequenceBits + workerIdBits;
        long datacenterIdBits = 5L;
        long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
        long workerId = 1L;
        long datacenterId = 1L;
        return ((timestamp - epoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << sequenceBits) |
                sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}
