package com.wro.licenses.hystrix;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.properties.HystrixProperty;
import com.wro.licenses.utils.UserContextHolder;

public class ThreadLocalAwareStrategy extends HystrixConcurrencyStrategy {

	private HystrixConcurrencyStrategy existingConcurrentStrategy;

	public ThreadLocalAwareStrategy(HystrixConcurrencyStrategy existingConcurrentStrategy) {
		this.existingConcurrentStrategy = existingConcurrentStrategy;
	}

	@Override
	public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixProperty<Integer> corePoolSize,
			HystrixProperty<Integer> maximumPoolSize, HystrixProperty<Integer> keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		return existingConcurrentStrategy != null
				? existingConcurrentStrategy.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime,
						unit, workQueue)
				: super.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	@Override
	public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
		return existingConcurrentStrategy != null ? existingConcurrentStrategy.getBlockingQueue(maxQueueSize)
				: super.getBlockingQueue(maxQueueSize);
	}

	@Override
	public <T> Callable<T> wrapCallable(Callable<T> callable) {
		return existingConcurrentStrategy != null ? existingConcurrentStrategy.wrapCallable(new DelegatingUserContextCallable<T>(callable, UserContextHolder.getContext()))
				: super.wrapCallable(callable);
	}

	@Override
	public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv) {
		return existingConcurrentStrategy != null ? existingConcurrentStrategy.getRequestVariable(rv)
				: super.getRequestVariable(rv);
	}

}
