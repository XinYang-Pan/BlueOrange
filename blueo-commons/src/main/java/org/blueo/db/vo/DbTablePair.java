package org.blueo.db.vo;

import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.blueo.commons.FormatterWrapper;
import org.blueo.db.sql.DdlBuildUtils;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DbTablePair {
	private DbTable current;
	private DbTable previous;

	private List<DbColumn> add = Lists.newArrayList();
	private List<DbColumn> modify = Lists.newArrayList();
	private List<DbColumn> drop = Lists.newArrayList();

	public String generateSql() {
		if (previous == null) {
			return DdlBuildUtils.generateCreateSql(current);
		}
		classify();
		try (FormatterWrapper fw = new FormatterWrapper(new Formatter(new StringBuilder()))) {
			for (DbColumn dbColumn : add) {
				fw.formatln(dbColumn.alterAdd(current.getName()));
			}
			for (DbColumn dbColumn : modify) {
				fw.formatln(dbColumn.alterModify(current.getName()));
			}
			for (DbColumn dbColumn : drop) {
				fw.formatln(dbColumn.alterDrop(current.getName()));
			}
			return fw.toString();
		}
	}

	private void classify() {
		Map<String, DbColumn> currentMap = buildMap(current);
		Map<String, DbColumn> previousMap = buildMap(previous);
		for (Entry<String, DbColumn> e : currentMap.entrySet()) {
			String columName = e.getKey();
			DbColumn curr = e.getValue();
			DbColumn prev = previousMap.remove(columName);
			if (prev == null) {
				add.add(curr);
			} else {
				String oneLineCurr = curr.getDefinitionSql();
				String oneLinePrev = prev.getDefinitionSql();
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

}
