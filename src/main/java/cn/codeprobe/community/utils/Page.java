package cn.codeprobe.community.utils;

/**
 * 封装分页
 * 通过浏览器所传过来的current、limit参数，获取分页所需要更多数据
 */
public class Page {

    // 当前页码
    private int current = 1;
    // 显示上限
    private int limit = 10;

    // 数据总数(用于计算总页数)
    private int rows;
    // 查询路径(用于复用分页链接)
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current >= 1) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit >= 1 && limit <= 100) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows >= 1) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取当前页的起始行
     *
     * @return
     */
    public int getOffset() {
        //current * limit - limit
        return (current - 1) * limit;
    }

    /**
     * 获取总页数
     *
     * @return
     */
    public int getTotal() {
        return rows % limit == 0 ? rows / limit : rows / limit + 1;
    }

    /**
     * 获取起始页码
     *
     * @return
     */
    public int getFrom() {
        return (current - 2) < 1 ? 1 : current - 2;
    }

    /**
     * 获取结束页码
     *
     * @return
     */
    public int getTo() {
        return (current + 2) > getTotal() ? getTotal() : current + 2;
    }
}
