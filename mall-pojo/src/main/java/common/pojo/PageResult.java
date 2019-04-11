package common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jack Wen.
 * @ClassName PageResult
 * @dsecription 分页结果类
 * @data 2019/3/15
 * @Vserion 1.0
 */
public class PageResult implements Serializable{

	/** 总记录数 */
	private long total;

	/** 当前页的记录*/
	private List rows;

	public PageResult(long total, List rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}
	
	
}
