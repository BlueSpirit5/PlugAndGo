package tesuji.games.go.monte_carlo;

import static tesuji.games.go.util.GoArray.FIRST;
import static tesuji.games.go.util.GoArray.LAST;
import static tesuji.games.go.util.GoArray.copy;
import static tesuji.games.go.util.GoArray.createBytes;
import static tesuji.games.go.util.GoArray.createRowArray;
import tesuji.games.go.util.TwelveCursor;
import tesuji.games.model.BoardChange;
import tesuji.games.model.BoardModelListener;

public class FogOfWarFilter implements MoveFilter, BoardModelListener
{
	private int id;
	private byte[] _fogOfWar;
	protected MonteCarloPluginAdministration _administration;

	public FogOfWarFilter()
	{
	}
	
//	@Override
	public void clear() 
	{
		byte[] _row = createRowArray(_administration.getBoardSize());
		for (int i=FIRST; i<=LAST; i++)
		{
			if (_row[i]<3)
				_fogOfWar[i] = Byte.MAX_VALUE;
			else
				_fogOfWar[i] = 0;
		}		
	}
	
//	@Override
	public void register(MonteCarloPluginAdministration administration)
	{
		_administration = administration;
		_fogOfWar = createBytes();
		clear();
	}

//	@Override
	public boolean accept(int xy, byte color)
	{
		return (_fogOfWar[xy]!=0);
	}

//	@Override
	public MoveFilter createClone()
	{
		return new FogOfWarFilter();
	}

//	@Override
	public void copyDataFrom(MoveFilter source)
	{
		FogOfWarFilter sourceFilter = (FogOfWarFilter)source;
		copy(sourceFilter._fogOfWar,_fogOfWar);		
	}

//	@Override
    public void changeBoard(BoardChange event)
    {
		int xy = event.getXY();
		_fogOfWar[xy] = 0;
		for (int n=12; --n>=0;)
		{
			int next = TwelveCursor.getNeighbour(xy, n);
			if (next>=FIRST && next<=LAST)
				_fogOfWar[next] = 0;
		}
    }
	
	@Override
	public boolean equals(Object o)
	{
		if (o==null || !(o instanceof FogOfWarFilter))
			return false;
		return id==((FogOfWarFilter)o).id;
	}

	public int getId()
    {
    	return id;
    }

	public void setId(int id)
    {
    	this.id = id;
    }
}
