
package cn.calendo.tcmdistribution.common.exception;

/**
 * 自定义异常
 *
 * @author calendo
 */
public class GlobalException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private String msg;
    private int status = 500;
    
    public GlobalException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public GlobalException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public GlobalException(String msg, int status) {
		super(msg);
		this.msg = msg;
		this.status = status;
	}
	
	public GlobalException(String msg, int status, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return status;
	}

	public void setCode(int status) {
		this.status = status;
	}
	
	
}
