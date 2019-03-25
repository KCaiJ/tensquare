package entity;

import java.io.Serializable;
import java.util.List;

/***
 * 分页结果类
 */
public class PageResult<T>  implements Serializable {
    private Long total; //分页长度
    private List<T> rows;//分页结果

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
