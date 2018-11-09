package cn.neusoft.xuxiao.exception;

public class CommonRuntimeException extends RuntimeException
{
  private static final long serialVersionUID = 154141025257886487L;
  private String code;
  private String msg;

  public CommonRuntimeException(String code, String msg, Throwable e)
  {
    super(msg, e);
    this.code = code;
    this.msg = msg;
  }

  public CommonRuntimeException(String code, String msg) {
    super(msg);
    this.code = code;
    this.msg = msg;
  }

  public String getCode() {
    return this.code;
  }

  public String getMsg() {
    return this.msg;
  }

  public String toString()
  {
    StringBuilder sb = new StringBuilder(getClass().getName())
      .append("[ERRORCODE=")
      .append(getCode())
      .append("] [")
      .append(this.msg == null ? "" : this.msg)
      .append("]");
    return sb.toString();
  }
}