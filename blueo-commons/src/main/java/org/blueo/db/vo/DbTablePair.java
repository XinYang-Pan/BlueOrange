package org.blueo.db.vo;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.blueo.db.sql.GenericSqlBuilder;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DbTablePair {
	// 
	private GenericSqlBuilder genericSqlBuilder = new GenericSqlBuilder();
	// 
	private DbTable current;
	private DbTable previous;
	// 
	private List<DbColumn> add;
	private List<DbColumn> modify;
	private List<DbColumn> drop;

	private void classify() {
		if (add != null) {
			return;
		}
		// 
		add = Lists.newArrayList();
		modify = Lists.newArrayList();
		drop = Lists.newArrayList();
		// 
		Map<String, DbColumn> currentMap = buildMap(current);
		Map<String, DbColumn> previousMap = buildMap(previous);
		for (Entry<String, DbColumn> e : currentMap.entrySet()) {
			String columName = e.getKey();
			DbColumn curr = e.getValue();
			DbColumn prev = previousMap.remove(columName);
			if (prev == null) {
				add.add(curr);
			} else {
				String oneLineCurr = genericSqlBuilder.getDefinitionSql(curr);
				String oneLinePrev = genericSqlBuilder.getDefinitionSql(prev);
				if (!Objects.equal(oneLineCurr, oneLinePrev)) {
					modify.add(curr);
				}
			}
		}
		// left in previous should be dropped.
		for (DbColumn dbColumnLeft : previousMap.values()) {
			drop.add(dbColumnLeft);
		}
	}

	private Map<String, DbColumn> buildMap(DbTable dbTable) {
		Map<String, DbColumn> currentMap = Maps.newHashMap();
		for (DbColumn dbColumn : dbTable.getDbColumns()) {
			this.put(currentMap, dbColumn);
		}
		this.put(currentMap, dbTable.getPk());
		return currentMap;
	}

	private void put(Map<String, DbColumn> map, DbColumn dbColumn) {
		map.put(dbColumn.getName(), dbColumn);
	}

	public DbTable getCurrent() {
		return current;
	}

	public void setCurrent(DbTable current) {
		this.current = current;
	}

	public DbTable getPrevious() {
		return previous;
	}

	public void setPrevious(DbTable previous) {
		this.previous = previous;
	}

	public List<DbColumn> getAdd() {
		classify();
		return add;
	}

	public List<DbColumn> getModify() {
		classify();
		return modify;
	}

	public List<DbColumn> getDrop() {
		classify();
		return drop;
	}

}
