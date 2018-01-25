package application;

public class Benchmarker {
	
	private long usedMemoryBefore, usedMemoryAfter, startTime, endTime;
	private Runtime runtime;
	
	Benchmarker()
	{
		runtime = Runtime.getRuntime();
		reset();
	}
	
	public void reset()
	{
		usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
		startTime = System.currentTimeMillis();
	}
	
	public void end()
	{
		endTime = System.currentTimeMillis();
		usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
	}
	
	public long msTook() { return endTime - startTime; }
	
	public long mbTook() { return (usedMemoryAfter-usedMemoryBefore)/1000000; }

}
