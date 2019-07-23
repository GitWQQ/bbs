package org.ssm.dufy.util;

public class PageUtil {
	//记录的总数
	private Integer count;
	//一页显示多少数据，查询条数
	private Integer limit=8;
	//当前第几页	
	private Integer curr;
	//startlimit
	private Integer startlimit;
	
	
	public Integer getStartlimit() {
		return startlimit;
	}

	public void setStartlimit(Integer startlimit) {
		this.startlimit = startlimit;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getCurr() {
		return curr;
	}

	public void setCurr(Integer curr) {
		this.curr = curr;
	}

	@Override
	public String toString() {
		return "PageUtil [count=" + count + ", limit=" + limit + ", curr=" + curr + ", startlimit=" + startlimit + "]";
	}
	
}
