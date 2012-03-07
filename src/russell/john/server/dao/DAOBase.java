package russell.john.server.dao;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class DAOBase
{
	/** Need to create the lazy Objectify object */
	private boolean transactional;

	/** A single objectify interface, lazily created */
	private Objectify lazyOfy;

	/** Creates a DAO without a transaction */
	public DAOBase()
	{
		this(false);
	}

	/**
	 * Creates a DAO possibly with a transaction.
	 */
	public DAOBase(boolean transactional)
	{
		this.transactional = transactional;
	}

	/**
	 * Easy access to the factory object. This is convenient shorthand for
	 * {@code ObjectifyService.factory()}.
	 */
	public ObjectifyFactory fact()
	{
		return ObjectifyService.factory();
	}

	/**
	 * Easy access to the objectify object (which is lazily created).
	 */
	public Objectify ofy()
	{
		if (this.lazyOfy == null)
		{
			if (this.transactional)
				this.lazyOfy = ObjectifyService.factory().beginTransaction();
			else
				this.lazyOfy = ObjectifyService.factory().begin();
		}

		return this.lazyOfy;
	}
}
