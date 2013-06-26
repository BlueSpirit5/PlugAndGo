/**
 * Project: Tesuji Go Framework.<br>
 * <br>
 * <font color="#CC6600"><font size=-1> Copyright (c) 1985-2006 Mark Boon<br>
 * All rights reserved.<br>
 * <br>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so,
 * provided that the above copyright notice(s) and this permission notice appear
 * in all copies of the Software and that both the above copyright notice(s) and
 * this permission notice appear in supporting documentation.<br>
 * <br>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.<br>
 * <br>
 * <font color="#00000"><font size=+1>
 * 
 */
package tesuji.games.go.search;

import tesuji.core.util.ArrayStack;
import tesuji.core.util.Factory;
import tesuji.core.util.FactoryReport;

import tesuji.games.go.common.GoMove;

public class SearchResultFactory
	implements Factory
{
	private static int nrResults = 0;
	
	/**
	 * This helper-class ensures there's a unique factory for each thread.
	 * This has the double benefit of not needing synchronization of the object-pool
	 * and that threads don't have to wait for each other to create objects.
	 */
	static class LocalAllocationHelper
		extends ThreadLocal<SearchResultFactory>
	{
		@Override
		public SearchResultFactory initialValue()
		{
			return new SearchResultFactory();
		}
	}

	private static LocalAllocationHelper _singleton;

	private ArrayStack<MonteCarloTreeSearchResult<GoMove>> mctsResultPool =
		new ArrayStack<MonteCarloTreeSearchResult<GoMove>>();
	
	private ArrayStack<MonteCarloHashMapResult> mchmResultPool =
		new ArrayStack<MonteCarloHashMapResult>();
	
	public static SearchResultFactory getSingleton()
	{
		if (_singleton==null)
		{
			_singleton = new LocalAllocationHelper();
			FactoryReport.addFactory(_singleton.get());
		}
		
		return _singleton.get();
	}
	
	/*
	 * (non-Javadoc)
	 * @see tesuji.core.util.Factory#getFactoryName()
	 */
	public String getFactoryName()
	{
		return "SearchResultFactory";
	}
	
	/*
	 * (non-Javadoc)
	 * @see tesuji.core.util.Factory#getFactoryReport()
	 */
	public String getFactoryReport()
	{
		return "Number of MonteCarloTreeSearchResult objects:\t\t"+nrResults+"\n";
	}

	public static MonteCarloTreeSearchResult<GoMove> createMonteCarloTreeSearchResult()
	{
		return getSingleton()._createMonteCarloTreeSearchResult();
	}
	
	private MonteCarloTreeSearchResult<GoMove> _createMonteCarloTreeSearchResult()
	{
		MonteCarloTreeSearchResult<GoMove> newResult;
        if (mctsResultPool.isEmpty())
        {
        	newResult = new MonteCarloTreeSearchResult<GoMove>(mctsResultPool);
        	nrResults++;
        }
        else
        	newResult = mctsResultPool.pop();
        
        return newResult;
	}
	
	public static void ensureMonteCarloHashMapResultCapacity(int capacity)
	{
		SearchResultFactory factory = getSingleton();
		for (int i=0; i<capacity; i++)
		{
			MonteCarloHashMapResult result = new MonteCarloHashMapResult(factory.mchmResultPool);
			result.init();
			factory.mchmResultPool.push(result);
        	nrResults++;
		}
	}
	
	public static MonteCarloHashMapResult createMonteCarloHashMapResult()
	{
		return getSingleton()._createMonteCarloHashMapResult();
	}
	
	public static void recycle(MonteCarloHashMapResult o)
	{
		getSingleton().mchmResultPool.push(o);
	}
	
	private MonteCarloHashMapResult _createMonteCarloHashMapResult()
	{
		MonteCarloHashMapResult newResult;
        if (mchmResultPool.isEmpty())
        {
        	newResult = new MonteCarloHashMapResult(mchmResultPool);
        	nrResults++;
        }
        else
        	newResult = mchmResultPool.pop();
        
        newResult.init();
        return newResult;
	}
}
